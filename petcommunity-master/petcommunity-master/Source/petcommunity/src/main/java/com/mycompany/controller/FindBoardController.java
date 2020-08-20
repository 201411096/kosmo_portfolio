package com.mycompany.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.modules.time.Time;
import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.mycompany.domain.FindBoardVO;
import com.mycompany.domain.LostBoardVO;
import com.mycompany.domain.MemberVO;
import com.mycompany.domain.PaginationVO;
import com.mycompany.service.FindBoardServiceImpl;
import com.mycompany.service.LostBoardServiceImpl;
import com.mycompany.service.MemberServiceImpl;
import com.mycompany.util.*;

@Controller
public class FindBoardController {
	@Autowired
	FindBoardServiceImpl findBoardService;
	@Autowired
	LostBoardServiceImpl lostBoardService;
	@Autowired
	MemberServiceImpl memberService;
	
	@ResponseBody
	   @RequestMapping(value = "/checkDogBreed.do", produces = "application/text; charset=utf-8")
	   public String toTheDeepLearning(@RequestParam HashMap<Object, Object> param,  MultipartHttpServletRequest filelist, HttpServletRequest request) {

	       String breed=null;
	       //===========업로드할 이미지 받아오기 ========================
	      Iterator<String> iter = filelist.getFileNames(); 
	       MultipartFile mfile = null; 
	       String fieldName = "";
	       while (iter.hasNext()) { 
	    	   //업로드된 파일 가져오기
	           fieldName = (String) iter.next();          
	           mfile = filelist.getFile(fieldName);  //저장된 파일 객체
	       }
	   
	       File file= null; 
	       try { 
	    	   //업로드 된 파일 file형식으로 변환
	          file = new File(mfile.getOriginalFilename());
	          file.createNewFile(); 
	          FileOutputStream fos = new FileOutputStream(file);
	          fos.write(mfile.getBytes());
	          fos.close(); 
	       }catch(Exception e) { 
	          e.printStackTrace();
	       }	       
	   //=============python과 socket통신으로 사진 보낸후 분석 결과 받기 =========================================
	      OutputStream out=null;
	      FileInputStream fin=null;
	      BufferedOutputStream bos = null;
	      BufferedReader br = null;
	      Socket soc=null; 
	     
	   //============소켓 열고 이미지 전송하기===================================      
	      try {
	         soc = new Socket("192.168.0.18", 8001); // 192.168.0.77은 루프백 아이피로 자신의 아이피를 반환해주고,         
	         out = soc.getOutputStream(); // 서버에 바이트단위로 데이터를 보내는 스트림을 개통합니다. 
	         bos = new BufferedOutputStream(out);
	         fin = new FileInputStream(file); // FileInputStream - 파일에서 입력받는 스트림을 개통합니다.	         
	         // change "1234" to "0000001234", to make sure 10 size.
	         String flen = String.valueOf(file.length());
	         String header = "0000000000".substring(0, 10-(int)flen.length()) +flen;
	         byte[] buffer = new byte[1024]; // 바이트단위로 임시저장하는 버퍼를 생성합니다.
	         int len; // 전송할 데이터의 길이를 측정하는 변수입니다.
	         int data = 0; // 전송횟수, 용량을 측정하는 변수입니다.
	      // FileInputStream을 통해 파일에서 입력받은 데이터를 버퍼에 임시저장하고 그 길이를 측정합니다.
	         while ((len = fin.read(buffer)) >= 0) { 
	            data++; // 데이터의 양을 측정합니다.
	         }         
	         int datas = data; // 아래 for문을 통해 data가 0이되기때문에 임시저장한다.
	         fin.close();
	         fin = new FileInputStream(file); // FileInputStream이 만료되었으니 새롭게 개통합니다.

	         len = 0;
	         SendingDogImageThread sdt = new SendingDogImageThread(soc, bos, len, data, buffer, fin, header);
	         boolean threadIsAlive = sdt.isAlive();
	         sdt.start();
	         sdt.join();//sdt가 처리될때까지 메인쓰레드는 기다림 
	         fin.close();

	      //==================결과값 받기=================================
	         
	         
	         br = new BufferedReader(new InputStreamReader(soc.getInputStream()));	         
	         bos.flush();	 
	         DogBreedClassifyingThread cw = new DogBreedClassifyingThread(soc, br);
	         cw.start();
	         cw.join();//cw가 처리될때까지 메인쓰레드가 기다림 
	         
	         while(true) { 
	        	 breed = cw.getBreed();
	        	 if(breed!=null)break;
	         }
	      }catch(Exception e1) {
	         e1.printStackTrace();
	      }finally {
	         try {
	        	fin.close();
	            bos.close();
	            br.close();
	            soc.close();
	         } catch (IOException e) {
	            e.printStackTrace();
	         }
	      }
	      System.out.println("return");
	      System.out.println("return값 : " + breed);
	      
	      return breed;
	   }

	
	@ResponseBody
	@RequestMapping(value = "/findboardListWithPaging.do", produces = "application/json; charset=utf-8")
	public Map getCommunityBoardList(@RequestParam(defaultValue="1") int curPage, String searchWord, String searchType, HttpServletRequest request) {
		Map result = new HashMap();
		Map searchMap = new HashMap();
		searchMap.put("searchType", searchType);
		searchMap.put("searchWord", searchWord);
		List<FindBoardVO> findBoardVOList = findBoardService.selectFindBoard(searchMap);		
		PaginationVO paginationVO = new PaginationVO(findBoardVOList.size(), curPage, 12);
		searchMap.put("startRow", paginationVO.getStartIndex()+1);
		searchMap.put("endRow", paginationVO.getStartIndex()+paginationVO.getPageSize());
				
		findBoardVOList = findBoardService.selectFindBoardWithPaging(searchMap);
		result.put("pagination", paginationVO);
		result.put("findBoardVOList", findBoardVOList);
		result.put("findBoardVOListSize", findBoardVOList.size());
		for(int i=0; i<findBoardVOList.size(); i++) {
			if(findBoardVOList.get(i).getMemberId()==null) {
				findBoardVOList.get(i).setMemberId(findBoardVOList.get(i).getFindboardName());
			}
		}
		//그림파일이 있으면 가져옴
		ArrayList<String> fileName = new ArrayList<String>();
		ArrayList<String> img = new ArrayList<String>();
		for(int i=0; i<findBoardVOList.size(); i++) {
			String directoryPath = request.getSession().getServletContext().getRealPath("resources/imgs")+"/findboard/"+findBoardVOList.get(i).getFindboardId();
			File dir = new File(directoryPath);
			File fileList [] = dir.listFiles();
			if(fileList!=null && fileList.length != 0) {//fileList가 not null이면
				fileName.add(findBoardVOList.get(i).getFindboardId()+"/"+fileList[0].getName());
			}else {
				fileName.add("default/1.png");
			}
		}		
		result.put("img", fileName);
		return result;
	}
	@RequestMapping(value = "/insertFindBoard.do", method=RequestMethod.POST, produces = "application/text; charset=utf-8")
	public ModelAndView insertFindBoard(FindBoardVO findBoardVO, HttpSession session, HttpServletRequest request, MultipartHttpServletRequest mtfRequest) throws IOException
	{
		ModelAndView mv = new ModelAndView();
		findBoardVO.setFindboardReadcount(0); // 조회수 0으로 설정
		if(session.getAttribute("memberVO")!=null) {
			findBoardVO.setMemberId( ((MemberVO)session.getAttribute("memberVO")).getMemberId() ); //로그인 되어있는 상태면 memberId값 세팅
			findBoardVO.setFindboardName( ((MemberVO)session.getAttribute("memberVO")).getMemberId() );
			findBoardVO.setFindboardTel( ((MemberVO)session.getAttribute("memberVO")).getMemberTel() );
		}
		int insertFlag = findBoardService.insertFindBoard(findBoardVO);
		if (insertFlag == 1) {
			FileUpload.makeDirectory(request.getSession().getServletContext().getRealPath("resources/imgs")+"/findboard/");
			FileUpload.uploadFiles(mtfRequest, request.getSession().getServletContext().getRealPath("resources/imgs")+"/findboard/" + findBoardVO.getFindboardId() + "/");
		}
		//-------------------------------------------------------------
		//push알림 title, contents
		System.out.println("X :" + findBoardVO.getFindboardX());
		System.out.println("Y :" + findBoardVO.getFindboardY());
		String title = findBoardVO.getFindboardTitle();
		String content = findBoardVO.getFindboardContent();
		String link = "https://115.91.88.227:60004/petcommunity/getFindBoard.do?findboardId="+findBoardVO.getFindboardId();
		// find 게시물 포스팅 시 위치 기준 반경 2km내 lost게시물 작성자에게 푸시 알람 보내는 소스코드
		List<LostBoardVO> lostBoardVO = lostBoardService.findPeopleByLocationOfLostPost(findBoardVO);
		if(lostBoardVO!=null) {
			for(LostBoardVO i : lostBoardVO) {
				String lostBoardWriter = i.getMemberId();
				String userDeviceIdKey=memberService.selectListPushTarget(lostBoardWriter);
				//String AUTH_KEY_FCM = "AAAAI2DgPEc:APA91bFUsctMK1XKNhZH6WUe4SW7FmJNKP_qQfUVzYVvRMrMp5Ig2Tx5D6CldfuVdtgkaeN2O-IEYfZH3nXRdgZes0kzazXvtuuz8rYlvxOH8Dtzfh74VekTsGVZf3GrSzZMt7sgHbX4";
				String AUTH_KEY_FCM = "AAAArrPhL3k:APA91bG-1ukftodsLHL_kHV-gRZLZImCeZAvgG8PZiLs0YLVA_6n1LJPNSAgs9ca86G9TfQLTC0IHlc7kM6Uu8CnPcBqyUnx1QH7v7IwfVhL7aeoXAYjdXjhG4FnXCI0ijeAg7B8uKR5";
				String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";
				String authKey = AUTH_KEY_FCM;
				String FMCurl = API_URL_FCM;
				
				URL url = new URL(FMCurl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				
				conn.setUseCaches(false); 
				conn.setDoInput(true);
				conn.setDoOutput(true);
				
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Authorization","key="+authKey);
				conn.setRequestProperty("Content-Type","application/json");
				
				JSONObject json = new JSONObject();
				if(userDeviceIdKey != null) {
					json.put("to",userDeviceIdKey.trim());
					JSONObject info = new JSONObject();
					info.put("title", title);   // Notification title
					info.put("body", content); // Notification body
					info.put("link", link);
					json.put("notification", info);
					
					OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
					System.out.println(">" + json.toString());
					wr.write(json.toString());
					wr.flush();
					conn.getInputStream();   
				}   
				
			}
		}
		//-------------------------------------------------------------
		mv.setViewName("/findboardlist");
		return mv;
	}
	@RequestMapping(value="/getFindBoard.do")
	public ModelAndView getFindBoard(FindBoardVO findBoardVO, HttpServletRequest request, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		findBoardVO = findBoardService.getFindBoard(findBoardVO);
		findBoardService.increaseFindBoardReadcount(findBoardVO);
		mv.setViewName("/findBoardContent");
		mv.addObject("findBoardContent", findBoardVO);
		
		FileUpload.makeDirectory(request.getSession().getServletContext().getRealPath("resources/imgs")+"/findboard/"+findBoardVO.getFindboardId());
		String directoryPath = request.getSession().getServletContext().getRealPath("resources/imgs")+"/findboard/"+Integer.toString(findBoardVO.getFindboardId());		
		File dir = new File(directoryPath);
		File fileList [] = dir.listFiles();
		ArrayList<File> fileArrayList = new ArrayList<File>();		
		for(File file : fileList) {
			fileArrayList.add(file);
		}
		if(fileArrayList.size()>=1)
			fileArrayList.remove(0);
		if(fileList.length>=1)
			mv.addObject("file", fileList[0]);
		else
			mv.addObject("fileflag", -1);		
		
		if( ((MemberVO)session.getAttribute("memberVO"))!=null ) { //로그인이 되어있는 상태라면
			if( ((MemberVO)session.getAttribute("memberVO")).getMemberId().equals(findBoardVO.getMemberId()) ) { //로그인이 되어있으면서 글 작성자와 같은 아이디면
				mv.addObject("isWriterFlag", 1);
			}else
				mv.addObject("isWriterFlag", 0);
		}else {
			mv.addObject("isWriterFlag", 0);
		}
		mv.addObject("fileList", fileArrayList);
		mv.addObject("directoryPath", directoryPath);
		return mv;
	}
	@RequestMapping(value = "/deleteFindBoard.do", method=RequestMethod.POST, produces = "application/text; charset=utf-8")
	public ModelAndView deleteFindBoard(FindBoardVO findBoardVO, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();		
		findBoardService.deleteFindBoard(findBoardVO);
		FileUpload.deleteDirectory(request.getSession().getServletContext().getRealPath("resources/imgs")+"/findboard/" + findBoardVO.getFindboardId());
		mv.setViewName("/findboardlist");
		return mv;
	}
	@RequestMapping("/loadFindBoardUpdate.do")
	public ModelAndView loadFindBoardUpdate(FindBoardVO findBoardVO, HttpServletRequest request, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		findBoardVO = findBoardService.getFindBoard(findBoardVO);
		mv.addObject("findBoardContent", findBoardVO);
		
		String directoryPath = request.getSession().getServletContext().getRealPath("resources/imgs")+"/findboard/"+Integer.toString(findBoardVO.getFindboardId());
		File dir = new File(directoryPath);
		File fileList [] = dir.listFiles();
		ArrayList<String> fileNameList = new ArrayList<String>(); 
		for(int i=0; i<fileList.length; i++)
		{
			fileNameList.add(fileList[i].getName());
		}
			
		if(fileList.length<1)
			mv.addObject("fileflag", -1);		
		
		mv.addObject("fileList", fileList);
		mv.addObject("fileNameList", fileNameList);
		mv.addObject("directoryPath", directoryPath);
		
		mv.setViewName("/findBoardUpdate");
		return mv;
	}
	@RequestMapping(value="/updateFindBoard.do", method=RequestMethod.POST, produces = "application/text; charset=utf-8")
	public ModelAndView updateFindBoard(FindBoardVO findBoardVO, HttpSession session, HttpServletRequest request, MultipartHttpServletRequest mtfRequest, String [] filename) throws IOException{
		ModelAndView mv = new ModelAndView();		
		if(session.getAttribute("memberVO")!=null) {
			findBoardVO.setMemberId( ((MemberVO)session.getAttribute("memberVO")).getMemberId() ); //로그인 되어있는 상태면 memberId값 세팅
			findBoardVO.setFindboardName( ((MemberVO)session.getAttribute("memberVO")).getMemberId() );
			findBoardVO.setFindboardTel( ((MemberVO)session.getAttribute("memberVO")).getMemberTel() );
		}
		
		int updateFlag = findBoardService.updateFindBoard(findBoardVO);
		if(updateFlag==1) {
			FileUpload.deleteFileWithoutList(request.getSession().getServletContext().getRealPath("resources/imgs")+"/findboard/" + findBoardVO.getFindboardId(), filename);
			FileUpload.uploadFiles(mtfRequest, request.getSession().getServletContext().getRealPath("resources/imgs")+"/findboard/" + findBoardVO.getFindboardId() + "/");
		}
			
		mv.setViewName("/findboardlist");
		return mv;
	}
	
	@RequestMapping(value = "/autoCompleteForFindBoard.do", method = RequestMethod.GET, produces = "application/text; charset=utf-8")
	@ResponseBody
	public String autoCompleteForFindBoard(FindBoardVO findBoardVO, String searchWord, String searchType) {
		Map searchMap = new HashMap();
		searchMap.put("searchType", searchType);
		searchMap.put("searchWord", searchWord);
		List<String> stringList = findBoardService.selectString(searchMap);
		if(stringList!=null) {
			String[] array = new String[stringList.size()];
			for(int i=0; i< stringList.size(); i++) {
				array[i] = stringList.get(i);
			}
			Gson gson = new Gson();
			return gson.toJson(array);
		}else {
			return null;
		}	
	}

}

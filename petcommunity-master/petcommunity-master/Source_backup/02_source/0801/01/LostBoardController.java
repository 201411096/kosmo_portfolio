package com.mycompany.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
import com.mycompany.util.FileUpload;

@Controller
public class LostBoardController {
	@Autowired
	LostBoardServiceImpl lostBoardService;
	@Autowired
	FindBoardServiceImpl findBoardService;
	@Autowired
	MemberServiceImpl memberService;
	@ResponseBody
	@RequestMapping(value = "/lostboardListWithPaging.do", produces = "application/json; charset=utf-8")
	public Map getCommunityBoardList(@RequestParam(defaultValue="1") int curPage, String searchWord, String searchType, HttpServletRequest request) {
		Map result = new HashMap();
		Map searchMap = new HashMap();
		searchMap.put("searchType", searchType);
		searchMap.put("searchWord", searchWord);
		List<LostBoardVO> lostBoardVOList = lostBoardService.selectLostBoard(searchMap);
		
		PaginationVO paginationVO = new PaginationVO(lostBoardVOList.size(), curPage, 12);
		searchMap.put("startRow", paginationVO.getStartIndex()+1);
		searchMap.put("endRow", paginationVO.getStartIndex()+paginationVO.getPageSize());
				
		lostBoardVOList = lostBoardService.selectLostBoardWithPaging(searchMap);
		
		result.put("pagination", paginationVO);
		result.put("lostBoardVOList", lostBoardVOList);
		result.put("lostBoardVOListSize", lostBoardVOList.size());
		
		//그림파일이 있으면 가져옴
		ArrayList<String> fileName = new ArrayList<String>();
		for(int i=0; i<lostBoardVOList.size(); i++) {
			String directoryPath = request.getSession().getServletContext().getRealPath("resources/imgs")+"/lostboard/"+lostBoardVOList.get(i).getLostboardId();
			FileUpload.makeDirectory(request.getSession().getServletContext().getRealPath("resources/imgs")+"/lostboard/"+lostBoardVOList.get(i).getLostboardId());
			
			File dir = new File(directoryPath);
			File fileList [] = dir.listFiles();
			
			if(fileList.length!=0) {
				fileName.add(fileList[0].getName());
			}else {
				fileName.add("__no__image__");
				System.out.println("이미지 없음");
			}
		}
//		ArrayList<String> fileName = new ArrayList<String>();
//		ArrayList<String> img = new ArrayList<String>();
//		for(int i=0; i<lostBoardVOList.size(); i++) {
//			String directoryPath = request.getSession().getServletContext().getRealPath("resources/imgs")+"/lostboard/"+lostBoardVOList.get(i).getLostboardId();
//					
//			File dir = new File(directoryPath);
//			File fileList [] = dir.listFiles();
//			
//			if(fileList!=null) {//fileList가 not null이면
//				for(File file : fileList) {//file의 개수만큼
//					fileName.add(lostBoardVOList.get(i).getLostboardId()+"/"+file.getName());			//리스트에 저장				
//				}
//			}		
//		}
		result.put("img", fileName);
		return result;
	}
	@RequestMapping(value = "/insertLostBoard.do", method=RequestMethod.POST, produces = "application/text; charset=utf-8")
	public ModelAndView insertLostBoard(LostBoardVO lostBoardVO, HttpSession session, HttpServletRequest request, MultipartHttpServletRequest mtfRequest) throws IOException
	{
		ModelAndView mv = new ModelAndView();
		lostBoardVO.setLostboardReadcount(0); // 조회수 0으로 설정
		if(session.getAttribute("memberVO")!=null) {
			lostBoardVO.setMemberId( ((MemberVO)session.getAttribute("memberVO")).getMemberId() ); //로그인 되어있는 상태면 memberId값 세팅
		}
		int insertFlag = lostBoardService.insertLostBoard(lostBoardVO);
		if (insertFlag == 1) {
			FileUpload.makeDirectory(request.getSession().getServletContext().getRealPath("resources/imgs")+"/lostboard/");
			FileUpload.uploadFiles(mtfRequest, request.getSession().getServletContext().getRealPath("resources/imgs")+"/lostboard/" + lostBoardVO.getLostboardId() + "/");
		}
		//----------------------------------------------------------------------
		// 푸쉬 알람 보내기
		//----------------------------------------------------------------------
		lostBoardVO.setLostboardLocation("서울 강남구 신사동 537-5");
		// 게시물 위치 기반 주변 회원 select
		List<MemberVO> memberVO = memberService.selectPeopleAroundLocation(lostBoardVO);
		// push알림 title, contents
		String title = lostBoardVO.getLostboardTitle();
		String content = lostBoardVO.getLostboardContent();
		// 푸시 알림 보내기
		for (MemberVO i : memberVO) {
			String userDeviceIdKey = i.getMemberToken();
			String AUTH_KEY_FCM = "AAAAI2DgPEc:APA91bFUsctMK1XKNhZH6WUe4SW7FmJNKP_qQfUVzYVvRMrMp5Ig2Tx5D6CldfuVdtgkaeN2O-IEYfZH3nXRdgZes0kzazXvtuuz8rYlvxOH8Dtzfh74VekTsGVZf3GrSzZMt7sgHbX4";
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
			json.put("to",userDeviceIdKey.trim());
			JSONObject info = new JSONObject();
			info.put("title", title);   // Notification title
			info.put("body", content); // Notification body
			json.put("notification", info);
			
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			System.out.println(">" + json.toString());
			wr.write(json.toString());
			wr.flush();
			conn.getInputStream();  
		}
		
		
		//----------------------------------------------------------------------		
		
		mv.setViewName("/lostboardlist");
		return mv;
	}
	@RequestMapping(value="/getLostBoard.do")
	public ModelAndView getLostBoard(LostBoardVO lostBoardVO, HttpServletRequest request, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		lostBoardVO = lostBoardService.getLostBoard(lostBoardVO);
		lostBoardService.increaseLostBoardReadcount(lostBoardVO);
		mv.setViewName("/lostBoardContent");
		mv.addObject("lostBoardContent", lostBoardVO);
		
		FileUpload.makeDirectory(request.getSession().getServletContext().getRealPath("resources/imgs")+"/lostboard/"+lostBoardVO.getLostboardId());
		String directoryPath = request.getSession().getServletContext().getRealPath("resources/imgs")+"/lostboard/"+Integer.toString(lostBoardVO.getLostboardId());
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
			if( ((MemberVO)session.getAttribute("memberVO")).getMemberId().equals(lostBoardVO.getMemberId()) ) { //로그인이 되어있으면서 글 작성자와 같은 아이디면
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
	@RequestMapping(value = "/deleteLostBoard.do", method=RequestMethod.POST, produces = "application/text; charset=utf-8")
	public ModelAndView deleteLostBoard(LostBoardVO lostBoardVO, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();		
		lostBoardService.deleteLostBoard(lostBoardVO);
		FileUpload.deleteDirectory(request.getSession().getServletContext().getRealPath("resources/imgs")+"/lostboard/" + lostBoardVO.getLostboardId());
		mv.setViewName("/lostboardlist");
		return mv;
	}
	@RequestMapping("/loadLostBoardUpdate.do")
	public ModelAndView loadLostBoardUpdate(LostBoardVO lostBoardVO, HttpServletRequest request, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		lostBoardVO = lostBoardService.getLostBoard(lostBoardVO);
		mv.addObject("lostBoardContent", lostBoardVO);
		
		String directoryPath = request.getSession().getServletContext().getRealPath("resources/imgs")+"/lostboard/"+Integer.toString(lostBoardVO.getLostboardId());
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
		
		mv.setViewName("/lostBoardUpdate");
		return mv;
	}
	@RequestMapping(value="/updateLostBoard.do", method=RequestMethod.POST, produces = "application/text; charset=utf-8")
	public ModelAndView updateLostBoard(LostBoardVO lostBoardVO, HttpSession session, HttpServletRequest request, MultipartHttpServletRequest mtfRequest, String [] filename) throws IOException{
		ModelAndView mv = new ModelAndView();		
		if(session.getAttribute("memberVO")!=null) {
			lostBoardVO.setMemberId( ((MemberVO)session.getAttribute("memberVO")).getMemberId() ); //로그인 되어있는 상태면 memberId값 세팅
		}
		
		int updateFlag = lostBoardService.updateLostBoard(lostBoardVO);
		if(updateFlag==1) {
			FileUpload.deleteFileWithoutList(request.getSession().getServletContext().getRealPath("resources/imgs")+"/lostboard/" + lostBoardVO.getLostboardId(), filename);
			FileUpload.uploadFiles(mtfRequest, request.getSession().getServletContext().getRealPath("resources/imgs")+"/lostboard/" + lostBoardVO.getLostboardId() + "/");
		}
			
		mv.setViewName("/lostboardlist");
		return mv;
	}
	
	@RequestMapping(value = "/autoCompleteForLostBoard.do", method = RequestMethod.GET, produces = "application/text; charset=utf-8")
	@ResponseBody
	public String autoCompleteForLostBoard(LostBoardVO lostBoardVO, String searchWord, String searchType) {
		Map searchMap = new HashMap();
		searchMap.put("searchType", searchType);
		searchMap.put("searchWord", searchWord);
		List<String> stringList = lostBoardService.selectString(searchMap);
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
	
	@RequestMapping(value = "/autoCompleteForMap.do", method = RequestMethod.GET, produces = "application/text; charset=utf-8")
	@ResponseBody
	public String autoCompleteForMap(String searchWord, String searchType) {
		Map searchMap = new HashMap();
		searchMap.put("searchType", searchType);
		searchMap.put("searchWord", searchWord);
		List<String> stringList = lostBoardService.selectString(searchMap);
		List<String> stringList2 = findBoardService.selectString(searchMap);
		if(stringList!=null || stringList2!=null) {
			String[] array = new String[stringList.size() + stringList2.size()];
			for(int i=0; i< stringList.size(); i++) {
				array[i] = stringList.get(i);
//				if(searchType.equals("location")) {
//					StringTokenizer st= new StringTokenizer(stringList.get(i));
//					String temp = "";
//					temp+=st.nextToken()+" ";
//					temp+=st.nextToken();
//					array[i]=temp;
//				}
			}
			for(int i=0; i<stringList2.size(); i++) {
				array[stringList.size()+i] = stringList2.get(i);
			}
			Gson gson = new Gson();
			return gson.toJson(array);
		}
		return "";
	}
	
	@RequestMapping(value = "/lostboardListWithoutPaging.do", produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map getLostBoardListWithoutPaging(HttpServletRequest request, @RequestParam(defaultValue="")String locationForSearch, int timeForSearch)
	{
		Map result = new HashMap();
		Map searchMap = new HashMap();
		Double timeForSearchArray [] = {0.0, 1.0/48, 1.0/24, 1.0/8, 1.0/2, 1.0, 7.0, 30.0};
		searchMap.put("locationForSearch", locationForSearch);
		searchMap.put("timeForSearch", timeForSearchArray[timeForSearch]);
		
		List<LostBoardVO> lostBoardVOList = lostBoardService.selectLostBoardForMap(searchMap);
		List<FindBoardVO> findBoardVOList = findBoardService.selectFindBoardForMap(searchMap);
		
		result.put("lostBoardVOList", lostBoardVOList);
		result.put("lostBoardVOListSize", lostBoardVOList.size());
		result.put("findBoardVOList", findBoardVOList);
		result.put("findBoardVOListSize", findBoardVOList.size());
		
		List<HashMap> lostBoardFileList = new ArrayList<HashMap>();
		List<HashMap> findBoardFileList = new ArrayList<HashMap>();
		for(int i=0; i<lostBoardVOList.size(); i++) {
			HashMap map = new HashMap();
			String directoryPath = request.getSession().getServletContext().getRealPath("resources/imgs")+"/lostboard/"+Integer.toString(lostBoardVOList.get(i).getLostboardId());
			File dir = new File(directoryPath);
			File fileList [] = dir.listFiles();
			if(fileList!=null && fileList.length>=1) {
				map.put("filename",  fileList[0].getName());
			}else {
				map.put("filename",  "??");
			}
			lostBoardFileList.add(map);
		}
		for(int i=0; i<findBoardVOList.size(); i++) {
			HashMap map = new HashMap();
			String directoryPath = request.getSession().getServletContext().getRealPath("resources/imgs")+"/findboard/"+Integer.toString(findBoardVOList.get(i).getFindboardId());
			File dir = new File(directoryPath);
			File fileList [] = dir.listFiles();
			if(fileList!=null && fileList.length>=1) {
				map.put("filename",  fileList[0].getName());
			}else {
				map.put("filename",  "??");
			}
			findBoardFileList.add(map);
		}
		
		result.put("lostBoardFileList", lostBoardFileList);
		result.put("findBoardFileList", findBoardFileList);
		return result;		
	}
}

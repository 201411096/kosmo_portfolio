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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
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
public class FindBoardController {
	@Autowired
	FindBoardServiceImpl findBoardService;
	@Autowired
	LostBoardServiceImpl lostBoardService;
	@Autowired
	MemberServiceImpl memberService;
	
	@ResponseBody
	@RequestMapping(value = "/findboardListWithPaging.do", produces = "application/json; charset=utf-8")
	public Map getCommunityBoardList(@RequestParam(defaultValue="1") int curPage, String searchWord, String searchType) {
		Map result = new HashMap();
		Map searchMap = new HashMap();
		searchMap.put("searchType", searchType);
		searchMap.put("searchWord", searchWord);
		List<FindBoardVO> findBoardVOList = findBoardService.selectFindBoard(searchMap);		
		PaginationVO paginationVO = new PaginationVO(findBoardVOList.size(), curPage);
		searchMap.put("startRow", paginationVO.getStartIndex()+1);
		searchMap.put("endRow", paginationVO.getStartIndex()+paginationVO.getPageSize());
				
		findBoardVOList = findBoardService.selectFindBoardWithPaging(searchMap);
		result.put("pagination", paginationVO);
		result.put("findBoardVOList", findBoardVOList);
		result.put("findBoardVOListSize", findBoardVOList.size());
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
		// find 게시물 포스팅 시 위치 기준 반경 2km내 lost게시물 작성자에게 푸시 알람 보내는 소스코드
		List<LostBoardVO> lostBoardVO = lostBoardService.findPeopleByLocationOfLostPost(findBoardVO);
		for(LostBoardVO i : lostBoardVO) {
			String lostBoardWriter = i.getMemberId();
			String userDeviceIdKey=memberService.selectListPushTarget(lostBoardWriter);
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

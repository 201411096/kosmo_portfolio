package com.mycompany.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.domain.MemberVO;
import com.mycompany.domain.PaginationVO;
import com.mycompany.domain.QnaVO;
import com.mycompany.service.QnaService;
import com.mycompany.util.FileUpload;

@Controller
public class QnaController {

	@Autowired
	private QnaService qnaService;

	/*
	 * 함수 이름 : getQnaBoardList
	 * 주요 기능 : 게시판 페이지 이동 
	 */
	@RequestMapping(value = "/cs.do")
	public ModelAndView getQnaBoardList() {
		ModelAndView mv = new ModelAndView();

		mv.setViewName("qnaBoardList");

		return mv;
	}

	/*
	 * 함수 이름 : getQnaBoardWrite 
	 * 주요 기능 : 게시판 작성페이지 이동 
	 * 함수 내용 : 비로그인의 경우 로그인 페이지 이동, 로그인의 경우 작성페이지 이동
	 */
	@RequestMapping("/write.do")
	public ModelAndView getQnaBoardWrite(QnaVO qnavo, HttpSession session) {

		ModelAndView mv = new ModelAndView();
		if (session.getAttribute("memberVO") == null) {
			mv.setViewName("login");
		} else {
			mv.setViewName("qnaBoardWrite");
		}
		return mv;
	}

	/*
	 * 함수 이름 : insertQnaBoard 
	 * 주요 기능 : 작성한 글 입력, 목록 페이지 이동 
	 * 함수 내용 : 연결 되어있는 아이디 세팅, 파일 첨부시 업로드
	 */
	@RequestMapping(value = "/writeIntoQna.do", method = RequestMethod.POST, produces = "application/text; charset=utf-8")
	public String insertQnaBoard(QnaVO qnavo, HttpSession session, HttpServletRequest request,
			MultipartHttpServletRequest mtfRequest) throws IOException {
		
		ModelAndView mv = new ModelAndView();
		qnavo.setMemberId(((MemberVO) session.getAttribute("memberVO")).getMemberId());
		int result = qnaService.insertQnaBoard(qnavo);

		if (result == 1) {
			FileUpload.makeDirectory(
					request.getSession().getServletContext().getRealPath("resources/imgs") + "/qnaboard/");
			FileUpload.uploadFiles(mtfRequest, request.getSession().getServletContext().getRealPath("resources/imgs")
					+ "/qnaboard/" + qnavo.getQuestionboardId() + "/");
		}
		mv.setViewName("qnaBoardWrite");

		return "redirect:/cs.do";
	}

	/*
	 * 함수 이름 : selectQnaBoardList 
	 * 주요 기능 : 게시판 목록 이동, 체크박스 및 검색어 입력시 출력, 페이징처리 
	 * 함수 내용 : 연결 되어있는 아이디 세팅, 체크박스 및 검색어 해쉬맵으로 받아 매퍼 연결
	 */
	@ResponseBody
	@RequestMapping(value = "/qnaList.do", produces = "application/json; charset=utf-8")
	public Map selectQnaBoardList(@RequestParam(defaultValue = "1") int curPage, QnaVO qnavo, String searchType,
			HttpSession session, String searchWord, HttpServletRequest request) {
		
		Map map = new HashMap();
		Map result = new HashMap();
		MemberVO membervo = (MemberVO) session.getAttribute("memberVO");
		map.put("searchType", searchType);
		map.put("searchWord", searchWord);
		
		List<QnaVO> qnavoList = qnaService.selectKeyword(map);
		
		PaginationVO paginationVO = new PaginationVO(qnavoList.size(), curPage);
		map.put("startRow", paginationVO.getStartIndex() + 1);
		map.put("endRow", paginationVO.getStartIndex() + paginationVO.getPageSize());
		qnavoList = qnaService.selectQnaBoardWithPaging(map);
		for(int i=0;i<qnavoList.size();i++) {
			qnavoList.get(i).setQuestionboardUploadtime(qnavoList.get(i).getQuestionboardUploadtime().substring(0,10));
			
		}
		result.put("pagination", paginationVO);
		result.put("QnaBoardVOList", qnavoList);
		result.put("QnaBoardVOListSize", qnavoList.size());
		result.put("membervo", membervo);
		
		ArrayList<String> fileArrayList = new ArrayList<String>();
		for (int i = 0; i < qnavoList.size(); i++) {
			String directoryPath = request.getSession().getServletContext().getRealPath("resources/imgs") + "/qnaboard/"
					+ Integer.toString(qnavoList.get(i).getQuestionboardId());
			File dir = new File(directoryPath);
			File fileList[] = dir.listFiles();

			if (fileList == null || fileList.length == 0) {
				fileArrayList.add("empty");
			} else if (fileList.length != 0) {
				fileArrayList.add(fileList[0].getName());		
			}
		}
		result.put("fileList", fileArrayList);
			
		return result;
	}

	/*
	 * 함수 이름 : getQnaBoardContent 
	 * 주요 기능 : 게시판 상세페이지 이동 
	 * 함수 내용 : 비로그인, 로그인, 관리자 구별하여 상세페이지 이동
	 */
	@RequestMapping("qnaContent.do")
	public ModelAndView getQnaBoardContent(QnaVO qnavo, HttpSession session, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		qnavo = qnaService.getQnaBoardContent(qnavo);
		MemberVO membervo = (MemberVO) session.getAttribute("memberVO");

		// 이미지 처리
		FileUpload.makeDirectory(request.getSession().getServletContext().getRealPath("resources/imgs") + "/qnaboard/"
				+ qnavo.getQuestionboardId());
		String directoryPath = request.getSession().getServletContext().getRealPath("resources/imgs") + "/qnaboard/"
				+ Integer.toString(qnavo.getQuestionboardId());
		File dir = new File(directoryPath);
		File fileList[] = dir.listFiles();
		ArrayList<File> fileArrayList = new ArrayList<File>();
		for (File file : fileList) {
			fileArrayList.add(file);
		}
		if (fileArrayList.size() >= 1)
			fileArrayList.remove(0);
		if (fileList.length >= 1) {
			mv.addObject("file", fileList[0]);
		}
		mv.addObject("fileList", fileArrayList);
		mv.addObject("directoryPath", directoryPath);

		if (membervo == null) {
			qnaService.updateReadcount(qnavo);
			mv.setViewName("qnaBoardContent");
			mv.addObject("qnaContent", qnaService.getQnaBoardContent(qnavo));

		} else if (membervo.getMemberFlag().equals("0")) {
			System.out.println("membervo.getMemberFlag():"+membervo.getMemberFlag());
			qnaService.updateReadcount(qnavo);
			mv.setViewName("qnaBoardContent");
			mv.addObject("qnaContent", qnaService.getQnaBoardContent(qnavo));

		} else if (membervo.getMemberFlag().equals("1")) {
			qnaService.updateReadcount(qnavo);
			String admin = ((MemberVO) session.getAttribute("memberVO")).getMemberFlag();
			List<QnaVO> listQna = qnaService.selectListRe(qnavo);
			mv.setViewName("qnaBoardContent");
			mv.addObject("qnaContent", qnaService.getQnaBoardContent(qnavo));
			mv.addObject("admin", admin);
			int groupListSize = listQna.size();
			mv.addObject("groupListSize", groupListSize);
		}

		return mv;
	}

	/*
	 * 함수 이름 : getQnaBoardModify 
	 * 주요 기능 : 게시글 수정페이지 이동 
	 * 함수 내용 : 수정할 게시글 내용 받아와 수정페이지에 연결
	 */
	@RequestMapping(value = "qnaModify.do", produces = "application/text; charset=utf-8")
	public ModelAndView QnaBoardModify(QnaVO qnavo, HttpSession session) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("qnaBoardModify");
		mv.addObject("qnaContent", qnaService.getQnaBoardContent(qnavo));

		return mv;
	}

	/*
	 * 함수 이름 : qnaBoardModify 
	 * 주요 기능 : 게시글 수정 후 목록페이지 이동 
	 * 함수 내용 : 수정내용 있으면 목록이동, 없으면 메인페이지 이동
	 */
	@RequestMapping(value="modify.do")
	public String qnaBoardModify(QnaVO qnavo, HttpSession session) {

		ModelAndView mv = new ModelAndView();
		int result = qnaService.updateQna(qnavo);
		
		if (result != 0) {
			return "redirect:/cs.do";
		}
		return "main.do";
	}

	/*
	 * 함수 이름 : deleteQnaBoard 
	 * 주요 기능 : 게시글 삭제 후 목록페이지 이동 
	 * 함수 내용 : 연결된 계정과 작성글의 아이디가 일치하는 경우만 삭제
	 */
	@RequestMapping(value = "delete.do", produces = "application/text; charset=utf-8")
	public ModelAndView deleteQnaBoard(QnaVO qnavo) {
		qnavo=qnaService.getQnaBoardContent(qnavo);

		ModelAndView mv = new ModelAndView();
		qnaService.deleteQnaBoard(qnavo);
		qnaService.deleteQnaBoardbyGroupId(qnavo);
		mv.setViewName("redirect:/cs.do");

		return mv;
	}

	/*
	 * 함수 이름 : getReplyQnaBoard 
	 * 주요 기능 : 답변하기 버튼 눌렀을 때 답변페이지 이동 
	 * 함수 내용 : 그룹아이디, 작성글 제목 출력
	 */
	@RequestMapping("reply.do")
	public ModelAndView getReplyQnaBoard(QnaVO qnavo) {

		ModelAndView mv = new ModelAndView();
		qnavo = qnaService.selectGroupId(qnavo);
		qnavo.getQuestionboardTitle();
		mv.addObject("qnaReplyContent", qnavo);
		mv.setViewName("qnaReplyBoardWrite");

		return mv;
	}

	/*
	 * 함수 이름 : qnaBoardReplyWrite 
	 * 주요 기능 : 답변 버튼 눌렀을 때 목록페이지 이동 
	 * 함수 내용 : 연결 되어있는 아이디 세팅
	 */
	@RequestMapping("replyWrite.do")
	public ModelAndView insertReply(QnaVO qnavo, HttpSession session) {

		ModelAndView mv = new ModelAndView();
		String reply = "  답변드립니다.";
		qnavo.setQuestionboardTitle(reply);
		qnavo.setMemberId(((MemberVO) session.getAttribute("memberVO")).getMemberId());
		qnaService.insertReply(qnavo);
		mv.setViewName("redirect:/cs.do");

		return mv;
	}

	/*
	 * 함수 이름 : checkId 
	 * 주요 기능 : 아이디 확인 
	 * 함수 내용 : 연결된 아이디와 게시글 아이디 일치, 불일치, 비로그인인 경우로 구분하여 다른 값 리턴
	 */
	@ResponseBody
	@RequestMapping("checkId")
	public String checkId(QnaVO qnavo, HttpSession session) {

		MemberVO membervo = (MemberVO) session.getAttribute("memberVO");
		qnavo = qnaService.getQnaBoardContent(qnavo);
		qnavo.getMemberId();

		String msg = "";
		if (session.getAttribute("memberVO") == null) {
			return "loginRequired";
		} else if (!membervo.getMemberId().equals(qnavo.getMemberId())) {
			return "misMatch";
		} else if (membervo.getMemberId().equals(qnavo.getMemberId())) {
			return "match";
		}
		return msg;
	}

	/*
	 * 함수 이름 : checkLogin 
	 * 주요 기능 : 로그인 확인 
	 * 함수 내용 : 로그인, 비로그인의 경우로 구분하여 다른 값 리턴
	 */
	@ResponseBody
	@RequestMapping("checkLogin")
	public String checkLogin(QnaVO qnavo, HttpSession session) {

		MemberVO membervo = (MemberVO) session.getAttribute("memberVO");
		String msg = "";
		if (session.getAttribute("memberVO") == null) {
			return "loginRequired";
		} else if (session.getAttribute("memberVO") != null) {
			return "write";
		}
		return msg;
	}
	


}

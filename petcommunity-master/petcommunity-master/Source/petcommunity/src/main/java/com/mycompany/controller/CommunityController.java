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

import com.mycompany.domain.BuyListVO;
import com.mycompany.domain.CommentVO;
import com.mycompany.domain.CommunityVO;
import com.mycompany.domain.FindBoardVO;
import com.mycompany.domain.LostBoardVO;
import com.mycompany.domain.MemberVO;
import com.mycompany.domain.PaginationVO;
import com.mycompany.service.CommunityService;
import com.mycompany.util.FileUpload;

@Controller
public class CommunityController {

	@Autowired
	private CommunityService communityService;

	

	/*
	 * 함수 이름 : getCommunityBoardList 주요 기능 : 게시판 목록보기 페이지로 넘겨준다 함수 내용 : 조회된 게시판 목록
	 * 가져온 후 페이징처리, 댓글 유무 확인, 사진 유무 확인
	 */
	@RequestMapping("/communityBoardList.do")
	public ModelAndView getCommunityBoardList(CommunityVO vo, ModelAndView mv, HttpServletRequest request) {
		// db의 모든 데이터를 가져옴
		List<CommunityVO> communityBoardList = communityService.getBoardList();// 게시판 리스트 가져오기

		// 20개씩 보여줄때 페이지 개수 계산
		int amountOfPage = (communityBoardList.size() - 1) / 10 + 1; // 페이지의 개수(20개씩 보여주므로 20으로 나눈후 1을 더해줌)
		int showPage = (amountOfPage - 1) / 5 + 1; // page개수 구하기

		int pageNo = 1;
		if (request.getParameter("pageNo") != null) {// pageNo가 파라미터로 넘어올때
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		} else {
			pageNo = 1;
		}
		if (pageNo > amountOfPage) {
			pageNo = amountOfPage;
		}

		int showPageNo = (pageNo - 1) / 5;
		int showPageStart = showPageNo * 5 + 1; // page인덱스가 시작하는 위치
		int showPageLast = 0;
		if ((showPageNo * 5 + 5) > amountOfPage) {// page인덱스가 마지막 인덱스를 포함한다면
			showPageLast = showPageNo * 5 + amountOfPage % 5;
		} else {
			showPageLast = showPageNo * 5 + 5; // page인덱스가 끝나는 위치
		}

		int startList = 10 * (pageNo - 1) + 1;// 게시판 해당 페이지 시작글
		int lastList = startList + 9; // 게시판 해당 페이지 마지막글
		if (startList - 1 == communityBoardList.size() / 10) {// 마지막 페이지일때 어디까지 불러와야 하는지 정해줌
			lastList = communityBoardList.size();
		}

		vo.setStartList(startList);
		vo.setLastList(lastList);

		List<CommunityVO> communityBoardListByPaging = communityService.communityBoardListByPaging(vo);
		// 사진 유무 확인
		ArrayList<String> checkImg = new ArrayList<String>();
		for (int i = 0; i < communityBoardList.size(); i++) {
			String directoryPath = request.getSession().getServletContext().getRealPath("resources/imgs")
					+ "/communityboard/" + communityBoardList.get(i).getCommunityboardId();
			File dir = new File(directoryPath);
			File fileList[] = dir.listFiles();

			if (fileList != null) {
				if (fileList.length != 0) {
					checkImg.add(communityBoardList.get(i).getCommunityboardId());
				}
			}
		}
		mv.addObject("checkImg", checkImg); // 이미지 있는 컨텐츠번호를 리스트에 담아서 mv에 저장

		mv.addObject("showPageStart", showPageStart);
		mv.addObject("showPageLast", showPageLast);
		mv.addObject("amountOfPage", amountOfPage);
		mv.addObject("communityBoardList", communityBoardListByPaging); // 게시판 리스트 저장
		mv.addObject("boardList", "value");
		mv.setViewName("communityBoardList");
		return mv;
	}

	/*
	 * 함수 이름 : getCommunityBoardwrite 주요 기능 : 게시판 쓰기 페이지로 넘겨준다 함수 내용 : 조회된 게시판 목록
	 * 가져온 후 페이징처리, 댓글 유무 확인, 사진 유무 확인
	 */
	@RequestMapping("/communityBoardWrite.do")
	public String getCommunityBoardwrite() {
		return "communityBoardWrite";
	}

	/*
	 * 함수 이름 : writeIntoBoard 주요 기능 : 쓰기 완료 후 게시판 리스트 페이지로 이동 함수 내용 : 입력된 내용 받아와서
	 * db저장, 이미지가 있다면 업로드
	 */
	@RequestMapping(value = "/writeIntoBoard.do", method = RequestMethod.POST, produces = "application/text; charset=utf-8")
	public String writeIntoBoard(CommunityVO vo, HttpServletRequest request, HttpSession session,
			MultipartHttpServletRequest mtfRequest) throws IOException {
		MemberVO mvo = (MemberVO) session.getAttribute("memberVO");
		vo.setMemberId(mvo.getMemberId());
		communityService.writeIntoBoard(vo);

		FileUpload.makeDirectory(
				request.getSession().getServletContext().getRealPath("resources/imgs") + "/communityboard/");
		FileUpload.uploadFiles(mtfRequest, request.getSession().getServletContext().getRealPath("resources/imgs")
				+ "/communityboard/" + vo.getCommunityboardId() + "/");
		return "redirect:communityBoardList.do";
	}

	/*
	 * 함수 이름 : modifyBoardContent 주요 기능 : 수정 완료 후 글내용으로 되돌아가기 함수 내용 :
	 */
	@RequestMapping("/modifyBoardContent.do")
	public ModelAndView modifyBoardContent(CommentVO cvo, CommunityVO vo, HttpServletRequest request,
			HttpSession session, ModelAndView mv) {
		communityService.modifyBoardContent(vo);
		// 수정 완료후 글 내용으로 되돌아가기
		mv.addObject("boardContent", communityService.getBoardContent(vo));
		mv.addObject("boardComment", communityService.getCommentContent(cvo));
		mv.setViewName("communityBoardContent");
		return mv;
	}

	/*
	 * 함수 이름 : getBoardListBySearch 주요 기능 : 검색된 게시판 list 가져오기 함수 내용 : 조회된 게시판 목록 가져온
	 * 후 페이징처리, 댓글 유무 확인, 사진 유무 확인
	 */
	@ResponseBody
	@RequestMapping("/getBoardListBySearch.do")
	public Map getBoardListBySearch(@RequestParam(defaultValue = "1") int curPage, CommunityVO vo,
			HttpServletRequest request, ModelAndView mv) {
		// parameter로 넘어온 글번호를 vo에 셋해준후 Mapper로 넘겨줌
		String searchType = request.getParameter("searchType");
		String keyword = request.getParameter("searchWord");

		String type = "";
		Map searchMap = new HashMap();
		// 검색타입에 따라 vo에 셋팅을 다르게 해줌
		if (searchType.equals("제목")) {
			type = "communityboard_title";
			vo.setSearchType(type);
			searchMap.put("searchType", "communityboard_title");
		} else if (searchType.equals("내용")) {
			type = "communityboard_content";
			vo.setSearchType(type);
			searchMap.put("searchType", "communityboard_content");
		} else if (searchType.equals("작성자")) {
			type = "member_id";
			vo.setSearchType(type);
			searchMap.put("searchType", "member_id");
		}

		vo.setKeyWord(keyword);

		PaginationVO paginationVO = new PaginationVO(communityService.getBoardListBySearch(vo).size(), curPage, 10);

		paginationVO.setRangeSize(10);
		searchMap.put("startRow", paginationVO.getStartIndex() + 1);
		searchMap.put("endRow", paginationVO.getStartIndex() + paginationVO.getPageSize());
		searchMap.put("keyWord", keyword);
		List<CommunityVO> communityBoardListBySearch = communityService.getBoardListBySearchWithPaging(searchMap);
		Map result = new HashMap();

		result.put("pagination", paginationVO);
		result.put("communityBoardListBySearch", communityBoardListBySearch);
		// 사진 유무 확인
		ArrayList<String> checkImg = new ArrayList<String>();
		for (int i = 0; i < communityBoardListBySearch.size(); i++) {
			String directoryPath = request.getSession().getServletContext().getRealPath("resources/imgs")
					+ "/communityboard/" + communityBoardListBySearch.get(i).getCommunityboardId();
			File dir = new File(directoryPath);
			File fileList[] = dir.listFiles();
			if (fileList != null) {
				if (fileList.length != 0) {
					checkImg.add(communityBoardListBySearch.get(i).getCommunityboardId());
				}

			}

		}
		result.put("checkImg", checkImg);

		return result;
	}

	// 카테고리에 선택된 게시판 내용 가져오기
	/*
	 * 함수 이름 : getBoardListByCategory 주요 기능 : 카테고리에 선택된 게시판 내용 가져오기 함수 내용 : 조회된 게시판
	 * 목록 가져온 후 페이징처리, 댓글 유무 확인, 사진 유무 확인
	 */
	@ResponseBody
	@RequestMapping("/getBoardListByCategory.do")
	public Map getBoardListByCategory(@RequestParam(defaultValue = "1") int curPage, CommunityVO vo,
			HttpServletRequest request, ModelAndView mv) {
		String category = request.getParameter("category");
		if (category.equals("지역별")) {

			String cityName = request.getParameter("cityName");
			String province = request.getParameter("province");

			vo.setCityName(cityName);
			vo.setProvince(province.substring(0, 2));// 문자열 짤라서 '종로'처럼 두글자로 만들어줌

			List<CommunityVO> getBoardListByLocation = communityService.getBoardListByLocation(vo);
			Map categoryMap = new HashMap();
			PaginationVO paginationVO = new PaginationVO(communityService.getBoardListByLocation(vo).size(), curPage,
					10);

			paginationVO.setRangeSize(10);
			categoryMap.put("startRow", paginationVO.getStartIndex() + 1);

			categoryMap.put("endRow", paginationVO.getStartIndex() + paginationVO.getPageSize());
			categoryMap.put("cityName", cityName);

			categoryMap.put("province", province.substring(0, 2));
			List<CommunityVO> getBoardListByLocationWithPaging = communityService
					.getBoardListByLocationWithPaging(categoryMap);

			Map result = new HashMap();

			result.put("pagination", paginationVO);
			result.put("communityBoardListBySearch", getBoardListByLocationWithPaging);
			// 사진 유무 확인
			ArrayList<String> checkImg = new ArrayList<String>();
			for (int i = 0; i < getBoardListByLocationWithPaging.size(); i++) {
				String directoryPath = request.getSession().getServletContext().getRealPath("resources/imgs")
						+ "/communityboard/" + getBoardListByLocationWithPaging.get(i).getCommunityboardId();
				File dir = new File(directoryPath);
				File fileList[] = dir.listFiles();
				if (fileList != null) {

					if (fileList.length != 0) {
						checkImg.add(getBoardListByLocationWithPaging.get(i).getCommunityboardId());
					}
				}
			}
			result.put("checkImg", checkImg);

			return result;
		} else if (category.equals("조회순")) {

			List<CommunityVO> getBoardListByReadCount = communityService.getBoardListByReadCount();
			Map categoryMap = new HashMap();
			PaginationVO paginationVO = new PaginationVO(communityService.getBoardListByReadCount().size(), curPage,
					10);
			paginationVO.setRangeSize(10);
			categoryMap.put("startRow", paginationVO.getStartIndex() + 1);
			categoryMap.put("endRow", paginationVO.getStartIndex() + paginationVO.getPageSize());
			List<CommunityVO> getBoardListByReadCountWithPaging = communityService
					.getBoardListByReadCountWithPaging(categoryMap);
			Map result = new HashMap();
			result.put("pagination", paginationVO);
			result.put("communityBoardListBySearch", getBoardListByReadCountWithPaging);

			// 사진 유무 확인
			ArrayList<String> checkImg = new ArrayList<String>();
			for (int i = 0; i < getBoardListByReadCountWithPaging.size(); i++) {
				String directoryPath = request.getSession().getServletContext().getRealPath("resources/imgs")
						+ "/communityboard/" + getBoardListByReadCountWithPaging.get(i).getCommunityboardId();
				File dir = new File(directoryPath);
				File fileList[] = dir.listFiles();
				if (fileList != null) {
					if (fileList.length != 0) {
						checkImg.add(getBoardListByReadCountWithPaging.get(i).getCommunityboardId());
					}

				}

			}
			result.put("checkImg", checkImg);
			return result;
		} else if (category.equals("추천순")) {

			List<CommunityVO> getBoardListByRecommend = communityService.getBoardListByRecommend();
			Map categoryMap = new HashMap();
			PaginationVO paginationVO = new PaginationVO(communityService.getBoardListByRecommend().size(), curPage,
					10);
			paginationVO.setRangeSize(10);
			categoryMap.put("startRow", paginationVO.getStartIndex() + 1);
			categoryMap.put("endRow", paginationVO.getStartIndex() + paginationVO.getPageSize());
			List<CommunityVO> getBoardListByRecommendWithPaging = communityService
					.getBoardListByRecommendWithPaging(categoryMap);
			Map result = new HashMap();
			result.put("pagination", paginationVO);
			result.put("communityBoardListBySearch", getBoardListByRecommendWithPaging);

			// 사진 유무 확인
			ArrayList<String> checkImg = new ArrayList<String>();
			for (int i = 0; i < getBoardListByRecommendWithPaging.size(); i++) {
				String directoryPath = request.getSession().getServletContext().getRealPath("resources/imgs")
						+ "/communityboard/" + getBoardListByRecommendWithPaging.get(i).getCommunityboardId();
				File dir = new File(directoryPath);
				File fileList[] = dir.listFiles();
				if (fileList != null) {
					if (fileList.length != 0) {
						checkImg.add(getBoardListByRecommendWithPaging.get(i).getCommunityboardId());
					}

				}
			}
			result.put("checkImg", checkImg);

			return result;
		} else {
			List<CommunityVO> getBoardListByReadCount = communityService.getBoardListByReadCount();
			Map categoryMap = new HashMap();
			PaginationVO paginationVO = new PaginationVO(communityService.getBoardListByReadCount().size(), curPage,
					10);
			paginationVO.setRangeSize(10);
			categoryMap.put("startRow", paginationVO.getStartIndex() + 1);
			categoryMap.put("endRow", paginationVO.getStartIndex() + paginationVO.getPageSize());
			List<CommunityVO> getBoardListByReadCountWithPaging = communityService
					.getBoardListByReadCountWithPaging(categoryMap);
			Map result = new HashMap();
			result.put("pagination", paginationVO);
			result.put("communityBoardListBySearch", getBoardListByReadCountWithPaging);
			// 사진 유무 확인
			ArrayList<String> checkImg = new ArrayList<String>();
			for (int i = 0; i < getBoardListByReadCountWithPaging.size(); i++) {
				String directoryPath = request.getSession().getServletContext().getRealPath("resources/imgs")
						+ "/communityboard/" + getBoardListByReadCountWithPaging.get(i).getCommunityboardId();
				File dir = new File(directoryPath);
				File fileList[] = dir.listFiles();
				if (fileList != null) {
					if (fileList.length != 0) {
						checkImg.add(getBoardListByReadCountWithPaging.get(i).getCommunityboardId());
					}

				}
			}
			result.put("checkImg", checkImg);

			return result;
		}
	}

	/*
	 * 함수 이름 : checkSession 주요 기능 : 로그인 유무 체크후 값 리턴 함수 내용 : ajax통신으로 logout이라는 문자열
	 * 반환
	 */
	@ResponseBody
	@RequestMapping("/checkSession.do")
	public String checkSession(HttpSession session, ModelAndView mv) {
		String msg = "";
		if (session.getAttribute("memberVO") == null) {
			msg = "logout";
		}
		return msg;
	}

	/*
	 * 함수 이름 : dislikeContent 주요 기능 : 게시글 추천 취소 함수 내용 :
	 */
	@ResponseBody
	@RequestMapping("/dislikeContent.do")
	public String dislikeContent(HttpServletRequest request, CommunityVO vo) {
		String communityboardId = request.getParameter("communityboardId");

		vo.setCommunityboardId(communityboardId);
		communityService.dislikeContent(vo);
		return "";
	}

	/*
	 * 함수 이름 : likeContent 주요 기능 : 추천클릭시 게시글 추천 함수 내용 :
	 */
	@ResponseBody
	@RequestMapping("/likeContent.do")
	public String likeContent(HttpServletRequest request, CommunityVO vo) {
		String communityboardId = request.getParameter("communityboardId");

		vo.setCommunityboardId(communityboardId);
		communityService.likeContent(vo);
		return "";
	}

	/*
	 * 함수 이름 : getBoardContent 주요 기능 : 게시판 글내용 가져오기 함수 내용 : 리스트에서 글 클릭시 해당 글내용 페이지
	 * 이동 사진 유무 체크 후 이미지 전달
	 */
	@RequestMapping("/getBoardContent.do")
	public ModelAndView getBoardContent(CommunityVO vo, CommentVO cvo, HttpServletRequest request, ModelAndView mv) {
		// parameter로 넘어온 글번호를 vo에 셋해준후 Mapper로 넘겨줌
		String communityboardId = request.getParameter("communityboardId");
		vo.setCommunityboardId(communityboardId);// 커뮤니티보드에 셋팅
		cvo.setCommunityboardId(communityboardId);// 커멘트에 셋팅
		CommunityVO content = communityService.getBoardContent(vo);//게시글 검색
		content.setCommunityboardContent(content.getCommunityboardContent().replaceAll("\n", "<br/>"));//개행문자 br태그로 변경해주기
		mv.addObject("boardContent", content);// 해당 글 정보 가져오기
		mv.addObject("boardComment", communityService.getCommentContent(cvo));// 해당글에 관련된 커멘트 가져오기
		communityService.addReadCount(vo); // 해당 글 조회수 올리기
		// 그림파일이 있으면 가져옴
		String directoryPath = request.getSession().getServletContext().getRealPath("resources/imgs")
				+ "/communityboard/" + vo.getCommunityboardId();

		File dir = new File(directoryPath);
		File fileList[] = dir.listFiles();
		ArrayList<File> fileArrayList = new ArrayList<File>();
		if (fileList != null) {
			for (File file : fileList) {
				fileArrayList.add(file);
			}
		}
		mv.addObject("boardContentImg", fileArrayList);
		mv.setViewName("communityBoardContent");
		return mv;
	}

	/*
	 * 함수 이름 : writeComment 주요 기능 : 커멘트 달기 함수 내용 :
	 */
	@RequestMapping("/writeComment.do")
	public ModelAndView writeComment(CommunityVO vo, CommentVO cvo, HttpServletRequest request, HttpSession session,
			ModelAndView mv) {
		String communityboardId = request.getParameter("communityboardId");

		MemberVO mvo = (MemberVO) session.getAttribute("memberVO");
		cvo.setCommunityboardId(communityboardId);
		cvo.setMemberId(mvo.getMemberId());
		communityService.writeComment(cvo);// 커멘트 달기 완료 후

		// 기존의 글로 다시 돌아감
		String directoryPath = request.getSession().getServletContext().getRealPath("resources/imgs")
				+ "/communityboard/" + vo.getCommunityboardId();

		File dir = new File(directoryPath);
		File fileList[] = dir.listFiles();
		ArrayList<File> fileArrayList = new ArrayList<File>();
		if (fileList != null) {
			for (File file : fileList) {
				fileArrayList.add(file);
			}
		}
		mv.addObject("boardContentImg", fileArrayList);
		vo.setCommunityboardId(communityboardId);
		mv.addObject("boardContent", communityService.getBoardContent(vo));
		mv.addObject("boardComment", communityService.getCommentContent(cvo));
		mv.setViewName("communityBoardContent");
		return mv;
	}

	/*
	 * 함수 이름 : communityBoardDelete 주요 기능 : 게시물 지우기 함수 내용 : 게시글 삭제시 관련 댓글 모두 삭제
	 */
	@RequestMapping("/communityBoardDelete.do")
	public String communityBoardDelete(CommunityVO vo, CommentVO cvo, HttpServletRequest request) {
		String communityboardId = request.getParameter("communityboardId");
		vo.setCommunityboardId(communityboardId);
		cvo.setCommunityboardId(communityboardId);

		// 게시글을 지우면서 관련된 댓글과 사진까지 삭제 해야함
		FileUpload.deleteDirectory(request.getSession().getServletContext().getRealPath("resources/imgs")
				+ "/communityboard/" + vo.getCommunityboardId());
		communityService.deleteOnCommunityBoard(vo);
		communityService.deleteBoardComment(cvo);
		return "redirect:communityBoardList.do";
	}

	/*
	 * 함수 이름 : communityBoardModifyPage 주요 기능 : 게시물 내용 수정하기페이지로 넘김 함수 내용 :
	 */
	@RequestMapping("/communityBoardModifyPage.do")
	public ModelAndView communityBoardModify(CommunityVO vo, HttpServletRequest request, ModelAndView mv) {
		String communityboardId = request.getParameter("communityboardId");
		vo.setCommunityboardId(communityboardId);// 커뮤니티보드에 셋팅
		mv.addObject("boardContent", communityService.getBoardContent(vo));// 해당 글 정보 가져오기
		mv.setViewName("communityBoardModify");
		return mv;
	}

	// 커멘트 삭제하기
	/*
	 * 함수 이름 : commentDelete 주요 기능 : 커멘트 삭제하기 함수 내용 :
	 */
	@RequestMapping("/commentDelete.do")
	public ModelAndView commentDelete(CommentVO cvo, CommunityVO vo, HttpServletRequest request, ModelAndView mv) {
		String boardcommentId = request.getParameter("boardcommentId");
		cvo.setBoardcommentId(boardcommentId);// 커뮤니티보드에 셋팅
		communityService.commentDelete(cvo);

		// 댓글 삭제 후 기존글로 돌아오기
		String communityboardId = request.getParameter("communityboardId");
		cvo.setCommunityboardId(communityboardId);
		vo.setCommunityboardId(communityboardId);
		mv.addObject("boardContent", communityService.getBoardContent(vo));
		mv.addObject("boardComment", communityService.getCommentContent(cvo));
		mv.setViewName("communityBoardContent");
		return mv;

	}
	
	// 커뮤니티 플로팅 배너 (우측)
		/*
		 * 함수 이름 : getBoardRank 
		 * 함수 주요 기능 : ajax와 연결되어있음. 커뮤니티 조회수 순으로 정렬하여 List배열 형태로 받아서 넘겨준다.(페이지에서는 조회수 상위 10위까지 보여짐.)
		 */
	@ResponseBody
	@RequestMapping(value="getBoardrank.do" , produces = "application/json; charset=utf-8")
	public Map getBoardRank() {

		Map result = new HashMap();
		
		List<CommunityVO> list2 =communityService.getBoardRank();
		for(CommunityVO i : list2) {
				
		}
		result.put("buyList", list2);

		return result;

	}
	
	
	// 커뮤니티 배너 (우측 실종탭)
			/*
			 * 함수 이름 : getLostRank 
			 * 함수 주요 기능 : ajax와 연결되어있음. 커뮤니티 조회수 순으로 정렬하여 List배열 형태로 받아서 넘겨준다.(페이지에서는 조회수 상위 10위까지 보여짐.)
			 */
		@ResponseBody
		@RequestMapping(value="getLostrank.do" , produces = "application/json; charset=utf-8")
		public Map getLostRank() {

			Map result = new HashMap();
			
			List<LostBoardVO> list2 =communityService.getLostRank();

			result.put("LostList", list2);

			return result;

		}
		
		
		// 커뮤니티 배너 (우측 목격탭)
		/*
		 * 함수 이름 : getFindRank 
		 * 함수 주요 기능 : ajax와 연결되어있음. 커뮤니티 조회수 순으로 정렬하여 List배열 형태로 받아서 넘겨준다.(페이지에서는 조회수 상위 10위까지 보여짐.)
		 */
	@ResponseBody
	@RequestMapping(value="getFindrank.do" , produces = "application/json; charset=utf-8")
	public Map getFindRank() {

		Map result = new HashMap();
		
		List<FindBoardVO> list2 =communityService.getFindRank();

		result.put("FindList", list2);

		return result;

	}

}

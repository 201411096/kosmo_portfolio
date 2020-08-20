package com.mycompany.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.domain.BuylistviewVO;
import com.mycompany.domain.ManagerVO;
import com.mycompany.domain.MemberVO;
import com.mycompany.domain.QnaVO;
import com.mycompany.service.AdminService;
import com.mycompany.service.QnaService;
import com.nexacro17.xapi.data.DataSet;
import com.nexacro17.xapi.data.DataTypes;

@Controller
public class AdminController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private QnaService qnaService;

	/*
	 * 함수 이름 : selectList 
	 * 주요 기능 : 판매내역 목록 출력
	 * 함수 내용 : BuylistDate에서 판매 시간 제거, 판매날짜만 받아와서 출력
	 */
	@RequestMapping("/adminPage.do")
	public ModelAndView selectList(BuylistviewVO buylistviewvo, String startDate, String endDate) {
		
		ModelAndView mv = new ModelAndView();
		List<BuylistviewVO> salesList = adminService.adminselectList(buylistviewvo);
	
		for (int i = 0; i < salesList.size(); i++) {
			salesList.get(i).setBuylistDate(salesList.get(i).getBuylistDate().substring(0, 10));			
		}
		
		mv.addObject("salesList", salesList);

		return mv;
	}

	/*
	 * 함수 이름 : selectDate
	 * 주요 기능 : 날짜 입력하여 선택한 날짜범위만 출력 
	 * 함수 내용 : BuylistDate에서 판매 시간 제거, 판매날짜만 받아와서 출력
	 */
	@RequestMapping("/datesearch.do")
	public ModelAndView selectDate(String startDate, String endDate) {
		
		ModelAndView mv = new ModelAndView();
		Map map = new HashMap();
		map.put("startDate", startDate);
		map.put("endDate", endDate);

		if (startDate == "" || startDate == null) {
			SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
			Date time = new Date();
			time.setMonth(3);
			String strTime = format.format(time);
			startDate = strTime.substring(0, 6);
		}
		if (endDate == "" || endDate == null) {
			SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
			Date time = new Date();
			String strTime = format.format(time);
			endDate = strTime.substring(0, 6);
		}

		List<BuylistviewVO> salesList = adminService.getSearchDate(map);
		for (int i = 0; i < salesList.size(); i++) {
			salesList.get(i).setBuylistDate(salesList.get(i).getBuylistDate().substring(0, 10));
		}

		mv.addObject("salesList", salesList);
		mv.setViewName("adminPage");

		return mv;
	}

	/*
	 * 함수 이름 : defaultMain
	 * 주요 기능 : 회원 목록 출력
	 * 함수 내용 : 
	 */
	@RequestMapping("/selectInfo.do")
	public ModelAndView defaultMain(MemberVO memberevo) {
		ModelAndView mv = new ModelAndView();
		List<MemberVO> list = adminService.getMemberList(memberevo);

		DataSet ds = new DataSet("ar");
		ds.addColumn("memberId", DataTypes.STRING, 100);
		ds.addColumn("memberName", DataTypes.STRING, 100);
		ds.addColumn("memberAddress", DataTypes.STRING, 100);
		ds.addColumn("memberTel", DataTypes.STRING, 100);
		ds.addColumn("memberEmail", DataTypes.STRING, 100);

		for (MemberVO vo : list) {
			int row = ds.newRow();
			ds.set(row, "memberId", vo.getMemberId());
			ds.set(row, "memberName", vo.getMemberName());
			ds.set(row, "memberAddress", vo.getMemberAddress());
			ds.set(row, "memberTel", vo.getMemberTel());
			ds.set(row, "memberEmail", vo.getMemberEmail());
		}
		mv.setViewName("all");
		mv.addObject("ds", ds);
		return mv;
	}

	/*
	 * 함수 이름 : getMemberSelect
	 * 주요 기능 : 검색조건에 맞는 회원정보 출력
	 * 함수 내용 : combobox, searchword에 입력받은 값 해쉬맵으로 받아 매퍼 연결
	 */
	
	@RequestMapping("/searchInfo.do")
	public ModelAndView getMemberSelect(String combobox, String searchword) throws UnsupportedEncodingException {
		ModelAndView mv = new ModelAndView();
		Map map = new HashMap();
		combobox = URLDecoder.decode(combobox, "utf-8");
		searchword = URLDecoder.decode(searchword, "utf-8");
		map.put("combobox", combobox);
		map.put("searchword", searchword);
		MemberVO membervo = new MemberVO();
		if (combobox.equals("0")) {
			membervo.setMemberId(searchword);
		} else if (combobox.equals("1")) {
			membervo.setMemberName(searchword);
		} else if (combobox.equals("2")) {
			membervo.setMemberAddress(searchword);
		}

		List<MemberVO> list = adminService.getMemberSelect(map);
		DataSet ds = new DataSet("ar");
		ds.addColumn("memberId", DataTypes.STRING, 100);
		ds.addColumn("memberName", DataTypes.STRING, 100);
		ds.addColumn("memberAddress", DataTypes.STRING, 100);
		ds.addColumn("memberEmail", DataTypes.STRING, 100);
		ds.addColumn("memberTel", DataTypes.STRING, 100);

		for (MemberVO vo : list) {
			int row = ds.newRow();
			ds.set(row, "memberId", vo.getMemberId());
			ds.set(row, "memberName", vo.getMemberName());
			ds.set(row, "memberAddress", vo.getMemberAddress());
			ds.set(row, "memberEmail", vo.getMemberEmail());
			ds.set(row, "memberTel", vo.getMemberTel());
		}
		mv.setViewName("all");
		mv.addObject("ds", ds);
		return mv;
	}

	/*
	 * 함수 이름 : deleteInfo
	 * 주요 기능 : 회원 삭제
	 * 함수 내용 : 삭제할 memberId 받아온 후 일치하는 정보 삭제, groupId 받아와서 qna게시판에 연결된 답변글 삭제 
	 */
	@RequestMapping("/deleteInfo.do")
	public void deleteInfo(String memberId, HttpServletRequest request) {
		QnaVO qnavo = new QnaVO();
		qnavo.setMemberId(memberId);
		
		List<QnaVO> qnavoList = qnaService.selectQuestionGroupId(qnavo);
		for (int i = 0; qnavoList.size() > i; i++) {
			qnaService.deleteQnaBoardbyGroupId(qnavoList.get(i));
		}
		
		adminService.deleteInfo(memberId);

	}

	/*
	 * 함수 이름 : managerSelect
	 * 주요 기능 : 
	 * 함수 내용 :
	 */
	@RequestMapping(value = "/managerselect.do")
	public ModelAndView managerSelect() {
		System.out.println("조회버튼");
		List<ManagerVO> list = adminService.managerSelect();

		DataSet ds = new DataSet("ar");
		ds.addColumn("managerId", DataTypes.STRING, 100);
		ds.addColumn("managerDept", DataTypes.STRING, 100);
		ds.addColumn("managerHireDate", DataTypes.STRING, 100);
		ds.addColumn("managerName", DataTypes.STRING, 100);
		ds.addColumn("managerTel", DataTypes.STRING, 100);

		for (ManagerVO vo : list) {
			int row = ds.newRow();
			ds.set(row, "managerId", vo.getMemberId());
			ds.set(row, "managerDept", vo.getManagerDept());
			ds.set(row, "managerHireDate", vo.getManagerHiredate());
			ds.set(row, "managerName", vo.getMemberName());
			ds.set(row, "managerTel", vo.getMemberTel());
		}

		ModelAndView mv = new ModelAndView();
		mv.addObject("ds", ds);
		mv.setViewName("all");
		return mv;
	}

	/*
	 * 함수 이름 : managerInsert
	 * 주요 기능 : 
	 * 함수 내용 :
	 */
	@RequestMapping(value = "/managerInsert.do")
	public ModelAndView managerInsert(String id, String dept, String hireDate, HttpServletRequest request) {
		
			HashMap map = new HashMap();
			map.put("memberId", id);
			map.put("dept", dept);
			map.put("hiredate", hireDate);

			List<ManagerVO> result = adminService.checkId(id);		
			System.out.println("결과"+result);
			if (result.size() == 0 ) {
				adminService.managerInsert(map);

			} else{
				adminService.managerupdate(map);
			}
		

			List<ManagerVO> list = adminService.managerSelect();
			// ---------------------------------------- //DB에서 받아온 값을 넥사크로로 바인딩할 수 있도록 처리
			DataSet ds = new DataSet("ar");
			ds.addColumn("managerId", DataTypes.STRING, 100);
			ds.addColumn("managerDept", DataTypes.STRING, 100);
			ds.addColumn("managerHireDate", DataTypes.STRING, 100);
			ds.addColumn("managerName", DataTypes.STRING, 100);
			ds.addColumn("managerTel", DataTypes.STRING, 100);

			for (ManagerVO vo : list) {

				int row = ds.newRow();
				ds.set(row, "managerId", vo.getMemberId());
				ds.set(row, "managerDept", vo.getManagerDept());
				ds.set(row, "managerHireDate", vo.getManagerHiredate());
				ds.set(row, "managerName", vo.getMemberName());
				ds.set(row, "managerTel", vo.getMemberTel());
			}

			ModelAndView mv = new ModelAndView();
			mv.addObject("ds", ds);
			mv.setViewName("all");
			return mv;

		
	}

	/*
	 * 함수 이름 : managerdelete
	 * 주요 기능 : 
	 * 함수 내용 :
	 */
	@RequestMapping(value = "/managerdelete.do")
	public ModelAndView managerdelete(String id) {

		try {
			 adminService.managerDelete(id);

		} finally {

			List<ManagerVO> list = adminService.managerSelect();
			// ---------------------------------------- //DB에서 받아온 값을 넥사크로로 바인딩할 수 있도록 처리
			DataSet ds = new DataSet("ar");
			ds.addColumn("managerId", DataTypes.STRING, 100);
			ds.addColumn("managerDept", DataTypes.STRING, 100);
			ds.addColumn("managerHireDate", DataTypes.STRING, 100);
			ds.addColumn("managerName", DataTypes.STRING, 100);
			ds.addColumn("managerTel", DataTypes.STRING, 100);

			for (ManagerVO vo : list) {

				int row = ds.newRow();
				ds.set(row, "managerId", vo.getMemberId());
				ds.set(row, "managerDept", vo.getManagerDept());
				ds.set(row, "managerHireDate", vo.getManagerHiredate());
				ds.set(row, "managerName", vo.getMemberName());
				ds.set(row, "managerTel", vo.getMemberTel());
			}

			ModelAndView mv = new ModelAndView();
			mv.addObject("ds", ds);
			mv.setViewName("all");
			return mv;

		}

	}
}

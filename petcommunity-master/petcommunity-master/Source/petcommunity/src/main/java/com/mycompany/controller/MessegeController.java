package com.mycompany.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.domain.MemberVO;
import com.mycompany.domain.MessageVO;
import com.mycompany.service.MemberServiceImpl;
import com.mycompany.service.MessageServiceImpl;

@Controller
public class MessegeController {
	
	@Autowired
	public MessageServiceImpl messageService; 
	@Autowired
	public MemberServiceImpl memberService;
	//메시지 insert
	@ResponseBody
	@RequestMapping("/sendMessage.do")
	public Map message(HttpSession session, int startPage, int endPage, String content, String otherId) {
		
		Map result = new HashMap();
		Map searchMap = new HashMap();
		MemberVO mvo = (MemberVO)session.getAttribute("memberVO");
		String id = mvo.getMemberId();
		System.out.println("otherId"+otherId);
		searchMap.put("id", id);
		searchMap.put("startPage", startPage);
		searchMap.put("endPage", endPage);
		searchMap.put("otherId", otherId);
		searchMap.put("content", content);
		messageService.insertMessage(searchMap);
		List<MessageVO> messageVO = messageService.getMessagePartner2(searchMap);
		System.out.println(messageVO);
		result.put("messageVO", messageVO);
		result.put("messageVOSize", messageVO.size());
//		MessageVO messageVO = messageService.addMessage(searchMap);
//		String messageSender=messageVO.getMessageSender();
//		String messageSendtime = messageVO.getMessageSendtime();
//		String messageContents = messageVO.getMessageContents();
//		String messageId = messageVO.getMessageId();
//		result.put("messageSender", messageSender);
//		result.put("messageSendtime", messageSendtime);
//		result.put("messageContents", messageContents);
//		result.put("messageId", messageId);
		result.put("loginId", id);
		return result;
	}

//	// 새로 쪽지 전송
//	@ResponseBody
//	@RequestMapping("/writeNewMessage.do")
//	public Map writeNewMessage(HttpSession session, int startPage, int endPage, String searchNew) {
//		Map result = new HashMap();
//		Map searchMap = new HashMap();
//		MemberVO mvo = (MemberVO)session.getAttribute("memberVO");
//		String id = mvo.getMemberId();
//		searchMap.put("id", id);
//		searchMap.put("searchNew", searchNew);
//		searchMap.put("startPage", startPage);
//		searchMap.put("endPage", endPage);		
//		System.out.println(id);
//		System.out.println(searchNew);
//		System.out.println(startPage);
//		System.out.println(endPage);
//		List<MemberVO> memberList = memberService.getMemberList(searchMap);
//		if(memberList==null) {
//			result.put("memberListSize", 0);
//		}
//		result.put("memberList", memberList);
//		result.put("memberListSize", memberList.size());
//		return result;
//	}
	// 검색 상대 불러오기(링크전용)
	@RequestMapping("/getChatPartner.do")
	public ModelAndView getChatPartner(HttpSession session, String messageFrom) {
		System.out.println("컨트롤 :"+messageFrom);
		ModelAndView mv = new ModelAndView();
		Map searchMap = new HashMap();
		MemberVO mvo = (MemberVO)session.getAttribute("memberVO");
		String id = mvo.getMemberId();
		searchMap.put("id", id);
		searchMap.put("searchNew", messageFrom);
		searchMap.put("startPage", 0);
		searchMap.put("endPage", 1);
		List<MemberVO> memberList = memberService.getMemberList(searchMap);
		for(MemberVO i: memberList) {
			i.setMemberAddress(i.getMemberAddress().substring(0,2));
		}
		mv.addObject("memberList", memberList);
		mv.setViewName("message");
		return mv;
	}
	// 대화 상대 찾아오기(링크전용)
	@RequestMapping("/getMessagePartner.do")
	public ModelAndView getMessagePartner(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		Map searchMap = new HashMap();
		MemberVO mvo = (MemberVO)session.getAttribute("memberVO");
		String id = mvo.getMemberId();
		searchMap.put("id", id);
		List<MessageVO> messageList = messageService.getMessagePartner(searchMap);
		// set을 통해 중복값 제거
		Set<String> messagePartnerList = new HashSet<String>();
		for(MessageVO i: messageList) {
		String target1 = i.getMessageTarget1();
		String target2 = i.getMessageTarget2();
		String messageContents = i.getMessageContents();
//		String messageSender = i.getMessageSender();
		String messageSendTime = i.getMessageSendtime();
//		String messageId = i.getMessageId();
//		String messageReadflag = i.getMessageReadflag();
		if(!target1.equals(id)) {
			messagePartnerList.add(target1);
//			String info = target1+"/"+messageContents+"/"+messageSendTime;
//			messagePartnerList.add(info);
		}
		if(!target2.equals(id)) {
			messagePartnerList.add(target2);
//			String info = target2+"/"+messageContents+"/"+messageSendTime;
//			messagePartnerList.add(info);
		}
	}
		
		
		
		mv.addObject("messagePartnerList", messagePartnerList);
		mv.setViewName("message");
		return mv;
	}
	// 대화상대 찾아오기 ver.2
	@ResponseBody
	@RequestMapping("/getMessagePartner2.do")
	public Map getMessagePartner2(HttpSession session, @RequestParam (defaultValue = "0")int startPage, @RequestParam (defaultValue = "6")int endPage, @RequestParam (defaultValue = "1")String otherId) {
		Map result = new HashMap();
		Map searchMap = new HashMap();
		MemberVO mvo = (MemberVO)session.getAttribute("memberVO");
		String id = mvo.getMemberId();
		System.out.println("otherId: "+otherId);
		if(otherId.equals("1")) {
			otherId=id;
		}
		searchMap.put("otherId", otherId);
		searchMap.put("id", id);
		searchMap.put("startPage", startPage);
		searchMap.put("endPage", endPage);
		List<MessageVO> messageVO = messageService.getMessagePartner2(searchMap);
		// set을 통해 중복값 제거
		Set<String> messagePartnerList = new HashSet<String>();
//		Set<MessageVO> messagePartnerList = new HashSet<MessageVO>();
		for(MessageVO i: messageVO) {
			String target1 = i.getMessageTarget1();
			String target2 = i.getMessageTarget2();
			String messageContents = i.getMessageContents();
//			String messageSender = i.getMessageSender();
			String messageSendTime = i.getMessageSendtime();
//			String messageId = i.getMessageId();
//			String messageReadflag = i.getMessageReadflag();
			if(!target1.equals(id)) {
				messagePartnerList.add(target1);
//				String info = target1+"/"+messageContents+"/"+messageSendTime;
//				messagePartnerList.add(info);
			}
			if(!target2.equals(id)) {
				messagePartnerList.add(target2);
//				String info = target2+"/"+messageContents+"/"+messageSendTime;
//				messagePartnerList.add(info);
			}
		}
		
		result.put("messagePartnerList", messagePartnerList);
		result.put("messagePartnerListSize", messagePartnerList.size());
		return result;
	}
	// 검색한 id로 대화상대 찾아오기
	@ResponseBody
	@RequestMapping("/searchId.do")
	public Map searchId(HttpSession session, @RequestParam (defaultValue = "0")int startPage, @RequestParam (defaultValue = "6")int endPage, String otherId, String searchNew, @RequestParam (defaultValue = "1")String Id) {
		Map result = new HashMap();
		Map searchMap = new HashMap();
		MemberVO mvo = (MemberVO)session.getAttribute("memberVO");
		Id = mvo.getMemberId();
		searchMap.put("id", Id);
		searchMap.put("otherId", otherId);
		searchMap.put("startPage", startPage);
		searchMap.put("endPage", endPage);
		// 검색어가 있을 경우
		System.out.println("검색어: "+searchNew);
		if(searchNew==null || searchNew.equals("")) {
			result.put("noSearch", "noSearch");
		}else {
			searchMap.put("searchNew", searchNew);
			List<MemberVO> memberList = memberService.getMemberList(searchMap);
			if(memberList==null) {
				result.put("noId", "noSearch");
			}else {
				result.put("memberList", memberList);
				result.put("messagePartnerListSize", memberList.size());
			}
		}
//		if(searchNew!=null || !searchNew.equals("")) {
//			System.out.println("여기 왔냐");
//			searchMap.put("searchNew", searchNew);
//			List<MemberVO> memberList = memberService.getMemberList(searchMap);
//			if(memberList!=null) {
//				result.put("memberList", memberList);
//				result.put("messagePartnerList", memberList.size());
//			}
//			result.put("memberList", "noSearch");
//		}
		return result;
	}
	// chat 가져오기
	@ResponseBody
	@RequestMapping("/getChat.do")
	public Map getChat(HttpSession session, int startPage, int endPage, String otherId) {
		Map result = new HashMap();
		Map searchMap = new HashMap();
		MemberVO mvo = (MemberVO)session.getAttribute("memberVO");
		String id = mvo.getMemberId();
		searchMap.put("id", id);
		searchMap.put("otherId", otherId);
		List<MessageVO> messageVO = messageService.getMessagePartner2(searchMap);
		result.put("messageVO", messageVO);
		result.put("messageVOSize", messageVO.size());
		result.put("loginId", id);
		return result;
	}
	// chat 삭제
	@ResponseBody
	@RequestMapping("/delMessage.do")
	public Map delMessage(HttpSession session, int startPage, int endPage, String otherId, String msgId) {
		// 삭제
		Map result = new HashMap();
		messageService.delMessage(msgId);
		// 갱신된 chat을 테이블 그리기 위해
		Map searchMap = new HashMap();
		MemberVO mvo = (MemberVO)session.getAttribute("memberVO");
		String id = mvo.getMemberId();
		searchMap.put("id", id);
		searchMap.put("otherId", otherId);
		List<MessageVO> messageVO = messageService.getMessagePartner2(searchMap);
		result.put("messageVO", messageVO);
		result.put("messageVOSize", messageVO.size());
		result.put("loginId", id);
		
		return result;
	}
	
	//소켓으로 메시지 받은 대상에게 chat창에 메시지 추가
	@ResponseBody
	@RequestMapping("/addMessage.do")
	public Map addMessage(String content, String id, String otherId) {
		System.out.println("addMessage컨트롤러"+id);
		System.out.println("addMessage컨트롤러"+otherId);
		System.out.println("addMessage컨트롤러"+content);
		Map result = new HashMap();
		Map searchMap = new HashMap();
		searchMap.put("content", content);
		searchMap.put("id", id);
		searchMap.put("otherId", otherId);
		MessageVO messageVO = messageService.addMessage(searchMap);
		String messageSender=messageVO.getMessageSender();
		String messageSendtime = messageVO.getMessageSendtime();
		String messageContents = messageVO.getMessageContents();
		String messageId = messageVO.getMessageId();
		result.put("messageSender", messageSender);
		result.put("messageSendtime", messageSendtime);
		result.put("messageContents", messageContents);
		result.put("messageId", messageId);
		result.put("loginId", otherId);
		return result;
	}
	
//	// 대화상대 찾아오기
//	@ResponseBody
//	@RequestMapping("/getMessagePartner.do")
//	public Map getMessegePartner(HttpSession session, int startPage, int endPage, String otherId,  String content, String toId) {
//		MemberVO mvo = (MemberVO)session.getAttribute("memberVO");
//		String id = mvo.getMemberId();
//		System.out.println(id);
//		System.out.println(otherId);
//		Map searchMap = new HashMap();
//		if(otherId.equals("1")||otherId.equals("나")) {
//			otherId=id;
//		}
//		searchMap.put("otherId", otherId);
//		searchMap.put("id", id);
//		searchMap.put("startPage", startPage);
//		searchMap.put("endPage", endPage);
//		if(!toId.equals("") || !content.equals("")) {
//			searchMap.put("toId", toId);
//			searchMap.put("content", content);
//			messageService.insertMessage(searchMap);
//		}
//		List<MessageVO> messageVO = messageService.getMessagePartner(searchMap);
//		// set을 통해 중복값 제거
////		Set<String> messagePartnerList = new HashSet<String>();
////		for(MessageVO i: messageVO) {
////			String target1 = i.getMessageTarget1();
////			String target2 = i.getMessageTarget2();
////			if(!target1.equals(id)) {
////				messagePartnerList.add(target1);
////			}
////			if(!target2.equals(id)) {
////				messagePartnerList.add(target2);
////			}
////		}
//		
////		for(String i: messagePartnerList) {
////			/* List<MessageVO> time = messageService.messageTimeSelect(i); */
////		}
//		Map map = new HashMap();
//		map.put("loginId", id);
//		map.put("otherId", otherId);
//		map.put("messageList",messageVO);
//		map.put("messageListSize",messageVO.size());
//		return map;
//	}
}

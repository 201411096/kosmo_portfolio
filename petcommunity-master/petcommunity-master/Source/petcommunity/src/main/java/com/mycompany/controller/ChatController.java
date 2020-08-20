package com.mycompany.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ChatController {

	@RequestMapping(value = "/getMemberInfo.do", produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map getMemberInfo(HttpSession session) {
		Map result = new HashMap();
		result.put("memberInfo", session.getAttribute("memberVO"));
		return result;
	}
	@RequestMapping(value = "/chat.do")
	public ModelAndView getMemberInform(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("memberInfo", session.getAttribute("memberVO"));
		mv.setViewName("chat");
		return mv;
	}
	
	@RequestMapping(value = "/test_chatWithOptions.do")
	public ModelAndView getMemberInformation(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("memberInfo", session.getAttribute("memberVO"));
		mv.setViewName("test_chatWithOptions");
		return mv;
	}
}

package com.mycompany.logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mycompany.controller.TestController;
import com.mycompany.domain.MemberVO;

// servlet-context.xml���� interceptor �±װ� �ʿ���
public class LoggerInterceptor extends HandlerInterceptorAdapter{
	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String requesturl = request.getRequestURI();
		StringBuffer logString = new StringBuffer("[RequestURI]" + requesturl);
		HttpSession session = request.getSession();
		Object memberVO = session.getAttribute("memberVO");
		
		if(memberVO!=null) {
			logString.append("[memberId]" + ((MemberVO)memberVO).getMemberId());
		}
		
		//logger.info(logString.toString());
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
		String requesturl = request.getRequestURI();
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		super.afterCompletion(request, response, handler, ex);
//		String requesturl = request.getRequestURI();
//		StringBuffer logString = new StringBuffer("[afterCompletetion_RequestURI]==>" + requesturl);
//		HttpSession session = request.getSession();
//		Object memberVO = session.getAttribute("memberVO");
//		
//		if(memberVO!=null) {
//			logString.append("[memberId]==>" + ((MemberVO)memberVO).getMemberId());
//		}
//		
//		logger.info(logString.toString());
	}
	
}

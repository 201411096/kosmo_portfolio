package com.mycompany.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycompany.domain.MemberVO;
import com.mycompany.domain.ProductCartVO;
import com.mycompany.service.ProductCartService;


@Controller
public class ProductCartController {
	
		@Autowired
		public ProductCartService productCartService;
	
	//장바구니 페이지 입장~~!!
	@ResponseBody
	@RequestMapping(value="/productCartByAjax.do")
	public List<ProductCartVO> productSelectList (HttpSession session, ProductCartVO cvo) {
		MemberVO vo = (MemberVO)session.getAttribute("memberVO");
	
		cvo.setMemberId(vo.getMemberId());
		
		List<ProductCartVO> cartListById = productCartService.getCartListById(cvo);
		
		return cartListById;
		
	}
	
	//장바구니의 물건 수량 바꿔주기
	@ResponseBody
	@RequestMapping(value="/changeProductCntOnCart.do")
	public List<ProductCartVO> changeProductCntOnCart (HttpSession session, ProductCartVO cvo, HttpServletRequest request) {
		//세션에 저장되어있는 회원정보 지정
		MemberVO vo = (MemberVO)session.getAttribute("memberVO");
		cvo.setMemberId(vo.getMemberId());		
		//넘어온 상품 번호와 상품 개수 받아서 db 변경해주기
		String buycartlistCnt = request.getParameter("buycartlistCnt");
		String buycartlistId = request.getParameter("buycartlistId");
		
		cvo.setProductCnt(buycartlistCnt);
		cvo.setProductId(buycartlistId);
		
		productCartService.changeProductCntOnCart(cvo);
		List<ProductCartVO> cartListById = productCartService.getCartListById(cvo);
		return cartListById;		
	}
	
	//장바구니에서 목록 제거
	@ResponseBody
	@RequestMapping(value="/deleteProductFromCart.do")
	public List<ProductCartVO> deleteProductFromCart (HttpSession session, ProductCartVO cvo, HttpServletRequest request) {
		MemberVO vo = (MemberVO)session.getAttribute("memberVO");
		cvo.setMemberId(vo.getMemberId());		
		String buycartlistId = request.getParameter("buycartlistId");

		cvo.setProductId(buycartlistId);
		
		productCartService.deleteProductFromCart(cvo);
		//목록제거후 리스트 다시 조회하여 반환
		List<ProductCartVO> cartListById = productCartService.getCartListById(cvo);
		return cartListById;	
	}
}

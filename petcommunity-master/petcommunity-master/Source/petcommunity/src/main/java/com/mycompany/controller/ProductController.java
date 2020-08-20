package com.mycompany.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.mycompany.domain.MemberVO;
import com.mycompany.domain.PaginationVO;
import com.mycompany.domain.ProductCartVO;
import com.mycompany.domain.ProductVO;
import com.mycompany.domain.ProductreviewVO;
import com.mycompany.domain.ShopVO;
import com.mycompany.service.ProductCartService;
import com.mycompany.service.ProductServiceImpl;
import com.mycompany.service.ProductreviewServiceImpl;


@Controller
public class ProductController {
	
	@Autowired
	public ProductServiceImpl productService;
	@Autowired
	public ProductreviewServiceImpl productreviewService;
	@Autowired
	public ProductCartService productCartService;
	
	@ResponseBody
	@RequestMapping(value="/productSelect.do", produces = "application/json; charset=utf-8")
	public Map productSelectList (@RequestParam (defaultValue = "1") int curPage, String tagCategory, ProductVO vo) {
		Map result = new HashMap();
		Map searchMap = new HashMap(); 
		searchMap.put("tagCategory", tagCategory);
		List<ProductVO> productList = productService.selectProductListBytagCategory(searchMap);
		PaginationVO paginationVO = new PaginationVO(productList.size(), curPage);
		searchMap.put("startRow", paginationVO.getStartIndex()+1);
		paginationVO.setPageSize(9);
		searchMap.put("endRow", paginationVO.getStartIndex()+paginationVO.getPageSize());
		productList = productService.selectProductListForPagination(searchMap);
		
		result.put("pagination", paginationVO);
		result.put("productList", productList);
		result.put("productListSize", productList.size());
		return result;
	}
	// 검색창 자동완성 기능
	@ResponseBody
	@RequestMapping(value="/productAutoCompleteSearch.do", produces = "application/json; charset=utf-8")
	public String productAutoCompleteSearch(String searchSomething, ProductVO vo) {
		List<String> result = productService.selectAutoSearchProduct(searchSomething);
		System.out.println(result);
		String[] productList = new String[result.size()];
		
		if (result != null) {
			for(int i=0; i<result.size(); i++) {
				productList[i] = (String)result.get(i);
			}
		}
		Gson gson = new Gson();
		return gson.toJson(productList);
	}
	@ResponseBody
	@RequestMapping(value="/productView.do")
	public ModelAndView productView(HttpSession session, @RequestParam(defaultValue="1") int curPage, ProductVO vo) {
		ModelAndView mv = new ModelAndView();
		Map searchMap = new HashMap();
		
		//로그인 check
		MemberVO memberVo = (MemberVO) session.getAttribute("MemberVO");
		int productId = vo.getProductId(); 
		ProductVO productInfo =productService.selectProductInfo(productId);
		System.out.println("상품상세설명 컨트롤러 "+productId);
		//리뷰 가져오기
		searchMap.put("productId",productId);
		List<ProductreviewVO> reviewList = productreviewService.selectListReview(searchMap);
		System.out.println("리뷰 가져오기"+ reviewList);
		// 별점 분류를 위한
		int score;
		double totalScore = 0;
		List score5 = new ArrayList();
		List score4 = new ArrayList();
		List score3 = new ArrayList();
		List score2 = new ArrayList();
		List score1 = new ArrayList();
		for (ProductreviewVO i: reviewList) {
			score = i.getProductreviewScore();
			totalScore += score;
			switch(score){
				case 5: score5.add(5);
						break;
				case 4: score4.add(4);
						break;
				case 3: score3.add(3);
						break;
				case 2: score2.add(2);
						break;
				case 1: score1.add(1);
						break;							
			}
		}
		int reviewListSize = reviewList.size();
		double avgScore=totalScore/reviewListSize;
		avgScore = Math.round(avgScore*100)/100.0;
		// 페이징
		PaginationVO paginationVO = new PaginationVO(reviewList.size(), curPage , 3);
		searchMap.put("startRow", paginationVO.getStartIndex()+1);
		paginationVO.setPageSize(3);// 화면에 노출되는 리뷰 리스트의 개수 설정
		searchMap.put("endRow", paginationVO.getStartIndex()+paginationVO.getPageSize()); //3까지
		reviewList=productreviewService.selectReviewByTagWithPaging(searchMap);
		// js(ajax success)로 담아서 넘김
		mv.addObject("score5Size", score5.size());
		mv.addObject("score4Size", score4.size());
		mv.addObject("score3Size", score3.size());
		mv.addObject("score2Size", score2.size());
		mv.addObject("score1Size", score1.size());
		mv.addObject("avgScore", avgScore);
		mv.addObject("loginCheck", memberVo);
		mv.addObject("productInfo", productInfo);
		mv.addObject("reviewListSize", reviewListSize);
		mv.addObject("reviewList", reviewList);
		mv.addObject("pagination", paginationVO);
		mv.setViewName("productView");
		return mv;
	}
	
	// 카트 리스트로 넘어감
	@RequestMapping(value="/buyCartList.do")
	public ModelAndView test(ProductCartVO vo, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		Map searchMap = new HashMap();
		// 로그인 체크
		MemberVO memberVo = (MemberVO) session.getAttribute("memberVO");
		if( memberVo != null) {
			String memberId = memberVo.getMemberId();
			vo.setMemberId(memberId);
			// 장바구니DB에 입력(기존 장바구니 상품은 개수 추가 / 없다면 새로 입력)
			ProductCartVO selectCartList = productCartService.getProductInfoFromCart(vo);
			if(selectCartList != null) {
				productCartService.addProductCnt(vo);
			}else {
				productCartService.insertProductToCart(vo);
			}	
			mv.addObject("ProductCartVO",vo);	
			mv.setViewName("productCart");
			return mv;
		}else {
			mv.setViewName("login");
			return mv;
		}	
	}
	// 카트 리스트로 넘어감
	@ResponseBody
	@RequestMapping(value="/buyCartList2.do")
	public String test1(ProductCartVO vo, HttpSession session, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		Map searchMap = new HashMap();
		// 로그인 체크
		MemberVO memberVo = (MemberVO) session.getAttribute("memberVO");
		String productId = request.getParameter("productId");
		String productCnt=request.getParameter("productCnt");
		vo.setProductCnt(productCnt);
		vo.setProductId(productId);
		if( memberVo != null) {
			String memberId = memberVo.getMemberId();
			vo.setMemberId(memberId);
			// 장바구니DB에 입력(기존 장바구니 상품은 개수 추가 / 없다면 새로 입력)
			ProductCartVO selectCartList = productCartService.getProductInfoFromCart(vo);
			if(selectCartList != null) {
				productCartService.addProductCnt(vo);
			}else {
				productCartService.insertProductToCart(vo);
			}	
			
			return "success";
		}else {
			mv.setViewName("login");
			return "login";
		}	
	}
}

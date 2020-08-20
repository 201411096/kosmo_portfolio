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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.annotation.SessionScope;

import com.mycompany.domain.BuyListVO;
import com.mycompany.domain.MemberVO;
import com.mycompany.domain.ProductreviewVO;
import com.mycompany.service.ProductreviewServiceImpl;

@Controller
public class ProductReviewController {
	
	@Autowired
	ProductreviewServiceImpl productreviewService;
	
	@ResponseBody
	@RequestMapping(value="/insertProductReview.do", produces = "application/json; charset=utf-8")
	public Map insertReivew(HttpServletRequest req, ProductreviewVO vo) {
		Map result = new HashMap();
		Map searchMap = new HashMap();
		int productId = vo.getProductId();
		// 세션에서 로그인 정보 가져오기
		HttpSession session = req.getSession();
		MemberVO memberVo = (MemberVO)session.getAttribute("memberVO");
		System.out.println(memberVo);
		// 로그인 상태 check
		if (memberVo == null) { //로그인 안된 상태
			result.put("loginCheck", "logout");
			return result;
		}else {//로그인 된 상태
			searchMap.put("productId", productId);
			searchMap.put("productreviewScore", vo.getProductreviewScore());
			searchMap.put("productreviewContent", vo.getProductreviewContent());
			searchMap.put("memberId", memberVo.getMemberId());
			// 제품 구매유무 확인
			int resultBuy = productreviewService.selectBuy(searchMap); 
			if( resultBuy > 0 ) {//제품을 구매했다면
				//작성한 리뷰 유무 check
				vo = productreviewService.reviewCheck(searchMap);
				if(vo != null) { // 이미 리뷰를 작성한 경우
					result.put("reviewCheck", "alreadyWriteReview");
					return result;
				}else {//리뷰를 처음작성하는 경우
					int writeComplete = productreviewService.insertReview(searchMap);
					System.out.println(writeComplete);
					List<ProductreviewVO> reviewList = productreviewService.selectListReview(searchMap);
					result.put("productId", productId);
				}
			}else {//제품을 구매하지 않았다면
				result.put("notBuyProduct", "notBuyProduct");
				return result;
			}		
		}
		return result;
	}
	
//	vo = productreviewService.reviewCheck(searchMap);
//	if(vo != null) { // 이미 리뷰를 작성한 경우
//		result.put("reviewCheck", "alreadyWriteReview");
//		return result;
//	}else {
//		 BuyListVO resultBuy = productreviewService.selectBuy(searchMap); 
//		 if( resultBuy !=null ) {
//			 int writeComplete = productreviewService.insertReview(searchMap);
//			 System.out.println(writeComplete);
//			 List<ProductreviewVO> reviewList = productreviewService.selectListReview(searchMap);
//			 result.put("productId", productId);
//		 }
//		 
//	}
//}
//return result;
//}
	
	
	@ResponseBody
	@RequestMapping(value="/reiviewModifyOrDelete.do", produces = "application/json; charset=utf-8")
	public Map reiviewModifyOrDelete(HttpServletRequest req, String writerId, String funcKind, String productId, ProductreviewVO vo) {
		System.out.println("writerId"+writerId);
		System.out.println("productId"+productId);
		System.out.println(funcKind);
		Map searchMap = new HashMap();
		Map result = new HashMap();
		// 세션에서 로그인 정보 가져오기
		HttpSession session = req.getSession();
		MemberVO memberVo = (MemberVO)session.getAttribute("memberVO");
		
		System.out.println("리뷰수정: "+ memberVo);
		if (memberVo == null) { //로그인 안된 상태
			result.put("loginCheck", "logout");
			return result;
		}else{
			String userId = memberVo.getMemberId();
			String userFlag = memberVo.getMemberFlag();
			System.out.println("로그인한"+userId);
			System.out.println(userFlag);
			if( userFlag.equals("1") || userId.equals(writerId)) {
				System.out.println("관리자, 아이디 검사 통과");
				searchMap.put("productId", productId);
				searchMap.put("writerId", writerId);
				if(funcKind.equals("delete")) {
					int deleteResult = productreviewService.deleteReview(searchMap);
					System.out.println("삭제결과"+deleteResult);
					if(deleteResult>0) {
						result.put("reviewDelete", "done");
						return result;
					}
				}
			}else {
				result.put("notWriter", "notWriter");
				return result;
			}
	}
		return result;
	}
}

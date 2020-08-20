package com.mycompany.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.mycompany.domain.CommunityVO;
import com.mycompany.domain.PaginationVO;
import com.mycompany.domain.ShopVO;
import com.mycompany.service.ShopServiceImpl;

@Controller
public class ShopController {
	
	@Autowired
	public ShopServiceImpl shopService;
	
	/* 
	    * 함수 이름 : shopListCategory
	    * 주요 기능 : 제품 검색(분류)에 따른 상품 리스트 출력, pagination 기능
	    * 함수 내용 : 비동기 통신을 활용한 검색(분류)-
	    *  keyword에 따른 리스트 DB검색 및 리스트 개수에 맞는 페이지 설정
	    */
	@ResponseBody
	@RequestMapping(value="/shopCategory.do", produces = "application/json; charset=utf-8")
	public Map shopListCategory(@RequestParam(defaultValue="1") int curPage, String tagCategory, ShopVO vo) {
//		System.out.println("컨트롤러 연결 ok");
		Map result = new HashMap();
		Map searchMap = new HashMap(); 
		searchMap.put("tagCategory", tagCategory);
		// keyword에 따른 상품 DB에서 가져옴
		List<ShopVO> shopList =shopService.selectShopCategoryList(searchMap);
//		System.out.println("서치한 리스트"+shopList);
		// 페이징
		PaginationVO paginationVO = new PaginationVO(shopList.size(), curPage);
		searchMap.put("startRow", paginationVO.getStartIndex()+1); // 1부터 시작
		paginationVO.setPageSize(9);// 화면에 노출되는 상품 리스트의 개수 설정
		searchMap.put("endRow", paginationVO.getStartIndex()+paginationVO.getPageSize()); //9까지
		// 기존 shopList에서 1~9개의 상품만 가져옴
		shopList = shopService.selectShopByTagWithPaging(searchMap);
		// js(ajax success)로 담아서 넘김
		result.put("pagination", paginationVO);
		result.put("shopList", shopList);
		result.put("shopListSize", shopList.size());
		return result;
	}
	
	/* 
	    * 함수 이름 : shopListAutoCompleteSerach
	    * 주요 기능 : 제품 검색 완성 기능
	    * 함수 내용 : 비동기 통신을 활용한 keyword 자동완성-
	    * 1글자 이상의 keyword를 포함하는 상품명을 DB에서 가져와 li형태로 출력 
	    */
	@ResponseBody
	@RequestMapping(value="/shopAutoCompleteSearch.do", produces = "application/json; charset=utf-8")
	public String shopListAutoCompleteSerach(String searchSomething, ShopVO vo) {
		System.out.println("컨트롤 단 옴");
//		Map searchKeyWord = new HashMap();
//		searchKeyWord.put("searchSomething", searchSomething);
		
		// 리스트형태로 상품리스트 DB에서 가져옴
		List<String> searchProductList = shopService.selectSearchAutoProduct(searchSomething);
//		List<String> searchShopList = shopService.selectSearchAutoShop(searchSomething);
//		List<String> searchCategoryList = shopService.selectSearchAutoCategory(searchSomething);
		// 리턴형에 맞는 
		String[] productList = new String [searchProductList.size()];
//		String[] shopList = new String [searchShopList.size()];
//		String[] categoryList = new String [searchCategoryList.size()];
		
		if(searchProductList != null) {
			for(int i=0; i<searchProductList.size(); i++) {
				productList[i] = searchProductList.get(i); 
			}
			Gson gson = new Gson();
			return gson.toJson(productList);
			
//		}else if (searchShopList != null) {
//			for(int i=0; i<searchShopList.size(); i++) {
//				shopList[i] = searchShopList.get(i); 
//			}
//			Gson gson = new Gson();
//			return gson.toJson(shopList);
//			
//		}else if (searchCategoryList != null) {
//			for(int i=0; i<searchCategoryList.size(); i++) {
//				categoryList[i] = searchCategoryList.get(i); 
//			}
//			Gson gson = new Gson();
//			return gson.toJson(categoryList);
		}else {
			return null;
		}
	}
	
	// 굿즈 플로팅 배너 (우측)
			/*
			 * 함수 이름 : shopRank 
			 * 함수 주요 기능 : ajax와 연결되어있음. 사료 판매 순으로 정렬하여 List배열 형태로 받아서 넘겨준다.(페이지에서는 조회수 상위 10위까지 보여짐.)
			 */
		@ResponseBody
		@RequestMapping(value="shoprank.do" , produces = "application/json; charset=utf-8")
		public Map shopRank() {

			Map result = new HashMap();
			
			List<Map<String,String>> list2 =shopService.shoprank(); 
			result.put("buyList", list2);

			return result;

		}
}

package com.mycompany.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.domain.PaginationVO;
import com.mycompany.domain.ProductVO;
import com.mycompany.service.AdminService;
import com.mycompany.service.AdminServiceImpl;
import com.mycompany.service.AdminProductService;
import com.mycompany.service.AdminProductServiceImpl;
import com.mycompany.service.ProductServiceImpl;

@Controller
public class AdminProductController {
	
	@Autowired
	AdminProductServiceImpl adminProductService;
	@Autowired
	ProductServiceImpl productService;
	@Autowired
	AdminServiceImpl adminService;

	/* 
	 * 함수 이름 : loadAdminProduct
	 * 주요 기능 : 상품 리스트 화면 로딩
	 */
	@RequestMapping("/adminProductList.do")
	public ModelAndView loadAdminProduct() {
		System.out.println("상품 리스트 컨트롤러 입장");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/adminProductList");
		return mv;
	}
	
	
	/* 
	 * 함수 이름 : getProductData
	 * 주요 기능 : 상품 리스트 반환
	 * 함수 내용 
	 * 		ㄴ jsp에서 검색어를 받아옴 (검색어가 없을 경우 -> 전체 검색)
	 * 		ㄴ 검색어로 검색한 상품 리스트와 리스트 크기를 json형태로 반환
	 */
	
	@ResponseBody
	@RequestMapping(value = "/getProductData.do", produces = "application/json; charset=utf-8")
	public Map getProductData(HttpSession session, @RequestParam(value = "searchWord") String searchWord) {
		Map result = new HashMap();
		Map<String, String> search = new HashMap<String, String>();
		search.put("searchWord", searchWord);
		List<ProductVO> productList = productService.searchListProduct(search);
		System.out.println(productList);
		result.put("productList", productList);
		result.put("productListSize", productList.size());
		return result;
	}
	
	/* 
	 * 함수 이름 : prodcutInsertPage
	 * 주요 기능 : 상품 등록 페이지를 불러옴
	 */
	@RequestMapping(value = "/loadInsertProduct.do")
	public ModelAndView productInsertPage(HttpSession session, ProductVO productVO) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/adminProductInsert");
		return mv;
	}
	
	/* 
	 * 함수 이름 : productInsert
	 * 주요 기능 : 상품을 등록하고 상품 리스트 페이지로 이동함
	 */
	@RequestMapping(value = "/insertProduct.do")
	public ModelAndView productInsert(HttpSession session, ProductVO productVO) {
		ModelAndView mv = new ModelAndView();

		// 상품 등록해야되는 부분
		adminProductService.insertProduct(productVO);
		mv.setViewName("/adminProductList");
		return mv;
	}
	
	
	/* 
	 * 함수 이름 : productDelete
	 * 주요 기능 : 상품을 삭제하고 상품 리스트 페이지로 이동함
	 */
	@RequestMapping(value = "/productDelete.do")
	public ModelAndView productDelete(HttpSession session, ProductVO productVO) {
		ModelAndView mv = new ModelAndView();
		adminProductService.deleteProduct(productVO);
		mv.setViewName("/adminPage");
		return mv;
	}
	
	
	/* 
	 * 함수 이름 : loadProductUpdatePage
	 * 주요 기능 : 상품 수정 페이지를 로딩함
	 * 함수 내용
	 * 		ㄴ 상품 수정 페이지를 로딩함
	 * 		ㄴ 상품 id를 같이 화면에 넘겨줌
	 */
	@RequestMapping(value = "/loadProductUpdatePage.do")
	public ModelAndView loadProductUpdatePage(HttpSession session, int productId) {
		System.out.println("loadProductUpdatePage.do 요청 받음 : " + productId);
		ModelAndView mv = new ModelAndView();
		ProductVO productVO = new ProductVO();
		productVO.setProductId(productId);
		
		productVO = (ProductVO) adminProductService.selectProduct(productVO);
		mv.addObject("ProductVO", productVO);
		mv.setViewName("adminProductUpdate");
		return mv;
	}
	
	
	/* 
	 * 함수 이름 : productUpdate
	 * 주요 기능 : 상품 정보를 수정함
	 * 함수 내용
	 * 		ㄴ 받아온 상품 id에 해당하는 상품을 받아온 상품 정보로 수정함 
	 */
	@RequestMapping(value = "/productUpdatePage.do")
	public ModelAndView productUpdate(HttpSession session, ProductVO productVO, int productId) {
		ModelAndView mv = new ModelAndView();
		adminProductService.updateProduct(productVO);
		mv.setViewName("/adminProductList");
		return mv;
	}
	
	
	/* 
	 * 함수 이름 : getProductDataWithPaging
	 * 주요 기능 : 검색어와 현재 페이지를 입력받아 현재 페이지에 해당하는 목록을 넘겨줌
	 * 함수 내용
	 * 		ㄴ 검색어와 현재 페이지를 입력받아 현재 페이지에 해당하는 도서 목록을 넘겨줌
	 * 		ㄴ 페이징을 돕는 PaginationVO 사용
	 * 반환되는 위치 : getProductDataWithPaging.js 
	 */
	@RequestMapping(value = "/getProductDataWithPaging.do", produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map getProductDataWithPaging(HttpSession session, @RequestParam(defaultValue = "1") int curPage,
			@RequestParam(value = "searchWord") String searchWord) {

		Map result = new HashMap();
		int listCnt = adminProductService.selectProductCntByNameWithPaging(searchWord);
		PaginationVO paginationVO = new PaginationVO(listCnt, curPage);
		Map searchMap = new HashMap();
		searchMap.put("searchWord", searchWord);
		searchMap.put("startRow", paginationVO.getStartIndex() + 1);
		searchMap.put("endRow", paginationVO.getStartIndex() + paginationVO.getPageSize());
		List<ProductVO> productList = adminProductService.selectProductSearchByNameWithPaging(searchMap);
		result.put("pagination", paginationVO);
		result.put("productList", productList);
		result.put("productListSize", productList.size());
		return result;
	}


}


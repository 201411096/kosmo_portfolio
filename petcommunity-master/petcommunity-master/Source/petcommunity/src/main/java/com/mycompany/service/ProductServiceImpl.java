package com.mycompany.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.dao.ProductDAOImpl;
import com.mycompany.domain.ProductVO;

@Service("productService")
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	ProductDAOImpl productDAO;
	@Override
	public List<ProductVO> selectProductListBytagCategory(Map searchMap) {
		List<ProductVO> result = productDAO.selectProductListBytagCategory(searchMap);
		return result;
	}
	@Override
	public List<ProductVO> selectProductListForPagination(Map searchMap) {
		List<ProductVO> result = productDAO.selectProductListForPagination(searchMap);
		return result;
	}
	@Override
	public List<String> selectAutoSearchProduct(String searchSomething) {
		List<String> result = productDAO.selectAutoSearchProduct(searchSomething);
		return result;
	}
	@Override
	public ProductVO selectProductInfo(int productId) {
		ProductVO result = productDAO.selectProductInfo(productId);
		return result;
	}
	
	@Override
	public List<ProductVO> searchListProduct(Map<String, String> search) {
		List<ProductVO> result = productDAO.searchListProduct(search);
		return result;
	}



}

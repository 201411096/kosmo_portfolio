package com.mycompany.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycompany.domain.ProductVO;

@Repository("productDAO")
public class ProductDAOImpl implements ProductDAO{
	
	@Autowired
	private SqlSessionTemplate mybatis;
	@Override
	public List<ProductVO> selectProductListBytagCategory(Map searchMap) {
		List<ProductVO> result = mybatis.selectList("ProductDAO.selectProductListBytagCategory", searchMap);
		return result;
	}
	@Override
	public List<ProductVO> selectProductListForPagination(Map searchMap) {
		List<ProductVO> result = mybatis.selectList("ProductDAO.selectProductListForPagination", searchMap);
		return result;
	}
	@Override
	public List<String> selectAutoSearchProduct(String searchSomething) {
		List<String> result = mybatis.selectList("ProductDAO.selectAutoSearchProduct",searchSomething);
		return result;
	}
	@Override
	public ProductVO selectProductInfo(int productId) {
		ProductVO result = mybatis.selectOne("ProductDAO.selectProductInfo", productId);
		return result;
	}
	
	@Override
	public List<ProductVO> searchListProduct(Map<String, String> search) {
		List<ProductVO> result = mybatis.selectList("ProductDAO.searchListProduct", search);
		return result;
	}

}

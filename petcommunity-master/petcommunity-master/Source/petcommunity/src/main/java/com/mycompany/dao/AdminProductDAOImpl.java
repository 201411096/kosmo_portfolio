package com.mycompany.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.mycompany.domain.ProductVO;

@Repository("adminProductDAO")
public class AdminProductDAOImpl implements AdminProductDAO{
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	@Override
	public int insertProduct(ProductVO productvo) {
		int result = mybatis.insert("AdminProductDAO.insertProduct", productvo);
		return result;
	}

	@Override
	public ProductVO selectProduct(ProductVO productvo) {
		ProductVO productVO = mybatis.selectOne("AdminProductDAO.selectProductDetail", productvo);
		return productVO;
	}

	public int updateProduct(ProductVO productvo) {
		int result = mybatis.update("AdminProductDAO.updateProduct", productvo);
		return result;
	}

	public int deleteProduct(ProductVO productvo) {
		int result = mybatis.update("AdminProductDAO.deleteProduct", productvo);
		return result;
	}

	public int selectProductCntByNameWithPaging(String searchWord) {
		return mybatis.selectOne("AdminProductDAO.selectProductCntByNameWithPaging", searchWord);
	}

	public List<ProductVO> selectProductSearchByNameWithPaging(Map map) {
		return mybatis.selectList("AdminProductDAO.selectProductSearchByNameWithPaging", map);
	}



}

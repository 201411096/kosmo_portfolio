package com.mycompany.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycompany.domain.MemberVO;
import com.mycompany.domain.ProductCartVO;

@Repository("productCartDAO")
public class ProductCartDAOImpl implements ProductCartDAO{

	@Autowired
	private SqlSessionTemplate mybatis;

	@Override
	public List<ProductCartVO> getCartListById(ProductCartVO vo) {
		return mybatis.selectList("productCartDAO.getCartListById", vo);
	}

	@Override
	public void changeProductCntOnCart(ProductCartVO vo) {
		System.out.println("===>  productMapper changeProductCntOnCart() 호출");
		mybatis.update("productCartDAO.changeProductCntOnCart", vo);
		
	}

	@Override
	public void deleteProductFromCart(ProductCartVO vo) {
		System.out.println("===>  productMapper deleteProductFromCart() 호출");
		mybatis.delete("productCartDAO.deleteProductFromCart", vo);
		
	}

	@Override
	public void insertProductToCart(ProductCartVO vo) {
		mybatis.insert("productCartDAO.insertProductToCart", vo);
		
	}

	@Override
	public void addProductCnt(ProductCartVO vo) {
		mybatis.update("productCartDAO.addProductCnt",vo);
		
	}

	@Override
	public ProductCartVO getProductInfoFromCart(ProductCartVO vo) {
		return mybatis.selectOne("productCartDAO.getProductInfoFromCart",vo);
		
	}



}

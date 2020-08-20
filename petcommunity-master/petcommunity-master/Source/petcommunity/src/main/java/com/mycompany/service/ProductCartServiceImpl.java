package com.mycompany.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.dao.ProductCartDAOImpl;
import com.mycompany.domain.ProductCartVO;

@Service("ProductService")
public class ProductCartServiceImpl implements ProductCartService {

	
	@Autowired
	private ProductCartDAOImpl productCartDAO;


	@Override
	public List<ProductCartVO> getCartListById(ProductCartVO vo) {
		return productCartDAO.getCartListById(vo);
	}


	@Override
	public void changeProductCntOnCart(ProductCartVO vo) {
		productCartDAO.changeProductCntOnCart(vo);		
	}


	@Override
	public void deleteProductFromCart(ProductCartVO vo) {
		productCartDAO.deleteProductFromCart(vo);		
	}


	@Override
	public void insertProductToCart(ProductCartVO vo) {
		productCartDAO.insertProductToCart(vo);
		
	}


	@Override
	public void addProductCnt(ProductCartVO vo) {
		productCartDAO.addProductCnt(vo);
		
	}


	@Override
	public ProductCartVO getProductInfoFromCart(ProductCartVO vo) {
		return productCartDAO.getProductInfoFromCart(vo);
		
	}
	


}

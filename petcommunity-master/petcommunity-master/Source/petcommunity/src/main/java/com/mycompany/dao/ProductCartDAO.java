package com.mycompany.dao;

import java.util.List;
import java.util.Map;

import com.mycompany.domain.ProductCartVO;

public interface ProductCartDAO {
	public List<ProductCartVO> getCartListById(ProductCartVO vo);
	public void changeProductCntOnCart(ProductCartVO vo);
	public void deleteProductFromCart(ProductCartVO vo);
	public void insertProductToCart(ProductCartVO vo);
	public void addProductCnt(ProductCartVO vo);
	public ProductCartVO getProductInfoFromCart(ProductCartVO vo);
}

package com.mycompany.service;

import java.util.List;
import java.util.Map;

import com.mycompany.domain.ProductVO;

public interface AdminProductService {
	public int insertProduct(ProductVO productvo);
	public ProductVO selectProduct(ProductVO productvo);
	public int updateProduct(ProductVO productvo);
	public int deleteProduct(ProductVO productvo);
	public int selectProductCntByNameWithPaging(String searchWord);
	public List<ProductVO> selectProductSearchByNameWithPaging(Map map);
	
	

}

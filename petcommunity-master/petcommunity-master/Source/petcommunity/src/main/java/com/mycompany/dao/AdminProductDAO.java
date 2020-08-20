package com.mycompany.dao;

import java.util.List;
import java.util.Map;

import com.mycompany.domain.ProductVO;

public interface AdminProductDAO {
	public int insertProduct(ProductVO productvo);
	public ProductVO selectProduct(ProductVO productvo);
	public int updateProduct(ProductVO productvo);
	public int deleteProduct(ProductVO productvo);
	public List<ProductVO> selectProductSearchByNameWithPaging(Map map);
	public int selectProductCntByNameWithPaging(String searchWord);
}

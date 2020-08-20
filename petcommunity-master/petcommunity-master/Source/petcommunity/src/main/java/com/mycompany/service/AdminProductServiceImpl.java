package com.mycompany.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.dao.AdminProductDAOImpl;
import com.mycompany.domain.ProductVO;

@Service("adminProductService")
public class AdminProductServiceImpl implements AdminProductService {

	@Autowired
	AdminProductDAOImpl adminProductDAO;
	
	@Override
	public int insertProduct(ProductVO productvo) {
		
		return adminProductDAO.insertProduct(productvo);
	}

	@Override
	public ProductVO selectProduct(ProductVO productvo) {
		
		return adminProductDAO.selectProduct(productvo);
	}

	@Override
	public int updateProduct(ProductVO productvo) {
		return adminProductDAO.updateProduct(productvo);
	}

	@Override
	public int deleteProduct(ProductVO productvo) {
		
		return adminProductDAO.deleteProduct(productvo);
	}

	public int selectProductCntByNameWithPaging(String searchWord) {
		return adminProductDAO.selectProductCntByNameWithPaging(searchWord);
	}

	public List<ProductVO> selectProductSearchByNameWithPaging(Map map) {
		return adminProductDAO.selectProductSearchByNameWithPaging(map);
	}



	

}

package com.mycompany.service;

import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.mycompany.dao.ShopDAOImpl;
import com.mycompany.domain.PaginationVO;
import com.mycompany.domain.ShopVO;


@Service("shopService")
public class ShopServiceImpl implements ShopService{
	
	@Autowired
	private ShopDAOImpl shopDAO;

	@Override
	public List<ShopVO> selectShopByTagWithPaging(Map searchListMap) {
		return shopDAO.selectShopByTagWithPaging(searchListMap);
	}

	
	@Override
	public List<ShopVO> selectShopCategoryList(Map searchMap) {
		return shopDAO.selectShopCategoryList(searchMap);
	}


	@Override
	public List<String> selectSearchAutoProduct(String searchSomething) {
		return shopDAO.selectSearchAutoProduct(searchSomething);
	}
	
	@Override
	public List<String> selectSearchAutoShop(String searchSomething) {
		return shopDAO.selectSearchAutoShop(searchSomething);
	}
	
	@Override
	public List<String> selectSearchAutoCategory(String searchSomething) {
		return shopDAO.selectSearchAutoCategory(searchSomething);
	}


	@Override
	public List<Map<String,String>> shoprank() {
		
		return shopDAO.shoprank();
	}
}

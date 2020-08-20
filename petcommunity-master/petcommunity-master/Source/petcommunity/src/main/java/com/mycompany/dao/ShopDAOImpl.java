package com.mycompany.dao;

import java.util.Map;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycompany.domain.PaginationVO;
import com.mycompany.domain.ShopVO;

@Repository("shopDAO")
public class ShopDAOImpl implements ShopDAO{
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	@Override
	public List<ShopVO> selectShopByTagWithPaging(Map searchListMap) {
		List<ShopVO> result = mybatis.selectList("ShopDAO.selectShopByTagWithPaging", searchListMap);
		return result;
	}

	@Override
	public List<ShopVO> selectShopCategoryList(Map searchMap) {
		List<ShopVO> result = mybatis.selectList("ShopDAO.selectShopCategoryList", searchMap);
		return result;
	}

	@Override
	public List<String> selectSearchAutoProduct(String searchSomething) {
		List<String> result = mybatis.selectList("ShopDAO.selectSearchAutoProduct", searchSomething);
		return result;
	}
	
	@Override
	public List<String> selectSearchAutoShop(String searchSomething) {
		List<String> result = mybatis.selectList("ShopDAO.selectSearchAutoShop", searchSomething);
		return result;
	}
	
	@Override
	public List<String> selectSearchAutoCategory(String searchSomething) {
		List<String> result = mybatis.selectList("ShopDAO.selectSearchAutoCategory", searchSomething);
		return result;
	}

	@Override
	public List<Map<String,String>> shoprank() {
		
		return mybatis.selectList("ShopDAO.shoprank");
	}
}

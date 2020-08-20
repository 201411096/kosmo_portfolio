package com.mycompany.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.dao.BuyDAOImpl;
import com.mycompany.domain.BuyListVO;
import com.mycompany.domain.MemberVO;
import com.mycompany.domain.ProductCartVO;


@Service("BuyService")
public class BuyServiceImpl implements BuyService{
	
	@Autowired
	BuyDAOImpl buyDAO;

	@Override
	public List<Map<String,String>> buyList(Map map) {
		
		return buyDAO.buyList(map);
	}

	@Override
	public List<Map<String,String>> buyReceipt(String id) {
		
		return buyDAO.buyReceipt(id);
	}

	@Override
	public void buyInsert(int totalPrice , ProductCartVO vo,List<ProductCartVO> cartList) {
		buyDAO.buyInsert(totalPrice,vo,cartList);
		
	}

	@Override
	public int buyPaging(String memberId) {
		
		return buyDAO.buyPaging(memberId);
	} 

}

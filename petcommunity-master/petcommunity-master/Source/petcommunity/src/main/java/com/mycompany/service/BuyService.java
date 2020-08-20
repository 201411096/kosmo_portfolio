package com.mycompany.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mycompany.domain.BuyListVO;
import com.mycompany.domain.MemberVO;
import com.mycompany.domain.ProductCartVO;


public interface BuyService {

	public List<Map<String,String>> buyList(Map map);
	
	public List<Map<String,String>> buyReceipt(String id);
	
	public void buyInsert(int totalPrice , ProductCartVO vo,List<ProductCartVO> cartList);

	public int buyPaging(String memberId);
}

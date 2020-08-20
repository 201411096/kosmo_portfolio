package com.mycompany.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycompany.domain.BuyListVO;
import com.mycompany.domain.MemberVO;
import com.mycompany.domain.ProductCartVO;



@Repository("BuyDAO")
public class BuyDAOImpl implements BuyDAO {

	@Autowired
	private SqlSessionTemplate mybatis;

	@Override
	public List<Map<String, String>> buyList(Map map) {
		return mybatis.selectList("buy.mybuyList",map);
	}

	@Override
	public List<Map<String, String>> buyReceipt(String id) {
		return mybatis.selectList("buy.buyReceipt",id);
	}

	@Override
	public void buyInsert(int totalPrice , ProductCartVO vo,List<ProductCartVO> cartList) {
		HashMap map = new HashMap();
		map.put("totalPrice", totalPrice);
		map.put("memberId", vo.getMemberId());
		
		mybatis.insert("buy.buyListInsert",map);
		
		HashMap map2 = new HashMap();
		for(ProductCartVO i :cartList) {
			map2.put("cnt", i.getBuycartlistCnt());
			map2.put("price", Integer.parseInt(i.getProductPrice())*Integer.parseInt(i.getBuycartlistCnt()));
			map2.put("productId", i.getProductId());
			map2.put("buyListId",map.get("buyListId"));

		mybatis.insert("buy.buyInsert",map2);
		
		mybatis.delete("buy.deleteCart",map);
		}
	}

	@Override
	public int buyPaging(String memberId) {
		
		return mybatis.selectOne("buy.buyPaging", memberId);
	}
	
}

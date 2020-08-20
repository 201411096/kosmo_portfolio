package com.mycompany.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycompany.domain.BuylistviewVO;
import com.mycompany.domain.ManagerVO;
import com.mycompany.domain.MemberVO;

@Repository("adminDAO")
public class AdminDAOImpl implements AdminDAO{
	@Autowired
	private SqlSessionTemplate mybatis;

	public List<BuylistviewVO> adminselectList(BuylistviewVO buylistviewvo) {
		return mybatis.selectList("admin.adminselectList", buylistviewvo);
	}
	
	public List<BuylistviewVO> getSearchDate(Map map) {
		return mybatis.selectList("admin.getSearchDate", map);
	}

	
	public List<MemberVO> getgetMemberList(MemberVO membervo) {
		return mybatis.selectList("admin.getMemberList", membervo);
	}
	public List<MemberVO> getMemberSelect(Map map) {
		return mybatis.selectList("admin.getMemberSelect", map);
	}

	public int deleteInfo(String memberId) {
		return mybatis.delete("admin.deleteInfo", memberId);
	}

	@Override
	public int managerInsert(HashMap map) {
		
		return mybatis.insert("admin.managerInsert",map);
	}

	@Override
	public List<ManagerVO> managerSelect() {
		
		return mybatis.selectList("admin.managerSelect");
	}

	@Override
	public int managerupdate(HashMap map) {
	
		return mybatis.update("admin.managerUpdate",map);
	}

	@Override
	public List<ManagerVO> checkId(String id) {
		
		return mybatis.selectList("admin.checkId",id);
	}

	@Override
	public void managerDelete(String id) {
		
		mybatis.delete("admin.managerDelete",id);
		
	}

	

}

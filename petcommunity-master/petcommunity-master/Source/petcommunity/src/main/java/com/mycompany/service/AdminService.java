package com.mycompany.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mycompany.domain.BuylistviewVO;
import com.mycompany.domain.ManagerVO;
import com.mycompany.domain.MemberVO;
import com.mycompany.domain.QnaVO;

public interface AdminService  {

	public List<BuylistviewVO> adminselectList(BuylistviewVO buylistviewvo);
	
	public List<BuylistviewVO> getSearchDate(Map map);

	// 넥사크로
	public List<MemberVO> getMemberList(MemberVO membervo);
	public List<MemberVO> getMemberSelect(Map map);

	public int deleteInfo(String memberId);
	public int managerInsert(HashMap map);
	public List<ManagerVO> managerSelect();
	public int managerupdate(HashMap map);
	public List<ManagerVO> checkId(String id);
	public void managerDelete(String id);
	



}

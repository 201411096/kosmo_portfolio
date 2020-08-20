package com.mycompany.dao;

import java.util.List;
import java.util.Map;

import com.mycompany.domain.LostBoardVO;
import com.mycompany.domain.MemberVO;

public interface MemberDAO {
	public int signup(MemberVO vo);
	
	public int idcheck(MemberVO vo);
	
	public MemberVO signin(MemberVO vo);
	
	public void tokenInsert(MemberVO vo);
	
	public List<MemberVO> selectPeopleAroundLocation(LostBoardVO vo);
	
	public String selectListPushTarget(String lostBoardWriter);
	
	public List<MemberVO> getMemberList(Map searchMap);
	
	public MemberVO memberList(String id);
	
	public int updateMember(MemberVO vo);
	
	public MemberVO findMemberId(MemberVO vo);
	
	public MemberVO findMemberpass(MemberVO vo);
	
	public int makeTemporaryPassword(MemberVO vo);
	
	public void insertLocationInfo(MemberVO vo);
}

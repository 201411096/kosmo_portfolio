package com.mycompany.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycompany.domain.LostBoardVO;
import com.mycompany.domain.MemberVO;

@Repository("MemberDAO")
public class MemberDAOImpl implements MemberDAO{

	@Autowired
	private SqlSessionTemplate mybatis;
	@Override
	public int signup(MemberVO vo) {
		return mybatis.insert("member.signup",vo);
	}
	@Override
	public int idcheck(MemberVO vo) {
		return  mybatis.selectOne("member.idcheck", vo);
	}
	@Override
	public MemberVO signin(MemberVO vo) {
		
		return mybatis.selectOne("member.signin",vo);
	}
	
	
	@Override
	public void tokenInsert(MemberVO vo) {
		mybatis.update("member.tokenInsert",vo);
		
	}
	@Override
	public List<MemberVO> selectPeopleAroundLocation(LostBoardVO vo) {
		return mybatis.selectList("member.selectPeopleAroundLocation", vo);
	}
	@Override
	public String selectListPushTarget(String lostBoardWriter) {
		return mybatis.selectOne("member.selectListPushTarget", lostBoardWriter);
	}
	@Override
	public MemberVO memberList(String id) {
		
		return mybatis.selectOne("member.memberList", id);
	}
	@Override
	public int updateMember(MemberVO vo) {
		
		return mybatis.update("member.updateMember",vo);
	}
	@Override
	public MemberVO findMemberId(MemberVO vo) {
		return mybatis.selectOne("member.findMemberId",vo);
	}
	@Override
	public MemberVO findMemberpass(MemberVO vo) {
		
		return mybatis.selectOne("member.findMemberPass",vo);
	}
	@Override
	public int makeTemporaryPassword(MemberVO vo) {
		
		return mybatis.update("member.makeTemporaryPassword",vo);
	}
	@Override
	public void insertLocationInfo(MemberVO vo) {
		mybatis.update("member.insertLocationInfo", vo);
		
	}
	@Override
	public List<MemberVO> getMemberList(Map searchMap) {
		List<MemberVO> result = mybatis.selectList("member.getMemberList", searchMap);
		return result;
	}

}

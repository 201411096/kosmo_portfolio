package com.mycompany.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.dao.MemberDAOImpl;
import com.mycompany.domain.LostBoardVO;
import com.mycompany.domain.MemberVO;

@Service("MemberService")
public class MemberServiceImpl implements MemberService {

	
	@Autowired
	private MemberDAOImpl memberDAO;
	
	@Override
	public int signup(MemberVO vo) {
		return memberDAO.signup(vo); 
		
	}

	@Override
	public int idcheck(MemberVO vo) {
		return  memberDAO.idcheck(vo);
	}

	@Override
	public MemberVO signin(MemberVO vo) {
		
		return memberDAO.signin(vo);
	}

	
	
	
	@Override
	public void tokenInsert(MemberVO vo) {
		memberDAO.tokenInsert(vo);
		
	}

	@Override
	public List<MemberVO> selectPeopleAroundLocation(LostBoardVO vo) {
		return memberDAO.selectPeopleAroundLocation(vo);
	}
	
	@Override
	public String selectListPushTarget(String lostBoardWriter) {
		return memberDAO.selectListPushTarget(lostBoardWriter);
	}

	@Override
	public MemberVO memberList(String id) {
		
		return memberDAO.memberList(id);
	}

	@Override
	public int updateMember(MemberVO vo) {
		
		return memberDAO.updateMember(vo);
	}

	@Override
	public MemberVO findMemberId(MemberVO vo) {
		
		return memberDAO.findMemberId(vo);
	}

	@Override
	public MemberVO findMemberPass(MemberVO vo) {
		
		return memberDAO.findMemberpass(vo);
	}

	@Override
	public int makeTemporaryPassword(MemberVO vo) {
		
		return memberDAO.makeTemporaryPassword(vo);
	}

	@Override
	public void insertLocationInfo(MemberVO vo) {
		memberDAO.insertLocationInfo(vo);
		
	}

	@Override
	public List<MemberVO> getMemberList(Map searchMap) {
		List<MemberVO> result = memberDAO.getMemberList(searchMap);		
		return result;
	}

}

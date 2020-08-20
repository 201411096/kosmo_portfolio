package com.mycompany.service;

import java.util.List;
import java.util.Map;

import com.mycompany.domain.LostBoardVO;
import com.mycompany.domain.MemberVO;

public interface MemberService {
	public int signup(MemberVO vo);
	
	public int idcheck(MemberVO vo);
	
	public MemberVO signin(MemberVO vo);
	
	//좌표값 추가
	public void insertLocationInfo(MemberVO vo);
	
	//token값 추가(MemberController)
	public void tokenInsert(MemberVO vo);
	
	//실종 게시물 주변 사람들 select(lostBoardController)
	public List<MemberVO> selectPeopleAroundLocation(LostBoardVO vo);
	
	// 찾기 게시판 주변 실종 게시판 글 작성자들 select(FindBoardController-findboardListWithPaging.do)
	public String selectListPushTarget(String lostBoardWriter);
	
	// 메시지 대상 찾기
	public List<MemberVO> getMemberList(Map searchMap);
	
	public MemberVO memberList(String id);
	
	public int updateMember(MemberVO vo);
	
	public MemberVO findMemberId(MemberVO vo);
	
	public MemberVO findMemberPass(MemberVO vo);
	
	public int makeTemporaryPassword(MemberVO vo);
	
}

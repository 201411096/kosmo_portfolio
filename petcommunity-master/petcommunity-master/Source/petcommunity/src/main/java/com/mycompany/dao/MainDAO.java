package com.mycompany.dao;

import java.util.List;
import java.util.Map;

import com.mycompany.domain.CommunityVO;
import com.mycompany.domain.FindBoardVO;
import com.mycompany.domain.LostBoardVO;
import com.mycompany.domain.MemberVO;
import com.mycompany.domain.QnaVO;

public interface MainDAO {
	
	public List<LostBoardVO> getLostBoardList();
	public List<FindBoardVO> getFindBoardList();
	public List<CommunityVO> getCommunityBoardList();
	public List<QnaVO> getQnaBoardList();
	public String lostCount();
	public String findCount();
	public String lostDoneCount();
}

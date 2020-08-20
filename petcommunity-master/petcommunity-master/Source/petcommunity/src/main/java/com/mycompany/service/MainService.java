package com.mycompany.service;

import java.util.List;
import java.util.Map;

import com.mycompany.domain.CommunityVO;
import com.mycompany.domain.FindBoardVO;
import com.mycompany.domain.LostBoardVO;
import com.mycompany.domain.QnaVO;

public interface MainService {

	List<LostBoardVO> getLostBoardList();

	List<FindBoardVO> getFindBoardList();

	List<CommunityVO> getCommunityBoardList();

	List<QnaVO> getQnaBoardList();
	
	public String lostCount();
	
	public String findCount();
	
	public String lostDoneCount();
	
}

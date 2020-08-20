package com.mycompany.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.dao.MainDAOImpl;
import com.mycompany.dao.QnaDAOImpl;
import com.mycompany.domain.CommunityVO;
import com.mycompany.domain.FindBoardVO;
import com.mycompany.domain.LostBoardVO;
import com.mycompany.domain.QnaVO;

@Service("mainService")
public class MainServiceImpl implements MainService{
	
	@Autowired
	private MainDAOImpl mainDAO;
	
	@Override
	public List<LostBoardVO> getLostBoardList() {
		
		return mainDAO.getLostBoardList();
	}

	@Override
	public List<FindBoardVO> getFindBoardList() {
		
		return mainDAO.getFindBoardList();
	}

	@Override
	public List<CommunityVO> getCommunityBoardList() {
		// TODO Auto-generated method stub
		return mainDAO.getCommunityBoardList();
	}

	@Override
	public List<QnaVO> getQnaBoardList() {
		// TODO Auto-generated method stub
		return mainDAO.getQnaBoardList();
	}

	@Override
	public String lostCount() {
		
		return mainDAO.lostCount();
	}

	@Override
	public String findCount() {
		
		return mainDAO.findCount();
	}

	@Override
	public String lostDoneCount() {
		
		return mainDAO.lostDoneCount();
	}

		

}

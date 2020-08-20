package com.mycompany.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycompany.domain.CommunityVO;
import com.mycompany.domain.FindBoardVO;
import com.mycompany.domain.LostBoardVO;
import com.mycompany.domain.QnaVO;

@Repository("mainDAO")
public class MainDAOImpl implements MainDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;

	public List<LostBoardVO> getLostBoardList() {
		return mybatis.selectList("mainDAO.getLostBoardList");
	}

	public List<FindBoardVO> getFindBoardList() {
		return mybatis.selectList("mainDAO.getFindBoardList");
	}

	public List<CommunityVO> getCommunityBoardList() {
		return mybatis.selectList("mainDAO.getCommunityBoardList");
	}

	public List<QnaVO> getQnaBoardList() {
		return mybatis.selectList("mainDAO.getQnaBoardList");
	}

	@Override
	public String lostCount() {
		
		return mybatis.selectOne("mainDAO.getLostCount");
	}

	@Override
	public String findCount() {
		
		return mybatis.selectOne("mainDAO.getfindCount");
	}

	@Override
	public String lostDoneCount() {
		String data ="찾음";
		return mybatis.selectOne("mainDAO.getLostDoneCount",data);
	}


}

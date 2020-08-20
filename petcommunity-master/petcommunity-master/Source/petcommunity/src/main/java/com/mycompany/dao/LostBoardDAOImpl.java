package com.mycompany.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycompany.domain.FindBoardVO;
import com.mycompany.domain.LostBoardVO;

@Repository("lostBoardDAO")
public class LostBoardDAOImpl implements LostBoardDAO{
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public List<LostBoardVO> selectLostBoard(Map map){
		return mybatis.selectList("lostBoardDAO.getLostBoardList", map);
	}

	@Override
	public List<LostBoardVO> selectLostBoardWithPaging(Map map) {
		return mybatis.selectList("lostBoardDAO.getLostBoardListWithPaging", map);
	}

	@Override
	public int insertLostBoard(LostBoardVO lostBoardVO) {
		return mybatis.insert("lostBoardDAO.insertLostBoard", lostBoardVO);
	}

	@Override
	public LostBoardVO getLostBoard(LostBoardVO lostBoardVO) {
		return mybatis.selectOne("lostBoardDAO.getLostBoard", lostBoardVO);
	}

	@Override
	public int increaseLostBoardReadcount(LostBoardVO lostBoardVO) {
		return mybatis.update("lostBoardDAO.increaseLostBoardReadcount", lostBoardVO);
	}

	@Override
	public int deleteLostBoard(LostBoardVO lostBoardVO) {
		return mybatis.delete("lostBoardDAO.deleteLostBoard", lostBoardVO);
	}

	@Override
	public int updateLostBoard(LostBoardVO lostBoardVO) {
		return mybatis.update("lostBoardDAO.updateLostBoard", lostBoardVO);
	}

	@Override
	public List<String> selectString(Map map) {
		return mybatis.selectList("lostBoardDAO.getStringForAutoComplete", map);
	}

	@Override
	public List<LostBoardVO> selectLostBoardForMap(Map map) {
		return mybatis.selectList("lostBoardDAO.getLostBoardForMap", map);
	}
	
	@Override
	public List<LostBoardVO> selectLostBoardForMap2(Map map) {
		return mybatis.selectList("lostBoardDAO.getLostBoardForMap2", map);
	}

	//알림 보낼 list추리는 함수
	@Override
	public List<LostBoardVO> findPeopleByLocationOfLostPost(FindBoardVO findBoardVO) {
		List<LostBoardVO> result = mybatis.selectList("lostBoardDAO.findPeopleByLocationOfLostPost", findBoardVO);
		return result;
	}
	
	
}

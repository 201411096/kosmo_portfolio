package com.mycompany.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycompany.domain.FindBoardVO;

@Repository("findBoardDAO")
public class FindBoardDAOImpl implements FindBoardDAO{
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public List<FindBoardVO> selectFindBoard(Map map){
		return mybatis.selectList("findBoardDAO.getFindBoardList", map);
	}

	@Override
	public List<FindBoardVO> selectFindBoardWithPaging(Map map) {
		return mybatis.selectList("findBoardDAO.getFindBoardListWithPaging", map);
	}

	@Override
	public int insertFindBoard(FindBoardVO findBoardVO) {
		return mybatis.insert("findBoardDAO.insertFindBoard", findBoardVO);
	}

	@Override
	public FindBoardVO getFindBoard(FindBoardVO findBoardVO) {
		return mybatis.selectOne("findBoardDAO.getFindBoard", findBoardVO);
	}

	@Override
	public int increaseFindBoardReadcount(FindBoardVO findBoardVO) {
		return mybatis.update("findBoardDAO.increaseFindBoardReadcount", findBoardVO);
	}

	@Override
	public int deleteFindBoard(FindBoardVO findBoardVO) {
		return mybatis.delete("findBoardDAO.deleteFindBoard", findBoardVO);
	}

	@Override
	public int updateFindBoard(FindBoardVO findBoardVO) {
		return mybatis.update("findBoardDAO.updateFindBoard", findBoardVO);
	}

	@Override
	public List<String> selectString(Map map) {
		return mybatis.selectList("findBoardDAO.getStringForAutoComplete", map);
	}

	@Override
	public List<FindBoardVO> selectFindBoardForMap(Map map) {
		return mybatis.selectList("findBoardDAO.getFindBoardForMap", map);
	}
	
	
}

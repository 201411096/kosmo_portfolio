package com.mycompany.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.dao.FindBoardDAOImpl;
import com.mycompany.domain.FindBoardVO;

@Service("findBoardService")
public class FindBoardServiceImpl implements FindBoardService{
	@Autowired
	FindBoardDAOImpl findBoardDAO;

	@Override
	public List<FindBoardVO> selectFindBoard(Map map){
		return findBoardDAO.selectFindBoard(map);
	}

	@Override
	public List<FindBoardVO> selectFindBoardWithPaging(Map map) {
		return findBoardDAO.selectFindBoardWithPaging(map);
	}

	@Override
	public int insertFindBoard(FindBoardVO findBoardVO) {
		return findBoardDAO.insertFindBoard(findBoardVO);
	}

	@Override
	public FindBoardVO getFindBoard(FindBoardVO findBoardVO) {
		return findBoardDAO.getFindBoard(findBoardVO);
	}

	@Override
	public int increaseFindBoardReadcount(FindBoardVO findBoardVO) {
		return findBoardDAO.increaseFindBoardReadcount(findBoardVO);
	}

	@Override
	public int deleteFindBoard(FindBoardVO findBoardVO) {
		return findBoardDAO.deleteFindBoard(findBoardVO);
	}

	@Override
	public int updateFindBoard(FindBoardVO findBoardVO) {
		return findBoardDAO.updateFindBoard(findBoardVO);
	}

	@Override
	public List<String> selectString(Map map) {
		return findBoardDAO.selectString(map);
	}

	@Override
	public List<FindBoardVO> selectFindBoardForMap(Map map) {
		return findBoardDAO.selectFindBoardForMap(map);
	}
	
	
}

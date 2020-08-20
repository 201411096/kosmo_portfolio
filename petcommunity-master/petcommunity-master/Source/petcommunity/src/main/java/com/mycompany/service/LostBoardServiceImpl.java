package com.mycompany.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.dao.LostBoardDAOImpl;
import com.mycompany.domain.FindBoardVO;
import com.mycompany.domain.LostBoardVO;

@Service("lostBoardService")
public class LostBoardServiceImpl implements LostBoardService{
	@Autowired
	LostBoardDAOImpl lostBoardDAO;

	@Override
	public List<LostBoardVO> selectLostBoard(Map map){
		return lostBoardDAO.selectLostBoard(map);
	}

	@Override
	public List<LostBoardVO> selectLostBoardWithPaging(Map map) {
		return lostBoardDAO.selectLostBoardWithPaging(map);
	}

	@Override
	public int insertLostBoard(LostBoardVO lostBoardVO) {
		return lostBoardDAO.insertLostBoard(lostBoardVO);
	}

	@Override
	public LostBoardVO getLostBoard(LostBoardVO lostBoardVO) {
		return lostBoardDAO.getLostBoard(lostBoardVO);
	}

	@Override
	public int increaseLostBoardReadcount(LostBoardVO lostBoardVO) {
		return lostBoardDAO.increaseLostBoardReadcount(lostBoardVO);
	}

	@Override
	public int deleteLostBoard(LostBoardVO lostBoardVO) {
		return lostBoardDAO.deleteLostBoard(lostBoardVO);
	}

	@Override
	public int updateLostBoard(LostBoardVO lostBoardVO) {
		return lostBoardDAO.updateLostBoard(lostBoardVO);
	}

	@Override
	public List<String> selectString(Map map) {
		return lostBoardDAO.selectString(map);
	}

	@Override
	public List<LostBoardVO> selectLostBoardForMap(Map map) {
		return lostBoardDAO.selectLostBoardForMap(map);
	}
	
	@Override
	public List<LostBoardVO> selectLostBoardForMap2(Map map) {
		return lostBoardDAO.selectLostBoardForMap2(map);
	}
	
	//알림 보낼 list추리는 함수
	@Override
	public List<LostBoardVO> findPeopleByLocationOfLostPost(FindBoardVO findBoardVO) {
		List<LostBoardVO> result = lostBoardDAO.findPeopleByLocationOfLostPost(findBoardVO);
		return result;
	}
	
}

package com.mycompany.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.dao.QnaDAOImpl;
import com.mycompany.domain.QnaVO;

@Service("qnaService")
public class QnaServiceImpl implements QnaService{

	@Autowired
	private QnaDAOImpl qnaDAO;
	
	@Override
	public int insertQnaBoard(QnaVO qnavo) {
		return qnaDAO.insertQnaBoard(qnavo);
	}

	@Override
	public List<QnaVO> getQnaBoardList(QnaVO qnavo) {
		return qnaDAO.getQnaBoardList(qnavo);
	}

	@Override
	public QnaVO getQnaBoardContent(QnaVO qnavo) {
		return qnaDAO.getQnaBoardContent(qnavo);
	}

	@Override
	public int updateQna(QnaVO qnavo) {
		return qnaDAO.updateQna(qnavo);
	}

	@Override
	public void updateReadcount(QnaVO qnavo) {
		qnaDAO.updateReadcount(qnavo);		
	}

	@Override
	public void deleteQnaBoard(QnaVO qnavo) {
		qnaDAO.deleteQnaBoard(qnavo);
	}


	@Override
	public List<QnaVO> selectKeyword(Map map) {
		return qnaDAO.selectKeyword(map);
	}

	@Override
	public void insertReply(QnaVO qnavo) {
		qnaDAO.insertReply(qnavo);
		
	}

	@Override
	public QnaVO selectGroupId(QnaVO qnavo) {
		return qnaDAO.selectGroupId(qnavo);
	}

	@Override
	public List<QnaVO> selectListRe(QnaVO qnavo) {
		return qnaDAO.selectListRe(qnavo);
	}

	@Override
	public List<QnaVO> selectQnaBoardWithPaging(Map map) {
		return qnaDAO.selectQnaBoardWithPaging(map);
	}

	@Override
	public List<QnaVO> selectQuestionGroupId(QnaVO qnavo) {
		return qnaDAO.selectQuestionGroupId(qnavo);
	}

	@Override
	public void deleteQnaBoardbyGroupId(QnaVO qnavo) {
		qnaDAO.deleteQnaBoardbyGroupId(qnavo);
		
	}
}

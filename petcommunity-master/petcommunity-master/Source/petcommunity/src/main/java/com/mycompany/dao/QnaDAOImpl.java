package com.mycompany.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycompany.domain.QnaVO;

@Repository("qnaDAO")
public class QnaDAOImpl implements QnaDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;

	
	@Override
	public int insertQnaBoard(QnaVO qnavo) {
		int result = mybatis.insert("qnaDAO.insertQnaBoard", qnavo);
		return result;
	}
	
	public List<QnaVO> getQnaBoardList(QnaVO qnavo) {
		return mybatis.selectList("qnaDAO.getQnaBoardList", qnavo);
	}

	public QnaVO getQnaBoardContent(QnaVO qnavo) {
		return mybatis.selectOne("qnaDAO.getQnaBoardContent", qnavo);
	}

	@Override
	public int updateQna(QnaVO qnavo) {
		return mybatis.update("qnaDAO.updateQna", qnavo);
	}

	public void updateReadcount(QnaVO qnavo) {
		mybatis.update("qnaDAO.updateReadcount", qnavo);
	}

	public void deleteQnaBoard(QnaVO qnavo) {
		mybatis.delete("qnaDAO.deleteQnaBoard", qnavo);
		
	}

	public List<QnaVO> selectKeyword(Map map) {
		return mybatis.selectList("qnaDAO.selectKeyword", map);
		
	}

	public void insertReply(QnaVO qnavo) {
		mybatis.insert("qnaDAO.insertReply", qnavo);
	}

	public QnaVO selectGroupId(QnaVO qnavo) {
		return mybatis.selectOne("qnaDAO.selectGroupId", qnavo);
		
	}

	public List<QnaVO> selectListRe(QnaVO qnavo) {
		return mybatis.selectList("qnaDAO.selectListRe", qnavo);
	}

	public List<QnaVO> selectQnaBoardWithPaging(Map map) {
		return mybatis.selectList("qnaDAO.selectQnaBoardWithPaging", map);
	}

	public List<QnaVO> selectQuestionGroupId(QnaVO qnavo) {
		return mybatis.selectList("qnaDAO.selectQuestionGroupId", qnavo);
	}

	public void deleteQnaBoardbyGroupId(QnaVO qnavo) {
		mybatis.delete("qnaDAO.deleteQnaBoardbyGroupId", qnavo);
		
	}


}

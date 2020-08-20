package com.mycompany.service;

import java.util.List;
import java.util.Map;

import com.mycompany.domain.QnaVO;

public interface QnaService {
	
	public int insertQnaBoard(QnaVO qnavo); 
	public List<QnaVO> getQnaBoardList(QnaVO qnavo);
	public QnaVO getQnaBoardContent(QnaVO qnavo);
	public int updateQna(QnaVO qnavo);
	public void updateReadcount(QnaVO qnavo);
	public void deleteQnaBoard(QnaVO qnavo);
	public List<QnaVO> selectKeyword(Map map);
	public void insertReply(QnaVO qnavo);
	public QnaVO selectGroupId(QnaVO qnavo);
	public List<QnaVO> selectListRe(QnaVO qnavo);
	public List<QnaVO> selectQnaBoardWithPaging(Map map);
	public List<QnaVO> selectQuestionGroupId(QnaVO qnavo);
	public void deleteQnaBoardbyGroupId(QnaVO qnavo);

	
}

package com.mycompany.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycompany.domain.MessageVO;

@Repository("messageDAO")
public class MessageDAOImpl implements MessageDAO{
	@Autowired
	private SqlSessionTemplate mybatis;
	
	@Override
	public List<MessageVO> getMessagePartner(Map searchMap) {
		List<MessageVO> result = mybatis.selectList("messageDAO.getMessagePartner", searchMap);
		return result;
	}

	@Override
	public List<MessageVO> getMessagePartner2(Map searchMap) {
		List<MessageVO> result = mybatis.selectList("messageDAO.getMessagePartner2", searchMap);
		return result;
	}
	@Override
	public void insertMessage(Map searchMap) {
		mybatis.insert("messageDAO.insertMessage", searchMap);
		
	}

	@Override
	public void delMessage(String msgId) {
		mybatis.delete("messageDAO.delMessage", msgId);
		
	}

	@Override
	public MessageVO addMessage(Map searchMap) {
		MessageVO result = mybatis.selectOne("messageDAO.addMessage", searchMap);
		return result;
	}

}

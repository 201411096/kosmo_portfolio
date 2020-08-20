package com.mycompany.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.dao.MessageDAOImpl;
import com.mycompany.domain.MessageVO;

@Service("messegeService")
public class MessageServiceImpl implements MessageService{
	@Autowired
	MessageDAOImpl messageDAO;
	@Override
	public List<MessageVO> getMessagePartner(Map searchMap) {
		List<MessageVO> result = messageDAO.getMessagePartner(searchMap);
		return result;
	
	}
	@Override
	public List<MessageVO> getMessagePartner2(Map searchMap) {
		List<MessageVO> result = messageDAO.getMessagePartner2(searchMap);
		return result;
		
	}
	
	@Override
	public void insertMessage(Map searchMap) {
		messageDAO.insertMessage(searchMap);
		
	}
	@Override
	public void delMessage(String msgId) {
		messageDAO.delMessage(msgId);
		
	}
	@Override
	public MessageVO addMessage(Map searchMap) {
		MessageVO result = messageDAO.addMessage(searchMap);
		return result;
	}

}

package com.mycompany.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.dao.AnimalDAOImpl;
import com.mycompany.domain.AnimalVO;
import com.mycompany.domain.MemberVO;

@Service("AnimalService")
public class AnimalServiceImpl implements AnimalService {

	@Autowired
	AnimalDAOImpl animalDAO;
	
	@Override
	public int animalinsert(AnimalVO vo,MemberVO mvo) {
		
		return animalDAO.animalinsert(vo,mvo);
	}

	@Override
	public List<AnimalVO> animalSelect(MemberVO mvo) {
		
		return animalDAO.animalSelect(mvo);
	}

	@Override
	public int animalDelete(AnimalVO vo) {
		
		return animalDAO.animalDelete(vo);
	}

	@Override
	public int animalUpdate(AnimalVO vo) {
		
		return animalDAO.animalUpdate(vo);
	}

}

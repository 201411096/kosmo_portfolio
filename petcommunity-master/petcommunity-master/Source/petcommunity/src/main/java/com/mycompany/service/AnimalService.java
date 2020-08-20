package com.mycompany.service;

import java.util.List;

import com.mycompany.domain.AnimalVO;
import com.mycompany.domain.MemberVO;

public interface AnimalService {

	public int animalinsert(AnimalVO vo, MemberVO mvo);
	
	public List<AnimalVO> animalSelect(MemberVO mvo);
	
	public int animalDelete(AnimalVO vo);
	
	public int animalUpdate(AnimalVO vo);
}

package com.mycompany.dao;

import java.util.List;

import com.mycompany.domain.AnimalVO;
import com.mycompany.domain.MemberVO;

public interface AnimalDAO {
	public int animalinsert(AnimalVO vo,MemberVO mvo);
	
	public List<AnimalVO> animalSelect(MemberVO mvo);
	
	public int animalDelete(AnimalVO vo);
	
	public int animalUpdate(AnimalVO vo);
}

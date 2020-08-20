package com.mycompany.dao;


import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycompany.domain.AnimalVO;
import com.mycompany.domain.MemberVO;
@Repository("AnimalDAO")
public class AnimalDAOImpl implements AnimalDAO {

	@Autowired
	private SqlSessionTemplate mybatis;
	@Override
	public int animalinsert(AnimalVO vo,MemberVO mvo) {
		int a=mybatis.insert("animal.animalinsert",vo);
		HashMap map = new HashMap();
		map.put("animalId", vo.getAnimalId());
		map.put("memberId", mvo.getMemberId());
		mybatis.insert("animal.raise",map);
		return a; 
		
	}
	@Override
	public List<AnimalVO> animalSelect(MemberVO mvo) {
		return mybatis.selectList("animal.animalselect",mvo);
	}
	@Override
	public int animalDelete(AnimalVO vo) {
		
		return mybatis.delete("animal.animalDelete",vo);
	}
	@Override
	public int animalUpdate(AnimalVO vo) {
		
		return mybatis.update("animal.animalUpdate",vo);
	}
	
	

}

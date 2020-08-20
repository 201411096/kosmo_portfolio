package com.mycompany.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.mycompany.domain.FindHospitalVO;

@Repository("findHospitalDAO")
public class FindHospitalDAOImpl implements FindHospitalDAO{
	
	@Autowired
	private SqlSessionTemplate mybatis;

	@Override
	public List<FindHospitalVO> selectFindHospital(Map map) {
		System.out.println("===>  HospitalMapper selectFindHospital() 호출");
		return mybatis.selectList("findHospitalDAO.getFindHospitalList", map);		
	}

	@Override
	public List<FindHospitalVO> selectFindHospitalWithPaging(Map map) {
		System.out.println("===>  HospitalMapper selectFindHospitalListWithPaging() 호출");
		return mybatis.selectList("findHospitalDAO.getFindHospitalListWithPaging", map);
	}
	
	@Override
	public List<FindHospitalVO> getFindHospitalListByLocation(FindHospitalVO vo) {
		System.out.println("===>  HospitalMapper getFindHospitalListByLocation() 호출");
		return mybatis.selectList("findHospitalDAO.getFindHospitalListByLocation", vo);	
	}

	@Override
	public List<FindHospitalVO> getFindHospitalListByLocationWithPaging(Map map) {
		System.out.println("===>  HospitalMapper getFindHospitalListByLocationWithPaging() 호출");
		return mybatis.selectList("findHospitalDAO.getFindHospitalListByLocationWithPaging", map);
	}
	
	@Override
	public List<FindHospitalVO> getFindHospitalListBySearch(FindHospitalVO vo) {
		System.out.println("===>  HospitalMapper getFindHospitalListBySearch() 호출");
		return mybatis.selectList("findHospitalDAO.getFindHospitalListBySearch", vo);
	}


	@Override
	public List<FindHospitalVO> getFindHospitalListBySearchWithPaging(Map map) {
		System.out.println("===>  HospitalMapper getFindHospitalListBySearchWithPaging() 호출");
		return mybatis.selectList("findHospitalDAO.getFindHospitalListBySearchWithPaging", map);
	}

	@Override
	public List<String> selectString(Map map) {
		System.out.println("===>  HospitalMapper selectString() 호출");
		return mybatis.selectList("findHospitalDAO.selectString", map);
	}
//
//	@Override
//	public List<FindHospitalVO> selectFindHospitalForMap(Map map) {
//		System.out.println("===>  HospitalMapper selectFindHospitalForMap() 호출");
//		return mybatis.selectList("findHospitalDAO.selectFindHospitalForMap", map);
//	}

	public List<Map<String, String>> getGeoLocation() {
		System.out.println("===>  HospitalMapper getGeoLocation() 호출"); 
		return mybatis.selectList("findHospitalDAO.getGeoLocation");
	}


}


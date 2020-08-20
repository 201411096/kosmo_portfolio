package com.mycompany.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mycompany.dao.FindHospitalDAOImpl;
import com.mycompany.domain.FindHospitalVO;


@Service("findHospitalService")
public class FindHospitalServiceImpl implements FindHospitalService{
	
	@Autowired
	FindHospitalDAOImpl findHospitalDAO;

	@Override
	public List<FindHospitalVO> selectFindHospital(Map map) {
		return findHospitalDAO.selectFindHospital(map);
	}

	@Override
	public List<FindHospitalVO> selectFindHospitalWithPaging(Map map) {
		return findHospitalDAO.selectFindHospitalWithPaging(map);
	}

	@Override
	public List<FindHospitalVO> getFindHospitalListByLocation(FindHospitalVO vo) {
		return findHospitalDAO.getFindHospitalListByLocation(vo);
	}

	@Override
	public List<FindHospitalVO> getFindHospitalListByLocationWithPaging(Map map) {
		return findHospitalDAO.getFindHospitalListByLocationWithPaging(map);
	}
	
	@Override
	public List<FindHospitalVO> getFindHospitalListBySearch(FindHospitalVO vo) {
		return findHospitalDAO.getFindHospitalListBySearch(vo);
	}
	
	@Override
	public List<FindHospitalVO> getFindHospitalListBySearchWithPaging(Map map) {
		return findHospitalDAO.getFindHospitalListBySearchWithPaging(map);
	}
	
	@Override
	public List<String> selectString(Map map) {
		return findHospitalDAO.selectString(map);
	}
	
//	@Override
//	public List<FindHospitalVO> selectFindHospitalForMap(Map map) {
//		return findHospitalDAO.selectFindHospitalForMap(map);
//	}

	public List<Map<String, String>> getGeoLocation() {

		return findHospitalDAO.getGeoLocation();
	}
	

}

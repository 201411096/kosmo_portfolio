package com.mycompany.service;

import java.util.List;
import java.util.Map;
import com.mycompany.domain.FindHospitalVO;

public interface FindHospitalService {
	public List<FindHospitalVO> selectFindHospital(Map map);
	public List<FindHospitalVO> selectFindHospitalWithPaging(Map map);
	public List<FindHospitalVO> getFindHospitalListByLocation(FindHospitalVO vo);
	public List<FindHospitalVO> getFindHospitalListByLocationWithPaging(Map map);
	public List<FindHospitalVO> getFindHospitalListBySearch(FindHospitalVO vo);
	public List<FindHospitalVO> getFindHospitalListBySearchWithPaging(Map map);
	public List<String> selectString(Map searchMap);
	//public List<FindHospitalVO> selectFindHospitalForMap(Map map);
	public List<Map<String, String>> getGeoLocation();
}


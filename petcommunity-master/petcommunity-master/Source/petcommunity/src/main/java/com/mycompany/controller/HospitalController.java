package com.mycompany.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sound.sampled.LineListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.mycompany.domain.FindHospitalVO;
import com.mycompany.domain.PaginationVO;
import com.mycompany.service.FindHospitalServiceImpl;

@Controller
public class HospitalController {
	

	@Autowired
	FindHospitalServiceImpl findHospitalService;
	
	//동물 병원 찾기 게시판 첫 화면에 병원 전체 리스트를 보여준다.
	@ResponseBody
	@RequestMapping(value="/findHospitalListWithPaging.do", produces="application/json; charset=utf-8")
	public Map getFindHospitalList(@RequestParam(defaultValue="1") int curPage, String searchWord) {
		System.out.println("동물 병원 찾기 컨트롤러 입장");
		Map result = new HashMap();
		Map searchMap = new HashMap();
	
		searchMap.put("searchWord", searchWord);
	
		List<FindHospitalVO> findHospitalVOList = findHospitalService.selectFindHospital(searchMap);
	
		PaginationVO paginationVO = new PaginationVO(findHospitalVOList.size(), curPage);
		searchMap.put("startRow", paginationVO.getStartIndex()+1);
		searchMap.put("endRow", paginationVO.getStartIndex()+paginationVO.getPageSize());
	
		findHospitalVOList = findHospitalService.selectFindHospitalWithPaging(searchMap);
		
		result.put("pagination", paginationVO);
		result.put("findHospitalVOList", findHospitalVOList);
		result.put("findHospitalVOListSize", findHospitalVOList.size());
		return result;
	}
	
	
	// 지역별로 선택된 동물 병원 리스트 가져오기	
	@ResponseBody
	@RequestMapping("/getFindHospitalListByLocation.do")
	public Map getFindHospitalListByLocation(@RequestParam(defaultValue="1") int curPage, FindHospitalVO vo, HttpServletRequest request, ModelAndView mv ){
		System.out.println("지역별로 검색하기");
		String cityName = request.getParameter("cityName");
		String province = request.getParameter("province");
		System.out.println(cityName);
		vo.setCityName(cityName);
		vo.setProvince(province.substring(0,2));
		
		List<FindHospitalVO> getFindHospitalListByLocation = findHospitalService.getFindHospitalListByLocation(vo);
		Map locationMap = new HashMap();
		PaginationVO paginationVO = new PaginationVO(findHospitalService.getFindHospitalListByLocation(vo).size(), curPage,10);
		
		System.out.println(findHospitalService.getFindHospitalListByLocation(vo).size());
		
		paginationVO.setRangeSize(10);
		locationMap.put("startRow", paginationVO.getStartIndex()+1);
		locationMap.put("endRow", paginationVO.getStartIndex()+paginationVO.getPageSize());
		locationMap.put("cityName", cityName);
		locationMap.put("province", province.substring(0,2));
		
		Map searchMap = null;
		List<FindHospitalVO> getFindHospitalListByLocationWithPaging = findHospitalService.getFindHospitalListByLocationWithPaging(locationMap);
		System.out.println(getFindHospitalListByLocationWithPaging);
		Map result = new HashMap();
		
		result.put("pagination", paginationVO);
		result.put("findHospitalVOList", getFindHospitalListByLocationWithPaging);
		result.put("findHospitalVOListSize", getFindHospitalListByLocationWithPaging.size());
//		result.put("findHospitalListByLocation", getFindHospitalListByLocationWithPaging);
		
		return result;
	}

		
	//검색어, 병원명 typing하여 검색된 list 가져오기
	@ResponseBody
	@RequestMapping("/getFindHospitalListBySearch.do")
	public Map getFindHospitalListBySearch(@RequestParam(defaultValue = "1") int curPage, FindHospitalVO vo, HttpServletRequest request, ModelAndView mv) {
	//값이 전송되지 않으면 기본값을 1로 만든다  : defaultValue=1
		System.out.println("타이핑해서 검색하기");
		// parameter로 넘어온 글번호를 vo에 셋해준후 Mapper로 넘겨줌
		String keyword = request.getParameter("searchWord");
		System.out.println(keyword);
		Map searchMap = new HashMap();

		vo.setKeyWord(keyword);
		
		PaginationVO paginationVO = new PaginationVO(findHospitalService.getFindHospitalListBySearch(vo).size(), curPage, 10);
		System.out.println(findHospitalService.getFindHospitalListBySearch(vo).size());
		paginationVO.setRangeSize(10);
		searchMap.put("startRow", paginationVO.getStartIndex()+1);
		searchMap.put("endRow", paginationVO.getStartIndex()+paginationVO.getPageSize());
		searchMap.put("keyWord", keyword);		
		List<FindHospitalVO> getFindHospitalListBySearch = findHospitalService.getFindHospitalListBySearchWithPaging(searchMap);
		System.out.println(getFindHospitalListBySearch);
		Map result = new HashMap();
		result.put("pagination", paginationVO);
		result.put("findHospitalVOList", getFindHospitalListBySearch);
		result.put("findHospitalVOListSize", getFindHospitalListBySearch.size());

		return result;
	
	}
	
//	
//	@RequestMapping(value = "/autoCompleteForFindHospital.do", method = RequestMethod.GET, produces = "application/text; charset=utf-8")
//	@ResponseBody
//	public String autoCompleteForFindHospital(FindHospitalVO vo, String searchWord) {
//		Map searchMap = new HashMap();
//		searchMap.put("searchWord", searchWord);
//		List<String> stringList = findHospitalService.selectString(searchMap);
//		if(stringList!=null) {
//			String[] array = new String[stringList.size()];
//			for(int i=0; i< stringList.size(); i++) {
//				array[i] = stringList.get(i);					 
//			}
//			Gson gson = new Gson();
//			return gson.toJson(array);
//		}else {
//			return null;
//		}
//	}
//
//	@RequestMapping(value = "/autoCompleteForMap2.do", method = RequestMethod.GET, produces = "application/text; charset=utf-8")
//	@ResponseBody
//	public String autoCompleteForMap2(String searchWord) {
//		Map searchMap = new HashMap();
//		searchMap.put("searchWord", searchWord);
//		List<String> stringList = findHospitalService.selectString(searchMap);
//	
//		if(stringList!=null) {
//			String[] array = new String[stringList.size()];
//			for(int i=0; i< stringList.size(); i++) {
//				array[i] = stringList.get(i);
//			}
//			Gson gson = new Gson();
//			return gson.toJson(array);
//		}
//		return "";
//	}

	@RequestMapping(value="/getGeoLocation", method= {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public List<Map<String, String>> getGeoLocation() {
        
        List<Map<String, String>> container = new ArrayList<>();
        container = findHospitalService.getGeoLocation();
        
        return container;
    }
	
//	@ResponseBody
//	@RequestMapping(value="/findHospitalListWithoutPaging.do", produces="application/json; charset=utf-8")
//	public Map getFindHospitalListWithoutPaging(@RequestParam(defaultValue="") int curPage, String searchWord) {
//		System.out.println("지도 검색 컨트롤러 입장");
//		Map result = new HashMap();
//		Map searchMap = new HashMap();
//	
//		searchMap.put("searchWord", searchWord);
//	
//		List<FindHospitalVO> findHospitalVOList = findHospitalService.selectFindHospitalForMap(searchMap);
//	
//		result.put("findHospitalVOList", findHospitalVOList);
//		result.put("findHospitalVOListSize", findHospitalVOList.size());
//		return result;
//	}
	
}

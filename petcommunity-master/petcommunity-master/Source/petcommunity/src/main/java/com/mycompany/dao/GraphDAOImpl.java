package com.mycompany.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("graphDAO")
public class GraphDAOImpl implements GraphDAO{
	@Autowired
	private SqlSessionTemplate mybatis;

	@Override
	public List<Map> getLostGraph(Map map) {
		return mybatis.selectList("graphDAO.getLostGraph", map);
	}

	@Override
	public List<Map> getFindGraphFromLostBoard(Map map) {
		return mybatis.selectList("graphDAO.getFindGraphFromLostBoard", map);
	}

	@Override
	public List<Map> makeSalesHistoryChartWithGrouping(Map map) {
		return mybatis.selectList("graphDAO.makeSalesHistoryChartWithGrouping", map);
	}
	
	@Override
	public List<Map> makeSalesHistoryChart(Map map) {
		return mybatis.selectList("graphDAO.makeSalesHistoryChart", map);
	}

	@Override
	public List<Map> makeSalesHistoryChartGroupByCategory(Map map) {
		return mybatis.selectList("graphDAO.makeSalesHistoryChartGroupByCategory", map);
	}
	
}

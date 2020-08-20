package com.mycompany.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mycompany.dao.GraphDAOImpl;

@Service("graphService")
public class GraphServiceImpl implements GraphService{
	@Autowired
	GraphDAOImpl graphDAO;

	@Override
	public List<Map> getLostGraph(Map map) {
		return graphDAO.getLostGraph(map);
	}

	@Override
	public List<Map> getFindGraphFromLostBoard(Map map) {
		return graphDAO.getFindGraphFromLostBoard(map);
	}

	@Override
	public List<Map> makeSalesHistoryChartWithGrouping(Map map) {
		return graphDAO.makeSalesHistoryChartWithGrouping(map);
	}

	@Override
	public List<Map> makeSalesHistoryChart(Map map) {
		return graphDAO.makeSalesHistoryChart(map);
	}

	@Override
	public List<Map> makeSalesHistoryChartGroupByCategory(Map map) {
		return graphDAO.makeSalesHistoryChartGroupByCategory(map);
	}
	
}

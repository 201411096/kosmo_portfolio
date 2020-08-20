package com.mycompany.service;

import java.util.List;
import java.util.Map;

public interface GraphService {
	List<Map> getLostGraph(Map map);
	List<Map> getFindGraphFromLostBoard(Map map);
	List<Map> makeSalesHistoryChartWithGrouping(Map map);
	List<Map> makeSalesHistoryChart(Map map);
	List<Map> makeSalesHistoryChartGroupByCategory(Map map);
}

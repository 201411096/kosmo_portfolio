$(function(){
//	Chart.defaults.global.legend.display = false;

	eventHandler();
	dateOptionSetting();
});

function dateOptionSetting(){
	var date1 = new Date().toJSON().slice(0,4)+'-01-01T00:00';
	var date2 = new Date().toJSON().slice(0,4)+'-12-31T23:59';
	$('#graph_04_startDate').val(date1);
	$('#graph_04_endDate').val(date2);
	$('#graph_05_startDate').val(date1);
	$('#graph_05_endDate').val(date2);
}

function eventHandler(){
	$('ul.tabs > li.tab-link').on('click', function(){
		makeGraph_01();
		makeGraph_02();
		makeGraph_03();
		makeGraph_04();
		makeGraph_05();
	});
}

function makeGraph_01(){
	//게시글 수 (실종동물 요일별, 시간별 통계)
	var graph_01 = new GraphClass("/petcommunity/lostGraph.do", "graph_01_container", "graph_01", "");
	graph_01.inputData.chartType=$('#graph_01_chartShape_option').val();
	graph_01.inputData.timeOption=$('#graph_01_chartTime_option').val();
	GraphClass.makeChartWithAjax(graph_01);

	$('#graph_01_chartShape_option').on('change', function(){
		graph_01.inputData.chartType=$('#graph_01_chartShape_option').val();
		GraphClass.makeChartWithAjax(graph_01)
	});
	$('#graph_01_chartTime_option').on('change', function(){
		graph_01.inputData.timeOption=$('#graph_01_chartTime_option').val();	
		GraphClass.makeChartWithAjax(graph_01)
	});
	
}

function makeGraph_02(){
	//게시글 수 (찾은 실종동물 요일별, 시간별 통계)
	var graph_02 = new GraphClass("/petcommunity/findGraphFromLostBoard.do", "graph_02_container", "graph_02", "");
	graph_02.inputData.chartType=$('#graph_02_chartShape_option').val();
	graph_02.inputData.timeOption=$('#graph_02_chartTime_option').val();
	GraphClass.makeChartWithAjax(graph_02);

	$('#graph_02_chartShape_option').on('change', function(){
		graph_02.inputData.chartType=$('#graph_02_chartShape_option').val();
		GraphClass.makeChartWithAjax(graph_02)
	});
	$('#graph_02_chartTime_option').on('change', function(){
		graph_02.inputData.timeOption=$('#graph_02_chartTime_option').val();	
		GraphClass.makeChartWithAjax(graph_02)
	});	
}

function makeGraph_03(){
	//매출 통계 ( 요일별, 시간별 통계)
	var graph_03 = new GraphClass("/petcommunity/makeSalesHistoryChartWithGrouping.do", "graph_03_container", "graph_03", "");
	graph_03.inputData.chartType=$('#graph_03_chartShape_option').val();
	graph_03.inputData.timeOption=$('#graph_03_chartTime_option').val();
	GraphClass.makeChartWithAjax(graph_03);

	$('#graph_03_chartShape_option').on('change', function(){
		graph_03.inputData.chartType=$('#graph_03_chartShape_option').val();
		GraphClass.makeChartWithAjax(graph_03)
	});
	$('#graph_03_chartTime_option').on('change', function(){
		graph_03.inputData.timeOption=$('#graph_03_chartTime_option').val();	
		GraphClass.makeChartWithAjax(graph_03)
	});
}
// 매출 통계 그래프
function makeGraph_04(){
	var graph_04 = new GraphClass("/petcommunity/makeSalesHistoryChart.do", "graph_04_container", "graph_04", "");
	graph_04.inputData.chartType=$('#graph_04_chartShape_option').val();
	graph_04.inputData.timeOption=$('#graph_04_chartTime_option').val();
	graph_04.inputData.startDate=$('#graph_04_startDate').val();
	graph_04.inputData.endDate=$('#graph_04_endDate').val();
	GraphClass.makeChartWithAjax(graph_04); // 그래프 생성
	$('#graph_04_chartShape_option').on('change', function(){
		graph_04.inputData.chartType=$('#graph_04_chartShape_option').val();
		GraphClass.makeChartWithAjax(graph_04)
	});
	$('#graph_04_chartTime_option').on('change', function(){
		graph_04.inputData.timeOption=$('#graph_04_chartTime_option').val();	
		GraphClass.makeChartWithAjax(graph_04)
	});
	$('#graph_04_startDate').on('change', function(){
		graph_04.inputData.startDate=$('#graph_04_startDate').val();	
		GraphClass.makeChartWithAjax(graph_04)
	});
	$('#graph_04_endDate').on('change', function(){
		graph_04.inputData.endDate=$('#graph_04_endDate').val();	
		GraphClass.makeChartWithAjax(graph_04)
	});
}

function makeGraph_05(){
	//매출 통계 (품목별)
	var graph_05 = new GraphClass("/petcommunity/makeSalesHistoryChartGroupByCategory.do", "graph_05_container", "graph_05", "");
	graph_05.inputData.chartType=$('#graph_05_chartShape_option').val();
	graph_05.inputData.startDate=$('#graph_05_startDate').val();
	graph_05.inputData.endDate=$('#graph_05_endDate').val();
	GraphClass.makeChartWithAjax(graph_05);

	$('#graph_05_chartShape_option').on('change', function(){
		graph_05.inputData.chartType=$('#graph_05_chartShape_option').val();
		GraphClass.makeChartWithAjax(graph_05)
	});
	$('#graph_05_startDate').on('change', function(){
		graph_05.inputData.startDate=$('#graph_05_startDate').val();	
		GraphClass.makeChartWithAjax(graph_05)
	});
	$('#graph_05_endDate').on('change', function(){
		graph_05.inputData.endDate=$('#graph_05_endDate').val();	
		GraphClass.makeChartWithAjax(graph_05)
	});
}
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>
	<hr>
	실종동물 요일, 시간별 통계 (group by)
	<div id="graph_01_outer_container">
		<div id="graph_01_option_container">
			<select id="graph_01_chartShape_option" class="form-control">
				<option selected value="bar">막대</option>
				<option value="pie">원형</option>
				<option value="line">선형</option>
				<option value="doughnut">도넛</option>
				<option value="polarArea">폴라</option>
			</select>
			<select id="graph_01_chartTime_option" class="form-control">
				<option selected value="0">시간</option>
				<option value="1">요일</option>
				<option value="2">월</option>
				<option value="3">년도</option>
			</select>
		</div>
		<div id="graph_01_container"></div>
	</div>
	<hr>
	찾은동물 요일, 시간별 통계 (group by)
	<div id="graph_02_outer_container">
		<div id="graph_02_option_container">
			<span>
				<select id="graph_02_chartShape_option" class="form-control">
					<option selected value="bar">막대</option>
					<option value="pie">원형</option>
					<option value="line">선형</option>
					<option value="doughnut">도넛</option>
					<option value="polarArea">폴라</option>
				</select>
			</span>
			<span>
				<select id="graph_02_chartTime_option" class="form-control">
					<option selected value="0">시간</option>
					<option value="1">요일</option>
					<option value="2">월</option>
					<option value="3">년도</option>
				</select>
			</span>
		</div>
		<div id="graph_02_container"></div>
	</div>
	<hr>
	매출 통계 (group by)
	<div id="graph_03_outer_container">
		<div id="graph_03_option_container">
			<span>
				<select id="graph_03_chartShape_option" class="form-control">
					<option selected value="bar">막대</option>
					<option value="pie">원형</option>
					<option value="line">선형</option>
					<option value="doughnut">도넛</option>
					<option value="polarArea">폴라</option>
				</select>
			</span>
			<span>
				<select id="graph_03_chartTime_option" class="form-control">
					<option selected value="0">시간</option>
					<option value="1">요일</option>
					<option value="2">월</option>
				</select>
			</span>
		</div>
		<div id="graph_03_container"></div>
	</div>
	매출 통계 (grouping x)
	<div id="graph_04_outer_container">
		<div id="graph_04_option_container">
			<span>
				<select id="graph_04_chartShape_option" class="form-control">
					<option value="bar">막대</option>
					<option value="pie">원형</option>
					<option selected value="line">선형</option>
					<option value="doughnut">도넛</option>
					<option value="polarArea">폴라</option>
				</select>
			</span>
			<span>
				<select id="graph_04_chartTime_option" class="form-control">
					<option value="0">초</option>
					<option value="1">분</option>
					<option value="2">시</option>
					<option selected value="3">일</option>
					<option value="4">월</option>
					<option value="5">년</option>
				</select>
			</span>
			<span>
				<input type="datetime-local" id="graph_04_startDate" class="form-control">
			</span>
			<span>
				<input type="datetime-local" id="graph_04_endDate" class="form-control">
			</span>
		</div>
		<div id="graph_04_container"></div>
	</div>
	매출 통계 (품목별)
	<div id="graph_05_outer_container">
		<div id="graph_05_option_container">
			<span>
				<select id="graph_05_chartShape_option" class="form-control">
					<option selected value="bar">막대</option>
					<option value="pie">원형</option>
					<option value="line">선형</option>
					<option value="doughnut">도넛</option>
					<option value="polarArea">폴라</option>
				</select>
			</span>
			<span>
				<input type="datetime-local" id="graph_05_startDate" class="form-control">
			</span>
			<span>
				<input type="datetime-local" id="graph_05_endDate" class="form-control">
			</span>
		</div>
		<div id="graph_05_container"></div>
	</div>	
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
<script src="resources/js/util/module_graph.js"></script>
<script src="resources/js/sample_graph.js"></script>
</body>
</html>
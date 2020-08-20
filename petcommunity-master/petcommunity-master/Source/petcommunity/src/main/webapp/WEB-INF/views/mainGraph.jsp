<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="./resources/css/mainGraph.css" />
</head>
<body>
	<div id="mainGraph1">
	<p>견종별 유기현황</p>
	<canvas id="mainGraph1_1" width="400" height="200"></canvas>
	</div>
	<div id="mainGraph2">
	<p>지역별 유기현황</p>
	<canvas id="mainGraph2_1" width="400" height="200"></canvas>
	</div>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/socket.io/2.3.0/socket.io.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
	<script
		src="./resources/bootstrap_template/template_01/js/jquery-3.2.1.min.js"></script>
	<script src="./resources/js/util/module_socket.js"></script>

	<script>
	var chartOptions = {
            maintainAspectRatio : false // 부모가 만든 크기 안에 꽉 차게 함(default 값: true)
         };
		var mainPublicData;
		var arkindCd = new Array();
		var arorgNm = new Array();
		var setKindCd;
		var setOrgNm;
		var yDateKindCd = new Array();
		var yDateOrgNm = new Array();
		getPublicData();
		waitingPublicData();
		function waitingPublicData() {
			socket.on('getMainPublicData', function(data) {
				mainPublicData = data;
			
				for (var i = 0; i < mainPublicData.length; i++) {
					arkindCd.push(mainPublicData[i].kindCd);
					arorgNm.push(mainPublicData[i].orgNm);	
				}

				var result1 = arkindCd.reduce((a, c) => (a[c] = (a[c] || 0) + 1, a), Object.create(null));
				var result2 = arorgNm.reduce((a, c) => (a[c] = (a[c] || 0) + 1, a), Object.create(null));

 				var items1 = Object.keys(result1).map(function(key) {
 					  return [key, result1[key]];
 					});

 					// Sort the array based on the second element
 				items1.sort(function(first, second) {
 				  return second[1] - first[1];
 				});
 				console.log(items1);
 				var items2 = Object.keys(result2).map(function(key) {
					  return [key, result2[key]];
					});

					// Sort the array based on the second element
				items2.sort(function(first, second) {
				  return second[1] - first[1];
				});
				
				var graph1_x = new Array();
				var graph1_y = new Array();
				
				var graph2_x = new Array();
				var graph2_y = new Array();

				for(var j=2;j<12;j++){
					graph1_x.push(items1[j][0]);
					graph1_y.push(items1[j][1]);
					}

				for(var j=0;j<10;j++){
					graph2_x.push(items2[j][0]);
					graph2_y.push(items2[j][1]);
					}
				var chartData1= makeChartData(graph1_x,graph1_y, "그래프1");
				var chartData2= makeChartData(graph2_x,graph2_y, "그래프2");
				makeChart(chartData1, chartOptions, "mainGraph1_1");
				makeChart(chartData2, chartOptions, "mainGraph2_1");
				});
		}
		function getPublicData() {
			console.log('getPublicData ...');
			var dataOptions = new Object();
			socket.emit('getMainPublicData', dataOptions);
		}

		function makeChart(chartData, chartOptions, chartContainer){
			   var ctx = document.getElementById(chartContainer).getContext('2d');
			   var myChart = new Chart(ctx, {
			      type : 'bar',
			      data : chartData,
			      options : chartOptions
			   });   
			}   
		function makeChartData(dataX, dataY, labelName){ 
			  var chartData = {
			                  labels : dataX,
			                  datasets : [
			                     {
			                        label : labelName,
			                        data : dataY,
			                        backgroundColor : [
			                           "#D26E6E",
			                           "#FAC6C6",
			                           "#F4A460",
			                           "#AAFA82",
			                           "#AFFFEE",
			                           "#A7EEFF",
			                           "#aaaaaa",
			                           "#A390EE",
			                           "#20a0ff",
			                        ],
			                     }
			                  ]
			               
			                  }
			   return chartData;
			}
	</script>
	
</body>
</html>
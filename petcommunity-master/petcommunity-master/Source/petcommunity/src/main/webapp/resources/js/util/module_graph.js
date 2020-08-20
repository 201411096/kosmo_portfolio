	Chart.defaults.global.responsive = true;
	Chart.defaults.global.maintainAspectRatio = false;
class GraphClass{
	constructor(url, chartContainerId, chartElementId, labelName){
		this.url=url;								// graph 생성시 ajax로 데이터를 받아올 controller url
		this.chartContainerId=chartContainerId;		// chart를 담을 element의 id
		this.chartElementId=chartElementId;			// chart의 element id
		this.inputData= {};							// 차트를 그리는데 사용할 값들을 담을 객체(ajax를 이용해서 controller로 보낼 객체)
		this.chartData;								// 차트 데이터를 구성할 데이터
		this.labelName=labelName;
		this.chartOptions={
				maintainAspectRatio : false //부모가 만든 크기 안에 꽉 차게 함(default 값: true)
		}
		this.chartType = 'line';					// 차트의 기본 형태
		this.mainColor = "rgba(75,192,192,1)"; 		// 선형 그래프일 경우 사용할 메인 색
		this.subColor = "rgba(75,192,192,0.4)"; 	// 선형 그래프일 경우 사용할 서브 색
	}
	
//차트의 데이터를 받아와서 차트를 그려주는 함수
// ㄴ 내부적으로 받아온 데이터를 가공해서 차트의 데이터로 만들어주는 함수와 차트를 그려주는 함수를 호출함
	static makeChartWithAjax(graphClass){
		$('#'+graphClass.chartContainerId).empty();
		$('#'+graphClass.chartContainerId).append('<canvas id="'+graphClass.chartElementId+'"></canvas>');
		$.ajax({
		      type:'post',
		      async:true,
		      url:graphClass.url,
		      contentType : "application/json; charset=UTF-8",
		      data : JSON.stringify(graphClass.inputData),
		      dataType : 'json',
		      success : function(outputData){
		    	  graphClass.chartType= outputData.chartType; // controller단에서 차트 모양을 정해줌(inputData 객체를 통해서 결정)
		    	  graphClass.chartData= GraphClass.makeChartData(graphClass, outputData); // 차트데이터를 구성
		    	  GraphClass.makeChart(graphClass, graphClass.chartData, graphClass.chartOptions); // 차트를 그림		    	  
		      },
		      error:function(request,status,error){
		         console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		      }
		   });
	}
//ajax에서 받아온 json 데이터를 가공하여 차트의 데이터로 만들어주는 함수
	static makeChartData(graphClass, outputData){
		var dataLabels = new Array();	// 컨트롤러 단에서 받은 데이터라벨
		var chartJsData = new Array(); // 컨트롤러 단에서 받은 데이터
		
		if(graphClass.chartType!=='line'){ // 선형그래프가 아니라면 그래프 색깔 생성
			graphClass.mainColor = GraphClass.mainColorGenerator(outputData.dataSize);
		}else if(graphClass.chartType==='line'){
			graphClass.mainColor = "rgba(75,192,192,1)";
		}		
		for(var i=0; i< outputData.dataSize; i++){
			dataLabels.push(outputData.data[i].name);
			chartJsData.push(outputData.data[i].value);
		}
		var chartData ={
			      labels : dataLabels,	//컨트롤러 단에서 받은 데이터라벨로 라벨 설정
			      datasets : [
			         {
			            label : graphClass.labelName,
			            fill : false,
			            lineTension : 0.1,
			            backgroundColor : graphClass.mainColor,
			            borderColor : graphClass.mainColor,
			            borderCapStyle : 'butt',
			            borderJoinStyle : 'miter',
			            pointBorderColor : graphClass.mainColor,
			            pointBackgroundColor : '#fff',
			            pointBorderWitdh : 2,
			            pointRadius : 1,
			            pointHitRadius : 10,
			            data : chartJsData,	// 컨트롤러 단에서 받은 데이터로 데이터 설정
			            spanGaps : false
			         },
			      ]
			   };
	   return chartData;
	}
//chart를 그리는 함수
	static makeChart(graphClass, chartData, chartOptions){
		   var ctx = document.getElementById(graphClass.chartElementId).getContext('2d');
		   var myChart = new Chart(ctx, {
			  type : graphClass.chartType,
		      data : chartData,
		      options : chartOptions
		   });
	}
//차트의 색깔을 구성해주는 함수
//		ㄴ 차트의 x축의 개수(데이터 개수)만큼 색깔을 만들어서 반환함
//		ㄴ makeChartData(chartData를 구성하는 함수)에서 불려짐
	static mainColorGenerator(size){
		var resultColor;
		var rRanNum =  Math.floor(Math.random()*255);
		var gRanNum =  Math.floor(Math.random()*255);
		var bRanNum =  Math.floor(Math.random()*255);
		
		if(size>1){
			resultColor = new Array();
			for(var i=0; i<size; i++){
				rRanNum =  Math.floor(Math.random()*255);
				gRanNum =  Math.floor(Math.random()*255);
				bRanNum =  Math.floor(Math.random()*255);
				var temp = 'rgba(' +rRanNum + ', ' + gRanNum + ', ' + bRanNum + ')';
				resultColor.push(temp);
			}
		}else if(size==1){
			resultColor = 'rgba(' +rRanNum + ', ' + gRanNum + ', ' + bRanNum + ')';
		}
		
		return resultColor
	}
}
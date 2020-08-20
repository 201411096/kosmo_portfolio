<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.marker-infowindow{
   width:300px;
   height:250px;
   text-align:center;
   border-radius:10px;
   border-collapse: separate;
}
img.infoWindowImage{
	width:100%;
	height:100px;
}
div.infoWindowImageContainer{
	max-width:50%;
	margin:auto;
}
</style>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
</head>
<body>
<button class="btn btn-secondary">버튼</button>
<div class="publicDataOptionContainer">
	<input type="date" class="form-control" id="publicDataStartDateOption">
	<input type="date" class="form-control" id="publicDataEndDateOption">
	<button id="drawPublicDataMarkerBtn" class="btn btn-secondary">마커 생성</button>
</div>
<div id="map" style="width:100%;height:600px;"></div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/socket.io/2.3.0/socket.io.js"></script>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=41ccd37d4644ab2ed5ed67441dda1abb&libraries=services"></script>
<script type="text/javascript">
//var socket = io("https://192.168.0.18:3000");
//var socket = io("https://121.171.119.57:3000");
var contextPath = getContextPath();
//var socket = io("https://115.91.88.227:60005");
var socket = io('https://192.168.0.24:3000');
var publicDataFromAPI;
var map;
drawMap();
waitingPublicData();
getPublicData();
publicDataMarkerEventListener();

function publicDataMarkerEventListener(){
	$('#drawPublicDataMarkerBtn').on('click', function(){
		console.log('button event ...');
		getPublicData();
	});
}

function drawMap(){
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = { 
        center: new kakao.maps.LatLng(37.519972628243366, 126.85287648507145), // 지도의 중심좌표
        level: 12 // 지도의 확대 레벨
    };

	map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
}

function waitingPublicData(){
// 	console.log('waitingPublicData 함수 확인 ... ');
	socket.on('getPublicData', function(data){
		publicDataFromAPI = data;
// 		console.log('socket.on ... waitingPublicData 이벤트 확인 ... 데이터 길이 : ' + data.length);
 		drawMarker(map);
	});
}

function getPublicData(){
	var dataOptions = new Object();
	dataOptions.dataCnt = '1000';
	dataOptions.crawlingOption = '0'; //0이면 크롤링 안함
	if($('#publicDataStartDateOption').val()==''){
		dataOptions.startDate = '20200101';
		console.log('startDate 안들어옴... ');
		console.log(dataOptions.startDate);
	}else{
		console.log('startDate 들어옴... ');
		dataOptions.startDate = $('#publicDataStartDateOption').val();
		console.log(dataOptions.startDate);		
	}
	if($('#publicDataEndDateOption').val()==''){
		dataOptions.endDate = '20201231';
		console.log('endDate 안들어옴... ');
		console.log(dataOptions.endDate);	
	}else{
		console.log('endDate 들어옴... ');
		dataOptions.endDate = $('#publicDataEndDateOption').val();
		console.log(dataOptions.endDate);		
	}
	socket.emit('getPublicData', dataOptions);
}
function drawMarker(){
	for(var i=0; i<publicDataFromAPI.length; i++){
		var iwContent = '<div class="marker-infowindow">'+
						 '<div class="form-group">품종 : '+publicDataFromAPI[i].kindCd+'</div>'+
						 '<div class="form-group">발견 장소 : '+publicDataFromAPI[i].happenPlace+'</div>'+
						 '</div>'; 
        if(publicDataFromAPI[i].x == 0 ) continue;
        
        var markerPosition = new kakao.maps.LatLng(publicDataFromAPI[i].y, publicDataFromAPI[i].x); // latitude 가 y, longitude가 x
        var imageSrc = contextPath + '/resources/imgs/marker/green.png', // 마커이미지의 주소입니다    
	    imageSize = new kakao.maps.Size(35, 35), // 마커이미지의 크기입니다
	    imageOption = {offset: new kakao.maps.Point(27, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
		var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);
        var marker = new kakao.maps.Marker({
            position: markerPosition
            ,image: markerImage // 마커이미지 설정
        });
		marker.setMap(map);
		marker.setRange(1000);
        var infowindow = new kakao.maps.InfoWindow({
	        content : iwContent
        });
        kakao.maps.event.addListener(marker, 'click', makeClickListener(map, marker, infowindow));
	}
}
function makeClickListener(map, marker, infowindow) {
	var status = 0;
	return function() {
		if(status==0){
			infowindow.open(map, marker);
			status=1;
		}else{
			infowindow.close(map, marker);
			status=0;
		}			
	};
}
function getContextPath() {
	   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}
</script>
</body>
</html>
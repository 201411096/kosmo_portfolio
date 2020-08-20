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
<div class="publicDataOptionContainer">
	<input type="date" class="form-control" id="publicDataStartDateOption">
	<input type="date" class="form-control" id="publicDataEndDateOption">
	<button id="drawPublicDataMarkerBtn" class="btn btn-secondary">마커 생성</button>
</div>
<div class="form-group">
	<label>마커 표시 시간</label>
	<select id="timeForSearch" class="form-control">
	<option value="0">전체</option>
	<option value="1">30분</option>
	<option value="2">1시간</option>
	<option value="3">3시간</option>
	<option value="4">12시간</option>
	<option value="5">하루</option>
	<option value="6">일주일</option>
	<option value="7">한달</option>
	</select>
</div>
<div class="form-group">
	<label>마커 표시 지역 검색</label>
	<span id="searchForMap-container">
		<input id="locationForSearch" type="text" class="form-control">
	</span>
</div>
<div id="map" style="width:100%;height:600px;"></div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/socket.io/2.3.0/socket.io.js"></script>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=41ccd37d4644ab2ed5ed67441dda1abb&libraries=services"></script>
<script type="text/javascript">
var contextPath = getContextPath();
// var socket = io("https://192.168.0.18:3000");
var socket = io("https://121.171.119.57:3000");
var publicDataFromAPI;
var mapDataFromDB;
var map;
var latitude = -1;
var longitude = -1;
// drawMap();

// new Promise(function(resolve, reject){
// 	getLocation();
// 	console.log('geolocation...');
// }).then(function(kakaoMapGenerate){
// 	kakaoMapGenerate();
// 	console.log('kakaomapgenerate...');
// }).then(function(getDataWithoutPaging){
// 	getDataWithoutPaging();
// 	console.log('getdatadb...');
// }).then(function(kakaoMapAPI){
// 	kakaoMapAPI();
// 	console.log('drawmarker...');
// })
waitingPublicData();
getPublicData();
publicDataMarkerEventListener();


// initPromiseFunc().then(function(){
// 	getLocation();
// }).then(kakaoMapGenerate)

// function initPromiseFunc(){
// 	return new Promise(function(resolve, reject){
// 		resolve();
// 	});
// }

// function mapPromiseFunc(){	
// }
function initializeFUnc(){
	
}

function publicDataMarkerEventListener(){
	$('#drawPublicDataMarkerBtn').on('click', function(){
		console.log('button event ...');
		getPublicData();
	});
}
// function drawMap(){
// 	var mapContainer = document.getElementById('map'), // 지도를 표시할 div
//     mapOption = { 
//         center: new kakao.maps.LatLng(37.519972628243366, 126.85287648507145), // 지도의 중심좌표
//         level: 12 // 지도의 확대 레벨
//     };

// 	map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
// }
// getPublicData 함수를 실행시키면 nodejs서버에 의해서 보내지는 데이터에 의해서 마커를 데이터를 세팅하고 마커를 그림
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
	dataOptions.dataCnt = '100';
	if($('#publicDataStartDateOption').val()==''){
		dataOptions.startDate = '20200101';
	}else{
		dataOptions.startDate = $('#publicDataStartDateOption').val();		
	}
	if($('#publicDataEndDateOption').val()==''){
		dataOptions.endDate = '20201231';
	}else{
		dataOptions.endDate = $('#publicDataEndDateOption').val();		
	}
	socket.emit('getPublicData', dataOptions);
}
function getLocation() {
    if (navigator.geolocation) {	// GPS를 지원하면
      navigator.geolocation.getCurrentPosition(function(position) {
        latitude = position.coords.latitude;
        longitude = position.coords.longitude;
        console.log('geolocation success--------------------------');
    	console.log(latitude);
    	console.log(longitude);
      }, function(error) {    	  	// 좌표를 못 가져오는 경우에 실행되는 부분
        latitude = 37.519972628243366;
        longitude = 126.85287648507145;
      }, {
        enableHighAccuracy: false,
        maximumAge: 0,
        timeout: Infinity
      });
    } else {
      //alert('GPS를 지원하지 않습니다');
    	console.log('GPS를 지원하지 않습니다');
    }
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
function kakaoMapGenerate(){
	$('#map').empty();
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = { 
        center: new kakao.maps.LatLng(latitude, longitude), // 지도의 중심좌표
        level: 7 // 지도의 확대 레벨
    };
	map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
}
function kakaoMapAPI(data){
// 	$('#map').empty();
// 	console.log($('#locationForSearch').val());
// 	var mapContainer = document.getElementById('map'), // 지도를 표시할 div
//     mapOption = { 
//         center: new kakao.maps.LatLng(latitude, longitude), // 지도의 중심좌표
//         level: 7 // 지도의 확대 레벨
//     };

// 	map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
		
	for(var i=0; i<data.lostBoardVOListSize; i++){
		var position =  new kakao.maps.LatLng(data.lostBoardVOList[i].lostboardX, data.lostBoardVOList[i].lostboardY);
		
		var imageSrc = contextPath + '/resources/imgs/marker/red.png', // 마커이미지의 주소입니다    
	    imageSize = new kakao.maps.Size(50, 50), // 마커이미지의 크기입니다
	    imageOption = {offset: new kakao.maps.Point(27, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
		var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);
		
		var marker = new kakao.maps.Marker({
			  position: position
			  ,image: markerImage // 마커이미지 설정
			});
		marker.setMap(map);
		marker.setRange(1000);
		var iwContent = '<div class="marker-infowindow">'+
						'<div class="form-group">'+data.lostBoardVOList[i].lostboardTitle+'</div>'+
						'<div class="form-group">'+data.lostBoardVOList[i].lostboardLocation+'</div>'+
//						'<a href=/petcommunity/getLostBoard.do?lostboardId='+data.lostBoardVOList[i].lostboardId+'>'+'게시글 보러가기'+'</a>'+
						'<div class="form-group">'+
						'<a class="btn btn-sm btn-secondary" href=/petcommunity/getLostBoard.do?lostboardId='+data.lostBoardVOList[i].lostboardId+'>'+'게시글 보러가기'+'</a>'+
						'<a class="btn btn-sm btn-secondary" href="https://map.kakao.com/link/roadview/'+data.lostBoardVOList[i].lostboardX+','+data.lostBoardVOList[i].lostboardY+'">'+'로드뷰'+'</a>'+
						'</div>'+
						'<div class="infoWindowImageContainer">'+'<img src="'+contextPath+'/resources/imgs/lostboard/'+data.lostBoardVOList[i].lostboardId+'/'+data.lostBoardFileList[i].filename +'" class="infoWindowImage" alt="이미지가 존재하지 않습니다.">'+'</div>'+
						'</div>';
		if(data.lostBoardFileList[i].filename=='??'){
			iwContent  = '<div class="marker-infowindow">'+
						'<div class="form-group">'+data.lostBoardVOList[i].lostboardTitle+'</div>'+
						'<div class="form-group">'+data.lostBoardVOList[i].lostboardLocation+'</div>'+
//						'<a href=/petcommunity/getLostBoard.do?lostboardId='+data.lostBoardVOList[i].lostboardId+'>'+'게시글 보러가기'+'</a>'+
						'<div class="form-group">'+
						'<a class="btn btn-sm btn-secondary" href=/petcommunity/getLostBoard.do?lostboardId='+data.lostBoardVOList[i].lostboardId+'>'+'게시글 보러가기'+'</a>'+
						'<a class="btn btn-sm btn-secondary" href="https://map.kakao.com/link/roadview/'+data.lostBoardVOList[i].lostboardX+','+data.lostBoardVOList[i].lostboardY+'">'+'로드뷰'+'</a>'+
						'</div>'+
						'<div class="infoWindowImageContainer">'+'<img src="'+contextPath+'/resources/imgs/lostboard/default/1.png" class="infoWindowImage" alt="이미지가 존재하지 않습니다.">'+'</div>'+
						'</div>';
		}
		var infowindow = new kakao.maps.InfoWindow({
		    content : iwContent
		});
		kakao.maps.event.addListener(marker, 'click', makeClickListener(map, marker, infowindow));
	}
	for(var i=0; i<data.findBoardVOListSize; i++){
		var position =  new kakao.maps.LatLng(data.findBoardVOList[i].findboardX, data.findBoardVOList[i].findboardY);
		
		var imageSrc = contextPath + '/resources/imgs/marker/blue.png', // 마커이미지의 주소입니다    
	    imageSize = new kakao.maps.Size(50, 50), // 마커이미지의 크기입니다
	    imageOption = {offset: new kakao.maps.Point(27, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
		var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);
		
		var marker = new kakao.maps.Marker({
			  position: position
			  ,image: markerImage // 마커이미지 설정
			});
		marker.setMap(map);
		marker.setRange(1000);
		var iwContent = '<div class="marker-infowindow">'+
						'<div class="form-group">'+data.findBoardVOList[i].findboardTitle+'</div>'+
						'<div class="form-group">'+data.findBoardVOList[i].findboardLocation+'</div>'+
//						'<a href=/petcommunity/getFindBoard.do?findboardId='+data.findBoardVOList[i].findboardId+'>'+'게시글 보러가기'+'</a>'+
						'<div class="form-group">'+
						'<a class="btn btn-sm btn-secondary" href=/petcommunity/getFindBoard.do?findboardId='+data.findBoardVOList[i].findboardId+'>'+'게시글 보러가기'+'</a>'+
						'<a class="btn btn-sm btn-secondary" href="https://map.kakao.com/link/roadview/'+data.findBoardVOList[i].findboardX+','+data.findBoardVOList[i].findboardY+'">'+'로드뷰'+'</a>'+
						'</div>'+
						'<div class="infoWindowImageContainer">'+'<img src="'+contextPath+'/resources/imgs/findboard/'+data.findBoardVOList[i].findboardId+'/'+data.findBoardFileList[i].filename +'" class="infoWindowImage" alt="이미지가 존재하지 않습니다.">'+'</div>'+
						'</div>';
		if(data.findBoardFileList[i].filename=='??'){
			iwContent = '<div class="marker-infowindow">'+
						'<div class="form-group">'+data.findBoardVOList[i].findboardTitle+'</div>'+
						'<div class="form-group">'+data.findBoardVOList[i].findboardLocation+'</div>'+
//						'<a href=/petcommunity/getFindBoard.do?findboardId='+data.findBoardVOList[i].findboardId+'>'+'게시글 보러가기'+'</a>'+
						'<div class="form-group">'+
						'<a class="btn btn-sm btn-secondary" href=/petcommunity/getFindBoard.do?findboardId='+data.findBoardVOList[i].findboardId+'>'+'게시글 보러가기'+'</a>'+
						'<a class="btn btn-sm btn-secondary" href="https://map.kakao.com/link/roadview/'+data.findBoardVOList[i].findboardX+','+data.findBoardVOList[i].findboardY+'">'+'로드뷰'+'</a>'+
						'</div>'+
						'<div class="infoWindowImageContainer">'+'<img src="'+contextPath+'/resources/imgs/findboard/default/1.png" class="infoWindowImage" alt="이미지가 존재하지 않습니다.">'+'</div>'+
						'</div>';
		}
		var infowindow = new kakao.maps.InfoWindow({
		    content : iwContent
		});
		kakao.maps.event.addListener(marker, 'click', makeClickListener(map, marker, infowindow));
	}
}
function getDataWithoutPaging(){
	$.ajax({
		type : 'post',
		async:true,
		url : '/petcommunity/lostboardListWithoutPaging.do',
		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
		data : {
			"locationForSearch" : $('#locationForSearch').val(),
			"timeForSearch" : $('#timeForSearch').val()
		},
		dataType : 'json',
		success : function(resultData){
			//kakaoMapAPI(resultData);
			mapDataFromDB = resultData;
		},
		error:function(request,status,error){
			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}		
	});
}
</script>
</body>
</html>
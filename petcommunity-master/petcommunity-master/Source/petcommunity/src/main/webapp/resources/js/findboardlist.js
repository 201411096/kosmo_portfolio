var curPage;			// 현재페이지
var latitude = -1; 		// 위도
var longitude = -1;		// 경도
var contextPath = getContextPath();
var publicDataFromAPI;  // 공공데이터포털 api에서 받아올 데이터
var map;			    // 지도 객체를 담을 변수 
var defaultOpts = {		// twbs-pagination plugin에서 사용할 페이징 옵션
	visiblePages : 10,
    onPageClick: function (event, page) { 			// page클릭시 이벤트
        $('#page-content').text('Page ' + page); 
        curPage=page;				  				// 현재 페이지  
        console.log('clickevent확인'); 				// 생성하는 순간 첫 페이지를 클릭 확인
        getDataInPaging(); 			  				// 페이지 클릭시 해당 페이지에 해당하는 게시글들을 가져오는 함수
    }
};

$(function(){
	getData();										// 검색 옵션에 맞춰 최대 게시글 수를 가져와서 페이징 구성
	getLocation();									// 현재 위치를 가져와서 전역 변수에 담는 함수
	getMarkerDataAndSetMarkers();					// 마커를 지도에 표시
	autoCompleteFunc();								// 게시글 검색어 자동완성 이벤트 연결
	documentPreventKeyDown();						// 페이지 enterkey 기본 이벤트 방지
	searchWordEventHandler();						// 검색어 enterkey 이벤트 연결
	searchBtnEventHandler();						// 검색 버튼 클릭 이벤트 연결
	writeBtnEventHandler();							// 게시글 작성 버튼 이벤트 연결
	searchForMapEventHandler();						// 지도 검색 이벤트 연결
	waitingPublicData();							// 공공데이터를 받아오는 소켓이벤트 연결(nodejs활용)
});

function drawMarker(){
	for(var i=0; i<publicDataFromAPI.length; i++){
		var iwContent = '<div class="marker-infowindow">'+
						'<div class="form-group">'+'<공공데이터>'+'</div>'+
						 '<div class="form-group">품종 : '+publicDataFromAPI[i].kindCd+'</div>'+
						 '<div class="form-group">발견 장소 : '+publicDataFromAPI[i].happenPlace+'</div>'+
						 '<div class="form-group">현재 상태 : '+publicDataFromAPI[i].processState+'</div>'+
						 '<div class="form-group">연락처 : '+publicDataFromAPI[i].careTel+'</div>'+
						 '<a class="btn btn-sm btn-secondary" href="https://map.kakao.com/link/roadview/'+publicDataFromAPI[i].y+','+publicDataFromAPI[i].x+'">'+'로드뷰'+'</a>'+
						 '</div>'; 
        if(publicDataFromAPI[i].x == 0 ) continue;
        
        var markerPosition = new kakao.maps.LatLng(publicDataFromAPI[i].y, publicDataFromAPI[i].x); // latitude 가 y, longitude가 x
        var imageSrc = contextPath + '/resources/imgs/marker/green.png', // 마커이미지의 주소입니다    
	    imageSize = new kakao.maps.Size(20, 30), // 마커이미지의 크기입니다
	    imageOption = {offset: new kakao.maps.Point(6, 28)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
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

function waitingPublicData(){
	socket.on('getPublicData', function(data){
		publicDataFromAPI = data;
 		drawMarker(map);
	});
}

function getPublicData(){ // 공공데이터를 가져오는 함수(nodejs 서버에서 파이썬 파일을 돌린 결과를 받아옴)
	var dataOptions = new Object();
	socket.emit('getPublicData', dataOptions);
}

function searchForMapEventHandler(){
	$('#timeForSearch').on('change', getMarkerDataAndSetMarkers);
	$('#locationForSearch').on('keydown', function(event){
		if (event.keyCode === 13) {
			if($('#locationForSearch').val()!=""){
				setCenterLocation($('#locationForSearch').val());
			}
			setTimeout(function(){
				getMarkerDataAndSetMarkers();
			}, 500);
			
		  };
	});
}

function documentPreventKeyDown(){
	document.addEventListener('keydown', function(event) {
		  if (event.keyCode === 13) {
		    event.preventDefault();
		  };
		}, true);
}

function searchWordEventHandler(){
	$('#keywordInput').on('keydown', function(e){
		if(e.keyCode === 13){
			$('#searchBtn').click();
		}
	});
}

function searchBtnEventHandler(){
	$('#searchBtn').on('click', getData);
}

function writeBtnEventHandler(){
	$('#writeBtn').on('click', function(){
		location.href='findBoardWrite.do';
	})
}

function autoCompleteFunc(){ // 검색어 자동완성 함수
	$('#keywordInput').autocomplete({
		source : function( request, response ) {
            $.ajax({
                   type: 'get',
                   url: "/petcommunity/autoCompleteForFindBoard.do",
                   dataType: "json",
                   data:{
                	   	"searchWord" : $('#keywordInput').val(),
       					"searchType" : $('#searchType').val()
                   },
                   success: function(data) {
                	   console.log('autocomplete success');
                       //서버에서 json 데이터 response 후 목록에 추가
                       response(
                           $.map(data, function(item) {    //json[i] 번째 에 있는게 item 임.
                               return {
                                   label: item,    			//UI 에서 보여지는 글자, 실제 검색어랑 비교 대상
                                   value: item,    		   //사용자 값
                               }
                           })
                       );
                   },
                   error: function(data){
                	   console.log('autocomplete error');
                   }
              });
           },
           select : function(event, ui){
        	   
           },
           appendTo :'#search-container',
           minLength: 1,		 // 최소 글자수
           autoFocus: false,		 //첫번째 항목 자동 포커스 기본값 false
           delay : 500,
	});
}
//사용하지 않음
function autoCompleteFuncForMap(){
	$('#locationForSearch').autocomplete({
		source : function( request, response ) {
            $.ajax({
                   type: 'get',
                   url: "/petcommunity/autoCompleteForMap.do",
                   dataType: "json",
                   data:{
                	   	"searchWord" : $('#locationForSearch').val(),
       					"searchType" : 'location'
                   },
                   success: function(data) {
                	   console.log('autocomplete success');
                       //서버에서 json 데이터 response 후 목록에 추가
                       response(
                           $.map(data, function(item) {    //json[i] 번째 에 있는게 item 임.
                               return {
                                   label: item,    			//UI 에서 보여지는 글자, 실제 검색어랑 비교 대상
                                   value: item,    		   //사용자 값
                               }
                           })
                       );
                   },
                   error: function(data){
                	   console.log('autocomplete error');
                   }
              });
           },
           select : function(event, ui){
        	   
           },
           appendTo : '#searchForMap-container',
           minLength: 1,		 // 최소 글자수
           autoFocus: false,		 //첫번째 항목 자동 포커스 기본값 false
           delay : 500,
	});
}

function getDataInPaging(){ // 페이지 클릭 시 이벤트(페이징 ul 생성시 이벤트 연결)
	$.ajax({
		type : 'post',
		async:true,
		url : '/petcommunity/findboardListWithPaging.do',
		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
		data : {"searchWord" : $('#keywordInput').val(),
				"searchType" : $('#searchType').val(),
				"curPage" : curPage,
				},
		dataType : 'json',
		success : function(resultData){
			console.log("getDataInPaging");
			drawTable(resultData);
			console.log(resultData);
		},
		error:function(request,status,error){
			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
		
	});
}

function getData(){ // 페이징 생성하는 함수
	$.ajax({
		type : 'post',
		async:true,
		url : '/petcommunity/findboardListWithPaging.do',
		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
		data : {"searchWord" : $('#keywordInput').val(),
				"searchType" : $('#searchType').val(),
				"curPage" : curPage,
				},
		dataType : 'json',
		success : function(resultData){
            var totalPages = resultData.pagination.pageCnt;
            var currentPage = $('#pagination-demo').twbsPagination('getCurrentPage');
            $('#pagination-demo').twbsPagination('destroy');
            $('#pagination-demo').twbsPagination($.extend({}, defaultOpts, {
                prev: "이전",
                next: "다음",
                first: '«',
                last: '»',
                startPage: currentPage,
                totalPages: totalPages
            }));
		},
		error:function(request,status,error){
			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}		
	});
}

function drawTable(data){ // 게시판을 그리는 함수
	$('#findboardTbody').empty();
	var trPrefix = '<tr>';
	var trSuffix = '</tr>';
	var tdPrefix = '<td>';
	var tdSuffix = '</td>';
	var spanPrefix = '<span>';
	var spanSuffix = '</span>';
	var br='<br/>';
	var listContent="";
	var img="";
	for(var i=0; i<data.findBoardVOListSize; i++){
		img='<img src="resources/imgs/findboard/'+data.img[i];
		listContent +=		
			tdPrefix + 
			'<a href=/petcommunity/getFindBoard.do?findboardId=' +data.findBoardVOList[i].findboardId + '>' +
			img+'" alt=" "/>'+br+
			data.findBoardVOList[i].findboardTitle +'</a>' + br +
			spanPrefix + data.findBoardVOList[i].findboardUploadtime +br + spanSuffix +
			
			'</td>';
		if(i==data.findBoardVOListSize-1 || i%4==3){
			$('#findboardTbody').append('<tr>'+listContent+'</tr>');
			listContent="";
		}
	}
}

function getMarkerDataAndSetMarkers(){ // 공공데이터로 가져온 데이터와 db의 데이터로 마커를 표시하는 함수
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
			setTimeout(function(){
				kakaoMapAPI(resultData);
			}, 2000);
			setTimeout(function(){
				getPublicData();
			}, 2500);
		},
		error:function(request,status,error){
			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}		
	});
}

function kakaoMapAPI(data){
	$('#map').empty();
	console.log($('#locationForSearch').val());
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = { 
        center: new kakao.maps.LatLng(latitude, longitude), // 지도의 중심좌표
        level: 7 // 지도의 확대 레벨
    };
	map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다		
	for(var i=0; i<data.lostBoardVOListSize; i++){ // 잃어버린 게시글의 개수만큼
		var position =  new kakao.maps.LatLng(data.lostBoardVOList[i].lostboardX, data.lostBoardVOList[i].lostboardY);		
		var imageSrc = contextPath + '/resources/imgs/marker/red.png', // 마커이미지의 주소입니다    
	    imageSize = new kakao.maps.Size(20, 30), // 마커이미지의 크기입니다
	    imageOption = {offset: new kakao.maps.Point(6, 28)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
		var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);		
		var marker = new kakao.maps.Marker({
			  position: position
			  ,image: markerImage // 마커이미지 설정
			});
		marker.setMap(map); // 마커를 맵에 표시
		marker.setRange(1000); // 마커가 보이는 범위 지정
		// infowindow에 들어갈 내용
		var iwContent = '<div class="marker-infowindow">'+
						'<div class="form-group">'+data.lostBoardVOList[i].lostboardTitle+'</div>'+
						'<div class="form-group">'+data.lostBoardVOList[i].lostboardLocation+'</div>'+
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
	for(var i=0; i<data.lostBoardVOListSize2; i++){
		var position =  new kakao.maps.LatLng(data.lostBoardVOList2[i].lostboardX, data.lostBoardVOList2[i].lostboardY);
		
		var imageSrc = contextPath + '/resources/imgs/marker/green.png', // 마커이미지의 주소입니다    
	    imageSize = new kakao.maps.Size(20, 30), // 마커이미지의 크기입니다
	    imageOption = {offset: new kakao.maps.Point(6, 28)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
		var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);
		
		var marker = new kakao.maps.Marker({
			  position: position
			  ,image: markerImage // 마커이미지 설정
			});
		marker.setMap(map);
		marker.setRange(1000);
		var iwContent = '<div class="marker-infowindow">'+
						'<div class="form-group">'+data.lostBoardVOList2[i].lostboardTitle+'</div>'+
						'<div class="form-group">'+data.lostBoardVOList2[i].lostboardLocation+'</div>'+
						'<div class="form-group">'+
						'<a class="btn btn-sm btn-secondary" href=/petcommunity/getLostBoard.do?lostboardId='+data.lostBoardVOList2[i].lostboardId+'>'+'게시글 보러가기'+'</a>'+
						'<a class="btn btn-sm btn-secondary" href="https://map.kakao.com/link/roadview/'+data.lostBoardVOList2[i].lostboardX+','+data.lostBoardVOList2[i].lostboardY+'">'+'로드뷰'+'</a>'+
						'</div>'+
						'<div class="infoWindowImageContainer">'+'<img src="'+contextPath+'/resources/imgs/lostboard/'+data.lostBoardVOList2[i].lostboardId+'/'+data.lostBoardFileList2[i].filename +'" class="infoWindowImage" alt="이미지가 존재하지 않습니다.">'+'</div>'+
						'</div>';
		if(data.lostBoardFileList2[i].filename=='??'){
			iwContent  = '<div class="marker-infowindow">'+
						'<div class="form-group">'+data.lostBoardVOList2[i].lostboardTitle+'</div>'+
						'<div class="form-group">'+data.lostBoardVOList2[i].lostboardLocation+'</div>'+
						'<div class="form-group">'+
						'<a class="btn btn-sm btn-secondary" href=/petcommunity/getLostBoard.do?lostboardId='+data.lostBoardVOList2[i].lostboardId+'>'+'게시글 보러가기'+'</a>'+
						'<a class="btn btn-sm btn-secondary" href="https://map.kakao.com/link/roadview/'+data.lostBoardVOList2[i].lostboardX+','+data.lostBoardVOList2[i].lostboardY+'">'+'로드뷰'+'</a>'+
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
	    imageSize = new kakao.maps.Size(20, 30), // 마커이미지의 크기입니다
	    imageOption = {offset: new kakao.maps.Point(6, 28)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
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


//marker click event
//closure를 이용한 infowindow on/off
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

//현재 작업경로를 가져오는 함수
function getContextPath() {
	   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}
// 현재 위치를 가져오는 함수
function getLocation() {
    if (navigator.geolocation) {	// GPS를 지원하면
      navigator.geolocation.getCurrentPosition(function(position) {
        latitude = position.coords.latitude;
        longitude = position.coords.longitude;
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
// 주소로 좌표를 검색하는 함수
function setCenterLocation(){
	var geocoder = new kakao.maps.services.Geocoder();
	// 주소로 좌표를 검색합니다
	geocoder.addressSearch($('#locationForSearch').val(), function(result, status) {
	    // 정상적으로 검색이 완료됐으면 
	     if (status === kakao.maps.services.Status.OK) {
	        latitude = result[0].y;
	        longitude = result[0].x;
	        console.log('search ... result ...');
	        console.log(latitude);
	        console.log(longitude);
	    }
	});
}

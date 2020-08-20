var curPage;
var latitude = -1;
var longitude = -1;
//var latitude = 37.519972628243366;
//var longitude = 126.85287648507145;
var contextPath = getContextPath();
var defaultOpts = {
	visiblePages : 10,
    onPageClick: function (event, page) {
        $('#page-content').text('Page ' + page);
        curPage=page;
        getDataInPaging();
    }
};

$(function(){
	getData();
	getLocation();
	getDataWithoutPaging();
	autoCompleteFunc();
	autoCompleteFuncForMap();
	documentPreventKeyDown();
	searchWordEventHandler();
	searchBtnEventHandler();
	writeBtnEventHandler();
	searchForMapEventHandler();
});

function searchForMapEventHandler(){
	$('#timeForSearch').on('change', getDataWithoutPaging);
//	$('#locationForSearch').on('keydown', getDataWithoutPaging);
	$('#locationForSearch').on('keydown', function(event){
		if (event.keyCode === 13) {
			if($('#locationForSearch').val()!=""){
				setCenterLocation($('#locationForSearch').val());
			}
			setTimeout(function(){
				getDataWithoutPaging();
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
		location.href='lostBoardWrite.do';
	})
}

//https://jqueryui.com/autocomplete/ 참고
function autoCompleteFunc(){
	$('#keywordInput').autocomplete({
		source : function( request, response ) {
            $.ajax({
                   type: 'get',
                   url: "/petcommunity/autoCompleteForLostBoard.do",
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
//           appendTo :'#search-container',
           minLength: 1,		 // 최소 글자수
           autoFocus: false,		 //첫번째 항목 자동 포커스 기본값 false
           delay : 500,
	});
}

function getDataInPaging(){
	$.ajax({
		type : 'post',
		async:true,
		url : '/petcommunity/lostboardListWithPaging.do',
		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
		data : {"searchWord" : $('#keywordInput').val(),
				"searchType" : $('#searchType').val(),
				"curPage" : curPage,
				},
		dataType : 'json',
		success : function(resultData){
			drawTable(resultData);
			console.log(resultData);
		},
		error:function(request,status,error){
			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
		
	});
}

function getData(){
	$.ajax({
		type : 'post',
		async:true,
		url : '/petcommunity/lostboardListWithPaging.do',
		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
		data : {"searchWord" : $('#keywordInput').val(),
				"searchType" : $('#searchType').val(),
				"curPage" : curPage,
				},
		dataType : 'json',
		success : function(resultData){
			drawTable(resultData);
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

function drawTable(data){
	$('#lostboardTbody').empty();
	var trPrefix = '<tr>';
	var trSuffix = '</tr>';
	var tdPrefix = '<td>';
	var tdSuffix = '</td>';

	for(var i=0; i<data.lostBoardVOListSize; i++){
		var listContent = 
						trPrefix +
						tdPrefix + data.lostBoardVOList[i].lostboardId + tdSuffix +
						tdPrefix + data.lostBoardVOList[i].lostboardStatus + tdSuffix +
						tdPrefix + '<a href=/petcommunity/getLostBoard.do?lostboardId=' +data.lostBoardVOList[i].lostboardId + '>' + data.lostBoardVOList[i].lostboardTitle +'</a>' + tdSuffix +
						tdPrefix + data.lostBoardVOList[i].memberId + tdSuffix +
						tdPrefix + data.lostBoardVOList[i].lostboardReadcount + tdSuffix +
						tdPrefix + data.lostBoardVOList[i].lostboardUploadtime + tdSuffix +
						trSuffix;	
		$('#lostboardTbody').append(listContent);
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
			kakaoMapAPI(resultData);
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

	var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
		
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
		var iwContent = '<div class="card marker-infowindow">'+
						'<div class="form-group">'+data.lostBoardVOList[i].lostboardTitle+'</div>'+
						'<div class="form-group">'+data.lostBoardVOList[i].lostboardLocation+'</div>'+
						'<a href=/petcommunity/getLostBoard.do?lostboardId='+data.lostBoardVOList[i].lostboardId+'>'+'게시글 보러가기'+'</a>'+
						'<div class="form-group">'+'<img src="'+contextPath+'/resources/imgs/lostboard/'+data.lostBoardVOList[i].lostboardId+'/'+data.lostBoardFileList[i].filename +'" class="d-block w-100" alt="이미지가 존재하지 않습니다.">'+'</div>'+
						'</div>';
		if(data.lostBoardFileList[i].filename=='??'){
			iwContent  = '<div class="card marker-infowindow">'+
						'<div class="form-group">'+data.lostBoardVOList[i].lostboardTitle+'</div>'+
						'<div class="form-group">'+data.lostBoardVOList[i].lostboardLocation+'</div>'+
						'<a href=/petcommunity/getLostBoard.do?lostboardId='+data.lostBoardVOList[i].lostboardId+'>'+'게시글 보러가기'+'</a>'+
						'<div class="form-group">'+'<img src="'+contextPath+'/resources/imgs/lostboard/default/1.png" class="d-block w-100" alt="이미지가 존재하지 않습니다.">'+'</div>'+
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
		var iwContent = '<div class="card marker-infowindow">'+
						'<div class="form-group">'+data.findBoardVOList[i].findboardTitle+'</div>'+
						'<div class="form-group">'+data.findBoardVOList[i].findboardLocation+'</div>'+
						'<a href=/petcommunity/getFindBoard.do?findboardId='+data.findBoardVOList[i].findboardId+'>'+'게시글 보러가기'+'</a>'+
						'<div class="form-group">'+'<img src="'+contextPath+'/resources/imgs/findboard/'+data.findBoardVOList[i].findboardId+'/'+data.findBoardFileList[i].filename +'" class="d-block w-100" alt="이미지가 존재하지 않습니다.">'+'</div>'+
						'</div>';
		if(data.findBoardFileList[i].filename=='??'){
			iwContent = '<div class="card marker-infowindow">'+
			'<div class="form-group">'+data.findBoardVOList[i].findboardTitle+'</div>'+
			'<div class="form-group">'+data.findBoardVOList[i].findboardLocation+'</div>'+
			'<a href=/petcommunity/getFindBoard.do?findboardId='+data.findBoardVOList[i].findboardId+'>'+'게시글 보러가기'+'</a>'+
			'<div class="form-group">'+'<img src="'+contextPath+'/resources/imgs/findboard/default/1.png" class="d-block w-100" alt="이미지가 존재하지 않습니다.">'+'</div>'+
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

// 현재 작업경로를 가져오는 함수
function getContextPath() {
	   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
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


function setCenterLocation(){
    console.log(latitude);
    console.log(longitude);
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
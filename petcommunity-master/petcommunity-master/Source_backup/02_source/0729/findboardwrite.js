var latitude = -1;
var longitude = -1;
//var latitude = 37.519972628243366;
//var longitude = 126.85287648507145;
$(function() {
//	getLocation();
	kakaoMapAPI();
	$('#findBoardWriteForm').on('submit',function(e){
		e.preventDefault();
		e.stopPropagation();
		if(checkSubmit()==true){
			$('#findBoardWriteForm')[0].submit(); // ***** [0].submit ***** 
		}
	});
	$(document).on('change', 'input.file', function(e){
		console.log($('input.file').val());
		if($(this).val()!=''){
			$('#file-list').append('<li class="list-group-item"> <input type="file" name="file" class="file" accept="image/gif, image/jpeg, image/png"/> </li>');
		}else{
			$(this).parent().remove();
			console.log('파일 비어있음');
		}
	});
});

function kakaoMapAPI() {
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
	mapOption = {
		center : new kakao.maps.LatLng(37.519972628243366, 126.85287648507145), // 지도의 중심좌표
		level : 7 // 지도의 확대 레벨
	};

	var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

	// 지도를 클릭한 위치에 표출할 마커입니다
	var marker = new kakao.maps.Marker({ 
	    // 지도 중심좌표에 마커를 생성합니다 
	    //position: map.getCenter() 
	});
	
	// 지도에 마커를 표시합니다
	marker.setMap(map);
	
	// 주소-좌표 변환 객체를 생성합니다
	var geocoder = new kakao.maps.services.Geocoder();
	
	// 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
	var mapTypeControl = new kakao.maps.MapTypeControl();

	// 지도에 컨트롤을 추가해야 지도위에 표시됩니다
	// kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
	map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

	// 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
	var zoomControl = new kakao.maps.ZoomControl();
	map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);	
	
	// 지도 클릭 이벤트
	kakao.maps.event.addListener(map, 'click', function(mouseEvent) {        
	    
	    // 클릭한 위도, 경도 정보를 가져옵니다 
	    var latlng = mouseEvent.latLng;
	    latitude = latlng.getLat();
	    longitude = latlng.getLng();
	    
	    // 마커 위치를 클릭한 위치로 옮깁니다
	    marker.setPosition(latlng);
	    // hidden 태그에 위도 경도 표시
	    setLocation(latitude,  longitude);
	    
	    searchDetailAddrFromCoords(mouseEvent.latLng, function(result, status) {
	        if (status === kakao.maps.services.Status.OK) {
	        	if(!!result[0].road_address){
	        		console.log('도로명 주소', result[0].road_address.address_name);
	        		setAddress(result[0].road_address.address_name, 0);
	        	}else{
	        		console.log('지번 주소', result[0].address.address_name);
	        		setAddress(result[0].address.address_name, 1);
	        	}
	        }   
	    });
	});
	
	function searchDetailAddrFromCoords(coords, callback) {
	    // 좌표로 법정동 상세 주소 정보를 요청합니다
	    geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
	}
}

function setLocation(latitude,  longitude){
	$('#findboardX').val(latitude);
	$('#findboardY').val(longitude);
}
// 주소 타입 : 도로명 주소 0, 지번 주소 1
function setAddress(addressname, addressType){
	$('#findboardLocation').val(addressname);
	$('#findboardLocationType').val(addressType);
}

function checkSubmit(){
	if(latitude==-1){
		alert('지도에 위치를 표시해주세요');
		return false;
	}
	if($('#findboardTitle').val()=="" | $('#findboardTitle').val()==null){
		alert('제목을 입력해주세요');
		return false;
	}
	return true;
}

//function getLocation() {
//    if (navigator.geolocation) {	// GPS를 지원하면
//      navigator.geolocation.getCurrentPosition(function(position) {
//        latitude = position.coords.latitude;
//        longitude = position.coords.longitude;
//    	console.log(latitude);
//    	console.log(longitude);
//    	kakaoMapAPI();
//      }, function(error) {    	  	// 좌표를 못 가져오는 경우에 실행되는 부분
//        latitude = 37.519972628243366;
//        longitude = 126.85287648507145;
//    	console.log(latitude);
//    	console.log(longitude);
//    	kakaoMapAPI();
//      }, {
//        enableHighAccuracy: false,
//        maximumAge: 0,
//        timeout: Infinity
//      });
//    } else {
//      alert('GPS를 지원하지 않습니다');
//    }
//  }
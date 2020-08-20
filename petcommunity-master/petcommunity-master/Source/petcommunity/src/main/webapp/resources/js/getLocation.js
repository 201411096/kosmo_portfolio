var latitude = -1;
var longitude = -1;
$(function() {
	getLocation();
});
function getLocation() {
    if (navigator.geolocation) {	// GPS를 지원하면
      navigator.geolocation.getCurrentPosition(function(position) {
        latitude = position.coords.latitude;
        longitude = position.coords.longitude;
        $("#memberX").val(latitude);
        $("#memberY").val(longitude);
        /*alert("모바일 대환영" + latitude +"/" + longitude);*/
        console.log('geolocation success--------------------------');
    	console.log(latitude);
    	console.log(longitude);
//    	kakaoMapAPI();
      }, function(error) {    	  	// 좌표를 못 가져오는 경우에 실행되는 부분
        alert("에러")
    	latitude = 37.519972628243366;
        longitude = 126.85287648507145;
        console.log('geolocation error--------------------------');
    	console.log(latitude);
    	console.log(longitude);
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
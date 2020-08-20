$(function(){
	var cities=["서울","인천","대전","광주","대구","울산","부산","세종","경기","강원","충북","충남","전북","전남","경북","경남","제주"];
    var seoul = ["종로구","중구","용산구","성동구","광진구","동대문구","중랑구","성북구","강북구","도봉구","노원구","은평구","서대문구","마포구"
    	,"양천구","강서구","구로구","금천구","영등포구","동작구","관악구","서초구","강남구","송파구","강동구"];
    var incheon = ["중구","동구","미추홀구","연수구","남동구","부평구","계양구","서구","강화군","옹진군"];
    var dajeon = ["동구","중구","서구","유성구","대덕구"];
    var gwangju = ["동구","서구","남구","북구","광산구"];
    var daegu = ["중구","동구","서구","남구","북구","수성구","달서구","달성군"];
    var ulsan = ["중구","남구","동구","북구","울주군"];
    var busan = ["중구","서구","동구","영도구","부산진구","동래구","남구","북구","강서구","해운대구","사하구","금정구","연제구","수영구","사상구","기장군"];
    var sejong = []
    var kyunggido = ["수원시","고양시","용인시","성남시","화성시","부천시","남양주시","안산시","안양시","평택시","광명시","하남시","의정부시","파주시"];
    var ganwondo = ["춘천시","원주시","강릉시","동해시","태백시","속초시","삼척시"];
    var chungbuk = ["청주시","충주시","제천시"];
    var chungnam = ["천안시","공주시","보령시","아산시","서산시","논산시","계룡시","당진시"];
    var jeonbuk = ["전주시","익산시","군산시","정읍시","남원시","김제시"];
    var jeonnam = ["목포시","여수시","순천시","나주시","광양시"];
    var kyungbuk = ["포항시","경주시","김천시","안동시","구미시","영주시","영천시","상주시","문경시","경산시"];
    var kyungnam = ["창원시","김해시","양산시","진주시","거제시","통영시","사천시","밀양시"];
    var jeju = ["제주시","서귀포시"];
    //province 항목 동적 생성하기
    $('#cityName').change(function(){
    	var selectItem = $("#cityName").val();
        	
        var changeItem;
     
        switch (selectItem) {
        case "서울" :
        	changeItem = seoul;
            break;
        case "인천" :
        	changeItem = incheon;
            break;
        case "대전" :
        	changeItem = dajeon;
            break;
        case "대구" :
        	changeItem = daegu;
            break;
        case "광주" :
        	changeItem = gwangju;
            break;
        case "울산" :
        	changeItem = ulsan;
            break;
        case "부산" :
        	changeItem = busan;
            break;
        case "세종" :
        	changeItem = sejong;
            break;
        case "충북" :
        	changeItem = chungbuk;
            break;
        case "경기" :
        	changeItem = kyunggido;
            break;
        case "강원" :
        	changeItem = ganwondo;
            break;
        case "충남" :
        	changeItem = chungnam;
            break;
        case "전북" :
        	changeItem = jeonbuk;
            break;
        case "전남" :
        	changeItem = jeonnam;
            break;
        case "경북" :
        	changeItem = kyungbuk;
            break;
        case "경남" :
        	changeItem = kyungnam;
            break;
        case "제주" :
        	changeItem = jeju;
            break;
      }
    
        
       $('#province').empty();
     
        for(var cityName in changeItem){
        	var option = $("<option>"+changeItem[cityName]+"</option>");
            $('#province').append(option);
        }
    });
    
    
    //검색버튼 클릭
    $('#searchLocation').click(function(){	
    	listByLocation();
    });
    //검색버튼 클릭
    $('#searchName').click(function(){
    	listBySearch();
    });
});    
 
var listByLocationWithPaging = {
	    visiblePages : 5,
	    onPageClick: function (event, page) {
	    	$('#page-content').text('Page ' + page);
	    	    curPage=page;
	    	    listByLocation();         
	}
};


var listBySearchWithPaging = {
	    visiblePages : 5,
	    onPageClick: function (event, page) {
	    	$('#page-content').text('Page ' + page);
	    	    curPage=page;
	    	    listBySearch();         
	}
};

function listByLocation(){

	$.ajax({
		type : 'post',
		async:true,
		url : '/petcommunity/getFindHospitalListByLocation.do',
		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
		dataType : 'json',
		data:{
			cityName : $('#cityName').val(),
			province : $('#province').val()
		},
		success : function(resultData){		
			drawTable(resultData);
			$('#NormalPaging').empty();
			 var totalPages = resultData.pagination.pageCnt;
	         var currentPage = $('#pagination-demo').twbsPagination('getCurrentPage');
	            $('#pagination-demo').twbsPagination('destroy');
	            $('#pagination-demo').twbsPagination($.extend({}, listByLocationWithPaging, {
	                startPage: currentPage,
	                totalPages: totalPages
	            }));
		},
		error:function(request,status,error){
			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
		
	});
}

function listBySearch(){
	$.ajax({
		type : 'post',
		async:true,
		url : '/petcommunity/getFindHospitalListBySearch.do',
		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
		data:{"searchWord" : $('#keywordInput').val(),
			"curPage" : curPage
			},
		dataType : 'json',
		success : function(resultData){		
				drawTable(resultData);
				$('#NormalPaging').empty();
				 var totalPages = resultData.pagination.pageCnt;
		         var currentPage = $('#pagination-demo').twbsPagination('getCurrentPage');
		            $('#pagination-demo').twbsPagination('destroy');
		            $('#pagination-demo').twbsPagination($.extend({}, listBySearchWithPaging, {
		                startPage: currentPage,
		                totalPages: totalPages
		            }));
		},
		error:function(request,status,error){
			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
		
	});
}


var curPage;
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
	//autoCompleteFunc();
	//autoCompleteFuncForMap();
	documentPreventKeyDown();
	searchForMapEventHandler();
});

//
//function searchForMapEventHandler(){
//	$('#searchLocation').on('click', getDataWithoutPaging);
//	$('#searchName').on('click', function(event){
//		if (event.keyCode === 13) {
//			if($('#searchName').val()!=""){
//				setCenterLocation($('#searchName').val());
//			}
//			setTimeout(function(){
//				getDataWithoutPaging();
//			}, 500);
//			
//		  };
//	});
//}

function documentPreventKeyDown(){
	document.addEventListener('keydown', function(event) {
		  if (event.keyCode === 13) {
		    event.preventDefault();
		  };
		}, true);
}

//function autoCompleteFunc(){
//	$('#searchName').autocomplete({
//		source : function( request, response ) {
//            $.ajax({
//                   type: 'get',
//                   url: "/petcommunity/autoCompleteForFindHospital.do",
//                   dataType: "json",
//                   data:{
//                	   	"searchWord" : $('#searchName').val()
//                	   	
//                   },
//                   success: function(data) {
//                	   console.log('autocomplete success');
//                       //서버에서 json 데이터 response 후 목록에 추가
//                       response(
//                           $.map(data, function(item) {    //json[i] 번째 에 있는게 item 임.
//                               return {
//                                   label: item,    			//UI 에서 보여지는 글자, 실제 검색어랑 비교 대상
//                                   value: item,    		   //사용자 값
//                               }
//                           })
//                       );
//                   },
//                   error: function(data){
//                	   console.log('autocomplete error');
//                   }
//              });
//           },
//           select : function(event, ui){
//        	   
//           },
//           appendTo :' ',
//           minLength: 1,		 // 최소 글자수
//           autoFocus: true,		 //첫번째 항목 자동 포커스 기본값 false
//           delay : 500,
//	});
//}

function autoCompleteFuncForMap2(){
	$('#locationForSearch').autocomplete({
		source : function( request, response ) {
            $.ajax({
                   type: 'get',
                   url: "/petcommunity/autoCompleteForMap2.do",
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

           minLength: 1,		 // 최소 글자수
           autoFocus: false,		 //첫번째 항목 자동 포커스 기본값 false
           delay : 500,
	});
}


function getDataInPaging(){
	$.ajax({
		type : 'post',
		async:true,
		url : '/petcommunity/findHospitalListWithPaging.do',
		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
		data : {"searchWord" : $('#keywordInput').val(),
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
		url : '/petcommunity/findHospitalListWithPaging.do',
		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
		data : {"searchWord" : $('#keywordInput').val(),
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
	$('#findHospitalTbody').empty();
	var trPrefix = '<tr>';
	var trSuffix = '</tr>';
	var tdPrefix = '<td>';
	var tdSuffix = '</td>';
	for(var i=0; i<data.findHospitalVOListSize; i++){
		var listContent = 
						trPrefix +
						tdPrefix + data.findHospitalVOList[i].findhospitalName + tdSuffix +
						tdPrefix + data.findHospitalVOList[i].findhospitalTel + tdSuffix +
						tdPrefix + data.findHospitalVOList[i].findhospitalAddress + tdSuffix +
						tdPrefix + data.findHospitalVOList[i].findhospitalOpenhour + tdSuffix +
						trSuffix;
		$('#findHospitalTbody').append(listContent);
	}
}



//marker click event
//closure를 이용한 infowindow on/off
//function makeClickListener(map, marker, infowindow) {
//	var status = 0;
//	return function() {
//		if(status==0){
//			infowindow.open(map, marker);
//			status=1;
//		}else{
//			infowindow.close(map, marker);
//			status=0;
//		}
//			
//	};
//}

//현재 작업경로를 가져오는 함수
//function getContextPath() {
//	   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
//}
//
//function getLocation() {
//    if (navigator.geolocation) {	// GPS를 지원하면
//      navigator.geolocation.getCurrentPosition(function(position) {
//        latitude = position.coords.latitude;
//        longitude = position.coords.longitude;
//        console.log('geolocation success--------------------------');
//    	console.log(latitude);
//    	console.log(longitude);
//      }, function(error) {    	  	// 좌표를 못 가져오는 경우에 실행되는 부분
//        latitude = 37.519972628243366;
//        longitude = 126.85287648507145;
//      }, {
//        enableHighAccuracy: false,
//        maximumAge: 0,
//        timeout: Infinity
//      });
//    } else {
//      //alert('GPS를 지원하지 않습니다');
//    	console.log('GPS를 지원하지 않습니다');
//    }
//}
//
//function setCenterLocation(){
//    console.log(latitude);
//    console.log(longitude);
//	var geocoder = new kakao.maps.services.Geocoder();
//	// 주소로 좌표를 검색합니다
//	geocoder.addressSearch($('#searchName').val(), function(result, status) {
//	    // 정상적으로 검색이 완료됐으면 
//	     if (status === kakao.maps.services.Status.OK) {
//	        latitude = result[0].y;
//	        longitude = result[0].x;
//	        console.log('search ... result ...');
//	        console.log(latitude);
//	        console.log(longitude);
//	    }
//	});
//}


//$(document).ready(function(){
//	$('#wordCloud').click(function(){
//        // 500 * 500 사이즈 윈도우 창을 변수로 담아서 새로 열어준다. 
//		let newWindow = window.open("","width=1000 height=1000");   
//        //newWindow라는 창에 img태그 생성해주기
//		let img = newWindow.document.createElement("img"); 
//		img.setAttribute("src","https://localhost:8443/petcommunity/resources/imgs/wordCloud/wordCloud.png");  //이미지가 저장되어있는 경로를 src 안에 넣기
//		img.setAttribute("width","1000");   //width속성 변경
//		img.setAttribute("height","1000");  //height속서 변경
//		newWindow.document.body.appendChild(img);   //body안에 가장 마지막 요소로 img 추가
//	});
//});





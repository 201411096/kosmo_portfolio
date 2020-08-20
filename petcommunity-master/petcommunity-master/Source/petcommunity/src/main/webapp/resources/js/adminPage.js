document.getElementById('startDate').valueAsDate = new Date();
$(document).ready(function() {
$('ul.tabs li').click(function(){
		var tab_id = $(this).attr('data-tab');

		$('ul.tabs li').removeClass('current');
		$('.tab-content').removeClass('current');

		$(this).addClass('current');
		$("#"+tab_id).addClass('current');
	
	});

$('.tab-link3').on('click', function() {
	getData();

});


$("#adminpop").click(function(){
	window.open("adminMember.do", "계정 관리",
	"width=1600,height=850,left=200,top=100");
});



});

//var curPage;
//var defaultOpts={
//		visiblePages : 10,
//		onPageClick : function(event,page){
//			$('#page-content').text('page'+page);
//			curPage=page;
//			getDataInpaging();
//		}
//};
//
//getData();
//$('#searchBtn').on('click', function(){
//	getData();
//	
//});
//
//$('#startdate').attr('value', (new Date().toISOString().substring(0, 10)));
////var date = new Date();
////document.getElementById('startdate').valueAsDate = date;
//
//function getDataInpaging(){
//	$.ajax({
//		type : 'post',
//		async : true,
//		url : '/petcommunity/adminListPage.do',
//		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
//		data : {"startDate" : $('#startDate').val(),
//			"endDate" : $('#endDate').val(),
//			"curPage" : curPage},
//		dataType : 'json',
//		success : function(resultData){
//			console.log("getDataInpaging")
//			window.scrollTo(0,0);
//			drawTable(resultData);
//		},
//		error : function(request, status, error){
//			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
//		}
//	});
//}
//
//function getData(){
//	$.ajax({
//		type : 'post',
//		async:true,
//		url : '/petcommunity/adminListPage.do',
//		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
//		data : {"startDate" : $('#startDate').val(),
//			"endDate" : $('#endDate').val(),
//			"curPage" : curPage},
//		dataType : 'json',
//		success : function(resultData){
//			console.log("getData")
//			drawTable(resultData);
//            var totalPages = resultData.pagination.pageCnt;
//            var currentPage = $('#pagination-demo').twbsPagination('getCurrentPage');
//            console.log("totalPages"+totalPages)
//            console.log('currentPage확인 '+ currentPage);
//            if($('#pagination-demo').data("twbs-pagination"))
//            	$('#pagination-demo').twbsPagination('destroy');
//            $('#pagination-demo').twbsPagination($.extend({}, defaultOpts, {
//                startPage: currentPage,
//                totalPages: totalPages
//            }));
//		},
//		error:function(request,status,error){
//			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
//		}		
//	});
//}
//
//function drawTable(data){
//	$('#SalesHistoryTbody').empty();
//	var trPrefix = '<tr>';
//	var trSuffix = '</tr>';
//	var tdPrefix = '<td>';
//	var tdSuffix = '</td>';
//	console.log("drawTable")
//	for(var i=0; i<data.salesListSize; i++){
//		var listContent =
//			trPrefix +
//				tdPrefix
//				+data.salesList[i].buylistDate
//				+tdSuffix+
//				
//				tdPrefix+
//				'<a href=/petcommunity/productView.do?productId='+data.salesList[i].productId +'>'
//				+data.salesList[i].productName
//				+tdSuffix+
//				
//				tdPrefix+
//				data.salesList[i].buyCnt
//				+tdSuffix+
//				
//				tdPrefix+
//				data.salesList[i].buyTotalprice
//				+tdSuffix+
//				
//			trSuffix;
//		$('#SalesHistoryTbody').append(listContent);
//	}
//}


//--------------------------------------------------------------------------------
//adminProduct here↓↓
/*
변수
	ㄴ curPage : 현재 페이지
	ㄴ defaultOpts : 페이징 처리에 사용되는 기본 옵션
		ㄴ 페이지를 클릭할 떄마다 해당 페이지에 맞는 데이터를 다시 불러오도록 이벤트를 걸어둠
	
함수
	ㄴ getProductDataInPaging : 페이지를 클릭했을 경우 불려오는 함수로 해당 페이지에 맞는 데이터를 불러옴
	ㄴ getProductData : 검색어를 포함하는 데이터들을 불러옴
		ㄴ 내부적으로 동적인 페이징을 구성함
	ㄴ drawProductTable : ajax로 받아온 데이터로 테이블을 그려주는 함수
	ㄴ updateBtnEvent : 수정 버튼들 눌렀을 경우 이벤트 핸들러
		ㄴ 해당 productId에 맞는 데이터들을 수정 화면으로 끌어가서 로딩함
	ㄴ deleteBtnEvent : 삭제 버튼들 눌렀을 경우 이벤트 핸들러
	
실행부분
	ㄴ 화면 로딩 후 바로 데이터를 가져와서 테이블를 그림
	ㄴ 수정 버튼 이벤트 핸들러 연결
	ㄴ 삭제 버튼 이벤트 핸들러 연결
*/

//페이징처리
var curPage;
var defaultOpts = {										//페이징 처리 함수에서 불리는 옵션
        totalPages: 10,
        onPageClick: function (event, page) {
            $('#page-content').text('Page ' + page);
            curPage=page;
            console.log('curPage확인 :' + curPage);
            getProductDataInPaging();
        }
    };
$(function(){
	getProductData();
	$('#searchBtn').on('click', getProductData);
	$(document).on("click","button[id^='updateProduct']", updateBtnEvent);
	$(document).on("click","button[id^='deleteProduct']", deleteBtnEvent);
});

function updateBtnEvent(){
//	alert('update:' + $(this).next().attr('action'))
//	$(this).next().action = "loadProductUpdatePage.do";
//	$(this).next().submit();
//	productId = $(this).next().find('input').val()
//	alert(productId)
//	window.location.href = "loadProductUpdatePage.do?productId="+productId;
	//window.open("loadProductUpdatePage.do?productId="+productId,"","width=500,height=600");
	$(this).submit();
}



function deleteBtnEvent(){
	$(this).submit();
}


function drawProductTable(data){
	$('#productTable').empty();
	var formPrefix1 = '<form action="/petcommunity/loadProductUpdatePage.do">';
	var formPrefix2 = '<form action="/petcommunity/productDelete.do">';
	var formSuffix = '</form>';
	var trPrefix = '<tr>';
	var trSuffix = '</tr>';
	var tdPrefix = '<td>';
	var tdSuffix = '</td>';
	var buttonUpdate = '<button class="btn btn-primary">수정</button>';
	var buttonDelete = '<button class="btn btn-warning">삭제</button>';
	var inputtypehiddenPrefix = '<input type="hidden" name="productId" value="';
	var inputtypehiddenSuffix = '">';
	for(var i=0; i<data.productListSize; i++){
		var listContent =  
						  trPrefix +
						  tdPrefix + data.productList[i].productId + tdSuffix +
						  tdPrefix + data.productList[i].productName + tdSuffix +
						  tdPrefix + data.productList[i].productPrice + tdSuffix +
						  tdPrefix + data.productList[i].productCnt + tdSuffix +
						  tdPrefix + data.productList[i].productFeature + tdSuffix +
						  tdPrefix + data.productList[i].productContent + tdSuffix +
						  tdPrefix + 
						  formPrefix1 + inputtypehiddenPrefix + data.productList[i].productId + inputtypehiddenSuffix + 
						  '<button class="btn btn-primary" id=updateProduct'+[i]+'>수정</button>' +
						  formSuffix +
						  tdSuffix +
						  tdPrefix +
						  formPrefix2 + inputtypehiddenPrefix + data.productList[i].productId + inputtypehiddenSuffix + 
						  '<button class="btn btn-warning" id=deleteProduct'+[i]+'>삭제</button>' + 
						  formSuffix +
						  tdSuffix +
						  trSuffix		  
						  ;
		$('#productTable').append(listContent);
	}
}




//검색 결과 수가 바뀌지 않는 경우에 불리는 함수
// ㄴ 페이징 총 수가 변하지는 않음
function getProductDataInPaging(){
	$.ajax({
		type : 'post',
		async:true,
		url : '/petcommunity/getProductDataWithPaging.do',
		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
		data : {"searchWord" : $('#searchWord').val(),
				"curPage" : curPage,
				},
		dataType : 'json',
		success : function(resultData){
			drawProductTable(resultData);
			console.log(resultData);
			console.log("ajax 안에서 curPage 확인 : " + curPage);
		},
		error:function(request,status,error){
			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
		
	});
}



//검색 결과 수가 바뀌는 경우에 불리는 함수
//ㄴ 페이징 총 수가 변함
function getProductData(){
	$.ajax({
		type : 'post',
		async:true,
		url : '/petcommunity/getProductDataWithPaging.do',
		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
		data : {"searchWord" : $('#searchWord').val()},
		dataType : 'json',
		success : function(resultData){
			drawProductTable(resultData);
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
	

var tagCategory;
var curPage;
var defaultOpts = {
	visiblePages : 10,
    onPageClick: function (event, page) {
    	$('#page-content').text('Page');
    	curPage=page;
        getDataInPaging(tagCategory);
    }
};

$(function(){
	searchAutoComplete();
	tagCategory="all";
	getData(tagCategory);
	$('.tagCategory').on('click', function(){
		tagCategory = $(this).attr( 'value' );
		//getShopData($(this).text();
		getData(tagCategory);
	});
	$('#searchBtn').on('click', function(){
		tagCategory = $('#searchSomething').val();
		getData(tagCategory);
	});
	$("#searchSomething").keyup(function (key) {
        if (key.keyCode == 13) { // 엔터키면
        	tagCategory = $('#searchSomething').val();
    		getData(tagCategory);
        }
    });
});

function searchAutoComplete(){
	$('#searchSomething').autocomplete({
		source : function( request, response ) {
            $.ajax({
                   type: 'get',
                   url: "/petcommunity/shopAutoCompleteSearch.do",
                   dataType: "json",
                   data:{
                	   	"searchSomething" : $('#searchSomething').val(),
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
           appendTo :'#searchInput-container',
           minLength: 1,		 // 최소 글자수
           autoFocus: true,		 //첫번째 항목 자동 포커스 기본값 false
           delay : 500,
           close : function(event){ 
           }
	});
}

function getDataInPaging(tagCategory){
	$.ajax({
		type : 'post',
		async:true,
		url : '/petcommunity/shopCategory.do',
		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
		data : {"tagCategory" : tagCategory,
				"curPage" : curPage
				},
		dataType : 'json',
		success : function(resultData){
			window.scrollTo(0, 0);
			drawShopTable(resultData);
		},
		error:function(request,status,error){
			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
				
	});
}

function getData(tagCategory){
	$.ajax({
		type : 'post',
		async:true,
		url : '/petcommunity/shopCategory.do',
		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
		data : {"tagCategory" : tagCategory,
				"curPage" : curPage
			},
		dataType : 'json',
		success : function(resultData){
			drawShopTable(resultData);
			var totalPages = resultData.pagination.pageCnt;
            var currentPage = 1;
            
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

function drawShopTable(data){
	$('#shopTable').empty();
	var divPrefix1 = '<div class="col-lg-4 col-sm-6 artists-col">';
	var a1 = '<a id="productName" href="';
	var a2 = '" target="_blank">';
	var divPrefix2 = '<div class="artists-item">';
	var img1 = '<img src="./resources/imgs/product_image/external/pawInHand/';
	var img2 = '.jpg" alt="">';
	var h41 = '<h4>';
	var h42 = '<hr id="shophr"></h4>';
	var span1 = '<span id="shopProductprice">'; 
	var span2 = ' 원 </span>';
	var p1 = '<p>';
	var p2 = '</p>';
	var divSuffix = '</div>';
	var aSuffix = '</a>';
	var shopnameimg = '<img id="shopnameimg" src="./resources/imgs/product_image/external/pawInHand/icon.png" alt=""/>';
	for(var i=0; i<data.shopListSize; i++){
		
	/*	if(i<=2){
			var newproduct = '<img id="newproductimg" src="./resources/imgs/product_image/external/pawInHand/newicon.png" alt=""/>';
		}else{
			var newproduct='';
		}*/
		
		var listContent = 
			divPrefix1 + 
			a1 + data.shopList[i].shopLink + a2 +
			divPrefix2 +
			img1 + data.shopList[i].shopProductname + img2 +
			p1+shopnameimg+p2+
			h41 + data.shopList[i].shopProductname.substring(9,data.shopList[i].shopProductname.length) + h42 +aSuffix+
			span1 + data.shopList[i].shopProductprice + span2 +
			divSuffix +
			divSuffix;
		$('#shopTable').append(listContent);
	}
}

$(window).scroll(function() {
$('.bannerDiv').animate({
	top : $(window).scrollTop() + "px"
}, {
	queue : false,
	duration : 600
});
});

//실종 배너
$(document).ready(
		function() {

			$("#bannerX2").click(
					function() {
						$('#banner2').slideToggle("fast");
						$("#bannerX2").toggleClass(
								'glyphicon-chevron-down glyphicon-chevron-up');

					});

		});


function sendRequest2(){

$.ajax({
	type : 'get',
	async : true,
	url : '/petcommunity/getLostrank.do?',
	contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
	dataType : 'json',
	success : function(resultData) {
		for ( var i in resultData) {
			var location1=resultData[i][0].lostboardLocation.split(" ");
			var location2=resultData[i][1].lostboardLocation.split(" ");
			var location3=resultData[i][2].lostboardLocation.split(" ");
			var location4=resultData[i][3].lostboardLocation.split(" ");
			var location5=resultData[i][4].lostboardLocation.split(" ");
			
			$("#a1").text(location1[0]+" "+location1[1])
			$("#a1").attr("href", "getLostBoard.do?lostboardId="+resultData[i][0].lostboardId)

			$("#b1").text(location2[0]+" "+location2[1])
			$("#b1").attr("href", "getLostBoard.do?lostboardId="+resultData[i][1].lostboardId)

			$("#c1").text(location3[0]+" "+location3[1])
			$("#c1").attr("href", "getLostBoard.do?lostboardId="+resultData[i][2].lostboardId)
			
			$("#d1").text(location4[0]+" "+location4[1])
			$("#d1").attr("href", "getLostBoard.do?lostboardId="+resultData[i][3].lostboardId)
			
			$("#e1").text(location5[0]+" "+location5[1])
			$("#e1").attr("href", "getLostBoard.do?lostboardId="+resultData[i][4].lostboardId)

		}

	},
	error : function(request, status, error) {
		console.log("code:" + request.status + "\n" + "message:"
				+ request.responseText + "\n" + "error:" + error);
	}

});

}
sendRequest2();

window.setInterval("sendRequest2()", 2000);



function sendRequest3(){

	$.ajax({
		type : 'get',
		async : true,
		url : '/petcommunity/getFindrank.do?',
		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
		dataType : 'json',
		success : function(resultData) {
			for ( var i in resultData) {
				
				var location1=resultData[i][0].findboardLocation.split(" ");
				var location2=resultData[i][1].findboardLocation.split(" ");
				var location3=resultData[i][2].findboardLocation.split(" ");
				var location4=resultData[i][3].findboardLocation.split(" ");
				var location5=resultData[i][4].findboardLocation.split(" ");
				
				$("#a2").text(location1[0]+" "+location1[1])
				$("#a2").attr("href", "getFindBoard.do?findboardId="+resultData[i][0].findboardId)

				$("#b2").text(location2[0]+" "+location2[1])
				$("#b2").attr("href", "getFindBoard.do?findboardId="+resultData[i][1].findboardId)

				$("#c2").text(location3[0]+" "+location3[1])
				$("#c2").attr("href", "getFindBoard.do?findboardId="+resultData[i][2].findboardId)
				
				$("#d2").text(location4[0]+" "+location4[1])
				$("#d2").attr("href", "getFindBoard.do?findboardId="+resultData[i][3].findboardId)
				
				$("#e2").text(location5[0]+" "+location5[1])
				$("#e2").attr("href", "getFindBoard.do?findboardId="+resultData[i][4].findboardId)

			}

		},
		error : function(request, status, error) {
			console.log("code:" + request.status + "\n" + "message:"
					+ request.responseText + "\n" + "error:" + error);
		}

	});

	}

	sendRequest3();

	window.setInterval("sendRequest3()", 2000);

	$(document).ready(
			function() {
				$("#keywordInput").keypress(function (e) {
				    if (e.which == 13){
				       listBysearch();  // 실행할 이벤트
				    }
				});	
			
	$('.tab_menu_btn').on('click',function(){
		  //버튼 색 제거,추가
		  $('.tab_menu_btn').removeClass('on');
		  $(this).addClass('on')
		  
		  //컨텐츠 제거 후 인덱스에 맞는 컨텐츠 노출
		  var idx = $('.tab_menu_btn').index(this);
		  
		  $('.tab_box').hide();
		  $('.tab_box').eq(idx).show();
		});

			});
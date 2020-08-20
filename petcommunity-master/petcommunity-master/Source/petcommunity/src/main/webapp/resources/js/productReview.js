var starScore = 5;

//======================================
function crayBtn1()
{
	$('#dialog-message').dialog({
		modal: true, 
		buttons: {
			"예": function() {$('#addCartSubmit').submit() },
			"아니오": function() { 
				$.ajax({
					type : 'post',
					async:true,
					url : '/petcommunity/buyCartList2.do',
					contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
					data : {"productId" : $("#productId").val(), 
							"productCnt" : $('#sst').val() 		
							},
					dataType : 'json',
					success : function(resultData){
					},
					error:function(request,status,error){
						console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
					}
				}),
				$(this).dialog('close');
			},
		}
	});
}
//===========================================
$(function(){
	//장바구니 담기 버튼에 confirm알림받아서 페이지 넘기기
	$('#addCart_btn').click(function(){
		crayBtn1();	
	});
	
	//댓글 삭제 이벤트
	$(".delete").on("click", function(){
		if(confirm("삭제하시겠습니까?")) {	
			writerId = $(this).prev().attr("value");
			productId = $(this).prev().prev().attr("value");
			/*modifyReview();*/
			/*$("#btn_modify").css("display", "block");
			$("#btn_reviewSubmit").css("display", "none");*/
			reviewModifyOrDelete(writerId, productId, "delete");
		}		
	});
	//댓글 수정 이벤트
//	$("#modify").on("click", function(){
//		confirm("수정하시겠습니까?");
//		writerId = $(this).prev().attr("value");
//		productId = $(this).prev().prev().attr("value");
//		/*modifyReview();*/
//		/*$("#btn_modify").css("display", "block");
//		$("#btn_reviewSubmit").css("display", "none");*/
//		reviewModifyOrDelete(writerId, "modify");
//	});
	
	// 글자수 제한 이벤트
	$("#productreviewContent").on("keyup", function(){
		if($(this).val().length>1000){
			alert("최대글자 수를 초과(1000자)");
			$(this).val($(this).val().substring(0, 1000));
		}
	});
	// 별점 입력 이벤트
	$('#starScoreDiv span').click(function(){
		starScore = $(this).attr("value");
        $(this).parent().children("span").removeClass("fa-heart-o");  /* 별점의 fa-heart-o 클래스 전부 제거 */ 
        $(this).nextAll("span").addClass("fa-heart-o"); /* 클릭한 별과, 그 앞 까지 별점에 fa-heart-o 클래스 추가 */
        return false;
    });
	// 리뷰버튼 클릭 이벤트
	$("#btn_reviewSubmit").on("click", function(){
		if($("#productreviewContent").val()===""){
			alert("리뷰를 입력해주세요")
		}else{
			loginCheck();
		}
	});
});

// 페이징
function fn_paging(curPage) {
	productId=$("#product_id").attr("value");
	location.href = "/petcommunity/productView.do?curPage=" + curPage + "&productId=" + productId;
}
// 리뷰 입력 함수(로그인 상태 확인, 기존 작성 리뷰 유무 확인, 리뷰 입력)
function loginCheck(){
	$.ajax({
		type : 'post',
		async:true,
		url : '/petcommunity/insertProductReview.do',
		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
		data : {"productId" : $("#reviewProductId").val(), 
				"productreviewScore" : starScore, 
				"productreviewContent" : $("#productreviewContent").val()
				},
		dataType : 'json',
		success : function(resultData){
			if(resultData.loginCheck==="logout"){
				window.location.href="/petcommunity/login.do"
				//window.history.back();
			}else if(resultData.reviewCheck=="alreadyWriteReview"){
				alert("이미 리뷰를 작성하셨습니다")
				
			}else if(resultData.notBuyProduct=="notBuyProduct"){
				alert("제품을 구매한 고객만 작성할 수 있습니다.")
			}else{
				alert("리뷰 작성 완료");
				window.location.reload();
			}
		},
		error:function(request,status,error){
			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
}
// 리뷰 수정(작성자와 로그인 아이디 비교, 리뷰 수정)
function reviewModifyOrDelete(writerId, productId, funcKind){
	$.ajax({
		type:"post",
		async:true,
		url : '/petcommunity/reiviewModifyOrDelete.do',
		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
		data : {"writerId" : writerId,
				"productId" : productId,
				"funcKind" : funcKind
				},
		dataType : 'json',
		success : function(resultData){
			if(resultData.loginCheck==="logout"){
				window.location.href="/petcommunity/login.do"
			}else if(resultData.notWriter=="notWriter"){				
				alert("작성자가 아니라 불가능합니다")
			}else if(resultData.reviewDelete=="done"){
				alert("삭제완료")
				window.location.reload();
			}
			else{
				alert("요청완료")
			}
			
		},
		error:function(request,status,error){
			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
}
function drawStarScore(starScore){
	alert("drawStarScore");
	$("#starScoreDiv").empty();
	var li1 = '<li>'
	var a1 = '<a class="starScore" value="'
	var a2 = '">'
	var iTag1 = '<i class="fa fa-heart">'
	var iTag2 = '</i>'
	var a3 = '</a>'
	var li2 = '</li>'
	var ioTag1 = '<i class="fa fa-heart-o">'
//	var li3 = '"><i class="fa fa-heart-o"></i></a></li>'
	for (var num=1; num<=5; num++){
		if(num<=starScore){
			var StarList = 
			li1 +
			a1 + num + a2 +
			iTag1 + iTag2 +
			a3 +
			li2;
			$("#starScoreUl").append(StarList);
		}else{
			var blinkStarList = 
				li1 +
				a1 + num + a2 +
				ioTag1 + iTag2 +
				a3 +
				li2;
			$("#starScoreDiv").append(blinkStarList);
		}
	}
}
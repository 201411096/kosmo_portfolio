


function getCartList(){
	$.ajax({
		type : 'post',
		async:true,
		url : '/petcommunity/productCartByAjax.do',
		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
		dataType : 'json',
		success : function(resultData){	
			buyTable(resultData);
		},
		error:function(request,status,error){
			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}		
	});
}

function buyTable(data){
	
	var amountOfCartList=data.length;
	//검색된 목록의 개수가 0이라면 메시지 띄어주기
	if(amountOfCartList==0){
		
		$('.totalTable').empty();
		$('.totalTable').html('결제할 상품이 없습니다!');
	//그렇지 않으면 목록 보여주기
	}else{
		$('#cartLists').empty();
		$('#forEmptyList').empty();
		var finalPrice=0;
		$('#listBeforePaying').empty();
		var trPrefix = '<tr>';
		var trSuffix = '</tr>';
		var tdPrefix = '<td>';
		var tdSuffix = '</td>';
		
		for(var i in data){
			//구매리스트 목록 생성		
			var listContent = 
							'<tr class=buyListHeight>' +
							tdPrefix + 
							"<img src='resources/imgs/product_image/internal/product_image/".concat(data[i].productName) +".jpg' id=cartListImg+"+[i]+" />"+ tdSuffix +
							'<input type=hidden name=productId value='+data[i].productId +' >'+
							tdPrefix + "<span id=productName"+[i]+'>' + data[i].productName.substr(0,30) +'</span>' +tdSuffix +
							tdPrefix + '<span id=productPrice'+[i]+' >'+ data[i].productPrice + '</span>원' + tdSuffix +
							tdPrefix + '<span name=buyCnt>'+data[i].buycartlistCnt+'</span>' + tdSuffix +
							tdPrefix + "<span name=productTotalPrice id=productTotalPrice"+[i]+">"+data[i].productPrice*data[i].buycartlistCnt+'</span>원' + tdSuffix +						
							trSuffix;
			$('#listBeforePaying').append(listContent);
			//주문 리스트 목록 생성
			var finalList=
				'<tr style="border-bottom:1px solid #e3e3e3" ><td >' + data[i].productName.substr(0,15) + '</td>'+
				'<td><span class=middle>x' + data[i].buycartlistCnt + '</span></td><td style=text-align:right > <span name=totalPrice class="last">'
				+data[i].productPrice*data[i].buycartlistCnt +'</span>원</td></tr>';
			$('#forEmptyList').append(finalList);
			
			//총 합계 금액 구하기
			finalPrice += parseInt(data[i].productPrice)*parseInt(data[i].buycartlistCnt);	
			
		}	
		$('#finalPrice').empty();
		$('#finalPrice').text(finalPrice);
		$('#finalPriceBeforePaying').text(finalPrice+"원");
		
	}
}	

$(function(){
	getCartList(); //화면이 뜨면 카트리스트를 받아옴 
});

// 필수 정보, 약관 동의 체크 안했을시 submit x
$('.main_btn').on('click', function() {
	var submit1 = false;
	if ($('#add1').val() === "") {
		alert("상세주소를 입력하세요");
		return false;
	} else if ($("input:radio[name=selector]").is(":checked") == false) {
		alert("결제방식을 선택하세요");
		return false;
	} else if ($("input:checkbox[id=f-option4]").is(":checked") == false) {
		alert("구매약관 동의 하셔야합니다");
		return false;
	} else
		submit1 = true;
	if (submit1 === true)
		$('#buyfrm').submit();
});






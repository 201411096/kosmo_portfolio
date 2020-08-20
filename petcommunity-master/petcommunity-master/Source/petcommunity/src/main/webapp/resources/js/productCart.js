function changeProductCntOnCart(cnt, buycartlistId){
	$.ajax({
		type : 'post',
		async:true,
		url : '/petcommunity/changeProductCntOnCart.do',
		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
		data:{
    	   	"buycartlistCnt" : cnt,
			"buycartlistId" : buycartlistId
		},
		dataType : 'json',
		success : function(resultData){
			updateTable(resultData);
		},
		error:function(request,status,error){
			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}		
	});
}
function deleteProductFromCart(buycartlistId){
	$.ajax({
		type : 'post',
		async:true,
		url : '/petcommunity/deleteProductFromCart.do',
		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
		data:{
			"buycartlistId" : buycartlistId
		},
		dataType : 'json',
		success : function(resultData){
			cartTable(resultData);
		},
		error:function(request,status,error){
			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}		
	});
}
function getCartList(){
	$.ajax({
		type : 'post',
		async:true,
		url : '/petcommunity/productCartByAjax.do',
		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
		dataType : 'json',
		success : function(resultData){
	
			cartTable(resultData);
		},
		error:function(request,status,error){
			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}		
	});
}

function cartTable(data){
	
	var amountOfCartList=data.length;
	//검색된 목록의 개수가 0이라면 메시지 띄어주기
	if(amountOfCartList==0){
		$('#cartLists').empty();
		$('.totalTable').empty();
		$('.totalTable').html('장바구니에 담겨진 상품이 없습니다!');
	//그렇지 않으면 목록 보여주기
	}else{
		var finalPrice=0;
		$('#cartLists').empty();
		var trPrefix = '<tr>';
		var trSuffix = '</tr>';
		var tdPrefix = '<td>';
		var tdSuffix = '</td>';
		
		for(var i in data){
			//장바구니 목록 생성		
			var listContent = 
							'<tr class=cartList>' +
							tdPrefix + 
							"<a href=# id=productId"+[i]+" value="+data[i].productId +"><img src='resources/imgs/product_image/internal/product_image/".concat(data[i].productName) +".jpg' class=cartListImg /></a>"+ tdSuffix +
							'<input type=hidden  value="+data[i].productId +" >'+
							tdPrefix + "<span id=productName"+[i]+'>' + data[i].productName +'</span>&nbsp;(<span id=productPrice'+[i]+'>'+ data[i].productPrice + '</span>원)' +tdSuffix +
							tdPrefix + "<span id=productTotalPrice"+[i]+">"+data[i].productPrice*data[i].buycartlistCnt+'</span><span id=won>원</span>' + tdSuffix +
							tdPrefix + 
								'<span>'+
									"<input type='number' id=productAmount" +[i] + '  min="0" value="'+data[i].buycartlistCnt+'" buycartlistId='+data[i].buycartlistId+' /> ' +
								'</span>'+
							tdSuffix +
							tdPrefix + "<a href=#  id=cancelImg"+[i]+" buycartlistId="+data[i].buycartlistId+ "><img src='resources/imgs/product_image/cartImg/cancelImg.png'  ></a>" + tdSuffix +
							trSuffix;
			$('#cartLists').append(listContent);
			
			//총 합계 금액 구하기
			finalPrice += parseInt(data[i].productPrice)*parseInt(data[i].buycartlistCnt);	
		}
		
		//totalTable setting
		
		//첫번째 상품 이름 가져오기
		var firstList = data[0].productName;	
		$('#totalTableProductName').text(firstList.substr(0,20)); //상품이름
		if(amountOfCartList>1){
			$('#amountOfCartList').text(parseInt(amountOfCartList)-Number(1)); // 외 x개 품목
		}else if(amountOfCartList<=1){
			$('#why').text('');
			$('#amountOfCartList').text('1');
		}
		$('#totalPrice').text(finalPrice);		//총 0000원
	}
}
	
function updateTable(data){
	var finalPrice=0;
	var amountOfCartList=data.length;
	for(var i=0; i<amountOfCartList; i++){
		$('#productTotalPrice'+[i]).empty();
		$('#productTotalPrice'+[i]).text(data[i].productPrice*data[i].buycartlistCnt);
		finalPrice += parseInt(data[i].productPrice)*parseInt(data[i].buycartlistCnt);	
	}
	$('#totalPrice').text(finalPrice);
}

$(function(){
	getCartList(); //화면이 뜨면 카트리스트를 받아옴 
	//삭제버튼 클릭
	$(document).on('click', "a[id^='cancelImg']", function(){
		deleteProductFromCart($(this).attr('buycartlistId'));
	});
	
	//input number change or click
	$(document).on('change keyup', "input[id^='productAmount']", function(){		
		changeProductCntOnCart($(this).val(), $(this).attr('buycartlistId'));	
	});
	
	$(document).on('click', "a[id^='productId']", function(){
		window.location.href='/petcommunity/productView.do?productId='+$(this).attr('value');
	});
	
	$(document).on('click', "#goToPay", function(){
		if($('.totalTable').text()=='장바구니에 담겨진 상품이 없습니다!'){
			alert('결제를 진행할 상품이 담겨있지 않습니다!')
		}else{
			window.location.href='/petcommunity/buy.do';
		}
		
	});
	
	$(document).on('click', "#backToShopping", function(){
		window.location.href='productList.do'
	});
	

});


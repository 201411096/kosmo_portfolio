var id = '1';
var startPage=0;
var endPage=5;
var count=0;
var otherId='1';
var toId='';
var content='';
var searchNew='all';
var panelCheck='1';
var myDiv = document.getElementById('message-table');

$(function(){
	// 쪽지 눌렀을 때 전체 메시지 대화 끌고옴
	$(".tab-link4").on("click", function(){
		startPage=0;
		endPage=5;
		getMypageMessage(startPage, endPage);
	})
	// 메시지 상대방 누르면 해당 상대와의 메시지 만 끌고옴
	$(document).on('click', '.mypage-messageId', function(){
		otherId = $(this).next().val();
		myDiv.scrollTop = 0;
		startPage=0; 
		endPage=5;
		getMypageMessage(startPage, endPage);
	});
	$(document).on('click', '.td-sendWho', function(){
		otherId = $(this).attr("value");
		myDiv.scrollTop = 0;
		startPage=0; 
		endPage=5;
		getMypageMessage(startPage, endPage);
	});
	// 종이비행기 누르면 입력 창띄움
	$(document).on("click", ".sendMsg", function(){
		divMessage=$(this).parent().parent().parent().parent().find('div.div-message');/*.children('tr.tr-sendBox');*/
//		tr = $(this).parent().parent().parent().val();/**/
		if(divMessage.css('display')=='none'){
			divMessage.css('display','block');
		}else{
			divMessage.css('display','none');
		}
//		openMessageSpace(id);
		/*window.open("/petcommunity/message.do?id="+id);*/
	});
	// 무한스크롤
	$('#message-table').scroll(function() { 
		var innerHeight = $(this).innerHeight(); 
		var scroll=$(this).scrollTop() + $(this).innerHeight(); 
		var height=$(this)[0].scrollHeight; 

		if(scroll >= height){ 
			startPage=endPage;
			endPage+=3;
			/*alert("스크롤")*/
		if(panelCheck=='1'){			
			getMypageMessage(startPage, endPage);
		}else{
			writeNewMessage(startPage,endPage);
		}
		

		}
	});
	
	// 메시지 보내기
	$(document).on("click", ".btn-message", function(){
		toId=$(this).prev().val().slice(0,-4);
		content=$(this).parent().find('input[type="text"]').val();
		startPage=0;
		getMypageMessage(startPage, endPage);
		startPage=0;
		endPage=5;
		toId='';
		content='';
		$(this).parent().find('input[type="text"]').val('');
	});
	//쪽지 확인 및 새로전송
	//쪽지 확인
	$("#showMessage").on("click", function(){
		panelCheck='1';
		span1=$("#showMessage");
		span2=$("#writeNewMessage");
		span1.css('color','#83bb44');
		span2.css('color','#333')
		toId='';
		content='';
		
		getMypageMessage(startPage, endPage);
	});
	//새로전송
	$("#writeNewMessage").on("click", function(){
		panelCheck='2';
		startPage=0;
		endPage=5;
		span1=$("#showMessage");
		span2=$("#writeNewMessage");
		span1.css('color','#333')
		span2.css('color','#83bb44');
		toId='';
		content='';
		$('#message-table').empty();
		writeNewMessage(startPage,endPage);
		/*drwaWriteMessageTable();*/
	});
	// 사람검색
	$(document).on("click", "#btn-searchSomeone", function(){
		searchNew=$("#searchSomeone").val();
		alert(searchNew);
		writeNewMessage(startPage,endPage);
	});
});
// DB에 메시지 넣기
//function insert(content,toId){
//	$.ajax({
//		type : 'post',
//		async:true,
//		url : '/petcommunity/getMessagePartner.do',
//		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
//		dataType : "json",
//		data :{
//			"content":content,
//			"toId":toId,
//		},
//		success: function(data) {
//	       	  alert("전송완료")
//	        },
//	        error: function(data){
//	        	console.log('autocomplete error');
//	        }
//		});
//}
// 쪽지 새로 전송 셀렉트
function writeNewMessage(startPage,endPage){
	$.ajax({
		type : 'post',
		async:true,
		url : '/petcommunity/writeNewMessage.do',
		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
		dataType : "json",
		data :{ "startPage":startPage,
			"endPage":endPage,
			"searchNew":searchNew
		},
		 success: function(data) {
			 drwaWriteMessageTable(data);
		 },
		 error: function(data){
      	   console.log('autocomplete error');
         }
	});
}
// message대상 셀렉트
function getMypageMessage(startPage, endPage){
	$.ajax({
		type : 'post',
		async:true,
		url : '/petcommunity/getMessagePartner.do',
		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
		dataType : "json",
		data :{
			"startPage":startPage,
			"endPage":endPage,
			"otherId":otherId,
			"toId":toId,
			"content":content
		},
		success: function(data) {
       	  drawTable(data);
        },
        error: function(data){
        	console.log('autocomplete error');
        }
	});
}
// message대상 화면 구성
function drawTable(data){
	if(startPage==0){
		$('#message-table').empty();	
		/*		h = '<h3>쪽지 확인</h3>';
		$('#message-table').append(h);*/	
		/*headSpan1 = '<span id="showMessage">쪽지 확인</span><span id="slash"> / </span><span id="writeNewMessage">새로 전송</span>';
		$('#message-table').append(headSpan1);*/
		}
	for(var i=0; i<data.messageListSize; i++){
		var checkWho1 = data.messageList[i].messageTarget1;
		var checkWho2 = data.messageList[i].messageTarget2;
		var userId = data.messageList[i].messageSender;
		var sendTime = data.messageList[i].messageSendtime;
		var content = data.messageList[i].messageContents;
		var msgId = data.messageList[i].messageId;
		var iTag = '<td class="sendMsg"><i class="fa fa-paper-plane"></td>';
		var toTr = '';
		// 메시지 송신자 확인
		if(userId==data.loginId){
			userId="나(보냄)";
			iTag = '';
			if(data.loginId!=checkWho1){
				toTr = '<tr class="tr-sendWho">'+
						'<td class="td-sendWho" value="'+checkWho1+'">'+checkWho1+'에게</td>'+
						'</tr>';
			}
			else if(data.loginId!=checkWho2){
				toTr = '<tr class="tr-sendWho">'+
						'<td class="td-sendWho" value="'+checkWho2+'">'+checkWho2+'에게</td>'+
						'</tr>';
			}
		}else{
			userId=userId+"(받음)";
		}
		// 읽은 메시지인지 확인
		var listContent =
			'<div class="mypage-message" value="div">'+
				'<table>'+
				'<colgroup>'+	
				'<col style="width: 20%" />'+
				'<col style="width: 30%" />'+
				'<col style="width: 45%" />'+
				'<col style="width: 5%" />'+
				'</colgroup>'+
				'<tr>'+
					'<td><img class="mypage-messageThumbnail" src="resources/imgs/review_thumbnail'+'/thumbnail_reivew'+'.jpg" alt="썸네일"></td>'+
					'<td class="mypage-messageId" >'+userId+'</td>'+
					'<input type="hidden" value="'+userId.slice(0,-4)+'">'+
					'<td class="mypage-messageTime">'+sendTime+'</td>'+
					iTag+
				'</tr>'+
					toTr+
				'<tr>'+
					'<td colspan="4" class="mypage-messageContent">'+content+'</td>'+
				'</tr>'+
				'</table>'+
				'<div class="div-message">'+
					'<hr>'+
						'<input type="text" name="searchMessage" class="searchMessage" placeholder="메시지를 입력하세요"></input>'+
						'<input type="hidden" class="messageId" value="'+msgId+'"></input>'+
						'<input type="hidden" class="toId" value="'+userId+'"></input>'+
						'<button class="btn-message">전송</button>'+
				'</div>'+
			'</div>'+
			'<hr>';
		
		$('#message-table').append(listContent);	
	}
}

// 새로쪽지 전송 페이지
function drwaWriteMessageTable(data){
	$('#message-table').empty();
	var divDisplay = '<div id="div-memberList"></div><div id="div-chat"></div>';
	$('#div-memberList').empty();
	var headSearch=
		'<div id="div-searchSomeone">'+
		'<input type="text" name="searchSomeone" id="searchSomeone" placeholder="사용자를 검색하세요"></input>'+
		'<button id="btn-searchSomeone">검색</button>'+
		'</div>'+
		'<hr>';
	$('#message-table').append(headSearch);	
	$('#message-table').append(divDisplay);
	for(var i=0; i<data.memberListSize; i++){
		var memberId = data.memberList[i].memberId;
		var memberAddress = data.memberList[i].memberAddress.slice(0,2);
		var iTag = '<td class="sendMsg"><i class="fa fa-paper-plane"></td>';
		var listContent =
			'<div class="mypage-newMessage">'+
				'<table>'+
				'<colgroup>'+	
					'<col style="width: 20%" />'+
					'<col style="width: 30%" />'+
					'<col style="width: 45%" />'+
					'<col style="width: 5%" />'+
				'</colgroup>'+
				'<tr>'+
				'<td><img class="mypage-messageThumbnail" src="resources/imgs/review_thumbnail'+'/thumbnail_reivew'+'.jpg" alt="썸네일"></td>'+
				'<td class="mypage-memberId" value="'+memberId+'">'+memberId+'</td>'+
				'<td class="mypage-memberLocation">'+memberAddress+'</td>'+
				iTag+
			'</tr>'+
				'</table>'+
				'<hr>'+
			'</div>';
		
		$('#message-table').append(listContent);	
	}
}
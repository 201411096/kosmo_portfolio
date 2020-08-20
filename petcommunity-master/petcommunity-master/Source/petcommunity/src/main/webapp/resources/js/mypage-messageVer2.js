//var socket = io("https://192.168.0.18:3000");
//var socket = io("https://115.91.88.227:60005"); // 대회의실용
//var socket = io("https://121.171.119.57:3000"); // 집
var startPage=0;//최초 탭 클릭 시 0~5개의 리스트, 챗만 가져옴
var endPage=6;
var otherId='1';//상대방 id
//var scrollController=0;
var loginId='';
messagePageFlag =1;
$(function(){
	// 메시지를 받았을 때
	//module_socket.js로 이동됨----------------
	//receiveMessage(); 
	// 소켓에 닉네임으로 사용할 id 저장
//	memberId=$("#hiddenId").val();
//	setNickname(memberId);
	//module_socket.js로 이동됨----------------
//	//쪽지 탭 진입시
//	$(".tab-link4").on("click", function(){
//		$('#message-table').css("display","block");
//		startPage=0;
//		endPage=6;
//		otherId='1';
//		$("#div-chat").empty();
//		getMypageMessage(startPage, endPage);
//	})
	// 아이디 검색 이벤트
	$('#btn-searchSomeone').on('click', function(){
		var searchNew='';
		searchNew = $('#searchSomeone').val();
		otherId='1';
		searchId(searchNew);
		$("#div-chat").empty();
		
	});
//	// 아이디 검색 이벤트(onfocus)
//	$('#searchSomeone').on('onfocus', function(){
//		var searchNew='';
//		searchNew = $('#searchSomeone').val();
//		otherId='1';
//		$("#div-chat").empty();
//		searchId(searchNew);
//	})
	// 아이디 검색 이벤트(keyup)
	$('#searchSomeone').on('keyup', function(){
		var searchNew='';
		searchNew = $('#searchSomeone').val();
		otherId='1';
		searchId(searchNew);
		$("#div-chat").empty();
		
	});
	//클릭 이벤트
	$(document).on("click", '.mypage-message', function(){
		$('.mypage-message').css({"background-color": "#fafafa"});
		$(this).css({"background-color": "#ffeb33"});
		otherId=$(this).attr("value");
//		scrollController=1;
		getChat(startPage, endPage);
	});
	$(document).on("click", '.sendMsg', function(){
		var divColorChange = $(this).parent().parent().parent().parent();		
		$('.mypage-message').css({"background-color": "#fafafa"});
		divColorChange.css({"background-color": "#83bb44"});
		otherId=$(this).prev().attr("value");
//		scrollController=1;
		getChat(startPage, endPage);
	});
	// 삭제
	$(document).on('click', '.messageDel', function(){
		if(confirm("삭제하시겠습니까?")){
		var msgId = $(this).attr('value');
		delMessage(msgId);
		sendDelData(msgId, loginId, otherId);
		}
	});
	// 무한스크롤(리스트)
	$("#div-memberList").scroll(function(){
		var innerHeight = $(this).innerHeight(); 
		var scroll=$(this).scrollTop() + $(this).innerHeight(); 
		var height=$(this)[0].scrollHeight; 

		if(scroll >= height){ 
			startPage=endPage;
			endPage+=3;
//			getMypageMessage(startPage, endPage);
		}
	});
	// 전송
	$('#btn-message').on('click', function(){
		if(otherId!='1'){
		var content=$('#writeMessage').val();
			if(content){
				sendMessage(content);
				sendMessageData(content, loginId, otherId);
				$('#writeMessage').val('');
			}
		}
	});
	// 전송 enter key
	$('#writeMessage').on('keypress', function(){
		if(event.keyCode==13 && otherId!='1'){
		var content=$('#writeMessage').val();
			if(content){
				sendMessage(content);
				sendMessageData(content, loginId, otherId);
				$('#writeMessage').val('');
			}
		}
	});

});
// 소켓으로 message 보냄
function sendMessageData(content, loginId, otherId){
	   var dataOptions = new Object();
	   dataOptions.messageContent=content;
	   dataOptions.messageFrom=loginId;
	   dataOptions.messageTo=otherId;
	   socket.emit('sendMessageData', dataOptions);
	}
//module_socket.js로 이동됨
// 소켓으로 massage 받음
//function receiveMessage(){
//	console.log('receiveMessage 함수 호출 확인')
//	socket.on('sendMessageData', function(data){
//	 console.log('receiveMessage evt');
//	 console.log(data.messageContent);
//	 console.log(data.messageFrom);
//	 console.log(data.messageTo);
////	 addMessage(data);
//	 getChat(startPage, endPage);
//	 toastMessage(data.messageContent, data.messageFrom, data.messageTo);
// });
//	socket.on('sendDelData', function(data){
//		console.log('receiveMessageDelete evt');
//		 console.log(data.msgId);
//		 console.log("보낸사람"+data.messageFrom);
//		 console.log(data.messageTo);
//		 getChat(startPage, endPage);
//	});
//}
// module_socket.js로 이동됨
// message toast 알림
//function toastMessage(messageContent, messageFrom, messageTo){
//	toastr["info"](messageFrom+'<a href="/petcommunity/getChatPartner.do?messageFrom='+messageFrom+'">바로가기</a>'+"<br /><br />"+messageContent);
////	toastr["info"](messageFrom+"<br /><br />"+messageContent);
////	toastr.info(messageFrom, messageContent, {timeOut: 5000});
//	toastr.options = {
//			  "closeButton": true,
//			  "debug": false,
//			  "newestOnTop": true,
//			  "progressBar": false,
//			  "rtl": false,
//			  "positionClass": "toast-top-right",
//			  "preventDuplicates": false,
//			  "onclick": false,
//			  "showDuration": 300,
//			  "hideDuration": 1000,
//			  "timeOut": 0,
//			  "extendedTimeOut": 0,
//			  "showEasing": "swing",
//			  "hideEasing": "linear",
//			  "showMethod": "fadeIn",
//			  "hideMethod": "fadeOut",
//			  "tapToDismiss": false
//			}
//}
//module_socket.js로 이동됨----------------
// 소켓 닉네임 로그인 id로 설정
//function setNickname(memberId){
//	   socket.emit('setNickname', memberId);
//	   console.log(memberId);
//	}

// 소켓으로 삭제 메시지 Data 보냄
function sendDelData(msgId, loginId, otherId){
	var dataOptions = new Object();
	dataOptions.msgId=msgId;
	dataOptions.messageFrom=loginId;
	dataOptions.messageTo=otherId;
	console.log(dataOptions.msgId);
	console.log(dataOptions.messageFrom);
	console.log(dataOptions.messageTo);
	socket.emit('sendDelData', dataOptions);
}
function sendMessage(content){
	$.ajax({
		async:true,
		url : '/petcommunity/sendMessage.do',
		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
		dataType : "json",
		data :{
			"startPage":startPage,
			"endPage":endPage,
			"otherId":otherId,
			"content":content
		},
		success: function(data){
			drawChatTable(data);
		},
		error: function(data){
			console.log('autocomplete error');
		}
	});
}
function searchId(searchNew){
	$.ajax({
		async:true,
		url : '/petcommunity/searchId.do',
		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
		dataType : "json",
		data :{
			"startPage":startPage,
			"endPage":endPage,
			"otherId":otherId,
			"searchNew":searchNew
		},
		success: function(data){
			if(data.noSearch=="noSearch"||data.noId=="noSearch"){
				startPage=0;
				endPage=6;
				getMypageMessage(startPage, endPage);
			}
			drwaWriteMessageTable(data);
		},
		error: function(data){
			console.log('autocomplete error');
		}
	});
}

function delMessage(msgId){
	$.ajax({
		async:true,
		url : '/petcommunity/delMessage.do',
		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
		dataType : "json",
		data :{
			"startPage":startPage,
			"endPage":endPage,
			"otherId":otherId,
			"msgId":msgId
		},
		success: function(data){
			alert("삭제완료");
			drawChatTable(data);
		},
		error: function(data){
			console.log('autocomplete error');
		}
	});
}
function getChat(startPage, endPage){
	$.ajax({
		async:true,
		url : '/petcommunity/getChat.do',
		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
		dataType : "json",
		data :{
			"startPage":startPage,
			"endPage":endPage,
			"otherId":otherId
		},
		success: function(data){
			drawChatTable(data);
		},
		error: function(data){
			console.log('autocomplete error');
		}
	});
}
function getMypageMessage(startPage, endPage){
	$.ajax({type : 'post',
		async:true,
		url : '/petcommunity/getMessagePartner2.do',
		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
		dataType : "json",
		data :{
			"startPage":startPage,
			"endPage":endPage,
			"otherId":otherId
		},
		success: function(data){
			drwaWriteMessageTable(data);
		},
		error: function(data){
			console.log('autocomplete error');
		}
		
	});
}
// 문자 전송한 상대의 chat에 메시지 추가
function addMessage(data){
	$.ajax({type : 'post',
		async:true,
		url : '/petcommunity/addMessage.do',
		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
		dataType : "json",
		data :{
			"content":data.messageContent,
			"id":data.messageFrom,
			"otherId":data.messageTo
		},
		success: function(data){
			console.log("ajax: success")
			drawAddMessageTable(data);
		},
		error: function(data){
			console.log('autocomplete error');
		}
		
	});
}
function drawAddMessageTable(data){
	var sender = data.messageSender;
	var sendtime = data.messageSendtime;
	var contents = data.messageContents;
	var messageId = data.messageId;
	loginId = data.loginId;
	var fromWho = '<td class="messageDel" value="'+messageId+'">x</td>';
	if(loginId!=sender){
		chatMessage= '<div class="chatMessageLeft" value="'+sender+'">';
		fromWho = '<td class="messageDel" value="'+messageId+'"></td>';;
	}else{
		sender="나";
		chatMessage= '<div class="chatMessageRight">';
		fromWho = '<td class="messageDel" value="'+messageId+'">X</td>';
	}
	var listContent = 
		chatMessage+
			'<table>'+
			'<colgroup>'+	
			'<col style="width: 20%" />'+
			'<col style="width: 2%" />'+
			'</colgroup>'+
			'<tr>'+
				'<td class="messageTime" value="'+sendtime+'">'+sendtime.slice(5, -3)+'  '+'('+sender+')'+'</td>'+
				fromWho+
			'</tr>'+
			'<tr>'+
				'<td class="messageContent">'+contents+'</td>'+
			'</tr>'+
			'</table>'+
		'</div>'+
		'<div></div>';
	$('#div-chat').append(listContent);
	const $divChat = $('#div-chat'); 
	$divChat.scrollTop($divChat[0].scrollHeight);
	
}

function drawChatTable(data){
	$('#div-chat').empty();
	var chatMessage='';
	const $divChat = $('#div-chat'); 
	$divChat.scrollTop($divChat[0].scrollHeight);
//	if(scrollController==1){
//	const $divChat = $('#div-chat'); 
//		$divChat.scrollTop($divChat[0].scrollHeight);
//		scrollController=0;
//	}
	
	for(var i=0; i<data.messageVOSize; i++){
		var target1 = data.messageVO[i].messageTarget1;
		var target2 = data.messageVO[i].messageTarget2;
		var sender = data.messageVO[i].messageSender;
		var sendtime = data.messageVO[i].messageSendtime;
		var contents = data.messageVO[i].messageContents;
		var messageId = data.messageVO[i].messageId;
		loginId = data.loginId;
		var fromWho = '<td class="messageDel" value="'+messageId+'">x</td>';
		if(loginId!=sender){
			chatMessage= '<div class="chatMessageLeft" value="'+sender+'">';
			fromWho = '<td class="messageDel" value="'+messageId+'"></td>';;
		}else{
			sender="나";
			chatMessage= '<div class="chatMessageRight">';
			fromWho = '<td class="messageDel" value="'+messageId+'">X</td>';
		}
		var listContent = 
			chatMessage+
				'<table>'+
				'<colgroup>'+	
				'<col style="width: 20%" />'+
				'<col style="width: 2%" />'+
				'</colgroup>'+
				'<tr>'+
					'<td class="messageTime" value="'+sendtime+'">'+sendtime.slice(5, -3)+'  '+'('+sender+')'+'</td>'+
					fromWho+
				'</tr>'+
				'<tr>'+
					'<td class="messageContent">'+contents+'</td>'+
				'</tr>'+
				'</table>'+
			'</div>'+
			'<div></div>';
		$('#div-chat').append(listContent);
		const $divChat = $('#div-chat'); 
		$divChat.scrollTop($divChat[0].scrollHeight);
	}
}

function drwaWriteMessageTable(data){
	if(startPage==0){
		$('#div-memberList').empty();
	}
	var title='';
	if(data.memberList!=null){
		title='검색한 사용자';
	}else{
		title='대화중인 상대'
	}
	var listTitle ='<h4>'+title+'</h4><hr>';
	$('#div-memberList').append(listTitle);
	for (var i=0; i<data.messagePartnerListSize; i++){
		var userId='';
		var tdInfo='';
		if(data.memberList!=null){
			userId=data.memberList[i].memberId;
			userAdress=data.memberList[i].memberAddress;
			tdInfo=userAdress.slice(0, 2);
			
		}else{
		userId = data.messagePartnerList[i];
		tdInfo='대화중';
		}
//		var userId = data.messagePartnerList[i].split("/")[0];
/*		var content = data.messagePartnerList[i].split("/")[1];
		var sendTime = data.messagePartnerList[i].split("/")[2];*/
//		var userId = data.messageList[i].messageSender;
//		var sendTime = data.messageList[i].messageSendtime;
//		var content = data.messageList[i].messageContents;
//		var msgId = data.messageList[i].messageId;
		var tdTag = '<td colspan="4" class="mypage-messageContent">'+tdInfo+'</td>';
		var iTag = '<td class="sendMsg"><i class="fa fa-paper-plane"></td>';
		var listContent =
		'<div class="mypage-message" value="'+userId+'">'+
		'<table>'+
		'<colgroup>'+	
		'<col style="width: 20%" />'+
		'<col style="width: 75%" />'+
//		'<col style="width: 45%" />'+
		'<col style="width: 5%" />'+
		'</colgroup>'+
		'<tr>'+
			'<td><img class="mypage-messageThumbnail" src="resources/imgs/review_thumbnail'+'/thumbnail_reivew'+'.jpg" alt="썸네일"></td>'+
			'<td class="mypage-messageId" value="'+userId+'">'+userId+'</td>'+
//			'<td></td>'+
//			'<td class="mypage-messageTime" value="'+sendTime+'">'+sendTime.slice(2,-3)+'</td>'+
			'<input type="hidden" value="'+userId+'"/>'+
			iTag+
		'</tr>'+
		'<tr>'+
			tdTag+
//			'<td colspan="4" class="mypage-messageContent">'+'대화중'+'</td>'+
		'</tr>'+
		'</table>'+
		'</div>'+
		'<hr>';
		
		$('#div-memberList').append(listContent);
	}
	
	
}
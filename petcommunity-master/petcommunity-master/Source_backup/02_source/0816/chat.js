//var socket = io('https://192.168.0.24:3000');
//var socket = io("https://192.168.0.18:3000");   // 내부ip  
//var socket = io("https://115.91.88.227:60005"); // 대회의실용
//var socket = io("https://121.171.119.57:3000"); // 집

var curRoomName;
var memberId;

setMemberValue();			// 멤버 아이디값 지정
setCurRoomName();		// 현재 입장할 방 세팅
selectRoom(memberId); // 방에 참가
listenAndAppendChatMessage('.messages', '<li class= "list-group-item">')
formSetting();
documentPreventKeyDown();
chatLocationEventHandler();
chatMessageBoxEnterListener();
socketEventHandling();
windowCloseEvent();
//$('#chatLocation').on('change', function(){
//	selectRoom(memberId);
//});

function windowCloseEvent(){
	$(window).on('unload', function(){
		var roomInfo = new Object();
		roomInfo.prev = curRoomName; // 이전 방 정보
		setCurRoomName(curRoomName);
		roomInfo.cur = '__exit__'
		roomInfo.memberId = memberId;
		socket.emit('joinRoom', roomInfo);
	})
}

function chatLocationEventHandler(){
	$('#chatLocation').on('change', function(){
		selectRoom(memberId);
	});	
}

function formSetting(){
	$('form').submit(function(e) {
	    e.preventDefault(); 
	    var messageObject = new Object();
	    messageObject.roomName = curRoomName;
	    messageObject.messageContent = $('#m').val();
	    messageObject.memberId = memberId;
	    console.log('check in submit ... ' + messageObject.roomName);
	    console.log('check in submit ... ' + messageObject.messageContent);
	    console.log('check in submit ... ' + messageObject.memberId);
	    socket.emit('chat message', messageObject);
	    $('#m').val('');
	    return false;
	});	
}
function chatMessageBoxEnterListener(){
	$('#m').on('keydown', function(e){
		if(e.keyCode===13){
			$('#messageSendBtn').click();
		}
	});
}

function documentPreventKeyDown(){
	document.addEventListener('keydown', function(event) {
		  if (event.keyCode === 13) {
		    event.preventDefault();
		  };
	}, true);
}

function selectRoom(memberId){
	var roomInfo = new Object();
	roomInfo.prev = curRoomName; // 이전 방 정보
	setCurRoomName(curRoomName);
	roomInfo.cur = curRoomName; // 이후 방 정보
	roomInfo.memberId = memberId;
	socket.emit('joinRoom', roomInfo);
}

function listenAndAppendChatMessage(targetElement, componentElement){
	socket.on('chat message', function(msg){
		console.log('check in socketonevent ...');
		$(targetElement).append($(componentElement).text(msg));
		$(targetElement).scrollTop($(targetElement)[0].scrollHeight); // 스크롤을 맨 아래로..
	});	
}

function socketEventHandling(){
	console.log('socketEventHandling function 호출 확인');
	socket.on('eventHandling', function(socketEvent){
		if(socketEvent=='clearMessageBox'){
			console.log('clearMessage event 확인');
			$('div.messages').empty();
		}
		if(socketEvent=='exitChat'){
			console.log('exitChat event 확인');
			var roomInfo = new Object();
			roomInfo.prev = curRoomName; // 이전 방 정보
			roomInfo.cur = 'tempRoom'; // 이후 방 정보
			roomInfo.memberId = memberId;
			socket.emit('joinRoom', roomInfo);
			window.close();
		}
	});
}

function setCurRoomName(){ // 현재 방 설정
	curRoomName = $('#chatLocation').val();
	console.log('curRoomName ... ' + curRoomName);
}

function setMemberValue(){
	if($('#memberId').val()!=""){
		memberId = $('#memberId').val();
	}else{
		memberId = "tempMember";
	}
}
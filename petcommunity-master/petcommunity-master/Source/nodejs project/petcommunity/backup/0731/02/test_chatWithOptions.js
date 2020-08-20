var socket = io("https://192.168.0.18:3000");

socket.on('chat message', function(msg){
	console.log('check in socketonevent ...');
  $('#messages').append($('<li>').text(msg));
});
var curRoomName = $('#option1').val();
var memberId;
if($('#memberId').val()!=""){
	memberId = $('#memberId').val();
}else{
	memberId = "tempMember";
}
selectRoom(curRoomName, memberId);
$('#option1').on('change', function(){
	selectRoom(curRoomName, memberId);
});
function selectRoom(curRoomName, memberId){
//	console.log('check in selectRoom ... ' + memberId);
	var roomInfo = new Object();
	roomInfo.prev = curRoomName; // 이전 방 정보
	curRoomName = $('#option1').val();
	roomInfo.cur = curRoomName; // 이후 방 정보
	roomInfo.memberId = memberId;
	socket.emit('joinRoom', roomInfo);
}
$('form').submit(function(e) {
    e.preventDefault(); 
//    socket.emit('chat message', $('#m').val());
    var messageObject = new Object();
    messageObject.roomName = $('#option1').val();
    messageObject.messageContent = $('#m').val();
    messageObject.memberId = memberId;
    console.log('check in submit ... ' + messageObject.roomName);
    console.log('check in submit ... ' + messageObject.messageContent);
    console.log('check in submit ... ' + messageObject.memberId);
    socket.emit('chat message', messageObject);
    $('#m').val('');
    return false;
});
//var socket = io("https://192.168.0.18:3000");   // 내부ip
//var socket = io("https://121.171.119.57:3000"); // 집
var socket = io("https://115.91.88.227:60005"); // 대회의실용

setNickname();			// 소켓의 닉네임을 설정하는 함수
receiveMessage();		// 쪽지를 받는 함수
var messagePageFlag =0; 
//소켓 닉네임을 로그인 id로 설정
function setNickname(){
	var memberId=$("#hiddenId").val();
	   socket.emit('setNickname', memberId);
	   console.log(memberId);
}
function toastMessage(messageContent, messageFrom, messageTo){
	toastr["info"](messageFrom+'<a href="/petcommunity/getChatPartner.do?messageFrom='+messageFrom+'">바로가기</a>'+"<br /><br />"+messageContent);
	toastr.options = {
			  "closeButton": true,
			  "debug": false,
			  "newestOnTop": true,
			  "progressBar": false,
			  "rtl": false,
			  "positionClass": "toast-top-right",
			  "preventDuplicates": false,
			  "onclick": false,
			  "showDuration": 300,
			  "hideDuration": 1000,
			  "timeOut": 0,
			  "extendedTimeOut": 0,
			  "showEasing": "swing",
			  "hideEasing": "linear",
			  "showMethod": "fadeIn",
			  "hideMethod": "fadeOut",
			  "tapToDismiss": false
			}
}
function receiveMessage(){
	socket.on('sendMessageData', function(data){
	 if(messagePageFlag==1){
		 getChat(startPage, endPage);
	 }
	 toastMessage(data.messageContent, data.messageFrom, data.messageTo);
 });
	socket.on('sendDelData', function(data){
		 if(messagePageFlag==1){
			 getChat(startPage, endPage);
		 }
	});
}
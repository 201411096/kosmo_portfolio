<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<script src="https://cdnjs.cloudflare.com/ajax/libs/socket.io/2.3.0/socket.io.js"></script>
<script src="./resources/bootstrap_template/template_01/js/jquery-3.2.1.min.js"></script>
<!-- <script src="./resources/js/util/module_socket.js"></script> -->
<script>
var socket = io("https://192.168.0.16:3000");
var mainPublicData;
getPublicData();
waitingPublicData();
function waitingPublicData(){
   console.log('waitingPublicData ...');
   socket.on('getMainPublicData', function(data){
      mainPublicData = data;
      console.log('data길이' + data.length);
   });
}
function getPublicData(){
   console.log('getPublicData ...');
   var dataOptions = new Object();
   socket.emit('getMainPublicData', dataOptions);
}
</script>
</body>
</html>
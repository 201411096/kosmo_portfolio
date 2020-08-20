<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <title>Socket.IO chat</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0">
    <link rel="stylesheet" type="text/css" href="./resources/css/chat.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>
	<div class="chatWrapper">
		<div class="chatContainer">
			<select class="form-control" id="chatLocation">
				<option value="전체">전체</option>
				<option value="서울">서울</option>
				<option value="경기">경기</option>
				<option value="인천">인천</option>
				<option value="강원">강원</option>
				<option value="충북">충북</option>
				<option value="충남">충남</option>
				<option value="대전">대전</option>
				<option value="세종">세종</option>
				<option value="전북">전북</option>
				<option value="전남">전남</option>
				<option value="광주">광주</option>
				<option value="경북">경북</option>
				<option value="경남">경남</option>
				<option value="부산">부산</option>
				<option value="울산">울산</option>
				<option value="대구">대구</option>
				<option value="제주">제주</option>
			</select>
			<div class="messages"></div>
			<input type="hidden" id="memberId" value="${memberInfo.memberId}">
			<form class="messageForm">
				<div class="input-group">
					<input type="text" class="form-control" id="m">
					<span class="input-group-btn">
						<button type="submit" class="btn btn-primary" id="messageSendBtn">Send</button>
					</span>
				</div>
			</form>
		</div>
	</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/socket.io/2.3.0/socket.io.js"></script>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="./resources/js/util/module_socket.js"></script>
<script src="./resources/js/chat.js"></script>
</body>
</html>
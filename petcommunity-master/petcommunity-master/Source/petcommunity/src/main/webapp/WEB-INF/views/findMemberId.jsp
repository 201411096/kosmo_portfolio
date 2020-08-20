<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="./resources/css/mypage.css">
<link rel="stylesheet" type="text/css"
   href="https://cdn.rawgit.com/moonspam/NanumSquare/master/nanumsquare.css"> 
</head>
<body>
	
		<div class="tedoory5">
		<h4>아이디 찾기</h4>
			<div class="form-group">
				<label for="memberName">이름</label> <input type="text"
					name="memberName" id="memberName" />
			</div>
			<div class="form-group">
				<label for="memberEmail">이메일</label> <input type="text"
					name="memberEmail" id="memberEmail" />
			</div>
			<input type="submit" id="idBtn" value="아이디 찾기">
		</div>
	
	<br>
	
		<div class="tedoory5">
		<h4>비밀번호 찾기</h4>
			<div class="form-group">
				<label for="memberId">아이디</label> <input type="text" name="memberId"
					id="memberId" />
			</div>
			<div class="form-group">
				<label for="memberEmail2">이메일</label> <input type="text"
					name="memberEmail2" id="memberEmail2" />
			</div>
			<input type="submit" id="passBtn" value="비밀번호 찾기">
		</div>
	<script
		src="./resources/bootstrap_template/template_01/js/jquery-3.2.1.min.js"></script>
	<script
		src="./resources/bootstrap_template/template_01/js/bootstrap.min.js"></script>
	<script
		src="./resources/bootstrap_template/template_01/js/jquery.slicknav.min.js"></script>
	<script
		src="./resources/bootstrap_template/template_01/js/owl.carousel.min.js"></script>
	<script
		src="./resources/bootstrap_template/template_01/js/circle-progress.min.js"></script>
	<script src="./resources/js/signup.js"></script>
	<script src="./resources/js/getLocation.js"></script>
	<script src="./resources/bootstrap_template/template_01/js2/main.js"></script>
</body>
</html>
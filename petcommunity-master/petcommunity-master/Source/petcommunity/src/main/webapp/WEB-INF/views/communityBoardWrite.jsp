<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="../views/header.jsp"%>
<html>
<head>
<!-- 합쳐지고 최소화된 최신 CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<!-- 부가적인 테마 -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="./resources/css/communityBoardWrite.css" />
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="resources/js/communityBoardWrite.js"></script>

<title>oki doghere커뮤니티</title>
</head>
<body>
<section id='section'>

	<div class="container">
	<header>
			<h1> 게시글 작성하기</h1>
	</header>
	<br/><br/>
		<form action="writeIntoBoard.do" method='post' enctype="multipart/form-data" name="commitWrite">
			<!-- 도시 고를 수 있는 셀렉트박스 -->
			<div class="col-xs-0 col-sm-2">
				<select class="form-control" id='cityName' name ='cityName'>
					<option >시/도</option>
					<option >서울</option>
					<option >인천</option>
					<option >대전</option>
					<option >광주</option>
					<option >울산</option>
					<option >대구</option>
					<option >부산</option>
					<option >세종</option>
					<option >경기</option>
					<option >강원</option>
					<option >충북</option>
					<option >충남</option>
					<option >전북</option>
					<option >전남</option>
					<option >경북</option>
					<option >경남</option>
					<option >제주</option>
				</select>
			</div>
			<div class="col-xs-0 col-sm-2">
				<select class="form-control" id='province' name ='province'>
					<option >구/군</option>					
				</select>
			</div>
			
			<!-- 글쓰기 -->
			<br/><br/>
			<div class="form-group">
				<label>글 제목</label> 
				<input type="text" class="form-control" name="communityboardTitle" id="communityboardTitle" placeholder="title">
			</div>
			<div class="form-group">
				<label>글 내용</label>
				<textarea class="form-control" rows="15" name="communityboardContent" id="communityboardContent" placeholder="contents"></textarea>
			</div>
			<div class="form-group" id="file-group">
				<ul class="list-group" id="file-list">
					<li class="list-group-item"> <input type="file" multiple="multiple" name="file" class="file" accept="image/gif, image/jpeg, image/png, image/jpg"/> </li>
				</ul>
			</div>
			<div class="col-md-4 col-md-offset-10">			
			<span class="input-group-btn">
				<button type="button" class="btn btn-default" id='commitWrite'>작성완료</button>
				<button type="button" class="btn btn-default" id='cancelWrite'>글쓰기 취소</button>
			</span>
			</div>			
		</form>

	</div>
	</section>
	<%@include file="../views/footer.jsp"%>
</body>
</html>
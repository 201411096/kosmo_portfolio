<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../views/header.jsp"%>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- 합쳐지고 최소화된 최신 CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<!-- 부가적인 테마 -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<!-- <link rel="stylesheet" href="./resources/bootstrap_template/template_01/css/style.css" /> -->
<link rel="stylesheet" href="./resources/css/qnaBoardModify.css" />
<link rel="stylesheet" type="text/css" href="https://cdn.rawgit.com/moonspam/NanumSquare/master/nanumsquare.css">

<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="resources/js/qnaBoardModify.js"></script>

<title>게시판</title>
</head>
<body>
<section id="container">
	<div class="container">
	<div id="qnaTitle">
			<h4>Qna center</h4>
			<p id="qnasubtitle">고객문의 수정페이지 입니다.</p>
		</div>
		<br /> <br />
		<form action="/petcommunity/modify.do" method='get' name="commitWrite" enctype="multipart/form-data">

			<!-- 글쓰기 -->
			<br /> <br /> <input type="hidden" class="form-control" name="questionboardId"
				id="questionboardId" value="${qnaContent.questionboardId}">

			<div class="form-group">
				<p>글 제목</p> 
				<input type="text" class="form-control"
					name="questionboardTitle" id="questionboardTitle"
					placeholder="제목" value="${qnaContent.questionboardTitle}">
			</div>
			<div class="form-group">
				<p>글 내용</p>
				<textarea class="form-control" rows="5" name="questionboardContent"
					id="questionboardContent" placeholder="내용을 입력하세요.">${qnaContent.questionboardContent }</textarea>
			</div>
					<button type="submit" class="btn btn-default" id='commitWrite'>완료</button>
					<a href="/petcommunity/cs.do" class="btn btn-default" id='cancelWrite'> 취소</a>
		</form>
	</div>
	</section>
</body>
</html>
<%@include file="../views/footer.jsp"%>
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
<link rel="stylesheet" href="./resources/css/lostboardupdate.css" />
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<title>게시판</title>
</head>
<body>
<section id='section'>
	<div class="container">
		<header>
			<h1 id="board_title">실종 게시글 수정</h1>
		</header>
		<br /> <br />
		<form action="/petcommunity/updateLostBoard.do" method='post'
			enctype='multipart/form-data' id="lostBoardWriteForm">
			<!-- 글쓰기 -->
			<br /> <br />
			<div class="form-group">
				<label>상태</label> <select class="form-control" id='status'
					name='lostboardStatus'>
					
					<option value="찾는 중" <c:if test="${lostBoardContent.lostboardStatus eq '찾는 중'}">selected</c:if>>찾는 중</option>
					<option value="찾음" <c:if test="${lostBoardContent.lostboardStatus eq '찾음'}">selected</c:if>>찾음</option>
				</select>
			</div>
			<div class="form-group">
				<label>위치</label>
				<div id="map"></div>
			</div>
			<c:if test="${not empty sessionScope.memberVO}">
				<div class="form-group">
					<span><input type="hidden" class="form-control"
						name="memberId" placeholder="memberId"
						value="${sessionScope.memberVO.memberId}"> </span>
				</div>
			</c:if>
			<input type="hidden" name="lostboardId" id="lostboardId" value="${lostBoardContent.lostboardId}">
			<input type="hidden" name="lostboardX" id="lostboardX" value="${lostBoardContent.lostboardX}">
			<input type="hidden" name="lostboardY" id="lostboardY" value="${lostBoardContent.lostboardY}">
			<input type="hidden" name="lostboardLocation" id="lostboardLocation"
				value="${lostBoardContent.lostboardLocation}">
			<input type="hidden" name="lostboardLocationType" id="lostboardLocationType" value="">
			<div class="form-group">
				<label>글 제목</label> <input type="text" class="form-control"
					name="lostboardTitle" id="lostboardTitle" placeholder="title" value="${lostBoardContent.lostboardTitle}">
			</div>
			<div class="form-group">
				<label>글 내용</label>
				<textarea class="form-control" rows="5" name="lostboardContent"
					placeholder="contents">${lostBoardContent.lostboardContent}</textarea>
			</div>
			<div class="form-group" id="file-group">
				<ul class="list-group" id="file-list">
					<c:set var="count" value="0" scope="page" />
					<c:forEach items="${fileNameList}" var = "fileName">
						
						<li class="list-group-item">
							<a class="btn btn-default file-button">파일 삭제</a>
							${fileName}
							<input type="hidden" name="filename" readonly="readonly" value="${fileName}">
						</li>
						<c:set var="count" value="${count + 1}" scope="page"/>
					</c:forEach>
					<li class="list-group-item"> <input type="file" name="file" class="file" accept="image/gif, image/jpeg, image/png"/> </li>
				</ul>
			</div>
			<div class="col-md-4 col-md-offset-10">
				<span class="input-group-btn">
					<button type="submit" class="btn btn-default" id="writeButton">수정완료</button>
					<button type="button" class="btn btn-default" id='cancelWrite'>수정
						취소</button>
				</span>
			</div>
		</form>
	</div>
</section>	
	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=41ccd37d4644ab2ed5ed67441dda1abb&libraries=services"></script>
	<script src="resources/js/lostboardupdate.js"></script>
	
</body>
</html>
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
<link rel="stylesheet" href="./resources/css/findboardcontent.css" />
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<title>게시판</title>
</head>
<body>
	<div class="container">
		<div class="card">
			<ul class="list-group list-group-flush">
				<li class="list-group-item">번호 : ${findBoardContent.findboardId}</li>
				<li class="list-group-item">제목 : ${findBoardContent.findboardTitle}</li>
				<li class="list-group-item">상태 : ${findBoardContent.findboardStatus}</li>
				<li class="list-group-item">작성자 : ${findBoardContent.findboardName}</li>
				<li class="list-group-item">전화번호 : ${findBoardContent.findboardTel}</li>
				<li class="list-group-item">업로드시간 : ${findBoardContent.findboardUploadtime}</li>
				<li class="list-group-item"><div id="map"></div></li>
				<li class="list-group-item">
					<div id="carouselExampleControls" class="carousel slide"
						data-ride="carousel">
						<div class="carousel-inner">
<!-- 						파일이 없을 경우 default image -->
						<c:if test="${not empty fileflag}">
							<div class="carousel-item active" data-interval='3000'>
								<img src="${pageContext.request.contextPath}/resources/imgs/findboard/default/1.png" alt="...">
							</div>
						</c:if>
						<c:if test="${empty fileflag}">
							<div class="carousel-item active" data-interval='3000'>
								<img src="${pageContext.request.contextPath}/resources/imgs/findboard/${findBoardContent.findboardId}/${file.name}" alt="이미지가 존재하지 않습니다.">
							</div>
						</c:if>
							<c:forEach items="${fileList}" var="file">
								<div class="carousel-item" data-interval='3000'>
									<img src="${pageContext.request.contextPath}/resources/imgs/findboard/${findBoardContent.findboardId}/${file.name}" alt="이미지가 존재하지 않습니다.">
								</div>							
							</c:forEach>
						</div>
						<a class="carousel-control-prev" href="#carouselExampleControls"
							role="button" data-slide="prev"> <span
							class="carousel-control-prev-icon" aria-hidden="true"></span> <span
							class="sr-only">Previous</span>
						</a> <a class="carousel-control-next" href="#carouselExampleControls"
							role="button" data-slide="next"> <span
							class="carousel-control-next-icon" aria-hidden="true"></span> <span
							class="sr-only">Next</span>
						</a>
					</div>
				</li>
			</ul>
			<div class="card-body">
				<p class="card-text">${findBoardContent.findboardContent}</p>
				<form id="findBoardContentForm" action='' method='post'>	
					<input type='hidden' id='memberId' name='memberId' value='${findBoardContent.memberId}'>
					<input type='hidden' id='findboardId' name='findboardId' value='${findBoardContent.findboardId}'>
					<input type='hidden' id='findboardTitle' name='findboardTitle' value='${findBoardContent.findboardTitle}'>
					<input type='hidden' id='findboardStatus' name='findboardStatus' value='${findBoardContent.findboardStatus}'>
					<input type='hidden' id='findboardName' name='findboardName' value='${findBoardContent.findboardName}'>
					<input type='hidden' id='findboardTel' name='findboardTel' value='${findBoardContent.findboardTel}'>
					<input type='hidden' id='findBoardContent' name='findBoardContent' value='${findBoardContent.findboardContent}'>
					<input type='hidden' id='findboardUploadtime' name='findboardUploadtime' value='${findBoardContent.findboardUploadtime}'>
					<input type="hidden" id="findboardX" name="findboardX" value="${findBoardContent.findboardX}">
					<input type="hidden" id="findboardY" name="findboardY" value="${findBoardContent.findboardY}">
					<input type="hidden" id="findboardLocation" name="findboardLocation" value="${findBoardContent.findboardLocation}">
					<div id="button-container">
						<span class="input-group-btn">
							<button type="button" class="btn btn-default" id="listButton">글
								목록</button>
							<c:if test="${isWriterFlag == 1}">
								<button type="button" class="btn btn-default" id="updateButton">글
									수정</button>
								<button type="button" class="btn btn-default" id='deleteButton'>글
									삭제</button>
							</c:if>
						</span>
					</div>
				</form>
			</div>
		</div>
<!-- 		<br> -->
<!-- 		<div> -->
<!-- 			<div class="comment-container"> -->
<!-- 			</div> -->
<!-- 			<div class="input-group"> -->
<!-- 				<textarea class="form-control"></textarea>   -->
<!-- 				<span class="input-group-btn" id="commentBtn"> -->
<!-- 					<button class="btn btn-primary btn-lg">작성</button> -->
<!-- 				</span> -->
<!-- 			</div> -->
<!-- 		</div> -->
	</div>

	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=41ccd37d4644ab2ed5ed67441dda1abb&libraries=services"></script>
		<script src="resources/js/findboardcontent.js"></script>
</body>
</html>
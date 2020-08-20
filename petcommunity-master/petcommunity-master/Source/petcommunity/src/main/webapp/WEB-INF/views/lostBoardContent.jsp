<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="../views/header_kys.jsp"%>
<html>
<head>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css"> -->
<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css"> -->
<!-- <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script> -->

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="./resources/css/lostboardcontent.css" />


<title>게시판</title>
</head>
<body>
	<section id='section'>
	<div class="container">
		<div class="card">
			<ul class="list-group list-group-flush" >
				<li class="list-group-item bgColor" >번호 : ${lostBoardContent.lostboardId}</li>
				<li class="list-group-item bgColor" >제목 : ${lostBoardContent.lostboardTitle}</li>
				<li class="list-group-item bgColor" ><a href="/petcommunity/getChatPartner.do?messageFrom=${lostBoardContent.memberId}" target="_blank" onClick="window.open(this.href, '', 'width=562, height=890'); return false;"><span id="contentWriter">작성자 : ${lostBoardContent.memberId}</span></a></li>
				<li class="list-group-item bgColor" >상태 : ${lostBoardContent.lostboardStatus}</li>
				<li class="list-group-item bgColor" >업로드시간 : ${lostBoardContent.lostboardUploadtime}</li>
				<li class="list-group-item"><div id="map"></div></li>
				<li class="list-group-item">
				<div class='bigPictureWrapper'>
					<div class='bigPicture'>
					</div>
				</div>
					<div id="carouselExampleControls" class="carousel slide"
						data-ride="carousel">
						<div class="carousel-inner">
<!-- 						파일이 없을 경우 default image -->
						<c:if test="${not empty fileflag}">
							<div class="carousel-item active" data-interval='3000'>
								<img src="${pageContext.request.contextPath}/resources/imgs/lostboard/default/1.png" alt="...">
							</div>
						</c:if>
						<c:if test="${empty fileflag}">
							<div class="carousel-item active" data-interval='3000'>
								<img src="${pageContext.request.contextPath}/resources/imgs/lostboard/${lostBoardContent.lostboardId}/${file.name}" alt="이미지가 존재하지 않습니다.">
							</div>
						</c:if>
							<c:forEach items="${fileList}" var="file">
								<div class="carousel-item" data-interval='3000'>
									<img src="${pageContext.request.contextPath}/resources/imgs/lostboard/${lostBoardContent.lostboardId}/${file.name}" alt="이미지가 존재하지 않습니다.">
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
				<li class="list-group-item boardcontent-container">
					<p class="card-text" id='contentText'>${lostBoardContent.lostboardContent}</p>
				</li>
			</ul>
			<div class="card-body">
				<form id="lostBoardContentForm" action='' method='post'>	
					<input type='hidden' id='memberId' name='memberId' value='${lostBoardContent.memberId}'>
					<input type='hidden' id='lostboardId' name='lostboardId' value='${lostBoardContent.lostboardId}'>
					<input type='hidden' id='lostboardTitle' name='lostboardTitle' value='${lostBoardContent.lostboardTitle}'>
					<input type='hidden' id='lostboardStatus' name='lostboardStatus' value='${lostBoardContent.lostboardStatus}'>
					<input type='hidden' id='lostBoardContent' name='lostBoardContent' value='${lostBoardContent.lostboardContent}'>
					<input type='hidden' id='lostboardUploadtime' name='lostboardUploadtime' value='${lostBoardContent.lostboardUploadtime}'>
					<input type="hidden" id="lostboardX" name="lostboardX" value="${lostBoardContent.lostboardX}">
					<input type="hidden" id="lostboardY" name="lostboardY" value="${lostBoardContent.lostboardY}">
					<input type="hidden" id="lostboardLocation" name="lostboardLocation" value="${lostBoardContent.lostboardLocation}">
					<div id="button-container">
						<span class="input-group-btn">
							<button type="button" class="btn btn-default" id="listButton">글
								목록</button>
							<c:if test="${isWriterFlag != 1 }">
								<a class="btn btn-default" href="findBoardWrite.do" id="writeBtn">목격 글 작성</a>
							</c:if>
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
	</div>
	</section>
	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=41ccd37d4644ab2ed5ed67441dda1abb&libraries=services"></script>
		<script src="resources/js/lostboardcontent.js"></script>
</body>
</html>
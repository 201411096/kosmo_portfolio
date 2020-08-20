<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="../views/header.jsp"%>
<html>
<head>
<!-- 합쳐지고 최소화된 최신 CSS -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<!-- 부가적인 테마 -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="./resources/css/communityBoardContent.css" />
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="resources/js/communityBoardContent.js"></script>		

<title>oki doghere커뮤니티</title>
</head>
<body>
	<section id = "section">
	<div class="container">

	<br/><br/>
		
		
			<!-- 글내용 -->
			<br/><br/><hr/>
			<div class="form-group">
				<input type="hidden" id="forRecommend" value="${boardContent.communityboardId}">
				<span id=communityContentTitle>${boardContent.communityboardLocation}</span>
				<h1>${boardContent.communityboardTitle}</h1>
				<div id=communityContentWriter>${boardContent.memberId} <br/></div>
				<div id=communityContentTime>${boardContent.communityboardUploadtime }</div>
			</div>
			<hr/>
			
			<div class="form-group">
				<div>
					<c:forEach items="${boardContentImg}" var="file">
						<div>
							<img src="resources/imgs/communityboard/${boardContent.communityboardId}/${file.name}"  alt="..." id='communityBoardContentImg'>															
						</div>
					</c:forEach>
				</div>
				<span id=communityContentContent>${boardContent.communityboardContent}</span>
			</div>
			
			<br/><br/>
			<c:if test="${not empty sessionScope.memberVO}">
				<img src='resources/imgs/communityboard/like/like.PNG' id='like'>
				<span class='text-primary' id='recommendToContent'>추천</span>
			</c:if>
			<!-- 댓글내용 -->		
			<hr/>
			<div class="form-group">
				<h4>댓글</h4>	
				<hr/>			
				<table>			
					<c:forEach items="${boardComment}" var = "boardComment">
							
						<tr><td id="commentWriter">${boardComment.memberId}</td></tr>								
						<tr><td id="commentContent">${boardComment.boardcommentContent}</td></tr>														
						<tr><td id="commentUploadTime">
						${boardComment.boardcommentUploadtime}
						<c:if test="${boardComment.memberId ne sessionScope.memberVO.memberId}">
							<hr/>
						</c:if>
						</td></tr>
						<c:if test="${boardComment.memberId eq sessionScope.memberVO.memberId
										or sessionScope.memberVO.memberFlag eq '1'}">
							<tr><td id="commentDelete">	
							
								<a href="#" class="commentDelete" id='commentDelete'>삭제하기</a>
								<input type="hidden" id="boardcommentId" value="${boardComment.boardcommentId}">
								<input type="hidden" id="communityboardId" value="${boardContent.communityboardId}">
								<hr/>
							</td></tr>
						</c:if>
																
					</c:forEach>
						
				</table>
			</div>			
			
			<form action="writeComment.do?">
			<div class="form-group">
				<label><span id='writeComment'>댓글달기</span></label>
				
				<input type='hidden' name='communityboardId' value='${boardContent.communityboardId}'>
				<c:choose>
				    <c:when test="${empty sessionScope.memberVO}">
				        <textarea class="form-control" rows="5" id="commentTextarea" name="communityboardContent" placeholder="로그인 이후 이용 가능합니다" disabled></textarea>
				    </c:when>			
				
				    <c:otherwise>
				        <textarea class="form-control" rows="5" id="commentTextarea" name="boardcommentContent" placeholder="댓글입력"></textarea>
				    </c:otherwise>			
				</c:choose>
			</div>
			
			<div class="col-xs-10 col-sm-10">	
				<button type="button" class="btn btn-default" id='goToList'>목록보기</button>
				<c:if test="${boardContent.memberId eq sessionScope.memberVO.memberId or sessionScope.memberVO.memberFlag eq '1'}">
					<button type="button" class="btn btn-default" id="communityBoardModify">수정하기</button>
					<button type="button" class="btn btn-default" id="communityBoardDelete" value="${boardContent.communityboardId}">삭제하기</button>
				</c:if>
				</div>	
				<span class="input-group-btn">
					<button type="submit" class="btn btn-default" id='commentBtn'>등록</button>
				</span>	
				
				</form>	
				
		</div>	
		</section>
		<%@include file="../views/footer.jsp"%>
</body>
</html>
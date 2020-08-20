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
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link rel="stylesheet" href="resources/css/qnaBoardContent.css">
<!-- <link rel="stylesheet" -->
<!-- 	href="./resources/bootstrap_template/template_01/css/style.css" /> -->
<link rel="stylesheet" type="text/css"
	href="https://cdn.rawgit.com/moonspam/NanumSquare/master/nanumsquare.css">
<title>고객문의</title>
</head>
<body>
	<section id="container">
		<h4 id="qnatitle">Qna center</h4>
		<p id="qnasubtitle">고객문의 게시판입니다.</p>
		<div id="qnaTable">
		<table>
			<tr>
				<td id="title">제목</td>
				<td id="content">${qnaContent.questionboardTitle}</td>
			</tr>
			<tr>
				<td id="title">작성자</td>
				<td id="content">${qnaContent.memberId}</td>
			</tr>
			<tr>
				<td id="title">작성일</td>
				<td id="content">${qnaContent.questionboardUploadtime}</td>
			</tr>
			<tr>
				<td id="title">조회수</td>
				<td id="content">${qnaContent.questionboardReadcount}</td>
			</tr>
			<tr>
				<td colspan="2" id="content-content">
				<c:if test="${not empty file}">
				<img id="qnacontentImage" alt="없음" src="resources/imgs/qnaboard/${qnaContent.questionboardId}/${file.name}" /><br><br>
				</c:if>
				${qnaContent.questionboardContent}
					<input type="hidden" name="questionboardGroupId"
					id="questionboardGroupId"
					value="${qnaContent.questionboardGroupId}" />
					<input type="hidden" name="questionboardAnswerflag"
					id="questionboardAnswerflag"
					value="${qnaContent.questionboardAnswerflag}"/>
				</td>
			</tr>
		</table>
		</div>
		<div id="mulBtn">
			<c:if test="${admin eq '1' && groupListSize eq 1}">
				<a href="/petcommunity/reply.do?questionboardGroupId=${qnaContent.questionboardGroupId}"
					class="btn btn-default" id="replyBtn">답변</a>
			</c:if>
			<button type="submit" id="deleteBtn" class="btn btn-default"
				value="${qnaContent.questionboardId}">삭제</button>
			<button type="submit" id="modifyBtn" class="btn btn-default"
				value="${qnaContent.questionboardId}">수정</button>
			<a class="btn btn-default" id="qnaListBtn"
				href="/petcommunity/qnaBoardList.do">목록</a>
		</div>
		
	</section>
	<script src="resources/js/qnaBoardContent.js"></script>
</body>
</html>
<%@include file="../views/footer.jsp"%>
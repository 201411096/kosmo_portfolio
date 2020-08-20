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
<link rel="stylesheet" href="./resources/bootstrap_template/template_01/css/style.css" />
<link rel="stylesheet" type="text/css" href="https://cdn.rawgit.com/moonspam/NanumSquare/master/nanumsquare.css">
<title>고객문의</title>

</head>

<body>
		<table>
			<tr>
				<td colspan="2"><h4>고객문의</h4>
					<hr id="lineStyle"></td>
				<td></td>
			</tr>
			<tr>
				<td id="title" style="width: 20%">제목
					<hr>
				</td>
				<td id="content" style="width: 80%">${qnaContent.questionboardTitle}<hr></td>
			</tr>
			<tr>
				<td id="title" style="width: 20%">작성자
					<hr>
				</td>
				<td id="content" style="width: 80%">${qnaContent.memberId}<hr></td>
			</tr>
			<tr>
				<td id="title" style="width: 20%">작성일
					<hr>
				</td>
				<td id="content" style="width: 80%">${qnaContent.questionboardUploadtime}<hr></td>
			</tr>
			<tr>
				<td id="title" style="width: 20%">조회수
					<hr>
				</td>
				<td id="content" style="width: 80%">${qnaContent.questionboardReadcount}<hr></td>
			</tr>
			<tr>
				<td colspan="2" id="content-content" style="width: 80%">${qnaContent.questionboardContent}<hr
						id="lineStyle">
						</td>
				<td></td>
			</tr>
			
			<tr>
				<td colspan="2" id="listButton"><button id="qnaListBtn"
						type="button" class="btn btn-default">목록보기</button></td>
				<td id="modi">
						<a class="btn btn-default" href="/petcommunity/qnaModify.do?questionboardId=${qnaContent.questionboardId}">수정</a>
					<button id="qnaDeleteBtn" type="button" class="btn btn-default">삭제</button></td>
			</tr>
		</table>
	<script src="resources/js/qnaBoardContent.js"></script>
</body>
</html>
<%@include file="../views/footer.jsp"%>
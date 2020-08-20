<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../views/header.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Insert title here</title>
<!-- Font Icon -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<link rel="stylesheet"
	href="./resources/bootstrap_template/template_01/css2/fonts1/material-icon/css/material-design-iconic-font.min.css">

<!-- Main css -->
<link rel="stylesheet" href="./resources/css/mypage.css">
<link rel="stylesheet" href="./resources/css/mypage-messageVer2.css">

</head>
<body>

<!-- <div class="main2"> -->
<section id="background_section">
<div class="signup-content2">
	<h4 id="qnatitle">Message</h4>
	<p id="qnasubtitle">실시간 쪽지.</p>
<div class="tedoory" id="message-table">
					<div id="div-searchSomeone">
						<input type="text" name="searchSomeone" id="searchSomeone"
							placeholder="사용자를 검색하세요"></input>
						<button id="btn-searchSomeone">검색</button>
					</div>
					<hr>
					<div id="div-memberList">
					<c:if test="${memberList eq null}">
						<h4>대화중인 상대</h4><hr>
					</c:if>
					<c:if test="${memberList ne null}">
						<h4>검색한 상대</h4><hr>
					</c:if>
						<c:forEach items="${memberList}" var="memberList">
							<div class="mypage-message" value="${memberList.memberId}">
							<table>
							<colgroup>
							<col style="width: 20%" />
							<col style="width: 30%" />
							<col style="width: 45%" />
							<col style="width: 5%" />
							</colgroup>
							<tr>
							<td><img class="mypage-messageThumbnail" src="resources/imgs/review_thumbnail/thumbnail_reivew.jpg" alt="썸네일"></td>
							<td class="mypage-messageId" value="${memberList.memberId}">${memberList.memberId}</td>
							<td></td>
							<input type="hidden" value="${memberList.memberId}"/>
							<td class="sendMsg"><i class="fa fa-paper-plane"></td>
							</tr>
							<tr>
								<td colspan="4" class="mypage-messageContent">${memberList.memberAddress}</td>	
							</tr>
							</table>
						</div>
						<hr>
						</c:forEach>

						<c:forEach items="${messagePartnerList}" var="messagePartnerList">
						<div class="mypage-message" value="${messagePartnerList}">
							<table>
							<colgroup>
							<col style="width: 20%" />
							<col style="width: 30%" />
							<col style="width: 45%" />
							<col style="width: 5%" />
							</colgroup>
							<tr>
							<td><img class="mypage-messageThumbnail" src="resources/imgs/review_thumbnail/thumbnail_reivew.jpg" alt="썸네일"></td>
							<td class="mypage-messageId" value="${messagePartnerList}">${messagePartnerList}</td>
							<td></td>
							<input type="hidden" value="${messagePartnerList}"/>
							<td class="sendMsg"><i class="fa fa-paper-plane"></td>
							</tr>
							<tr>
								<td colspan="4" class="mypage-messageContent">대화중</td>	
							</tr>
							</table>
						</div>
						<hr>
						
						</c:forEach>
					</div>
					<div id="firstDiv-chat">
						<div id="div-chat"></div>
						<div id="div-send">
							<input type="text" id="writeMessage" class="writeMessage" placeholder="메시지를 입력하세요"></input>
							<button id="btn-message">전송</button>
						</div>
					</div>
				</div>
				</div>
<!-- 				</div> -->
	</section>


<script
	src="https://cdnjs.cloudflare.com/ajax/libs/twbs-pagination/1.4.2/jquery.twbsPagination.min.js"></script>
<!-- 	<script src="./resources/js/util/module_socket.js"></script> -->
	<script src="./resources/js/mypage.js"></script>
	<script src="./resources/js/mypage-messageVer2.js"></script>
	<script src="./resources/bootstrap_template/template_01/js2/main.js"></script>
	
	
</body>
</html>
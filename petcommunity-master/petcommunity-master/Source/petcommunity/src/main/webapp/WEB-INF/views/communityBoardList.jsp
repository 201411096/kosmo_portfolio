<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="../views/header.jsp"%>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- 합쳐지고 최소화된 최신 CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<!-- 부가적인 테마 -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="./resources/css/communityBoardList.css" />
<link rel="stylesheet" type="text/css"
	href="https://cdn.rawgit.com/moonspam/NanumSquare/master/nanumsquare.css">
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/twbs-pagination/1.4.2/jquery.twbsPagination.min.js"></script>
<script src="resources/js/communityBoardList.js" type="text/javascript"></script>
<title>oki doghere커뮤니티</title>
</head>
<body>
	<section id="backGround">
		<div id="chatLink">
			<img alt=""
				src="resources/bootstrap_template/template_01/img/gallery/chat.png"
				width="80"><br>실시간 채팅
		</div>

		<h4 id="qnatitle">Community</h4>
		<p id="qnasubtitle">지역별 커뮤니티 게시판입니다.</p>
		<div class="container">

			<!-- 스크롤 배너 시작 -->

			<div class="bannerDiv">

				<i class="glyphicon glyphicon-chevron-up" id="bannerX"></i>
				<div id="banner">
					<div class="Ranktitle">가장 많이 본 커뮤니티</div>
					<div id="display">
						<div id="" class="banner_contents">
							<span class="badge badge-danger">1</span><a href="#" class=""
								id="a"></a><br> <span class="badge badge-default">2</span><a
								href="#" id="b"></a><br> <span class="badge badge-default">3</span><a
								href="#" id="c"></a><br> <span class="badge badge-default">4</span><a
								href="#" id="d"></a><br> <span class="badge badge-default">5</span><a
								href="#" id="e"></a><br> <span class="badge badge-default">6</span><a
								href="#" id="f"></a><br> <span class="badge badge-default">7</span><a
								href="#" id="g"></a><br> <span class="badge badge-default">8</span><a
								href="#" id="h"></a><br> <span class="badge badge-default">9</span><a
								href="#" id="i"></a><br> <span class="badge badge-default">10</span><a
								href="#" id="j"></a><br>
						</div>
					</div>
				</div>

			</div>


			<!-- 스크롤 배너 시작 -->
			<div class="bannerDiv">
				<div class="glyphicon glyphicon-chevron-up" id="bannerX1"></div>
				<div id="banner1">
					<div class="tab_menu_container">
						<button class="tab_menu_btn on" type="button">
							실종신고<span class="badge badgeNew">N</span>
						</button>
						<button class="tab_menu_btn" type="button" id="findRank">
							목격<span class="badge badgeNew">N</span>
						</button>

					</div>
					<div class="tab_box_container">
						<div class="tab_box on">

							<span class="badge badge-danger">1</span><a href="#" id="a1"></a><br>
							<span class="badge badge-default">2</span><a href="#" id="b1"></a><br>
							<span class="badge badge-default">3</span><a href="#" id="c1"></a><br>
							<span class="badge badge-default">4</span><a href="#" id="d1"></a><br>
							<span class="badge badge-default">5</span><a href="#" id="e1"></a><br>

						</div>

						<div class="tab_box">
							<span class="badge badge-danger">1</span><a href="#" id="a2"></a><br>
							<span class="badge badge-default">2</span><a href="#" id="b2"></a><br>
							<span class="badge badge-default">3</span><a href="#" id="c2"></a><br>
							<span class="badge badge-default">4</span><a href="#" id="d2"></a><br>
							<span class="badge badge-default">5</span><a href="#" id="e2"></a><br>

						</div>

					</div>
				</div>
			</div>

			<!-- 스크롤 배너 끝 -->

			<section id="container">

				<div class="col-xs-0 col-sm-3">
					<select class="form-control1" id='showBy'>
						<option>전체보기</option>
						<option>지역별</option>
						<option>추천순</option>
						<option>조회순</option>
					</select>
				</div>
				<div id=hiddenBox>
					<div class="col-xs-0 col-sm-2">
						<select class="form-control1" id='cityName' name='cityName'>
							<option>-----</option>
						</select>
					</div>
					<div class="col-xs-0 col-sm-2">
						<select class="form-control1" id='province' name='province'>
							<option>-----</option>
						</select>
					</div>
				</div>

				<table class="table table-hover">
					<thead>
						<tr>
							<th>번호</th>
							<th>제목</th>
							<th>작성자</th>
							<th>등록일</th>
							<th>추천</th>
							<th>조회수</th>
						</tr>
					</thead>
					<tbody id="communityList">
					<tbody id="communityHide">
						<c:forEach items="${communityBoardList}" var="communityBoardList">


							<tr class='tbodyTr'>
								<td>${communityBoardList.communityboardId}</td>
								<td class='secondChild'><a
									href='getBoardContent.do?communityboardId=${communityBoardList.communityboardId}'
									class="text-dark"> [<span id='locationTag'>${communityBoardList.communityboardLocation}</span>]
										${communityBoardList.communityboardTitle} <c:if
											test="${communityBoardList.commentCount > 0}">
											<span class="text-warning">[${communityBoardList.commentCount}]</span>
										</c:if>
								</a> <c:forEach items="${checkImg}" var="checkImg">
										<c:if
											test="${checkImg eq communityBoardList.communityboardId}">
											<img src='resources/imgs/communityboard/like/image.PNG'>
										</c:if>
									</c:forEach></td>
								<td>${communityBoardList.memberId}</td>
								<td>${communityBoardList.communityboardUploadtime}</td>
								<td>${communityBoardList.communityboardRecommend}</td>
								<td>${communityBoardList.communityboardReadcount}</td>

							</tr>

						</c:forEach>
					</tbody>
					</tbody>
				</table>


				<div class="search row">
					<div class="col-xs-2 col-sm-2">
						<select name="type" class="form-control2" id='type'>

							<option>제목</option>
							<option>내용</option>
							<option>작성자</option>

						</select>
					</div>

					<div class="col-xs-10 col-sm-10">
						<div class="input-group">
							<input type="text" name="keyWord" class='form-control2'
								id="keywordInput" /> <span class="input-group-btn">
								<button id="searchBtn" type="button" class="btnC">검색</button>
							</span> <span class="input-group-btn">
								<button id="writeBtn" type="button" class="btnC">글쓰기</button>
							</span>

						</div>
					</div>

				</div>


				<div align="center" id='getBoardPaging'>
					<ul class="pagination">
						<li><a href="/petcommunity/communityBoardList.do">&#60;&#60;</a></li>
						<li><a href="#" id='prevPage'>&#60;</a></li>
						<c:forEach begin="${showPageStart}" end="${showPageLast}"
							var="idx">
							<li><a
								href="/petcommunity/communityBoardList.do?pageNo=${idx}"
								id='curPage${idx}'>${idx}</a></li>
						</c:forEach>

						<li><a href="#" id='nextPage'>&#62;</a></li>
						<li><a
							href="/petcommunity/communityBoardList.do?pageNo=${amountOfPage}">&#62;&#62;</a></li>
					</ul>
				</div>




			</section>
			<div class="col-md-offset-3" id="pagination_container">
				<ul id="pagination-demo" class="pagination-lg"></ul>
			</div>

		</div>
	</section>
	<%@include file="../views/footer.jsp"%>
</body>
</html>
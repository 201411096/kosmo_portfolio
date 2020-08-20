<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../views/header.jsp"%>
<html>
<head>
<!-- 합쳐지고 최소화된 최신 CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<!-- 부가적인 테마 -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link rel="stylesheet" href="resources/css/findboardlist.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/twbs-pagination/1.4.2/jquery.twbsPagination.min.js"></script>
<!-- autocomplete 3 cdn link -->
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">  
<!-- <script src="https://code.jquery.com/jquery-1.12.4.js"></script> -->
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<title>게시판</title>
</head>
<body>
<%-- 	<jsp:include page="/header.do"></jsp:include> --%>
	<div class="container">
		<header>
			<h1>게시판</h1>
		</header>
		<hr />
		<section id="container">
			<form role="form" method="get">
				<table class="table table-hover" id="findboardTable">
					<thead>
						<tr>
							<th>번호</th>
							<th>상태</th>
							<th>제목</th>
							<th>작성자</th>
							<th>조회수</th>
							<th>등록일</th>
						</tr>
					</thead>
					<tbody id="findboardTbody">
						
					</tbody>
				</table>
				<div class="search row">
					<div class="col-xs-2 col-sm-2">
						<select id="searchType" name="searchType" class="form-control">
							<option value="title">제목</option>
							<option value="content">내용</option>
							<option value="writer">작성자</option>
							<option value="titleandcontent">제목+내용</option>
							<option value="location">지역</option>
						</select>
					</div>
					<div class="col-xs-10 col-sm-10">
						<div class="input-group">
							<span id="search-container">
							<input type="text" name="keyword" id="keywordInput" class="form-control"/> 
							</span>	
							<span class="input-group-btn">
								<button id="searchBtn" type="button" class="btn btn-default">검색</button>
							</span> 
							<span class="input-group-btn">
								<button id="writeBtn" type="button" class="btn btn-default">글쓰기</button>
							</span>
						</div>
					</div>
				</div>
				<div class="col-md-offset-3" id="pagination_container">
					<ul id="pagination-demo" class="pagination-lg"></ul>
				</div>
			</form>
		</section>
	</div>
	<script src="resources/js/findboardlist.js"></script>
</body>

</html>
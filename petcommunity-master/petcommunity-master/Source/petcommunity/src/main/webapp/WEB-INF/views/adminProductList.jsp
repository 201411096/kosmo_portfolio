<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="../views/header.jsp"%>
<html>
<head>
<!-- 합쳐지고 최소화된 최신 CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<!-- 부가적인 테마 -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="./resources/css/adminProduct.css" />
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="resources/js/adminProduct.js" type="text/javascript"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twbs-pagination/1.4.2/jquery.twbsPagination.min.js"></script>

<title>관리자 페이지</title>
</head>
<body>
	<div class="container">
		<section id="container">
			<!-- <ul class="tabs">
				<li class="tab-link current" data-tab="tab-1">판매 내역</li>
				<li class="tab-link" data-tab="tab-2">판매 통계</li>
				<li class="tab-link3" data-tab="tab-3">관리자 페이지</li>
				<li id="adminpop">계정관리</li>
			</ul> -->
			<hr />

			<div id="tab-3" class="tab-content">
					<div class="col-xs-10 col-sm-10">
						<div class="input-group">	
							<span id="search-container">
							<input type="text" name="searchWord" class='form-control' id="searchWord" placeholder="검색어를 입력하세요."/>
							<button id="searchBtn" type="button" class="btn btn-default">검색</button>
							<a href="/petcommunity/loadInsertProduct.do" id="writeBtn" class="btn btn-default">등록</a>
							</span> 
					</div></div>
					<br><br/><br/><br/>

					<form role="form" method="get">
							<table id="productDataTable" class="table table-hover">
								<colgroup>
									<col style="width: 10%" />
									<col style="width: 30%" />
									<col style="width: 10%" />
									<col style="width: 10%" />
									<col style="width: 10%" />
									<col style="width: 20%" />
									<col style="width: 5%" />
									<col style="width: 5%" />
									
								</colgroup>
							

								<thead>
									<tr>
										<th scope="col">상품 번호</th>
										<th scope="col">상품명</th>
										<th scope="col">가격</th>
										<th scope="col" style="padding-left: 0px; padding-right: 0px;">수량</th>
										<th scope="col" style="width: 56px; padding-left: 0px; padding-right: 0px;">카테고리</th>
										<th scope="col" style="width: 56px; padding-left: 0px; padding-right: 0px;">상품내용</th>
										<th scope="col">수정</th>
										<th scope="col">삭제</th>
									</tr>
								</thead>
								<tbody id="productTable">
							
								</tbody>
							</table>
							
					
							
						<div class="col-md-offset-3" id="pagination_container">
							<ul id="pagination-demo" class="pagination-lg"></ul>
						</div>
						</form>
						</div>		
						</section>
					</div>
<script src="resources/js/adminProduct.js" type="text/javascript"></script>
</body>
</html>
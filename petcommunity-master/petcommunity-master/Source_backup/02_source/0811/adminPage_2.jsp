<!-- 3개를 바꿈.. -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="../views/header.jsp"%>
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
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/twbs-pagination/1.4.2/jquery.twbsPagination.min.js"></script>
<link rel="stylesheet" href="./resources/css/adminPage.css" />
<link rel="stylesheet" href="./resources/css/adminGraph.css" />
<link rel="stylesheet"
	href="./resources/bootstrap_template/template_01/css/style.css" />

<title>관리자 페이지</title>
</head>
<body>
	<!-- header section -->
<!-- 	<header class="header-section"> -->
<!-- 		<div class="header-warp"> -->
<!-- 			<a href="header.do" class="site-logo"> <img -->
<!-- 				src="./resources/bootstrap_template/template_01/img/logo2.png" -->
<!-- 				alt=""> -->
<!-- 			</a> -->
<!-- 			<ul class="main-menu"> -->
<%-- 				<c:if test="${! empty sessionScope.memberVO}"> --%>
<%--             ${sessionScope.memberVO.memberName}님, 안녕하세요 --%>
<!--             <a href="logout.do">[로그아웃하기]</a> -->
					
<%-- 				</c:if> --%>
<!-- 				<li><a href="lostboardlist.do">분실 동물 찾기</a></li> -->
<!-- 				<li><a href="findboardlist.do">분실 동물 신고</a></li> -->

<!-- 				<li><a href="/petcommunity/communityBoardList.do">커뮤니티</a></li> -->
<!-- 				<li><a href="shop.do">유기견 후원 스토어</a></li> -->
<!-- 				<li><a href="/petcommunity/productList.do">오키도키 굿즈</a></li> -->
<!-- 				<li><a href="/petcommunity/findHospitalList.do">동물 병원 정보</a></li> -->
<!-- 				<li><a href="/petcommunity/cs.do">고객 문의</a></li> -->

<%-- 				<c:if test="${empty sessionScope.memberVO}"> --%>
<!-- 					<li><a href="login.do">로그인/회원가입</a></li> -->
<%-- 				</c:if> --%>
<%-- 				<c:if test="${sessionScope.memberVO.memberId eq 'admin1234'}"> --%>
<!-- 					<li><a href="login.do">관리자페이지</a></li> -->
<%-- 				</c:if> --%>
<!-- 			</ul> -->
<!-- 			<hr> -->
<!-- 		</div> -->
<!-- 	</header> -->
	<!-- header section -->
	<div class="container">
		<section id="container">
			<ul class="tabs">
				<li class="tab-link current" data-tab="tab-1">판매 내역</li>
				<li class="tab-link" data-tab="tab-2">통계 그래프</li>
				<li class="tab-link" data-tab="tab-3">관리자 페이지</li>
				<li id="adminpop">계정관리</li>
			</ul>
			<hr />
			<div id="tab-1" class="tab-content current">
				<div class="search">
					<form action="/petcommunity/datesearch.do" id="datesearch" name="datesearch">

						<div class="input-group">
							<input type="date" name="startDate" class='form-control'
								id="startDate" /> 
								<input type="date" name="endDate"
								class='form-control' id="endDate" /> 
								<input id="searchBtn"
								type="submit" class="btn btn-default" value="검색">
								<a href="/petcommunity/adminPage.do" id="listBtn" class="btn btn-default">목록</a>
						</div>
					</form>
					<br>
					<table class="mypage-table">
						<colgroup>
							<col style="width: 20%" />
							<col style="width: 50%" />
							<col style="width: 10%" />
							<col style="width: 20%" />
						</colgroup>
						<thead>
							<tr id="title">
								<th>주문날짜</th>
								<th>상품명</th>
								<th>수량</th>
								<th>가격</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${salesList}" var="item">
								<tr>
									<td>${item.buylistDate}</td>
									<td><input type="hidden" name="productId" id="productId" />
										<a href="productView.do?productId=${item.productId}">
											${item.productName}</a></td>
									<td>${item.buyCnt}</td>
									<td>${item.buyTotalprice}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			
			<div id="tab-2" class="tab-content">
				<div id="graph_04_outer_container" class="graph_outer_container_full">
					<div class="alert alert-info">매출 통계</div>
					<div id="graph_04_option_container">
						<div class="form-group">
							<label class="col-md-2">모형</label>
							<div class="col-md-10">
							<select id="graph_04_chartShape_option" class="form-control">
								<option value="bar">막대</option>
								<option value="pie">원형</option>
								<option selected value="line">선형</option>
								<option value="doughnut">도넛</option>
								<option value="polarArea">폴라</option>
							</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2">시간단위</label>
							<div class="col-md-10">
							<select id="graph_04_chartTime_option" class="form-control">
								<option value="0">초</option>
								<option value="1">분</option>
								<option value="2">시</option>
								<option selected value="3">일</option>
								<option value="4">월</option>
								<option value="5">년</option>
							</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2">시작일</label>
							<div class="col-md-10">
							<input type="datetime-local" id="graph_04_startDate" class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2">종료일</label>
							<div class="col-md-10">
							<input type="datetime-local" id="graph_04_endDate" class="form-control">
							</div>
						</div>
					</div>
					<div id="graph_04_container" class="graph-container"></div>
				</div>
				<div id="graph_05_outer_container" class="graph_outer_container">
					<div class="alert alert-info">품목별 매출 통계</div>					
					<div id="graph_05_option_container">
						<div class="form-group">
							<label class="col-md-2">모형</label>
							<div class="col-md-10">
							<select id="graph_05_chartShape_option" class="form-control">
								<option value="bar">막대</option>
								<option selected value="pie">원형</option>
								<option value="line">선형</option>
								<option value="doughnut">도넛</option>
								<option value="polarArea">폴라</option>
							</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2">시작일</label>
							<div class="col-md-10">
							<input type="datetime-local" id="graph_05_startDate" class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2">시작일</label>
							<div class="col-md-10">
							<input type="datetime-local" id="graph_05_endDate" class="form-control">
							</div>
						</div>
					</div>
					<div id="graph_05_container" class="graph-container"></div>
				</div>
				<div id="graph_03_outer_container" class="graph_outer_container">
					<div class="alert alert-info">시간별 매출 통계</div>
					<div id="graph_03_option_container">
						<div class="form-group">
							<label class="col-md-2">시작일</label>
							<div class="col-md-10">
							<select id="graph_03_chartShape_option" class="form-control">
								<option selected value="bar">막대</option>
								<option value="pie">원형</option>
								<option value="line">선형</option>
								<option value="doughnut">도넛</option>
								<option value="polarArea">폴라</option>
							</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2">시간단위</label>
							<div class="col-md-10">						
							<select id="graph_03_chartTime_option" class="form-control">
								<option selected value="0">시간</option>
								<option value="1">요일</option>
								<option value="2">월</option>
							</select>
							</div>
						</div>
					</div>
					<div id="graph_03_container" class="graph-container"></div>
				</div>
				<div id="graph_02_outer_container" class="graph_outer_container">
					<div class="alert alert-info">시간별 목격횟수 통계</div>
					<div id="graph_02_option_container">
						<span>
							<select id="graph_02_chartShape_option" class="form-control">
								<option selected value="bar">막대</option>
								<option value="pie">원형</option>
								<option value="line">선형</option>
								<option value="doughnut">도넛</option>
								<option value="polarArea">폴라</option>
							</select>
						</span>
						<span>
							<select id="graph_02_chartTime_option" class="form-control">
								<option selected value="0">시간</option>
								<option value="1">요일</option>
								<option value="2">월</option>
								<option value="3">년도</option>
							</select>
						</span>
					</div>
					<div id="graph_02_container" class="graph-container"></div>
				</div>
				<div id="graph_01_outer_container" class="graph_outer_container">
					<div class="alert alert-info">시간별 실종횟수 통계</div>
					<div id="graph_01_option_container">
						<select id="graph_01_chartShape_option" class="form-control">
							<option selected value="bar">막대</option>
							<option value="pie">원형</option>
							<option value="line">선형</option>
							<option value="doughnut">도넛</option>
							<option value="polarArea">폴라</option>
						</select>
						<select id="graph_01_chartTime_option" class="form-control">
							<option selected value="0">시간</option>
							<option value="1">요일</option>
							<option value="2">월</option>
							<option value="3">년도</option>
						</select>
					</div>
					<div id="graph_01_container" class="graph-container"></div>
				</div>
			</div><!-- tab-2 end .. --> 		
		
			<div id="tab-3" class="tab-content">
				<div class="search row current">
					<div class="col-xs-10 col-sm-10">
						<div class="input-group">	
							<span id="search-container">
							<input type="text" name="searchWord" class='form-control' id="searchWord" placeholder="검색어를 입력하세요."/>
							</span> 
							<span class="input-group-btn"> 
								<button id="searchBtn" type="button" class="btn btn-default" style=" width: 76px; margin-right: 10%;">검색</button>
								<a href="/petcommunity/loadInsertProduct.do" id="writeBtn" class="btn btn-default" style="margin-left: 100%;">상품 등록</a>
							</span>	
					</div></div></div>
					<br>
<!-- 
					<div id="productInfoButtonContainer">
						<div class="input-group">				
							<form action="/petcommunity/loadInsertProduct.do">
								<span class="input-group-btn">
									<button id="writeBtn" type="submit" class="btn btn-default">상품 등록</button>
								</span>
							</form>
					</div></div> -->
					
					<br>	
					
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
										<th scope="col">수량</th>
										<th scope="col">카테고리</th>
										<th scope="col">상품 내용</th>
										<th scope="col">수정</th>
										<th scope="col">삭제</th>
									</tr>
								</thead>
								<tbody id="productTable">
							
								</tbody>
							</table>
						<div class="col-md-offset-3" id="pagination_container" style="margin-left:0px;">
							<ul id="pagination-demo" class="pagination-lg"></ul>
						</div>
						</form>
						</div>				
			</section>
		</div>
	<script src="resources/js/adminProduct.js" type="text/javascript"></script>
	<script src="resources/js/adminPage.js" type="text/javascript"></script>
	<script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
	<script src="resources/js/util/module_graph.js"></script>
	<script src="resources/js/adminGraph.js"></script>
</body>
</html>
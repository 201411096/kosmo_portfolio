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
<link rel="stylesheet" href="resources/css/lostboardlist.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/twbs-pagination/1.4.2/jquery.twbsPagination.min.js"></script>
<!-- autocomplete 3 cdn link -->
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">  
<!-- <script src="https://code.jquery.com/jquery-1.12.4.js"></script> -->
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<title>게시판</title>
</head>
<body>
<section id="section">
	<div class="container">
		<h3 id="qnatitle">Lost board</h3>
		<p id="qnasubtitle">실종 동물 신고 게시판</p>
		<hr />
		
		<section id="container">
			<div class="form-group">
				<label class="col-md-2"><span class='selectText'>마커 표시 시간</span></label>
				<div class="col-md-4">
				<select id="timeForSearch" class="form-control">
				<option value="0">전체</option>
				<option value="1">30분</option>
				<option value="2">1시간</option>
				<option value="3">3시간</option>
				<option value="4">12시간</option>
				<option value="5">하루</option>
				<option value="6">일주일</option>
				<option value="7">한달</option>
				</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-2"><span class='selectText'>마커 표시 지역 검색</span></label>
				<div class="col-md-4">
				<span id="searchForMap-container">
					<input id="locationForSearch" type="text" class="form-control">
				</span>
				</div>
			</div>
<!-- 			<div class="form-group"> -->
<!-- 				<label><span class='selectText'>마커 표시 시간</span></label> -->
<!-- 				<select id="timeForSearch" class="form-control"> -->
<!-- 				<option value="0">전체</option> -->
<!-- 				<option value="1">30분</option> -->
<!-- 				<option value="2">1시간</option> -->
<!-- 				<option value="3">3시간</option> -->
<!-- 				<option value="4">12시간</option> -->
<!-- 				<option value="5">하루</option> -->
<!-- 				<option value="6">일주일</option> -->
<!-- 				<option value="7">한달</option> -->
<!-- 				</select> -->
<!-- 			</div> -->
<!-- 			<div class="form-group"> -->
<!-- 				<label class='selectText'>마커 표시 지역 검색</label> -->
<!-- 				<span id="searchForMap-container"> -->
<!-- 					<input id="locationForSearch" type="text" class="form-control"> -->
<!-- 				</span> -->
<!-- 			</div> -->
			<div id="markerGroup">
			   <span class="markerSpan"><img alt="" src="./resources/imgs/marker/red.png">잃어버린 장소</span>
			   <span class="markerSpan"><img alt="" src="./resources/imgs/marker/blue.png">목격한 장소</span>
			   <span class="markerSpan2"><img alt="" src="./resources/imgs/marker/green.png">탐색 추천</span>
			</div>

			<div class="card">
				<div id="map"></div>
			</div>
			<form role="form" method="get">
				<table class="table" id="lostboardTable">
<%-- 					 <caption>잃어버린 강아지를 찾아주세요!</caption> --%>
					<colgroup>
						<col id='col1'/>
						<col id='col2'/>
						<col id='col3'/>
						<col id='col4'/>
					</colgroup>
					<thead>
						<tr>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
						</tr>
					</thead>
					<tbody id="lostboardTbody">
						
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
							<c:if test="${not empty sessionScope.memberVO}"> 
							<span class="input-group-btn">
								<button id="writeBtn" type="button" class="btn btn-default">글쓰기</button>
							</span>
							</c:if>
						</div>
					</div>
				</div>
				<div class="col-md-offset-3" id="pagination_container">
					<ul id="pagination-demo" class="pagination-lg"></ul>
				</div>
			</form>
		</section>
	</div>
	</section>
		<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=41ccd37d4644ab2ed5ed67441dda1abb&libraries=services"></script>
	<script src="resources/js/lostboardlist.js"></script>	
	
</body>

</html>
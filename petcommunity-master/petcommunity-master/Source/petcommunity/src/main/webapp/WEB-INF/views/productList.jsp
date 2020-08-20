<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="../views/header.jsp"%>
<!DOCTYPE html>
<html lang="zxx">
<head>
<!-- Stylesheets -->
<!-- <link rel="stylesheet" href="resources/css/bootstrap.min.css"/>    -->
<!-- Main Stylesheets -->
<!-- <link rel="stylesheet" href="./resources/bootstrap_template/template_01/css/style.css"/> -->
<!-- nav bar -->
<link rel="stylesheet"
	href="./resources/bootstrap_template/bootstrap_ain/css/style.css" />
<!-- search -->
<link rel="stylesheet" href="resources/css/productList.css">
<!-- Paging -->



<script
	src="https://cdnjs.cloudflare.com/ajax/libs/twbs-pagination/1.4.2/jquery.twbsPagination.min.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<!-- autocomplete 3 cdn link -->
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

</head>
<body>
	<!-- Page Preloder -->

	<!-- Products page -->
	<!-- Product filter section -->
	<section class="product-filter-section">
		<div class="container">
			<!-- 스크롤 배너 시작 -->

			<div class="bannerDiv">
				<div class="glyphicon glyphicon-chevron-up" id="bannerX"></div>
				<div id="banner">
					<div class="Ranktitle">BEST 사료</div>
					<div id="display">


						<div id="" class="banner_contents">
							<span class="badge badge-danger">1</span><span><a href="#"
								id="a"></a></span><br> <span class="badge badge-default">2</span><a
								href="#" id="b"></a><br> <span class="badge badge-default">3</span><a
								href="#" id="c"></a><br>

						</div>
					</div>
				</div>

			</div>
			<!-- 스크롤 배너 끝 -->

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

			<div class="section-title">
				<h4 id="producttitle">Okidoghere Goods</h4>
				<p id="productsubtitle">반려견 사료 리스트입니다.</p>
			</div>
			<br />
			<form></form>
			<br />
			<div id="ul-div">
				<ul class="product-filter-menu">
					<li><a href="#" name="tagCategory" class="tagCategory"
						value="all">전체</a><span id="bar">|</span></li>
					<li><a href="#" name="tagCategory" class="tagCategory"
						value="퍼피용">퍼피용</a><span id="bar">|</span></li>
					<li><a href="#" name="tagCategory" class="tagCategory"
						value="성견용">성견용</a><span id="bar">|</span></li>
					<li><a href="#" name="tagCategory" class="tagCategory"
						value="전연령">전연령</a><span id="bar">|</span></li>
					<li><a href="#" name="tagCategory" class="tagCategory"
						value="노견용">노견용</a><span id="bar">|</span></li>
					<li><a href="#" name="tagCategory" class="tagCategory"
						value="소형견용">소형견용</a><span id="bar">|</span></li>
					<li><a href="#" name="tagCategory" class="tagCategory"
						value="대형견용">대형견용</a><span id="bar">|</span></li>
					<li><a href="#" name="tagCategory" class="tagCategory"
						value="비만견용">비만견용</a></li>
				</ul>
			</div>
			<div id="ul-div">
				<ul class="product-filter-menu">
					<li><a href="#" name="tagCategory" class="tagCategory"
						value="임신/수유견">임신/수유견</a><span id="bar">|</span></li>
					<li><a href="#" name="tagCategory" class="tagCategory"
						value="그레인프리 사료">그레인프리 사료</a><span id="bar">|</span></li>
					<li><a href="#" name="tagCategory" class="tagCategory"
						value="뼈관절 사료">뼈관절 사료 잡화</a><span id="bar">|</span></li>
					<li><a href="#" name="tagCategory" class="tagCategory"
						value="피부모질 사료">피부모질 사료</a><span id="bar">|</span></li>
					<li><a href="#" name="tagCategory" class="tagCategory"
						value="기타 기능성 사료">기타 기능성 사료</a></li>
				</ul>
			</div>
			<div id="ul-div">
				<ul class="product-filter-menu">
					<li><a href="#" name="tagCategory" class="tagCategory"
						value="위장개선 사료">위장개선 사료</a><span id="bar">|</span></li>
					<li><a href="#" name="tagCategory" class="tagCategory"
						value="대포장사료">대포장사료</a><span id="bar">|</span></li>
					<li><a href="#" name="tagCategory" class="tagCategory"
						value="저알러지 사료">저알러지 사료</a><span id="bar">|</span></li>
					<li><a href="#" name="tagCategory" class="tagCategory"
						value="소프트 사료">소프트 사료</a><span id="bar">|</span></li>
					<li><a href="#" name="tagCategory" class="tagCategory"
						value="오븐에구운사료">오븐에구운사료</a></li>
				</ul>
			</div>
			<!-- Product filter section -->
			<div id="searchDiv">
				<div class="input-group" id="searchBar">
					<span id="#searchInput-container"> <input
						class="form-control" id="searchSomething" type="text">
					</span> <span>
						<button id="searchBtn" type="button" class="btn btn-default">검색</button>
					</span>
				</div>
				<p style="clear: both;"></p>
			</div>
			<div id='productTable' class="row artists-row">
				<!-- forEach 사용 -->
				<!-- 
         <c:forEach var="shop" items="${shopList}">
            <div class="col-lg-4 col-sm-6 artists-col">
               <a href="${shop.shopLink }" target="_blank">
               <div class="artists-item">
                  <img src="./resources/imgs/product_image/pawInHand/${shop.shopProductname}.jpg" alt="">
                  <h4>${shop.shopProductname}</h4>
                  <span>${shop.shopProductprice}</span>
                  <p>${shop.shopName}</p>
               </div>
               </a>
            </div> 
            
            
         </c:forEach>
          -->
				<!--forEach 사용 끝 -->

			</div>
			<div id="pagination-div">
				<div id="pagination_container">
					<ul id="pagination-demo" class="pagination-lg"></ul>
				</div>
			</div>
		</div>
	</section>
	<!-- Products page end -->


	<!-- Footer section  -->

	<!-- Footer section end -->
	<%@include file="../views/footer.jsp"%>
	<script src="./resources/js/productList.js"></script>


</body>
</html>
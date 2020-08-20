<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../views/header.jsp"%>
<!DOCTYPE html>
<html lang="zxx">
<head>
<title>The Look - Photo Gallery Template</title>
<meta charset="UTF-8">
<meta name="description" content="Instyle Fashion HTML Template">
<meta name="keywords" content="instyle, fashion, html">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Favicon -->
<link href="img/favicon.ico" rel="shortcut icon" />

<!-- Google font -->
<link
	href="https://fonts.googleapis.com/css?family=Lato:300,300i,400,400i,700,700i&display=swap"
	rel="stylesheet">

<link
	href="https://fonts.googleapis.com/css2?family=Amatic+SC:wght@700&family=Noto+Serif+KR:wght@600&display=swap"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="https://cdn.rawgit.com/moonspam/NanumSquare/master/nanumsquare.css">

<!-- Stylesheets -->
<!-- <link rel="stylesheet" -->
<!-- 	href="./resources/bootstrap_template/bootstrap_seyeong/css/font-awesome.min.css" /> -->
<link rel="stylesheet"
	href="./resources/bootstrap_template/bootstrap_seyeong/css/owl.carousel.min.css" />
<link rel="stylesheet"
	href="./resources/bootstrap_template/bootstrap_seyeong/css/animate.css" />
<link rel="stylesheet"
	href="./resources/bootstrap_template/bootstrap_footer/css/style.css" />


<!-- Main Stylesheets -->
<link rel="stylesheet"
	href="./resources/bootstrap_template/bootstrap_seyeong/css/style.css" />


<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
   <![endif] -->
</head>

<body>
	<div id="bodyBackgroundColor">
		<!-- Hero section -->
		<section class="hero-section">
			<div class="hero-slider owl-carousel" align="left">
				<img class="mungimg"
					src="./resources/bootstrap_template/bootstrap_seyeong/img/slider/wordcloudcover.png"
					alt="#"> <img class="mungimg"
					src="./resources/bootstrap_template/bootstrap_seyeong/img/slider/maincover2.png"
					alt="#">
			</div>
		</section>
		<!-- 		</div> -->
		<!-- 							 <figure><img id="dialogFlow" src="./resources/bootstrap_template/template_01/img/gallery/noun_364.png" alt="" style="width: 10%"></figure> -->
		<!-- 			<div class="hero-slider owl-carousel"> -->
		<!-- 				<div class="hs-item"> -->
		<!-- 					<div class="hs-text"> -->
		<!-- 						<h2> -->
		<!-- 							Give Love,<br>Give Hope, -->
		<!-- 						</h2> -->
		<!-- 					</div> -->
		<!-- 					<div class="hs-bg set-bg sm-overlay" id="imgMain" -->
		<!-- 						data-setbg="resources/bootstrap_template/bootstrap_seyeong/img/slider/cover3-1.jpg"></div> -->
		<!-- 				</div> -->
		<!-- 				<div class="hs-item"> -->
		<!-- 					<div class="hs-text"> -->
		<!-- 						<h2> -->
		<!-- 							아이들을 생각하는 마음으로<br>꼼꼼하게 고른 원료 -->
		<!-- 						</h2> -->
		<!-- 						<br> -->
		<!-- 					</div> -->
		<!-- 					<div class="hs-bg set-bg sm-overlay" -->
		<!-- 						data-setbg="resources/bootstrap_template/bootstrap_seyeong/img/slider/11111.png"></div> -->
		<!-- 				</div> -->
		<!-- 			</div> -->


		<!-- 	Hero section end -->

		<!-- Gallery section -->
		<section class="gallery-section">
			<div class="sp-container-two">
				<div class="gallery-text">
					<div class="vl1"></div>
					<h1>오키독히얼</h1>
					<p id="sub1">about okidoghere</p>
					<p id="sub2">반려동물을 위한 모든 것</p>
				</div>
				<div class="memberCountTitle"></div>
				<div class="count-row">
					<div class="gallery-item">
						<div class="mungmung">
							<img alt=""
								src="./resources/bootstrap_template/bootstrap_seyeong/img/mung1.png">
						</div>
						<div class="memberCountCon" id="memberCountCon1"></div>
						<div class="memberCountSubTitle">&nbsp실종신고</div>
					</div>
				</div>
				<div class="count-row">
					<div class="gallery-item">
						<div class="mungmung">
							<img alt=""
								src="./resources/bootstrap_template/bootstrap_seyeong/img/mung1.png">
						</div>
						<div class="memberCountCon" id="memberCountCon2"></div>
						<div class="memberCountSubTitle">&nbsp &nbsp 목격</div>
					</div>
				</div>
				<div class="count-row">
					<div class="gallery-item">
						<div class="mungmung">
							<img alt=""
								src="./resources/bootstrap_template/bootstrap_seyeong/img/mung1.png">
						</div>
						<div class="memberCountCon" id="memberCountCon3"></div>
						<div class="memberCountSubTitle" id="findCounttitle"> &nbsp&nbsp가족의 품으로 <br>&nbsp돌아간 반려동물</div>
					</div>
				</div>
			</div>
		</section>
		<!-- Gallery section end -->
		<section class="lastsection">
			<div class="gallery-row" align="left">
				<img class="mungimg"
					src="./resources/bootstrap_template/bootstrap_seyeong/img/main1.png"
					alt="#"><a href="/petcommunity/mainGraph.do" id="maingraph"
					class="maingraph"><strong>유기견 관련 통계 보러가기 -></strong></a>
					<a href="/petcommunity/shop.do" id="readMore"
					class="readMore"><strong>유기견 후원스토어 보러가기 -></strong></a>
			</div>
		</section>

		<!-- Blog section -->
		<section class="blog-section">
		<div class="blog-title-row1">	
				<div class="blog-item1">
				
					<div class="blog-thumb1">
						<img
							src="./resources/bootstrap_template/bootstrap_seyeong/img/gallery/01.png"
							alt="">
					</div>
					<div class="blog-content1">
						<span class="blog-cata-title">
							<h4>[ 잃어버렸어요 ]</h4> <span>Lost Board List</span><br>
						</span>
						<c:forEach items="${lostBoardvo}" var="item">
							<div id="board-title">
								<a id="link"
									href="/petcommunity/getLostBoard.do?lostboardId=${item.lostboardId}">${item.lostboardTitle}</a>
								<br>
							</div>
						</c:forEach>
						<div id="moreView">
							<a href="/petcommunity/lostboardlist.do" class="site-btn" id="site-btn-read">READ
								MORE <img
								src="./resources/bootstrap_template/bootstrap_seyeong/img/icons/arrow-right-black.png"
								alt="">
							</a>
						</div>
					</div>
				</div>
				<div class="blog-item2">
					<div class="blog-thumb2">
						<img
							src="./resources/bootstrap_template/bootstrap_seyeong/img/gallery/02.png"
							alt="">
					</div>
					<div class="blog-content2">
						<span class="blog-cata-title">
							<h4>[ 목격했어요 ]</h4> <span>Find Board List</span><br>
						</span>
						<c:forEach items="${FindBoardvo}" var="item">
							<div id="board-title">
								<a id="link"
									href="/petcommunity/getFindBoard.do?findboardId=${item.findboardId}">${item.findboardTitle}</a>
								<br>
							</div>
						</c:forEach>
						<div id="moreView">
							<a href="/petcommunity/findboardlist.do" class="site-btn">READ
								MORE <img
								src="./resources/bootstrap_template/bootstrap_seyeong/img/icons/arrow-right-black.png"
								alt="">
							</a>
						</div>
					</div>
			</div>
			</div>	
			<div class="blog-title-row2">
				<div class="blog-item3">
					<div class="blog-thumb3">
						<img
							src="./resources/bootstrap_template/bootstrap_seyeong/img/gallery/03.png"
							alt="">
					</div>
					<div class="blog-content3">
						<span class="blog-cata-title">
							<h4>[ 고객문의 ]</h4> <span>Qna Board List</span><br>
						</span>
						<c:forEach items="${Qnavo}" var="item">
							<div id="board-title">
								<a id="link"
									href="/petcommunity/qnaContent.do?questionboardId=${item.questionboardId}">${item.questionboardTitle}</a>
								<br>
							</div>
						</c:forEach>
						<div id="moreView">
							<a href="/petcommunity/cs.do" class="site-btn">READ MORE <img
								src="./resources/bootstrap_template/bootstrap_seyeong/img/icons/arrow-right-black.png"
								alt=""></a>
						</div>
					</div>
				</div>
				<div class="blog-item4">
					<div class="blog-thumb4">
						<img
							src="./resources/bootstrap_template/bootstrap_seyeong/img/gallery/04.png"
							alt="">
					</div>
					<div class="blog-content4">
						<span class="blog-cata-title">
							<h4>[ 커뮤니티 ]</h4> <span>COMMUNITY BOARD</span><br>
						</span>
						<c:forEach items="${CommunityBoardvo}" var="item">
							<div id="board-title">
								<a id="link"
									href="/petcommunity/getBoardContent.do?communityboardId=${item.communityboardId}">${item.communityboardTitle}</a>
								<br>
							</div>
						</c:forEach>
						<div id="moreView">
							<a href="/petcommunity/communityBoardList.do" class="site-btn">READ
								MORE <img
								src="./resources/bootstrap_template/bootstrap_seyeong/img/icons/arrow-right-black.png"
								alt="">
							</a>
						</div>
					</div>
				</div>
			</div>
		</section>
	</div>

	<!-- Blog section end -->
	<!--    <!-- Footer section  -->
	<%@include file="../views/footer.jsp"%>

	<!--====== Javascripts & Jquery ====== -->
	<script
		src="./resources/bootstrap_template/bootstrap_seyeong/js/jquery-3.2.1.min.js"></script>
	<script
		src="./resources/bootstrap_template/bootstrap_seyeong/js/bootstrap.min.js"></script>
	<script
		src="./resources/bootstrap_template/bootstrap_seyeong/js/owl.carousel.min.js"></script>
	<script
		src="./resources/bootstrap_template/bootstrap_seyeong/js/circle-progress.min.js"></script>
	<script
		src="./resources/bootstrap_template/bootstrap_seyeong/js/main.js"></script>
		<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
		<script
		src="./resources/js/mainPage.js"></script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <%@include file="../views/header.jsp"%> --%>
<!doctype html>
<head>
<!-- header 시작-->
<!-- Stylesheets -->
<link rel="stylesheet" type="text/css"
	href="https://cdn.rawgit.com/moonspam/NanumSquare/master/nanumsquare.css">
<link rel="stylesheet"
	href="./resources/bootstrap_template/template_01/css/style.css" />
<link rel="stylesheet" href="./resources/css/head.css" />
<link rel="stylesheet" href="./resources/css/review.css" />

<!-- header 끝 -->

<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="icon" href="img/favicon.png" type="image/png">
<title>Fashiop</title>
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="./resources/bootstrap_template/bootstrap_ain/productDetail/css/bootstrap.css">
<link rel="stylesheet"
	href="./resources/bootstrap_template/bootstrap_ain/productDetail/vendors/linericon/style.css">
<link rel="stylesheet"
	href="./resources/bootstrap_template/bootstrap_ain/productDetail/css/font-awesome.min.css">
<!-- <link rel="stylesheet" href="./resources/bootstrap_template/bootstrap_ain/productDetail/vendors/owl-carousel/owl.carousel.min.css"> -->
<!-- <link rel="stylesheet" href="./resources/bootstrap_template/bootstrap_ain/productDetail/vendors/lightbox/simpleLightbox.css"> -->
<!-- <link rel="stylesheet" href="./resources/bootstrap_template/bootstrap_ain/productDetail/vendors/nice-select/css/nice-select.css"> -->
<!-- <link rel="stylesheet" href="./resources/bootstrap_template/bootstrap_ain/productDetail/vendors/animate-css/animate.css"> -->
<!-- <link rel="stylesheet" href="./resources/bootstrap_template/bootstrap_ain/productDetail/vendors/jquery-ui/jquery-ui.css"> -->
<!-- main css -->

<link rel="stylesheet"
	href="./resources/bootstrap_template/bootstrap_ain/productDetail/css/style.css">
<!-- 아래거는 리뷰 별 -->
<link rel="stylesheet"
	href="./resources/bootstrap_template/bootstrap_ain/productDetail/css/responsive.css">


<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="//code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="//code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>

<body>
	<!-- header section -->
	<header class="header-section">
		<div class="header-warp">
			<a href="main.do" class="site-logo"> <img
				src="./resources/bootstrap_template/template_01/img/logo2.png"
				alt="">
			</a>
			<ul class="main-menu">
				<c:if test="${! empty sessionScope.memberVO}">
				${sessionScope.memberVO.memberName}님, 안녕하세요
				<a href="logout.do">[로그아웃하기]</a>
					<a href="mypageselect.do">[마이 페이지]</a>
				</c:if>
				<li><a href="lostboardlist.do">분실 동물 찾기</a></li>
				<li><a href="findboardlist.do">분실 동물 신고</a></li>
				<li><a href="/petcommunity/communityBoardList.do">커뮤니티</a></li>
				<li><a href="shop.do">유기견 후원 스토어</a></li>
				<li><a href="/petcommunity/productList.do">오키도키 굿즈</a></li>
				<li><a href="/petcommunity/findHospitalList.do">동물 병원 정보</a></li>
				<li><a href="/petcommunity/cs.do">고객 문의</a></li>
				<c:if test="${empty sessionScope.memberVO}">
					<li><a href="login.do">로그인/회원가입</a></li>
				</c:if>
				<c:if test="${sessionScope.memberVO.memberId eq 'admin1234'}">
					<li><a href="login.do">관리자페이지</a></li>
				</c:if>
			</ul>
			<hr>
		</div>
	</header>
	<!--================Single Product Area =================-->
	<section id="background_section">
	<h4 id="productviewtitle">Okidoghere Goods</h4>
		<p id="productviewsubtitle">상품 상세페이지 입니다.</p>
	<div class="product_image_area">
		<div class="container">
<!-- 			<h4 id="productViewtitle">고객센터</h4> -->
<!-- 			<p id="productViewsubtitle">상품 상세정보입니다.</p> -->
			<div class="row s_product_inner">
				<div class="col-lg-6">
					<div class="s_product_img">
						<img
							src="./resources/imgs/product_image/internal/product_image/${productInfo.productName}.jpg"
							style='height: 100%; width: 100%; object-fit: contain;'>
					</div>
				</div>
				<!-- 제품 상세내역 -->
				<div class="col-lg-5 offset-lg-1" id="productInfoContainer">
					<div class="s_product_text">
						<h3>${productInfo.productName }</h3>
						<h2>${productInfo.productPrice }원</h2>
						<ul class="list">
							<li><a class="active" href="#"> <span>카테고리</span> 
									&nbsp; &nbsp; &nbsp; ${productInfo.productFeature }
							</a></li>
							<li><a class="active" href="#"> <span>재고</span> 
									&nbsp; &nbsp; &nbsp; ${productInfo.productCnt }
							</a></li>
						</ul>
						<p>${productInfo.productContent }</p>
						<form id='addCartSubmit' action="/petcommunity/buyCartList.do">

							<div class="product_count">
								<!-- 							<label for="qty">개수:</label> -->
								<label>CHOOSE !
</label> <input type="text" name="productCnt" id="sst"
									maxlength="12" value="1" title="Quantity:"
									class="input-text qty">
								<button
									onclick="var result = document.getElementById('sst'); var sst = result.value; if( !isNaN( sst )) result.value++;return false;"
									class="increase items-count" type="button">
									<i class="lnr lnr-chevron-up"></i>
								</button>
								<button
									onclick="var result = document.getElementById('sst'); var sst = result.value; if( !isNaN( sst ) &amp;&amp; sst > 1 ) result.value--;return false;"
									class="reduced items-count" type="button">
									<i class="lnr lnr-chevron-down"></i>
								</button>
							</div>



							<div class="card_area">
								<button id="addCart_btn" type="button" class="main_btn">장바구니
									담기</button>
								<input type="hidden" id="productId" name="productId"
									value="${productInfo.productId }">
							</div>
						</form>
						<div id="dialog-message" title="장바구니 이동" style='display: none'>
							장바구니에 상품을 담았습니다.<br />장바구니 페이지로 이동하시겠습니까?
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--================End Single Product Area =================-->

	<!--================Product Description Area =================-->
	<section class="product_description_area">
		<div class="container">
			<ul class="nav nav-tabs" id="myTab" role="tablist">
				<li class="nav-item"><a class="nav-link" id="home-tab"
					data-toggle="tab" href="#home" role="tab" aria-controls="home"
					aria-selected="true">상세설명</a></li>
				<li class="nav-item"><a class="nav-link active" id="review-tab"
					data-toggle="tab" href="#review" role="tab" aria-controls="review"
					aria-selected="false">리뷰</a></li>
			</ul>
			<div class="tab-content" id="myTabContent">
				<div class="tab-pane fade" id="home" role="tabpanel"
					aria-labelledby="home-tab">
						<p>급여주의사항간식의 과도한 급여는 주식의 섭취를 방해하여 영양 밸런스를 무너뜨릴 수 있으니 권장 급여량을
							참고하여 급여량을 잘 지켜주세요.</p>
						<p>간식 권장 급여량은 보통활동량을 가진 표준체형의 어덜트(성견, 성묘)의 하루 섭취 칼로리의 10%를
							기준으로 계산되었습니다. 반려동물의 체형과 활동량을 고려하여 급여해주세요.</p>
						<p>처음 급여하시는 경우 소량씩 급여하여 장이 적응할 수 있는 시간을 주세요.</p>
						<p>식이 알러지가 있는 친구라면 반드시 원재료를 확인 후 급여해주세요.</p>
						<p>질병 등 건강 문제가 있어 식이조절이 필요한 친구라면, 급여 전 전문의와 상담을 통해 급여해주세요.</p>
						
				</div>

				<div class="tab-pane fade show active" id="review" role="tabpanel"
					aria-labelledby="review-tab">
					<div class="row">
						<!-- 리뷰탭 왼쪽 시작-->
						<div class="col-lg-6">
							<div class="row total_rate">
								<div class="col-6">
									<div class="box_total">
										<h5>평점</h5>
										<h4>${avgScore}</h4>
										<h6>(${reviewListSize} 리뷰)</h6>
									</div>
								</div>
								<div class="col-6">
									<div class="rating_list">
										<h3>리뷰 ${reviewListSize} 개의 평점</h3>
										<ul class="list">
											<li><a href="#">5점 <i class="fa fa-heart"></i> <i
													class="fa fa-heart"></i> <i class="fa fa-heart"></i> <i
													class="fa fa-heart"></i> <i class="fa fa-heart"></i>
													${score5Size}
											</a></li>
											<li><a href="#">4점 <i class="fa fa-heart"></i> <i
													class="fa fa-heart"></i> <i class="fa fa-heart"></i> <i
													class="fa fa-heart"></i> <i class="fa fa-heart-o"></i>
													${score4Size}
											</a></li>
											<li><a href="#">3점 <i class="fa fa-heart"></i> <i
													class="fa fa-heart"></i> <i class="fa fa-heart"></i> <i
													class="fa fa-heart-o"></i> <i class="fa fa-heart-o"></i>
													${score3Size}
											</a></li>
											<li><a href="#">2점 <i class="fa fa-heart"></i> <i
													class="fa fa-heart"></i> <i class="fa fa-heart-o"></i> <i
													class="fa fa-heart-o"></i> <i class="fa fa-heart-o"></i>
													${score2Size}
											</a></li>
											<li><a href="#">1점 <i class="fa fa-heart"></i> <i
													class="fa fa-heart-o"></i> <i class="fa fa-heart-o"></i> <i
													class="fa fa-heart-o"></i> <i class="fa fa-heart-o"></i>
													${score1Size}
											</a></li>
										</ul>
									</div>
								</div>
							</div>
							<!-- 리뷰단 시작 -->
							<div class="review_list">


								<!-- 리뷰 시작 -->
								<c:forEach var="reviewList" items="${reviewList }">
									<div class="review_item">
										<div class="media">
											<div id="review_thmbnail" class="d-flex">
												<img
													src="resources/imgs/review_thumbnail/thumbnail_reivew.jpg"
													style="width: 60px; height: 60px; border-radius: 50%;" alt="썸네일">
											</div>
											<div class="media-body">
												<p>${reviewList.productreviewUploadtime}</p>
												<h4>${reviewList.memberId}&nbsp&nbsp&nbsp&nbsp
													<input type="hidden" id="product_id"
														value="${reviewList.productId}" /> <input type="hidden"
														id="writerId" value="${reviewList.memberId}" />
													<!-- <i class="fa fa-pencil-square-o" id="modify">&nbsp</i> -->
													<i class="fa fa-trash delete" id="delete"></i>
												</h4>
												<c:forEach begin="1" end="${reviewList.productreviewScore }"
													step="1">
													<i class="fa fa-heart"></i>
												</c:forEach>
											</div>
										</div>
										<p>${reviewList.productreviewContent }</p>
										<hr />
									</div>
								</c:forEach>
								<div id="pagination-div">
									<div id="pagination_container">
										<!--  class="col-md-offset-4"  -->
										<ul id="pagination-demo" class="pagination-lg"></ul>
									</div>
								</div>
								<!-- 리뷰틀  끝-->
								<div class="pagination-wrapper clearfix">
									<ul class="pagination float-right" id="pages">
									</ul>
								</div>
								<!-- 페이징 시작 -->
								<div id="paginationTag">
									<c:if test="${pagination.curRange ne 1 }">
										<a href="#" onClick="fn_paging(1)">[처음]</a>
									</c:if>
									<c:if test="${pagination.curPage ne 1}">
										<a href="#" onClick="fn_paging('${pagination.prevPage }')">[이전]</a>
									</c:if>
									<c:forEach var="pageNum" begin="${pagination.startPage }"
										end="${pagination.endPage }">
										<c:choose>
											<c:when test="${pageNum eq  pagination.curPage}">
												<span style="font-weight: bold;"><a href="#"
													onClick="fn_paging('${pageNum }')">${pageNum }</a></span>
											</c:when>
											<c:otherwise>
												<a href="#" onClick="fn_paging('${pageNum }')">${pageNum }</a>
											</c:otherwise>
										</c:choose>
									</c:forEach>
									<c:if
										test="${pagination.curPage ne pagination.pageCnt && pagination.pageCnt > 0}">
										<a href="#" onClick="fn_paging('${pagination.nextPage }')">[다음]</a>
									</c:if>
									<c:if
										test="${pagination.curRange ne pagination.rangeCnt && pagination.rangeCnt > 0}">
										<a href="#" onClick="fn_paging('${pagination.pageCnt }')">[끝]</a>
									</c:if>
								</div>
								<!-- 페이징 끝-->
							</div>
							<!-- 리뷰단 끝 -->
						</div>
						<!-- 리뷰탭 왼쪽 끝-->
						<div class="col-lg-6">
							<div class="review_box">
								<h4>리뷰작성</h4>
								<p>평점:</p>
								<div id="starScoreDiv">
									<span class="fa fa-heart" value="1"></span> <span
										class="fa fa-heart" value="2"></span> <span class="fa fa-heart"
										value="3"></span> <span class="fa fa-heart" value="4"></span> <span
										class="fa fa-heart" value="5"></span>
								</div>
								<!-- 								<ul class="list" id="starScoreUl"> -->
								<!-- 									<li> -->

								<!-- 											<i class="fa fa-heart" value="1"></i> -->

								<!-- 									</li> -->
								<!-- 									<li> -->

								<!-- 											<i class="fa fa-heart" value="2"></i> -->

								<!-- 									</li> -->
								<!-- 									<li> -->
								<!-- 											<i class="fa fa-heart" value="3"></i> -->
								<!-- 									</li> -->
								<!-- 									<li> -->

								<!-- 											<i class="fa fa-heart" value="4"></i> -->

								<!-- 									</li> -->
								<!-- 									<li> -->
								<!-- 										<a class="starScore" value="5"> -->
								<!-- 											<i class="fa fa-heart" value="5"></i> -->
								<!-- 										</a> -->
								<!-- 									</li> -->
								<!-- 								</ul> -->

								<p>리뷰 내용</p>
								<p style="font-size: 11px; color: gray">(1000자 이내)</p>
								<!-- <form class="row contact_form" action="/petcommunity/insertProductReview.do" method="post" id="contactForm" novalidate="novalidate"> -->
								<!-- 									<div class="col-md-12"> -->
								<!-- 										<div class="form-group"> -->
								<!-- 											<input type="text" class="form-control" id="name" name="name" placeholder="Your Full name"> -->
								<!-- 										</div> -->
								<!-- 									</div> -->

								<div class="col-md-12">
									<div class="form-group">
										<textarea class="form-control" id="productreviewContent"
											placeholder="내용을 작성해 주세요" rows="10"></textarea>
									</div>
								</div>
								<div class="col-md-12 text-right">
									<button type="button" value="submit" id="btn_modify"
										class="btn submit_btn" style="display: none;">수정하기</button>
									<button type="button" value="submit" id="btn_reviewSubmit"
										class="btn submit_btn" style="display: block;">작성하기</button>
									<input type="hidden" id="reviewProductId" name="productId"
										value="${productInfo.productId }"> <input
										type="hidden" id="loginCheck" name="loginCheck"
										value="${loginCheck }">
								</div>
								<!-- </form> -->
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	</section>
	<!--================End Product Description Area =================-->


	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<!-- 프로덕트뷰 -->
	<script
		src="./resources/bootstrap_template/bootstrap_ain/productDetail/js/jquery-3.2.1.min.js"></script>
	<script
		src="./resources/bootstrap_template/bootstrap_ain/productDetail/js/popper.js"></script>
	<script
		src="./resources/bootstrap_template/bootstrap_ain/productDetail/js/bootstrap.min.js"></script>
	<script
		src="./resources/bootstrap_template/bootstrap_ain/productDetail/js/stellar.js"></script>
	<script
		src="./resources/bootstrap_template/bootstrap_ain/productDetail/vendors/lightbox/simpleLightbox.min.js"></script>
	<script
		src="./resources/bootstrap_template/bootstrap_ain/productDetail/vendors/nice-select/js/jquery.nice-select.min.js"></script>
	<script
		src="./resources/bootstrap_template/bootstrap_ain/productDetail/vendors/isotope/imagesloaded.pkgd.min.js"></script>
	<script
		src="./resources/bootstrap_template/bootstrap_ain/productDetail/vendors/isotope/isotope-min.js"></script>
	<script
		src="./resources/bootstrap_template/bootstrap_ain/productDetail/vendors/owl-carousel/owl.carousel.min.js"></script>
	<script
		src="./resources/bootstrap_template/bootstrap_ain/productDetail/js/jquery.ajaxchimp.min.js"></script>
	<script
		src="./resources/bootstrap_template/bootstrap_ain/productDetail/js/mail-script.js"></script>
	<script
		src="./resources/bootstrap_template/bootstrap_ain/productDetail/vendors/jquery-ui/jquery-ui.js"></script>
	<script
		src="./resources/bootstrap_template/bootstrap_ain/productDetail/vendors/counter-up/jquery.waypoints.min.js"></script>
	<script
		src="./resources/bootstrap_template/bootstrap_ain/productDetail/vendors/counter-up/jquery.counterup.js"></script>
	<script
		src="./resources/bootstrap_template/bootstrap_ain/productDetail/js/theme.js"></script>

	<!-- js 이벤트 -->
	<script src="./resources/js/productReview.js"></script>
	<!-- main.js -->
	<script src="./resources/bootstrap_template/template_01/js/main.js"></script>
</body>

</html>
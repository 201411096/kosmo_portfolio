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

<!-- 
	 	<ul class="tabs">
				<li class="tab-link current" data-tab="tab-1">판매 내역</li>
				<li class="tab-link" data-tab="tab-2">판매 통계</li>
				<li class="tab-link" data-tab="tab-3">관리자 페이지</li>
			</ul>
			<hr /> -->

			<div class="col-xs-10 col-sm-10">
							<form id="productUpdate" action="/petcommunity/productUpdatePage.do">
								<div class="form-group">
									<label>상품 번호</label> <input type="text" class="form-control" name="productId" value="${ProductVO.productId}" readonly>
								</div>
								<div class="form-group">
									<label>상품명</label> <input type="text" class="form-control" name="productName" value="${ProductVO.productName}">
								</div>
								<div class="form-group">
									<label>상품 가격</label> <input type="text" class="form-control" name="productPrice" value="${ProductVO.productPrice}">
								</div>
								
								<div class="form-group">
									<label>수량</label> <input type="text" class="form-control" name="productCnt" value="${ProductVO.productCnt}">
								</div>
								
								<div class="form-group">
									<label>상품 특징</label>  
									<select class="product-filter-menu" id="productFeature" name="productFeature">
                                        <option >tagCategory</option>
                                        <option value="all" <c:if test="${ProductVO.productFeature eq 'all' }">selected</c:if> > all</option>
                                        <option value="퍼피용" <c:if test="${ProductVO.productFeature eq '퍼피용' }">selected</c:if> >퍼피용</option>
                                        <option value="성견용" <c:if test="${ProductVO.productFeature eq '성견용' }">selected</c:if> >성견용</option>
                                        <option value="전연령" <c:if test="${ProductVO.productFeature eq '전연령' }">selected</c:if> >전연령</option>
                                        <option value="노견용" <c:if test="${ProductVO.productFeature eq '노견용' }">selected</c:if> >노견용</option>
                                        <option value="소형견용" <c:if test="${ProductVO.productFeature eq '소형견용' }">selected</c:if> >소형견용</option>
                                        <option value="대형견용" <c:if test="${ProductVO.productFeature eq '대형견용' }">selected</c:if> >대형견용</option>
                                        <option value="비만견용" <c:if test="${ProductVO.productFeature eq '비만견용' }">selected</c:if> >비만견용</option>
                                        <option value="임신/수유건" <c:if test="${ProductVO.productFeature eq '임신/수유건' }">selected</c:if> >임신/수유건</option>
                                        <option value="그레인프리 사료" <c:if test="${ProductVO.productFeature eq '그레인프리 사료' }">selected</c:if> >그레인프리 사료</option>
                                        <option value="뼈관절 사료 잡화" <c:if test="${ProductVO.productFeature eq '뼈관절 사료 잡화' }">selected</c:if> >뼈관절 사료 잡화</option>
                                        <option value="피부모질 사료" <c:if test="${ProductVO.productFeature eq '피부모질 사료' }">selected</c:if> >피부모질 사료</option>
                                        <option value="기타 기능성 사료" <c:if test="${ProductVO.productFeature eq '기타 기능성 사료' }">selected</c:if> >기타 기능성 사료</option>
                                        <option value="위장개선 사료" <c:if test="${ProductVO.productFeature eq '위장개선 사료' }">selected</c:if> >위장개선 사료</option>
                                        <option value="대포장 사료" <c:if test="${ProductVO.productFeature eq '대포장 사료' }">selected</c:if> >대포장 사료</option>
                                        <option value="저알러지 사료" <c:if test="${ProductVO.productFeature eq '저알러지 사료' }">selected</c:if> >저알러지 사료</option>
                                        <option value="소프트 사료" <c:if test="${ProductVO.productFeature eq '소프트 사료' }">selected</c:if> >소프트 사료</option>
                                        <option value="오븐에 구운 사료" <c:if test="${ProductVO.productFeature eq '오븐에 구운 사료' }">selected</c:if> >오븐에 구운 사료</option>
                                    </select>
								</div>
								
								<div class="form-group">
									<label>상품 내용</label><textarea class="form-control" name="productContent" rows="5">${ProductVO.productContent}</textarea>
								</div>
								<button class="btn btn-default">수정</button>		
							</form>
						</div>
			<div class="col-md-offset-3" id="pagination_container">
				<ul id="pagination-demo" class="pagination-lg"></ul>
			</div>
	</section>
</div>
</body>
</html>
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
			</ul> -->
			<hr />

			<div class="card-body">
							<form id="productInsert" action="/petcommunity/insertProduct.do">
								<div class="form-group">
									<label>상품 번호</label> <input type="text" class="form-control" name="productId" value="" placeholder="-----" disabled>
								</div>
								<div class="form-group">
									<label>상품명</label> <input type="text" class="form-control" name="productName" value="">
								</div>
								<div class="form-group">
									<label>상품 가격</label> <input type="text" class="form-control" name="productPrice" value="">
								</div>	
								<div class="form-group">
									<label>수량</label> <input type="text" class="form-control" name="productCnt" value="">
								</div>	
													
								<div class="form-group">
									<label>상품 특징</label>  
									<select class="form-control" id="productFeature" name="productFeature">
                                        <option >tagCategory</option>
                                        <option value="퍼피용">퍼피용</option>
                                        <option value="성견용">성견용</option>
                                        <option value="전연령">전연령</option>
                                        <option value="노견용">노견용</option>
                                        <option value="소형견용">소형견용</option>
                                        <option value="대형견용">대형견용</option>
                                        <option value="비만견용">비만견용</option>
                                        <option value="임신/수유건">임신/수유건</option>
                                        <option value="그레인프리 사료">그레인프리 사료</option>
                                        <option value="뼈관절 사료 잡화">뼈관절 사료 잡화</option>
                                        <option value="피부모질 사료">피부모질 사료</option>
                                        <option value="기타 기능성 사료">기타 기능성 사료</option>
                                        <option value="위장개선 사료">위장개선 사료</option>
                                        <option value="대포장 사료">대포장 사료</option>
                                        <option value="저알러지 사료">저알러지 사료</option>
                                        <option value="소프트 사료">소프트 사료</option>
                                        <option value="오븐에 구운 사료">오븐에 구운 사료</option>
                                    </select>
								</div>
				
								<div class="form-group">
									<label>상품 내용</label><textarea class="form-control" name="productContent" rows="5"></textarea>
								</div>
								<button class="btn btn-default" name='writeBtn' id='writeBtn'>등록</button>
							</form>
						</div>

	</section>
</div>
</body>
</html>
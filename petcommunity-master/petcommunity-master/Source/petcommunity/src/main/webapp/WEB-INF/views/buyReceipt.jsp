<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../views/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>마이페이지</title>

<link rel="stylesheet" href="./resources/css/buyReceipt.css">

</head>
<body>
<div class=back>
	<div class="tedoory">
		<figure>
			<img id="buyImg"
				src="./resources/bootstrap_template/template_01/img/gallery/buyReceipt.PNG"
				alt="sing up image">
		</figure>
		<div id="Layout">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="tblTop">

			</table>
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="tblTop2">
				<tr>
					<td width="45%" class="noTd">주문번호. <span class="noStxt">${buyReceipt[0].buyId}</span></td>
				</tr>
			</table>
			<table width="100%" cellpadding="0" cellspacing="0" class="tbl stamp">
				<tr>
					<td width="7%" rowspan="5" align="center" class="titTd">구<br>매
						<br>자<br>정<br>보
					</td>
				</tr>
				<tr>
					<th>구매자 성함</th>
					<td width="25%" align="center">${buyReceipt[0].memberName}</td>
					<th width="14%">휴대폰 번호</th>
					<td align="center">${buyReceipt[0].memberTel}</td>
				</tr>
				<tr>
					<th>배송지</th>
					<td colspan="3">${buyReceipt[0].memberAddress}</td>
				</tr>
				<tr>
					<th>구매 날짜</th>
					<td align="center" colspan="3">${buyReceipt[0].buyListDate}</td>
				</tr>
			</table>

			<div class="basicDiv"></div>

			<table  class="tbl">
				<c:forEach items="${buyReceipt}" var="buy">
					<tr>
						<th class="pimg">이미지</th>
						<th>상품명</th>
						<th>수량</th>
						<th>가격</th>
						<th>결제 금액</th>
					</tr>
					<tr>
						<td class="item pimg"><img
							src="./resources/imgs/product_image/internal/product_image/${buy.productName}.jpg" /></td>
						<td class="item"><div>${buy.productName}</div></td>
						<td class="article">${buy.buyCnt}</td>
						<td class="price">${buy.productPrice}</td>
						<td class="sum">${buy.buyListTotalprice}</td>
					</tr>
				</c:forEach>
			</table>

		</div>
	</div>
	</div>
</body>
</html>
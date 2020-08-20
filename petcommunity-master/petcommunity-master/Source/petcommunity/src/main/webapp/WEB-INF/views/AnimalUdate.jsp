<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">

<title>반려동물 정보 수정</title>
<% String animalId =(String)request.getParameter("animalId"); %>
<% String animalName =(String)request.getParameter("animalName"); %>
<% String animalBirthday =(String)request.getParameter("animalBirthday"); %>
<% String name =(String)request.getParameter("animalName"); %>
<% String animalGender =(String)request.getParameter("animalGender"); %>
<% String animalFeature =(String)request.getParameter("animalFeature"); %>
<!-- Font Icon -->
<link rel="stylesheet"
	href="./resources/bootstrap_template/template_01/css2/fonts1/material-icon/css/material-design-iconic-font.min.css">
<link rel="stylesheet" type="text/css"
   href="https://cdn.rawgit.com/moonspam/NanumSquare/master/nanumsquare.css"> 
<!-- Main css -->
<link rel="stylesheet"
	href="./resources/bootstrap_template/template_01/css2/style.css">

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/twbs-pagination/1.4.2/jquery.twbsPagination.min.js"></script>
</head>
<body>

	<div class="main2">
		<!-- Sign up form -->
			<div class="container">
				<div class="signup-content">
					<div class="signup-form"> 
						<!-- <figure>
							<img
								src="./resources/bootstrap_template/template_01/img/gallery/%ED%9A%8C%EC%9B%90%EA%B0%80%EC%9E%85.PNG"
								alt="sing up image" style="width:300px ">
						</figure> -->
						
						<h2 class="form-title">반려동물정보 수정</h2>
						<form action="animalUpdate.do" method="POST" class="register-form"
							id="register-form">
							<input type="hidden" name="animalId" value="<%=animalId%>"/>
							<div class="form-group">
								<label for="animalName"><i
									class="zmdi zmdi-account material-icons-name"></i></label> <input
									type="text" name="animalName" id="animalName" value="<%= animalName %>"
									/>
							</div>
							
								<div class="form-group">
									<input type="hidden" id="genderValue" value="<%= animalGender %>">
									<label for="animalGender">암컷</label> <input type="radio"
										name="animalGender" id="animalGender" value="암컷" />
								</div>

								<div class="form-group">
									<label for="animalGender2">수컷</label> <input type="radio"
										name="animalGender" id="animalGender2" value="수컷" />

								</div>

								<div class="form-group">
									<span><i class="zmdi zmdi-face"></i>&nbsp;반려동물 생일</span> <input
										type="date" name="animalBirthday" id="animalBirthday" value="<%= animalBirthday %>"/>
								</div>

								<div class="form-group">
									<textarea name="animalFeature" id="animalFeature" cols="53"
										rows="10" ><%= animalFeature %></textarea>
								</div>
								<div class="form-group form-button">
									<input type="submit" name="animalIn" id="animalUp"
										class="form-submit" value="등록" />
								</div>
						</form>
					</div>

				</div>
			</div>

	</div>

	<!-- JS -->
	<script
		src="./resources/bootstrap_template/template_01/js/jquery-3.2.1.min.js"></script>
	<script
		src="./resources/bootstrap_template/template_01/js/bootstrap.min.js"></script>
	<script
		src="./resources/bootstrap_template/template_01/js/jquery.slicknav.min.js"></script>
	<script
		src="./resources/bootstrap_template/template_01/js/owl.carousel.min.js"></script>
	<script
		src="./resources/bootstrap_template/template_01/js/circle-progress.min.js"></script>
	<script src="./resources/bootstrap_template/template_01/js2/main.js"></script>
	<script src="./resources/js/mypage.js"></script>
</body>
<!-- This templates was made by Colorlib (https://colorlib.com) -->
</html>
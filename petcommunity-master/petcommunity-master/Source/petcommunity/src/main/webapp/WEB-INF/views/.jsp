<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">

<title>반려동물 등록</title>

<!-- Font Icon -->
<link rel="stylesheet"
	href="./resources/bootstrap_template/template_01/css2/fonts1/material-icon/css/material-design-iconic-font.min.css">

<!-- Main css -->
<link rel="stylesheet"
	href="./resources/bootstrap_template/template_01/css2/style.css">
<link rel="stylesheet"
	href="./resources/css/mypage.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/twbs-pagination/1.4.2/jquery.twbsPagination.min.js"></script>
</head>
<body>

	<div class="main2">
		<!-- Sign up form -->
		<section class="signup">
			<div class="container">
				<div class="signup-content">
					<div class="signup-form">
						<!-- <figure>
							<img
								src="./resources/bootstrap_template/template_01/img/gallery/%ED%9A%8C%EC%9B%90%EA%B0%80%EC%9E%85.PNG"
								alt="sing up image" style="width:300px ">
						</figure> -->
						<c:if test="${0 eq result}">
							<input type="hidden" id="result" value='${msg}' />
						</c:if>
						<h2 class="form-title">반려동물 등록</h2>
						<form action="animalinsert.do" method="POST" class="register-form" enctype="multipart/form-data"
							id="register-form">
							<div class="form-group">
								<label for="animalName"><i
									class="zmdi zmdi-account material-icons-name"></i></label> <input
									type="text" name="animalName" id="animalName" placeholder="반려동물 이름"
									/>
							</div>
							
								<div class="form-group">

									<label for="animalGender">암컷</label> <input type="radio"
										name="animalGender" id="animalGender" value="암컷" />
								</div>

								<div class="form-group">
									<label for="animalGender2">수컷</label> <input type="radio"
										name="animalGender" id="animalGender2" value="수컷" />

								</div>

								<div class="form-group">
									<span><i class="zmdi zmdi-face"></i>&nbsp;반려동물 생일</span> <input
										type="date" name="animalBirthday" id="animalBirthday" />
								</div>

								<div class="form-group">
									<textarea name="animalFeature" id="animalFeature" cols="53"
										rows="10" placeholder="반려동물 특징"></textarea>
								</div>
								<div class="form-group form-button">
									 <input type="file" name="file" class="file" accept="image/gif, image/jpeg, image/png, image/jpg"/>
									<input type="submit" name="animalIn" id="animalIn"
										class="form-submit" value="등록" />
								</div>
						</form>
					</div>

				</div>
			</div>
		</section>

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
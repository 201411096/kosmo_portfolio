<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../views/header.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>마이페이지</title>

<!-- Font Icon -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<link rel="stylesheet"
	href="./resources/bootstrap_template/template_01/css2/fonts1/material-icon/css/material-design-iconic-font.min.css">

<!-- Main css -->

<link rel="stylesheet" href="./resources/css/mypage.css">
<link rel="stylesheet" href="./resources/css/mypage-messageVer2.css">

</head>
<body>
<div class="main2">
		<!-- Sign up form -->
		<div class="signup-content2">

			<br />
			<div class="ultag">
				<ul class="tabs">
					<li class="tab-link current" data-tab="tab-1">나의 반려동물</li>
					<li class="tab-link2" data-tab="tab-2">나의 주문내역</li>
					<li class="tab-link3" data-tab="tab-3">회원 정보 수정</li>
					<li class="tab-link4" data-tab="tab-4">쪽지 확인</li>
				</ul>
				
				</div>
			<hr>

			<div id="tab-1" class="tab-content current">
				<!-- <div class="memberCountCon"></div> -->
				<c:choose>
					<c:when test="${empty animalList}">
						
							<h4>현재 등록 되어있는 반려동물이 없습니다</h4>
							
								<span class="glyphicon glyphicon-plus animal"></span>
							
						
					</c:when>

					<c:otherwise>

						<c:forEach items="${animalList}" var="animal">

							<form class="frm" method="post">
								<input type="hidden" name="animalName"
									value="${animal.animalName}" /> <input type="hidden"
									name="animalId" value="${animal.animalId}" /> <input
									type="hidden" name="animalBirthday"
									value="${animal.animalBirthday}" /> <input type="hidden"
									name="animalGender" value="${animal.animalGender}" /> <input
									type="hidden" name="animalFeature"
									value="${animal.animalFeature}" />


								<div class="tedoorymain">
									<div align="right">
										<span class="glyphicon glyphicon-plus icon-my animal"></span>
									</div>
									<table class="aniamlTb">
										<colgroup>
											<col style="width: 20%" />
											<col style="width: 25%" />
											<col style="width: 25%" />
											<col style="width: 25%" />
										</colgroup>
										<tr class="my1">
											<td><c:if test="${! empty animal.imgAnimal}">
													<img class="imgAnimal"
														src="resources/imgs/animal/${animal.animalId}/${animal.imgAnimal}">
												</c:if>
												<c:if test="${empty animal.imgAnimal}">
													<img class="imgAnimal"
														src="resources/bootstrap_template/template_01/img/gallery/pngwing.com (8).png">
												</c:if></td>
											<td class="mypage-animal2">
												<h3>${animal.animalName}</h3>
												<h5>태어난지&nbsp;&nbsp;${animal.dateBirthday}일</h5>
											</td>
											<td class="mypage-animal2">
												<h3>생일</h3>
												<h5>${animal.animalBirthday}</h5>
											</td>
											<td class="mypage-animal2">
												<h3>성별</h3>
												<h5>${animal.animalGender}</h5>
											</td>
										</tr>
									</table>
									<hr>
									<br>
										<textarea name="animalFeature" id="animalFeature" disabled>${animal.animalFeature}</textarea>
									<br><br>
									<div align="right">
										<input type="submit" value="수정" id="uBtn" class="uBtn" />
										<button id='dBtn'>
											<a href='animalDelete.do?animalId=${animal.animalId}'>삭제</a>
										</button>
									</div>
								</div>
							</form>
							<br>

						</c:forEach>
					</c:otherwise>
				</c:choose>
			</div>

			<div id="tab-2" class="tab-content">
				<div class="tedoory3">
					<figure>
						<img
							src="./resources/bootstrap_template/template_01/img/gallery/mypage.png"
							alt="">
					</figure>
					<br><br>
					<table class="mypage-table">
						<colgroup>
							<col style="width: 15%" />
							<col style="width: 15%" />
							<col style="width: 50%" />
							<col style="width: 20%" />
						</colgroup>
						<thead>
							<tr>
								<td>주문번호</td>
								<td>주문날짜</td>
								<td>상품명</td>		
								<td>총가격</td>
							</tr>
						</thead>
						<tbody id="buyListTbody">
						</tbody>
					</table>
					<ul id="pagination-demo" class="pagination-lg"></ul>
				</div>
				
			</div>
			<div id="tab-3" class="tab-content">
				<div class="tedoory3">
					<form action="memberUpdate.do" method="POST"
						class="memberUpdate-form" id="memberUpdate-form">
						<div class="form-group">
							<label for="name"><i
								class="zmdi">이름</i></label> <input
								type="text" name="memberName" id="memberName" />
						</div>
						<div class="form-group">
							<label for="tel"><i class="zmdi">휴대폰 번호</i></label> <input
								type="text" name="memberTel" id="memberTel" />
						</div>
						<div class="form-group">
							<label for="id"><i class="zmdi">ID</i></label> <input
								type="text" id="memberId" disabled="disabled" /> <input
								type="hidden" name="memberId" id="memberid">
						</div>

						<div class="form-group">
							<label for="pass"><i class="zmdi">비밀번호</i></label><input
								type="password" name="memberPassword" id="memberPassword" />
						</div>
						<div class="form-group">
							<label for="re-pass"><i class="zmdi">비밀번호
									확인</i></label> <input type="password" name="re_pass" id="re_pass" />
						</div>
						<span id="passresult"></span>
						<div class="form-group">
							<label for="email"><i class="zmdi">email</i></label> <input
								type="email" name="memberEmail" id="memberEmail" />
						</div>

						<div class="form-group">
							<label class='zmdi'>주소</label> <input type="text"
								id="sample2_postcode" placeholder="우편번호"> <input
								type="button" id="addrBtn" onclick="sample2_execDaumPostcode()"
								value="우편번호 찾기"><br> <input type="text"
								id="sample2_address" name="memberAddress"><br> <input
								type="text" id="sample2_detailAddress" placeholder="상세주소">
							<input type="text" id="sample2_extraAddress">
						</div>

						<div id="layer"
							style="display: none; position: fixed; overflow: hidden; z-index: 1; -webkit-overflow-scrolling: touch;">
							<img src="//t1.daumcdn.net/postcode/resource/images/close.png"
								id="btnCloseLayer"
								style="cursor: pointer; position: absolute; right: -3px; top: -3px; z-index: 1"
								onclick="closeDaumPostcode()" alt="닫기 버튼">
						</div>
						<div class="form-group">
							<input type="submit" name="memberUpdate" class="form-submit"
								value="정보 수정" />
						</div>
					</form>
				</div>
			</div>

			<div id="tab-4" class="tab-content">
				<!-- <span id="showMessage">쪽지 확인</span><span id="slash"> / </span><span id="writeNewMessage">새로 전송</span> -->
				<div class="tedoory" id="message-table">
					<div id="div-searchSomeone">
						<input type="text" name="searchSomeone" id="searchSomeone"
							placeholder="사용자를 검색하세요"></input>
						<button id="btn-searchSomeone">검색</button>
					</div>
					<hr>
					<div id="div-memberList"></div>
					<div id="firstDiv-chat">
						<div id="div-chat"></div>
						<div id="div-send">
							<input type="text" id="writeMessage" class="writeMessage" placeholder="메시지를 입력하세요"></input>
							<button id="btn-message">전송</button>
						</div>
					</div>
				</div>

				<!-- <div class="tedoory" id="message-table2">
				</div> -->
			</div>


		</div>





	</div>
	<%@include file="../views/footer.jsp"%>
	<!-- JS -->
	<script
	src="https://cdnjs.cloudflare.com/ajax/libs/twbs-pagination/1.4.2/jquery.twbsPagination.min.js"></script>
	<script src="./resources/js/mypage.js"></script>
	<script src="./resources/js/mypage-messageVer2.js"></script>
	<script src="./resources/bootstrap_template/template_01/js2/main.js"></script>
	<script
		src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

	<script>
// 	 toastr["info"]("content!!!!<br /><br /><button type="button" class="btn clear">Yes</button>", "title!!!")
	
		// 우편번호 찾기 화면을 넣을 element
		var element_layer = document.getElementById('layer');

		function closeDaumPostcode() {
			// iframe을 넣은 element를 안보이게 한다.
			element_layer.style.display = 'none';
		}

		function sample2_execDaumPostcode() {
			new daum.Postcode(
					{
						oncomplete : function(data) {
							// 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

							// 각 주소의 노출 규칙에 따라 주소를 조합한다.
							// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
							var addr = ''; // 주소 변수
							var extraAddr = ''; // 참고항목 변수

							//사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
							if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
								addr = data.roadAddress;
							} else { // 사용자가 지번 주소를 선택했을 경우(J)
								addr = data.jibunAddress;
							}

							// 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
							if (data.userSelectedType === 'R') {
								// 법정동명이 있을 경우 추가한다. (법정리는 제외)
								// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
								if (data.bname !== ''
										&& /[동|로|가]$/g.test(data.bname)) {
									extraAddr += data.bname;
								}
								// 건물명이 있고, 공동주택일 경우 추가한다.
								if (data.buildingName !== ''
										&& data.apartment === 'Y') {
									extraAddr += (extraAddr !== '' ? ', '
											+ data.buildingName
											: data.buildingName);
								}
								// 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
								if (extraAddr !== '') {
									extraAddr = ' (' + extraAddr + ')';
								}
								// 조합된 참고항목을 해당 필드에 넣는다.
								document.getElementById("sample2_extraAddress").value = extraAddr;

							} else {
								document.getElementById("sample2_extraAddress").value = '';
							}

							// 우편번호와 주소 정보를 해당 필드에 넣는다.
							document.getElementById('sample2_postcode').value = data.zonecode;
							document.getElementById("sample2_address").value = addr;
							// 커서를 상세주소 필드로 이동한다.
							document.getElementById("sample2_detailAddress")
									.focus();

							// iframe을 넣은 element를 안보이게 한다.
							// (autoClose:false 기능을 이용한다면, 아래 코드를 제거해야 화면에서 사라지지 않는다.)
							element_layer.style.display = 'none';
						},
						width : '100%',
						height : '100%',
						maxSuggestItems : 5
					}).embed(element_layer);

			// iframe을 넣은 element를 보이게 한다.
			element_layer.style.display = 'block';

			// iframe을 넣은 element의 위치를 화면의 가운데로 이동시킨다.
			initLayerPosition();
		}

		// 브라우저의 크기 변경에 따라 레이어를 가운데로 이동시키고자 하실때에는
		// resize이벤트나, orientationchange이벤트를 이용하여 값이 변경될때마다 아래 함수를 실행 시켜 주시거나,
		// 직접 element_layer의 top,left값을 수정해 주시면 됩니다.
		function initLayerPosition() {
			var width = 300; //우편번호서비스가 들어갈 element의 width
			var height = 400; //우편번호서비스가 들어갈 element의 height
			var borderWidth = 5; //샘플에서 사용하는 border의 두께

			// 위에서 선언한 값들을 실제 element에 넣는다.
			element_layer.style.width = width + 'px';
			element_layer.style.height = height + 'px';
			element_layer.style.border = borderWidth + 'px solid';
			// 실행되는 순간의 화면 너비와 높이 값을 가져와서 중앙에 뜰 수 있도록 위치를 계산한다.
			element_layer.style.left = (((window.innerWidth || document.documentElement.clientWidth) - width) / 2 - borderWidth)
					+ 'px';
			element_layer.style.top = (((window.innerHeight || document.documentElement.clientHeight) - height) / 2 - borderWidth)
					+ 'px';
		}
	</script>
</body>
<!-- This templates was made by Colorlib (https://colorlib.com) -->
</html>
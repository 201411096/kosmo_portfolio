$(document)
		.ready(

				// 페이징 시작
				// ================================================================
				function() {
					var curPage;
					var defaultOpts = {
						visiblePages : 5,
						onPageClick : function(event, page) {
							$('#page-content').text('page' + page);
							curPage = page;
							getDataInpaging();
						}
					};

					$('.tab-link2').on('click', function() {
						getData();

					});

					function getDataInpaging() {
						$
								.ajax({
									type : 'post',
									async : true,
									url : '/petcommunity/mypageselect.do',
									contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
									data : {
										"curPage" : curPage
									},
									dataType : 'json',
									success : function(resultData) {
										console.log("getDataInpaging")
										window.scrollTo(0, 0);
										drawbuyTable(resultData);
									},
									error : function(request, status, error) {
										console.log("code:" + request.status
												+ "\n" + "message:"
												+ request.responseText + "\n"
												+ "error:" + error);
									}
								});
					}

					function getData() {
						$
								.ajax({
									type : 'post',
									async : true,
									url : '/petcommunity/mypageselect.do',
									contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
									data : {
										"curPage" : curPage,
									},
									dataType : 'json',
									success : function(resultData) {
										drawbuyTable(resultData);
										var totalPages = resultData.pagination.pageCnt;
										var currentPage = $('#pagination-demo')
												.twbsPagination(
														'getCurrentPage');
										$('#pagination-demo').twbsPagination(
												'destroy');
										$('#pagination-demo').twbsPagination(
												$.extend({}, defaultOpts, {
													// 추가하는 부분
													prev : "이전",
													next : "다음",
													first : '«',
													last : '»',
													// 여기까지 4줄~
													startPage : currentPage,
													totalPages : totalPages
												}));
									},
									error : function(request, status, error) {
										console.log("code:" + request.status
												+ "\n" + "message:"
												+ request.responseText + "\n"
												+ "error:" + error);
									}
								});
					}

					function drawbuyTable(data) {
						$('#buyListTbody').empty();
						var trPrefix = '<tr>';
						var trSuffix = '</tr>';
						var tdPrefix = '<td>';
						var tdSuffix = '</td>';
						if (data.buyListSize == 0) {
							var listContent = trPrefix
									+ '<td colspan="4" class=buyListNull>'
									+ "주문 내역이 없습니다" + tdSuffix + trSuffix;

							$('#buyListTbody').append(listContent);
						} else {
							for (var i = 0; i < data.buyListSize; i++) {
								var listContent = trPrefix+
								tdPrefix + data.buyList[i].buyId + tdSuffix 
												
										+ tdPrefix
										+ data.buyList[i].buyDate
										+ tdSuffix 

										+ '<td class=buyListTd>'
										+ '<a href="buyReceipt.do?buy='
										+ data.buyList[i].buyId + '">'
										+ data.buyList[i].buyName + '</a>'
										+ tdSuffix+
										
										tdPrefix
										+ data.buyList[i].buyTotal
										+ tdSuffix + trSuffix;

								$('#buyListTbody').append(listContent);
							}
						}
					}
					// 페이징 끝
					// ================================================================

					// 회원 정보 수정페이지
					$('.tab-link3')
							.on(
									'click',
									function() {
										$
												.ajax({
													type : 'post',
													async : true,
													url : '/petcommunity/memberUpdate1.do',
													contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
													dataType : 'json',
													success : function(data) {

														document
																.getElementById("memberName").value = data.list.memberName;
														document
																.getElementById("memberTel").value = data.list.memberTel;
														document
																.getElementById("memberId").value = data.list.memberId;
														document
																.getElementById("memberEmail").value = data.list.memberEmail;
														document
																.getElementById("memberid").value = data.list.memberId;

													}
												});

									});

					// 회원 수정시 유효성 검사
					$('#memberUpdate-form input[type=submit]')
							.click(
									function() {
										var re = /^[a-zA-Z0-9]{4,12}$/;
										var email = /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/;

										var submit1 = false;
										if ($('#memberName').val() === "") {
											alert("이름을 입력하세요");
											return false;
										} else if ($('#memberTel').val() === "") {
											alert("전화번호를 입력하세요.");
											return false;
										}

										else if ($('#memberPassword').val() === "") {
											alert("비밀번호를 입력하세요.");
											return false;
										} else if ($('#re_pass').val() === "") {
											alert("비밀번호 확인을 입력하세요.");
											return false;
										} else if ($('#memberPassword').val() !== $(
												'#re_pass').val()) {
											alert("비밀번호 확인이 다릅니다.");
											return false;
										} else if (!re
												.test($('#memberPassword')
														.val())
												|| !re
														.test($('#re_pass')
																.val())) {
											alert("비밀번호는 4~12의 영문자와 숫자로 입력해주세요");
											return false;
										} else if ($('#memberEmail').val() === "") {
											alert("이메일을 입력하세요.");
											return false;
										} else if (!email
												.test($('#memberEmail').val())) {
											alert("이메일 형식이 아닙니다");
											return false;
										} else if ($('#sample2_address').val() === "") {
											alert("주소를 입력하세요");
											return false;
										} else
											submit1 = true;

										if (submit1 === true) {
											alert("수정되었습니다! 마이페이지로 이동합니다.")
											$('#memberUpdate-form').submit();
										}
									});

					// 반려동물 등록시 널값 처리
					$('#register-form input[type=submit]').click(
							function() {

								var submit = false;
								if ($('#animalName').val() === "") {
									alert("반려동물 이름을 입력하세요");
									return false;
								} else if ($("input:radio[name=animalGender]")
										.is(":checked") == false) {
									alert("반려동물의 성별을 선택하세요");
									return false;
								} else if ($('#animalBirthday').val() === "") {
									alert("반려동물 생일을 입력하세요");
									return false;
								} else if ($('#animalFeature').val() === "") {
									alert("반려동물 특징을 입력하세요");
									return false;
								}

								else
									submit = true;
								if (submit === true)
									$('#register-form').submit();
							});
					// 반려 동물 등록 후 닫기 버튼 누르면 원래 부모페이지 새로고침.
					$("#mypageButton").click(function() {
						opener.parent.location.reload();
						close();
					});

					// 반려동물 등록시 새창
					$(".animal")
							.click(
									function() {
										window
												.open("animalInsert.do",
														"반려동물 등록",
														"width=520,height=750,left=700,top=150")
									});

					// 반려동물 수정 클릭시 새창 뜨면서 form submit
					$(".uBtn")
							.on(
									"click",
									function() {

										$('.frm').attr("action",
												"AnimalUdate.do");
										$('.frm').attr("target", "formInfo");
										window
												.open("", "formInfo",
														"height=750, width=520,left=700, top=150");
										$('.frm').submit();

									});
					// 암컷, 수컷 일경우 체크 눌려짐 효과.
					if ($("#genderValue").val() == '암컷') {
						$("#animalGender").prop('checked', true);
					} else {

						$("#animalGender2").prop('checked', true);
					}

					// 반려동물 정보 수정후 부모페이지 새로고침
					$("#animalUp").click(function() {
						opener.parent.location.reload();
						close();
					});

					if ($("#result").val() == 0) {
						alert("오류 , 다시 등록 해주세요")
					}
					;
					// 반려동물 삭제시 확인창 뜨기. 취소하면 이벤트 실행안됨.
					$(document).on("click", "#dBtn", function() {
						var result = confirm('삭제 하시겠습니까?');

						if (result) {

						} else {
							event.preventDefault();
						}
					});

					// 마이페이지 탭 연결.
					$('ul.tabs li').click(function() {
						var tab_id = $(this).attr('data-tab');

						$('ul.tabs li').removeClass('current');
						$('.tab-content').removeClass('current');

						$(this).addClass('current');
						$("#" + tab_id).addClass('current');
					});

				});
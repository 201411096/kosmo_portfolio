var curPage;
var defaultOpts = {
	visiblePages : 10,
	onPageClick : function(event, page) {
		$('#page-content').text('page' + page);
		curPage = page;
		getDataInpaging();
	}
};

getData();
$('#searchBtn').on('click', function() {
	getData();

});

function getDataInpaging() {
	$.ajax({
		type : 'post',
		async : true,
		url : '/petcommunity/qnaList.do',
		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
		data : {
			"searchWord" : $('#keywordInput').val(),
			"searchType" : $('#searchType').val(),
			"curPage" : curPage
		},
		dataType : 'json',
		success : function(resultData) {
			console.log("getDataInpaging")
			window.scrollTo(0, 0);
			drawQnaTable(resultData);
		},
		error : function(request, status, error) {
			console.log("code:" + request.status + "\n" + "message:"
					+ request.responseText + "\n" + "error:" + error);
		}
	});
}

function getData() {
	$.ajax({
		type : 'post',
		async : true,
		url : '/petcommunity/qnaList.do',
		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
		data : {
			"searchWord" : $('#keywordInput').val(),
			"searchType" : $('#searchType').val(),
			"curPage" : curPage,
		},
		dataType : 'json',
		success : function(resultData) {
			drawQnaTable(resultData);
			var totalPages = resultData.pagination.pageCnt;
			var currentPage = $('#pagination-demo').twbsPagination(
					'getCurrentPage');
			$('#pagination-demo').twbsPagination('destroy');
			$('#pagination-demo').twbsPagination($.extend({}, defaultOpts, {
				prev : "이전",
				next : "다음",
				first : '«',
				last : '»',

				startPage : currentPage,
				totalPages : totalPages
			}));
		},
		error : function(request, status, error) {
			console.log("code:" + request.status + "\n" + "message:"
					+ request.responseText + "\n" + "error:" + error);
		}
	});
}

function drawQnaTable(data) {
	$('#QnaBoardTbody').empty();

	var trPrefix = '<tr>';
	var trPrefix_admin = '<tr id="adminList">';
	var trSuffix = '</tr>';
	var tdPrefix = '<td>';
	var tdPrefix_boardId = '<td id="td_boardId" width="7%" style="text-align:left">';
	var tdPrefix_boardImage = '<td width="10%">';
	var tdPrefix_memberId = '<td id="td_memberId" width="12% style="text-align:center">';
	var tdPrefix_uploadtime = '<td id="td_uploadtime" width="13%" style="text-align:center">';
	var tdPrefix_readcount = '<td id="td_readcount" width="6%" style="text-align:center">';
	var tdPrefix_title = '<td id="td_title" style="text-align:left">';
	var tdSuffix = '</td>';

	for (var i = 0; i < data.QnaBoardVOListSize; i++) {
		
		var imgCon = "";
		var preImage = "";
		if (data.fileList[i] != "empty") {
			// title 뒤에 이미지 첨부
			imgCon = '<img alt="" src="resources/bootstrap_template/bootstrap_seyeong/img/fileImage.png">';
			// title 앞에 이미지 첨부
			preImage = '<img id="titleImage" alt="이미지" src="resources/imgs/qnaboard/'+ data.QnaBoardVOList[i].questionboardId + '/'+ data.fileList[i] + '">';
		}
		
		// 관리자 답변 글인 경우 title 앞에 답변이미지 첨부
		var listContent = "";
		if (data.QnaBoardVOList[i].questionboardAnswerflag == 1) {
			listContent = trPrefix_admin;
			tdPrefix_title = '<td id="boardtitle"> <img alt="없음" src="resources/bootstrap_template/bootstrap_seyeong/img/reImage.png">';
		} else {
			listContent = trPrefix;
			tdPrefix_title = '<td id="boardtitle">';
		};

		
		listContent

		+= tdPrefix_boardId + data.QnaBoardVOList[i].questionboardId + tdSuffix

		+ tdPrefix_boardImage + preImage + tdSuffix

		+ tdPrefix_title
				+ '<a href="/petcommunity/qnaContent.do?questionboardId='
				+ data.QnaBoardVOList[i].questionboardId + '">'
				+ data.QnaBoardVOList[i].questionboardTitle + imgCon + tdSuffix

				+ tdPrefix_memberId + data.QnaBoardVOList[i].memberId + tdSuffix

				+ tdPrefix_uploadtime + data.QnaBoardVOList[i].questionboardUploadtime
				+ tdSuffix

				+ tdPrefix_readcount + data.QnaBoardVOList[i].questionboardReadcount
				+ tdSuffix

				+ trSuffix;

		$('#QnaBoardTbody').append(listContent);
	}
}

// 글쓰기버튼
$('#writeBtn').click(function() {
	$.ajax({
		url : "/petcommunity/checkLogin.do?",
		type : "POST",
		success : function(data) {
			if (data == "loginRequired") {
				alert("로그인이 필요합니다.");
				window.location.href = "/petcommunity/login.do";
			} else if (data == "write") {
				window.location.href = "/petcommunity/write.do";
			} else {
				alert("에러");
			}
		}
	});
});

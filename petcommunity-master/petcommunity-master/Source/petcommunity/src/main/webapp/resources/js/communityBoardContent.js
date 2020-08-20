$(function(){
	
	//커멘트 textarea에 글을 쓰기 시작하면 그때 댓글버튼 활성화
	$('#commentBtn').attr('disabled',true);
	$('#commentTextarea').focusin(function(){		
		if($('#commentTextarea').text()!=null){
			$('#commentBtn').attr('disabled',false);
		}else if($('#commentTextarea').text()==""){
			$('#commentBtn').attr('disabled',true);
		}
	});
	//목록보기 클릭
	$('#goToList').click(function(){
		window.location.href='/petcommunity/communityBoardList.do?';
	});
	//게시글삭제버튼 클릭
	$('#communityBoardDelete').click(function(){
		if(confirm("글을 삭제하시겠습니까?")) {
			window.location.href='/petcommunity/communityBoardDelete.do?communityboardId='+$("#communityBoardDelete").val();
		}	
	});
	//게시글수정버튼 클릭
	$('#communityBoardModify').click(function(){
		window.location.href='/petcommunity/communityBoardModifyPage.do?communityboardId='+$("#communityBoardDelete").val();
	});
	
	//댓글삭제버튼 클릭
	$('#commentDelete').click(function(){
		if(confirm("댓글을 삭제하시겠습니까?")) {	
			window.location.href='/petcommunity/commentDelete.do?boardcommentId='+$("#boardcommentId").val()+'&communityboardId='+$("#communityboardId").val();
		}	
	});
	
	//추천 버튼
	$('#like').click(function(){
	
		if($('#like').attr('src')=='resources/imgs/communityboard/like/like.PNG'){		
			$.ajax({
	    		url: '/petcommunity/likeContent.do?communityboardId='+$("#forRecommend").val(),
	    		type: "POST",
	    		success : function(data){
	    			$('#like').attr('src','resources/imgs/communityboard/like/liked.PNG');
	    		},
	    		error : function(){
	    			alert("에러");
	    		}
	    	});
		}else{			
			$.ajax({
	    		url: "/petcommunity/dislikeContent.do?communityboardId="+$("#forRecommend").val(),
	    		type: "POST",
	    		success : function(data){
	    			$('#like').attr('src','resources/imgs/communityboard/like/like.PNG');
	    		},
	    		error : function(){
	    			alert("에러");
	    		}
	    	});
		}
		
	});
	
	
});


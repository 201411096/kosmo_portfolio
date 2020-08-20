	
// 수정버튼
	$('#modifyBtn').click(function(){
		$.ajax({
    		url: "/petcommunity/checkId.do?questionboardId="+$("#modifyBtn").val(),
    		type: "POST",
    		success : function(data){
    			console.log(data);
    			if(data=="loginRequired"){
    				console.log(111);
    				alert("로그인이 필요합니다.");
    				window.location.href="/petcommunity/login.do";
    			}else if(data=="misMatch"){
    				console.log(222);
    				alert("본인이 작성한 글만 수정 가능합니다.");
    				window.location.href="/petcommunity/cs.do";
    				}else if(data=="match"){
    					console.log(333);
    					window.location.href="/petcommunity/qnaModify.do?questionboardId="
    						+$("#modifyBtn").val();
    				}else{
    					alert("에러");
    				}
    		}
		});
	});
	
// 삭제버튼
	$('#deleteBtn').click(function(){
		
		$.ajax({
    		url: "/petcommunity/checkId.do?questionboardId="+$("#deleteBtn").val(),
    		type: "POST",
    		success : function(data){
    			console.log(data);
    			if(data=="loginRequired"){
    				alert("로그인이 필요합니다.");
    				window.location.href="/petcommunity/login.do";
    			}else if(data=="misMatch"){
    				alert("본인이 작성한 글만 삭제 가능합니다.");
    				window.location.href="/petcommunity/cs.do";
    				}else if(data=="match"){
    					var result=confirm("삭제하시겠습니까?");
    					if(result){
    					window.location.href="/petcommunity/delete.do?questionboardId="
    						+$("#deleteBtn").val();
    					}
    				}else{
    					alert("에러");
    				}
    		}
		});
	});
	

$(function(){
	$(".tagCategory").on("click", function(){
		tagCategory = $(this).attr("value");
		alert(tagCategory)
		getData(tagCategory);
	});
});

function getData(tagCategory){
	alert("getData 도착" + tagCategory)
	$.ajax({
		type : 'post',
		async:true,
		url : "/petcommunity/productSelect.do",
		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
		data : {"tagCategory" : tagCategory,
			},
		dataType : 'json',
		success : function(resultData){
			
		}
	});
}
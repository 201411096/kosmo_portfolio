 var lastScroll = 0;
    $(window).scroll(function(event){ // 스크롤이 움직일때 마다 이벤트 실행
        // 현재 스크롤의 위치를 저장할 st 변수
        var st = $(this).scrollTop();
        // 스크롤 상하에 따른 반응 정의
        if (st > lastScroll){
            if ($(window).scrollTop() >= 300 && $(window).scrollTop()<=400) { 
            	$.ajax({
            		type : 'post',
            		async : true,
            		url : '/petcommunity/mainCount1.do',
            		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
            		success : function(data) {
            			var memberCountConTxt = data;
            			$({
            				val : 0
            			}).animate({
            				val : memberCountConTxt
            			}, {
            				duration : 2000,
            				step : function() {
            					var num = numberWithCommas(Math.floor(this.val));
            					$("#memberCountCon1").text(num);
            				},
            				complete : function() {
            					var num = numberWithCommas(Math.floor(this.val));
            					$("#memberCountCon1").text(num);
            				}
            			});

            			function numberWithCommas(x) {
            				return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
            			}
            			
            		}
            	});



            	$.ajax({
            		type : 'post',
            		async : true,
            		url : '/petcommunity/mainCount2.do',
            		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
            		success : function(data) {
            			var memberCountConTxt = data;
            			$({
            				val : 0
            			}).animate({
            				val : memberCountConTxt
            			}, {
            				duration : 2000,
            				step : function() {
            					var num = numberWithCommas(Math.floor(this.val));
            					$("#memberCountCon2").text(num);
            				},
            				complete : function() {
            					var num = numberWithCommas(Math.floor(this.val));
            					$("#memberCountCon2").text(num);
            				}
            			});

            			function numberWithCommas(x) {
            				return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
            			}
            			
            		}
            	});



            	$.ajax({
            		type : 'post',
            		async : true,
            		url : '/petcommunity/mainCount3.do',
            		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
            		success : function(data) {
            			var memberCountConTxt = data;
            			$({
            				val : 0
            			}).animate({
            				val : memberCountConTxt
            			}, {
            				duration : 2000,
            				step : function() {
            					var num = numberWithCommas(Math.floor(this.val));
            					$("#memberCountCon3").text(num);
            				},
            				complete : function() {
            					var num = numberWithCommas(Math.floor(this.val));
            					$("#memberCountCon3").text(num);
            				}
            			});

            			function numberWithCommas(x) {
            				return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
            			}
            			
            		}
            	});	// 이벤트 정의
  
            }
        } else {
        }
        // 현재 스크롤 위치(st)를 마지막 위치로 업데이트
        lastScroll = st;
   

    });
    
    
  //WordCloud here ↓↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓    
    $(document).ready(function(){
    	$('#wordCloud').click(function(){
            // 500 * 500 사이즈 윈도우 창을 변수로 담아서 새로 열어준다. 
    		let newWindow = window.open("","width=1000 height=1000");   
            //newWindow라는 창에 img태그 생성해주기
    		let img = newWindow.document.createElement("img"); 
    		img.setAttribute("src","https://localhost:8443/petcommunity/resources/imgs/wordCloud/wordCloud.png");  //이미지가 저장되어있는 경로를 src 안에 넣기
    		img.setAttribute("width","1000");   //width속성 변경
    		img.setAttribute("height","1000");  //height속서 변경
    		newWindow.document.body.appendChild(img);   //body안에 가장 마지막 요소로 img 추가
    	});
    });    
    


<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- c태그 사용위해 불러옴 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>인기있는 여행지</title>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<!-- JQuery사용 위해 불러옴 -->
<style>
.container {
margin:20px;
	width: 300vw;
	/* transition: transform 1s ease  0.5s; */
	transition-duration: 0.5s;
}

.inner {
	width: 100vw;
	float: left;
	posiiton:relative;
}

.inner img {
	width: 40%;
	height:40%
}
button{
	text-align:center;
}
span{
	font-size:130%;
	font-weight:600;
	position:absolute;
	bottom:-10px;
	left:600px;
	text-shadow: 3px 3px 3px white;
	
}
</style>
</head>
<body style="margin: 0" >
	<div style="overflow: hidden">
		<div class="container">
			<div class="inner">
				<img src="#"  >
				<span>title</span>

			</div>
			<div class="inner">
				<img src="#" >

			</div>
			<div class="inner">
				<img src="#">
			</div>
		</div>
	</div>

	<button class="button1">1</button>
	<button class="button2">2</button>
	<button class="button3">3</button>
</body>
<script>
$(".button1").click(function(){
    $(".container").css({"transform":"translate(0vw)"});
});
$(".button2").click(function(){
   $(".container").css({"transform":"translate(-100vw)"});
});
$(".button3").click(function(){
   $(".container").css({"transform":"translate(-200vw)"});
});

$(document).ready(function() {
$.ajax({
	type:"get"  
	, url: "popularImage" 
	, dataType :"json"  
	,success : function(data){ 
		console.log(data) 
		if(data.success){
			console.log(data.list);
			for(var i=0 ; i< data.list.length ; i++){
				console.log("list: ",data.list[i]);
				console.log("img: ",data.list[i].firstImage);
				$(".inner>img").eq(i).attr({"src":data.list[i].firstImage});
				console.log(".inner>img : ",$(".inner>img").eq(i).attr("src"));	
			}
		
		}
	}
	,error : function(e){ //실패하면 => 해당 내용이 e로 들어옴
		console.log(e);
	}
})
});


	</script>
</html>
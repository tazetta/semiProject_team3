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

	width: 300vw;
	transition-duration: 0.5s;
}

.inner {
	width: 100vw;
	float: left;
	posiiton:relative;
}

.inner img {
	width: 800px;
	height: 700px;
}
button{
	text-align:center;
}
span{
	font-size:130%;
	font-weight:600;
	position:absolute;
	bottom:20px;
	left:300px;
	text-shadow: 0px 1px #ffffff, 4px 4px 0px #dad7d7;

	
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
				<span>title</span>

			</div>
			<div class="inner">
				<img src="#">
				<span>title</span>
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
				$(".inner>span").eq(i).html(data.list[i].title);
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
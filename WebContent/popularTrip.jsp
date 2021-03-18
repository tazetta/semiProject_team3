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
/* .container {
	width: 300vw;
	transition-duration: 0.5s;
}

.inner {
	width: 100vw;
	float: left;
}

.inner img {
	width: 70%;
	height: 500px;
}

button {
	text-align: center;
} */
/* 브라우저 마진과 패딩 리셋 */
	* {margin:0;padding:0;}

	/* INPUT 가리기 */
	.section [id*="slide"] {display:none;}
	
	/* 슬라이드 영역 - max-width 크기를 조절해주면 됩니다*/
	.section .slidewrap {max-width:700px;margin-left:20px;overflow:hidden;position:relative;}
	.section .slidelist {white-space:nowrap;font-size:0;}
	.section .slidelist > li {display:inline-block;vertical-align:middle;width:100%;transition:all .5s;}
	.section .slidelist > li > a {display:block;position:relative;}

	/* 좌우로 넘기는 LABEL버튼에 대한 스타일 */
	.section .slide-control {position:absolute;top:0;left:0;z-index:9;width:100%;height:100%;}
	.section .slide-control label {position:absolute;z-index:1;top:50%;transform:translateY(-50%);padding:50px;cursor:pointer;}
	.section .slide-control .left {left:20px;background:url('./left.png') center center / 100% no-repeat;}
	.section .slide-control .right {right:20px;background:url('./right.png') center center / 100% no-repeat;}
	.section .slide-control [class*="control"] {display:none;}

	/* INPUT이 체크되면 변화값이 li까지 전달되는 스타일 */
	.section [id="slide01"]:checked ~ .slidewrap .slidelist > li {transform:translateX(0%);}
	.section [id="slide02"]:checked ~ .slidewrap .slidelist > li {transform:translateX(-100%);}
	.section [id="slide03"]:checked ~ .slidewrap .slidelist > li {transform:translateX(-200%);}
	
	/*  INPUT이 체크되면 변화값이 좌우 슬라이드 버튼을 담고 있는 div 까지 전달되는 스타일 */
	.section [id="slide01"]:checked ~ .slidewrap .control01 {display:block;}
	.section [id="slide02"]:checked ~ .slidewrap .control02 {display:block;}
	.section [id="slide03"]:checked ~ .slidewrap .control03 {display:block;}
	
	.img{
		width:700px;
		height:500px;
	}
	.slidewrap span{
		font-size:110%;
		font-weight:600;
		position:absolute;
		top:0;
		color: black;
		z-index:9;
		text-shadow: 2px 2px 2px white;
		background-color:#F6E3CE;
		padding:5px 10px;
	}
	
	.a{
	 z-index:10;
	 border: 3px solid red;
	}
	
	
</style>
</head>
<body >
	<div class="section">
	<input type="radio" name="slide" id="slide01" checked>
	<input type="radio" name="slide" id="slide02">
	<input type="radio" name="slide" id="slide03">
	<div class="slidewrap">
		<span>인기있는 여행지 </span>
		<ul class="slidelist">
			<li>
				<a class="a" href="#" >
					<img src="#" class="img">
				</a>
			</li>
			<li>
				<a class="a" href="#" >
					<img src="#" class="img">
				</a>
			</li>
			<li>
				<a class="a" href="#" >
					<img src="#" class="img">
				</a>
			</li>
		</ul>

		<div class="slide-control">
			<div class="control01">
				<label for="slide03" class="left"></label>
				<label for="slide02" class="right"></label>
			</div>
			<div class="control02">
				<label for="slide01" class="left"></label>
				<label for="slide03" class="right"></label>
			</div>
			<div class="control03">
				<label for="slide02" class="left"></label>
				<label for="slide01" class="right"></label>
			</div>
		</div>
	</div>
</div>

		<!-- <div class="container">
			 <ul>
				<li class="inner"><img src="#"></li>
				<li class="inner"><img src="#"></li>
				<li class="inner"><img src="#"></li>
			</ul> 
			<div class="inner">
				<img src="#">
			</div>
			<div class="inner">
				<img src="#">
			</div>
			<div class="inner">
				<img src="#">
			</div>
		</div> -->

	<!-- <button class="button1">1</button>
	<button class="button2">2</button>
	<button class="button3">3</button> -->
</body>
<script>
	/* $(".button1").click(function() {
		$(".container").css({
			"transform" : "translate(0vw)"
		});

	});
	$(".button2").click(function() {
		$(".container").css({
			"transform" : "translate(-100vw)"
		});
	});
	$(".button3").click(function() {
		$(".container").css({
			"transform" : "translate(-200vw)"
		});
	}); */

	$(document).ready(
			function() {
				$.ajax({
					type : "get",
					url : "popularImage",
					dataType : "json",
					success : function(data) {
						console.log(data)
						if (data.success) {
							console.log(data.list);
							for (var i = 0; i < data.list.length; i++) {
								console.log("list: ", data.list[i]);
								console.log("img: ", data.list[i].firstImage);
								console.log("contentIdx: ",data.list[i].contentId);
								$(".img").eq(i).attr({
									"src" : data.list[i].firstImage
								});
								$(".a").eq(i).attr({"href":"./tripDetail?contentId="+data.list[i].contentId});
								/* $(".a").eq(i).append("<span>"+data.list[i].title+"</span>"); */
								console.log(".img : ", $(".img").eq(i).attr("src"));
								console.log("title:",data.list[i].title);
		
							}
						}
					},
					error : function(e) { //실패하면 => 해당 내용이 e로 들어옴
						console.log(e);
					}
				})
			});
</script>
</html>
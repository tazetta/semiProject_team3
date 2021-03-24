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

/* 브라우저 마진과 패딩 리셋 */
* {margin:0;padding:0;}
	.section input[id*="slide"] {display:none;}
	.section {margin-left:80px; margin-top:40px;}
	.section .slidewrap {max-width:800px;margin:0px;}
	.section .slidelist {white-space:nowrap;font-size:0;overflow:hidden;position:relative;}
	.section .slidelist > li {display:inline-block;vertical-align:middle;width:100%;transition:all .5s;}
	.section .slidelist > li > a {display:block;position:relative;}
	.section .slidelist > li > a img {width:100%;}
	.section .slidelist label {position:absolute;z-index:10;top:50%;transform:translateY(-50%);padding:50px;cursor:pointer;}
	.section .slidelist .textbox {position:absolute;z-index:1;top:50%;left:50%;transform:translate(-50%,-50%);line-height:1.6;text-align:center;}
	.section .slidelist .textbox h3 {font-size:36px;color:#fff;;transform:translateY(30px);transition:all .5s;}
	.section .slidelist .textbox p {font-size:16px;color:#fff;opacity:0;transform:translateY(30px);transition:all .5s;}
	
	/* input에 체크되면 슬라이드 효과 */
	.section input[id="slide01"]:checked ~ .slidewrap .slidelist > li {transform:translateX(0%);}
	.section input[id="slide02"]:checked ~ .slidewrap .slidelist > li {transform:translateX(-100%);}
	.section input[id="slide03"]:checked ~ .slidewrap .slidelist > li {transform:translateX(-200%);}
	
	/* 좌,우 슬라이드 버튼 */
	.slide-control > div {display:none;}
	.section .left {left:30px;background:url('./left.png') center center / 100% no-repeat;}
	.section .right {right:30px;background:url('./right.png') center center / 100% no-repeat;}
	.section input[id="slide01"]:checked ~ .slidewrap .slide-control > div:nth-child(1) {display:block;}
	.section input[id="slide02"]:checked ~ .slidewrap .slide-control > div:nth-child(2) {display:block;}
	.section input[id="slide03"]:checked ~ .slidewrap .slide-control > div:nth-child(3) {display:block;}
	
	
	.img{
		width:800px;
		height:460px;
	}
	.slidewrap span{
		font-size:110%;
		font-weight:600;
		position:absolute;
		top:140px;
		left:380px;
		color: black;
		z-index:9;
		text-shadow: 2px 2px 2px white;
		background-color:  blanchedalmond ;
		padding:5px 10px;
	}
	

	
</style>
</head>
<body >
	<div class="section">
	<input type="radio" name="slide" id="slide01" checked>
	<input type="radio" name="slide" id="slide02">
	<input type="radio" name="slide" id="slide03">
	<div class="slidewrap">
		<span>인기있는 여행지 <img src="./css/emoji1.png" alt="emoji" width="20px" height="20px"></span>
		<ul class="slidelist">
			<!-- 슬라이드 영역 -->
			<li class="slideitem">
				<a href="" target="_blanck" class="a">
					<div class="textbox">
						<h3>첫번째</h3>
					</div>
					<img src="" class="img">
				</a>
			</li>
			<li class="slideitem">
				<a href="" target="_blanck" class="a">
					
					<div class="textbox">
						<h3>두번째</h3>
					</div>
					<img src="" class="img">
				</a>
			</li>
			<li class="slideitem">
				<a href="" target="_blanck" class="a"> 
					<div class="textbox">
						<h3>세번째</h3>
					</div>
					<img src="" class="img">
				</a>
			</li class="slideitem">

			<!-- 좌,우 슬라이드 버튼 -->
			<div class="slide-control">
				<div>
					<label for="slide03" class="left"></label>
					<label for="slide02" class="right"></label>
				</div>
				<div>
					<label for="slide01" class="left"></label>
					<label for="slide03" class="right"></label>
				</div>
				<div>
					<label for="slide02" class="left"></label>
					<label for="slide01" class="right"></label>
				</div>
			</div>

		</ul>
	</div>
</div>

</body>
<script>


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
								console.log("contentId: ", data.list[i].contentId);
								$(".img").eq(i).attr({"src" : data.list[i].firstImage });
								$(".a").eq(i).attr({"href" : "./tripDetail?contentId="+ data.list[i].contentId});
								 $(".textbox>h3").eq(i).html(data.list[i].title); 
								 
								console.log(".img : ", $(".img").eq(i).attr("src"));
								console.log("title:", data.list[i].title);
								console.log("href:",$(".a").eq(i).attr("href"));

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
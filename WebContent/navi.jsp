<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>navi</title>
<link rel="icon" href="./koreaCI.png">
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
<style>
div.bar {
	display: flex;
	background-color: white;
	border-bottom: 1.6px solid #025169;
	box-shadow: 3px 2px 3px lightgray;
	/* border-top:0.5px solid #0B610B; */
	/* #025169  #540B0B  */
	height: 50px;
	border-radius: 2px;
/* 	border: 1px solid lightgray; */
	justify-content: center;
	align-items: center;
	margin:  10px 0px;
	width: 100%;
	min-width: 1000px;
}


.naviUl li{
position: relative;
	float: left;
	padding: 5px 30px;
	color: black;
	font-size: 100%;
	font-weight: 600;
	font-family: "NanumGothic";
	text-align: center;
	width: 110px;
	height: 20px;
	list-style-type: none;
	display: inline;
}

.naviUl a:link {
	text-decoration: none;
	font-size: 90%;
		color: black;
}

.naviUl a:visited {
		color: black;
}

/* @font-face{
	font-family: "NanumGothicBold";
	src : local("NanumGothicBold");
	src:url('./css/NanumGothicBold.ttf') format('truetype');
	font-style:normal;
} */
</style>
</head>
<body>
	<div class="bar">
		<ul class="naviUl">
			<li><a href="themeContentList">테마별</a></li>

			<li><a href="areaContentList">지역별</a></li>

			<li><a href="boardList">커뮤니티</a></li>

			<c:choose>
				<c:when test="${sessionScope.isManager eq true}">
				
					<li><a href="qnaList">고객센터</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="qnaListUser">고객센터</a></li>
				</c:otherwise>

			</c:choose>


			<c:if test="${sessionScope.isManager ne true}">
				<li><a href="profile">마이페이지</a></li>
			</c:if>
			<c:if test="${sessionScope.isManager eq true}">
				<li><a href="managerList">관리자 페이지</a></li>
			</c:if>
		</ul>
	</div>

</body>
<script>
	$(".naviUl li a").hover(function () {
        $(this).css({"color":"#A4A4A4"});
    },
    function(){
    	$(this).css({"color":"black"});}
    );
	
	</script>
</html>
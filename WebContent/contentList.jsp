<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>테마별</title>
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
<style>
div.contentList {
	position: absolute;
	top: 25%;
}

div.content {
	padding: 0px 15px;
	border: 1px solid black;
	width: 120px;
	text-align: center;
}

div.clear {
	border-color: red;
	clear: left;
	border: 1px solid black;
}

div.areaList>div {
	float: left;
	border: 1px solid black;
	padding: 5px 5px;
	width: 140px;
	/* text-align: center; */
}

div.areaList {
	position: absolute;
	left: 25%;
	top: 10%;
}

nav {
	width: 1000px;
	height: 50px;
	margin: 10px;
	left: 50%;
}

.mainUl>li {
	position: relative;
	font-weight: 600;
	font-size: 24;
	left: 50%;
	float: left;
	border: 1px solid black;
	padding: 10px 15px;
	list-style-type: none;
}

.mainUl>li:hover {
	background-color: darkkhaki;
}

a:link {
	color: black;
	text-decoration: none;
}

a:visited {
	color: black;
	text-decoration: none;
}
</style>
</head>
<body>
	<nav>
		<ul class="mainUl">
			<li class='content'><a href="./contentList">테마별</a></li>
			<li class='area'><a href="./areaList">지역별</a></li>
			<li class='community'>커뮤니티</li>
			<li class='help'>고객센터</li>
			<li class='mypage'>마이페이지</li>
		</ul>
	</nav>

	<div class="contentList">
		<c:forEach items="${contentList}" var="content">
			<div class="content" id="${content.contentCode}">
				<a href="./themeContentList?nav=${content.contentCode}">${content.name}</a>
			</div>
		</c:forEach>
	</div>

	<form action="resultList" method="get">
		<div class="areaList">
			<c:forEach items="${areaList}" var="area" varStatus="status">
				<c:if test="${status.index % 5 == 0}">
					<div class="clear">
						<input type="checkbox" name="local" value="${area.areaCode}">${area.name}
					</div>
				</c:if>
				<c:if test="${status.index % 5 != 0}">
					<div>
						<input type="checkbox" name="local" value="${area.areaCode}">${area.name}
					</div>
				</c:if>
			</c:forEach>
		</div>
		<input type="hidden" name="nav" value="${nav}" /> 
		<input type="hidden" name="type" value="theme"/>
		<input type="submit" value="검색" />
	</form>
</body>
<script>
	$(document).ready(function() {
		$("div#"+${nav}).css({"background-color" : "lightgray"});
	});
</script>
</html>
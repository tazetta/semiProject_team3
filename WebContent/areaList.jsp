<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>지역별</title>
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
<style>
	div.first {
		border-color: red;
		clear: left;
		border: 1px solid black;
		color: red;
	}
	div {
		float: left;
		border: 1px solid black;
		width: 100px;
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
		padding: 0px 15px;
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

	<form action="localList" method="GET">
	<div>
		<c:forEach items="${list}" var="area" varStatus="status">
			<c:if test="${status.index % 8 == 0}">
				<div class="first">
					<input type="checkbox" name="city" value="${area.cityCode}">${area.name}
				</div>
			</c:if>
			<c:if test="${status.index % 8 != 0}">
				<div>
					<input type="checkbox" name="city" value="${area.cityCode}">${area.name}
				</div>
			</c:if>
		</c:forEach>
		<br /> <input type="submit" value="검색">
	</div>
	</form>
</body>
<script>
	var msg = "${msg}";
	if (msg != "") {
		alert(msg);
	}
</script>
</html>
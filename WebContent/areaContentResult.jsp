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
div.areaList {
	position: absolute;
	top: 25%;
}

div.area {
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

div.cityList>div {
	float: left;
	border: 1px solid black;
	padding: 5px 5px;
	width: 140px;
	/* text-align: center; */
}

div.cityList {
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

table, th, td {
	border: 1px solid black;
	border-collapse: collapse;
	padding: 5px 10px;
	text-align: center;
	margin-left: auto;
	margin-right: auto;
	margin-top: 200px;
}

.pageArea {
	width: 500px;
	text-align: center;
	margin: 10px;
}

.pageArea span {
	font-size: 16px;
	border: 1px solid lightgray;
	padding: 2px 10px;
	color: gray;
}

a {
	text-decoration: none;
}

#page {
	font-weight: 600;
	color: red;
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

		<div class="areaList">
			<c:forEach items="${areaList}" var="area">
				<div class="area" id="${area.areaCode}">
					<a href="./areaContentList?nav=${area.areaCode}">${area.name}</a>
				</div>
			</c:forEach>
		</div>

	<form action="resultList" method="get">
		<div class="cityList">
			<c:forEach items="${cityList}" var="city" varStatus="status">
				<c:if test="${status.index % 5 == 0}">
					<div class="clear">
						<input type="checkbox" name="local" value="${city.cityCode}">${city.name}
					</div>
				</c:if>
				<c:if test="${status.index % 5 != 0}">
					<div>
						<input type="checkbox" name="local" value="${city.cityCode}">${city.name}
					</div>
				</c:if>
			</c:forEach>
			<input type="hidden" name="nav" value="${nav}" /> 
			<input type="hidden" name="type" value="area" /> 
				<input type="submit"value="검색" />
		</div>
	</form>
	<table>
		<tr>
			<th>사진</th>
			<th>제목</th>
			<th>등록일</th>
			<th>즐겨찾기 수</th>
		</tr>
		<c:forEach items="${list}" var="selectResult" varStatus="status">
			<tr>
				<th><img src="${selectResult.firstImage}" width="100px"
					height="100px" /></th>
				<th><a href="./tripDetail?contentId=${selectResult.contentId }"  target="_blank">${selectResult.title}</a></th>
				<th>${selectResult.reg_date}</th>
				<th>${selectResult.bookmarkCnt}</th>
			</tr>
		</c:forEach>
	</table>
	
		<div class="pageArea">
			<span> 
				<c:if test="${currPage == 1}">이전</c:if> 
				<c:if	 test="${currPage > 1}">
					<a href="./resultList?${url}&page=${currPage-1}">이전</a>
				</c:if>
			</span> 
			<span id="page">${currPage}</span> 
			<span> 
				<c:if test="${currPage == maxPage}">다음</c:if> 
				<c:if test="${currPage < maxPage}"><a href="./resultList?${url}&page=${currPage+1}">다음</a></c:if>
			</span>
		</div>
</body>
<script>
	$(document).ready(function() {
		$("div#"+${nav}).css({"background-color" : "lightgray"});
	});
</script>
</html>
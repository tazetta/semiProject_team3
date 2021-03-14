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
	top: 20%;
}

div.area {
	padding: 5px 15px;
	border: 1px solid black;
	width: 120px;
	height: 30px;
	text-align: center;
}

div.clear {
	clear: left;
	border: 1px solid black;
}

div.cityList>div {
	float: left;
	border: 1px solid black;
	padding: 5px 5px;
	width: 140px;
}

div.cityList {
	position: absolute;
	left: 25%;
	top: 15%;
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
	padding: 10px 20px;
	text-align: center;
	margin-left: auto;
	margin-right: auto;
	margin-top: 200px;
}
.title{
	width:50%;
}

.pageArea {
	width: 100%;
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
button{
	padding: 20px 20px;
}
</style>
</head>
<body>
	<jsp:include page="top.jsp" />
	<jsp:include page="navi.jsp" />
	
	
	<div>
		<button onclick="location.href='./search?keyword=${keyword}&searchType=${searchType}&alignType=bookmarkCnt'">인기순</button>
		<button onclick="location.href='./search?keyword=${keyword}&searchType=${searchType}&alignType=reg_date'">최신순</button>
	</div>

	<table>
		<tr>
			<th>사진</th>
			<th>제목</th>
			<th>등록일</th>
			<th>즐겨찾기 수</th>
		</tr>
		<c:forEach items="${list}" var="result" varStatus="status">
			<tr>
				<th><img src="${result.firstImage}" width="100px"
					height="100px" /></th>

				<th class="title"><a href="./tripDetail?contentId=${result.contentId}"
					target=window.open()>${result.title}</a></th>
				<th>${result.regDate}</th>
				<th>${result.bookmarkCnt}</th>
			</tr>
		</c:forEach>
	</table>

	<div class="pageArea">
		<span> 
			<c:if test="${currPage == 1}">이전</c:if> 
			<c:if test="${currPage > 1}">
				<a href="./search?${url}&page=${currPage-1}">이전</a>
			</c:if>
		</span> <span id="page">${currPage}</span> 
		<span> 
			<c:if test="${currPage == maxPage}">다음</c:if> 
			<c:if test="${currPage < maxPage}">
				<a href="./search?${url}&page=${currPage+1}">다음</a>
			</c:if>
		</span>
		<span>${currPage}/${maxPage}</span>
	</div>
</body>
<script>
</script>
</html>
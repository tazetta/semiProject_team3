<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 페이지 - 메인</title>
<link rel="stylesheet" type="text/css" href="basic.css">
</head>
<style>
table, th, td {
	border: 1px solid black;
	border-collapse: collapse;
	padding: 10px 20px;
	text-align: center;
	margin-left: auto;
	margin-right: auto;
	margin-top: 30px;
}

.regDate {
	width:80px;
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

#page {
	font-weight: 600;
	color: red;
}
#tripSearchBar{
	text-align: center;
}
</style>
<body>
	<jsp:include page="top.jsp" />
	<jsp:include page="admin_navbar.jsp" />
	<div>
		<div id='tripSearchBar'>
			<form action="tripSearch" method="GET">
				<select name="searchType">
					<option value="title">여행지 이름</option>
					<option value="address">여행지 위치</option>
				</select> <input type="text" name="keyword"> 
				<input type="submit" value="검색">
			</form>
		</div>
		<div>
			<table>
				<tr>
					<th>contentID</th>
					<th>여행지 이름</th>
					<th>등록 날짜</th>
					<th>비활성화 여부</th>
					<th>비활성화 여부2</th>
				</tr>
				<c:forEach items="${tripList}" var="trip">
					<tr>
						<td>${trip.contentId}</td>
						<td class='title'>${trip.title}</td>
						<td class='regDate'>${trip.reg_date}</td>
						<c:if test="${trip.deactivate eq true}">
							<td>Y</td>
						</c:if>
						<c:if test="${trip.deactivate eq false}">
							<td>N</td>
						</c:if>
						<td>${trip.deactivate}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div class="pageArea">
			<span> <c:if test="${currPage == 1}">이전</c:if>
			<c:if test="${currPage > 1}">
				<c:if test="${keyword eq null}">
					<a href="./tripManage?page=${currPage-1}">이전</a>
				</c:if>
				<c:if test="${keyword ne null}">
					<a href="./tripSearch?${url}&page=${currPage-1}">이전</a>
				</c:if>
			</c:if>
			</span> 
			<span id="page">
				${currPage}
			</span> 
			<span> 
				<c:if test="${currPage == maxPage}">다음</c:if>
				<c:if test="${currPage < maxPage}">
					<c:if test="${keyword  eq null}">
						<a href="./tripManage?page=${currPage+1}">다음</a>
					</c:if>
					<c:if test="${keyword  ne null}">
						<a href="./tripSearch?${url}&page=${currPage+1}">다음</a>
					</c:if>
				</c:if>
			</span>
			<span>${currPage}/${maxPage}</span>
		</div>
	</div>
</body>
<script>
	
</script>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 페이지 - 메인</title>
<link rel="stylesheet" type="text/css" href="basic.css">
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
</head>
<style>
table{
	height:200px;
}
table, th, td {
	border: 1px solid black;
	border-collapse: collapse;
	padding: 10px 20px;
	text-align: center;
	margin-left: auto;
	margin-right: auto;
	margin-top: 30px;
}
.title{
	width:200px;
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

#tripSearchBar {
	text-align: center;
}

div.tripManageList {
	position: absolute;
	top: 20%;
}

div.tripManageName {
	padding: 5px 15px;
	border: 1px solid black;
	width: 120px;
	height: 30px;
	text-align: center;
}
div.deactivate{
	position:absolute;
	left:60%;
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
				</select> 
				<input type="text" name="keyword"> 
				<input type="submit" value="검색">
			<input type="checkbox" name="deactivate" value="TRUE"/>비활성화 여부
			</form>
			<button onclick="location.href='tripDeactivateFilter?${isDeactivate}'">비활성화된 게시물만 보기</button>
		</div>
		
		<div class="tripManageList">
			<div class="tripManageName" id="99">
				<a href="./tripManageList?tripNav=99">여행지 목록</a>
			</div>
			<div class="tripManageName" id="100">
				<a href="./tripInsertInformation?tripNav=100">여행지 저장</a> 
			</div>
		</div>
		
		<div>
			<table>
				<tr>
					<th>contentID</th>
					<th>여행지 이름</th>
					<th>등록 날짜</th>
					<th>비활성화 여부</th>
				</tr>
				<c:forEach items="${tripList}" var="trip">
					<tr>
						<td>${trip.contentId}</td>
						<td class='title'><a
							href="./tripManageDetail?contentId=${trip.contentId}&page=${currPage}">${trip.title}</a></td>
						<td>${trip.reg_date}</td>
						<td>
						<c:if test="${trip.deactivate eq true}">
							Y
						</c:if>
						<c:if test="${trip.deactivate eq false}">
							N
						</c:if>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div class="pageArea">
			<span> <c:if test="${currPage == 1}">이전</c:if> <c:if
					test="${currPage > 1}">
					<c:if test="${keyword eq null && deactivate eq 'FALSE'}">
						<a href="./tripManageList?page=${currPage-1}">이전</a>
					</c:if>
					<c:if test="${keyword ne null}">
						<a href="./tripSearch?${url}&page=${currPage-1}">이전</a>
					</c:if>
					<c:if test="${deactivate eq 'TRUE'}">
						<a href="./tripDeactivateFilter?page=${currPage-1}">이전</a>
					</c:if>
				</c:if>
			</span> <span id="page"> ${currPage} </span> <span> <c:if
					test="${currPage == maxPage}">다음</c:if> <c:if
					test="${currPage < maxPage}">
					<c:if test="${keyword eq null && deactivate eq 'FALSE'}">
						<a href="./tripManageList?page=${currPage+1}">다음</a>
					</c:if>
					<c:if test="${keyword  ne null}">
						<a href="./tripSearch?${url}&page=${currPage+1}">다음</a>
					</c:if>
					<c:if test="${deactivate eq 'TRUE'}">
						<a href="./tripDeactivateFilter?page=${currPage+1}">이전</a>
					</c:if>
				</c:if>
			</span> 
			<span>${currPage}/${maxPage}</span>
		</div>
	</div>
</body>
<script>
	$(document).ready(function() {
		$("div#"+${tripNav}).css({"background-color" : "lightgray"});
	});
	
	$('a').hover(function(){
		   $(this).css({'font-weight':'600'});
	},function(){
		    $(this).css({'font-weight':'1'});
	});
</script>
</html>
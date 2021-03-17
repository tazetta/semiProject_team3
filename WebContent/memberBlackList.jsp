<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>블랙리스트 회원정보 리스트</title>
</head>
<style>
table, th,td {
	border: 1px solid lightgray;
	border-collapse: collapse;
	text-align: center;
	padding: 20px;
}

#page{
     margin-top:10px;
     text-align:center;           
}

#page span{
	font-size : 16px;
	border:1px solid lightgray;
	padding: 2px 10px;
	margin:2px
}
</style>
<body>
	<!--상단페이지-->
	<jsp:include page="top.jsp" />

	<!--상단네비-->
	<jsp:include page="navi_manager.jsp" />

	<!-- 사이드 네비 -->
	<jsp:include page="side_manager.jsp" />


		<div class="memberList_main">
			<table>
				<tr>
					<th>블랙리스트 번호</th>
					<th>아이디</th>
					<th>블랙리스트 등록일</th>
					<th>블랙리스트 등록사유</th>
					<th>등록 관리자</th>
					<th></th>
				</tr>
				<c:forEach items="${memberBlackList}" var="black">
					<tr>
						<td>${black.blackidx}</td>
						<td>${black.id}</td>
						<td>${black.reg_date}</td>
						<td>${black.reason}</td>
						<td>${black.managerid}</td>
						<td><a href="./memberBlackDetail?id=${black.id}">상세보기</a></td>
					</tr>
				</c:forEach>
			</table>
		</div>
		
		<div id="page">
			<span>
				<c:if test="${currPage==1}">이전</c:if>
				<c:if test="${currPage>1}">
					<a href='./memberBlackList?page=${currPage-1}'>이전</a>
				</c:if>
			</span>
			<span>${currPage}</span>
			<span>
				<c:if test="${currPage == maxPage}">다음</c:if>
         		<c:if test="${currPage < maxPage}">
         			<a href="./memberBlackList?page=${currPage+1}">다음</a></c:if>
			</span>		
		</div>
</body>
</html>
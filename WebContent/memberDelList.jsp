<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>탈퇴 회원정보 리스트</title>
</head>
<style>
table, th,td {
	border: 1px solid lightgray;
	border-collapse: collapse;
	text-align: center;
	padding: 20px;
}
</style>
<body>
	<!--상단페이지-->
	<jsp:include page="top.jsp" />

	<!--상단네비-->
	<jsp:include page="navi_manager.jsp" />

	<!-- 사이드 네비&검색필터 -->
	<jsp:include page="side_manager.jsp" />


		<div class="memberList_main">
			<table>
				<tr>
					<th>가입일</th>
					<th>탈퇴여부</th>
					<th>아이디</th>
					<th>이름</th>
					<th>핸드폰 번호</th>
					<th>이메일</th>
					<th></th>
				</tr>
				<c:forEach items="${memberDelList}" var="member">
					<tr>
						<td>${member.reg_date}</td>
						<td>${member.withdraw}</td>
						<td>${member.id}</td>
						<td>${member.name}</td>
						<td>${member.phone}</td>
						<td>${member.email}</td>
						<td><a href="memberDelDetail?id=${member.id}">상세보기</a></td>
					</tr>
				</c:forEach>
			</table>
		</div>
</body>
</html>
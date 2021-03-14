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
button {
	
}
</style>
<body>
	<!--상단페이지-->
	<jsp:include page="top.jsp" />

	<!--상단네비-->
	<jsp:include page="admin_navbar.jsp" />

	<div id="admin_main">
		<table>
			<tr>
				<th>관리자 번호</th>
				<th>등록일</th>
				<th>이름</th>
			</tr>
			<c:forEach items="${adminList}" var="sysAdmin">
				<tr>
					<td>${sysAdmin.infoidx}</td>
					<td>${sysAdmin.reg_date}</td>
					<td>${sysAdmin.name}</td>
					<td><a href="adminDel?infoidx=${sysAdmin.infoidx}">삭제</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
<script>
</script>
</html>
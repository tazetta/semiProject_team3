<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	table, th, td{
		border: 1px solid black;
		border-collapse: collapse;
		padding: 5px 10px;
	}
</style>
</head>
<body>
	<jsp:include page="loginBox.jsp"/>
	<h3>회원리스트</h3>
	<hr/>
	<table>
		<tr>
			<th>ID</th><th>NAME</th><th>EMAIL</th><th>삭제</th>
		</tr>
		<c:forEach items="${list}" var="member">
		<tr>
			<td><a href="detail?id=${member.id}">${member.id}</a></td>
			<td>${member.name}</td>
			<td>${member.email}</td>
			<td><a href="memberDel?id=${member.id}">삭제</a></td>
		</tr>	
		</c:forEach>
	</table>	
</body>
<script>
	var msg="${msg}";
	if(msg!=""){
		alert(msg);
	}
</script>
</html>
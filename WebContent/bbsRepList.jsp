<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
		<style>
			table,td,th{
				border: 1px solid black;
				border-collapse: collapse;
				padding: 5px 10px;
			}
		</style>
	</head>
	<body>
		<table>
			<tr>
				<th>신고게시물 no.</th>
				<th>신고 당한 ID</th>
				<th>신고 사유</th>
				<th>블라인드 여부</th>
			</tr>
			<c:forEach items="${list }" var="rep">
			<tr>
				<th><a href="./repDetail?boardIdx=${rep.boardIdx }&bbsRepIdx=${rep.bbsRepIdx}">${rep.boardIdx }</a></a></th>
				<th>${rep.id }</th>
				<th>${rep.reason }</th>
				<c:if test="${rep.deactivate eq 'FALSE' }">
					<th>N</th>
				</c:if>
				<c:if test="${rep.deactivate eq 'TRUE' }">
					<th>Y</th>
				</c:if>
			</tr>			
			</c:forEach>
		</table>
	</body>
	<script>
	</script>
</html>
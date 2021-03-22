<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>팝업 수정 페이지</title>
<link rel="stylesheet" type="text/css" href="basic.css">
</head>
<body>
	<!--상단페이지-->
	<jsp:include page="top.jsp" />

	<!--상단네비-->
	<jsp:include page="navi_manager.jsp" />

	<div class="pop">
		<form action="popupUpdate" method="post">
			<table>
				<input type="hidden" name="infoidx" value="${dto.infoidx}" />
				<tr>
					<th>등록관리자</th>
					<td><input type="text" name="managerid"
						value="${sessionScope.loginId}" readonly /></td>
				</tr>
				<tr>
					<th>제목</th>
					<td><input type="text" name="subject" value="${dto.subject}" /></td>
				</tr>
				<tr>
					<th>내용</th>
					<td><textarea name="content">${dto.content}</textarea></td>
				</tr>
				<tr>
					<th>노출여부</th>
					<td><input type="radio" name="popupalert" value="YES"
						<c:if test="${dto.popupalert eq 'YES'}">checked</c:if> />Y <input
						type="radio" name="popupalert" value="NO"
						<c:if test="${dto.popupalert eq 'NO'}">checked</c:if> />N</td>
				</tr>
				<tr>
					<td colspan="2">
						<button>저장</button>
						<button onclick="location.href='./popupList'">닫기</button>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
<script>
	var msg = "${msg}";
	if(msg!=""){
		alert(msg);
	}	
</script>
</html>
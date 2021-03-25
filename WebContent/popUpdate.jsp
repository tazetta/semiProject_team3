<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>팝업 수정 페이지</title>
<style>
.pop_regist {
	margin-top: 6%
}

.pop_form {
	text-align: center;
	    width: 300px;
    margin: 3% 25%;
}

#popup_form {
	border-collapse: collapse;
	width: 40%;
	height: 100%;
	margin-left: 30%;
	margin-bottom: 2%;
}

#p_content {
	width: 60%;
}

textarea {
	padding-bottom: 30%;
}

#popup_form th, td {
	border: 1px solid lightgray;
	text-align: center;
	padding: 5px;
	font-size: 14px;
}

th {
	width: 15%;
}

td {
	background-color: white;
}

#p_title {
	background-color: gray;
	color: white;
}

.addbtn {
	width: 350px;
	margin: 0 45%;
	padding-top: 1%;
}

.add {
	font-size: 13px;
	padding: 6px 15px;
	margin: 1px 0;
}

hr{
    width: 130px;
    border: 1px solid black;
}

h3{
	margin: 0;
}
</style>
</head>
<body>
	<!--상단페이지-->
	<jsp:include page="top.jsp" />

	<!--상단네비-->
	<jsp:include page="navi_manager.jsp" />

	<div class="pop_regist">
		<form action="popupUpdate" method="post">
			<div class="pop_form">
				<h3>팝업 수정하기</h3>
				<hr/>
			</div>
			<table id="popup_form">
				<input type="hidden" name="infoidx" id="p_content" value="${dto.infoidx}" />
				<tr id="p_title">
					<th>등록관리자</th>
					<td><input type="text" name="managerid" id="p_content"
						value="${sessionScope.loginId}" readonly /></td>
				</tr>
				<tr id="p_title">
					<th>제목</th>
					<td><input type="text" name="subject" id="p_content"
						value="${dto.subject}" /></td>
				</tr>
				<tr id="p_title">
					<th>내용</th>
					<td><textarea name="content" id="p_content">${dto.content}</textarea></td>
				</tr>
				<tr>
					<th style="color:white; background-color: gray">노출여부</th>
					<td>
					<input type="radio" name="popupalert" value="YES"
						<c:if test="${dto.popupalert eq 'YES'}">checked</c:if> />Y 
					<input type="radio" name="popupalert" value="NO"
						<c:if test="${dto.popupalert eq 'NO'}">checked</c:if> />N
					</td>
				</tr>
			</table>
			<div class="addbtn">
				<button class="add">저장</button>
				<input type="button" class="add"
					onclick="location.href='./popupList'" value="닫기" />
			</div>
		</form>
	</div>
</body>
<script>
	var msg = "${msg}";
	if (msg != "") {
		alert(msg);
	}
</script>
</html>
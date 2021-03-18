<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 페이지 - 메인</title>
<link rel="stylesheet" type="text/css" href="basic.css">
<Style>
#manager_main{
	width: 1000px;
	height : 450px;
	margin: auto;
}

#manager_table {
	width : 600px;
	border-collapse: collapse;
	margin-top: 30px;
}

th,td{
	border-bottom: 1px solid lightgray; 
	text-align: center;
	padding: 8px;
	font-size: 14px;
}

td{
	background-color: white;
}

#title {
	background-color: gray;
	color : white;
}


.button{
	padding: 20px;
	margin: 4px;
	text-align: right;
}
</Style>
</head>
<body>
	<!--상단페이지-->
	<jsp:include page="top.jsp" />

	<!--상단네비-->
	<jsp:include page="navi_manager.jsp" />

	<div id="manager_main">
		<table id="manager_table">
			<tr id="title">
				<th>관리자 ID</th>
				<th>등록일</th>
				<th>이름</th>
				
				<c:if test="${sessionScope.loginId eq 'sysadmin'}">
				<th></th>
				</c:if>
			</tr>
			<c:forEach items="${managerList}" var="manager">
				<tr>
					<td>${manager.managerid}</td>
					<td>${manager.reg_date}</td>
					<td>${manager.name}</td>

					<c:if test="${sessionScope.loginId eq 'sysadmin'}">
						<td><a href="managerDel?managerid=${manager.managerid}">삭제</a></td>
					</c:if>
					
				</tr>
			</c:forEach>

			<c:if test="${sessionScope.loginId eq 'sysadmin'}">
				<div class="button">
					<input type="button" value="신규 관리자 등록" onclick="show();" />
				</div>
			</c:if>
		</table>
	</div>
</body>
<script>
	function show() {
		window
				.open(
						"managerRegist.jsp",
						"신규관리자등록",
						"width=500, height=400, left=530, top=100, location=no, status=no, scrollbars=no");
	}

	var msg = "${msg}";
	if (msg != "") {
		alert(msg);
	}
</script>
</html>
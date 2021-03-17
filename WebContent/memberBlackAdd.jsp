<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>블랙리스트 추가 페이지</title>
<style>
table, th, td {
	border: 1px solid black;
	border-collapse: collapse;
	text-align: center;
	padding: 10px;
}

#content{
	min-width: 800px;
	padding : 50px 100px;	
	background-color: whitesmoke;
	margin-top: 30px;
	
}

.memberList_main{
	width: 500px;
	margin: auto;
	font-size: 14px;
}
</style>
</head>
<body>
	<!--상단페이지-->
	<jsp:include page="top.jsp" />

	<!--상단네비-->
	<jsp:include page="navi_manager.jsp" />

	<!-- 사이드 네비-->
	<jsp:include page="side_manager.jsp" />
	
	<div id="content">
		<h4>블랙리스트 추가</h4>
		<hr />
			<form action="memberBlackAdd" method="post">
				<table class="memberList_main">
<%-- 					<input type="hidden" name="blackidx" value="${dto.blackidx}"/>
					<input type="hidden" name="status" value="${dto.blackstatus}"/> --%>
					
					<tr>
						<th>관리자 ID</th>
						<td><input type="text" name="managerid" value="${sessionScope.loginId}" readonly/></td>
					</tr>
					<tr>
						<th>해당 아이디</th>
						<td><input type="text" name="id" value="${dto.id}" readonly/></td>
					</tr>
					<tr>
						<th>블랙리스트 등록사유</th>
						<td><input type="text" name="reason" /></td>
					</tr>
				</table>
			<div style="text-align: center; margin-top: 20px;">
				<button id="add">추가</button>
				<input type="button" onclick="location.href='./memberBlackDetail'" value="취소" />
			</div>
			</form>
	</div>
</body>
<script>
	var msg = "${msg}";
	if (msg != "") {
		alert(msg);
	}
/* 	
	$("#add").click(function() {
		console.log("a");
	}); */
		
	
</script>
</html>
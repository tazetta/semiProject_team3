<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>블랙리스트 추가 페이지</title>
<style>
#addFrom{
	/* width: 1300px; */
	width : 60%;
	height : 300px;
	float: right;
	margin-right: 11%;
	margin-top : 4%;
	
}

.memberList_add{
	border-collapse: collapse;
	margin-top: 75px;
	margin-left: 31%;
	width : 40%;
}

th, td {
	border: 1px solid lightgray;
	text-align: center;
	font-size: 13px;
}

.addbtn {
	width: 350px;
	margin: 0 43%;
	padding-top: 65px;
}

.add{
	font-size: 13px;
	padding: 6px 15px;
	margin: 6px 0;
}

.memberlist {
 	color : #08444d;
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
	
	<div id="addFrom">
		<h3 style="text-align: center">블랙리스트 추가</h3>
		<hr />
			<form action="memberBlackAdd" method="post">
				<table class="memberList_add">		
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
			<div class="addbtn">
				<button class="add">추가</button>
				&nbsp;&nbsp;&nbsp;&nbsp;
				 <input type="button" class="add" onclick="location.href='./memberList'" value="닫기"/>
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
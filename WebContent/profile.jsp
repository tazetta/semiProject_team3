<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>

<head>
<meta charset="utf-8">
<title>마이 페이지 - 회원정보 보기</title>
<link rel="icon" href="south-korea.png">
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<style>
body {
	min-width: 1500px;
}


/*콘텐츠*/
#content {
	height: 600px;
	/* background-color: #F2F2F2; */
	text-align: center;
	position: relative;
	top: 0px;
	left: 20px;
	float: left;
	width: 85%;
}
#content table,#content tr, #content td{
	 border: 1px solid #E6E6E6; 
	border-collapse: collapse;
	text-align: center;
	padding: 10px;
	
}

th{
width:150px;
background-color: #E6E6E6;
	color: black;
	border :1px solid white;
}
	
span {
	position: relative;
	top: 50px;
	font-weight: 600;
}

table#profile {
	background-color: white;
	position: relative;
	top: 80px;
	margin: 0 auto;
	width: 500px;
	clear: both;
	
}


#wd {
	position: absolute;
	right: 20px;
	bottom: 20px;
}
</style>

</head>

<body>
	<jsp:include page="top.jsp" />
	<jsp:include page="navi.jsp" />
	<jsp:include page="myLeft.jsp" />
	

		<div id="content">
			<span>회원정보</span>

			<table id="profile">
				<tr>
					<th>아이디</th>
					<td>${sessionScope.loginId}</td>
				</tr>
				<tr>
					<th>이름</th>
					<td>${profile.name}</td>
				</tr>
				<tr>
					<th>핸드폰 번호</th>
					<td>${profile.phone}</td>
				</tr>
				<tr>
					<th>이메일</th>
					<td>${profile.email}</td>
				</tr>
				<tr>
					<td colspan="2" style="border: none">
						<button onclick="location.href='memberUpdateForm'">회원정보 수정</button>
						<button onclick="location.href='pwUpdate.jsp'">비밀번호 변경</button>
					</td>
				</tr>
			</table>
			<button id="wd" onclick="location.href='memberWithdraw.jsp'">회원탈퇴</button>
		</div>


</body>
<script>
var msg = "${msg}"; 
if (msg != "") {  
	alert(msg); 
}



</script>

</html>
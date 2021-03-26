<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
<style>

#side {
	position: relative;
	float: left;
	padding: 10px;
}

#aside_table{
	border-collapse: collapse;
	margin-top : 50px; 
	margin-left: 86px;
	margin-right : 100px;
}

#aside_table td {
	border: 1px solid lightgray;
	text-align: center;
    padding: 15px 5px;
    width: 168px;
    font-size: 16px;
}

a {
	text-decoration: none;
	font-size: 90%;
	color: black;
}

a:hover {
	font-weight: 600;
}

</style>
</head>
<body>
	<div id="side">
		<table id="aside_table">
			<tr>
				<td class="genmem"><a href="./memberList" target="_parent">일반 회원</a></td>
			</tr>
			<tr>
				<td class="blackmem"><a href="./memberBlackList" target="_parent">블랙리스트 회원</a></td>
			</tr>
			<tr>
				<td class="delmem"><a href="./memberDelList" target="_parent">탈퇴 회원</a></td>
			</tr>
		</table>
	</div>
</body>
</html>
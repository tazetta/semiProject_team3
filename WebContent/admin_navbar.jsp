<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title></title>
<style>
.navbar {
	display: flex;
	background-color: #faf9f9;
	height: 60px;
	color: black;
	justify-content: center;
	align-items: center;
}

li {
	padding: 8px 24px;
	display: inline;
}

li:hover {
	background-color: burlywood;
}

a {
	text-decoration: none;
	color: black;
}
</style>
</head>
<body>
<div class="navbar">
			<li><a href="./adminList" target="_parent">관리자정보</a></li>
			<li><a href="./tripManageList">여행지 관리</a></li>
			<li><a href="">신고내역 관리</a></li>
			<li><a href="info_General.jsp" target="_parent">회원정보 관리</a></li>
			<li><a href="./popupList" target="_parent">팝업 관리</a></li>
	</div>
</body>
</html>
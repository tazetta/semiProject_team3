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
    height: 50px;
    border-radius: 5px;
    border: 1px solid lightgray;
    color: black;
    justify-content: center;
    align-items: center;
    
}

li {
	padding: 8px 24px;
	display: inline;
}

li:hover {
	font-weight: 600;
}

a {
	text-decoration: none;
	font-size: 90%;
	color: black;
}
</style>
</head>
<body>
	<jsp:include page="navi.jsp" />
	<div class="navbar">
			<li><a href="./managerList" target="_parent">관리자정보</a></li>
			<li><a href="./tripManageList">여행지 관리</a></li>
			<li class="repList"><a href="./reportBBS">신고내역 관리</a></li>
			<li><a href="./memberList" target="_parent">회원정보 관리</a></li>
			<li><a href="./popupList" target="_parent">팝업 관리</a></li>
	</div>
</body>
</html>
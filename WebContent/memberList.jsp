<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일반 회원정보 리스트</title>
<style>
#side {
	position: relative;
	float: left;
	margin-left: 100px;
	padding: 10px;
}

table, tr, th,td {
	border: 1px solid lightgray;
	border-collapse: collapse;
	text-align: center;
	padding: 20px;
}

a {
	text-decoration: none;
	font-size: 90%;
	color: black;
}

a:hover {
	font-weight: 600;
}

.search {
	padding: 30px;
}

#content {
	margin-left: 300px;
	margin-right: 300px;
}
</style>

</head>
<body>
	<!--상단페이지-->
	<jsp:include page="top.jsp" />

	<!--상단네비-->
	<jsp:include page="navi_manager.jsp" />

	<div id="side">
		<table>
			<tr>
				<td><a href="./memberList">일반 회원</a></td>
			</tr>
			<tr>
				<td><a href="#">블랙리스트 회원</a></td>
			</tr>
			<tr>
				<td><a href="#">탈퇴 회원</a></td>
			</tr>
		</table>
	</div>

	<div id="content">
		<div class="search">
			<form>
				<select name="filter">
					<option value="id">아이디</option>
					<option value="name">이름</option>
				</select> <input type="text" name="search" /> 
				<input type="submit" value="검색" />
			</form>
		</div>

		<div class="memberList_main">
			<table>
				<tr>
					<th>가입일</th>
					<th>아이디</th>
					<th>이름</th>
					<th>핸드폰 번호</th>
					<th>이메일</th>
					<th></th>
				</tr>
				<c:forEach items="${memberList}" var="member">
					<tr>
						<td>${member.reg_date}</td>
						<td>${member.id}</td>
						<td>${member.name}</td>
						<td>${member.phone}</td>
						<td>${member.email}</td>
						<td><a href="memberDetail?id=${member.id}">상세보기</a></td>
					</tr>
				</c:forEach>
			</table>
		</div>

	</div>
</body>
</html>
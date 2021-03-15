<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일반 회원정보 상세보기</title>
<style>
#side {
	position: relative;
	float: left;
	margin-left: 200px;
	padding: 10px;
}

table, th, td {
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
	width:50%;
	margin: 30px;
}

#content {
	margin-left: 300px;
	margin-right: 300px;
}

.memberList_main{
	margin: auto;
}


.black{
	margin-top: 50px;
	text-align: center;
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
				</select> <input type="text" name="search" /> <input type="submit"
					value="검색" />
			</form>
		</div>

		<div>
			<table class="memberList_main">
			<tr>
                <th>가입일</th>
                <th>아이디</th>
                <th>이름</th>
                <th>핸드폰</th>
                <th>이메일</th>
                <th>탈퇴여부</th>
                <th>수정일</th>
                <th>글,댓글 신고수</th>
                <th>블랙리스트</th>
            </tr>
            <tr>
                <td>${dto.reg_date}</td>
                <td>${dto.id}</td>
                <td>${dto.name}</td>
                <td>${dto.phone}</td>
                <td>${dto.email}</td>
                <td>${dto.withdraw}</td>
                <td>${dto.update_date}</td>
                <td>${dto.reportcnt}</td>
                <td>${dto.blackcnt}</td>
            </tr>
			</table>
				<div class="black">
                	<button onclick="location.href='#'">블랙리스트 추가</button>
                	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                	<button onclick="location.href='./memberList'">닫기</button>
				</div>

		</div>

	</div>
</body>
</html>
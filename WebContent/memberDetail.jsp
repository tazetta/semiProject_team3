<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일반 회원정보 상세보기</title>
<style>
.memberList_main {
	border-collapse: collapse;
	margin-top: 30px;
	margin-right: 59px;
}

th, td {
	border: 1px solid lightgray;
	text-align: center;
	font-size: 13px;
}

.black {
	width: 200px;
	margin: auto;
	padding-top: 30px;
}
</style>

</head>
<body>
	<!--상단페이지-->
	<jsp:include page="top.jsp" />

	<!--상단네비-->
	<jsp:include page="navi_manager.jsp" />

	<!-- 사이드 네비&검색필터 -->
	<jsp:include page="side_manager.jsp" />


	<div>
		<table class="memberList_main">
			<tr clospan="2"></tr>
			<tr>
				<th>가입일</th>
				<td>${dto.reg_date}</td>
			</tr>
			<tr>
				<th>아이디</th>
				<td>${dto.id}</td>
			</tr>
			<tr>
				<th>이름</th>
				<td>${dto.name}</td>
			</tr>
			<tr>
				<th>핸드폰</th>
				<td>${dto.phone}</td>
			</tr>
			<tr>
				<th>이메일</th>
				<td>${dto.email}</td>
			</tr>
			<tr>
				<th>탈퇴여부</th>
				<td>${dto.withdraw}</td>
			</tr>
			<tr>
				<th>수정일</th>
				<td>${dto.update_date}</td>
			</tr>
			<tr>
				<th>글,댓글 신고수</th>
				<td>${dto.reportcnt}</td>
			</tr>
			<tr>
				<th>블랙리스트로 등록된 횟수</th>
				<td>${dto.blackcnt}</td>
			</tr>
			<tr>
				<th>블랙리스트 상태</th>
				<td>${dto.blackstatus}</td>
			</tr>
		</table>
		<div class="black">
			<button onclick="location.href='./memberBlackAddForm?id=${dto.id}'">블랙리스트 추가</button>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<button onclick="location.href='./memberList'">닫기</button>
		</div>
	</div>
</body>
<script>
	var msg = "${msg}";
	if (msg != "") {
		alert(msg);
	}
</script>
</html>
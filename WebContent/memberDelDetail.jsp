<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>탈퇴 회원정보 상세보기</title>
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
<style>
table, th, td {
	border: 1px solid lightgray;
	border-collapse: collapse;
	text-align: center;
	padding: 20px;
}

.memberList_main{
	margin: auto;
}

.memberexist{
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

	<!-- 사이드 네비&검색필터 -->
	<jsp:include page="side_manager.jsp" />


		<div>
			<table class="memberList_main">
			<tr>
                <th>가입일</th>
                <th>탈퇴여부</th>
                <th>아이디</th>
                <th>이름</th>
                <th>핸드폰</th>
                <th>이메일</th>
                <th>수정일</th>
                <th>글,댓글 신고수</th>
                <th>블랙리스트로 등록된 횟수</th>
            </tr>
            <tr>
                <td>${dto.reg_date}</td>
                <td>${dto.withdraw}</td>
                <td>${dto.id}</td>
                <td>${dto.name}</td>
                <td>${dto.phone}</td>
                <td>${dto.email}</td>
                <td>${dto.update_date}</td>
                <td>${dto.reportcnt}</td>
                <td>${dto.blackcnt}</td>
            </tr>
			</table>
				<div class="memberexist">
                	<button onclick="location.href='./memberDraw?id=${dto.id}'">회원 삭제</button>
                	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                	
                	<button onclick="location.href='./memberRestore?id=${dto.id}'">회원 복구</button>
                	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                	
                	<button onclick="location.href='./memberDelList'">닫기</button>
				</div>
		</div>
</body>
<script>
	var msg = "${msg}";
	if(msg!=""){
		confirm(msg);
	}	
</script>
</html>
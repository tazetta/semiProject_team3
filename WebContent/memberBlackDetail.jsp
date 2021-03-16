<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>블랙리스트 회원정보 상세보기</title>
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
                <th>블랙리스트</th>
                <th>아이디</th>
                <th>이름</th>
                <th>핸드폰</th>
                <th>이메일</th>
                <th>탈퇴여부</th>
                <th>수정일</th>
                <th>글,댓글 신고수</th>
                
            </tr>
            <tr>
                <td>${dto.reg_date}</td>
                <td>${dto.blackcnt}</td>
                <td>${dto.id}</td>
                <td>${dto.name}</td>
                <td>${dto.phone}</td>
                <td>${dto.email}</td>
                <td>${dto.withdraw}</td>
                <td>${dto.update_date}</td>
                <td>${dto.reportcnt}</td>
                
            </tr>
			</table>
				<div class="memberexist">
                	<button onclick="location.href='./memberBlackDel?id=${dto.id}'">블랙리스트 삭제</button>
                	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  
                	<button onclick="location.href='./memberBlackList'">닫기</button>
				</div>
		</div>
</body>
<script>
	var msg = "${msg}";
	if(msg!=""){
		alert(msg);
	}	
</script>
</html>
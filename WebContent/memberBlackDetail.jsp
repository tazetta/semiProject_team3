<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	white-space: nowrap;
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
                <th>블랙리스트 등록일</th>
				<th>등록 관리자</th>                
                <th>아이디</th>
                <th>이름</th>
                <th>블랙리스트 등록사유</th>
                <th>블랙리스트로 등록된 횟수</th>
                <th>글,댓글 신고수</th>
                <th>블랙리스트 상태</th>
                <th>마지막 수정일</th>
            </tr>
            <tr>
                <td>${dto.reg_date}</td>
                <td>${dto.managerid}</td>
                <td>${dto.id}</td>
                <td>${dto.name}</td>
                <td>${dto.reason}</td>
                <td>${dto.blackcnt}</td>
                <td>${dto.reportcnt}</td>
                <td>${dto.blackstatus}</td>
                <td>${dto.update_date}</td>           
            </tr>
			</table>
				<div class="memberexist">
				<c:if test="${dto.blackstatus ne 'FALSE'}">
                	<button onclick="location.href='./memberBlackDel?blackidx=${dto.blackidx}'">블랙리스트 삭제</button>
                	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  				</c:if>
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
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>탈퇴 회원정보 리스트</title>
</head>
<style>
#memberList_main2 {
	width: 900px;
	height: 445px;
	margin: 128px 508px 0 508px;
}

#member {
	border-collapse: collapse;
	margin: 20px 0;
	width: 800px;
}

#member th,td{
	border: 1px solid lightgray; 
	text-align: center;
	font-size: 14px;
}
#member th {
	padding: 8px;
}
#member td{
	background-color: white;
	padding: 12px;
}

#title {
	background-color: gray;
	color: white;
}

#delpage{
     margin-top:50px;
     text-align:center;           
}

#delpage span{
	font-size : 16px;
	border:1px solid lightgray;
	padding: 2px 10px;
	margin:2px;
	background-color: whitesmoke;
}

.delmem{
	background-color: lightgray;
	font-weight: bold;
}

</style>
<body>
	<!--상단페이지-->
	<jsp:include page="top.jsp" />

	<!--상단네비-->
	<jsp:include page="navi_manager.jsp" />

	<!-- 사이드 네비&검색필터 -->
	<jsp:include page="side_manager.jsp" />


		<div id="memberList_main2">
			<table id="member">
				<tr id="title">
					<th>가입일</th>
					<th>탈퇴여부</th>
					<th>아이디</th>
					<th>이름</th>
					<th>핸드폰 번호</th>
					<th>이메일</th>
					<th></th>
				</tr>
				<c:forEach items="${memberDelList}" var="member">
					<tr>
						<td>${member.reg_date}</td>
						<td>${member.withdraw}</td>
						<td>${member.id}</td>
						<td>${member.name}</td>
						<td>${member.phone}</td>
						<td>${member.email}</td>
						<td><a href="memberDelDetail?id=${member.id}"><b>상세보기</b></a></td>
					</tr>
				</c:forEach>
			</table>
		</div>
		
		<div id="delpage">
			<span>
				<c:if test="${currPage==1}">이전</c:if>
				<c:if test="${currPage>1}">
					<a href='./memberDelList?page=${currPage-1}'>이전</a>
				</c:if>
			</span>
			<span>${currPage}</span>
			<span>
				<c:if test="${currPage == maxPage}">다음</c:if>
         		<c:if test="${currPage < maxPage}">
         			<a href="./memberDelList?page=${currPage+1}">다음</a></c:if>
			</span>		
		</div>		
</body>
<script>
var msg = "${msg}";
if (msg != "") {
	alert(msg);
}
</script>
</html>
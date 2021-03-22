<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일반 회원정보 리스트</title>

<style>
#memberList_main{
	width: 900px;
	height : 660px;
	margin: 0 508px;
}

#member {
	border-collapse: collapse;
	margin: 45px 0;
}

th,td{
	border: 1px solid lightgray; 
	text-align: center;
	padding: 8px;
	font-size: 14px;
}

td{
	background-color: white;
}

#title {
	background-color: gray;
	color : white;
}

#page{
     margin-top:50px;
     text-align:center;           
}

#page span{
	font-size : 16px;
	border:1px solid lightgray;
	padding: 2px 10px;
	margin:2px;
}

.genmem, .blackmem, .delmem{
	background-color: rgb(240, 191, 58);
}
</style>

</head>
<body>
	<!--상단페이지-->
	<jsp:include page="top.jsp" />

	<!--상단네비-->
	<jsp:include page="navi_manager.jsp" />

	<!-- 사이드 네비& -->
	<jsp:include page="side_manager.jsp" />
	<!-- 검색필터 -->
	<jsp:include page="search_member.jsp"/>


		<div id="memberList_main">
			<table id="member">
				<tr id="title">
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
		
		<div id="page">
			<span>
				<c:if test="${currPage==1}">이전</c:if>
				<c:if test="${currPage>1}">
					<a href='./memberList?page=${currPage-1}'>이전</a>
				</c:if>
			</span>
			<span>${currPage}</span>
			<span>
				<c:if test="${currPage == maxPage}">다음</c:if>
         		<c:if test="${currPage < maxPage}">
         			<a href="./memberList?page=${currPage+1}">다음</a></c:if>
			</span>		
		</div>
</body>
<script>
	var msg = "${msg}";
	if(msg!=""){
		alert(msg);
	}	
</script>
</html>
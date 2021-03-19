<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>블랙리스트 회원정보 리스트</title>
</head>
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
<body>
	<!--상단페이지-->
	<jsp:include page="top.jsp" />

	<!--상단네비-->
	<jsp:include page="navi_manager.jsp" />

	<!-- 사이드 네비 -->
	<jsp:include page="side_manager.jsp" />


		<div class="blackList_main">
			<table id="member">
				<tr id="title">
					<th>블랙리스트 등록일</th>
					<th>블랙리스트 상태</th>
					<th>아이디</th>
					<th>블랙리스트 등록사유</th>
					<th>등록 관리자</th>
					<th></th>
				</tr>
				<c:forEach items="${memberBlackList}" var="black">
					<tr>
						<td>${black.reg_date}</td>
						<td>${black.blackstatus}</td>
						<td>${black.id}</td>
						<td>${black.reason}</td>
						<td>${black.managerid}</td>
						<td><a href="./memberBlackDetail?blackidx=${black.blackidx}"><b>상세보기</b></a></td>
					</tr>
				</c:forEach>
			</table>
		</div>
		
		<div id="page">
			<span>
				<c:if test="${currPage==1}">이전</c:if>
				<c:if test="${currPage>1}">
					<a href='./memberBlackList?page=${currPage-1}'>이전</a>
				</c:if>
			</span>
			<span>${currPage}</span>
			<span>
				<c:if test="${currPage == maxPage}">다음</c:if>
         		<c:if test="${currPage < maxPage}">
         			<a href="./memberBlackList?page=${currPage+1}">다음</a></c:if>
			</span>		
		</div>
</body>
<script>
	var msg = "${msg}";
	if (msg != "") {
		alert(msg);
	}
/* 	
	$("#add").click(function() {
		console.log("a");
	}); */
		
	
</script>
</html>
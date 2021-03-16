<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- c태그 사용위해 불러옴 -->
<%
	String loginId = (String) request.getSession().getAttribute("loginId");
	String isManager =  (String) request.getSession().getAttribute("isManager");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>고객센터</title>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<!-- JQuery사용 위해 불러옴 -->
<style>
/*콘텐츠*/
#content {
	height: 600px;
	background-color: #F2F2F2;
	text-align: center;
	position: relative;
	top: 0px;
	left: 20px;
	margin: 0 auto;
	width: 90%;
}

table, th, td {
	border: 1px solid black;
	border-collapse: collapse;
	text-align: center;
}

table#qna {
	background-color: white;
	position: relative;
	top: 80px;
	margin: 0 auto;
	width: 60%;
}

#wd {
	position: absolute;
	right: 20px;
	bottom: 20px;
}

/*페이징*/
.pageArea {
	text-align: center;
	position: absolute;
	top: 50%;
	left: 50%;
}

.pageArea span {
	font-size: 16px;
	border: 1px solid lightgray;
	background-color: lightgray;
	padding: 2px 10px;
}

a {
	text-decoration: none;
}

#page {
	font-weight: 600;
	border: none;
	background-color: transparent;
}
</style>
</head>
<body>

	<jsp:include page="top.jsp" />
	<jsp:include page="navi.jsp" />


	<section id="background">
		<div id="content">
			<table id="qna">
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>작성일</th>
					<th>작성자</th>
					<th>답변</th>
				</tr>
				<c:forEach items="${list}" var="qna">
				 <c:if test="${qna.id eq loginId}"> 
					<tr>
						<td>${qna.rnum}</td>
						<td style="width: 400px"><a href="#">${qna.subject}</a></td>
						<td>${qna.reg_date}</td>
						<td>${qna.id}</td>
						<td>y/n</td>
					</tr>
				 </c:if> 
				</c:forEach>
				 <c:if test="${isManager eq true}">
				 <c:forEach items="${list}" var="qna">
					<tr>
						<td>${qna.rnum}</td>
						<td style="width: 400px"><a href="#">${qna.subject}</a></td>
						<td>${qna.reg_date}</td>
						<td>${qna.id}</td>
						<td>y/n</td>
					</tr>
				</c:forEach>
				 </c:if> 
			</table>
			<c:if test="${qna.id eq loginId}"> 
			<button id="wd" onclick="location.href='writeFormQ.jsp'">글쓰기</button>
			
			</c:if>
			
			 <c:if test="${isManager eq true}">
			 <button id="wd" onclick="location.href='writeFormA.jsp'">글쓰기</button> 
			 </c:if> 
		</div>

		<div class="pageArea">
			<span> 
				<c:if test="${currPage==1}">이전</c:if> 
				<c:if test="${currPage>1}"><a href="?page=${currPage-1}">이전</a></c:if>
			</span> 
			<span id="page">${currPage}</span> 
			<span> <c:if test="${currPage==maxPage}">다음</c:if> 
			<c:if test="${currPage<maxPage}"> <a href="?page=${currPage+1}">다음</a> </c:if>
			</span>
		</div>


	</section>

</body>
<script>
var msg = "${msg}"; 
if (msg != "") {  
	alert(msg); 
}

<%request.removeAttribute("msg");%>
	
	
	</script>
</html>
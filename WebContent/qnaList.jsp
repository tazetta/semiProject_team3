<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- c태그 사용위해 불러옴 -->
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
	clear: both;
}

#wd {
	position: absolute;
	right: 20px;
	bottom: 20px;
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
				<tr>
					<td>${qna.rnum}</td>
					<td><a href="#">${qna.subject}</a></td>
					<td>${qna.reg_date}</td>
					<td>${qna.id}</td>
					<td>y/n</td>
				</tr>
				 </c:forEach>
			</table>
			<button id="wd" onclick="location.href='qnaWriteForm.jsp'">글쓰기</button>
		</div>


	</section>

</body>
<script>
var msg = "${msg}"; 
if (msg != "") {  
	alert(msg); 
}
	
	
	</script>
</html>
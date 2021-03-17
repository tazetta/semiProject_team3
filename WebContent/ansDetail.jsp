<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- c태그 사용위해 불러옴 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>고객센터 - 상세보기</title>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<!-- JQuery사용 위해 불러옴 -->
<style>
body {
	
}
/*콘텐츠*/
#content {
	background-color: #F2F2F2;
	position: relative;
	top: 0px;
	left: 20px;
	margin: 0 auto;
	width: 90%;
	height: 600px;
}

table, th, td {
	border: 1px solid black;
	border-collapse: collapse;
	padding: 10px;
}

table#qna {
	background-color: white;
	position: relative;
	top: 80px;
	margin: 0 auto;
	width: 60%;
	clear: both;
}

span {
	font-size: 80%;
	color: gray;
}
</style>
</head>
<body>
	<jsp:include page="top.jsp" />
	<jsp:include page="navi.jsp" />

	<div id="content">
		<table id="qna">
			<tr>
				<td colspan="2"><span>제목</span><br />${dto.subject}</td>
			</tr>
			<tr>
				<td><span>작성자</span><br />${dto.id}</td>
				<td><span>작성일</span><br />${dto.reg_date}</td>
			</tr>
			<tr>
				<td colspan="2"><span>문의내용</span><br/><br/>${dto.content}</td>
			</tr>
			<tr>
				<td colspan="2"><span>답변</span><br/><br/><b>${dto.subjectA}</b><br/>${dto.contentA}</td>
			</tr>
			<tr>
				<td colspan="2">
					<c:choose>
							<c:when test="${sessionScope.isManager eq true}">
									<input type="button" onclick="location.href='./qnaList'" value="목록" />
							</c:when>
							<c:otherwise>	
								 <input type="button" onclick="location.href='./qnaListUser'" value="목록" />								
							</c:otherwise>
						</c:choose>
					</td>
			</tr>
		</table>
	</div>

</body>
<script>
	</script>
</html>
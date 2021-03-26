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
min-width:1400px;
	font-family: "NanumGothic"; 
}
/*콘텐츠*/
#content {
	/* background-color: #F2F2F2; */
	position: relative;
	border-left:1px solid lightgray;
	border-right:1px solid lightgray;
	top: 0px;
	left: 20px;
	margin: 0 auto;
	width: 70%;
	height: 700px;
}

table, th, td {
	/* border: 1px solid black; */
	border-collapse: collapse;
	padding: 10px;
}

table#qna {
	background-color: white;
	position: relative;
	top: 80px;
	margin: 0 auto;
	width: 80%;
	clear: both;
}

span {
	font-size: 80%;
	color: gray;
}
.btn{
	float:right;
}
.answer{
	background-color:  #0B610B;
	color:white;
	font-weight:600;
	padding:3px 10px;

}
</style>
</head>
<body>
	<jsp:include page="top.jsp" />
	<jsp:include page="navi.jsp" />

	<div id="content">
		<table id="qna">
			<tr>
				<td colspan="2"><span>제목</span><br /><b style="font-size:140%">${dto.subject}</b></td>
			</tr>
			<tr>
				<td><span>작성자</span><br />${dto.id}</td>
				<td ><span>작성일</span><br />${dto.reg_date}</td>
			</tr>
			<tr>
				<td colspan="2" style="padding-bottom:30px;"><hr/><span>문의내용</span><br/><br/>${dto.content}</td>
			<tr> 
				<td colspan="2" style="background-color: #F2F2F2; padding-top:15px;padding-bottom:30px; "><span class="answer">답변&nbsp;&nbsp;&nbsp;&nbsp;${dto.reg_dateA}</span><br/><br/><b>${dto.subjectA}</b><br/>${dto.contentA}</td>
			</tr>
			<tr>
				<td colspan="2" style="background-color:transParent">
					<c:choose>
							<c:when test="${sessionScope.isManager eq true}">
									<input type="button" onclick="location.href='./qnaList'" value="목록"  class="btn"/>
							</c:when>
							<c:otherwise>	
								 <input type="button" onclick="location.href='./qnaListUser'" value="목록"  class="btn"/>								
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
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- c태그 사용위해 불러옴 -->
<%
	String loginId = (String) request.getSession().getAttribute("loginId");
	String isManager = (String) request.getSession().getAttribute("isManager");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>고객센터</title>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<!-- JQuery사용 위해 불러옴 -->
 <!-- <link rel="stylesheet" href="./css/qnaListUser.css"> -->
<style>
body{
	min-width:1400px;
	font-family: "NanumGothic"; 
}
/*콘텐츠*/
#content {
	height: 800px;
	/* background-color: #F2F2F2; */
	text-align: center;
	position: relative;
	top: 0px;
	left: 20px;
	margin: 0 auto;
	width: 95%;
}
.noneList {
	position: relative;
	top: 150px;
	height: 60px;
	text-align: center;
	align-items: stretch;
	background-color: transParent;
}

table, th, td {
	/* border: 1px solid black; */
	border-collapse: collapse;
	text-align: center;
	padding:10px;
}
#qna th{
	background-color:  #0B0B3B;
	color:white;
}

#qna td{
border-bottom: 1px solid lightgray;
}
#title{
	font-weight:600;
	color:black;
}
table#qna {
	background-color: white;
	position: relative;
	top: 80px;
	margin: 0 auto;
	width: 1000px;
}

.wd {
	position: absolute;
	right: 20px;
	top: 20px;
}

/*페이징*/
.pageArea {
	text-align: center;
	position: relative;
	top: 15%;
	left: 20px;
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


		<div id="content">

			<c:choose>
				<c:when test="${list eq  '[]'}">
					<div class="noneList">
						<p>작성한 문의글이 없습니다</p>
					</div>
					<button class="wd" onclick="location.href='writeFormQ.jsp'">문의하기</button>

				</c:when>
				<c:otherwise>
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
								<td style="width: 400px" ><a
									href="qnaDetail?qnaIdx=${qna.qnaIdx}" id="title">${qna.subject}</a></td>
								<td>${qna.reg_date}</td>
								<td>${qna.id}</td>


								<td ><c:choose>
										<c:when test="${qna.ansIdx gt 0}">
											<b><a href="ansDetail?qnaIdx=${qna.qnaIdx }" style="color:green">답변완료</a></b>
										</c:when>
										<c:otherwise>

										</c:otherwise>

									</c:choose></td>
							</tr>

						</c:forEach>

					</table>
					<button class="wd" onclick="location.href='writeFormQ.jsp'">문의하기</button>

					<div class="pageArea">
						<span> <c:if test="${currPage==1}">이전</c:if> 
						<c:if test="${currPage>1}"> <a href="qnaListUser?page=${currPage-1}">이전</a> </c:if>
						</span> <span id="page">${currPage}</span> <span> 
						<c:if test="${currPage==maxPage}">다음</c:if> 
						<c:if test="${currPage<maxPage}"> <a href="qnaListUser?page=${currPage+1}">다음</a> </c:if>
						</span>
					</div>
				</c:otherwise>
			</c:choose>
		</div>



</body>
<script>
	var msg = "${msg}";
	if (msg != "") {
		alert(msg);
	}
<%request.removeAttribute("msg");%>
	
</script>
</html>
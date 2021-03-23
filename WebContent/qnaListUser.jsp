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
 <link rel="stylesheet" href="./css/qnaListUser.css">
<style>

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
								<th style="width: 400px"><a
									href="qnaDetail?qnaIdx=${qna.qnaIdx}">${qna.subject}</a></th>
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
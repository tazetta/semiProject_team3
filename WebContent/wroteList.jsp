<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String loginId = (String) request.getSession().getAttribute("loginId");
%>
<!-- c태그 사용위해 불러옴 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내가 쓴 글 보기</title>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<!-- JQuery사용 위해 불러옴 -->
<style>
body {
	min-width: 1400px;
}


table, tr, td {
	border: 1px solid lightgray;
	border-collapse: collapse;
	text-align: center;
	padding: 20px;
}


/*콘텐츠*/
#content {
	
	text-align: center;
	position: relative;
	top: 0px;
	left: 20px;
	float: left;
	margin: 10px;
	width: 85%;
	height: 1000px;
}

span {
	position: relative;
	top: 30px;
	font-weight: 600;
}

table#wroteList {
	background-color: white;
	text-align: center;
	position: relative;
	top: 80px;
	margin: 0 auto;
	width: 900px;
}

.noneList {
	position: relative;
	top: 150px;
	height: 60px;
	text-align: center;
	align-items: stretch;
	background-color: transParent;
}
/*페이징*/
.pageArea {
	text-align: center;
	position: relative;
	top: 70px;
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
<jsp:include page="myLeft.jsp" />

	<section id=background>
		<div id="content">
			<span>커뮤니티 - 내가 쓴 글</span>
			<c:if test="${list eq  '[]'}">
				<div class="noneList">
					<p>작성한 글이 없습니다</p>
				</div>
			</c:if>
			<table id="wroteList">

				<c:forEach items="${list}" var="bbs">

					<tr>
						<td>${bbs.rnum }</td>
						<th style="width: 500px"><a
							href="boardDetail?boardIdx=${bbs.boardIdx}&page=1">${bbs.subject }</a></th>
						<td>${bbs.reg_date }</td>
						<%-- <td>
							<button
								onclick="location.href='./boardUpdateForm?boardIdx=${bbs.boardIdx}&id=${loginId}'">수정</button>
							<br />
							<button onclick="location.href='./wroteDel?boardIdx=${bbs.boardIdx}'">삭제</button>
						</td> --%>
					</tr>
				</c:forEach>
			</table>
			<c:if test="${list ne  '[]'}">
				<div class="pageArea">
					<span> <c:if test="${currPage==1}">이전</c:if> <c:if
							test="${currPage>1}">
							<a href="?page=${currPage-1}">이전</a>
						</c:if>
					</span> <span id="page">${currPage}</span> <span> <c:if
							test="${currPage==maxPage}">다음</c:if> <c:if
							test="${currPage<maxPage}">
							<a href="?page=${currPage+1}">다음</a>
						</c:if>
					</span>
				</div>
			</c:if>
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
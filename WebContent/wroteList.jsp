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


table, tr, td,th {
	border: 1px solid lightgray;
	border-collapse: collapse;
	text-align: center;
	padding: 10px;
}

.firstTr th{
	background-color: gray;
	color:white;
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
	width: 800px;
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
/* 	left: 0px; */
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


		<div id="content">
			<span>커뮤니티 - 내가 쓴 글</span>
			<c:choose>
				<c:when test="${list eq  '[]'}">
				<div class="noneList">
					<p>작성한 글이 없습니다</p>
				</div>
				</c:when>
				<c:otherwise>
					<table id="wroteList">
			 <tr class="firstTr">
			 	<th>글번호</th>
			 	<th>제목</th>
			 	<th>작성일</th>
			 	<th>삭제</th>
			 </tr>

				<c:forEach items="${list}" var="bbs" varStatus="status">

					<tr>
						<td>${bbs.rnum }</td>
						<th style="width: 450px"><a
							href="boardDetail?boardIdx=${bbs.boardIdx}&page=1">${bbs.subject }</a></th>
						<td>${bbs.reg_date }</td>
						<td> 
							<input type="button"  value="삭제" onclick="deletingList(${bbs.boardIdx})" class="del" />  
							
						
						</td>
						
					</tr>
				</c:forEach>
			</table>
			
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

/* var value = new Array();
<c:forEach items="${list}" var="bbs">
	value.push('${bbs.boardIdx}');
</c:forEach>
console.log(value[0]); */


function deletingList(boardIdx){
	console.log(boardIdx);
	 if(confirm("정말로 삭제하시겠습니까?")){
		 
		 	location.href="wroteDel?boardIdx="+boardIdx;
		}else{
			location.href="wroteList";
		}
};  
	
</script>
</html>
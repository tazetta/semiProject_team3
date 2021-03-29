<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%String loginId = (String)request.getSession().getAttribute("loginId"); %>
<!DOCTYPE html>
<html>
<head>
<!-- <meta name="viewport" content="width=device",initial-scale="1"> -->
<meta charset="utf-8">
<title>커뮤니티</title>
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
<link rel="icon" href="south-korea.png">
<style>
body{
font-family: "NanumGothic"; 

}
table, th, td {
	/* border:1px solid gray; */
	border-collapse: collapse;
	text-align: center;
	font-size:100%;
	color:black;
}

th, td {
	/* font-size: 18px; */
	padding: 6px 10px;
}

table {
	margin-top: 10px;
	margin-left: 80px;
	width: 1000px
}

#comm_select {
	width: 50px;
	height: 40px;
	font-size: 13px;
	font-weight: 600;

}

#boardkeyword {
	width: 400px;
	height: 34px;
	font-size: 14px;
	font-weight: 600;
}

#boardSearch {
	width: 50px;
	height: 40px;
	font-size: 14px;
	font-weight: 600;
}

#write {
	width: 70px;
	height: 40px;
	position: relative;
	left: 30%;
font-size: 13px;
	font-weight: 600;
}

.pageArea {
	margin-top: 10px;
	text-align: center;
}

.pageArea span {
	font-size: 16px;
	/* border: 1px solid lightgray; */
	background-color: lightgray;
	padding: 2px 10px;
	margin: 2px
}
#page{
background-color: transparent;

}
#field {
	margin-left: 15%;
	text-align: center;
	width: 1200px;
	margin-top: 20px;
}

.mouse_over:hover {
	font-weight: 600;
}

#commuTable th {
	background-color: #0B0B3B;
	color: white;
}

#commuTable td {
	border-bottom: 1px solid lightgray;
}

</style>
</head>
<body>
	<jsp:include page="top.jsp" />
	<jsp:include page="navi.jsp" />

	<div id="field">
		<select id="comm_select" name="comm_select">
			<option value="subject">제목</option>
			<option value="content">내용</option>
			<option value="id">작성자</option>
		</select> <input type="text" id="boardkeyword" />
		<button id="boardSearch">검색</button>
		<table id="commuTable">
			<tr>
				<th style="width: 70px">글 번호</th>
				<th>제목</th>
				<th style="width: 120px">작성자</th>
				<th style="width: 60px">조회수</th>
				<th style="width: 100px">작성날짜</th>
			</tr>
			<c:if test="${managerbbsList ne null}">
				<c:forEach items="${managerbbsList}" var="managerbbs">
					<tr style="background-color:  #E6E6E6" >
						<td ><b>공지</b></td>
						<td ><a class="mouse_over"
							href="boardDetail?boardIdx=${managerbbs.boardIdx}&page=${currPage}">${managerbbs.subject}</a></td>
						<td >${managerbbs.id}</td>
						<td >${managerbbs.bHit}</td>
						<td >${managerbbs.reg_date}</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:forEach items="${list}" var="bbs">
				<tr>
					<td>${bbs.boardIdx}</td>
					<td><a class="mouse_over"
						href="boardDetail?boardIdx=${bbs.boardIdx}&page=${currPage}">${bbs.subject}</a></td>
					<td>${bbs.id}</td>
					<td>${bbs.bHit}</td>
					<td>${bbs.reg_date}</td>
				</tr>
			</c:forEach>
		</table>
		<div class="pageArea">
			<span> <c:if test="${currPage==1}">이전</c:if> <c:if
					test="${currPage>1}">
					<a class="mouse_over" href='./boardList?page=${currPage-1}'>이전</a>
				</c:if>
			</span> <span id="page" style="font-weight: 600">${currPage}</span> <span>
				<c:if test="${currPage == maxPage}">다음</c:if> <c:if
					test="${currPage < maxPage}">
					<a class="mouse_over" href="./boardList?page=${currPage+1}">다음</a>
				</c:if>
			</span>
			<c:if test="${loginId ne null}">
				<button id="write" onclick="location.href='./boardwriteForm.jsp'">글쓰기</button>
			</c:if>
		</div>
	</div>
</body>
<script>

	$('#boardSearch').click(function(){
		console.log("검색요청");
		var searchType = $('#comm_select').val();
		var keyword = $('#boardkeyword').val();
		if(keyword==""){
			alert("검색어를 입력해주세요");
			$('#boardkeyword').focus();
		}else{
			location.href="./boardSearch?searchType="+searchType+"&boardkeyword="+keyword;
			
		}
	});
	var msg="${msg}";
	if(msg!=""){
		alert(msg);
	}
</script>
</html>
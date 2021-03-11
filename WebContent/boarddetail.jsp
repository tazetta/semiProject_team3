<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	table{
		margin-left:18%;
		width:800px;	
	}
	table, th, td {
		border: 1px solid black;
		border-collapse: collapse;
		text-align: center;
		padding : 5px 10px;
	}
	#total{
		margin-left:20%;
		border:1px solid black;
		width :1200px;
	}
	button{
		width:100px;
		height: 30px;
	}
	#btn1{
		position: relative;
		top:35px;
		left: 18%;
	}
	#btn2{
		position: relative;
		left:67%;
		margin:5px;
	}
	#comment{
		position: relative;
		left:18%;
		font-size:18px;
		width:700px;
		height: 30px;
		margin-top: 5px;
	}
	#comm_regist{
		position: relative;
		left:18%;
	}
	p{
		text-align: center;
	}
</style>
</head>
<body>
	<iframe src="top.jsp" width="100%" height="90px" frameborder="0" scrolling="no"></iframe>
    <iframe id="navi" src="navi.jsp" width="100%" height="90px" frameborder="0" scrolling="no"></iframe>
    	
		<div id="total">
			<%-- <c:if test="${dto.id==loginId}"> --%>
			<div id="btn1">
				<button onclick="location.href='./boardupdate'">수정</button>
				<button onclick="location.href='./boarddel'">삭제</button>
			</div>
			<%-- </c:if> --%>
			<%-- <c:if test="${dto.id!=loginId}"> --%>
			<div id= "btn2">
				<button onclick="location.href='./report'">신고</button>
				<%-- </c:if> --%>
				<button onclick="location.href='./boardList'">목록</button>
			</div>
		<table>
			<tr>
				<th>작성자</th>
				<td>${dto.id}</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>${dto.subject}</td>
			</tr>
			<tr>
				<th>내용</th>
				<td>${dto.content}</td>
			</tr>
			<%-- <c:if test="${dto.newFileName ne null}"> --%>
			<tr>
				<th>첨부사진</th>
				<td>
					<a href="photo/${dto.newFileName}" target="_blank">${dto.oriFileName}</a>
					<br/>
					<img src="photo/${dto.newFileName}" alt="${dto.oriFileName}" width="500px"/>
				</td>
			</tr>			
			<%-- </c:if> --%>
		</table>
		<input id="comment" type="text" placeholder="댓글을 입력해주세요"/>
		<button id="comm_regist">등록</button>
		<%-- <c:if test="댓글이 있는경우">
		<table>
			<tr>
				<td>userId</td>
				<td>추천추천</td>
			</tr>
		</table>
		</c:if>
		<c:if test="댓글이 없는경우"> --%>
			<p>현재 댓글이 없습니다.</p>
		<%-- </c:if> --%>
		</div>
</body>
</html>
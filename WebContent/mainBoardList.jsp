<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인의 커뮤니티 글</title>
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
<style>
	#boardTableDiv{
		position: absolute;
		top: 100%;
		border : 0px;
		text-align: center;
		margin-left:70%; 

	}
	#boardTable,.mainBoard{
		border : 1px solid black;
		border-collapse: collapse;
		padding: 5px 10px;
		
	}
	.mouse_over:hover{
		font-weight: 600;
	}
</style>
</head>
<body>
	<div id="boardTableDiv">
	<h3 style="background-color: blanchedalmond">커뮤니티 인기 게시물</h3>
	<table id= "boardTable">
		<tr class="mainBoard">
			<th class="mainBoard" style="width: 400px;">제목</th>
			<th class="mainBoard" style="width: 200px;">작성자</th>
		</tr>
		<c:forEach items="${list}" var="board">
		<tr class="mainBoard">
			<td class="mainBoard"><a class="mouse_over" href="./boardDetail?boardIdx=${board.boardIdx}&page=1">${board.subject}</a></td>
			<td class="mainBoard">${board.id}</td>
		</tr>
		</c:forEach>
	</table>
	</div>
</body>
<script>
</script>
</html>
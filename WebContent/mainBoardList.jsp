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
	#boardTable{
		position: absolute;
		top: 70%;
	}
	#boardTable,.mainBaord{
		border : 1px solid black;
		border-collapse: collapse;
		padding: 5px 10px;
	}
</style>
</head>
<body>
	<table id="boardTable">
		<tr class="mainBaord">
			<th class="mainBaord" style="width: 400px;">제목</th>
			<th class="mainBaord" style="width: 200px;">작성자</th>
		</tr>
		<c:forEach items="${list}" var="board">
			<tr class="mainBaord">
				<td class="mainBoard"><a class="mouse_over" href="boardDetail?boardIdx=${board.boardIdx}&page=1">${board.subject}</a></td>
				<td class="mainBoard">${board.id}</td>
			</tr>
		</c:forEach>
	</table>
</body>
<script>

</script>
</html>
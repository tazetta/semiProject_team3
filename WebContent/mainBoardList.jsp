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
#boardTableDiv {
	position: absolute;
	/* top: 100%; */
	top: 200px;
	margin-left: 950px;
	background-color: white;
	border-top: 2px solid lightgray;
	text-align: center;
	width: 35vw;
	/* margin-left:70%; */
}

#boardTable, .mainBoard {
	/* border: 1px solid black; */
	border-collapse: collapse;
	padding: 10px 10px;
}

.mouse_over:hover {
	font-weight: 600;
}

#boardTableDiv span {
	position: absolute;
	top: -40px;
	left: 220px;
	font-size: 110%;
	font-weight: 600;
	color: black;
	padding: 5px 10px;
}

#boardTable th{
	 border-bottom: 1px solid lightgray;
}
#boardTable td{
 border-bottom: 1px solid #EEECEC;
 font-size:90%;
}

</style>
</head>
<body>
	<div id="boardTableDiv">
		<span>커뮤니티 인기 게시물 <img src="./css/emoji2.png" alt="emoji"
			width="20px" height="20px"></span>
		<!--   #E0E6F8 -->
		<table id="boardTable">
			<tr class="mainBoard">
				<td class="mainBoard" style="width: 400px;">제목</td>
				<td class="mainBoard" style="width: 200px;">작성자</td>
			</tr>
			<c:forEach items="${list}" var="board">
				<tr class="mainBoard">
					<td class="mainBoard"><a class="mouse_over"
						href="./boardDetail?boardIdx=${board.boardIdx}&page=1">${board.subject}</a></td>
					<td class="mainBoard">${board.id}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
<script>
	
</script>
</html>
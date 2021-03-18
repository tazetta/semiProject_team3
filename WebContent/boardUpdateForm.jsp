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
		width:800px;
		position: relative;
		top:20px;
		left: 20%;
	
	}
	table, th, td {
		border: 1px solid black;
		border-collapse: collapse;
		text-align: center;
		padding : 5px 10px;
		font-size: 18px;
	}
	input[type='text']{
		width:100%
	}
	textarea{
		width:100%;
		height:300px;
		resize:none;
	}
	#submit{
		position:absolute;
		top:95%;
		left:71%;
	}
	textarea,input[type='text'],input[type='file'],input[type='button'],button{
		font-size: 16px;
	}
	
</style>
</head>
<body>
	<jsp:include page="top.jsp" />
	<jsp:include page="navi.jsp" />
    
	<form action="boardUpdate?boardIdx=${dto.boardIdx}&page=${page}" method="post" enctype="multipart/form-data">
		<table>
			<tr>
				<th>작성자</th>
				<td><input type="text" name="userId" value="${dto.id}" readonly/></td>
			</tr>
			<tr>
				<th>제목</th>
				<td><input type="text" name="subject" value="${dto.subject}"/></td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea name="content">${dto.content}</textarea></td>
			</tr>
			<tr>
				<td colspan="2">
				<c:if test="${dto.newFileName ne null}">
				<img src="photo/${dto.newFileName}" alt="${dto.oriFileName}" width="500px"/>
				</c:if>
					<input type="file" name="photo"/>
				</td>
			</tr>			
			<tr>
				<td colspan="2"><button>수정</button>
				<input type="button" id="cancel" onclick="location.href='./boardList'" value="취소"/>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
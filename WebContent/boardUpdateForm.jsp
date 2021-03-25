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
				<td><input type="text" id="subject" name="subject" value="${dto.subject}" maxlength="100"/></td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea name="content" id="content" maxlength="1000">${dto.content}</textarea></td>
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
				<td colspan="2"><input type="button" id="regist" value="수정"/>
				<input type="button" id="cancel" onclick="location.href='./boardList'" value="취소"/>
				</td>
			</tr>
		</table>
	</form>
</body>
<script>
var $subject = $("#subject");
var $content = $("#content");
$("#regist").click(function(){
	if($subject.val()==""||$content.val()==""){
		console.log($subject.val()+"/"+$content.val())
		alert("제목과 내용을 모두 작성해주세요");
	}else{
		$("form").submit();
	}
});
</script>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String loginId = (String)request.getSession().getAttribute("loginId"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>


<style>
	table{
		width:800px;
		position:absolute;
		left:20%		
	}
	table, th, td {
		border: 1px solid black;
		border-collapse: collapse;
		text-align: center;
		padding : 5px 10px;
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
	
</style>
</head>
<body>
	<jsp:include page="top.jsp" />
	<jsp:include page="navi.jsp" />
    
	<form action="boardWrite" enctype="multipart/form-data" method="post">
		<table>
			<tr>
				<th>작성자</th>
				<td><input type="text" name="userId" id="userId"value="${loginId}" readonly/></td>
			</tr>
			<tr>
				<th>제목</th>
				<td><input type="text" id="subject" name="subject" placeholder="제목을 입력하세요"/></td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea id="content" name="content" placeholder="내용을 입력하세요"></textarea></td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="file" name="photo" id="photo"/>
				</td>
			</tr>
			<tr>
				<td colspan="2"><button id="regist">등록</button>
				<input type="button" id="cancel" onclick="location.href='./boardList'" value="취소"/>
				</td>
			</tr>
		</table>
	</form>
</body>
<script>
</script>
</html>
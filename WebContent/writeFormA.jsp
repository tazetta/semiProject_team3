<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- c태그 사용위해 불러옴 -->
<%String loginId = (String)request.getSession().getAttribute("loginId"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>고객센터 - 글쓰기</title>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<!-- JQuery사용 위해 불러옴 -->
<style>
body {
	
}
/*콘텐츠*/
#content {
	background-color: #F2F2F2;
	text-align: center;
	position: relative;
	top: 0px;
	left: 20px;
	margin: 0 auto;
	width: 96%;
	height:900px;
}

table, th, td {
	border: 1px solid black;
	border-collapse: collapse;
	text-align: center;
	padding:10px;
}

table#qna {
	background-color: white;
	position: relative;
	top: 80px;
	margin: 0 auto;
	width: 60%;
	clear: both;
}

input[type='text'] {
	width: 90%;
	height:30px;
}

textarea {
	background-color: white;
	width: 100%;
	height: 400px;
	resize: none;
}
</style>
</head>
<body>
	<jsp:include page="top.jsp" />
	<jsp:include page="navi.jsp" />
	<form action="writeAns" method="post">
	<input type="hidden" name="qnaIdx" value="${dto.qnaIdx}"/>
			<div id="content">
				<table id="qna">
					<tr>
						<th>작성자</th>
						<td>${loginId}</td>
						
					</tr>
					<tr>
						<th>제목</th>
						<td><input type="text" id="subject" name="subject" placeholder="제목을 입력하세요" /></td>
					</tr>
					<tr>
						<th>문의내용</th>
						<td>${dto.content}</td>
					</tr>
					<tr>
						<th>내용</th>
						<td><textarea id="text" name="content" placeholder="내용을 입력하세요"></textarea></td>
					</tr>

					<tr>
						<td colspan="2">
						<input type="button" value="저장" id="save" /> 
						
						
						
						<c:choose>
							<c:when test="${qna.ansIdx gt 0}">
									<input type="button" id="cancel" onclick="location.href='./qnaList'" value="취소" />
							</c:when>
							<c:otherwise>
								 <input type="button" id="cancel" onclick="location.href='./unAnsList'" value="취소" />
							</c:otherwise>

						</c:choose>
						</td>
					</tr>
				</table>
			</div>

	</form>
</body>
<script>
	var $subject = $("#subject");
	var $content = $("#text");
	$("#save").click(function(){
		if($subject.val()==""||$content.val()==""){
			console.log($subject.val()+"/"+$content.val())
			alert("제목과 내용을 모두 작성해주세요");
		}else{
			$("form").submit();
		}
	});
</script>
</html>
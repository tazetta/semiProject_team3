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
	min-width:1400px;
	font-family: "NanumGothic"; 
}
/*콘텐츠*/
#content {
	/* background-color: #F2F2F2; */
	text-align: center; 
	border-left: 1px solid lightgray;
	border-right: 1px solid lightgray;
	position: relative;
	top: 0px;
	left: 20px;
	margin: 0 auto;
	width: 70%;
	height:800px;
}

#content table, #content th, #content td {
	/* border: 1px solid gray; */
	border-collapse: collapse;
	background-color: #F2F2F2;
	/* text-align: center; */
	padding:10px;
}

table#qna {
	background-color: white;
	position: relative;
	top: 80px;
	margin: 0 auto;
	width: 80%;
	clear: both;
}
/* 
input[type='text'] {
	width: 90%;
	height: 30px;
} */
#subject{
width: 90%;
	height: 30px;
}
textarea {
	background-color: white;
	width: 95%;
	height: 400px;
	resize: none;
	margin:5px;
}
#question{
	position: relative;
	top: 50px;
	font-weight: 600;

}

#qna span{
font-size: 85%;
	color: gray;
}
</style>
</head>
<body>
	<jsp:include page="top.jsp" />
	<jsp:include page="navi.jsp" />

	<form action="writeQue" method="post">
			<div id="content">
			<span id="question">문의하기</span>
				<table id="qna">
					
					<tr>
						<td><span>문의 제목</span><br/><input type="text" id="subject" name="subject" placeholder="제목을 입력하세요" /></td>
					</tr>
					<tr>
						<td><span>문의 내용</span><br/><textarea id="text" name="content" placeholder="내용을 입력하세요"></textarea></td>
					</tr>

					<tr>
						<td colspan="2">
						<input type="button" value="저장" id="save" /> 
						<input type="button" id="cancel" onclick="location.href='./qnaListUser'" value="취소" /></td>
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
		}else if($subject.val().length>100){
			alert("제목은 100자 이하로 입력해주세요");
		}else if($content.val().length>1000){
			alert("내용은 1000자 이하로 입력해주세요");	
		}
		else{
			$("form").submit();
		}
	});
</script>
</html>
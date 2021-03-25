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
	font-family: "NanumGothic";
}
/*콘텐츠*/
#content {
	/* background-color: #F2F2F2; */
	border-left: 1px solid lightgray;
	border-right: 1px solid lightgray;
	/* text-align: center; */
	position: relative;
	top: 0px;
	left: 20px;
	margin: 0 auto;
	width: 70%;
	height: 900px;
}

table, th, td {
	/* border: 1px solid lightgray; */
	border-collapse: collapse;
	/* text-align: center; */
	padding: 10px;
}

table.qna {
	background-color: white;
	position: relative;
	top: 80px;
	margin: 0 auto;
	width: 80%;
	clear: both;
}

.qna th {
	width: 200px;
}

input[type='text'] {
	width: 90%;
	height: 30px;
}

textarea {
	background-color: white;
	width: 90%;
	height: 400px;
	resize: none;

}
span {
	font-size: 80%;
	color: gray;
}

</style>
</head>
<body>
	<jsp:include page="top.jsp" />
	<jsp:include page="navi.jsp" />
	<form action="writeAns" method="post">
		<input type="hidden" name="qnaIdx" value="${dto.qnaIdx}" />
		<div id="content">
			<table class ="qna">
				<tr>
					<td colspan="2"><span>문의 제목</span><br />
					<b style="font-size: 140%">${dto.subject}</b></td>
				</tr>
				<tr>
					<td><span>문의 작성자</span><br />${dto.id}</td>
					<td><span>문의 작성일</span><br />${dto.reg_date}</td>

				</tr>
				<tr>
					<td colspan="2" style="padding-top: 10px; padding-bottom: 60px;"><hr /><span>문의 내용</span><br/>${dto.content}</td>
				</tr>
			</table>
			<table class ="qna" style="background-color: #E6E6E6;text-align:center;">
				<tr>
					<td><span>제목</span><br/><input type="text" id="subject" name="subject"
						placeholder="제목을 입력하세요" /></td>
				</tr>
				<tr>
					<td><span>내용</span><br/><textarea id="text" name="content" placeholder="내용을 입력하세요"></textarea></td>
				</tr>

				<tr>
					<td colspan="2"><input type="button" value="저장" id="save" />



						<c:choose>
							<c:when test="${qna.ansIdx gt 0}">
								<input type="button" id="cancel"
									onclick="location.href='./qnaList'" value="취소" />
							</c:when>
							<c:otherwise>
								<input type="button" id="cancel"
									onclick="location.href='./unAnsList'" value="취소" />
							</c:otherwise>

						</c:choose></td>
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
		}else{
			$("form").submit();
		}
	});
</script>
</html>
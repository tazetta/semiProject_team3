<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>팝업 등록 페이지</title>
</head>
<style>
.pop_regist{
	margin-top: 6%
}

.pop_form{
	text-align: center;
}
#popup_form {
	border-collapse: collapse;
    width: 40%;
    height: 100%;
    margin-left: 30%;
    margin-bottom: 2%;
}

#p_content{
    width: 60%;
}

textarea{
    padding-top : 30%;
}

th, td {
	border: 1px solid lightgray;
	text-align: center;
	padding: 5px;
	font-size: 14px;
}

th {
    width: 15%;
}

td {
	background-color: white;
}

#p_title {
	background-color: gray;
	color: white;
}

.addbtn {
	width: 350px;
	margin: 0 45%;
	padding-top: 1%;
}

.add{
	font-size: 13px;
	padding: 6px 15px;
	margin: 1px 0;
}
</style>
<body>
	<!--상단페이지-->
	<jsp:include page="top.jsp" />

	<!--상단네비-->
	<jsp:include page="navi_manager.jsp" />
	
	<div class="pop_regist">
	
		<form action="popupWrite" method="post">
		<div class="pop_form"><h3>신규 팝업 등록하기</h3></div>
			<table id="popup_form">
				<tr id="p_title">
					<th>등록관리자</th>
					<td><input type="text" id="p_content" name="managerid"
						value="${sessionScope.loginId}" readonly /></td>
				</tr>
				<tr id="p_title">
					<th>제목</th>
					<td><input type="text" id="p_content" name="subject" /></td>
				</tr>
				<tr id="p_title">
					<th>내용</th>
					<td><textarea id="p_content" name="content"></textarea></td>
				</tr>
			</table>
				<div class="addbtn">
						<button class="add">팝업 등록</button> 
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="add" onclick="location.href='./popupList'" value="취소" />
				</div>
		</form>
	</div>
</body>
<script>
	var msg = "${msg}";
	if (msg != "") {
		alert(msg);
	}

	var $subject = $("#p_subject");
	var $content = $("#p_content");
	$("#test").click(function() {

		if ($subject.val() == "" || $content.val() == "") {
			alert("제목이나 내용을 작성해주세요");
		}
	});
</script>
</html>
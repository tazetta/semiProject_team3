<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>팝업 상세보기</title>
<style>
.pop_regist {
	margin-top: 6%
}

.pop_form {
	text-align: center;
    width: 300px;
    margin: 3% 25%;
}

#popup_form {
	border-collapse: collapse;
	width: 40%;
	height: 100%;
	margin-left: 30%;
	margin-bottom: 2%;
}

#p_content {
	width: 60%;
}

#popup_form th, td {
	border: 1px solid lightgray;
	text-align: center;
	padding: 15px;
	font-size: 14px;
}

th {
	width: 15%;
	color : white;
}

td {

	background-color: white;
}

#p_title {
	background-color: gray;
	
}

.addbtn {
	width: 350px;
	margin: 0 45%;
	padding-top: 1%;
}

.add {
	font-size: 13px;
	padding: 6px 15px;
	margin: 1px 0;
}

hr{
    width: 130px;
    border: 1px solid black;
}

h3{
	margin: 0;
}
</style>
</head>
<body>
	<!--상단페이지-->
	<jsp:include page="top.jsp" />
	
	<!--상단네비-->
	<jsp:include page="navi_manager.jsp" />
	<div class="pop_regist">
	<div class="pop_form"><h3>팝업 상세보기</h3><hr/></div>
		<table id="popup_form">
			<tr id="p_title">
				<th>등록관리자</th>
				<td>${dto.managerid}</td>
			</tr>
			<tr id="p_title">
				<th>제목</th>
				<td>${dto.subject}</td>
			</tr>
			<tr id="p_title">
				<th>내용</th>
				<td>${dto.content}</td>
			</tr>
			<tr id="p_title">
				<th>노출여부</th>
				<td>${dto.popupalert}</td>
			</tr>
		</table>
			<div class="addbtn">
					<button class="add" onclick="location.href='./popupUpdateForm?infoidx=${dto.infoidx}'">수정</button>
					<input type="button" class="add" onclick="location.href='./popupList'" value="닫기"/>
	</div>
	</div>
<script>
	var msg = "${msg}";
	if(msg!=""){
		alert(msg);
	}	
</script>
</body>
</html>
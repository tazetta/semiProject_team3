<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>팝업 상세보기</title>
<style>
#pop_main {
	width: 900px;
	margin: 7% 34%;
}

#popup {
	border-collapse: collapse;
	margin: 45px 0;
}

th, td {
	border: 1px solid lightgray;
	text-align: center;
	padding: 8px;
	font-size: 14px;
}

td {
	background-color: white;
}

#title {
	background-color: gray;
	color: white;
}
</style>
</head>
<body>
	<!--상단페이지-->
	<jsp:include page="top.jsp" />
	
	<!--상단네비-->
	<jsp:include page="navi_manager.jsp" />
	
		<table>
			<tr>
				<th>등록관리자</th>
				<td>${dto.managerid}</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>${dto.subject}</td>
			</tr>
			<tr>
				<th>내용</th>
				<td>${dto.content}</td>
			</tr>
			<tr>
				<th>노출여부</th>
				<td>${dto.popupalert}</td>
			</tr>
			<tr>
				<td colspan="2">
					<button onclick="location.href='./popupUpdateForm?infoidx=${dto.infoidx}'">수정</button>
					<input type="button" onclick="location.href='./popupList'" value="닫기"/>
				</td>
			</tr>
		</table>
<script>
	var msg = "${msg}";
	if(msg!=""){
		alert(msg);
	}	
</script>
</body>
</html>
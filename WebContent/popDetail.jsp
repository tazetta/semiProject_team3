<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>팝업 상세보기</title>
</head>
<link rel="stylesheet" type="text/css" href="basic.css">
<body>
<body>
	<!--상단페이지-->
	<iframe src="top.jsp" width="100%" height="90px" frameborder="0" scrolling="no"></iframe>
	
	<!--상단네비-->
	<iframe src="navi_manager.jsp" width="100%" height="90px" frameborder="0" scrolling="no"></iframe>
	
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
					<button onclick="location.href='./popupList'">닫기</button>
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
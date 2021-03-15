<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>신규관리자 등록 성공</title>
</head>
<body>
	
</body>
<script>
closeWin();
	function closeWin() {
		alert("관리자 등록에 성공 하였습니다.");
		//top.window.close();
		/* location.href="./managerList","_parent"; */
		opener.location.reload();
		self.close(); //부모창 새로고침 하기 
	}
</script>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
	</head>
	<body>
	<%
		request.setCharacterEncoding("utf-8");
		String userId = request.getParameter("userId");
	%>
		<form action="findId" method="POST">
		<fieldset id="fieldset1">
            <div id ="userName1"></div>
            <h6>회원가입 시 사용한 아이디는 <b><%=userId%></b> 입니다.</h6>
            <div style="text-align: right;">
                <button type="button" onclick="location.href='./login.jsp'">확인</button>
            </div>
        </fieldset>
		</form>
	
	</body>
	<script>
	</script>
</html>
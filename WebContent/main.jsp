<%@page import="com.mvc.dto.BoardDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String loginId = (String) request.getSession().getAttribute("loginId");
	//ArrayList<BoardDTO> list = (ArrayList<BoardDTO>) request.getSession().getAttribute("list");
%>
<!-- c태그 사용위해 불러옴 -->
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<title>index</title>
<!-- <link rel="icon" href="favicon.png"> -->
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>

<style>
body {
	min-width: 1400px;
 font-family: "NanumGothic"; 
}

.popup {
	background-color: gray;
	width: 50%;
	height: 50%;
	position: absolute;
	z-index: 10;
	opacity: 0.8;
}

#weatherCast{
	padding: 10px 10px;
	width:100px;
}
</style>
</head>

<body>

	<jsp:include page="noticePop.jsp" />
	<jsp:include page="top.jsp" />
	<jsp:include page="navi.jsp" />

<%-- 	<c:if test="${sessionScope.loginId eq null}">
		<section>
			<form action="login" method="post">
				<table>
					<tr>
						<th>ID</th>
						<td><input type="text" name="userId" /></td>
					</tr>
					<tr>
						<th>PW</th>
						<td><input type="password" name="userPw" /></td>
					</tr>
					<tr>
						<td colspan="2"><input type="submit" value="login" /> <input
							type="button" value="회원가입" onclick="location.href='joinForm.jsp'" />
						</td>
					</tr>
				</table>
			</form>
		</section>
	</c:if> --%>
	<jsp:include page="popularTrip.jsp"/>
	<br/>
	<jsp:include page="weatherCast.jsp" />
	
	<jsp:include page="mainBoardList.jsp" />
</body>
<script>
	
	$("li").hover(function() {
		$(this).toggleClass("li");
	});
/* 
 	var msg = "${msg}";
	if (msg != "") {
		alert(msg);
	}  */
	
	<%request.removeAttribute("msg");%>

	$("#popupBtn").click(function() {
		$(".popup").html("");
		$(".popup").removeClass();
		$("#popupBtn").attr("type", "hidden");
	});
</script>

</html>
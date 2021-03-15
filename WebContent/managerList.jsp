<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 페이지 - 메인</title>
<link rel="stylesheet" type="text/css" href="basic.css">
<Style>
table {
	margin-left: 600px;
	margin-top: 30px;
}
</Style>
</head>
<body>
	<!-- 이 페이지는 총 관리자만 접근 가능 -->
	<!--상단페이지-->
	<jsp:include page="top.jsp" />
	
	<!--상단네비-->
	<jsp:include page="navi_manager.jsp" />

    <div id="manager_main">
        <table>
            <tr>
                <th>관리자 ID</th>
                <th>등록일</th>
                <th>이름</th>
            </tr>
            <c:forEach items="${managerList}" var="manager">
            <tr>
                <td>${manager.managerid}</td>
                <td>${manager.reg_date}</td>
                <td>${manager.name}</td>
                <td><a href="managerDel?managerid=${manager.managerid}">삭제</a></td>
            </tr>
            </c:forEach>
            <div style="text-align: right; margin-right:500px; margin-top: 50px;">
            <!-- <button onclick="location.href='manager_regist.jsp'">신규 관리자 등록</button>  -->
            <input type="button" value="신규 관리자 등록" onclick="show();" />
            </div>
        </table>
    </div>
</body>
<script>
function show(){
	window.open(
			"managerRegist.jsp","신규관리자등록",
			"width=500, height=400, left=530, top=100, location=no, status=no, scrollbars=no"
			);
}

var msg = "${msg}";
if(msg!=""){
	alert(msg);
}	
</script>
</html>
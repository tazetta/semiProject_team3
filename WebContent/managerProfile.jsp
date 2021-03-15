<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일반 관리자 정보 확인</title>
<style>

#manager_profile{
	min-width: 1500px;
	border: 1px solid black;
	margin: 100px 300px;
}

	table, tr, td {
	border: 1px solid lightgray;
	border-collapse: collapse;
	text-align: center;
	padding: 20px;
}
</style>
</head>
<body>
	<!-- 이 페이지는 일반 관리자들 각자 정보 확인 가능 -->

	<!--상단페이지-->
	<iframe src="top.jsp" width="100%" height="90px" frameborder="0" scrolling="no"></iframe>

	<!--상단네비-->
	<iframe src="navi_manager.jsp" width="100%" height="90px" frameborder="0" scrolling="no"></iframe>
	
	 <div id="manager_profile">
        <table>
        	<c:forEach items="${managerProfile}" var="mprofile">
            <tr>
                <th>등록일</th>
                <td>${mprofile.reg_date}</td>
            </tr>
            <tr>
                <th>관리자 ID</th>
                <td>${mprofile.managerid}</td>
            </tr>
            <tr>
                <th>이름</th>
                <td>${mprofile.name}</td>
            </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>팝업 목록 페이지</title>
<link rel="stylesheet" type="text/css" href="basic.css">
</head>
<body>
	<!--상단페이지-->
	<jsp:include page="top.jsp" />
	
	<!--상단네비-->
	<jsp:include page="admin_navbar.jsp" />
    <div class="pop">
            <table >
                <tr>
                    <th>등록일</th>
                    <th>등록 관리자</th>
                    <th>제목</th>
                    <th>노출여부</th>                 
                </tr>
                <c:forEach items="${popupList}" var="popup">
                <tr>
	                <td>${popup.reg_date}</td>
	                <td>${popup.managerid}</td>
					<td><a href="popupDetail?infoidx=${popup.infoidx}">${popup.subject}</a></td>
					<td>${popup.popupalert}</td>
					<td><a href="popupDel?infoidx=${popup.infoidx}">삭제</a></td>
                </tr>
                </c:forEach>
            </table>
             <button onclick="location.href='pop_write.jsp'">등록</button>
        </div>
</body>
<script>
	var msg = "${msg}";
	if(msg!=""){
		alert(msg);
	}	
</script>
</html>
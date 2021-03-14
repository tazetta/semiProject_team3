<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>팝업 등록 페이지</title>
<link rel="stylesheet" type="text/css" href="basic.css">
</head>
<body>
	<!--상단페이지-->
	<iframe src="top.jsp" width="100%" height="90px" frameborder="0" scrolling="no"></iframe>
	
	<!--상단네비-->
	<iframe src="navi_manager.jsp" width="100%" height="90px" frameborder="0" scrolling="no"></iframe>
	
    <div class="pop">
        <form action="popupWrite" method="post">
            <table>
                <tr>
                    <th>등록관리자</th>
                    <td><input type="text" name="managerid" value="${sessionScope.loginId}" readonly/></td>
                </tr>
                <tr>
                    <th>제목</th>
                    <td><input type="text" name="subject"/></td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td><textarea name="content"></textarea></td>
                </tr>
<!--                 <tr>
                    <th>노출여부</th>
                    <td>
                    <input type="radio" name="popupalert" value="YES"/>Y
                    <input type="radio" name="popupalert" value="NO"/>N
                    </td>
                </tr>   -->
                <tr>
                    <td colspan="2"><button>팝업 등록</button></td>
                </tr>
            </table>
        </form>
        </div>
</body>
<script>
	var msg = "${msg}";
	if(msg!=""){
		alert(msg);
	}	
</script>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%String loginId = (String)request.getSession().getAttribute("loginId"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
<style>
			table,td,th{
                border: 1px solid black;
                border-collapse: collapse;
                padding: 5px 10px;
                text-align: center;
                font-size: 16px;
            }
            table{
            	width: 100%;
                height: 100%;  
            }
            #reason{
            	width:100%;
            	height:350px;
				resize:none;
				font-size: 16px;
            }
            .text{
            	width:100%;
            	font-size: 16px;
            }
	
</style>
</head>
<body>
	<c:if test="${msg eq null}">
	<form action="./boardReport" method="post">
		<table>
			<tr>
				<th>작성자</th>
				<td><input type="text" class="text" name="userId" id="userId"value="${loginId}" readonly/></td>
			</tr>
			<tr>
				<th>신고게시물번호</th>
				<td><input type="text" class="text" name="boardIdx" value="${boardIdx}" readonly/></td>
			</tr>
			<tr>
				<th>신고사유</th>
				<td><textarea id="reason" name="reason" placeholder="신고사유를 입력하세요"></textarea></td>
			</tr>
			<tr>
				<td colspan="2"><input type="button" id ="regist" value="등록"/>
				<input type="button" onclick=window.close() value="취소"/>
				</td>
			</tr>
		</table>
	</form>
	</c:if>
</body>
<script>
var $reason = $("#reason");
$("#regist").click(function(){
	if($reason.val()==""){
		alert("신고사유를 입력해주세요");
	}else{
		$("form").submit();
	}
});
var msg="${msg}";
if(msg!=""){
	alert(msg);
	window.close();
}
</script>
</html>
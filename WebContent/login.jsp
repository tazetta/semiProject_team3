<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>로그인 페이지</title>
		<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
		<style>
            table{
                margin: auto;
                margin-top: 15%;
            }
            table,th,td{
                border: 1px solid black;
                border-collapse: collapse;
                padding: 5px 10px;
                font-size: 20px;
            }
            fieldset{
            	padding: 20px;
                width: 800px;
                margin: auto;
                margin-top: 8%;
                border: 1px solid white;
                background-color: whitesmoke;
            }
        </style>
	</head>
	<body>
	<fieldset>
		<h2 style="text-align : center; font-size : 40px;">로그인</h2>
        <form action="login" method="post">
	        <table>
	                <tr>
	                    <th style="background-color : purple">아이디</th>
	                    <td>
	                        <input type="text" name="userId" placeholder="아이디"/>
	                    </td>
	                    <td rowspan="2">
	                        <input type="submit" id="send" value="로그인"/>
	                    </td>
	                </tr>
	                <tr>
	                    <th style="background-color : purple">비밀번호</th>
	                    <td>
	                        <input type="password" name="userPw" placeholder="비밀번호"/>
	                    </td>
	                </tr>
	                <tr>
	                    <td colspan="3">
	                        <input type="button" onclick="location.href='joinForm.jsp'" value="회원가입"/>
	                        <input type="button" onclick="location.href='findIdPw.jsp'" value="아이디/비밀번호 찾기"/>
	                        <input type="button" onclick="location.href='main.jsp'" value="메인으로"/>
	                    </td>
	                </tr>
	            </table>
	            <br/>
	            <br/>
	            <br/>
	            <br/>
        
        </form>
    </fieldset>
	</body>
	<script>
		var msg = "${msg}";
		if(msg != ""){
			alert(msg);
		}
	</script>
</html>
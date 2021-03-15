<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
	</head>
	<style>
		table,td,th{
                border: 1px solid black;
                border-collapse: collapse;
                padding: 5px 10px;
        }
		table{
            	width:500px;
        }
		fieldset{
                padding: 20px 30px;
                width: 500px;
                margin: auto;
                margin-top: 10%;
                border: 1px solid white;
                background-color: whitesmoke;
        }
	</style>
	<body>
		<fieldset>
            <div>아이디 찾기</div>
            <table>
                <tr>
                    <th>이름</th>
                    <th>${findId}</th>
                </tr>
            </table>
            <div style="text-align: right; margin-top: 10px;">
                <input type="button" onclick="location.href='./login.jsp'" value="확인"/>
            </div>
        </fieldset>
	</body>
	<script>
		
	</script>
</html>
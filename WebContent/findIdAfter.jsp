<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>아이디 찾기 결과 페이지</title>
		<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
	</head>
	<style>
		table,td,th{
                border: 1px solid black;
                border-collapse: collapse;
                padding: 5px 10px;
        }
		table{
            	width:700px;
        }
		fieldset{
                padding: 20px 30px;
                width: 700px;
                margin: auto;
                margin-top: 10%;
                border: 1px solid white;
                background-color: whitesmoke;
        }
	</style>
	<body>
		<fieldset>
            <div style="font-size : 25px">아이디 찾기 결과</div>
            <br/>
            <table>
                <tr>
                    <th style="background-color : blanchedalmond">아이디</th>
                    <th>${findId}</th>
                </tr>
            </table>
            <div style="text-align: right; margin-top: 20px;">
                <input type="button" onclick="location.href='./login.jsp'" value="확인"/>
            </div>
        </fieldset>
	</body>
	<script>
		
	</script>
</html>
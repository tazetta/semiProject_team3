<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
		<style>
            table,td,th{
                border: 1px solid black;
                border-collapse: collapse;
                padding: 5px 10px;
            }
            fieldset{
                padding: 20px 30px;
            }
            div{
                margin-bottom: 10px;
            }
        </style>
	</head>
	<body>
		<fieldset>
            <div>아이디 찾기</div>
            <table>
                <tr>
                    <th>이름</th>
                    <td>
                        <input type="text">
                    </td>
                </tr>
                <tr>
                    <th>핸드폰 번호</th>
                    <td>
                        <input type="text">
                    </td>
                </tr>
            </table>
            <div style="text-align: right;">
                <button>찾기</button>
            </div>
        </fieldset>
        <fieldset>
            <div>비밀번호 찾기</div>
            <table>
                <tr>
                    <th>아이디</th>
                    <td>
                        <input type="text">
                    </td>
                </tr>
                <tr>
                    <th>이름</th>
                    <td>
                        <input type="text">
                    </td>
                </tr>
                <tr>
                    <th>핸드폰 번호</th>
                    <td>
                        <input type="text">
                    </td>
                </tr>
            </table>
            <div style="text-align: right;">
                <button>찾기</button>
            </div>
        </fieldset>
	</body>
	<script>

	</script>
</html>
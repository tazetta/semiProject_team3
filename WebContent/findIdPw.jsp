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
                        <input type="text" id="userName" name="userName" placeholder="이름을 입력해주세요."/>
                    </td>
                </tr>
                <tr>
                    <th>핸드폰 번호</th>
                    <td>
                        <input type="text" id="userPhone" name="userPhone" maxlength="13" placeholder="핸드폰번호를 입력해주세요."/>
                    </td>
                </tr>
            </table>
            <div style="text-align: right;">
                <button id="btn1">찾기</button>
            </div>
        </fieldset>
        <fieldset>
            <div>비밀번호 찾기</div>
            <table>
                <tr>
                    <th>아이디</th>
                    <td>
                        <input type="text" id="userId" name="userId" placeholder="아이디를 입력해주세요."/>
                    </td>
                </tr>
                <tr>
                    <th>이름</th>
                    <td>
                        <input type="text" id="userName" name="userName" placeholder="이름을 입력해주세요."/>
                    </td>
                </tr>
                <tr>
                    <th>핸드폰 번호</th>
                    <td>
                        <input type="text" id="userPhone" name="userPhone" maxlength="13" placeholder="핸드폰번호를 입력해주세요."/>
                    </td>
                </tr>
            </table>
            <div style="text-align: right;">
                <button id="btn2">찾기</button>
            </div>
        </fieldset>
	</body>
	<script>
		$('#userPhone1').keydown(function(event) {
	        var key = event.charCode || event.keyCode || 0;
	        $text = $(this);
	        if (key !== 8 && key !== 9) {
	            if ($text.val().length == 3) {
	                $text.val($text.val() + '-');
	            }
	            if ($text.val().length == 8) {
	                $text.val($text.val() + '-');
	            }
	        }
	     
	        return (key == 8 || key == 9 || key == 46 || (key >= 48 && key <= 57) || (key >= 96 && key <= 105));          
    	});
	
	
		$("#btn1").click(function(){
			var $name = $("#userName");
			var $phone = $("#userPhone");
		});
		
		$("#btn2").click(function(){
			
		});
	</script>
</html>
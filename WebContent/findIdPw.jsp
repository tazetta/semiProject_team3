<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>아이디 비밀번호 찾기 페이지</title>
		<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
		<style>
            table,td,th{
                border: 1px solid black;
                border-collapse: collapse;
                padding: 5px 10px;
            }
            table{
            	width:500px;
            }
            #fieldset1{
                padding: 20px 30px;
                width: 500px;
                margin: auto;
                margin-top: 10%;
                border: 1px solid white;
                background-color: whitesmoke;
            }
            #fieldset2{
            	padding: 20px 30px;
            	width: 500px;
            	margin: auto;
            	border: 1px solid white;
            	background-color: whitesmoke;
            }
            div{
                margin-bottom: 10px;
            }
            input[type='text']{
                width: 90%;
        	}
        </style>
	</head>
	<body>
	<h2 style="text-align : center">아이디 비밀번호 찾기</h2>
	<form action="findId" method="POST">
		<fieldset id="fieldset1">
            <div>아이디 찾기</div>
            <table>
                <tr>
                    <th style="background-color : blanchedalmond">이름</th>
                    <td>
                        <input type="text" id="userName" name="userName" placeholder="이름을 입력해주세요."/>
                    </td>
                </tr>
                <tr>
                    <th style="background-color : blanchedalmond">핸드폰 번호</th>
                    <td>
                        <input type="text" id="userPhone" name="userPhone" maxlength="13" placeholder="핸드폰번호를 입력해주세요."/>
                    </td>
                </tr>
            </table>
            <div style="text-align: right; margin-top: 10px;">
                <button id="btn1" type="submit">찾기</button>
                <input type="button" onclick="location.href='./login.jsp'" value="취소"/>
            </div>
        </fieldset>
	</form>
	
	<form action="findPw" method="POST">
        <fieldset id="fieldset2">
            <div>비밀번호 찾기</div>
            <table>
                <tr>
                    <th style="background-color : blanchedalmond">아이디</th>
                    <td>
                        <input type="text" id="userId" name="userId" placeholder="아이디를 입력해주세요."/>
                    </td>
                </tr>
                <tr>
                    <th style="background-color : blanchedalmond">이름</th>
                    <td>
                        <input type="text" id="userName1" name="userName" placeholder="이름을 입력해주세요."/>
                    </td>
                </tr>
                <tr>
                    <th style="background-color : blanchedalmond">핸드폰 번호</th>
                    <td>
                        <input type="text" id="userPhone1" name="userPhone" maxlength="13" placeholder="핸드폰번호를 입력해주세요."/>
                    </td>
                </tr>
            </table>
            <div style="text-align: right; margin-top: 10px;">
                <button id="btn2">찾기</button>
                <input type="button" onclick="location.href='./login.jsp'" value="취소"/>
            </div>
        </fieldset>
	</form>
	</body>
	<script>
	
	var msg="${msg}";
	if(msg!=""){
		alert(msg);
	}
	
		var nameChk = false;
		var phoneChk = false;
		var idChk = false;
		
/*          $('#userName').focusout(function(){
            if($(this).val()==''){
                 alert('이름을 입력해주세요.');
                 $(this).focusin();
             }else{
                 nameChk = true;
             }
         });
        
         $('#userPhone').focusout(function(){
            if($(this).val()==''){
                 alert('번호를 입력해주세요.');
                 $(this).focusin();
             }else{
                 phoneChk = true;
             }
         });  */
	
	
		$('#userPhone').keydown(function(event) {
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
	
	
		 $('#btn1').click(function(){
			var $name = $("#userName");
			var $phone = $("#userPhone");
			
			if(nameChk){
				if($name.val()==''){
					alert('이름을 입력해주세요.');
					
				}else if($phone.val()==''){
					alert('핸드폰번호를 입력해주세요.');
					
				}
			}
		});
		
		/* $('#userId').focusout(function(){
            if($(this).val()==''){
                 alert('아이디를 입력해주세요.');
                 $(this).focusin();
             }else{
                 idChk = true;
             }
         });
		
		 $('#userName1').focusout(function(){
            if($(this).val()==''){
                 alert('이름을 입력해주세요.');
                 $(this).focusin();
             }else{
                 nameChk = true;
             }
         }); 
        
         $('#userPhone1').focusout(function(){
            if($(this).val()==''){
                 alert('번호를 입력해주세요.');
                 $(this).focusin();
             }else{
                 phoneChk = true;
             }
         });  */
	
	
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
		
		 $("#btn2").click(function(){
			 
			 var $id = $("#userId");
			 var $name = $("#userName1");
			 var $phone = $("#userPhone1");
			 
			if($id.val()==''){
				alert('가입하신 아이디를 입력해주세요.');
			}else if($id.val().length<5){
				alert('가입하신 아이디를 5자 이상 입력해주세요.');
			}else if($name.val()==''){
				alert('가입하신 이름을 입력해주세요.');
			}else if($phone.val()==''){
				alert('가입하신 핸드폰 번호를 입력해주세요.');
			}/* else{
				alert('새로운 비밀번호를 입력해주세요.');
				location.href="findpwUpdate.jsp";
			} */
		});
	</script>
</html>
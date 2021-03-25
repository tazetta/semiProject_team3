<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>비밀번호 찾기 후 수정 페이지</title>
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
            	height : 100px;
        }
		fieldset{
                padding: 20px 30px;
                width: 500px;
                margin: auto;
                margin-top: 10%;
                border: 1px solid white;
                background-color: whitesmoke;
        }
        input[type='text'],input[type='password']{
                width: 90%;
        }
	</style>
	<body>
		<form action="findpwUpdate?id=${id}" method="POST">
		<fieldset>
            <div style="font-size : 25px; font-weight: bold;">비밀번호 재설정</div>
            <br/>
            <br/>
            <table>
                <tr>
                    <th style="background-color : blanchedalmond">비밀번호</th>
                    <td>
                        <input type="password" id="newPw" name="newPw" maxlength="20" placeholder="새로운 비밀번호를 입력해주세요."/>
                    </td>
                </tr>
                <tr>
                    <th style="background-color : blanchedalmond">비밀번호 확인</th>
                    <td>
                        <input type="password" id="pwConfirm" maxlength="20" placeholder="비밀번호 확인."/>
                        <br/>
                        <span id="passChk"></span>
                    </td>
                </tr>
            </table>
            <div style="text-align: center; margin-top: 10px;">
            <input type = "button" id="btn" value="확인"/>
                	<!-- <input type="button" value="확인" id="btn"/> -->
            </div>
        </fieldset>
		</form>
	</body>
	<script>
	var msg="${msg}";
	if(msg!=""){
		alert(msg);
	}
	
	
	var $newPw = $("#newPw");
	var $pwConfirm = $("#pwConfirm");
	var pwChk = false;
	
	//pw에 한글 입력안되게(영어 숫자 특수문자만)
	$(document).ready(function(){
		  $("input[name=newPw]").keyup(function(event){ 
		   if (!(event.keyCode >=37 && event.keyCode<=40)) {
		    var inputVal = $(this).val();
		    $(this).val(inputVal.replace(/[^a-z0-9~!@#$%^&*()_.,+<>?:{}]/gi,''));
		   }
		  });
		});
	
 	//pw가 5자 이상인가?
    $('#newPw').focusout(function(){
        if($(this).val()!==$('#pwConfirm').val()){
            $('#passChk').html('비밀번호가 일치하지 않습니다.');
            $('#passChk').css('color','red');
            pwChk = false;
        }else{
            $('#passChk').html('비밀번호가 일치합니다.');
            $('#passChk').css('color','green');
            pwChk = true;
        }
    }); 
    //pw와 pw확인이 값이 일치하는가?
    $('#pwConfirm').keyup(function(){
        if($(this).val()!==$('#newPw').val()){
            $('#passChk').html('비밀번호가 일치하지 않습니다.');
            $('#passChk').css('color','red');
            pwChk = false;
        }else{
            $('#passChk').html('비밀번호가 일치합니다.');
            $('#passChk').css('color','green');
            pwChk = true;
        }
    });
    
    $('#btn').click(function(){
    	if($newPw.val()==""||$pwConfirm==""){//비밀번호 또는 비밀번호확인이 공백일때
    		console.log($newPw.val());
    		alert("비밀번호를 입력해주세요.")
    		$newPw.focus();
    	}else if($newPw.val().length<5){
    		alert("비밀번호는 5자 이상 입력해주세요!!");
    		$newPw.focus();
    	}else if(pwChk==false){
    		alert("비밀번호가 일치하지 않습니다.");
    		$newPw.focus();
    	}else{
    		console.log("비밀번호가 수정되었습니다.");
    		$('form').submit();
    	}
    });
    
    /* else{
	alert("비밀번호가 변경되었습니다.");
	location.href="login.jsp";
} */
	</script>
</html>
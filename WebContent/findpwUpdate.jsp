<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
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
		<form action="findpwUpdate" method="POST">
		<fieldset>
            <div>비밀번호 재설정</div>
            <table>
                <tr>
                    <th style="background-color : blanchedalmond">비밀번호</th>
                    <td>
                        <input type="password" id="newPw" name="newPw" placeholder="새로운 비밀번호를 입력해주세요."/>
                    </td>
                </tr>
                <tr>
                    <th style="background-color : blanchedalmond">비밀번호 확인</th>
                    <td>
                        <input type="password" id="pwConfirm" placeholder="비밀번호 확인."/>
                        <br/>
                        <span id="passChk"></span>
                    </td>
                </tr>
            </table>
            <div style="text-align: right; margin-top: 10px;">
                <td>
                	<input type="button" value="확인" id="btn"/>
                </td>
            </div>
        </fieldset>
		</form>
	</body>
	<script>
	
	var $newPw = $("#newPw");
	var $pwConfirm = $("#pwConfirm");
	var pwChk = false;
	
 	//2. pw가 5자 이상인가?
    $('#newPw').focusout(function(){
        if($(this).val().length<5){
            alert('비밀번호를  5자 이상 입력해주세요.');
            $(this).focusin();
        }else if($(this).val()!==$('#pwConfirm').val()){
            $('#passChk').html('비밀번호가 일치하지 않습니다.');
            $('#passChk').css('color','red');
            pwChk = false;
        }else{
            $('#passChk').html('비밀번호가 일치합니다.');
            $('#passChk').css('color','green');
            pwChk = true;
        }
    }); 
    //3. pw와 pw확인이 값이 일치하는가?
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
    	if($newPw.val()==""||$pwConfirm==""){
    		console.log($newPw.val());
    		alert("비밀번호를 입력해주세요.")
    	}else if($newPw.val().length<5){
    		alert("비밀번호는 5자 이상 입력해주세요.");
    	}else if(pwChk==false){
    		alert("비밀번호가 일치하지 않습니다.");
    	}else{
    		alert("비밀번호가 변경되었습니다.");
    		location.href="login.jsp";
    	}
    });
	</script>
</html>
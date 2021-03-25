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
                padding: 10px 20px;
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
	<form action="findId1" method="POST">
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
                <input type="button" id="btn1" value="찾기"/>
                <input type="button" onclick="location.href='./login.jsp'" value="취소"/>
            </div>
        </fieldset>
	</form>
	
	<form action="findPw1" method="POST">
        <fieldset id="fieldset2">
            <div>비밀번호 찾기</div>
            <table>
                <tr>
                    <th style="background-color : blanchedalmond">아이디</th>
                    <td>
                        <input type="text" id="userId" name="userId" maxlength="12" placeholder="아이디를 입력해주세요."/>
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
				<input type="button" id="btn2" value="찾기"/>            
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
		
 		//Id에 한글 입력안되게(영어 숫자만)
 		$(document).ready(function(){
 			  $("input[name=userId]").keyup(function(event){ 
 			   if (!(event.keyCode >=37 && event.keyCode<=40)) {
 			    var inputVal = $(this).val();
 			    $(this).val(inputVal.replace(/[^a-z0-9]/gi,''));
 			   }
 			  });
 			});
 		
 		//phone에 한글,영어,특수문자 입력안되게(숫자만)
 		$(document).ready(function(){
 			  $("input[name=userPhone]").keyup(function(event){ 
 			   if (!(event.keyCode >=37 && event.keyCode<=40)) {
 			    var inputVal = $(this).val();
 			    $(this).val(inputVal.replace(/[^0-9-]/gi,''));
 			   }
 			  });
 			});
		
/*         $('#userName').focusout(function(){
            if($(this).val()==''){
                 alert('가입하신 이름을 입력해주세요.');
                 
             }else{
                 nameChk = true;
             }
         });
        
        $('#userPhone').focusout(function(){
            if($(this).val()==''){
                 alert('가입하신 핸드폰번호를 입력해주세요.');
                 
             }else{
                 phoneChk = true;
             }
         }); */
	
	
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
	
	
 		/*  $('#btn1').click(function(){
			var $name = $("#userName");
			var $phone = $("#userPhone");
			
			
			if($name.val()==''){
				alert('이름, 핸드폰번호를 확인해주세요.');
				$name.focus();
			}else if($phone.val()==''){
				alert('이름, 핸드폰번호를 확인해주세요.');
				$phone.focus();
			}
			
		});  */
		
/*         $('#userId').focusout(function(){
            if($(this).val().length<5){
                 alert('가입하신 아이디를 5자 이상 입력해주세요.');
                 
             }else{
                 idChk = true;
             }
         });
		
        $('#userName1').focusout(function(){
            if($(this).val()==''){
                 alert('가입하신 이름을 입력해주세요.');
                 
             }else{
                 nameChk = true;
             }
         });
        
        $('#userPhone1').focusout(function(){
            if($(this).val()==''){
                 alert('가입하신 핸드폰번호를 입력해주세요.');
                 
             }else{
                 phoneChk = true;
             }
         }); */
	
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
				$id.focus();
			}else if($id.val().length<5){
				alert('가입하신 아이디를 5자 이상 입력해주세요.');
				$id.focus();
			}else if($name.val()==''){
				alert('가입하신 이름을 입력해주세요.');
				$name.focus();
			}else if($phone.val()==''){
				alert('가입하신 핸드폰 번호를 입력해주세요.');
				$phone.focus();
            }else{
            	var params = {};
				params.id = $id.val();
				params.name = $name.val();
				params.phone = $phone.val();
            	$.ajax({
					type:'get'
					,url:'findPw'
					,data: params
					,dataType:'JSON'
					,success:function(obj){
						console.log(obj);
						if(obj.use == false){//입력값이 맞다면
							alert("입력하신 정보를 확인해주세요.");
						}else{
							alert('비밀번호를 수정해주세요.');
							$("form").submit();
						}
					}
					,error:function(e){
						console.log(e);
					}
				});
            }
		}); 
	
	$("#btn1").click(function(){
		 
		 var $name = $("#userName");
		 var $phone = $("#userPhone");
		 
		if($name.val()==''){
			alert('가입하신 이름을 입력해주세요.');
			$name.focus();
		}else if($phone.val()==''){
			alert('가입하신 핸드폰 번호를 입력해주세요.');
			$phone.focus();
        }else{
        	var params = {};
			params.name = $name.val();
			params.phone = $phone.val();
        	$.ajax({
				type:'get'
				,url:'findId'
				,data: params
				,dataType:'JSON'
				,success:function(obj){
					console.log(obj.use);
					if(obj.use == ""){//아이디 값이 없다면
						alert("이름, 핸드폰번호를 다시 확인 후 입력해주세요.");
					}else{
						location.href="./findId1?id="+obj.use;
					}
				}
				,error:function(e){
					console.log(e);
				}
			});
       }
	}); 
	</script>
</html>
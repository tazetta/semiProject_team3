<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>회원가입</title>
		<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
		<style>
            table,td,th{
                border: 1px solid black;
                border-collapse: collapse;
                padding: 5px 10px;
            }
            table{
                width: 500px;
            }
            input[type='text'],input[type='password'],input[type='email']{
                width: 70%;
            }
            span{
                font-size: 10pt;
            }
            fieldset{
                padding: 20px;
                width: 500px;
                margin: auto;
                margin-top: 15%;
                border: 1px solid white;
                background-color: whitesmoke;
            }
        </style>
	</head>
	<body>
		<h2>회원가입</h2>
        <hr/>
           <fieldset>
               <table>
                   <tr>
                       <th>아이디</th>
                       <td>
                           <input type="text" id="userId" name="userId" placeholder="아이디를 입력해주세요."/>
                           <input type="submit" id="overlay" value="중복 확인"/>
                       </td>
                   </tr>
                   <tr>
                       <th>이름</th>
                       <td>
                           <input type="text" id="userName" name="userName" placeholder="이름을 입력해주세요."/>
                       </td>
                   </tr>
                   <tr>
                       <th>비밀번호</th>
                       <td>
                           <input type="password" id="userPw" name="userPw" placeholder="비밀번호를 입력해주세요."/>
                       </td>
                   </tr>
                   <tr>
                       <th>비밀번호 확인</th>
                       <td>
                           <input type="password" id="pwConfirm" placeholder="비밀번호를 한번 더 입력해주세요."/>
                           <span></span>
                       </td>
                   </tr>
                   <tr>
                       <th>핸드폰 번호</th>
                       <td>
                           <input type="text" id="userPhone" name="userPhone" maxlength="13" placeholder="핸드폰번호를 입력해주세요."/>
                       </td>
                   </tr>
                   <tr>
                       <th>이메일</th>
                       <td>
                           <input type="email" id="email" name="email" placeholder="이메일을 입력해주세요."/>
                       </td>
                   </tr>
               </table>
               <div style="text-align: center;margin-top: 10px;">
                   <button>회원가입</button>
                   <input type="button" onclick="location.href='./login.jsp'" value="취소"/>
               </div>
           </fieldset>
        
	</body>
	<script>
		var overChk = false;//중복 체크 여부
		var idChk = false;
		var pwChk = false;
		var emailChk = false;
	
		$("#overlay").click(function(){
			
			$.ajax({
				type:'get'
				,url:'overlay'
				,data:{"id":$("#userId").val()}
				,dataType:'JSON'
				,success:function(obj){
					console.log(obj);
					if(obj.use){//obj를 사용할수있다면
						alert('사용할 수 있는 아이디 입니다.');
						//비동기 방식을 사용하면 javascript 를 이용하여 좀더 다양한 효과를 줄 수 있다.
						$("#userId").css({backgroundColor:'yellowgreen'});
						overChk = true;
					}else{
						alert('이미 사용중인 아이디 입니다.');
						$("#userId").val('');
					}
				}
				,error:function(e){
					console.log(e);
				}
			});
			
		});
		//1. id가 5자 이상인가? id창에서 벗어날때 보이게할것
        $('#userId').focusout(function(){
           if($(this).val().length<5){
                alert('아이디를 5자 이상 입력해주세요.');
                $(this).focus();
            }else{
                idChk = true;
            }
        });
        //2. pw가 5자 이상인가?
        $('#userPw').focusout(function(){
            if($(this).val().length<5){
                alert('비밀번호를  5자 이상 입력해주세요.');
                $(this).focus();
            }else if($(this).val()!==$('#pwConfirm').val()){
                $('span').html('비밀번호가 일치하지 않습니다.');
                $('span').css('color','red');
                pwChk = false;
            }else{
                $('span').html('비밀번호가 일치합니다.');
                $('span').css('color','green');
                pwChk = true;
            }
        });
        //3. pw와 pw확인이 값이 일치하는가?
        $('#pwConfirm').keyup(function(){
            if($(this).val()!==$('#userPw').val()){
                $('span').html('비밀번호가 일치하지 않습니다.');
                $('span').css('color','red');
                pwChk = false;
            }else{
                $('span').html('비밀번호가 일치합니다.');
                $('span').css('color','green');
                pwChk = true;
            }
        });
        //4. 핸드폰 번호입력시 하이픈 자동입력
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
	
        //5. 이메일은 형식을 갖추었는가?(@,.)
        $('#email').focusout(function(){
            var val = $(this).val();
            if(val.indexOf('@')<0){
                alert('이메일의 형식을 맞춰 주세요(@ 추가)');
                $(this).focus();
            }else if(val.indexOf('.')<0){
                alert('이메일의 형식을 맞춰주세요(. 추가');
                $(this).focus();
            }else{
                emailChk = true;
            }
        });
                
                
	
		$('button').click(function(){//버튼을 클릭하는 순간부터
			
			var $id = $("#userId");//selector 값을 변수에 담는다.(나중에 간편하게 사용하기 위해)
			var $name = $("#userName");
			var $pw = $("#userPw");
			var $phone = $("#userPhone");
			var $email = $("#email");
			//유효성 체크
			if(overChk){
				
				if($id.val()==''){
					alert('아이디를 입력해 주세요!');
					$id.focus();
				}else if($name.val()==''){
					alert('이름을 입력해 주세요!');
					$name.focus();
				}else if($pw.val()==''){
					alert('비밀번호를 입력해 주세요!');
					$pw.focus();
				}else if($phone.val()==''){
					alert('핸드폰번호를 입력해 주세요!');
					$phone.focus();
				}else if($email.val()==''){
					alert('이메일을 입력해 주세요!');
					$email.focus();
				}else{
					var params = {};
					params.id = $id.val();
					params.name = $name.val();
					params.pw = $pw.val();
					params.phone = $phone.val();
					params.email = $email.val();
					
					$.ajax({
						type:'POST'
						,url:'join'
						,data:params//하나하나 다 집어넣기에는 복잡하기에 params사용
						,dataType:'JSON'
						,success:function(data){
							console.log(data);
							if(data.success == true){
								alert('회원가입을 축하 드립니다.');
								location.href="./index.jsp";
							}else{
								alert('잠시후 다시 시도해 주세요.');
							}
						},error:function(e){
							
						}
					});
				}			
			}else{
				alert('중복 체크를 해 주세요!');
				$id.focus();//위의var id = $("#userId");에서 userId로 포커스이동
			}
			
		});
		
	
	</script>
</html>
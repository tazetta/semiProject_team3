<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>

<head>
<meta charset="utf-8">
<title>마이 페이지 - 회원정보 수정</title>
<link rel="icon" href="south-korea.png">
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<style>
body {
	min-width: 1500px;
	font-family: "NanumGothic"; 
}



/*콘텐츠*/
#content {
	height: 600px;
	/* background-color: #F2F2F2; */
	text-align: center;
	float: left;
	width: 80%;
}

table, tr, td {
	border-collapse: collapse;
	text-align: center;
	padding: 10px;
}


span {
	position: relative;
	top: 50px;
	font-weight: 600;
	color: dimgrey;
}

table#profile {
	position: relative;
	top: 80px;
	margin: 0 auto;
	width: 600px;
}

#profile input[type="text"], #profile input[type="password"], #profile input[type="email"]
	{
	width: 80%;
	height: 30px;
}
</style>

</head>

<body>
	<jsp:include page="top.jsp" />
	<jsp:include page="navi.jsp" />
	<jsp:include page="myLeft.jsp" />
	<div id="content">
		<span>프로필 수정</span>
		<table id="profile">
			<tr style="border-top:1px solid #D8D8D8;">
				<th>아이디</th>
				<td>${sessionScope.loginId}</td>
			</tr>
			<tr>
				<th>이름</th>
				<td><input type="text" id="userName" value="${profile.name}"></td>
			</tr>
			<tr>
				<th>핸드폰 번호</th>
				<td><input type="text" id="userPhone" value="${profile.phone}"></td>
			</tr>
			<tr>
				<th>이메일</th>
				<td><input type="email" id="userEmail" value="${profile.email}"></td>
			</tr>
			<tr>
				<th>현재 비밀번호</th>
				<td><input type="password" id="userPw"></td>
			</tr>
			<tr style="border-top:1px solid #D8D8D8;">
				<td colspan="2" style="padding-top:30px;"><input type="button"
					value="저장" id="save" />
					<button onclick="location.href='profile'">취소</button></td>
			</tr>
		</table>
	</div>



</body>
<script>
	
    
    /*비밀번호 유효성 검사*/
    
    $("#save").click(function(){
    	var $pw = $("#userPw");
    	var $name =  $("#userName");
    	var $phone = $("#userPhone");
    	var $email = $("#userEmail");
    	
    	if($pw.val()==""){
    		alert("비밀번호를 입력해주세요");
    	}else if($name.val()==""||$phone.val()==""||$email.val()==""){
    		console.log($name.val());
    		alert("수정할 내용을 공란없이 입력해주세요");
    	}else if($name.val().length>50){
    		alert("이름은 50자 이하로 입력해주세요");
    	}else if($phone.val().length>20){
    		alert("핸드폰 번호는 20자 이하로 입력해주세요");
    	}else if($email.val().length>50){
    		alert("이메일은 50자 이하로 입력해주세요");
    	}else if($email.val().indexOf("@")<0||$email.val().indexOf(".")<0){
    		alert("이메일 형식을 맞춰주세요(@,.필수)");
    	}
    	else{
    		console.log("서버로 전송");
    		
    		var params ={};
    		params.pw = $pw.val();
    		params.name = $name.val();
    		params.email = $email.val();
    		params.phone = $phone.val();
    		
    		$.ajax({
    			type:"post"
    			,url:"memberUpdate"
    			,data:params
    			,dataType:"json"
    			,success:function(data){
    				console.log("data:",data);
    				if(data.success ==true){
    					alert("저장되었습니다");
    					location.href="profile"; //컨트롤러 탄 후 회원정보 페이지로 이동(profile.jsp)
    				}else{
    					alert("비밀번호를 확인해주세요");
    				}
    			},error:function(e){
    				console.log(e);
    			}
    		});
    	}
    });
</script>

</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- c태그 사용위해 불러옴 -->
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<title>마이 페이지- 비밀번호변경</title>
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
	background-color: white;
	position: relative;
	top: 80px;
	margin: 0 auto;
	width: 500px;
}

input[type="password"] {
	width: 80%;
	height: 30px;
}

#passChk {
	position: relative;
	top: 30px;
	left: 380px;
}
</style>

</head>

<body>

	<jsp:include page="top.jsp" />
	<jsp:include page="navi.jsp" />

<jsp:include page="myLeft.jsp" />

	<div id="content">
		<span>비밀번호 변경</span> <br />
		<form action="pwUpdate" method="post">
			<table id="profile">
				<tr style="border-top:1px solid #D8D8D8;">
					<th>현재 비밀번호</th>
					<td><input type="password" name="userPw" id="userPw"></td>
				</tr>
				<tr>
					<th>변경할 비밀번호</th>
					<td><input type="password" name="updatePw" id="updatePw"></td>
				</tr>
				<tr>
					<th>비밀번호 확인</th>
					<td><input type="password" id="confirmPw"></td>
				</tr>
				<tr style="border-top:1px solid #D8D8D8;">
					<td colspan="2" style="padding-top:30px;">
					<input type="button" value="저장" id="save" /> 
					<input type="button" value="취소" id="save"  onclick="location.href='profile'"/>
					</td>
				</tr>
			</table>
		</form>
		<span id="passChk"></span>

	</div>

</body>
<script>
var msg = "${msg}"; //el태그 사용해서 request로 저장된 msg 담기
if (msg != "") {  //공백이 아니면 (컨트롤러를 거쳐서 msg가 담기면)
	alert(msg); //해당 메시지 출력
}
/*카테고리 이벤트*/
$("li").hover(function () {
    $(this).toggleClass("li");
});
$("li").click(function(){
    $(this).css({"background-color":"#E6E6E6","font-weight": "600"});
});

$(".menu").hover(function () {
    $(this).toggleClass("menuHover");
});
$(".menu").click(function () {
    $(this).css({ "background-color": "#F5D0A9", "font-weight": "600" });
})
	 var $userPw = $("#userPw");
	 var $updatePw = $("#updatePw");
	 var $confirmPw = $("#confirmPw");
	 var pwChk = false;
	 
/*비밀번호 불일치*/
 $("#confirmPw").keyup(function(){
	 if($updatePw.val() != $confirmPw.val()){
		 $("#passChk").html("비밀번호 불일치");
		 $("#passChk").css({"color": "red" });
		 pwChk= false;
	 }else{
		 $("#passChk").html("비밀번호 일치");
		 $("#passChk").css({"color": "green" });
		 pwChk= true;
	 }
 }) ;
 
 $("#updatePw").keyup(function(){
     if ($(this).val() != $("#confirmPw").val()) {
         $("#passChk").html("비밀번호 불일치");
         $("#passChk").css({ "color": "red" });
         pwChk = false;
     } else {
         $("#passChk").html("비밀번호 일치");
         $("#passChk").css({ "color": "green" });
         pwChk = true;
     }
 })

/*비밀번호 유효성 검사*/
 
 $("#save").click(function(){
	 if($userPw.val()==""||$updatePw.val()==""||$confirmPw==""){
	 	console.log($userPw.val()+"/"+$updatePw.val());
	 	alert("비밀번호를 입력해주세요");
	 }else if($updatePw.val().length<5){
		 alert("비밀번호는 5자 이상 입력해주세요");
	 }else if($updatePw.val().length>50){
		 alert("비밀번호는 50자 이하로 입력해주세요");
	 }else if(pwChk==false){
		 alert("비밀번호가 일치하지 않습니다");
	 }else{
		 $("form").submit();
		 /* location.href='pwUpdate'; */
	 }
	 
 });
</script>

</html>
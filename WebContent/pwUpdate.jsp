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
}


/*좌측 카테고리*/
table, tr, td {
	border: 1px solid lightgray;
	border-collapse: collapse;
	text-align: center;
	padding: 20px;
}

section#left {
	position: relative;
	float: left;
	margin-left: 10px;
	padding: 10px;
}

.menuHover {
	font-weight: 600;
}
/*콘텐츠*/
#content {
	height: 600px;
	background-color: #F2F2F2;
	text-align: center;
	float: left;
	width: 80%;
}

table, tr, td {
	border: 1px solid lightgray;
	border-collapse: collapse;
	text-align: center;
	padding: 20px;
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
	width: 600px;
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


	<section id="left">
		<div>
			<table>
				<tr>
					<td class="menu"><a href="profile">사용자 정보</a></td>
				</tr>
				<tr>
					<td class="menu"><a href="#">내가 쓴 글 보기</a></td>
				</tr>
				<tr>
					<td class="menu"><a href="#">가봤어요</a></td>
				</tr>
				<tr>
					<td class="menu"><a href="#">즐겨찾기</a></td>
				</tr>
			</table>
		</div>
	</section>

	<div id="content">
		<span>비밀번호 변경</span> <br />
		<form action="pwUpdate" method="post">
			<table id="profile">
				<tr>
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
				<tr>
					<td colspan="2" style="border: none">
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
 
/*비밀번호 유효성 검사*/
 
 $("#save").click(function(){
	 if($userPw.val()==""||$updatePw.val()==""||$confirmPw==""){
	 	console.log($userPw.val()+"/"+$updatePw.val());
	 	alert("비밀번호를 입력해주세요");
	 }else if($updatePw.val().length<5){
		 alert("비밀번호는 5자 이상 입력해주세요");
	 }else if(pwChk==false){
		 alert("비밀번호가 일치하지 않습니다");
	 }else{
		 $("form").submit();
		 /* location.href='pwUpdate'; */
	 }
	 
 });
</script>

</html>
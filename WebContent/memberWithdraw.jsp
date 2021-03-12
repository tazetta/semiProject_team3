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
	min-width: 1300px;
}

/*top*/
a:link {
	color: rgb(71, 71, 71);
	text-decoration: none;
}

a:visited {
	color: rgb(71, 71, 71);
	text-decoration: none;
}

li {
	float: left;
	list-style-type: none;
	padding: 0 10 0 10;
}

.ci {
	position: relative;
	top: 20px;
	left: 50px;
}

.login {
	/* margin-right: 80px;; */
	display: flex;
	position: absolute;
	top: 40px;
	right: 5%;
}
/*검색창*/
form {
	width: 700px;
	height: 40px;
}

div#search {
	position: absolute;
	top: 40px;
	left: 500px;
	height: 30px;
}

input[type='text'] {
	border: 2px solid #e8f8fd;
	background-color: #e8f8fd;
	text-align: center;
	width: 500px;
	height: 40px;
}

input[type='submit'] {
	border: #e8f8fd;
	background-color: #c8e4ec;
	width: 50px;
	height: 35px;
	margin-left: 10px;
	font-weight: 600;
}

/*네비*/
div.bar {
	display: flex;
	background-color: #faf9f9;
	height: 50px;
	border-radius: 5px;
	border: 1px solid lightgray;
	color: black;
	justify-content: center;
	align-items: center;
}

section#navi {
	margin-top: 67px;
}

.navi {
	position: relative;
	float: left;
	padding: 20px 30px;
	color: black;
	font-size: 120%;
	font-weight: 500;
	text-align: center;
	width: 100px;
	height: 20px;
	list-style-type: none;
	display: inline;
}

.li {
	font-weight: 600;
}

a:link {
	text-decoration: none;
	font-size: 90%;
	color: black;
}

a:visited {
	color: black;
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
	margin-top:10px;
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

table#pwChk {
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

</style>

</head>

<body>
<header>
	 <section>
		<div class="ci">
			<a href="main.html" target="_parent"><img alt="CI" src="" width="200px" height="50px"></a>
		</div>
		<div id="search">
			<form >
				<input type="text" name="search" placeholder="검색어를 입력해주세요" id="searchText"/> <input
					type="submit" value="검색" />
			</form>
		</div>
		<div class="login">
			<ul>
				<li><a href="#">로그인</a></li>
				<li><a href="#">회원가입 </a></li>

			</ul>
		</div>
	</section>
	<section id="navi">
		<div class="bar">
			<ul>
				<li class="navi"><a href="#">테마별</a></li>

				<li class="navi"><a href="#">지역별</a></li>

				<li class="navi"><a href="#">커뮤니티</a></li>

				<li class="navi"><a href="#">고객센터</a></li>

				<li class="navi"><a href="profile">마이페이지</a></li>

			</ul>
		</div>
	</section>
	</header>

	<section id="left">
		<div>
			<table>
				<tr>
					<td class="menu"><a href="profile">사용자 정보</a></td>
				</tr>
				<tr>
					<td class="menu"><a href="wroteList">내가 쓴 글 보기</a></td>
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
		<span>탈퇴안내</span> <br />
		
				<form action="memberWithdraw" method="post">
			<table id="pwChk">
				<tr>
					<th>비밀번호 확인</th>
					<td><input type="password" name="userPw" id="confirmPw"></td>
				</tr>
				<tr >
					<th colspan="2" >
					<br/>
						✓ 사용하고 계신 아이디는 탈퇴 할 경우 재사용이 불가능합니다.<br/><br/>
						✓ 게시판형 서비스에 남아있는 글은 탈퇴 후 삭제가 불가능합니다.<br/>
						<br/> 
					</th>
				</tr>
				<tr>
					<td colspan="2" style="border: none">
					<input type="button" value="탈퇴하기" id="withdraw"></td>
					
				</tr>
			</table>
			</form>
	</div>

</body>
<script>
var msg = "${msg}"; 
if (msg != "") {  
	alert(msg); 
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

	 var $confirmPw = $("#confirmPw");

 
/*비밀번호 유효성 검사*/
 
 $("#withdraw").click(function(){
	 if($confirmPw.val()==""){
	 	alert("비밀번호를 입력해주세요");
	 }else{
		 $("form").submit();
	 }
	 
 });
</script>

</html>
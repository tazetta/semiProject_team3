<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>

<head>
<meta charset="utf-8">
<title>마이 페이지 - 회원정보 보기</title>
<link rel="icon" href="south-korea.png">
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<style>
body {
	min-width: 1500px;
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
form{
width:700px;
height:40px;
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
	margin-top: 40px;
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
	position: relative;
	top: 0px;
	left: 20px;
	float: left;
	margin: 10px;
	width: 80%;
}

span {
	position: relative;
	top: 50px;
	font-weight: 600;
}

table#profile {
	background-color: white;
	position: relative;
	top: 80px;
	margin: 0 auto;
	width: 600px;
	clear: both;
}

#wd {
	position: absolute;
	right: 20px;
	bottom: 20px;
}
</style>

</head>

<body>
	<section>
		<div class="ci">
			<a href="main.html" target="_parent"><img alt="CI" src=""
				width="200px" height="50px"></a>
		</div>
		<div id="search">
			<form >
				<input type="text" name="search" placeholder="검색어를 입력해주세요" /> <input
					type="submit" value="검색" />
			</form>
		</div>
		<div class="login">
			<ul>
				<li><a href="logout">로그아웃</a></li>
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

	<section id="background">
		<div id="content">
			<span>회원정보</span>

			<table id="profile">
				<tr>
					<th>아이디</th>
					<td>${sessionScope.loginId}</td>
				</tr>
				<tr>
					<th>이름</th>
					<td>${profile.name}</td>
				</tr>
				<tr>
					<th>핸드폰 번호</th>
					<td>${profile.phone}</td>
				</tr>
				<tr>
					<th>이메일</th>
					<td>${profile.email}</td>
				</tr>
				<tr>
					<td colspan="2" style="border: none">
						<button onclick="location.href='memberUpdateForm'">회원정보 수정</button>
						<button onclick="location.href='pwUpdate.jsp'">비밀번호 변경</button>
					</td>
				</tr>
			</table>
			<button id="wd">회원탈퇴</button>
		</div>


	</section>
</body>
<script>
var msg = "${msg}"; //el태그 사용해서 request로 저장된 msg 담기
if (msg != "") {  //공백이 아니면 (컨트롤러를 거쳐서 msg가 담기면)
	alert(msg); //해당 메시지 출력
}

	/*카테고리 이벤트 */
	$("li").hover(function () {
        $(this).toggleClass("li");
    });
	
    $(".menu").hover(function () {
        $(this).toggleClass("menuHover");
    });
    $(".menu").click(function () {
        $(this).css({ "background-color": "#F5D0A9", "font-weight": "600" });
    })
</script>

</html>
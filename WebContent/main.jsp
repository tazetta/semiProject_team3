<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String loginId = (String) request.getSession().getAttribute("loginId");
%>
<!-- c태그 사용위해 불러옴 -->
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<title>index</title>
<link rel="icon" href="favicon.png">
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>

<style>
 body {
	min-width: 1500px;
}
/*top*/
li {
	float: left;
	list-style-type: none;
	padding: 0 10 0 10;
	margin-left: 15px;
}

.ci {
	position: relative;
	top: 20px;
	left: 50px;
}

.login {
	display: flex;
	position: absolute;
	top: 40px;
	right: 5%;
}
/*검색창*/
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
	margin-top: 70px;
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

.popup {
	background-color: gray;
	width: 50%;
	height: 50%;
	position: absolute;
	z-index: 10;
	opacity: 0.8;
}

#weatherCast{
	padding: 10px 10px;
	width:100px;
}
</style>
</head>

<body>

	<jsp:include page="noticePop.jsp" />
	<jsp:include page="top.jsp" />
	<jsp:include page="navi.jsp" />


	<!-- <section>
		<span class="ci"> <a href="main.html" target="_parent"><img
				alt="CI" src="koreaCI.png" width="200px" height="50px"></a>
		</span>
		<div id="search">
			<form action="search" method="GET">
				<select name="searchType">
					<option value="title">제목</option>
					<option value="overview">내용</option>
				</select>
				<input type="text" name="keyword" placeholder="검색어를 입력해주세요" /> <input
					type="submit" value="검색" />
			</form>
		</div>
		<div class="login">
			<ul>
				<li><a href="login.jsp">로그인</a></li>
				<li><a href="joinForm.jsp">회원가입 </a></li>

			</ul>
		</div>
	</section>

	<section id="navi">
		<div class="bar">
			<ul>
				<li class="navi"><a href="./themeContentList">테마별</a></li>

				<li class="navi"><a href="./areaContentList">지역별</a></li>

				<li class="navi"><a href="./boardList">커뮤니티</a></li>

				<li class="navi"><a href="#">고객센터</a></li>

				<li class="navi"><a href="profile">마이페이지</a></li>

			</ul>
		</div>
	</section> -->

	<section>
		<form action="login" method="post">
			<table>
				<tr>
					<th>ID</th>
					<td><input type="text" name="userId" /></td>
				</tr>
				<tr>
					<th>PW</th>
					<td><input type="text" name="userPw" /></td>
				</tr>
				<tr>
					<td colspan="2"><input type="submit" value="login" /> <input
						type="button" value="회원가입" onclick="location.href='joinForm.jsp'" />
					</td>
				</tr>

			</table>
		</form>
	</section>

<form action="weatherCast.jsp" method="GET">

	<input type="submit" id="weatherCast" value="날씨 테스트"/>
</form>
</body>
<script>
	$("li").hover(function() {
		$(this).toggleClass("li");
	});

	var msg = "${msg}";
	if (msg != "") {
		alert(msg);
	}
	
	<%request.removeAttribute("msg");%>

	$("#popupBtn").click(function() {
		$(".popup").html("");
		$(".popup").removeClass();
		$("#popupBtn").attr("type", "hidden");
	});
</script>

</html>
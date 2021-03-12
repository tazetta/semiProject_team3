<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%String loginId = (String)request.getSession().getAttribute("loginId"); %>
<!-- c태그 사용위해 불러옴 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<!-- JQuery사용 위해 불러옴 -->
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
	margin-top: 30px;
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
	background-color: #F2F2F2;
	text-align: center;
	position: relative;
	top: 0px;
	left: 20px;
	float: left;
	margin: 10px;
	width: 1200px;
	height : 1200px;
}

span {
	position: relative;
	top: 50px;
	font-weight: 600;
}

table#wroteList {
	background-color: white;
	position: relative;
	top: 80px;
	margin: 0 auto;
	width: 900px;

}
.noneList{
position:relative;
top: 150px;
height:60px;
text-align: center;
align-items:stretch;
background-color : transParent;
}
/*페이징*/
.pageArea {
	text-align: center;
	position: absolute;
	bottom : -500px;
	left:780px;
}

.pageArea span {
	font-size: 16px;
	border: 1px solid lightgray;
	background-color: lightgray;
	padding: 2px 10px;
}

a {
	text-decoration: none;
}

#page {
	font-weight: 600;
	border: none;
	background-color : transparent;

}
</style>
</head>
<body>
	<section>
		<div class="ci">
			<a href="main.html" target="_parent"><img alt="CI" src="./koreaCI.png" width="150px" height="60px"></a>
		</div>
		<div id="search">
			<form>
				<input type="text" name="search" placeholder="검색어를 입력해주세요" /> <input
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

	<section id="left">
		<div>
			<table>
				<tr>
					<td class="menu"><a href="./profile">사용자 정보</a></td>
				</tr>
				<tr>
					<td class="menu"><a href="./wroteList">내가 쓴 글 보기</a></td>
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

	<section id=background>
		<div id="content">
			<span>커뮤니티 - 내가 쓴 글</span>
				<c:if test="${list eq '[]'}">
					<div class="noneList">
						<p>작성한 글이 존재하지 않습니다</p>
					</div>
				</c:if>
				<table id="wroteList">
					
					<c:forEach items="${list}" var="bbs">
						<tr class="bbsTr">
							<td >${bbs.rnum}</td>
							<th style="width:500px"><a href="boardDetail?boardIdx=${bbs.boardIdx}">${bbs.subject }</a></th>
							<td>${bbs.reg_date }</td>
							<td>
								<button onclick="location.href='boardUpdateForm?boardIdx=${bbs.boardIdx}&id=${loginId}'">수정</button><br/>
								<button onclick="location.href='boardDel'">삭제</button>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
</section>
	<c:if test="${list ne '[]'}">
		<div class="pageArea">
			<span> 
				<c:if test="${currPage==1}">이전</c:if> 
				<c:if test="${currPage>1}"><a href="?page=${currPage-1}">이전</a></c:if>
			</span> 
			<span id="page">${currPage}</span> 
			<span> 
				<c:if test="${currPage==maxPage}">다음</c:if> 
				<c:if test="${currPage<maxPage}"><a href="?page=${currPage+1}">다음</a></c:if>
			</span>
		</div>
	</c:if>
</body>
<script>
	
</script>
</html>
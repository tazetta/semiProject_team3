<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일반 회원정보 리스트</title>
</head>
<body>
	<!--상단페이지-->
	<iframe src="top.jsp" width="100%" height="90px" frameborder="0" scrolling="no"></iframe>
	
	<iframe src="navi_manager.jsp" width="100%" height="90px" frameborder="0" scrolling="no"></iframe>
	
			<div id ="side">
			<table>
				<tr>
					<td class="menu"><a href="profile">사용자 정보</a></td>
				</tr>
				<tr>
					<td class="menu"><a href="wroteList">내가 쓴 글 보기</a></td>
				</tr>
				<tr>
					<td class="menu"><a href="visitedList">가봤어요</a></td>
				</tr>
				<tr>
					<td class="menu"><a href="#">즐겨찾기</a></td>
				</tr>
			</table>
		</div>
	<div id="search">
		<form>
			<select name="filter">
				<option value="id">아이디</option>
				<option value="name">이름</option>
			</select> 
			<input type="text"name="search" /> 
			<input type="submit" value="검색" />
		</form>
	</div>

	<div id="memberList_main">
		<table>
			<tr>
				<th>가입일</th>
				<th>아이디</th>
				<th>이름</th>
				<th>핸드폰 번호</th>
				<th>이메일</th>
				<th></th>
			</tr>
<%-- 			<c:forEach items="${memberList}" var="member">
				<tr>
					<td>${memberList.managerid}</td>
					<td>${memberList.reg_date}</td>
					<td>${memberList.name}</td>
					<td><a href="memberDetail?userid=${member.userid}">상세보기</a></td>
				</tr>
			</c:forEach> --%>
		</table>
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
<style>
#side {
	position: relative;
	float: left;
	margin-left: 100px;
	padding: 10px;
}

table, th, td {
	border: 1px solid lightgray;
	border-collapse: collapse;
	text-align: center;
	padding: 20px;
}

a {
	text-decoration: none;
	font-size: 90%;
	color: black;
}

a:hover {
	font-weight: 600;
}

.search {
	padding: 30px;
}

#content {
	margin-left: 300px;
	margin-right: 300px;
}
</style>
</head>
<body>
	<div id="side">
		<table>
			<tr>
				<td><a href="./memberList" target="_parent">일반 회원</a></td>
			</tr>
			<tr>
				<td><a href="./memberBlackList" target="_parent">블랙리스트 회원</a></td>
			</tr>
			<tr>
				<td><a href="./memberDelList" target="_parent">탈퇴 회원</a></td>
			</tr>
		</table>
	</div>

	<!-- <div class="search">
			<select id="filter" name="filter">
				<option value="id">아이디</option>
				<option value="name">이름</option>
			</select> 
			<input type="text" id="memberKeyword"/> 
			<button id="memberSearch">검색</button>
	</div> -->
</body>
<script>
/* $('#memberSearch').click(function(){
	var searchType = $('#filter').val()
	var memberKeyword = $('#memberKeyword').val()
    location.href="./memberSearch?searchType="+searchType+"&memberKeyword="+memberKeyword;
}); */
</script>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
<style>
.m_search{
	width: 87%;
	margin-top : 5%;
}

#filter {
	font-size: 16px;
	width : 7%;
	padding : 7px 8px;
	float: left;
}

.m_search input{
	width: 13%;
	padding: 8px;
	float: left;
	margin: 0 1%;
}

.m_search button {
	width: 5%;
	padding: 8px;
	border: 1px solid;
	background-color: whitesmoke;
	outline: none;

</style>
</head>
<body>
	<div class="m_search">
			<select id="filter" name="filter">
				<option value="id">아이디</option>
				<option value="name">이름</option>
			</select> 
			<input type="text" id="memberKeyword"/> 
			<button id="memberSearch">검색</button>
	</div>
</body>
<script>
$('#memberSearch').click(function(){
	var searchType = $('#filter').val()
	var memberKeyword = $('#memberKeyword').val()
	location.href="./memberSearch?searchType="+searchType+"&memberKeyword="+memberKeyword;
});
</script>
</html>
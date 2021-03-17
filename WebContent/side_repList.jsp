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
				<td id="bssRep"><a href="./reportBBS" target="_parent">게시글 신고 내역</a></td>
			</tr>
			<tr>
				<td id="comRep"><a href="./reportComment" target="_parent">댓글 신고 내역</a></td>
			</tr>
		</table>
	</div>

</body>
<script>
</script>
</html>
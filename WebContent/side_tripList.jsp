<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
<style>
			.tripManageList table,.tripManageList th,.tripManageList td{
				border: 1px solid lightgray;
				border-collapse: collapse;
				padding: 15px 5px;;
				text-align: center;
			}
			th{				
				width: 90px;
				min-width: 90px;
			}
			table{				
				width : 50%;
			}
			.tripmange{
				background-color: lightgray;
			}
			table {
	height: 50%;
}

table, th, td {
	border: 1px solid black;
	border-collapse: collapse;
	padding: 10px 20px;
	margin-top: 20px;
}

.title {
	width: 200px;
}

.pageArea {
	width: 100%;
	text-align: center;
	margin: 10px;
}

.pageArea span {
	font-size: 16px;
	border: 1px solid lightgray;
	padding: 2px 10px;
	color: gray;
}

.date {
	width: 100px;
}

#page {
	font-weight: 600;
	color: red;
}

#tripSearchBar {
	width: 100%;
	margin-left: 30%;
}

.tripManageList {
	width: 180px;
	float: left;
	margin-top:  50px;
	margin-left: 5%;
}

.tripManageName {
	padding: 5px 15px;
	border: 1px solid black;
	box-sizing: border-box;
	height: 30px;
	text-align: center;
	border-collapse: collapse;
	margin: 5px;
}

#noneResult {
	font-size: 36px;
	font-weight: 600px;
	text-align: center;
}
.tripBody{
	margin-top : 10px;
	width:100%;
	min-width : 900px;	
}
.midBody{
	width: 600px;
	margin-left: 30%;
	white-space: nowrap;
}

.button {
	text-align: center;
	border-color: white;
}
.select{
	width:100%;
} 
table input[type='text']{
	width:100%;
}
textarea {
	width: 100%;
	height: 150px;
	resize: none;
}
</style>
</head>
<body>
	<div id="side">
		<table class="tripManageList">
			<tr>
				<td class="tripManageName tripList" id="99">
					<a href="./tripManageList?tripNav=99" class="list">여행지 목록</a>
				</td>
			</tr>
			<tr>	
				<td  class="tripManageName tripAdd" id="100">
					<a href="./tripInsertInformation?tripNav=100" class="list">여행지 저장</a>			
				</td>
			</tr>
		</table>
	</div>
</body>
<script>
</script>
</html>
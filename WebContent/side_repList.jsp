<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
<style>
			table,th,td{
				border: 1px solid lightgray;
				border-collapse: collapse;
				padding: 15px 5px;;
				text-align: center;
			}
			fieldset,p{			
				text-align: left;
			}
			th{				
				width: 90px;
				min-width: 90px;
			}
			table{				
				width : 50%;
			}
			.btn{
				font-size: 14pt;
				font-weight: bold;
				margin-top: 10px;
				padding: 5px 10px;
			} 
			.body{
				margin-left:20%;
				font-size: 10pt;
				min-width: 700px;
				width:60%;
			}
			.comtent{
				height: 300px;				
			}
			#pros{
				float: right;
				padding: 5px;
				
			}
			#side {
				position: relative;
				top:50px;
				padding: 10px;
				z-index: 10;
				float: left;
			}
			.mid{
				width: 90%;
				min-width: 1000px;
				margin-left: 5%;
				border:  2px solid red;
			}
			#page {
				text-align: center;
				margin-top: 10px;
				font-size: 12pt;
				font-weight: bold;
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
			.nowrap{
				white-space: nowrap;
			}
			
			.header{
				background-color: gray;
				color: white;
			}
</style>
</head>
<body>
	<div id="side">
		<table style="width: 180px;text-align: center;">
			<tr>
				<td class="bssRep"><a href="./reportBBS" target="_parent">게시글 신고 내역</a></td>
			</tr>
			<tr>
				<td class="comRep"><a href="./reportComment" target="_parent">댓글 신고 내역</a></td>
			</tr>
		</table>
	</div>
</body>
<script>
</script>
</html>
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
				width : 100%;
				border: 1px solid lightgray;
				border-collapse: collapse;
				white-space: nowrap;
				padding: 20px;
			}
			fieldset,p{			
				text-align: left;
			}
			th{
				width: 150px;
				min-width: 50px;
			}
			.btn{
				font-size: 14pt;
				font-weight: bold;
				margin-top: 10px;
				padding: 5px 10px;
			} 
			.body{
				width: 800px;
				position:relative;
				left : 20%;
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
				margin-left: 100px;
				padding: 10px;
				height: 1000px;
				z-index: 10;
				float: left;
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
		<table style="width: 120px;">
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
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>신규 관리자 등록</title>
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
<style>

h2{
	text-align: center;
}

table, td, th {
	border: 1px solid black;
	border-collapse: collapse;
	padding: 5px 10px;
}

table {
	width: 400px;
}

input[type='text'], input[type='password'] {
	width: 70%;
}

span {
	font-size: 10pt;
}

fieldset {
	padding: 20px;
	margin: 5%;
	border: 1px solid white;
	background-color : whitesmoke;
}

.regist_btn{
	margin: auto;
	padding-top: 7%;
}
.regist{
	font-size: 13px;
	padding: 6px 15px;
	margin: 6px 0;
}
</style>
</head>
<body>
	<div class="new"><h2>신규 관리자 등록</h2>
	<hr />
	<fieldset>
		<form action="managerRegist" method="post">
			<table>
				<tr>
					<th>관리자 ID</th>
					<td><input type="text" id="managerId" name="managerId" placeholder="admin+숫자로 입력 " /></td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td><input type="password" id="managerPw" name="managerPw" />
					</td>
				</tr>
				<tr>
					<th>이름</th>
					<td><input type="text" id="managerName" name="managerName" />
					</td>
				</tr>
				</form>
			</table>
			<div class="regist_btn" style="text-align: center; margin-top: 10px;">
				<button class="regist">등록</button>
				&nbsp;&nbsp;&nbsp;
				<button class="regist" onclick="window.close();">취소</button>
			</div>
	</fieldset>
	</div>

</body>
<script>
var msg = "${msg}";
if(msg!=""){
	alert(msg);
}	

/* $('#test').click(function() {
	var $id = $("#managerId");
	var $pw = $("#managerPw");
	var $name = $("#managerName");
	
	var params = {};
	params.id = $id.val();
	params.pw = $pw.val();
	params.name = $name.val();
	
	$.ajax({
		type:'POST'
		,url:'./managerRegist'
		,data:params
		,dataType:'JSON'
		,success:function(data){
			console.log(data);
			if(data.success == true){
				alert('관리자를 등록하였습니다.');
				window.close();
			}else{
				alert('잠시 후 다시 시도해 주세요.');
			},error:function(e){
				console.log(e);
		}
	}); */
	
	
/* }); */
	
	

</script>
</html>
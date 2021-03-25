<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>신규 관리자 등록</title>
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
<style>
h2 {
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
}

.regist_btn {
	margin: auto;
	padding-top: 7%;
}

.regist {
	font-size: 13px;
	padding: 6px 15px;
	margin: 6px 0;
}

#overlay {
	background-color: darkseagreen;
    border: 1px solid black;
	color: white;
}
</style>
</head>
<body>
	<div class="new">
		<h2>신규 관리자 등록</h2>
		<hr />
		<fieldset>
			<form action="managerRegist" method="post">
				<table>
					<tr>
						<th>관리자 ID</th>
						<td>
						<input type="text" id="managerId" name="managerId" placeholder="admin+숫자로 입력 " /> 
						<input type="button" id="overlay" value="✔" />
						</td>
					</tr>
					<tr>
						<th>비밀번호</th>
						<td><input type="password" name="managerPw" /></td>
					</tr>
					<tr>
						<th>이름</th>
						<td><input type="text" name="managerName" /></td>
					</tr>
					</form>
				</table>
				<div class="regist_btn"
					style="text-align: center; margin-top: 10px;">
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

var overChk = false;

$("#overlay").click(function () {
console.log($('#managerId').val());

	if($('#managerId').val() == '') {
		alert('관리자 ID를 입력해 주세요.');
	}else{
	$.ajax({
		type:'get'
		,url:'managerOverlay'
		,data: {"managerId":$("#managerId").val()}
		,dataType:'JSON'
		,success:function(obj){
			console.log(obj);
			if(obj.use){
				alert('사용할 수 있는 관리자 아이디입니다.');
				$("#overlay").css({color:'black'});
				overChk = true;
			}else{
				alert('이미 사용중인 관리자 아이디입니다.');
				$("#managerId").val('');
			}
		},error:function(e){
				console.log(e);
		}
	});
	}
});
	
</script>
</html>
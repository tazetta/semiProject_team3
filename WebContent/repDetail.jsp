<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
		<style>
			table,th,td{
				width : 100%;
				border: 1px solid black;
				border-collapse: collapse;
			}
			th,td{
				width: 150px;
			}
			fieldset,p{			
				margin-top : 10px;
				box-sizing : 100%;
				text-align: left;
			}
			div{			
				width:500px;
			}
			button {
				margin-top : 10px;
			}
			#btn{
				
			}
				
		</style>
	</head>
	<body>
	<div>
		<table>
			<tr>
				<th>제목</th>
				<td>${dto.subject}</td>
				<td>신고수 / <b>${repCnt }</b></td>
				<td>
					블라인드 					
					<select id="YN">
						<option  value="TRUE" ${dto.deactivate eq 'TRUE' ? 'selected="selected"' : '' }>Y</option>
						<option value="FALSE" ${dto.deactivate eq 'FALSE' ? 'selected="selected"' : '' }>N</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>내용</th>
				<td colspan="3">${dto.content}</td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td colspan="3">
					<img src="photo/${dto.newFileName}" alt="${dto.oriFileName}" width="500px"/>
				</td>
			</tr>
		</table>
		<fieldset>
			<p>
				신고 사유 
				<br/><br/>
				<b>${reason }</b>
			</p>
		</fieldset>
		<div #btn>
			<input type="button"  onclick="location.href='./reportBBS'" value="목록"/>		
			&nbsp;&nbsp;&nbsp;
			<button id="btn"> 적용 </button>
		</div>
	</div>
	<input type="hidden" value="${dto.boardIdx}"/>
	<input type="hidden" value="${bbsRepIdx }"/>
	</body>
	<script>
		$("#btn").click(function () {
			var bbsIdx ="${dto.boardIdx}";
			var bbsRepIdx="${bbsRepIdx }";
			console.log($("#YN").val());
			$.ajax({
				type:"get"
				,url:"updateYN"
				,data:{
					"updateYN":$("#YN").val()
					,"boardIdx":bbsIdx
					,"bbsRepIdx":bbsRepIdx
				
				}
				,dataType:"json"
				,success: function(data) {
					console.log(data.suc);
					if(data.suc>0){
						location.href="./reportBBS";
					}
				}
				,error: function(e) {
					console.log(e);
				}
			});
		});	
	
	
	</script>
</html>
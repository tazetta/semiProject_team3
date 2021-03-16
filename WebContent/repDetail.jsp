<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
			#body{
				width: 800px;
			}
				
		</style>
	</head>
	<body>
		<jsp:include page="top.jsp" />
		<jsp:include page="navi_manager.jsp"/>
		<div>
			<ol style="float: left; margin-right: 50px;">
				<ul><a href="./reportBBS">게시글 신고 내역</a></ul>
				<ul><a href="./reportComment">댓글 신고 내역</a></ul>
			</ol>
		</div>
		<div id="body">
			<table>
				<tr>
					<th>제목</th>
					<td>${dto.subject}</td>
					<td>신고수 / <b>${reason.repCnt }</b></td>
					<c:if test="${reason.deactivate eq 'FALSE' }">
						<td style="background-color: coral;">
							블라인드 				
							<select id="YN">
								<option value="TRUE" ${dto.deactivate eq 'TRUE' ? 'selected="selected"' : '' }>Y</option>
								<option value="FALSE" ${dto.deactivate eq 'FALSE' ? 'selected="selected"' : '' }>N</option>
							</select>
						</td>
					</c:if>
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
					신고 사유 ${reason.deactivate}
					<br/><br/>
					<b>${reason.reason }</b>
				</p>
			</fieldset>
			<div id=#btn>
				<input type="button"  onclick="location.href='./reportBBS?page=${currPage}&deactivate=${reason.deactivate}'" value="목록"/>		
				&nbsp;&nbsp;&nbsp;
				<c:if test="${reason.deactivate eq 'FALSE' }">
					<button id="btn"> 적용 </button>
				</c:if>
			</div>
		</div>
	</body>
	<script>
		$("#btn").click(function () {
			var deactivate = "${reason.deactivate}";
			var bbsIdx ="${dto.boardIdx}";
			var bbsRepIdx="${reason.commentRepIdx }";
			var YN =$("#YN").val();
			var chkMsg ="Y";
			if(YN=="FALSE"){
				chkMsg="N";
			}
			
			var chk = confirm('선택 하신 값이 맞습니까? '+ chkMsg);
			if(chk){
				$.ajax({
					type:"get"
					,url:"updateYN"
					,data:{
						"updateYN":YN
						,"boardIdx":bbsIdx
						,"bbsRepIdx":bbsRepIdx
						,"type":'1'
						,"deactivate":deactivate
					}
					,dataType:"json"
					,success: function(data) {
						console.log(data.suc);
						if(data.suc>0){
							location.href="./reportBBS?page=${currPage}&deactivate=${reason.deactivate}";
						}
					}
					,error: function(e) {
						console.log(e);
					}
				});				
			}
		});	
	
	
	</script>
</html>
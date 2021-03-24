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
		/* 
			table,th,td{
				width : 100%;
				border: 1px solid black;
				border-collapse: collapse;
				white-space: nowrap;
			}
			fieldset,p{			
				text-align: left;
			}
			th{
				width: 150px;
			}
			.btn{
				font-size: 20pt;
				margin-top: 10px;
			} 
			.body{
				width: 800px;
			}
			
			 */
			.bssRep,.repList{
				background-color: lightgray;				
				font-weight: bold;
			}	
		</style>
	</head>
	<body>
		<jsp:include page="top.jsp" />
		<jsp:include page="navi_manager.jsp"/>
		<div class="mid">
		<jsp:include page="side_repList.jsp"/>
		<br/><br/><br/>
			<table class="body">
				<tr class="nowrap">
					<th>제목</th>
					<td>${dto.subject}</td>
					<th>신고수 / <b>${reason.repCnt }</b></th>
					<c:if test="${reason.deactivate eq 'FALSE' }">
						<th style="background-color: coral;">
							블라인드 				
							<select id="YN">
								<option value="TRUE" ${dto.deactivate eq 'TRUE' ? 'selected="selected"' : '' }>Y</option>
								<option value="FALSE" ${dto.deactivate eq 'FALSE' ? 'selected="selected"' : '' }>N</option>
							</select>
						</th>
					</c:if>
				</tr>
				<tr class="comtent">
					<th >내용</th>
					<td colspan="3" style="text-align: left;">${dto.content}</td>
				</tr>
				<tr>
					<th>첨부파일</th>
					<td colspan="3">
						<img src="photo/${dto.newFileName}" alt="${dto.oriFileName}" width="500px"/>
					</td>
				</tr>
				<tr>
					<td style="border: 1px solid white;" colspan="5">
						<fieldset>
							<legend>신고 사유 </legend>
								<b>${reason.reason }</b>
							<div style="text-align: right;">신고자 : ${reason.rid }</div>
						</fieldset>
						<div style="text-align: right;">
							<input  class="btn" type="button"  onclick="location.href='./reportBBS?page=${currPage}&deactivate=${reason.deactivate}'" value="목록"/>		
							&nbsp;&nbsp;&nbsp;
							<c:if test="${reason.deactivate eq 'FALSE' }">
								<button id="btn" class="btn"> 적용 </button>
							</c:if>
						</div>
					</td>
				</tr>
			</table>
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
			
			if(confirm('선택 하신 값이 맞습니까? '+ chkMsg)){
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
						}else{
							alert("정상적인 방법으로 수정을 시도하세요 .");
							location.href="./reportBBS";
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
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
				margin : 10px;
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
			#comment{
				margin-top : 20px;
				width: 600px;
			}	
			#comment th{
				width: 150px;
			}
			#body{
				width: 800px;
			}
			#comRep{
				background-color: gray;
			}
		</style>
	</head>
	<body>	
		<jsp:include page="top.jsp" />
		<jsp:include page="navi_manager.jsp"/>
		<jsp:include page="side_repList.jsp"/>
		<div>
			<table>
				<tr>
					<th>제목</th>
					<td>${dto.subject}</td>>
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
			<div id="comment">
				<c:forEach items="${list }" var="com">
					<c:if test="${com.reIdx ne reason.reIdx}">
					<table>
						<tr>
							<th>${com.id }</th>
							<td>${com.content }</td>
							<td>${com.reg_date }</td>
						</tr>
					</table>	
					</c:if>
					<c:if test="${com.reIdx eq reason.reIdx}">						
						<table>
							<tr style="background-color: coral;">
								<th>${com.id }</th>
								<td>${com.content }</td>
								<td>${com.reg_date }</td>
								<td>신고수 / ${reason.repCnt }</td>
								<c:if test="${reason.deactivate eq 'FALSE' }">
									<td>
										블라인드 				
										<select id="YN">
											<option value="TRUE" ${com.deactivate eq 'TRUE' ? 'selected="selected"' : ""}>Y</option>
											<option value="FALSE" ${com.deactivate eq 'FALSE' ? 'selected="selected"' : ""}>N</option>
										</select>
									</td>
								</c:if>
							</tr>
							<tr>
								<td colspan="5" style="border: 1px solid white;">
									<fieldset>
										<p>
											신고 사유
											<br/><br/>
										<b>${reason.reason }</b>
									</fieldset>
								</td>
							</tr>
						</table>
					</c:if>
				</c:forEach>
			</div>
			<div id=#btn>
				<input type="button"  onclick="location.href='./reportComment?page=${currPage}&deactivate=${reason.deactivate}'" value="목록"/>		
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
			var reIdx ="${reason.reIdx}";
			var commentRepIdx="${reason.commentRepIdx }";
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
						,"boardIdx":reIdx
						,"bbsRepIdx":commentRepIdx
						,"type":'2'
						,"deactivate":deactivate
					}
					,dataType:"json"
					,success: function(data) {
						console.log(data.suc);
						if(data.suc>0){
							location.href="./reportComment?page=${currPage}&deactivate=${reason.deactivate}";
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
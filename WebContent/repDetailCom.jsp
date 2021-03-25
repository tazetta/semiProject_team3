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
				margin-top: -20px;
			} 
			.body{
				width: 800px;
			}
			#comtent{
				height: 300px;				
			}
			 */
			.comRep,.repList{
				background-color: lightgray;				
				font-weight: bold;
			}
			#white{
				border-color: white;
			}
			.inbody{
				width: 100%;
			}
		</style>
	</head>
	<body>	
		<jsp:include page="top.jsp" />
		<jsp:include page="navi_manager.jsp"/>
		<div class="mid">
		<jsp:include page="side_repList.jsp"/>
		<br/><br/><br/>
		<table class="body" >
			<tr>
				<td id="white" >
					<table class="inbody">
						<tr>
							<th>제목</th>
							<td>${dto.subject}</td>
						</tr>
						<tr class="comtent">
							<th>내용</th>
							<td colspan="3" style="text-align: left;">${dto.content}</td>
						</tr>
						<tr>
							<th>첨부파일</th>
							<td colspan="3">
								<img src="photo/${dto.newFileName}" alt="${dto.oriFileName}" width="500px"/>
							</td>
						</tr>
					</table>
					<br/>
						<c:forEach items="${list }" var="com">
						
							<%-- <c:if test="${com.reIdx ne reason.reIdx}"> 신고 외 댓글 
							<table>
								<tr>
									<th style="width: 100px;">${com.id }</th>
									<td>${com.content }</td>
									<td style="width: 150px;">${com.reg_date }</td>
								</tr>
							</table>	
							</c:if> --%>
							
							<c:if test="${com.reIdx eq reason.reIdx}">		
								<br/>				
								<table  class="inbody">
									<tr>
										<th style="width: 100px;">${com.id }</th>
										<td style="text-align: left;">${com.content }</td>
										<th>${com.reg_date }</th>
										<th>신고수 / ${reason.repCnt }</th>
										<c:if test="${reason.deactivate eq 'FALSE' }">
											<th style="background-color: coral;">
												블라인드 				
												<select id="YN">
													<option value="TRUE" ${com.deactivate eq 'TRUE' ? 'selected="selected"' : ""}>Y</option>
													<option value="FALSE" ${com.deactivate eq 'FALSE' ? 'selected="selected"' : ""}>N</option>
												</select>
											</th>
										</c:if>
									</tr>							
									<tr>
										<td colspan="5" style="border: 1px solid white;">
											<fieldset>
												<legend>신고 사유 </legend>
													<b>${reason.reason }</b>
													<div style="text-align: right;">신고자 : ${reason.rid }</div>													
											</fieldset>
										</td>
									</tr>
								</table>
							</c:if>
						</c:forEach>
							<table class="inbody">
								<tr>
									<td  style="border: 1px solid white;" colspan="4">
										<div style="text-align: right;">
											<input class="btn" type="button"  onclick="location.href='./reportComment?page=${currPage}&deactivate=${reason.deactivate}'" value="목록"/>											&nbsp;&nbsp;&nbsp;
										<c:if test="${reason.deactivate eq 'FALSE' }">
											<button id="btn" class="btn"> 적용 </button>
										</c:if>
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>				
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
			
			if(confirm('선택 하신 값이 맞습니까? '+ chkMsg)){
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
						}else{
							alert("정상적인 방법으로 수정을 시도하세요 .");
							location.href="./reportComment";
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
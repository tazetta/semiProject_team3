<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%String loginId = (String)request.getSession().getAttribute("loginId"); %>
<%String isManager = (String) request.getSession().getAttribute("isManager"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
<style>
	table{
		margin-left:18%;
		width:800px;	
	}
	table, th, td {
		border: 1px solid black;
		border-collapse: collapse;
		text-align: center;
		padding : 5px 10px;
	}
	#total{
		margin-left:10%;
		border:0px solid black;
		width :1200px;
	}
	button{
		width:100px;
		height: 30px;
	}
	#btn1{
		position: relative;
		top:35px;
		left: 18%;
	}
	#btn2{
		position: relative;
		left:67%;
		margin:5px;
	}
	.comment{
		position: relative;
		left:18%;
		font-size:18px;
		width:700px;
		height: 30px;
		margin-top: 5px;
		margin-bottom :5px;
	}
	#comm_regist,#comm_update{
		position: relative;
		left:18%;
	}
	p{
		text-align: center;
	}
	
</style>
</head>
<body>
	<jsp:include page="top.jsp" />
	<jsp:include page="navi.jsp" />
    	
		<div id="total">
			<div id="btn1">
				<c:if test="${dto.id==loginId || (isManager=='true'&& dto.id=='관리자')}">
				<button onclick="location.href='./boardUpdateForm?boardIdx=${dto.boardIdx}&id=${dto.id}&page=${currPage}'">수정</button>
				</c:if>
				<c:if test="${dto.id==loginId || isManager=='true'}">
				<button onclick="location.href='./boardDel?boardIdx=${dto.boardIdx}&id=${dto.id}&page=${currPage}'">삭제</button>
				</c:if>
			</div>
			<div id= "btn2">
				<c:if test="${dto.id!=loginId && (dto.isManager=='false' || dto.isManager == null)}">
				<button onclick="window.open('./boardReportForm?boardIdx=${dto.boardIdx}','신고','width=500px,height=500px,location=no,status=no,scrollbars=yes');">신고</button>
				</c:if>
				<button onclick="location.href='./boardList?&page=${currPage}'">목록</button>
			</div>
		<table>
			<tr>
				<th>작성자</th>
				<td>${dto.id}</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>${dto.subject}</td>
			</tr>
			<tr>
				<th>내용</th>
				<td>${dto.content}</td>
			</tr>
			<c:if test="${dto.newFileName ne null}">
			<tr>
				<th class="bbstable">첨부사진</th>
				<td class="bbstable">
					<a href="photo/${dto.newFileName}" target="_blank">${dto.oriFileName}</a>
					<br/>
					<img src="photo/${dto.newFileName}" alt="${dto.oriFileName}" width="500px"/>
				</td>
			</tr>			
			</c:if>
		</table>
		<input id="comment" class="comment" type="text" placeholder="댓글을 입력해주세요"/>
		<button id="comm_regist">등록</button>
		<c:if test="${commentUpdatedto.id ne null}">
			<input id ="comm_up" class="comment" type="text" value="${commentUpdatedto.content}"/>
			<button id="comm_update">수정</button>
		</c:if>
		<c:if test="${not empty list}">
		<c:forEach items="${list}" var="comment">
			<c:if test="${comment.deactivate eq 'TRUE' }">
			</c:if>
			<c:if test="${comment.deactivate eq 'FALSE' }">
				<table class ="comm_table">
					<tr>
						<td style="width:150px;">${comment.id}</td>
						<td>
							${comment.content}
							<c:if test="${comment.id==loginId}"><!-- 작성자만 버튼 보이게 -->
								<a href="commentUpdateForm?reIdx=${comment.reIdx}&id=${comment.id}&boardIdx=${dto.boardIdx}&page=${currPage}">수정</a>
							</c:if>
							<c:if test="${comment.id==loginId || isManager=='true'}">
								<a href="commentDel?reIdx=${comment.reIdx}&id=${comment.id}&boardIdx=${dto.boardIdx}&page=${currPage}">삭제</a>
							</c:if>
						</td>
						<td style="width:150px;">${comment.reg_date}</td>
						<c:if test="${comment.id!=loginId}">
						<td style="width:50px;">
							<input type="button" value="신고" onclick="window.open('./commReportForm?reIdx=${comment.reIdx}','신고','width=500px,height=500px,location=no,status=no,scrollbars=yes');"/>
						</td>
						</c:if>
					</tr>
				</table>			
			</c:if>
		</c:forEach>
		</c:if>
		<c:if test="${empty list}"><!-- 댓글이 없는경우 -->
			<p class ="comm_table">현재 댓글이 없습니다.</p>
		</c:if>
		</div>
</body>
<script>
	$('#comm_regist').click(function(){
		var comment = $('#comment').val();
		if(comment==""){
			alert("댓글내용을 입력해주세요");
			$('#comment').focus();
		}else{
			location.href='./commentWrite?comment='+comment+'&boardIdx=${dto.boardIdx}&page=${currPage}';			
		}
	});
	$('#comm_update').click(function(){
		var comment = $('#comm_up').val();
		if(comment==""){
			alert("댓글내용을 입력해주세요");
			$('#comm_up').focus();
		}else{
			location.href='./commentUpdate?comment='+comment+'&boardIdx=${dto.boardIdx}&reIdx=${commentUpdatedto.reIdx}&id=${commentUpdatedto.id}&page=${currPage}';			
		}
	});
	var msg="${msg}";
	if(msg!=""){
		alert(msg);
	}
</script>
</html>
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
		border: 1px solid gray;
		border-collapse: collapse;
		text-align: center;
		padding : 5px 10px;
	}
	#total{
		margin-left:10%;
		border:0px solid black;
		width :1200px;
		margin-top:60px;
	}
	button{
		width:100px;
		height: 30px;
	}
	#btn1{
		position: absolute;
		top:150px;
		left: 22%;
	}
	#btn2{
		position: absolute;
		left:53.5%;
		top:145px;
		margin:5px;		
	}
	#btn3{
		position: absolute;
		left:59.5%;
		top:145px;
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
	.comm_table{
		border: 1px solid lightgray;
	}
	.mouse_over:hover{
		font-weight: 600;
		color: blue;
	}
	#more{
		text-align: center;
		margin-top: 10px;
		font-size: 18px;
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
			<div>
				<c:if test="${dto.id!=loginId && (dto.isManager=='false' || dto.isManager == null)}">
				<button id= "btn2" onclick="window.open('./boardReportForm?boardIdx=${dto.boardIdx}','신고','width=500px,height=500px,location=no,status=no,scrollbars=yes');">신고</button>
				</c:if>
				<div id= "btn3">
				<c:if test="${boardkeyword eq null}">
				<button onclick="location.href='./boardList?&page=${currPage}'">목록</button>
				</c:if>
				<c:if test="${boardkeyword ne null}">
				<button onclick="location.href='./boardSearch?${url}&page=${currPage}'">목록</button>
				</c:if>
				</div>
			</div>
		<table>
			<tr>
				<th style="width: 70px;">작성자</th>
				<td>${dto.id}</td>
			</tr>
			<tr>
				<th style="width: 70px;">제목</th>
				<td>${dto.subject}</td>
			</tr>
			<tr style="height: 300px;">
				<th style="width: 70px;">내용</th>
				<td>${dto.content}</td>
			</tr>
			<c:if test="${dto.newFileName ne null}">
			<tr>
				<th>첨부사진</th>
				<td>
					<%-- <a href="photo/${dto.newFileName}" target="_blank">${dto.oriFileName}</a> --%>
					<!-- <br/> -->
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
		
				<table id="comment_table" class ="comm_table">
					<tr class ="comm_table">
						<td class ="comm_table" style="width:150px;">${comment.id}</td>
						<td class ="comm_table">
							${comment.content}
							<c:if test="${comment.id==loginId}"><!-- 작성자만 버튼 보이게 -->
								<a style="color:blue;" class="mouse_over" href="commentUpdateForm?reIdx=${comment.reIdx}&id=${comment.id}&boardIdx=${dto.boardIdx}&page=${currPage}">수정</a>
							</c:if>
							<c:if test="${comment.id==loginId || isManager=='true'}">
								<a style="color:blue;" class="mouse_over" href="commentDel?reIdx=${comment.reIdx}&id=${comment.id}&boardIdx=${dto.boardIdx}&page=${currPage}">삭제</a>
							</c:if>
						</td>
						<td class ="comm_table" style="width:150px;">${comment.reg_date}</td>
						<td class ="comm_table" style="width:50px;">
						<c:if test="${comment.id!=loginId}">
							<input type="button" value="신고" onclick="window.open('./commReportForm?reIdx=${comment.reIdx}','신고','width=500px,height=500px,location=no,status=no,scrollbars=yes');"/>
						</c:if>
						</td>
					</tr>
				</table>			
			
		</c:forEach>
		</c:if>
		<span id="test">
		</span>
			<div id="more">
         	<c:if test="${commcurrPage < maxPage}">
         		<a style="background-color: lightgray;" id = "more" class="mouse_over" href="./boardDetail?boardIdx=${dto.boardIdx}&page=${currPage}&commpage=${commcurrPage+1}">더보기</a>         		
		 	</c:if>
			</div>
		
		<c:if test="${empty list}"><!-- 댓글이 없는경우 -->
			<p>현재 댓글이 없습니다.</p>
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
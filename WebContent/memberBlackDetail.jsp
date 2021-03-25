<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>블랙리스트 회원정보 상세보기</title>
<style>
#mdetail{
	/* width: 1300px; */
	width : 60%;
	height : 300px;
	float: right;
	margin-right: 10%;
	margin-top : 4%;
}

.memberList_de {
	border-collapse: collapse;
	margin-top: 60px;
	width : 102%;
}

.memberList_de th, td {
	border: 1px solid lightgray;
	text-align: center;
	font-size: 13px;
}

.memberList_de th{
	padding: 8px;
	background-color: gray;
	color : white;
}

#caption{
	font-weight: 600;
}

.black {
	width: 250px;
	margin: auto;
	padding-top: 65px;
}

.mbtn{
	font-size: 13px;
	padding: 6px 15px;
	margin: 6px 0;
}

.blackmem{
	background-color: lightgray;
	font-weight: bold;
}

#reason{
	border-collapse: collapse;
	margin-top: 20px;
	width : 54%;
}

#reason th, td {
	border: 1px solid lightgray;
	text-align: center;
	font-size: 13px;
}

#reason th{
	padding: 8px;
}

#reason td{
	padding: 8px;
}

</style>

</head>
<body>
	<!--상단페이지-->
	<jsp:include page="top.jsp" />

	<!--상단네비-->
	<jsp:include page="navi_manager.jsp" />

	<!-- 사이드 네비&검색필터 -->
	<jsp:include page="side_manager.jsp" />


		<div id="mdetail">
			<table class=memberList_de>
			<h5 id="cate">블랙리스트 회원</h5>
			  <h3 id="caption">[${dto.id}] 님의 상세 정보</h3>
			  <hr/>
			<tr>
                <th>블랙리스트 등록일</th>
				<th>등록 관리자</th>                
                <th>아이디</th>
                <th>이름</th>
                <th>블랙리스트로 등록된 횟수</th>
                <th>글,댓글 신고수</th>
                <th>블랙리스트 상태</th>
                <th>마지막 수정일</th>
            </tr>
            <tr>
                <td>${dto.reg_date}</td>
                <td>${dto.managerid}</td>
                <td>${dto.id}</td>
                <td>${dto.name}</td>
                <td>${dto.blackcnt}</td>
                <td>${dto.reportcnt}</td>
                <td>${dto.blackstatus}</td>
                <td>${dto.update_date}</td>           
            </tr>
			</table>
			
			<table id="reason">
			<tr>
				<th>블랙리스트 등록일</th>
				<th>블랙리스트 등록사유</th>
			</tr>
			<c:forEach items="${reason}" var="black">
			<tr>
				<td>${black.reg_date}</td>
				<td>${black.reason}</td>
			</tr>
			</c:forEach>
			</table>
			
				<div class="black">
				<c:if test="${dto.blackstatus ne 'FALSE'}">
                	<button class="mbtn" onclick="location.href='./memberBlackDel?blackidx=${dto.blackidx}'">블랙리스트 삭제</button>
                	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  				</c:if>
                	<button class="mbtn" onclick="location.href='./memberBlackList'">닫기</button>
				</div>
		</div>
</body>
<script>
	var msg = "${msg}";
	if(msg!=""){
		alert(msg);
	}	
</script>
</html>
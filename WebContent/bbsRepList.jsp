<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
		<style>		
		#page {
			text-align: center;
		}
		#click{
			color: blue;
			font-weight: bold;
		}		
		.bssRep ,.repList{
			background-color: lightgray;
			font-weight: bold;
		}
		</style>
	</head>
<body>
	<jsp:include page="top.jsp" />
	<jsp:include page="navi_manager.jsp" />
	<div class="mid">
		<jsp:include page="side_repList.jsp" />
		<br/>
		<!-- <a href="./reportBBS">미처리 내역 </a> / <a href="./reportBBS?deactivate=TRUE">처리 내역 </a> -->
		<br/>
		<table class="body">
			<tr class="nowrap">
				<td style="border-color: white;border-bottom-color: lightgray;" colspan="6">
					<select id="pros" onchange=processing()>
						<option value="A"  ${deactivate eq 'FALSE' ? 'selected="selected"' : '' }>미처리 내역</option>
						<option value="B"${deactivate eq 'TRUE' ? 'selected="selected"' : '' }>처리 내역</option>
					</select>
				</td>
			</tr>
			<tr class="nowrap">
				<th>신고 no.</th>
				<th>신고자</th>
				<th>신고 당한 ID</th>
				<th style="width: 30%; min-width: 150px;">신고 사유</th>
				<th>블라인드 여부</th>
				<th>처리자</th>
			</tr>
			<c:forEach items="${list }" var="rep">
				<tr>
					<td>
						<a  id="click" href="./repDetail?boardIdx=${rep.boardIdx }&bbsRepIdx=${rep.bbsRepIdx}&page=${currPage}">${rep.bbsRepIdx }</a>
					</td>
					<td>${rep.rid }</td>
					<td>${rep.id }</td>
					<td>${rep.reason }</td>
					<c:if test="${rep.deactivate eq 'FALSE' }">
						<td>N</td>
					</c:if>
					<c:if test="${rep.deactivate eq 'TRUE' }">
						<td>Y</td>
					</c:if>
					<td>${rep.managerId }</td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="5" style="border: 1px solid white;"><c:if
						test="${list eq '[]'}">
						<p style="text-align: center;">신고 된 게시글이 없습니다.</p>
					</c:if>
					<div id="page">
						<span>
							<c:if test="${currPage==1}"></c:if> <c:if test="${currPage>1}">
								<a href='./reportBBS?page=${currPage-1}&deactivate=${deactivate}'>이전</a>
							</c:if>
						</span>
						<span>${currPage}</span>
						<span>
							<c:if test="${currPage == maxPage}"></c:if>
							<c:if  test="${currPage < maxPage}">
								<a href="./reportBBS?page=${currPage+1}&deactivate=${deactivate}">다음</a>
							</c:if>
						</span>
					</div>
				</td>
			</tr>
		</table>
	</div>
</body>
<script>
	function processing() {
		var pros = $('#pros').val();
		console.log(pros);
		if(pros=='A'){
			location.href="./reportBBS";
		}else{
			location.href="./reportBBS?deactivate=TRUE";
		}
	}
</script>
</html>
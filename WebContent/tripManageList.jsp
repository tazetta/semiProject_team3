<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 페이지 - 메인</title>
<link rel="stylesheet" type="text/css" href="basic.css">
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
</head>
<style>
.tripList{
	background-color: lightgray;
}
.header{
				background-color: gray;
				color: white;
			}
</style>
<body>
	<jsp:include page="top.jsp" />
	<jsp:include page="navi_manager.jsp" /> 
	<div class="tripBody">
		<jsp:include page="side_tripList.jsp"/>

		<div id='tripSearchBar'>
			<form action="tripSearch" method="GET">
				<select name="searchType">
					<option value="title">여행지 이름</option>
					<option value="address">여행지 위치</option>
				</select> <input type="text" name="keyword"> <input type="submit"
					value="검색"> <input type="checkbox" name="deactivate"
					value="TRUE" />비활성화 여부
				<button type="button"
					onclick="location.href='tripDeactivateFilter?${isDeactivate}'">비활성화된
					게시물만 보기</button>
			</form>
		</div>

		<div>
			<c:choose>
				<c:when test="${tripList ne '[]'}">
					<table class="midBody">
						<tr class="header"'>
							<th>contentID</th>
							<th>여행지 이름</th>
							<th>등록 날짜</th>
							<th>비활성화 여부</th>
						</tr>
						<c:forEach items="${tripList}" var="trip">
							<tr>
								<td>${trip.contentId}</td>
								<td class='title'><a
									href="./tripManageDetail?contentId=${trip.contentId}&page=${currPage}">${trip.title}</a></td>
								<td class="date">${trip.reg_date}</td>
								<td><c:if test="${trip.deactivate eq true}">
							Y
						</c:if> <c:if test="${trip.deactivate eq false}">
							N
						</c:if></td>
							</tr>
						</c:forEach>
					</table>
				</c:when>
				<c:otherwise>
					<div id="noneResult">"${keyword}"에 대한 검색 결과가 없습니다.</div>
				</c:otherwise>
			</c:choose>
		</div>
		<c:if test="${tripList ne '[]'}">
		<div class="pageArea">
			<span> <c:if test="${currPage == 1}">이전</c:if> <c:if
					test="${currPage > 1}">
					<c:choose>
						<c:when test="${keyword eq null && deactivate eq 'FALSE'}">
							<a href="./tripManageList?page=${currPage-1}">이전</a>
						</c:when>
						<c:when test="${keyword ne null}">
							<a href="./tripSearch?${url}&page=${currPage-1}">이전</a>
						</c:when>
						<c:when test="${deactivate eq 'TRUE'}">
							<a href="./tripDeactivateFilter?page=${currPage-1}">이전</a>
						</c:when>
					</c:choose>
				</c:if>
			</span> <span id="page"> ${currPage} </span> <span> <c:if
					test="${currPage == maxPage}">다음</c:if> <c:if
					test="${currPage < maxPage}">
					<c:choose>
						<c:when test="${keyword eq null && deactivate eq 'FALSE'}">
							<a href="./tripManageList?page=${currPage+1}">다음</a>
						</c:when>
						<c:when test="${keyword  ne null}">
							<a href="./tripSearch?${url}&page=${currPage+1}">다음</a>
						</c:when>
						<c:when test="${deactivate eq 'TRUE'}">
							<a href="./tripDeactivateFilter?page=${currPage+1}">다음</a>
						</c:when>
					</c:choose>
				</c:if>
			</span> <span>${currPage}/${maxPage}</span>
		</div>
		</c:if>
	</div>
</body>
<script>
	/* $(document).ready(function() {
		$("div#"+${tripNav}).css({"background-color" : "lightgray","font-weight":'600'});
		console.log(sessionStorage.getItem("url"));
	}); */
	
 	$('.list').hover(function(){
		   $(this).css({'font-weight':'600'});
	},function(){
		    $(this).css({'font-weight':'1'});
	});
</script>
</html>
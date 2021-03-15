<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <!-- c태그 사용위해 불러옴 -->
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>마이페이지 - 가봤어요</title>
 	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script> <!-- JQuery사용 위해 불러옴 -->
 	<style>
 		body {
	min-width: 1400px;
}

/*좌측 카테고리*/
table, tr, td {
	border: 1px solid lightgray;
	border-collapse: collapse;
	text-align: center;
	padding: 20px;
}

section#left {
	position: relative;
	float: left;
	margin-left: 10px;
	padding: 10px;
}

.menuHover {
	font-weight: 600;
}

/*콘텐츠*/
#content {
	background-color: #F2F2F2;
	text-align: center;
	position: relative;
	top: 0px;
	left: 20px;
	float: left;
	margin: 10px;
	width: 1200px;
	height: 1200px;
	/* flex-direction:column; */
}

span {
	position: relative;
	top: 50px;
	font-weight: 600;
}

table#visitedList {
	background-color: white;
	text-align: center;
	position: relative;
	top: 80px;
	margin: 0 auto;
	width: 900px;
}

.noneList {
	position: relative;
	top: 150px;
	height: 60px;
	text-align: center;
	align-items: stretch;
	background-color: transParent;
}
/*페이징*/
.pageArea {
	text-align: center;
	position: absolute;
	top: 50%;
	left: 50%;
}

.pageArea span {
	font-size: 16px;
	border: 1px solid lightgray;
	background-color: lightgray;
	padding: 2px 10px;
}

a {
	text-decoration: none;
}

#page {
	font-weight: 600;
	border: none;
	background-color: transparent;
}
 	</style>
	</head>
	<body>
	<jsp:include page="top.jsp" />
	<jsp:include page="navi.jsp" />

	<section id="left">
		<div>
			<table>
				<tr>
					<td class="menu"><a href="profile">사용자 정보</a></td>
				</tr>
				<tr>
					<td class="menu"><a href="wroteList">내가 쓴 글 보기</a></td>
				</tr>
				<tr>
					<td class="menu"><a href="visitedList">가봤어요</a></td>
				</tr>
				<tr>
					<td class="menu"><a href="bookmarkList">즐겨찾기</a></td>
				</tr>
			</table>
		</div>
	</section>
	
	<section id=background>
		<div id="content">
			<span>내가 가 본 여행지</span>
			<c:if test="${list eq '[]'}">
				<div class="noneList">
					<p>가봤어요에 등록된 여행지가 없습니다</p>
				</div>
			</c:if>
			<table id="visitedList">

				<c:forEach items="${list}" var="bm">
					<tr>
						<th style="font-size: 150%"  colspan="3">${bm.title }</th>
					</tr>
					<tr>
						<td rowspan="2"><img src="${bm.firstImage}" width="200px" height="200px" /></td>
						<td class="overview" rowspan="2">${bm.overview}</td>
						<td style="width:100px">${bm.reg_date }</td>
						
					</tr>
					<tr>
						<td>
							<button onclick="location.href='#'">삭제</button>
						</td>
					</tr>
				</c:forEach>
			</table>
			<c:if test="${list ne '[]'}">
				<div class="pageArea">
					<span> <c:if test="${currPage==1}">이전</c:if> <c:if
							test="${currPage>1}">
							<a href="?page=${currPage-1}">이전</a>
						</c:if>
					</span> <span id="page">${currPage}</span> <span> <c:if
							test="${currPage==maxPage}">다음</c:if> <c:if
							test="${currPage<maxPage}">
							<a href="?page=${currPage+1}">다음</a>
						</c:if>
					</span>
				</div>
			</c:if>
		</div>
	</section>
	</body>
	<script>
	// 말줄임 기능
	/* $('.overview').each(function(){
	    var lentgh = 20; //글자수
	      if($(this).text().length >= length){
	        $(this).html($(this).text().substr(0,length)+'...');
				console.log($(this).text().length);
	      }
	  }); */
	</script>
</html>
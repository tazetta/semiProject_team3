<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>테마별</title>
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
<style>
body {
	min-width: 1400px;
}

table, tr, td {
	border: 1px solid lightgray;
	border-collapse: collapse;
	text-align: center;
	padding: 20px;
}
#content {
	background-color: #F2F2F2;
	text-align: center;
	position: relative;
	top: 70px;
	left: 170px;
	float: left;
	margin: 10px;
	width: 80%;
	height: 80%;
	/* flex-direction:column; */
}
span {
	position: relative;
	top: 50px;
	font-weight: 600;
}

.pageArea {
	width: 100%;
	text-align: center;
	margin: 10px;
}

.pageArea span {
	font-size: 16px;
	border: 1px solid lightgray;
	background-color: lightgray;
	padding: 2px 10px;
}

.text {
	text-align: left;
	margin-left: 2.5%;
	margin-right: 2.5%;
	width: 95%;
	height: auto;
	text-overflow: ellipsis;
	overflow: hidden;
	display: -webkit-box;
	-webkit-box-orient: vertical;
	-webkit-line-clamp: 2;
}

.bottom {
	position: relative;
	bottom: 0px;
	float: right;
}

#list {
	/*background-color: #FFFFFF;*/
	margin-top: 1%;
	margin-right: 1%;
	width: 80%;
	float: left;
	overflow: hidden;
}

#page {
	font-weight: 600;
	border: none;
	background-color: transparent;
}

a {
	text-decoration: none;
}

button {
	padding: 20px 20px;
}

#btn {
	position: absolute;
	top: -2%;
	right: 0%;
}
</style>
</head>
<body>
	<jsp:include page="top.jsp" />
	<jsp:include page="navi.jsp" />

	<section id=background>
		<div id="content">
			<div id="btn">
				<button
					onclick="location.href='./search?keyword=${keyword}&searchType=${searchType}&alignType=bookmarkCnt&deactivate=FALSE'">인기순</button>
				<button
					onclick="location.href='./search?keyword=${keyword}&searchType=${searchType}&alignType=reg_date&deactivate=FALSE'">최신순</button>
			</div>

			<c:forEach items="${list}" var="result">
				<table>
					<tr>
						<th colspan="3" style="font-size: 150%">						
							<a href="./tripDetail?contentId=${result.contentId}&page=${currPage}"
							target=window.open()>${result.title}</a>
						</th>
					</tr>
					<tr>
						<td id="user" rowspan="2">
							<div>
								<a href="tripDetail?contentId=${result.contentId}"
									target=window.open()> <img src="${result.firstImage}"
									width="300px" height="200px"></a>
							</div>
						</td>
						<td colspan="2" id="text">
							<div class="ellipsis">${result.overview}</div>
						</td>
					</tr>
					<tr>
						<th class="title">${result.address}</th>
					</tr>
				</table>
			</c:forEach>
	<div class="pageArea">
		<span> <c:if test="${currPage == 1}">이전</c:if> <c:if
				test="${currPage > 1}">
				<a href="./search?${url}&page=${currPage-1}">이전</a>
			</c:if>
		</span> <span id="page"> ${currPage} </span> <span> <c:if
				test="${currPage == maxPage}">다음</c:if> <c:if
				test="${currPage < maxPage}">
				<a href="./search?${url}&page=${currPage+1}">다음</a>
			</c:if>
		</span> <span>${currPage}/${maxPage}</span>
	</div>
		</div>
	</section>
</body>
<script>
	// 말줄임 기능
	$('.ellipsis').each(function() {
		var length = 200; //글자수
		$(this).each(function() {

			if ($(this).text().length >= length) {
				$(this).text($(this).text().substr(0, length) + '...');
			}

		});
	});
</script>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- c태그 사용위해 불러옴 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지 - 가봤어요</title>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<!-- JQuery사용 위해 불러옴 -->
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
	width: 80%;
	height: 80%;
	/* flex-direction:column; */
}

span {
	position: relative;
	top: 50px;
	font-weight: 600;
}

/* table#visitedList {
	background-color: white;
	text-align: center;
	position: relative;
	top: 80px;
	margin: 0 auto;
	width: 900px;
} */
.noneList {
	position: relative;
	top: 150px;
	height: 60px;
	text-align: center;
	align-items: stretch;
	background-color: transParent;
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
	background-color: #FFFFFF;
	margin-top: 1%;
	margin-right: 1%;
	width: 80%;
	float: left;
	overflow: hidden;
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

	<c:forEach items="${list}" var="bm">
		<div id="list">
			<table>
				<tr>
					<th colspan="3" style="font-size: 150%">${bm.title }</th>
				</tr>
				<tr>
					<td id="user" rowspan="2">
						<div>
							<a href="tripDetail?contentId=${bm.contentid}"
								target=window.open()> <img src="${bm.firstimage}"
								width="300px" height="200px"></a>
						</div>
					</td>
					<td colspan="2" id="text">
						<div class="ellipsis">${bm.overview}</div>
					</td>
				</tr>
				<tr>
					<td class="bottom"><a
						href="./bookmarkUpdate?myidx=${bm.myidx}&deact=${bm.deactivate}&conIdx=${bm.contentid}&type=${bm.type}"
						target="_blanck">삭제</a></td>
					<td class="bottom" colspan="2">${bm.reg_date }</td>
				</tr>

			</table>
		</div>
	</c:forEach>

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
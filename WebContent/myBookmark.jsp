<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <!-- c태그 사용위해 불러옴 -->
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>마이페이지 - 즐겨찾기</title>
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
	width: 80%;
/* 	height: 500px; */
	/* flex-direction:column; */
}

#content span {
	position: relative;
	top: 50px;
	left:0px;
	font-weight: 600;
}

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
}
.bottom{
position:relative;
bottom:0px;
float:right;
}

.bottom a{
	background-color: lightgray;
	padding:5px;
}

#list{ 
position:relative;
top:20px;
left:60px;
background-color: #FFFFFF;
    margin-top:30px;

    width: 60%;
    float: left;
    overflow: hidden;
}

#list table, #list tr, #list th,#list td{
padding:5px 10px;
border:none;
}
/*페이징*/
.pageArea {
	text-align: center;
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
			<span>가보고 싶은 여행지</span>
			<c:if test="${list eq '[]'}">
				<div class="noneList">
					<p>즐겨찾기에 등록된 여행지가 없습니다</p>
				</div>
			</c:if>
				
					<c:forEach items="${list}" var="bm">
				<div id="list">
					<table >
						<tr>
							<th colspan="3" style="font-size:150%">${bm.title }</th>
						</tr>
						<tr>
							<td id="trip" rowspan="2">
								<div>
									<a href="./tripDetail?contentId=${bm.contentid}" target=window.open()><img src="${bm.firstimage}" width="250px" height="150px">
									</a>
								</div>
							</td>
							<td colspan="2" id="text">
								<div class="ellipsis">${bm.overview}</div>
							</td>
						</tr>
						<tr>
							<td class="bottom">
							<a href="bookmarkUpdate?myidx=${bm.myidx}&type=${bm.type}&deact=${bm.deactivate}&conIdx=${bm.contentid}" target="_blanck">삭제</a></td>
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
	
	var msg = "${msg}";
	if (msg != "") {
		alert(msg);
	}
	
	// 말줄임 기능
	$('.ellipsis').each(function(){
	    var length = 100; //글자수
	    $(this).each(function(){
	    	
	      if($(this).text().length >= length){
	        $(this).text($(this).text().substr(0,length)+'...');
	      }
	    	
	    });
	  }); 
	
	  $(".menu").hover(function () {
	        $(this).toggleClass("menuHover");
	    });
	    $(".menu").click(function () {
	        $(this).css({ "background-color": "#F5D0A9", "font-weight": "600" });
	    })
	</script>
</html>
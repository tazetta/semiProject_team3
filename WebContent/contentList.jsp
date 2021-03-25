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
div.contentList {
	position: absolute;
	top: 20%;
}

div.content {
	padding: 0px 15px;
	border: 1px solid black;
	width: 120px;
	height: 30px;
	text-align: center;
}

div.clear {
	clear: left;
	border: 1px solid black;
}

div.areaList>div {
	float: left;
	border: 1px solid black;
	padding: 5px 5px;
	width: 140px;
}

div.areaList {
	position: absolute;
	left: 25%;
	top: 15%;
}

a:link {
	color: black;
	text-decoration: none;
}

a:visited {
	color: black;
	text-decoration: none;
}
div.chkBtn{
	position: absolute;
	top:30%;
	right:53%;
}
.btn{
    border:#BDBDBD ;
    background-color:#D8D8D8;
    font-weight: 600;
	padding:20px 40px;
}
</style>
</head>
<body>
	<jsp:include page="top.jsp" />
	<jsp:include page="navi.jsp" />
	<div class="contentList">
		<c:forEach items="${contentList}" var="content">
			<div class="content" id="${content.contentCode}">
				<a href="./themeContentList?nav=${content.contentCode}">${content.name}</a>
			</div>
		</c:forEach>
	</div>

	<form action="resultList" method="get">
		<div class="areaList">
			<c:forEach items="${areaList}" var="area" varStatus="status">
				<c:if test="${status.index % 5 == 0}">
					<div class="clear">
						<input type="checkbox" name="local" value="${area.areaCode}">${area.name}
					</div>
				</c:if>
				<c:if test="${status.index % 5 != 0}">
					<div>
						<input type="checkbox" name="local" value="${area.areaCode}">${area.name}
					</div>
				</c:if>
			</c:forEach>
		</div>
		<input type="hidden" name="nav" value="${nav}" />
		<input type="hidden" name="type" value="theme" />
		<div class = "chkBtn">
			<input class="btn" type="button" onclick="maxChkBox()" value="검색" />
		</div>
	</form>
</body>
<script>
	$(document).ready(function() {
		$("div#"+${nav}).css({"background-color" : "lightgray","font-weight":"600"});
	});
	   
	$('a').hover(function(){
		   $(this).css({'font-weight':'600'});
	},function(){
		    $(this).css({'font-weight':'1'});
	});
	
	function maxChkBox(){
		var cnt = 0;
		$('input[type="checkbox"]').each(function(idx, item){
			
			if($(this)[0].checked){
					cnt++;
				}
		});

		if(cnt == 0){
			alert('하나 이상을 선택해 주세요.');
		} else{
			$('form').submit();
		}
	}
</script>
</html>
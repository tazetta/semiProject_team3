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
	float: left;
	margin-left: 3%;
	border: 1px solid lightgray;

}


div.content {
	padding: 3px 15px;
	/* border: 1px solid black; */
	width: 150px;
	height: 30px;
	text-align: center;	
}

div.clear {
	clear: left;
	/* border: 1px solid black; */
}

div.areaList>div {
	float: left;
	/* border: 1px solid black; */
	padding: 5px 5px;
	width: 140px;
}

div.areaList {

}

/*  a:link {
	color: black;
	text-decoration: none;
}

a:visited {
	color: black;
	text-decoration: none;
} 
 */
div.chkBtn{
	text-align: center;
/* 	margin-top: 15px; */
	margin:0 auto;
}
.btn{
    border:#BDBDBD ;
    background-color:#D8D8D8;
    font-weight: 600;
	padding:10px 10px;
	margin:3px;
}
.mid{
	min-width: 600px;
	width: 800px;
	margin: 0 auto;
}
.areabody{
	margin-top: 25px;
	width: 100%;
	min-width: 1000px;
}
</style>
</head>
<body>
   	<jsp:include page="top.jsp" />
	<jsp:include page="navi.jsp" />
	
	<div class="areabody">
	<div class="contentList">
		<c:forEach items="${contentList}" var="content">
			<div class="content" id="${content.contentCode}">
				<a href="./themeContentList?nav=${content.contentCode}">${content.name}</a>
			</div>
		</c:forEach>
	</div>


<!-- 	<form action="resultList" method="get" onsubmit="return false"> -->
	<form action="resultList" method="get">
		<table class="mid">
			<tr>
				<td>
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
				</td>
			</tr>
			<tr>
				<td>
					<div class = "chkBtn">
						<input type="button" id="allBtn" class="btn" onclick="allChk()" value="전체 선택"/>
						<input type="button" class="btn" onclick="minChkBox()" value="검색" />
					</div>
				</td>
			</tr>
		</table>
		
		<input type="hidden" name="nav" value="${nav}" />
		<input type="hidden" name="type" value="theme" />
		
	</form>
	</div>
</body>
<script>
	$(document).ready(function() {
		$("div#"+${nav}).css({"background-color" : "lightgray","font-weight":"600"});
	});
	   

	function minChkBox(){
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
	var isChecked = false;
	function allChk(){
		if(isChecked == false) {
			$("input[name=local]").prop("checked",true);		
			$("#allBtn").val("전체해제");
			isChecked = true;
		} else{
			$("input[name=local]").prop("checked",false);		
			$("#allBtn").val("전체선택");
			isChecked = false;
		}
	}
</script>
</html>
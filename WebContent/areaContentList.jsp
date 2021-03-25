<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>지역별</title>
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
<style>
div.areaList {
	float: left;
	margin-left: 3%;
}

div.area {
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

div.cityList>div {
	float: left;
	border: 1px solid black;
	padding: 5px 5px;
	width: 140px;
}

div.cityList {

}

/* a:link {
	color: black;
	text-decoration: none;
}

a:visited {
	color: black;
	text-decoration: none;
} */

div.chkBtn{
	text-align: center;
	margin-top: 15px;
}
.btn{
    border:#BDBDBD ;
    background-color:#D8D8D8;
    font-weight: 600;
	padding:20px 40px;
}
.mid{
	min-width: 600px;
	width: 800px;
	margin-left:20%;
}
.areabody{
	margin-top: 25px;
	width: 100%;
	min-width: 1000px;
	margin-left: 5%;
}
</style>
</head>
<body>
	<jsp:include page="top.jsp" />
	<jsp:include page="navi.jsp" />
	<div class="areabody">
	<div class="areaList">
		<c:forEach items="${areaList}" var="area">
			<div class="area" id="${area.areaCode}">
				<a href="./areaContentList?nav=${area.areaCode}">${area.name}</a>
			</div>
		</c:forEach>
	</div>

	<form action="resultList" method="GET">
	<table class="mid">
		<tr>
			<td>
				<div class="cityList">
					<c:forEach items="${cityList}" var="city" varStatus="status">
						<c:if test="${status.index % 5 == 0}">
							<div class="clear">
								<input type="checkbox" name="local" value="${city.cityCode}">${city.name}
							</div>
						</c:if>
						<c:if test="${status.index % 5 != 0}">
							<div>
								<input type="checkbox" name="local" value="${city.cityCode}">${city.name}
							</div>
						</c:if>
					</c:forEach>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div class = "chkBtn">
					<input type="button" class="btn" id="allBtn" onclick="allChk()" value="전체 선택"/>	
					<input class="btn" type="button" onclick="maxChkBox()" value="검색" />
				</div>
			</td>
		</tr>		
	</table>
		<input type="hidden" name="nav" value="${nav}" /> 
		<input type="hidden" name="type" value="area" /> 
	</form>
	</div>
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
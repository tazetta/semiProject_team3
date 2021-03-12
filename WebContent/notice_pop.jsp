<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>팝업 노출</title>
<script>
	function setCookie(name, value, expirehours) {
		var todayDate = new Date();
		todayDate.setHours(todayDate.getHours() + expirehours);
		document.cookie = name + "=" + escape(value) + "; path=/; expires="
				+ todayDate.toGMTString() + ";"
	}

	function TodayPopup() {
		setCookie("ncookie", "done", 24);
		document.getElementById('popup').style.visibility = "hidden";
	}

	function closePopup() {
		document.getElementById('popup').style.visibility = "hidden";
	}
</script>
<style>
#popup {
	background-color: rgba(194, 194, 221, 0.5);
	width: 100%;
	height: 100%;
	position: absolute;
	z-index: 10;
}

#popupMain {
	width: 40%;
	height: 50%;
	background-color: white;
	margin-top: 5%;
	margin-left: 30%;
	opacity: 1;
	background-color: white;
	text-align: center;
}

b {
	font-weight: bold;
	color: black;
}

.close {
	justify-content: center;
	background-color: rgb(233, 176, 176);
}
</style>
</head>
<body>
<c:if test="${dto.subject ne null }">
	<div id="popup">
		<div id="popupMain">
			<div style="height:100%;">
				<br />
				<h3>${dto.subject}</h3>
				<br />
				<hr />
				<br /> <span>${dto.content}</span>

			</div>
			<div class="close">
				<a href="#" onclick="TodayPopup()"><b>오늘 하루동안 열지않기</b></a>
				<button onclick="closePopup()" />
				닫기
				</button>
			</div>
		</div>
	</div>
</c:if>
</body>
<script>
	cookiedata = document.cookie;
	if (cookiedata.indexOf("ncookie=done") < 0) {
		document.getElementById('popup').style.visibility = "visible";
	} else {
		document.getElementById('popup').style.visibility = "hidden";
	}
</script>
</html>
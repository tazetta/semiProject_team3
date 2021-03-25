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
	background-color: rgba(0, 0, 0, 0.5);
	width: 100%;
	height: 106%;
	position: absolute;
	top: 0;
	left: 0;
	z-index: 999;
}

#popupMain {
	width: 30%;
	height: 340px;
	background-color: white;
	margin-top: 5%;
	margin-left: 30%;
	opacity: 1;
	background-color: white;
	text-align: center;
	border: 8px solid #3571B5;
}

b {
	font-weight: bold;
	color: black;
}

#close {
	background-color: white;
	vertical-align: bottom;
	margin-top: 19%;
}

.closebtn {
	border: 1px solid #304a8a;
	background-color: #3f5a9d;
	color: white;
	padding: 0 14px;
	font-size: 14px;
	height: 25px;
	float: right;
	margin: 0 3%;
}

hr {
	width: 80%;
	margin: auto;
}

#close a {
	margin-left: 40%;
	padding: 3px;
}

.closebtn:hover {
	text-decoration: underline;
}

#close a:hover {
	text-decoration: underline;
}
</style>
</head>
<body>
	<c:if test="${dto.subject ne null }">
		<div id="popup">
			<div id="popupMain">
				<h3 style="padding: 40px 0 20px;">${dto.subject}</h3>
				<hr />
				<div style="padding: 20px;">${dto.content}</div>

			<div id="close">
				<a href="#" onclick="TodayPopup()"><b>오늘 하루동안 열지않기</b></a>
				<button class="closebtn" onclick="closePopup()" />
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
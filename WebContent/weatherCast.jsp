<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
</head>
<body>
	<select id="area" name="area" onchange='areaList(value)'>
		<option value="서울">서울</option>
		<option value="부산">부산</option>
		<option value="대구">대구</option>
		<option value="인천">인천</option>
		<option value="광주">광주</option>
		<option value="대전">대전</option>
		<option value="울산">울산</option>
		<option value="세종특별자치시">세종특별자치시</option>
		<option value="경기도">경기도</option>
		<option value="강원도">강원도</option>
		<option value="충청북도">충청북도</option>
		<option value="충청남도">충청남도</option>
		<option value="전라북도">전라북도</option>
		<option value="전라남도">전라남도</option>
		<option value="경상북도">경상북도</option>
		<option value="경상남도">경상남도</option>
		<option value="제주도">제주도</option>
	</select>
</body>

<div>
<p>오늘</p>
	<p id="POP"></p>
	<p id="PTY"></p>
	<p id="R06"></p>
	<p id="REH"></p>
	<p id="SKY"></p>
	<p id="TMN"></p>
	<p id="TMX"></p>
</div>
<div>
<p>내일</p>
	<p id="POP"></p>
	<p id="PTY"></p>
	<p id="R06"></p>
	<p id="REH"></p>
	<p id="SKY"></p>
	<p id="TMN"></p>
	<p id="TMX"></p>
</div>
<div>
<p>모레</p>
	<p id="POP"></p>
	<p id="PTY"></p>
	<p id="R06"></p>
	<p id="REH"></p>
	<p id="SKY"></p>
	<p id="TMN"></p>
	<p id="TMX"></p>
</div>

<script>
	function areaList(value) {
		var $area = $("#area");
		
		$.ajax({
			type : 'GET',
			url : 'mainWeatherCast',
			data : {"area" : $area.val() },
			dataType : 'JSON',
			success : function(obj) {
				console.log(obj.list);
				console.log(obj.list[0].POP);
				console.log('today : ' + obj.today);
				console.log('tomorrow : ' + obj.tomorrow);
				console.log('afterTomorrow : ' + obj.afterTomorrow);
				$('#POP').html(obj.list[0].POP);
				$('#PTY').html(obj.list[1].PTY);
				$('#REH').html(obj.list[2].REH);
				$('#SKY').html(obj.list[3].SKY);
				$('#TMX').html(obj.list[5].TMX);
			},
			error : function(e) {
				console.log(e);
			}
		})
	}	
</script>
</html>
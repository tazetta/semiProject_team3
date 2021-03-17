<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>날씨 테스트</title>
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
<style>
	#div1{
		border: 1px solid black;
        border-collapse: collapse;
        padding: 5px 10px;
        width: 300px;
	}
	#div2{
		border: 1px solid black;
        border-collapse: collapse;
        padding: 5px 10px;
        width: 300px;    
	}
	#div3{
		border: 1px solid black;
        border-collapse: collapse;
        padding: 5px 10px;
        width: 300px;
	}
	
	img {
		width: 50px;
		height: 50px;
	}
</style>
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
<div id="div1">
<h3>오늘</h3>
	<p id="POP0"></p>
	<p id="PTY0"></p>
	<p id="REH0"></p>
	<p id="SKY0"></p>
	<p id="TMX0"></p>
</div>
<div id="div2">
<h3>내일</h3>
	<p id="POP1"></p>
	<p id="PTY1"></p>
	<p id="REH1"></p>
	<p id="SKY1"></p>
	<p id="TMX1"></p>
</div>
<div id="div3">
<h3>모레</h3>
	<p id="POP2"></p>
	<p id="PTY2"></p>
	<p id="REH2"></p>
	<p id="SKY2"></p>
	<p id="TMX2"></p>
</div>

<script>
$(document).ready(function() {
	$("select option[value='서울']").attr("selected",true);
	var $area = $("#area");
	$.ajax({
		type : 'GET',
		url : 'mainWeatherCast',
		data : {
			"area" : $area.val()
		},
		dataType : 'JSON',
		success : function(obj) {
			classify(obj);
		},
		error : function(e) {
			console.log(e);
		}
	})
});

	function areaList(value) {
		var $area = $("#area");

		$.ajax({
			type : 'GET',
			url : 'mainWeatherCast',
			data : {
				"area" : $area.val()
			},
			dataType : 'JSON',
			success : function(obj) {
				classify(obj);
			},
			error : function(e) {
				console.log(e);
			}
		})
	}
	
	function classify(obj){
		for(var i = 0; i < obj.list.length; i++) {
			if(obj.list[i].fcstDate == obj.today) {
				drawWeatherCast(obj, 0, 0);
			} else if(obj.list[i].fcstDate == obj.tomorrow) {
				drawWeatherCast(obj, 1, 5);
			} else{
				drawWeatherCast(obj, 2, 10);
			}
		}
	}
	
	function drawWeatherCast(obj, idVar, index) {
		$('#POP'+idVar).html("강수 확률 : " + obj.list[0+index].POP);
		$('#PTY'+idVar).html("강수 형태 : " + obj.list[1+index].PTY);
		$('#REH'+idVar).html("습도 : " + obj.list[2+index].REH);
        $('#SKY'+idVar).html("<img src='"+skyState(obj.list[3+index].SKY)+"'/>");
		$('#TMX'+idVar).html("낮 최고기온 : " + obj.list[4+index].TMX);
	}
	
	function skyState(sky){
		var image = "";
		switch(sky){
			case "맑음":
				image = "./WeatherIcon/맑음.png";
				break;
			case "구름많음":
				image = "./WeatherIcon/구름많음.png";
				break;
			case "흐림":
				image = "./WeatherIcon/흐림.png";
				break;
		}
		return image;
	}

</script>
</html>
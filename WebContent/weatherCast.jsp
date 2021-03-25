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
#div1 {
		/* border: 1px solid black; */
		border-collapse: collapse;
		padding: 5px 10px;
		width: 250px;
		/* position: absolute; */
	}

#div2 {
		/* border: 1px solid black; */
		border-collapse: collapse;
		padding: 5px 10px;
		width: 250px;
		/* position: absolute;
		left: 329px; */
	}

#div3 {
		/* border: 1px solid black; */
		border-collapse: collapse;
		padding: 5px 10px;
		width: 250px;
		/* position: absolute;
		left: 650px; */
	}

#area,#div1, #div2, #div3 {
		float: left;
		margin:5px;
		margin-bottom: 50px;
	}

#area{
		height:30px;
		background-color:gray;
		color:white;
		border-radius:5px;
		padding:5px 10px;
	}

 #weather {
		width: 50px;
		height: 50px;
	}
 
#weatherS{
		text-align:center;
		margin:0 auto;
		/* border: 1px solid black; */
		/* width :60vw; */
		width : 80%;
	}

p{
		padding-top:5px;
	}
#TMX0,#TMX1,#TMX2{
		font-size:  14pt;
		font-weight: bold;
	}
</style>
</head>
<body>
<section id="weatherS">
	<select id="area" name="area" onchange='areaList(value)'>
		<option value="서울">서울</option>
		<option value="부산">부산</option>
		<option value="대구">대구</option>
		<option value="인천">인천</option>
		<option value="광주">광주</option>
		<option value="대전">대전</option>
		<option value="울산">울산</option>
		<option value="세종특별자치시">세종시</option>
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

	<div id="div1">
		<h3 >오늘</h3>
		<!-- <h3 style="background-color: blanchedalmond">오늘</h3> -->
		<p id="SKY0"></p>
		<p id="PTY0"></p>
		<p id="POP0"></p>
		<p id="REH0"></p>
		<p id="TMX0"></p>
	</div>
	<div id="div2">
		<h3 >내일</h3>
		<p id="SKY1"></p>
		<p id="PTY1"></p>
		<p id="POP1"></p>
		<p id="REH1"></p>
		<p id="TMX1"></p>
	</div>
	<div id="div3">
		<h3 >모레</h3>
		<p id="SKY2"></p>
		<p id="PTY2"></p>
		<p id="POP2"></p>
		<p id="REH2"></p>
		<p id="TMX2"></p>
	</div>
</section>
</body>
<script>
	$(document).ready(function() {
		/* $("select option[value='서울']").attr("selected",true); */
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

	function classify(obj) {
		for (var i = 0; i < obj.list.length; i++) {
			if (obj.list[i].fcstDate == obj.today) {
				drawWeatherCast(obj, 0, 0);
			} else if (obj.list[i].fcstDate == obj.tomorrow) {
				drawWeatherCast(obj, 1, 5);
			} else {
				drawWeatherCast(obj, 2, 10);
			}
		}
	}

	function drawWeatherCast(obj, idVar, index) {
		$('#POP' + idVar).html("강수 확률 : " + obj.list[0 + index].POP);
		if (obj.list[1 + index].PTY != "없음") {
			$('#PTY' + idVar).html("<img  id='weather' src='" + ptyState(obj.list[1 + index].PTY) + "'/>");
			$('#SKY' + idVar).html("");
		} else {
			$('#PTY' + idVar).html("");
			$('#SKY' + idVar).html("<img id='weather' src='" + skyState(obj.list[3 + index].SKY) + "'/>");
		}
		$('#REH' + idVar).html("습도 : " + obj.list[2 + index].REH);
		$('#TMX' + idVar).html("낮 최고기온 : " + obj.list[4 + index].TMX);
	}

	function skyState(sky) {
		var image = "";
		switch (sky) {
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

	function ptyState(pty) {
		var image = "";
		switch (pty) {
		case "비":
			image = "./WeatherIcon/비.png";
			break;

		case "비/눈":
			image = "./WeatherIcon/눈비.png";
			break;

		case "눈":
			image = "./WeatherIcon/눈.png";
			break;

		case "소나기":
			image = "./WeatherIcon/소나기.png";
			break;

		case "빗방울":
			image = "./WeatherIcon/비.png";
			break;

		case "빗방울/눈날림":
			image = "./WeatherIcon/눈비.png";
			break;

		case "눈날림":
			image = "./WeatherIcon/눈.png";
			break;
		}
		return image;
	}
</script>
</html>
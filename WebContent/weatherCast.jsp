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
	<h2>설명</h2>
	<h4>main 오기전에 /mainWeatherCast 들려서 다시 와야되는데 그냥 바로 weatherCast.jsp로
		와서 시작값이 서울이 아님</h4>
	<h4>css는 못하겠으니 아무나 해주세요.. ㅜㅜ</h4>
	<h4>필요 없는값이 여러개 있는 것 같으니 F12눌러서 넘어오는 값 확인해주세요.</h4>
	<h4>WeatherCastDTO에 무슨값인지 적어놨어요. 그냥 여기에 적어둘게요</h4>
	<p>POP; // 강수 확률 %</p>
	<p>PTY; // 강수 형태 코드값</p>
	<p>R06; // 6시간 강수량 범주 (1mm)</p>
	<p>REH; // 습도 %</p>
	<p>SKY; // 하늘상태 코드값</p>
	<p>TMN; // 아침 최저기온 섭씨</p>
	<p>TMX; // 낮 최고기온 섭씨</p>
	<p>UUU; // 풍속(동서성분) m/s</p>
	<p>VVV; // 풍속(남북성분) m/s</p>
	<h4>today tomorrow 이런건 날짜 비교해서 데이터 다르게 보여주려고 하는데 Javascript에서는 날짜 포맷변환이 귀찮아서 걍 서버에서 보냈어요</h4>
	</div>
<div>
<h3>오늘</h3>
	<p id="POP"></p>
	<p id="PTY"></p>
	<p id="R06"></p>
	<p id="REH"></p>
	<p id="SKY"></p>
	<p id="TMN"></p>
	<p id="TMX"></p>
</div>
<!-- <div>
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
</div> -->

<script>
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
				console.log(obj.list);
				console.log(obj.list[0].POP);
				console.log('today : ' + obj.today);
				console.log('tomorrow : ' + obj.tomorrow);
				console.log('afterTomorrow : ' + obj.afterTomorrow);
				$('#POP').html("강수 확률 : " + obj.list[0].POP);
				$('#PTY').html("강수 형태 : " + obj.list[1].PTY);
				$('#REH').html("습도 : " + obj.list[2].REH);
				$('#SKY').html("하늘 상태 : " + obj.list[3].SKY);
				$('#TMX').html("낮 최고기온 : " + obj.list[5].TMX);
			},
			error : function(e) {
				console.log(e);
			}
		})
	}
</script>
</html>
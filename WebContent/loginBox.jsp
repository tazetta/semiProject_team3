<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div id="login"></div>
<script>	
	var loginId = "${sessionScope.loginId}";
	
	if(loginId != ""){
		//session 에 loginId 가 있으면 로그인 박스를 보여준다.
		var content="안녕하세요 "+loginId+" 님, <a href='logout'>[로그아웃]</a>";
		document.getElementById("login").innerHTML = content;		
	}else{
		location.href="index.jsp";//session 에 loginId 가 없으면 index.jsp 로 보낸다.
	}
</script>
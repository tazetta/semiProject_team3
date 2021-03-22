<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- c태그 사용위해 불러옴 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<!-- JQuery사용 위해 불러옴 -->
<style>
/*좌측 카테고리*/
#leftDiv {
	position: relative;
	 background-color: #F2F2F2;
	float: left;
	top: -10px;
	left: 15px;
}

.menuHover {
	color: red;
}

.leftUl li {
	list-style-type: none;
	padding: 10px;
	/* border:1px solid black; */
	font-size: 90%;
	font-weight: 600;
	text-align: center;
}

.leftUl {
	padding: 10px;
	text-align: center;
}
</style>
</head>
<body>
	<section id="left">
		<div id="leftDiv">
			<ul class="leftUl">
				<li><a href="profile">사용자 정보</a></li>
				<li><a href="wroteList">내가 쓴 글 보기</a></li>
				<li><a href="visitedList">가봤어요</a></li>
				<li><a href="bookmarkList">즐겨찾기</a></li>
			</ul>


		</div>
	</section>
</body>
<script>
	$(".leftUl li a").hover(function () {
        $(this).css({"color":"#088A85"});
    },
    function(){
    	$(this).css({"color":"black"});
    }
	
	);

	</script>
</html>
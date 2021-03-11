<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>navi</title>
		<link rel="icon" href="favicon.png">
		<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
		<style>
        div.bar {
            display: flex;
            background-color: #faf9f9;
            height: 60px;
            border-radius: 5px;
            border: 1px solid lightgray;
            color: black;
            margin-left: 30px;
            margin-right: 30px;
            justify-content: center;
            align-items: center;
        }

        li {
            position: relative;
            float: left;
            padding: 20px 30px;
            color: black;
            font-size: 120%;
            font-weight: 500;
            text-align: center;
            width: 100px;
            height: 20px;
            list-style-type: none;
            display: inline;
        }

        .li {
            font-weight: 600;
        }

        a:link {
            text-decoration: none;
            font-size: 90%;
            color: black;
        }

        a:visited {
            color: black;
        }
    </style>
	</head>
	<body>
	<div class="bar">
        <ul>
            <li><a href="#">테마별</a> </li>

            <li><a href="#">지역별</a></li>

            <li><a href="#">커뮤니티</a></li>

            <li><a href="#">고객센터</a></li>

            <li><a href="#">마이페이지</a></li>

        </ul>
    </div>
	
	</body>
	<script>
	$("li").hover(function () {
        $(this).toggleClass("li");
    });
    $("li").click(function(){
        $(this).css({" background-color":"lightgray"})
    })
	</script>
</html>
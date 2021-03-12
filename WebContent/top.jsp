<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><html>

<head>
    <meta charset="utf-8">
    <title>top</title>
    <link rel="icon" href="./koreaCI.png">
    <style>
   
        a:link {
            color: rgb(71, 71, 71);
            text-decoration: none;
        }
        a:visited {
            color: rgb(71, 71, 71);
            text-decoration: none;
        }
        li {
            float: left;
            list-style-type: none;
            padding: 0 10 0 10;

        }
        .ci {
            position: relative;
            top: 20px;
            left: 50px;
        }
        .login {
            /* margin-right: 80px;; */
            display: flex;
            position: absolute;
            top: 40px;
            right: 5%; 
        }
        /*검색창*/
        div#search{
            position: absolute;
            top: 40px;
            left: 500px;
            height: 30px;
        }
        input[type='text']{
            border: 2px solid#e8f8fd;
            background-color:#e8f8fd;
            text-align: center;
            width: 500px;
            height: 40px;
        }
        input[type='submit']{
            border:#e8f8fd ;
            background-color:#c8e4ec;
            width: 50px;
            height: 35px;
            margin-left: 10px;
            font-weight: 600;
        }
    </style>

</head>

<body>
    
    <div class="ci">
        <a href="index.jsp" target="_parent"><img alt="CI" src="./koreaCI.png" width="150px" height="60px"></a>
    </div>
    <div id="search">
        <form >
            <input type="text" name="search" placeholder="검색어를 입력해주세요" />
            <input type="submit"  value="검색" />
        </form>
    </div>
    <div class="login">
        <ul>
          <li><a href="#">로그인</a></li>
          <li><a href="#">회원가입</a></li>
        </ul>
    </div>



</body>

</html>
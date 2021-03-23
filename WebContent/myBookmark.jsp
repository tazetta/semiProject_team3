<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <!-- c태그 사용위해 불러옴 -->
<!DOCTYPE html>
<html>
   <head>
   <meta charset="UTF-8">
   <title>마이페이지 - 즐겨찾기</title>
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script> <!-- JQuery사용 위해 불러옴 -->
    <!--  <link rel="stylesheet" href="./css/myBookmark.css"> -->
    <style>
body {
	min-width: 1400px;
}

/*콘텐츠*/
#content {
	text-align: center;
	position: relative;
	top: 0px;
	left: 20px;
	float: left;
	margin-bottom: 18px;	
	width: 85%;

}

#spanTitle {
	position: relative;
	top: 30px;
	left: 0px;
	font-weight: 600;
	background-color:  #93c47dff ;
	padding:5px 10px;
	color:white;
}

.text {
	text-align: left;
	margin-left: 2.5%;
	margin-right: 2.5%;
	width: 95%;
	height: auto;
}

.bottom {
	position: relative;
	bottom: 0px;
	right:10px;
	float: right;
}

.bottom a {
	background-color: lightgray;
	padding: 5px;
}

.list {
	background-color: white;
	border :1px solid gray;
	border-radius:5px;
	text-align: center;
	position: relative;
	top: 40px;
	margin: 0 auto;
	width: 800px;
	margin-top:20px;

}


.list table, .list tr, .list th, .list td {
	padding: 5px 10px;
	border: none;
	
}


.noneList {
	position: relative;
	top: 150px;
	height: 60px;
	text-align: center;
	align-items: stretch;
	background-color: transParent;
}
/*페이징*/
.pageArea {
	text-align: center;
	position: relative;
	top: 70px;
	left: 20px;
}

.pageArea span {
	font-size: 16px;
	border: 1px solid lightgray;
	background-color: lightgray;
	padding: 2px 10px;
}

a {
	text-decoration: none;
}

#page {
	font-weight: 600;
	border: none;
	background-color: transparent;
}
    </style>
   </head>
   <body>
   <jsp:include page="top.jsp" />
   <jsp:include page="navi.jsp" />
<jsp:include page="myLeft.jsp" />

      <div id="content">
         <span id="spanTitle">가보고 싶은 여행지</span>
         <c:if test="${list eq '[]'}">
            <div class="noneList">
               <p>즐겨찾기에 등록된 여행지가 없습니다</p>
            </div>
         </c:if>
            
               <c:forEach items="${list}" var="bm">
            <div class="list">
               <table >
                  <tr>
                     <th colspan="3" style="font-size:150%">${bm.title }</th>
                  </tr>
                  <tr>
                     <td id="user" rowspan="2">
                        <div>
                           <a href="./tripDetail?contentId=${bm.contentid}" target=window.open()><img src="${bm.firstimage}" width="250px" height="150px">
                           </a>
                        </div>
                     </td>
                     <td colspan="2" id="text">
                        <div class="ellipsis">${bm.overview}</div>
                     </td>
                  </tr>
                  <tr>
                     <td class="bottom">
                     <a href="myUpdate?myidx=${bm.myidx}&type=${bm.type}&deact=${bm.deactivate}&conIdx=${bm.contentid}" >삭제</a></td>
                     <td class="bottom" colspan="2">${bm.reg_date }</td>
                  </tr>
            
               </table>
            </div>
               </c:forEach>
               
               
         
         <c:if test="${list ne '[]'}">
            <div class="pageArea">
               <span> <c:if test="${currPage==1}">이전</c:if> <c:if
                     test="${currPage>1}">
                     <a href="bookmarkList?page=${currPage-1}">이전</a>
                  </c:if>
               </span> <span id="page">${currPage}</span> <span> <c:if
                     test="${currPage==maxPage}">다음</c:if> <c:if
                     test="${currPage<maxPage}">
                     <a href="bookmarkList?page=${currPage+1}">다음</a>
                  </c:if>
               </span>
            </div>
         </c:if>
      </div>

   </body>
   <script>
   
   var msg = "${msg}";
   if (msg != "") {
      alert(msg);
   }
   
   // 말줄임 기능
   $('.ellipsis').each(function(){
       var length = 120; //글자수
       $(this).each(function(){
          
         if($(this).text().length >= length){
           $(this).text($(this).text().substr(0,length)+'...');
         }
          
       });
     }); 
   
     $(".menu").hover(function () {
           $(this).toggleClass("menuHover");
       });
       $(".menu").click(function () {
           $(this).css({ "background-color": "#F5D0A9", "font-weight": "600" });
       })
   </script>
</html>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title></title>
<style>
body {
  margin: 0;
}

#m_navbar {
  display: flex;
  height: 50px;
  border-bottom: 1px solid #08444d;
  justify-content: center;
  align-items: center;
  min-width: 1000px;
}

#m_navbar li {
  padding: 8px 24px;
  display: inline;
  font-weight: 600;
}

#m_navbar a:hover {
  color: #08444d;
}

#m_navbar a {
  text-decoration: none;
  font-size: 89%;
  color: rgb(155, 153, 153);
}
</style>
</head>
<body>
	<jsp:include page="navi.jsp" />
	<div id="m_navbar">
			<li class="managerlist"><a href="./managerList" target="_parent">관리자정보</a></li>
			<li><a href="./tripManageList">여행지 관리</a></li>
			<li class="repList"><a href="./reportBBS">신고내역 관리</a></li>
			<li class="memberlist"><a href="./memberList" target="_parent">회원정보 관리</a></li>
			<li class="popupList"><a href="./popupList" target="_parent">팝업 관리</a></li>
	</div>
</body>
</html>
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
	<form action="insert" method="GET">
		<table>
			<tr>
				<th>contentId</th>
				<td><input type="number" name="contentId" /></td>
			</tr>
			<tr>
				<th>firstImage</th>
				<td><input type="text" name="firstImage" /></td>
			</tr>
			<tr>
				<th>latitude</th>
				<td><input type="text" name="latitude" /></td>
			</tr>
			<tr>
				<th>longitude</th>
				<td><input type="text" name="longitude" /></td>
			</tr>
			<tr>
				<th>address</th>
				<td><input type="text" name="address" />
			</tr>
			<tr>
				<th>title</th>
				<td><input type="text" name="title" /></td>
			</tr>
			<tr>
				<th>contentCode</th>
				<td><input type="text" name="contentCode" /></td>
			</tr>
			<tr>
				<th>mediumCode</th>
				<td><input type="text" name="mediumCode" /></td>
			</tr>
			<tr>
				<th>smallCode</th>
				<td><input type="text" name="smallCode" /></td>
			</tr>
			<tr>
				<th>areaCode</th>
				<td><input type="text" name="areaCode" /></td>
			</tr>
			<tr>
				<th>cityCode</th>
				<td><input type="text" name="cityCode" /></td>
			</tr>
			<tr>
				<th>largeIdx</th>
				<td><input type="text" name="largeIdx" /></td>
			</tr>
			<tr>
				<th>overview</th>
				<td><input type="text" name="overview" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="저장" /></td>
			</tr>
		</table>
	</form>
</body>
<script>
	
</script>
</html>
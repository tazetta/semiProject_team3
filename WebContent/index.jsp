<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<script>
	function page() {
		location.href="./main";
	}
	page();
	
	var msg = "${msg}";
	if (msg != "") {
		alert(msg);
	} 
	
</script>
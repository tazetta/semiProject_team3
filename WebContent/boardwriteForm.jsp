<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String loginId = (String)request.getSession().getAttribute("loginId"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>


<style>
	table{
		width:800px;
		position:absolute;
		left:20%		
	}
	table, th, td {
		border: 1px solid black;
		border-collapse: collapse;
		text-align: center;
		padding : 5px 10px;
	}
	input[type='text']{
		width:100%
	}
	textarea{
		width:100%;
		height:300px;
		resize:none;
	}
	#submit{
		position:absolute;
		top:95%;
		left:71%;
	}
	
</style>
</head>
<body>
	<iframe src="top.jsp" width="100%" height="90px" frameborder="0" scrolling="no"></iframe>
    <iframe id="navi" src="navi.jsp" width="100%" height="90px" frameborder="0" scrolling="no"></iframe>
    
	<form id="regist" action="boardWrite" enctype="multipart/form-data" method="post">
		<table>
			<tr>
				<th>작성자</th>
				<td><input type="text" name="userId" id="userId"value="${loginId}" readonly/></td>
			</tr>
			<tr>
				<th>제목</th>
				<td><input type="text" id="subject" name="subject"/></td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea id="content" name="content"></textarea></td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="file" name="photo" id="photo"/>
				</td>
			</tr>
			<tr>
				<td colspan="2"><button id="regist">등록</button>
				<input type="button" id="cancel" onclick="location.href='./boardList'" value="취소"/>
				</td>
			</tr>
		</table>
	</form>
</body>
<script>
	/* $('#regist').click(function(){
		console.log('등록클릭');
		var $subject = $('#subject');
		var $content = $('#content');
		var $userId = $('#userId');
		var $photo = $('#photo');
		
		if($subject.val()==''){
			alert("제목을 입력해주세요.");
			$subject.focus();
		}else if($content.val()==''){
			alert("내용을 입력해주세요.");
			$content.focus();
		}else{
			/* var params = {};
			params.subject = $subject.val();
			params.content = $content.val();
			params.userId = $userId.val();
			params.photo = $photo.val();
			var formData = new FormData();
			formData.append("subject", $subject.val());
			formData.append("content", $content.val());
			formData.append("userId", $userId.val());
			formData.append("photo", $photo[0].files[0]);


			$('#form').ajaxForm({
				type:'POST'
				,url:'./boardWrite'
				,enctype: 'multipart/form-data'
				,processData: false
			    ,contentType: false
				,data:formData
				,dataType:'JSON'
				,success:function(data){
					console.log(data);
					if(data.boardIdx>0){
						alert('글등록에 성공하였습니다.');
						location.href="./boardDetail?boardIdx="+data.boardIdx;
					}else{
						alert('글등록에 실패하였습니다.');
						location.href="./boardList";
					}
				},error:function(e){
					console.log(e);
				}
			});
		}
	}); */
</script>
</html>
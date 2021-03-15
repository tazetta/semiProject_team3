<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>신규 관리자 등록</title>
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
		<style>
			h2{
				text-align: center;
			}
            table,td,th{
                border: 1px solid black;
                border-collapse: collapse;
                padding: 5px 10px;
            }
            table{
                width: 400px;
            }
            input[type='text'],input[type='password']{
                width: 70%;
            }
            span{
                font-size: 10pt;
            }
            fieldset{
                padding: 20px;
                width: 300px;
                margin: auto;
                margin-top: 15%;
                border: 1px solid white;
                background-color: whitesmoke;
            }
        </style>
</head>
<body>
	<h2>신규 관리자 등록</h2>
        <hr/>
           <fieldset>
           	<form action="managerRegist" method="post">
               <table>
                   <tr>
                       <th>관리자 ID</th>
                       <td>
                           <input type="text" id="managerId" name="managerId"/>
                       </td>
                   </tr>
                   <tr>
                       <th>비밀번호</th>
                       <td>
                           <input type="password" id="managerPw" name="managerPw"/>
                       </td>
                   </tr>
                   <tr>
                       <th>이름</th>
                       <td>
                           <input type="text" id="managerName" name="managerName"/>
                       </td>
                   </tr>
                  </form>
               </table>
               <div style="text-align: center;margin-top: 10px;">
                   <button id="test">등록</button>
                   <input type="button" onclick="window.close();" value="취소"/>
               </div>
           </fieldset>
        
	</body>
<script>
var msg = "${msg}";
if(msg!=""){
	alert(msg);
}	

/* $('#test').click(function() {
	var $id = $("#managerId");
	var $pw = $("#managerPw");
	var $name = $("#managerName");
	
	var params = {};
	params.id = $id.val();
	params.pw = $pw.val();
	params.name = $name.val();
	
	$.ajax({
		type:'POST'
		,url:'./managerRegist'
		,data:params
		,dataType:'JSON'
		,success:function(data){
			console.log(data);
			if(data.success == true){
				alert('관리자를 등록하였습니다.');
				window.close();
			}else{
				alert('잠시 후 다시 시도해 주세요.');
			},error:function(e){
				console.log(e);
		}
	}); */
	
	
/* }); */
	
	

</script>
</html>
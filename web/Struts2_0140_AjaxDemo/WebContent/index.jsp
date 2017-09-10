<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Struts2 Ajax Demo</title>
<sx:head/>
	<script type="text/javascript">
	var XHR = false;
	function CreateXHR(){
		try{
			XHR = new ActiveXObject("msxml2.XMLHTTP");
		}
		catch(e1){
			try{
				XHR = new ActiveXObject("microsoft.XMLHTTP");
			}
			catch(e2){
				try{
					XHR = new XMLHttpRequest();
				}
				catch(e3){
					XHR = false;
				}
			}
		}
	}
	
	function sendRequest(){
		CreateXHR();
		if(XHR){
			var name = document.getElementById("name").value;
			var password = document.getElementById("password").value;
			var uri = "http://localhost:8080/AjaxDemo/login.action?name="+name+"&password="+password;
			
			XHR.open("GET",uri,true);
			XHR.onreadystatechange = resultHander;
			XHR.send(null);
		}
	} 
	
	function resultHander(){
		if(XHR.readyState == 4 && XHR.status == 200){
			alert(XHR.responseText);
		}
	}
	
	</script>
	</head>
<body>
	<center>
		Name: <input type="text" id="name" /><br/>
		Password: <input type="password" id="password" /><br />
		<input type="button" value="OK" onclick="sendRequest();" />	
	</center>
</body>
</html>
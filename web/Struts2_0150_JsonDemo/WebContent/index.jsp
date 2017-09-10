<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Struts2 JSON Demo</title>

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
			XHR.open("GET",uri,true);
			XHR.onreadystatechange = resultHander;
			XHR.send(null);
		}
	} 
	
	function resultHander(){
		if(XHR.readyState == 4 && XHR.status == 200){
			var userObj = JSON.parse(XHR.responseText);
			var userStr = "<table border = 0 >";
			userStr += ('<tr><td><b>Name</b></td><td>' + userObj.USER.name + '</td></tr>');
			userStr += ('<tr><td><b>Age</b></td><td>' + userObj.USER.age + '</td></tr>'); 
			userStr += "</table>";
			document.getElementById('jsonDiv').innerHTML = userStr;
		}
	}
	
	</script>
	</head>
<body>
	<center>
			<div id="jsonDiv"></div>
			<input type="button" value="OK" onclick="sendRequest();" />	
	</center>
</body>
</html>
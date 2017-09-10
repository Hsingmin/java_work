<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Struts2RedirectAction</title>
</head>
<body>
	<center>
		<s:form action="user">
			<s:textfield label="Name" name="name" />
			<s:textfield label="Age" name="age" />
			<s:textfield label="Telephone" name="telephone" />
			<s:textfield label="Address" name="address" />
			<s:submit />
		</s:form>	
	</center>
</body>
</html>
<%@ page language="java" import="java.util.Map" %>
<%@ page language="java" import="java.util.HashMap" %>
<%@ page language="java" import="org.soldieringup.Roster" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	//Map<Object,Roster> r = (HashMap) request.getAttribute("roster");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Soldier&#9733;Up</title>
<link href="Styles/styles.css" rel="stylesheet" type="text/css"/>
</head>

<body>
<div id="wrapper">
<div id="header">
<p class="soldierFont">Soldier&#9733;Up</p>
</div>
<div id="content_area">

 <c:forEach var="content" items="${roster}">
 	<font color='red'>"Key Name" <br/></font>
 	<font color='red'>${content.key.name} <br/></font>
 	${content.key.value} <br/>
  </c:forEach> 
</div>
</div>
</body>
</html>

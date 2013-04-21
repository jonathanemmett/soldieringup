<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="org.soldieringup.Business" %>
<%@ page import="org.soldieringup.MongoEngine" %> 
<%@ page import="org.soldieringup.Photo" %>
<%@ page import="org.soldieringup.ZIP" %>
<%@ page import="java.util.List" %>
<%@ page import="org.bson.types.ObjectId;" %> 
<% 
	Business foundBusiness = (Business) request.getAttribute( "account" );
	ZIP businessZIP = (ZIP) request.getAttribute( "account_zip" );
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Soldier Up - Entrepreneurs serving those who served us.</title>
<link href="Styles/styles.css" rel="stylesheet" />
<script src="Scripts/CoverImageDrag.js" type="text/javascript"></script>
<script src="Scripts/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="Scripts/jquery.inputmask.js" type="text/javascript"></script>
<script src="Scripts/jquery.inputmask.date.extensions.js" type="text/javascript"></script>
<script src="Scripts/jquery.form.js" type="text/javascript"></script>
<!--[if lt IE 9]>
    <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]--> 
</head>
<body>
<jsp:include page="Includes/header.jsp"></jsp:include>
<section>
<div id="cover_banner" style="border:#000 solid 1px; height:300px; position:relative; overflow:hidden;">
	<% if( foundBusiness.getCoverSrc() != null ){%>
	<img src="<%="Images/"+foundBusiness.getCoverSrc() %>" />
	<%} %>
</div>
<% if( foundBusiness.getName() != null){ %>
	<article style="width:200px; float:left">
		<p><%=foundBusiness.getName() %></p>
			<span style="width:120px; height:24px; display:block;background-image:url(Images/fullStarRating.png);"></span>
		<p><%=foundBusiness.getAddress() %></p>
		<p><%=businessZIP.getCity() + " " + businessZIP.getState() + " " + foundBusiness.getZip() %></p>
	</article>
	<article style="margin-left:210px; margin-right:10px;">
		<p><%=foundBusiness.getLongSummary() %></p>
	</article>
<% }%> 
</section>
</body>
</html>




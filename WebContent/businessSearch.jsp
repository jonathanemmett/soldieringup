<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.soldieringup.Business" %>
<%@ page import="org.soldieringup.Engine" %>
<%  Engine engine = new Engine(); %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Soldier Up - Entrepreneurs serving those who served us.</title>
<link href="Styles/styles.css" rel="stylesheet" />
<style>
#tags_query_section
{
	position: relative;
	margin-bottom:50px;
}

#tags_query_section span
{
	position: absolute;
	top:25px;
	left:38%;
	font-size: xx-small;
}
</style>
<!--[if lt IE 9]>
    <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]--> 
</head>
<body>
<jsp:include page="Includes/header.jsp"></jsp:include>
<section style="overflow:auto;">
<form action="businessSearch.jsp" method="get" style="text-align:center;">
	<p id="tags_query_section">
	Tags:<input id="tags_query" type="text" name="tags"/><input type="submit"/><span>(Enter separate tags with a comma )</span>
	</p>
</form>
<%
	if( request.getParameter( "tags" ) != null )
	{
		String tagString = request.getParameter( "tags" );
		String[] tags = tagString.split(",");
		if( tags.length > 0 )
		{
			ArrayList<Business> foundBusinesses = engine.getBusinessesFromTags( tags );
			for( int i = 0; i < foundBusinesses.size(); ++i )
			{
				%>
				<div style="float:left; margin-left:20px; width:45%; padding-bottom:10px;">
					<img style="float:left;" src="Images/<%=foundBusinesses.get( i ).getProfileSrc()%>"/>
					<h1 style="margin-left:110px; margin-top:0px;padding-top:0px;"><a href="business.jsp?aid=<%=foundBusinesses.get(i).getBid()%>"><%=foundBusinesses.get( i ).getName()%></a></h1>
					<p style="margin-left:110px;"><%=foundBusinesses.get( i ).getShortSummary()%></p>
				</div>
				<% 
			}
		}
	}
%>
</section>
</body>
</html>
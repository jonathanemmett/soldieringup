<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="org.soldieringup.Question" %>
<%@ page import="org.soldieringup.User" %>
<%@ page import="org.soldieringup.Veteran" %>
<%@ page import="org.soldieringup.database.MySQL" %>
<%
	long questionIndex;

	if( request.getParameter( "qid" ) != null )
	{
		questionIndex = Long.valueOf( request.getParameter( "qid" ).toString() );
	}
	else
	{
		questionIndex = 1;
	}
	
	MySQL databaseConnection = MySQL.getInstance();
	Question queriedQuestion = databaseConnection.getQuestionFromId( questionIndex );
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Soldier Up - Entrepreneurs serving those who served us.</title>
<link href="Styles/Styles.css" rel="stylesheet" />
<!--[if lt IE 9]>
    <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
<style>

#question_section
{
	overflow:hidden;
}

#left_question_section
{
	width:60%;
	margin-bottom:-3000px;
	padding-bottom:3000px;	
}

#right_question_section
{
	width:30%;
	float:right;
	margin-bottom:-3000px;
	padding-bottom:3000px;
}

#question_section h1
{
	border-bottom:#000 solid 1px;
	font-size: medium;
	margin-bottom:0px;
}
</style> 
</head>
<body>
<jsp:include page="Includes/header.jsp"></jsp:include>
<section id="question_section">
<%
if( queriedQuestion != null)
{
	Veteran veteranFromQuestion = databaseConnection.getVeteran( queriedQuestion.getVid() );
	User associatedUser = databaseConnection.getUserFromId( veteranFromQuestion.getUid() );
%>
<section id="right_question_section">
<img src="Images/<%=veteranFromQuestion.getProfileSrc()%>"/>
<p><%=associatedUser.getFirstName() + " " + associatedUser.getLastName()%></p>
<h1>Aspiration</h1>
<p style="margin-top:5px; padding-top:0px;"><%=veteranFromQuestion.getGoal()%></p>
</section>
<section id="left_question_section">
<p><%=queriedQuestion.getQuestionTitle()%></p>
<p><%=queriedQuestion.getAvailability()%></p>
<p><%=queriedQuestion.getDetailedDescription()%></p>
<div style="background-color:"></div>
</section>
<%
}
else
{
	out.println("<p>Question does not exist</p>");
}
%>
</section>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.lang.NumberFormatException" %>
<%@ page import="org.soldieringup.Question" %>
<%@ page import="org.soldieringup.database.MySQL;" %>
<%
	session.setAttribute( "uid", 38 );
	session.setAttribute( "vid", 9 );
	session.setAttribute( "editing_account_type" , "veteran" );

	if( session.getAttribute( "vid" ) == null ||
	session.getAttribute( "vid" ) == "" )
	{
		%><jsp:forward page="/login.jsp"/><% 
	}
	
	long vid = Long.valueOf( session.getAttribute( "vid" ).toString() );
	MySQL databaseConnection = MySQL.getInstance();
	Question questionToModify = null;
	
	boolean isUpdatingQuestion = false;
	
	if( request.getParameter( "qid" ) != null )
	{
		try
		{
			questionToModify = databaseConnection.getQuestionFromId( Long.valueOf( request.getParameter( "qid" ).toString() ) );
			isUpdatingQuestion = questionToModify.getVid() == vid;
		}
		catch( NumberFormatException e )
		{
		}
	}
	
	if( isUpdatingQuestion )
	{
		session.setAttribute( "question_form_request_type", "update" );
		session.setAttribute( "question_form_update_question_id", request.getParameter( "qid" ) );
	}
	else
	{
		session.setAttribute( "question_form_request_type", "insert" );
		session.removeAttribute( "question_form_update_question_id" );
	}

%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Soldier Up - Entrepreneurs serving those who served us.</title>
<link href="Styles/Styles.css" rel="stylesheet" />
<link href="Styles/questionForms.css" rel="stylesheet" />
<!--[if lt IE 9]>
    <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]--> 
</head>
<body>
<jsp:include page="Includes/header.jsp"></jsp:include>
<section>
<h1 style="border-bottom:#000 solid 1px; font-size: medium; margin-top: 0px;">Fill out the following fields</h1>
<form method="post" action="UpdateVeteranQuestion">	
<p>
	<label>Title:</label><input type="text" maxlength="50"  name="question_title" value="<%=isUpdatingQuestion ? questionToModify.getQuestionTitle() : ""%>"/>
</p>
<p>
	<label>Availability:</label><input type="text" name="availability" value="<%=isUpdatingQuestion ? questionToModify.getAvailability() : ""%>"/>
</p>
<p>
	<label>Tags:</label><input type="text" name="tags"/>
	<input type="text" name="tags"/>
	<input type="text" name="tags"/>
</p>
<p>
	<label style="display:inline-block;display:inline-block;width:200px">Question:</label><textarea style="margin-left:0px;padding:0px;display:inline-block;" name="question_detailed_description" rows="30" cols="50"><%=isUpdatingQuestion ? questionToModify.getDetailedDescription() : ""%></textarea>
</p>
<p>
	<input type="submit" style="margin-left:200px;"name="UpdateQuestion" value="<%=isUpdatingQuestion ? "Update" : "Insert"%>">
	<% if( isUpdatingQuestion ){%>
		<input type="submit" name="DeleteQuestion" value="Delete">
	<%} %>
</p>
</form>
</section>
</body>
</html>
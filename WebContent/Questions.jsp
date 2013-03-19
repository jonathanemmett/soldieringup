<%@page import="org.soldieringup.database.MySQL"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import ="java.util.ArrayList" %>
<%@ page import ="java.util.Iterator" %>
<%@ page import ="org.soldieringup.Question" %>
<%@ page import ="org.soldieringup.database.MySQL;" %>
<%
	if( session.getAttribute( "aid" ) == null ||
		session.getAttribute( "aid" ) == "" ||
		session.getAttribute( "editing_account_type" ) == null ||
		session.getAttribute( "editing_account_type" ) != "veteran"
	  )
	{
	%><jsp:forward page="/login.jsp"/><% 
	}

	long vid = Long.valueOf( session.getAttribute( "aid" ).toString() );
	ArrayList<Question> questionsAsked = MySQL.getInstance().getQuestionsFromVeteran( vid ); 
	Iterator<Question> questionsIterator = questionsAsked.iterator();
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
.veteran_question_div
{
	border-color:#eeeeee;
	border-style:solid;;
	border-width:1px 0px;
}

.question_links a
{
	display:inline-block;
	margin: auto 5px;
}

.question_links a:first-child
{
	margin-left:0px;	
}
</style>
</head>
<body>
<jsp:include page="Includes/header.jsp"></jsp:include>
<section>
<p>Ask a Question</p>
<% while( questionsIterator.hasNext() )
{ 
	Question currentQuestion = questionsIterator.next();
%>
	<div class="veteran_question_div">
	<p><%=currentQuestion.getQuestionTitle()%><span style="float:right;">Meeting Requests</span></p>
	<p class ="question_links">
	<a href="question.jsp?qid=<%=currentQuestion.getQid()%>">View</a>|
	<a href="VeteranQuestionForm.jsp?qid=<%=currentQuestion.getQid()%>">Edit</a>|
	<a href="UpdateVeteranQuestion?command=delete&qid=<%=currentQuestion.getQid()%>">Delete</a></p>
	</div>
<%} %>
</section>
</body>
</html>
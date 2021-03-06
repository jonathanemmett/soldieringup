<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import ="java.util.Iterator" %>
<%@ page import ="java.util.List" %>
<%@ page import ="org.soldieringup.Business" %>
<%@ page import ="org.soldieringup.MongoEngine" %>
<%@ page import ="org.soldieringup.MeetingRequest" %>
<%@ page import ="org.soldieringup.Question" %>
<%@ page import ="org.soldieringup.User" %>
<%@ page import ="org.soldieringup.Veteran;" %>
<%
	if( session.getAttribute( "uid" ) == null ||
		session.getAttribute( "uid" ) == "" ||
		session.getAttribute( "editing_account_type" ) == null ||
		session.getAttribute( "editing_account_type" ) != "veteran"
	  )
	{
	%><jsp:forward page="/login.jsp"/><% 
	}

	MongoEngine engine = new MongoEngine();
	User currentUser = engine.findUsers( "_id", session.getAttribute( "uid" ) ).get( 0 );
	List<Question> questionsAsked = engine.findQuestions( "veteran", currentUser ); 
	Iterator<Question> questionsIterator = questionsAsked.iterator();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Soldier Up - Entrepreneurs serving those who served us.</title>
<link href="Styles/styles.css" rel="stylesheet" />
<!--[if lt IE 9]>
    <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]--> 
<style>
.veteran_question_div
{
	border-color:#eeeeee;
	border-style:solid;
	border-width:1px 0px 0px 0px;
	padding-top: 10px;
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

.meeting_requests
{
	display:inline-block;
	margin-top:0px;
	position: absolute;
	margin-left:670px;
	list-style:none;
	padding-left:0px;
}

.meeting_requests ul
{
	width:300px;
	height:300px;
	list-style-type:none;
	position:absolute;
	left: -5000px;
	top: 0px;
	overflow:auto;
	background-color:#ffffff;
	z-index: 99;
	border:#eeeeee solid 1px;
}

.meeting_requests ul li
{
	border-bottom:#eeeeee solid 1px;
}

.meeting_requests a
{
	color:#000;
	text-decoration:none;
}

.meeting_requests h2
{
	border-bottom:#000 solid 1px;
}

.veteran_question_div li:hover ul
{
	left:-300px;
}
</style>
</head>
<body>
<jsp:include page="Includes/header.jsp"></jsp:include>
<section>
<p><a href="VeteranQuestionForm.jsp">Ask a Question</a></p>
<% while( questionsIterator.hasNext() )
{ 
	Question currentQuestion = questionsIterator.next();
	List<MeetingRequest> meetingRequests = engine.findMeetingRequest( "question", currentQuestion );
%>
	<div class="veteran_question_div">
	<div><span><%=currentQuestion.getQuestionTitle()%></span>
		<% if( !meetingRequests.isEmpty() ){ %>
			<ul class = "meeting_requests">
				<li><a href="#" class="meeting_requests_header" style="float:right;">Meeting Requests (<%=meetingRequests.size() %>)
				</a><ul>
			<%
				Iterator<MeetingRequest> meetingRequestsIt = meetingRequests.iterator();
				while( meetingRequestsIt.hasNext() )
				{
					MeetingRequest currentMeetingRequest = meetingRequestsIt.next();
					Business businessFromRequest = (Business) engine.findAccounts( "_id", currentMeetingRequest.getBusiness() ).get( 0 );
						%><li>
						<h2><a href="Account?aid=<%=businessFromRequest.getObject_id().toString() %>"><%=businessFromRequest.getName()%></a></h2>
						<p>Day: <%=currentMeetingRequest.getDay()%></p>
						<p>Time: <%=currentMeetingRequest.getTime()%></p>
						<p>Location: <%=currentMeetingRequest.getLocation()%></p>
						<p><input type="submit" value="Accept"/><input type="submit" value="Decline"/></p>
					</li><%
				}
			%>
					</ul>
				</li>
			</ul>
		<%}%>
	</div>
	<p class ="question_links">
	<a href="question.jsp?qid=<%=currentQuestion.getID().toString()%>">View</a>|
	<a href="VeteranQuestionForm.jsp?qid=<%=currentQuestion.getID().toString()%>">Edit</a>|
	<a href="UpdateVeteranQuestion?command=delete&qid=<%=currentQuestion.getID().toString()%>">Delete</a></p>
	</div>
	<%} %>
</section>
</body>
</html>
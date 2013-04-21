<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="org.bson.types.ObjectId" %>
<%@ page import="org.soldieringup.MongoEngine" %>
<%@ page import="org.soldieringup.Tag" %>
<%@ page import="org.soldieringup.Question" %>
<%@ page import="java.lang.NumberFormatException" %>
<%@ page import="java.util.*;" %>
<%
	if( session.getAttribute( "editing_account_type" ) == null ||
	session.getAttribute( "editing_account_type" ) != "veteran" )
	{
		%><jsp:forward page="/login.jsp"/><% 
	}
	
	MongoEngine engine = new MongoEngine();
	Question questionToModify = null;
	Iterator<Tag> questionTags = null;
	
	boolean isUpdatingQuestion = false;
	
	if( request.getParameter( "qid" ) != null && ObjectId.isValid( request.getParameter( "qid" ) ) )
	{
		questionToModify = engine.findQuestions( "_id", new ObjectId( request.getParameter( "qid" ) )  ).get( 0 );
		isUpdatingQuestion = questionToModify.getVeteran().getObject_id().equals( session.getAttribute( "uid" ) );
		if( isUpdatingQuestion )
		{
			List<Tag> questionTagsList = questionToModify.getTags();
			questionTags = questionTagsList.iterator();
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
<link href="Styles/styles.css" rel="stylesheet" />
<link href="Styles/questionForms.css" rel="stylesheet" />
<link href="Styles/jquery-ui-autocomplete.min.css" rel="stylesheet" />
<script src="Scripts/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="Scripts/jquery-ui-autocomplete.min.js"></script>

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
	<label>Tags:</label><input type="text" id="tag_test" name="tag" value="<%=isUpdatingQuestion && questionTags.hasNext() ? questionTags.next().get_name() : ""  %>" />
	<input type="text" name="tag" value="<%=isUpdatingQuestion && questionTags.hasNext() ? questionTags.next().get_name() : ""  %>" />
	<input type="text" name="tag" value="<%=isUpdatingQuestion && questionTags.hasNext() ? questionTags.next().get_name() : ""  %>" />
</p>
<p>
	<label style="display:inline-block;display:inline-block;width:200px">Question:</label><textarea style="margin-left:0px;padding:0px;display:inline-block;" name="question_detailed_description" rows="30" cols="50"><%=isUpdatingQuestion ? questionToModify.getDetailedDescription() : ""%></textarea>
</p>
<p>
	<input type="submit" style="margin-left:200px;"name="UpdateQuestion" value="<%=isUpdatingQuestion ? "Update" : "Insert"%>">
</p>
</form>
</section>
<script type="text/javascript">

$( '[name="tag"]' ).autocomplete({source:'QueryTags'});
</script>
</body>
</html>
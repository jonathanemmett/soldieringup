<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="org.bson.types.ObjectId" %>    
<%@ page import="org.soldieringup.service.MongoEngine" %>
<%@ page import="org.soldieringup.Question" %>
<%@ page import="org.soldieringup.User" %>
<%@ page import="org.soldieringup.Veteran" %>
<%
	if( session.getAttribute( "aid" ) == null )
	{
		%><jsp:forward page="/login.jsp"/><%
	}

	MongoEngine engine = new MongoEngine();
	Question queriedQuestion = null;

	if( request.getParameter( "qid" ) != null )
	{
		queriedQuestion = engine.findQuestions( "_id", request.getParameter( "qid" ) ).get( 0 );
	}

%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Soldier Up - Entrepreneurs serving those who served us.</title>
<link href="Styles/styles.css" rel="stylesheet" type="text/css" />
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

#left_question_section h3
{

	margin-top:0px;
	margin-bottom:10px;
	border-bottom:#000 solid 1px;
}

#left_question_section .under_header
{
	margin-top:0px;
	margin-bottom:30px;
}

#question_description
{
	margin-top:30px;
}

#right_question_section
{
	width:35%;
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

#contact_form
{
	text-align:left;
}

#contact_form label
{
	width:100px;
	vertical-align:top;
	display: inline-block;
}

#contact_form .no_label_line
{
	margin-left:100px;
}

</style> 
<link href="Styles/formStyles.css" rel="stylesheet" />
<script src="Scripts/LightBox.js" type="text/javascript"></script>
<script src="Scripts/jquery-1.9.js" type="text/javascript"></script>
<script src="Scripts/jquery.dform-1.0.1.js" type="text/javascript"></script>
<script src="Scripts/jquery.form.js" type="text/javascript"></script>
</head>
<body>
<jsp:include page="Includes/header.jsp"></jsp:include>
<section id="question_section">
<%
if( queriedQuestion != null)
{
	User veteranFromQuestion = queriedQuestion.getVeteran();
%>
<section id="right_question_section">
<img src="Images/<%=veteranFromQuestion.getProfileSrc()%>"/>
<p><%=veteranFromQuestion.getFirstName() + " " + veteranFromQuestion.getLastName()%></p>
<h1>Aspiration</h1>
<p style="margin-top:5px; padding-top:0px;"><%=veteranFromQuestion.getVeteran().getGoal()%></p>
</section>
<section id="left_question_section">
<h3>Help Needed</h3>
<p class="under_header"><%=queriedQuestion.getTitle()%></p>
<h3>Availability</h3>
<p class="under_header"><%=queriedQuestion.getAvailability()%></p>
<p id="question_description"><%=queriedQuestion.getDescription()%></p>
<div id="meeting_request_button" style="border-radius:25px;background-image:url('Images/SoldierUpBackgroundButton.png');font-family:steamer; color:#FFF; text-align:center; vertical-align:middle; font-size:2.5em;padding:25px;">SoldierUp For <%=veteranFromQuestion.getFirstName()%></div>
</section>
</section>
<script>

var currentQuestionId = <%="\""+queriedQuestion.getID().toString()+"\";"%>
var askingVeteranId = <%="\""+veteranFromQuestion.getObject_id().toString()+"\";"%>
$( "#meeting_request_button" ).click(function(){
	var lightBox = new LightBox();

	var form = document.createElement( "form" );
	form.setAttribute( "id", "contact_form" );
	lightBox.appendElement( form );

	var body = document.getElementsByTagName( "body" ).item(0);
	body.appendChild( lightBox.retrieveDiv() );
	
	var exitSpan = document.createElement( "span" );
	exitSpan.setAttribute( 'class', 'remove_fields' );
	lightBox.appendElement( exitSpan );

	$( exitSpan ).click(function(){
		body.removeChild( lightBox.retrieveDiv() );
	});
	
	$( "#contact_form" ).dform({
		"action":"MeetingRequests",
		"method":"post",
		"html" :
		[
		 	{
		 		"type" : "h1",
		 		"html" : "RSVP Request"
		 	},
		 	{
		 		"type" : "hidden",
		 		"name" : "qid",
		 		"value"  : currentQuestionId
		 	},
		 	{
		 		"type" : "hidden",
		 		"name" : "aid",
		 		"value"  : askingVeteranId
		 	},
		 	{
		 		"type"  : "hidden",
		 		"name"  : "cmd",
		 		"value" : "insert"
		 	},
		 	{
		 		"type" : "p",
		 		"html" :
		 			[
						{
							"type" : "label",
							"html" : "Event Date"
						},
						{
							"id" : "event_date",
							"name" : "day",
							"type" : "text"
						}
					]
		 	},
		 	{
		 		"type" : "p",
		 		"html" :
		 			[
						{
							"type" : "label",
							"html" : "Event Time"
						},
						{
							"id" : "event_time",
							"name" : "time",
							"type" : "text"
						}
					]
		 	},
		 	{
		 		"type" : "p",
		 		"html" :
		 			[
						{
							"type" : "label",
							"html" : "Location"
						},
						{
							"id" : "meeting_location",
							"name" : "location",
							"type" : "textarea",
							"cols" : "30",
							"rows" : "15"
						}
					]
		 	},
		 	{
		 		"type" : "p",
		 		"html" :
		 			[
						{
							"id" : "send_meeting_request",
							"name" : "send_meeting_request",
							"class" : "no_label_line",
							"type" : "submit"
						}
					]
		 	}
		]
	});
	
	$( "#contact_form" ).submit( function(){
		alert( "submitting" );
		$( this ).ajaxSubmit({
			success: function(responseText){
				$( "#contact_form" ).html("<p>Request successfully sent. Way to SoldierUp!</p>");
			}
		});
		alert("leaving");
		return false;
	});
	
});
</script>
<%
}
else
{
	out.println("<p>Question does not exist</p>");
}
%>
</body>
</html>
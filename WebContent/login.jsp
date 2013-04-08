<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="org.soldieringup.User" %>
<%@ page import="org.soldieringup.Business" %>
<%@ page import="org.soldieringup.Veteran" %>
<%@ page import="org.apache.commons.validator.routines.EmailValidator" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map;" %>
<%
	session.setAttribute( "login_page", request.getRequestURI() );
	Map<String,String> loginErrors;

	try
	{
		loginErrors = session.getAttribute( "login_errors" ) != null ?
			(Map<String,String>) session.getAttribute( "login_errors" ) : null;
	}
	catch( ClassCastException e)
	{
		loginErrors = null;
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Soldier Up - Entrepreneurs serving those who served us.</title>
<link href="Styles/styles.css" rel="stylesheet" />
<link href="Styles/formStyles.css" rel="stylesheet" />
<script src="Scripts/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="Scripts/jquery.inputmask.js" type="text/javascript"></script>
<script src="Scripts/jquery.inputmask.date.extensions.js" type="text/javascript"></script>
<script src="Scripts/jquery.form.js" type="text/javascript"></script>
</head>
<body>
<jsp:include page="Includes/header.jsp"></jsp:include>
<section>
	<form id="main-login-form" action="Login" method="post" style="display:inline; margin:auto;">
		<h1>Login to get started</h1>
		<p>
			<label>Email:</label><input id="login_email" type="email" name="email"/>
			<% if( loginErrors != null && loginErrors.containsKey("missing_email") )
			   {							
					out.println("<span class=\"login_error\"> "+ loginErrors.get("missing_email")  +" </span>");
			   }
			%>
		</p>
		<p>
			<label>Password:</label><input type="password" name="password" />
			<% if( loginErrors != null && loginErrors.containsKey("missing_password") )
			   {							
					out.println("<span class=\"login_error\"> "+ loginErrors.get("missing_password")  +" </span>");
			   }
			%>
		</p>
		<p>
			<input class="align_end_of_label" type="submit" name="login" value="Login"/>
			<a id="forget_password" href="#">Forgot Password?</a>
			<% if( loginErrors != null && loginErrors.containsKey("final") )
			   {							
					out.println("<span class=\"login_error\"> "+ loginErrors.get("final")  +" </span>");
			   }
			%>
		</p>
		</form>
</section>
<script type="text/javascript">

$( document ).ready( function(){
	
	$( "#login_email" ).blur( function(){
		$.post( "UniqueEmail", 
							{ request:'email', email: $("#login_email").val() }, 
							function( data ){
								//alert( data );
							} );
	});
});
</script>
</body>
<% session.removeAttribute( "login_errors" ); %>
</html>
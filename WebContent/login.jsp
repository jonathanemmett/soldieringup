<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="org.soldieringup.User" %>
<%@ page import="org.soldieringup.Business" %>
<%@ page import="org.soldieringup.Veteran" %>
<%@ page import="org.soldieringup.database.MySQL" %>
<%@ page import="org.apache.commons.validator.routines.EmailValidator" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.sql.ResultSet;" %>
<%

	Map<String,String> loginResults = new HashMap<String,String>();
	if( request.getParameter("login") != null )
	{
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		if( email == null || !EmailValidator.getInstance().isValid( email ) )
		{
			loginResults.put( "missing_email", "Invalid");			
		}
		
		if( password == null )
		{
			loginResults.put( "missing_password", "Required" );
		}
		
		// If the map of login results is empty, we have not found any errors, so we can continue processing
		if( loginResults.isEmpty() )
		{
			MySQL databaseConnection = MySQL.getInstance();
			User loggedInUser = databaseConnection.validateUser( email, password );
			if( loggedInUser != null )
			{
				session.setAttribute( "id", loggedInUser.getId() );
				Map<Integer, Business> ownedBusinesses = databaseConnection.getBusinessesFromOwner( loggedInUser.getId() );
				if( ownedBusinesses.size() > 0 )
				{
					List<Integer> businessIDs = new ArrayList<Integer>( ownedBusinesses.keySet() );
					session.setAttribute( "bid", businessIDs.get( 0 ) );
					session.removeAttribute( "vid" );
					response.sendRedirect( "editBusiness.jsp" );
				}

				Veteran foundVeteran = databaseConnection.getVeteran( loggedInUser.getId() );
				if( foundVeteran != null )
				{
					session.setAttribute( "vid", foundVeteran.getVid() );
					session.removeAttribute( "bid" );
					response.sendRedirect( "editVeteranProfile.jsp" );
				}	
			}
			else
			{
				loginResults.put( "final", "invalid");
			}
		}
		
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Soldier Up - Entrepreneurs serving those who served us.</title>
<link href="Styles/Styles.css" rel="stylesheet" />
<script src="Scripts/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="Scripts/jquery.inputmask.js" type="text/javascript"></script>
<script src="Scripts/jquery.inputmask.date.extensions.js" type="text/javascript"></script>
<script src="Scripts/jquery.form.js" type="text/javascript"></script>
</head>
<body>
<jsp:include page="Includes/header.jsp"></jsp:include>
<section>
	<h1 style="border-bottom:#000 solid 1px; font-size: medium; margin-top: 0px;">Login to get started</h1>
	<form id="login_form" action="login.jsp" method="post" style="text-align: center;">
		<p>
			<label style="display:inline-block;width:100px; text-align:right;">Email:</label>
			<input id="login_email" type="email" name="email"/>
			<% if( loginResults.containsKey("missing_email") )
			   {							
					out.println("<span class=\"error\"> "+ loginResults.get("missing_email")  +" </span>");
			   }
			%>
		</p>
		<p>
			<label style="display:inline-block;width:100px;text-align:right;">Password:</label>
			<input type="password" name="password" />
			<% if( loginResults.containsKey("missing_password") )
			   {							
					out.println("<span class=\"error\"> "+ loginResults.get("missing_password")  +" </span>");
			   }
			%>
		</p>
		<p>
			<input type="submit" style="margin-left:130px;" name="login" value="Login"/>
			<% if( loginResults.containsKey("final") )
			   {							
					out.println("<span class=\"error\"> "+ loginResults.get("final")  +" </span>");
			   }
			%>
			<a href="#">Forgot Password?</a>
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
</html>
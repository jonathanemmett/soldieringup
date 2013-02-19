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
					response.sendRedirect( "editBusiness.jsp" );
				}

				Veteran foundVeteran = databaseConnection.getVeteran( loggedInUser.getId() );
				if( foundVeteran != null )
				{
					session.setAttribute( "vid", foundVeteran.getVid() );
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
<p id="logo">
Soldier&#9733;Up
</p>
<section>
	<form action="login.jsp" method="post">
		<p>
			<label>Email:</label>
			<input id="email" type="email" name="email"/>
			<% if( loginResults.containsKey("missing_email") )
			   {							
					out.println("<span class=\"error\"> "+ loginResults.get("missing_email")  +" </span>");
			   }
			%>
		</p>
		<p>
			<label>Password:</label>
			<input type="password" name="password" />
			<% if( loginResults.containsKey("missing_password") )
			   {							
					out.println("<span class=\"error\"> "+ loginResults.get("missing_password")  +" </span>");
			   }
			%>
		</p>
		<p>
			<input type="submit" name="login" value="Login"/>
			<% if( loginResults.containsKey("final") )
			   {							
					out.println("<span class=\"error\"> "+ loginResults.get("final")  +" </span>");
			   }
			%>
		</p>
	</form>
</section>
<script type="text/javascript">

$( document ).ready( function(){
	
	$( "#email" ).blur( function(){
		$.post( "UniqueEmail", 
							{ request:'email', email: $("#email").val() }, 
							function( data ){
								//alert( data );
							} );
	});
});
</script>
</body>
</html>
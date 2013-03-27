<div id="navigation_header">
	<span id="logo">
		Soldier&#9733;Up
	</span>
	<span id="navigation_links">
	<% if( session.getAttribute( "edit_account_type" ) != null )
	   { 
		   if( session.getAttribute( "editing_account_type" ).equals( "veteran" ) )
		   {	
		%>
			<a href="editBusiness.jsp">Edit Business Profile</a>
			<a href="#">Mailbox</a>
			<a href="#">Logout</a>
		<%} 
		  else if( session.getAttribute( "vid" ) != null )
		  { %>
				<a href="#">Browse Entrepreneurs</a>
				<a href="#">Manage Questions</a>
				<a href="editVeteranProfile.jsp">Edit Profile</a>
				<a href="#">Mailbox</a>
				<a href="#">Logout</a>
		<%}
	  }
	  else 
	  { %>
		<a href="login.jsp">Login</a>
	<%} %>
	</span>
</div>
<div id="navigation_header">
	<span id="logo">
		Soldier&#9733;Up
	</span>
	<span id="navigation_links">
	<% if( session.getAttribute( "editing_account_type" ) != null )
	   { 
		 if( session.getAttribute( "editing_account_type" ).equals( "business" ) )
		 {	
		%>
			<a href="editBusiness.jsp">Edit Profile</a>
	   <%} 
		 else
		 { %>
		  	<a href="businessSearch.jsp">Browse Entrepreneurs</a>
			<a href="Questions.jsp">Manage Questions</a>
			<a href="editVeteranProfile.jsp">Edit Profile</a>
				
		<%}%>
			<a href="#">Mailbox</a>
			<a href="Logout">Logout</a>
		<%
	  }
	  else 
	  { %>
		<a href="login.jsp">Login</a>
	<%} %>
	</span>
</div>
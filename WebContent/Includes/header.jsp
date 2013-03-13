<div id="navigation_header">
	<span id="logo">
		Soldier&#9733;Up
	</span>
	<span id="navigation_links">
	<% if( session.getAttribute( "bid" ) != null ){ %>
		<a href="editBusiness.jsp">Edit Business Profile</a>
		<a href="#">Mailbox</a>
		<a href="#">Logout</a>
	<%} else if( session.getAttribute( "vid" ) != null ){ %>
		<a href="#">Browse Entrepreneurs</a>
		<a href="#">Manage Questions</a>
		<a href="editVeterayProfile.jsp">Edit Profile</a>
		<a href="#">Mailbox</a>
		<a href="#">Logout</a>
	<%} else { %>
		<a href="login.jsp">Login</a>
	<%} %>
	</span>
</div>
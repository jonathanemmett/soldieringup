<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="org.soldieringup.database.MySQL"%>
<%@ page import="org.soldieringup.Business"%>
<%@ page import="org.soldieringup.User"%>
<%@ page import="org.soldieringup.ZIP"%>
<%@ page import="org.soldieringup.Utilities"%>
<%@ page import="org.soldieringup.Veteran"%>
<%@ page import="org.apache.commons.validator.routines.EmailValidator;" %>
<!DOCTYPE html>
<head>
<% 
Map<String, String> inputErrors = (Map<String, String>)request.getAttribute( "registration_errors" );

MySQL databaseConnection = MySQL.getInstance();

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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Register</title>
<link href="Styles/styles.css" rel="stylesheet" />
<link href="Styles/formStyles.css" rel="stylesheet" />
<!--[if lt IE 9]>
    <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]--> 
<script src="Scripts/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="Scripts/jquery.inputmask.js" type="text/javascript"></script>
<script src="Scripts/jquery.inputmask.date.extensions.js" type="text/javascript"></script>
<script src="Scripts/jquery.form.js" type="text/javascript"></script>
</head>

<body>
<jsp:include page="Includes/header.jsp"></jsp:include>
<section>
	<article id="soldier_up_information">
		<ul>
			<li id="info_heading">Sign up Today!</li>
			<li>Veterans made the ultimate sacrifice for us! They gave you a life, now give them one!</li>
			<li>Earn badges to track your progress!</li>
			<li>Easy communication between you and other veterans!</li>
			<li>Receive your own profile page for others to see what help you provide!</li>
		</ul>
		<h1 style="border-bottom:#000 solid 1px; font-size: medium; margin-top: 20px;">Registered? Login!</h1>
		<form id="login_form" action="Login" method="post">
			<% if( loginErrors != null && !loginErrors.isEmpty()) {%><p class="login_error">Email or password is invalid </p><%} %>
			<p>
				<label style="display:inline-block;width:100px; text-align:left;">Email:</label>
				<input id="login_email" type="email" name="email"/>
			</p>
			<p>
				<label style="display:inline-block;width:100px;text-align:left;">Password:</label>
				<input type="password" name="password" />
			</p>
			<p>
				<input type="submit" style="margin-left:105px;" name="login" value="Login"/>
				<a href="#">Forgot Password?</a>
			</p>
		</form>
	</article>
	<div id="signup_form">
		<form method="post" action="BusinessRegistration">
			<h1>Primary Contact Information</h1>
			<span class="fields">
				<label>First Name<% Utilities.printErrorSpan( out, "first_name", inputErrors );%></label>
				<input type="text" name="first_name" required value="<%=Utilities.getValueFromString( request.getParameter( "first_name" ) ) %>" />
			</span>
			<span class="fields">
				<label>Last Name<% Utilities.printErrorSpan( out, "last_name", inputErrors );%></label>
				<input type="text" name="last_name" required value="<%=Utilities.getValueFromString( request.getParameter( "last_name" ) ) %>" />
			</span>
			<span class="fields full_length">
				<label>Address<% Utilities.printErrorSpan( out, "contact_address", inputErrors );%></label>
				<input type="text" name="contact_address" value="<%=Utilities.getValueFromString( request.getParameter( "contact_address" ) ) %>"  />
			</span>
			<span class="fields">
				<label>State</label>
				<select name="contact_state">
					<option value="AL">Alabama</option>
					<option value="AK">Alaska</option>
					<option value="AZ">Arizona</option>
					<option value="AR">Arkansas</option>
					<option value="CA">California</option>
					<option value="CO">Colorado</option>
					<option value="CT">Connecticut</option>
					<option value="DE">Delaware</option>
					<option value="FL">Florida</option>
					<option value="GA">Georgia</option>
					<option value="HI">Hawaii</option>
					<option value="ID">Idaho</option>
					<option value="IL">Illinois</option>
					<option value="IN">Indiana</option>
					<option value="IA">Iowa</option>
					<option value="KS">Kansas</option>
					<option value="KY">Kentucky</option>
					<option value="LA">Louisiana</option>
					<option value="ME">Maine</option>
					<option value="MD">Maryland</option>
					<option value="MA">Massachusetts</option>
					<option value="MI">Michigan</option>
					<option value="MN">Minnesota</option>
					<option value="MS">Mississippi</option>
					<option value="MO">Missouri</option>
					<option value="MT">Montana</option>
					<option value="NE">Nebraska</option>
					<option value="NV">Nevada</option>
					<option value="NH">New Hampshire</option>
					<option value="NJ">New Jersey</option>
					<option value="NM">New Mexico</option>
					<option value="NY">New York</option>
					<option value="NC">North Carolina</option>
					<option value="ND">North Dakota</option>
					<option value="OH">Ohio</option>
					<option value="OK">Oklahoma</option>
					<option value="OR">Oregon</option>
					<option value="PA">Pennsylvania</option>
					<option value="RI">Rhode Island</option>
					<option value="SC">South Carolina</option>
					<option value="SD">South Dakota</option>
					<option value="TN">Tennessee</option>
					<option value="TX">Texas</option>
					<option value="UT">Utah</option>
					<option value="VT">Vermont</option>
					<option value="VA">Virginia</option>
					<option value="WA">Washington</option>
					<option value="WV">West Virginia</option>
					<option value="WI">Wisconsin</option>
					<option value="WY">Wyoming</option>
				</select>
			</span>
			<span class="fields">
				<label>City<% Utilities.printErrorSpan( out, "contact_city", inputErrors );%></label>
				<input type="text" name="contact_city" required value="<%=Utilities.getValueFromString( request.getParameter( "contact_city" ) ) %>"  />
			</span>
			<span class="fields">
				<label>Email<% Utilities.printErrorSpan( out, "contact_email", inputErrors );%></label>
				<input type="text" name="contact_email" required value="<%=Utilities.getValueFromString( request.getParameter( "contact_email" ) ) %>"  />
			</span>
			<span class="fields">
				<label>ZIP<% Utilities.printErrorSpan( out, "contact_ZIP", inputErrors );%></label>
				<input type="text" name="contact_ZIP" required value="<%=Utilities.getValueFromString( request.getParameter( "contact_ZIP" ) ) %>" />
			</span>
			<span class="fields">
				<label>Primary Phone<% Utilities.printErrorSpan( out, "contact_primary_number", inputErrors );%></label>
				<input id="primary_phone" type="text" name="contact_primary_number" required value="<%=Utilities.getValueFromString( request.getParameter( "contact_primary_number" ) ) %>" />
			</span>
			<span class="fields">
				<label>Secondary Phone</label>
				<input id="secondary_phone" type="text" name="contact_secondary_number" required value="<%=Utilities.getValueFromString( request.getParameter( "contact_secondary_number" ) ) %>" />
			</span>
			<h1 class="">Business Details</h1>
			<span id="original_division_box" class="fields full_length">
				<label>Business<% Utilities.printErrorSpan( out, "business_name", inputErrors );%></label>
				<input id="business_name" name="business_name" value="<%=Utilities.getValueFromString( request.getParameter( "business_name" ) ) %>" />
			</span>
			<span class="fields full_length">
				<label>Short Summary<% Utilities.printErrorSpan( out, "business_short_summary", inputErrors );%></label>
				<textarea name="business_short_summary" rows="5"><%=Utilities.getValueFromString( request.getParameter( "business_short_summary" ) ) %></textarea>
			</span>
			<span class="fields full_length">
				<label>Detailed Summary<% Utilities.printErrorSpan( out, "business_long_summary", inputErrors );%></label>
				<textarea name="business_long_summary" rows="10"><%=Utilities.getValueFromString( request.getParameter( "business_long_summary" ) ) %></textarea>
			</span>
			<span class="fields full_length">
				<label>Street<% Utilities.printErrorSpan( out, "business_address", inputErrors );%></label>
				<input type="text" name="business_address" required="required" value="<%=Utilities.getValueFromString( request.getParameter( "first_name" ) ) %>" />
			</span>
			<span class="fields full_length">
				<label>City<% Utilities.printErrorSpan( out, "business_city", inputErrors );%></label>
				<input type="text" name="business_city" required="required" value="<%=Utilities.getValueFromString( request.getParameter( "business_city" ) ) %>" />
			</span>
			<span class="fields">
				<label>State</label>
				<select name="business_state">
					<option value="AL">Alabama</option>
					<option value="AK">Alaska</option>
					<option value="AZ">Arizona</option>
					<option value="AR">Arkansas</option>
					<option value="CA">California</option>
					<option value="CO">Colorado</option>
					<option value="CT">Connecticut</option>
					<option value="DE">Delaware</option>
					<option value="FL">Florida</option>
					<option value="GA">Georgia</option>
					<option value="HI">Hawaii</option>
					<option value="ID">Idaho</option>
					<option value="IL">Illinois</option>
					<option value="IN">Indiana</option>
					<option value="IA">Iowa</option>
					<option value="KS">Kansas</option>
					<option value="KY">Kentucky</option>
					<option value="LA">Louisiana</option>
					<option value="ME">Maine</option>
					<option value="MD">Maryland</option>
					<option value="MA">Massachusetts</option>
					<option value="MI">Michigan</option>
					<option value="MN">Minnesota</option>
					<option value="MS">Mississippi</option>
					<option value="MO">Missouri</option>
					<option value="MT">Montana</option>
					<option value="NE">Nebraska</option>
					<option value="NV">Nevada</option>
					<option value="NH">New Hampshire</option>
					<option value="NJ">New Jersey</option>
					<option value="NM">New Mexico</option>
					<option value="NY">New York</option>
					<option value="NC">North Carolina</option>
					<option value="ND">North Dakota</option>
					<option value="OH">Ohio</option>
					<option value="OK">Oklahoma</option>
					<option value="OR">Oregon</option>
					<option value="PA">Pennsylvania</option>
					<option value="RI">Rhode Island</option>
					<option value="SC">South Carolina</option>
					<option value="SD">South Dakota</option>
					<option value="TN">Tennessee</option>
					<option value="TX">Texas</option>
					<option value="UT">Utah</option>
					<option value="VT">Vermont</option>
					<option value="VA">Virginia</option>
					<option value="WA">Washington</option>
					<option value="WV">West Virginia</option>
					<option value="WI">Wisconsin</option>
					<option value="WY">Wyoming</option>
				</select>
			</span>
			<span class="fields">
				<label>ZIP<% Utilities.printErrorSpan( out, "business_ZIP", inputErrors );%></label>
				<input type="text" name="business_ZIP" required value="<%=Utilities.getValueFromString( request.getParameter( "business_ZIP" ) ) %>" />
			</span>
			<span id="submit_new_user" class="fields full_length">
				<input type="submit" value="Sign Up" required="required" value="Soldier&#9733;Up Today!" />
			</span>
		</form>
	</div>
</section>
<script type="text/javascript" >
window.onload = function( event ){
	maskNumbers();
};

function maskNumbers(){
	$("#primary_phone").inputmask("mask", {mask : "(999) 999-9999" } );
	$("#secondary_phone").inputmask("mask", {mask : "(999) 999-9999" } );
}

</script>
</body>
<% session.removeAttribute( "login_errors" ); %>
</html>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="org.soldieringup.Business" %>
<%@ page import="org.soldieringup.database.MySQL" %>
<%@ page import="org.soldieringup.Divisions"%>
<%@ page import="org.soldieringup.User"%>
<%@ page import="org.soldieringup.Veteran" %>
<%@ page import="org.soldieringup.ZIP;"%>

<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Register</title>
<link href="Styles/Styles.css" rel="stylesheet" />
<link href="Styles/formStyles.css" rel="stylesheet" />
<!--[if lt IE 9]>
    <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]--> 
<script src="Scripts/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="Scripts/jquery.inputmask.js" type="text/javascript"></script>
<script src="Scripts/jquery.inputmask.date.extensions.js" type="text/javascript"></script>
</head>

<body>
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

MySQL databaseConnection = MySQL.getInstance();
 %>
<jsp:include page="Includes/header.jsp"></jsp:include>
<section>
	<article id="soldier_up_information">
		<ul>
			<li id="info_heading">Sign up Today!</li>
			<li>Achieve help from expert entrepreneurs!</li>
			<li>Hassle Free registration!</li>
			<li>Get started with the business you want!</li>
			<li>You've Soldiered Up for us! Now let us Soldier Up for you!</li>
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
		<form method="post" method="post" action="VeteranRegistration">
			<h1>Fill out the following fields</h1>
			<span class="fields">
				<label>First Name</label>
				<input type="text" name="first_name" required />
			</span>
			<span class="fields">
				<label>Last Name</label>
				<input type="text" name="last_name" required/>
			</span>
			<span class="fields">
				<label>Password</label>
				<input type="password" name="password" required />
			</span>
			<span class="fields">
				<label>Confirm</label>
				<input type="password" name="redo_password" required/>
			</span>
			<span class="fields full_length">
				<label>Address</label>
				<input type="text" name="contact_address" />
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
				<label>City</label>
				<input type="text" name="contact_city" required />
			</span>
			
			<span class="fields">
				<label>Email</label>
				<input type="text" name="contact_email" required />
			</span>
			<span class="fields">
				<label>ZIP</label>
				<input type="text" name="contact_ZIP" required/>
			</span>
			<span class="fields">
				<label>Primary Phone</label>
				<input id="primary_phone" type="text" name="contact_primary_number" required />
			</span>
			<span class="fields">
				<label>Secondary Phone</label>
				<input id="secondary_phone" type="text" name="contact_secondary_number" required/>
			</span>
			<span class="fields full_length">
				<label>Describe the business you are trying to start</label>
				<textarea rows="5" cols="30" name="goal"></textarea>
			</span>
			<span id="submit_new_user" class="fields full_length">
				<input type="submit" value="Sign Up" required="required" />
			</span>
		</form>
	</div>
</section>
<script type="text/javascript" >
/*var addElement = document.getElementById("add_position");
  addElement.addEventListener("mousedown", addPosition, true);*/

window.onload = function( event ){
	maskNumbers();
	maskDates();
};

// Adds new experience boxes to the board
function addPosition(e){
	var submitSpan = document.getElementById("submit_new_user");

	// Create all the fields needed for the new experience field
	var divisionSpan = createDivision();
	var positionSpan = createPosition();
	var startDate = createTime("start_date");
	var endDate = createTime("end_date");
	var description = createDescription();
	
	//Add the delete box to the new position
	var deleteBox = document.createElement("span");
	deleteBox.setAttribute("class","remove_fields");
	positionSpan.appendChild( deleteBox );
	disableClickRemove( deleteBox, submitSpan.parentNode, divisionSpan, positionSpan, startDate, endDate, description );
	
	// Insert all of the new fields into a document
	submitSpan.parentNode.insertBefore( divisionSpan, submitSpan );
	submitSpan.parentNode.insertBefore( positionSpan, submitSpan );
	submitSpan.parentNode.insertBefore( startDate, submitSpan );
	submitSpan.parentNode.insertBefore( endDate, submitSpan );
	submitSpan.parentNode.insertBefore( description, submitSpan );
	e.preventDefault();
}

function maskNumbers()
{
	$("#primary_phone").inputmask("mask", {mask : "(999) 999-9999" } );
	$("#secondary_phone").inputmask("mask", {mask : "(999) 999-9999" } );
}

function maskDates()
{
	var startDates = document.getElementsByName("start_date");
	var endDates = document.getElementsByName("end_date");
	$(startDates).inputmask({"mask" : "99/9999", "placeholder" : "mm/yyyy" });
	$(endDates).inputmask({"mask" : "99/9999", "placeholder" : "mm/yyyy" });
}

function createTime(string){
	var timeSpan = document.createElement("span");
	timeSpan.setAttribute("class","fields");

	var label = document.createElement("label");
	var labelText = string == "start_date" ? "Start Time" : "End Time";
	var name = string == "start_date" ? string : "end_date";
	var labelTextNode = document.createTextNode( labelText );
	label.appendChild( labelTextNode );
	timeSpan.appendChild( label );
	
	var textInput = document.createElement("input");
	textInput.setAttribute("type","text");
	textInput.setAttribute("required","required");
	textInput.setAttribute("name",name);
	$( textInput ).inputmask({"mask" : "99/9999", "placeholder" : "mm/yyyy" });
	timeSpan.appendChild(textInput);

	return timeSpan;
}

function createPosition(){
	var positionSpan = document.createElement("span");
	positionSpan.setAttribute("class","fields");
	var positionLabel = document.createElement("label");
	var positionLabelText = document.createTextNode("Position");
	positionLabel.appendChild(positionLabelText);
	positionSpan.appendChild(positionLabel);
	
	var positionInput = document.createElement("input");
	positionInput.setAttribute("type","text");
	positionInput.setAttribute("name","position");
	positionInput.setAttribute("required","required");
	positionSpan.appendChild(positionInput);
	
	return positionSpan;
}

function createDivision(){
	var divisionSpan = document.createElement("span");
	divisionSpan.setAttribute("class","fields");
	
	// Label used to create a division of the military
	var positionLabel = document.createElement("label");
	var positionLabelText = document.createTextNode("Division");
	positionLabel.appendChild( positionLabelText );
	divisionSpan.appendChild( positionLabel );
	
	// Division textarea for veteran to fill in
	var divisionsBox = document.createElement("select");
	var originalBox = document.getElementById("original_division_box");
	divisionsBox.innerHTML = originalBox.innerHTML; 
	divisionsBox.setAttribute("name","division");
	divisionSpan.appendChild( divisionsBox );
	return divisionSpan;
}

function createDescription(){
	var spanSection = document.createElement("span");
	spanSection.setAttribute("class","fields full_length");
	
	// Label for the description of the service
	var divisionLabel = document.createElement("label");
	var divisionLabelText = document.createTextNode("Description");
	divisionLabel.appendChild( divisionLabelText );
	spanSection.appendChild( divisionLabel );
	
	// Textarea to describe the division
	var textBox = document.createElement("textarea");
	textBox.setAttribute("rows","5");
	textBox.setAttribute("required","required");
	textBox.setAttribute("name","description");
	spanSection.appendChild( textBox );
	return spanSection;
}

function disableClickRemove( deleteButton, parent, division, position, startDate, endDate, description)
{
	deleteButton.addEventListener('click',function(e){
		parent.removeChild(division);
		parent.removeChild(position);
		parent.removeChild(startDate);
		parent.removeChild(endDate);
		parent.removeChild(description);
	}, false);
}

</script>
</body>
<% session.removeAttribute( "login_errors" ); %>
</html>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="org.soldieringup.Business" %>
<%@ page import="org.soldieringup.Photo" %>
<%@ page import="org.soldieringup.User" %>
<%@ page import="org.soldieringup.database.MySQL" %> 
<%@ page import="org.soldieringup.database.ZIP" %> 
<%
	if( session.getAttribute( "bid" ) == null ||
		session.getAttribute( "bid" ) == "" )
	{
	%><jsp:forward page="/login.jsp"/><% 
	}
	
	long bid = Long.valueOf( session.getAttribute( "bid" ).toString() );
	
	MySQL databaseConnection = MySQL.getInstance();	
	Business foundBusiness = databaseConnection.getBusiness( bid );
	ZIP businessZIP = databaseConnection.getZIP( foundBusiness.getZip() );
	Photo coverPhoto = databaseConnection.getPhotoFromId( foundBusiness.getCoverId() );
	User contactUser = databaseConnection.getUserFromId( foundBusiness.getContactId() );
%>  
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Soldier Up - Entrepreneurs serving those who served us.</title>
<link href="Styles/Styles.css" rel="stylesheet" />
<link href="Styles/formStyles.css" rel="stylesheet" />
<script src="Scripts/CoverImageDrag.js" type="text/javascript"></script>
<script src="Scripts/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="Scripts/jquery.inputmask.js" type="text/javascript"></script>
<script src="Scripts/jquery.inputmask.date.extensions.js" type="text/javascript"></script>
<script src="Scripts/jquery.form.js" type="text/javascript"></script>
</head>
<body>
<p id="logo">
Soldier&#9733;Up
</p>
<section style="margin-bottom:10px;">
<div id="cover_banner" style="border:#000 solid 1px; height:300px; position:relative; overflow:hidden;">
	<% if( coverPhoto != null){ out.println("<img id=\"cover_photo_display\" src=\"Images/"+ coverPhoto.getSrc()+"\"/>");} %>
	<form id="imageform" method="post" action="UploadImage" enctype="multipart/form-data">
		<input type="file" id="cover_photo" name="cover_photo" style="display:none;" />
		<span id="cover_photo_button" style="position:absolute; right:10px; bottom:10px;"><img src="Images/site_logo.png"/></span>
	</form>
	<form id="imageCoordinates" method="post" action="UploadImage" enctype="multipart/form-data">
		<input type="hidden" id="cover_photo_x" name="cover_photo_x" value="0"/>
		<input type="hidden" id="cover_photo_y" name="cover_photo_y" value="0"/>
	</form>
</div>
<% if( foundBusiness.getBusinessName() != null){ %>
<section style="width:30%; float:left;">
	<h3>Business Info</h3>
	<form id="edit_business_form" method="post" action="Business">
		<span class="fields full_length">
			<label>Name</label>
			<input type="text" name="first_name" required value="<%=foundBusiness.getBusinessName()%>" />
		</span>
		<span class="fields full_length">
			<label>Address</label>
			<input type="text" name="first_name" required value="<%=foundBusiness.getAddress()%>" />
		</span>
		<span class="fields full_length">
			<label>City</label>
			<input type="text" name="first_name" required value="<%=businessZIP.getCity()%>" />
		</span>
		<span class="fields full_length">
			<label>State</label>
			<input type="text" name="first_name" required value="<%=businessZIP.getState()%>" />
		</span>
		<span class="fields full_length" style="margin-bottom:0px;">
			<label>ZIP</label>
			<input type="text" name="first_name" required value="<%=foundBusiness.getZip()%>" />
		</span>
		<span class="fields full_length" style="margin-top:0px">
			<input type="submit"/>
		</span>
	</form>
	<h3>Contact info</h3>		
	<form id="update_user" action="UpdateUser">
		<span class="fields full_length">
			<label>First Name</label>
			<input type="text" name="first_name" value="<%=contactUser.getFirstName()%>" />
		</span>
		<span class="fields full_length">
			<label>Last Name</label>
			<input type="text" name="last_name" value="<%=contactUser.getLastName()%>" />
		</span>
		<span class="fields full_length">
			<label>Primary Number</label>
			<input type="text" name="primary_number" value="<%=contactUser.getPrimaryNumber()%>"  />
		</span>
		<span class="fields full_length">
			<label>Secondary Number</label>
			<input type="text" name="secondary_number" value="<%=contactUser.getSecondaryNumber()%>"  />
		</span>
		<span class="fields full_length">
			<label>Address</label>
			<input type="text" name="contact_address" value="<%=contactUser.getAddress()%>"  />
		</span>
		<span class="fields full_length">
			<label>Email</label>
			<input type="text" name="contact_email" value="<%=contactUser.getEmail()%>" />
		</span>
		<span class="fields full_length">
			<input type="submit" name="update_user" />
		</span>
	</form>
</section>
<section style="width:25%; float:right;">
	<h1>Tags</h1>
	<form>
	<input type="text" name="tag"/>
	<select id="hours"><option value="1">1</option></select>
	<p>Web Development 4 hours</p>
	<p></p>
	</form>
</section>
<section style="width:35%; margin-left:34%;">
	<h1>Short Summary</h1>
	<p><textarea style="width:98%; margin:0px; padding:0px;"><%=foundBusiness.getShortSummary()%></textarea></p>
	<h1>Long Summary</h1>
	<p><textarea style="width:98%;"><%=foundBusiness.getLongSummary()%></textarea></p>
	<h1>Tag List</h1>
<input type="text" name="current_tag"/>
<select name="hours_invested">
<option>1</option>
<option>2</option>
</select>
</section>
<%} %>
</section>
<div style="height:10px;"></div>
<script>

$(document).ready(function(){
	
	$('#cover_photo_button').click( loadImage );
	
	$('#cover_photo').live( 'change', function(){
		$('#imageform').ajaxForm({
			
			success: function( responseText ){

				if( $("#cover_photo_display") != null ){
					$("#cover_photo_display").remove();
				}
				
				var image = new Image();
				image.setAttribute("id","cover_photo_display");
				image.onload = function(){
					
					// Load the image into the container
					$("#cover_banner").prepend( image );
					addSubmitCoordinatesFunction();
					CoverImageDrag( image, document.getElementById("cover_photo_x"), document.getElementById("cover_photo_y") );
				}
				
				image.src = responseText;
			}
		}).submit();
	});
	
	$.post('TagSubscriber',{ cmd:'query' },function( data ){
			alert( data.tags[0] );
	},"json");
});

function addSubmitCoordinatesFunction()
{
	// Create the button that allows for submission
	var uploadButton = document.createElement("span");
	uploadButton.setAttribute("id","upload_coordinates");
	uploadButton.innerHTML = "Upload";
	
	$("#cover_banner").append( uploadButton );

	$( "#upload_coordinates" ).live( 'click', function(){
		$("#imageCoordinates").ajaxForm({
		
			success: function( responseText ){
				alert( "The result is " + responseText );
			}
		}).submit();
	});
}

function loadImage( event )
{
	var coverUploadInput = document.getElementById( "cover_photo" );
	coverUploadInput.click();
}

</script>
</body>
</html>




<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="org.soldieringup.Business" %>
<%@ page import="org.soldieringup.Photo" %>
<%@ page import="org.soldieringup.User" %>
<%@ page import="org.soldieringup.database.MySQL" %> 
<%@ page import="org.soldieringup.ZIP" %> 
<%
	if( session.getAttribute( "bid" ) == null ||
		session.getAttribute( "bid" ) == "" )
	{
	%><jsp:forward page="/login.jsp"/><% 
	}
	
	session.setAttribute( "editing_account_type" , "veteran" );

	long bid = Long.valueOf( session.getAttribute( "bid" ).toString() );
	
	MySQL databaseConnection = MySQL.getInstance();
	Business foundBusiness = databaseConnection.getBusiness( bid );
	ZIP businessZIP = databaseConnection.getZIP( foundBusiness.getZip() );
	User contactUser = databaseConnection.getUserFromId( foundBusiness.getContactId() );
%>  
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Soldier Up - Entrepreneurs serving those who served us.</title>
<link href="Styles/Styles.css" rel="stylesheet" />
<link href="Styles/formStyles.css" rel="stylesheet" />
<link href="Styles/EditProfileStyles.css" rel="stylesheet" />
<link href="Styles/jquery.Jcrop.min.css" rel="stylesheet" type="text/css"/>
<!--[if lt IE 9]>
    <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]--> 
<script src="Scripts/CoverImageDrag.js" type="text/javascript"></script>
<script src="Scripts/LightBox.js" type="text/javascript"></script>
<script src="Scripts/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="Scripts/jquery.dform-1.0.1.js" type="text/javascript"></script>
<script src="Scripts/jquery.inputmask.js" type="text/javascript"></script>
<script src="Scripts/jquery.inputmask.date.extensions.js" type="text/javascript"></script>
<script src="Scripts/jquery.form.js" type="text/javascript"></script>
<script src="Scripts/jquery.Jcrop.min.js" type="text/javascript"></script>
<script src="Scripts/CropPhoto.js" type="text/javascript"></script>
</head>
<body>
<p id="logo">
Soldier&#9733;Up<%=businessZIP.getState()+" "+foundBusiness.getZip()%>
</p>
<section id="edit_profile_section" style="margin-bottom:10px;">
<% if( foundBusiness.getBusinessName() != null){ %>
<div id="cover_banner" style="border:#000 solid 1px; height:300px; position:relative; overflow:hidden;">
	<% if( foundBusiness.getCoverSrc() != null){ out.println("<img id=\"cover_photo_display\" src=\"Images/"+ foundBusiness.getCoverSrc()+"\"/>");} %>
	<form id="upload_cover_image_form" method="post" action="UploadImage" enctype="multipart/form-data">
		<input type="hidden" name="type" value="cover"/>
		<input type="file" id="upload_cover_photo_input" name="photo" style="visibility:hidden;" />
		<span id="upload_cover_photo_button" style="position:absolute; right:10px; bottom:10px;"><img src="Images/site_logo.png"/></span>
	</form>
	<form id="upload_profile_coordinates_form" method="post" action="ResizePhoto">
		<input type="hidden" id="cover_photo_y" name="cover_photo_y" value="0"/>
	</form>
</div>
<div class="edit_profile_sub_section">
	<div class="edit_profile_sub_section_left_side">
		<h3>Profile Image</h3>
		<form id="upload_profile_pic_form" action="UploadImage" method="post">
			<input type="hidden" name="type" value="profile"/>
			<span id="profile_pic_display" style="display:inline-block; position:relative; width:100px; height:100px; border:#000 solid 1px;">
				<% if( foundBusiness.getProfilePhotoSrc() != null){ out.println("<img id=\"profile_photo_display\" src=\"Images/"+ foundBusiness.getProfilePhotoSrc()+"\"/>");} %>
				<span id="upload_profile_pic" style="width:20px; height:20px; background:#eee; position: absolute; right: 5px; bottom: 5px">
				</span>
			</span>
			<input id="upload_profile_pic_input" type="file" name="photo" style="visibility:hidden;" />
		</form>
	</div>
	<div class="edit_profile_sub_section_right_side">
		<h3>Password <span style="font-size:small">  (must be at least 8 characters long and contain at least 2 digits).</span></h3>
		<form id="password_change_form" method="post" action="Password">
			<p class="no_bottom_margin">
				<label for="change_password_new_password">Password</label>
				<label for="change_password_confirm_new_password">Confirm</label>
			</p>
			<input id="change_password_new_password" type="password"/>
			<input id="change_password_comfirm_new_password" type="password"/>
			<input id="change_password_submit" type="submit"/>
		</form>
	</div>
</div>
<div class="edit_profile_sub_section" style="clear:both;">
	<div class="edit_profile_sub_section_left_side">
		<h3>Business Info</h3>
		<form id="edit_business_form" method="post" action="UpdateBusiness">
			<span class="fields full_length">
				<label>Name</label>
				<input type="text" name="name" required value="<%=foundBusiness.getBusinessName()%>" />
			</span>
			<span class="fields full_length">
				<label>Address</label>
				<input type="text" name="address" required value="<%=foundBusiness.getAddress()%>" />
			</span>
			<span class="fields full_length">
				<label>City</label>
				<input type="text" name="city" required value="<%=businessZIP.getCity()%>" />
			</span>
			<span class="fields full_length">
				<label>State</label>
				<input type="text" name="state" required value="<%=businessZIP.getState()%>" />
			</span>
			<span class="fields full_length" style="margin-bottom:0px;">
				<label>ZIP</label>
				<input type="text" name="ZIP" required value="<%=foundBusiness.getZip()%>" />
			</span>
			<span class="fields full_length">
				<input type="submit" name="update_business" />
			</span>
		</form>
	</div>
	<div class="edit_profile_sub_section_right_side">
		<h3>Business Summary</h3>
		<form method="post" action="UpdateBusiness">
			<p class="no_bottom_margin">Short Summary<input type="submit" class="description_submit"/></p>
			<p class="no_top_margin"><textarea name="short_summary" class="description_boxes" cols="50" rows="15"><%=foundBusiness.getShortSummary()%></textarea></p>
		</form>
		<form method="post" action="UpdateBusiness">
			<p class="no_bottom_margin">Long Summary<input type="submit" class="description_submit"/></p>
			<p class="no_top_margin"><textarea name="long_summary" class="description_boxes" cols="50" rows="30"><%=foundBusiness.getLongSummary()%></textarea></p>
		</form>
	</div>
</div>
<div class="edit_profile_sub_section" style="clear:both;">
	<div class="edit_profile_sub_section_left_side">
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
	</div>
	<div class="edit_profile_sub_section_right_side">
		<h3>Tags</h3>
		<form>
			<p class="no_bottom_margin">Enter the skill you are willing to help with, and how long you can help for</p>
			<input type="text" name="tag"/>
			<select id="hours"><option value="1">1</option></select>
			<input type="submit" name="Insert"/>
			<p style="overflow:hidden; border-radius: 20px;position:relative; border:#000 solid 1px; padding:3px 0px 3px 10px;">Web Development 
				<span style="padding:3px 10px 3px 5px; position:absolute;right:0px;top:0px;display:inline-block; float:right;color:#ffffff;background-color:#000000; border-bottom-right-radius:20px; border-top-right-radius:20px;">4 hours</span>
			</p>
		</form>
	</div>
</div>
<section>
</section>
<%} %>
</section>
<div style="height:10px;"></div>
<script>

$(document).ready(function() { 
	
	// Ajax options for uploading of profile and cover photos
	var coverPhotoUploadAjaxOptions = {
		dataType: 'json',
		type: 'post',
		success: coverUploadComplete
	};
	
	var profilePhotoUploadAjaxOptions = {
			dataType: 'text',
			type: 'get',
			success: profilePhotoUploadComplete
	};
	
	// Set up the profile photo to upload and start the image cropper
	// when the input button for profile photos are clicked.
	$( "#upload_profile_pic" ).click(function(){
		$( "#upload_profile_pic_input" ).click();
	});
	
	$( "#upload_profile_pic_form" ).submit( function(){
		$(this).ajaxSubmit( profilePhotoUploadAjaxOptions );
		return false;
	});
	
	$( "#upload_profile_pic_input" ).change(function(){
		$( "#upload_profile_pic_form" ).submit();
	});
	
	
	// Set up the cover photo to upload a cover image when a file is selected,
	// and allow the user to drag it to it's appropriate position
	$( '#upload_cover_image_form' ).submit(function() {
		$(this).ajaxSubmit( coverPhotoUploadAjaxOptions ); 
		return false; 
	});
	
	$( '#upload_cover_photo_button' ).click( function(){
		var coverUploadInput = document.getElementById( "upload_cover_photo_input" );
		coverUploadInput.click();	
	});

	$( '#upload_cover_photo_input' ).change( function() {
		$( '#upload_cover_image_form' ).submit();
	});
}); 

/**
 * Handles when a cover photo has been successfully uploaded to the server
 * @param responseText Response text coming from the ajax request
 * @param statusText Text describing whether the request was successful or not
 * @param xhr Request object
 * @param $form Form that the request was uploaded from
 */
function coverUploadComplete( responseText, statusText, xhr, $form ) {
	$.get("UploadImage",{ temp_upload:"true" },function(data){
		if( data != null){
			var image = new Image();
				
			// Remove the current cover photo displayed if it exists.
			$( "#cover_banner #cover_photo_display" ).remove();
				
			// Add the image to the cover photo container, and give it functionality so that a user
			// can drag it up and down.
			image.setAttribute("id","cover_photo_display");
			image.onload = function(){
				$("#cover_banner").prepend( image );
				addSubmitCoordinatesFunction();
				CoverImageDrag( image, document.getElementById( "cover_photo_y" ) );
			};
			image.src = "TempUploads/" + data.temp_cover_src;
		}
	},"json");
}
	
/**
 * Handles after a user has cropped the uploaded profile picture to what they
 * wish to display
 */
function cropProfilePicComplete( responseText, statusText, xhr, $form ){	
	$.get("UploadImage",{ temp_upload:"true" },function(data){
		if( data != null){
			
			// Remove the profile pic from the screen if one currently exists
			$( "#profile_photo_display" ).remove();
			
			// Insert the new profile pic into the profile pic container
			var image = new Image();
			image.setAttribute( "id", "profile_photo_display" );
			image.onload = function(){
				$( "#profile_pic_display" ).prepend( image );
			};
			image.src = "Images/" + data.temp_profile_src;
		}
	},"json");
}

/**
 * Handles after a user uploaded a profile pic. The request retrieves the upload photo,
 * and adds it to a LightBox for cropping the image
 */
function profilePhotoUploadComplete( responseText, statusText, xhr, $form ){
	// The UploadImage request returns the srcs of the photos they uploaded
	// for their profile when sent with a GET request.
	$.get( "UploadImage" , function(data){
		if( data != null){
			
			var image = new Image();
			image.setAttribute( "id", "upload_profile_pic_picture" );
			image.onload = function( event ){
				
				// Create a new LightBox to place the ImageCropper on
				var box = new LightBox( image );
				
				var body = document.getElementsByTagName( "body" )[0];
				body.appendChild( box.retrieveDiv() );
				
				var form = document.createElement( "form" );
				form.setAttribute( "id", "upload_profile_pic_coordinates_form" );
				box.appendElement( form );
				
				// This is the form that will be sent to the resizePhoto request
				// for profile pictures.
				$( "#upload_profile_pic_coordinates_form" ).dform({
						"action":"ResizePhoto",
						"method":"post",
						"html" :
						[
							{
								"id" : "upload_profile_pic_x",
								"name" : "upload_profile_pic_x",
								"type" : "hidden"
							},
							{
								"id" : "upload_profile_pic_y",
								"name" : "upload_profile_pic_y",
								"type" : "hidden"
							},
							{
								"id" : "upload_profile_pic_width",
								"name" : "upload_profile_pic_width",
								"type" : "hidden"
							},
							{
								"id" : "upload_profile_pic_height",
								"name" : "upload_profile_pic_height",
								"type" : "hidden"
							}
						]
					});

				// Options to be added to the profile pic readjust request   
				var profilePicUploadOptions = {
						success: cropProfilePicComplete
					};

				$( "#upload_profile_pic_coordinates_form" ).submit( function(){
					$( this ).ajaxSubmit( profilePicUploadOptions );
					return false;
				});
				
				var exitButton = document.createElement( "span" );
				exitButton.setAttribute( 'class', 'remove_fields' );

				$( exitButton ).click(function(){
			        $( "#upload_profile_pic_coordinates_form" ).submit();
					$( box.retrieveDiv() ).remove();
				});
				
				box.appendElement( exitButton );
				CropPhoto( "#upload_profile_pic_picture", 
						   "upload_profile_pic_x",
						   "upload_profile_pic_y", 
						   "upload_profile_pic_width", 
						   "upload_profile_pic_height" );
				
				$(document).bind( 'mousewheel DOMMouseScroll',function(){ 
			        stopWheel();
			    });
			};

			image.src = "TempUploads/"+data.temp_profile_src;		}
	},"json");
}

/**
 * Function that prevents the user from scrolling the page. This allows us to display
 * a LightBox without worrying about the user scrolling through the page.
 */
function stopWheel(e){
    if(!e){ /* IE7, IE8, Chrome, Safari */ 
        e = window.event; 
    }
    if(e.preventDefault) { /* Chrome, Safari, Firefox */ 
        e.preventDefault(); 
    } 
    e.returnValue = false; /* IE7, IE8 */
}

/**
 * Adds the functionality to allow a user to submit the coordinates of the
 * cropping box for profile images.
 */
function addSubmitCoordinatesFunction()
{
	// Create the button that allows for submission
	var uploadButton = document.createElement("span");
	uploadButton.setAttribute("id","upload_coordinates");
	uploadButton.innerHTML = "Upload";
	
	$( "#cover_banner" ).append( uploadButton );
	
	alert( "Button added ");
	$( "#upload_coordinates" ).click( function(){
		alert("Button clicked");
		$( "#upload_profile_coordinates_form" ).ajaxForm({
			dataType: 'text',
			success: function( responseText ){
				alert("Request complete!");
				$( uploadButton ).remove();

				$.get("UploadImage",function(data){
					if( data != null){
						var image = new Image();
						alert("Still strong!");	
						// Remove the current cover photo displayed if it exists.
						$( "#cover_banner #cover_photo_display" ).remove();
							
						// Add the image to the cover photo container, and give it functionality so that a user
						// can drag it up and down.
						image.setAttribute("id","cover_photo_display");
						image.onload = function(){
							$("#cover_banner").prepend( image );
						};
						image.src = "Images/" + data.temp_cover_src;
					}
				},"json");
			}
		}).submit();
	});
}
</script>
</body>
</html>




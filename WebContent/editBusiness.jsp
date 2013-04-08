<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="java.util.*" %>
<%@ page import="org.soldieringup.Business" %>
<%@ page import="org.soldieringup.Photo" %>
<%@ page import="org.soldieringup.Tag" %>
<%@ page import="org.soldieringup.User" %>
<%@ page import="org.soldieringup.database.MySQL" %> 
<%@ page import="org.soldieringup.ZIP" %> 
<%
	session.setAttribute( "uid", 39 );
	session.setAttribute( "aid", 14 );
	session.setAttribute( "editing_account_type" , "business" );

	if( session.getAttribute( "aid" ) == null ||
		session.getAttribute( "aid" ) == "" ||
		session.getAttribute( "editing_account_type" ) == null ||
		session.getAttribute( "editing_account_type" ) != "business"
		)
	{
	%><jsp:forward page="/login.jsp"/><% 
	}
	
	session.setAttribute( "editing_account_type" , "business" );

	long bid = Long.valueOf( session.getAttribute( "aid" ).toString() );
	
	MySQL databaseConnection = MySQL.getInstance();
	Business foundBusiness = databaseConnection.getBusiness( bid );
	ZIP businessZIP = databaseConnection.getZIP( foundBusiness.getZip() );
	User contactUser = databaseConnection.getUserFromId( foundBusiness.getUid() );
%>  
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Soldier Up - Entrepreneurs serving those who served us.</title>
<link href="Styles/Styles.css" rel="stylesheet" type="text/css" />
<link href="Styles/formStyles.css" rel="stylesheet" />
<link href="Styles/EditProfileStyles.css" rel="stylesheet" />
<link href="Styles/jquery.Jcrop.min.css" rel="stylesheet" />
<link href="Styles/jquery-ui-autocomplete.min.css" rel="stylesheet" />
<!--
<link href="http://code.jquery.com/ui/1.10.1/themes/base/jquery-ui.css" rel="stylesheet" />
-->
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
<!--
<script src="http://code.jquery.com/ui/1.10.1/jquery-ui.js"></script>
-->
<script src="Scripts/jquery-ui-autocomplete.min.js"></script>
<script src="Scripts/CropPhoto.js" type="text/javascript"></script>
<script src="Scripts/CoverImageScripts.js" type="text/javascript"></script>
<script src="Scripts/ProfileImageScripts.js" type="text/javascript"></script>
<script src="Scripts/TagScripts.js" type="text/javascript"></script>
</head>
<body>
<jsp:include page="Includes/header.jsp"></jsp:include>
<section id="edit_profile_section" style="margin-bottom:10px;">
<% if( foundBusiness.getName() != null){ %>
<div id="cover_banner">
	<% if( foundBusiness.getCoverSrc() != null){ out.println("<img id=\"cover_photo_display\" src=\"Images/"+ foundBusiness.getCoverSrc()+"\"/>");} %>
	<form id="upload_cover_image_form" method="post" action="UploadImage" enctype="multipart/form-data">
		<input type="hidden" name="type" value="cover"/>
		<input type="file" id="upload_cover_photo_input" name="photo" />
		<span id="upload_cover_photo_button"><img src="Images/site_logo.png"/></span>
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
				<% if( foundBusiness.getProfileSrc() != null){ out.println("<img id=\"profile_photo_display\" src=\"Images/"+ foundBusiness.getProfileSrc()+"\"/>");} %>
				<span id="upload_profile_pic" style="width:20px; height:20px; background:#eee; position: absolute; right: 5px; bottom: 5px">
				</span>
			</span>
			<input id="upload_profile_pic_input" type="file" name="photo" style="visibility:hidden;" />
		</form>
	</div>
	<div class="edit_profile_sub_section_right_side">
		<h3>Password <span style="font-size:small">  (must be at least 8 characters long and contain at least 2 digits).</span></h3>
		<form id="password_change_form" method="post" action="UpdatePassword">
			<p class="no_bottom_margin">
				<label for=change_password_new_password>Password</label>
				<label for="change_password_confirm_new_password">Confirm</label>
			</p>
			<input id="change_password_new_password" name="password" type="password"/>
			<input id="change_password_confirm_new_password" name="confirm_password" type="password"/>
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
				<input type="text" name="name" required value="<%=foundBusiness.getName()%>" />
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
<div class="edit_profile_sub_section">
	<div class="edit_profile_sub_section_left_side">
		<h3>Contact info</h3>		
		<form id="update_user" action="UpdateUserProfile" method="post">
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
			<input type="text" name="primary_number" value="<%=contactUser.getPrimary_number()%>"  />
		</span>
		<span class="fields full_length">
			<label>Secondary Number</label>
			<input type="text" name="secondary_number" value="<%=contactUser.getSecondary_number()%>"  />
		</span>
		<span class="fields full_length">
			<label>Address</label>
			<input type="text" name="address" value="<%=contactUser.getAddress()%>"  />
		</span>
		<span class="fields full_length">
			<label>Email</label>
			<input type="text" name="email" value="<%=contactUser.getEmail()%>" />
		</span>
		<span class="fields full_length">
			<input type="submit"/>
		</span>
	</form>
	</div>
	<div class="edit_profile_sub_section_right_side">
		<h3>Tags</h3>
		<form id="tag_form" action="QueryTags" method="post">
			<p class="no_bottom_margin">Enter the skill you are willing to help with, and how long you can help for</p>
			<input type="hidden" name="cmd" value="attachTagToAccount" />
			<input id="tag_search_input" name="tag"/>
			<select id="hours" name="hours_requested">
				<option value="1">1</option>
				<option value="4">4</option>
				<option value="8">8</option>
			</select>
			<input type="submit"/>
				<%
					ArrayList<Tag> businessTags = databaseConnection.getTagsFromAccount( bid );
					Iterator<Tag> tagIt = businessTags.iterator();
					
					while( tagIt.hasNext() )
					{
						Tag currentTag = tagIt.next();
						%>
						<div id="<%="tag-"+currentTag.get_tid() %>" class="account_tag" style="position:relative;">
						<p><%=currentTag.get_name()%><span class="account_tag_hours_section">4 hours</span></p>
						<span class="remove_fields"></span>
						</div>
						<%
					}
				%>
		</form>
	</div>
</div>
<section>
</section>
<%} %>
</section>
<div style="height:10px;"></div>
<script>

$( window ).load( function(){
	
	var ajaxPasswordChangeOptions = {
		dataType: 'text',
		type: 'post',
		success: passwordChangeComplete
	};
	
	$( "#password_change_form" ).submit( function(){
		$( this ).ajaxSubmit( ajaxPasswordChangeOptions );
		return false;
	});
	
	function passwordChangeComplete( responseText, statusText, xhr, $form ){
		alert( responseText );
	}
});
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

</script>
</body>
</html>




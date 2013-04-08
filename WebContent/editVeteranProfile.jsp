<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="java.util.*" %>
<%@ page import="org.soldieringup.Engine" %>
<%@ page import="org.soldieringup.Veteran" %>
<%@ page import="org.soldieringup.Photo" %>
<%@ page import="org.soldieringup.Tag" %>
<%@ page import="org.soldieringup.User" %>
<%@ page import="org.soldieringup.ZIP" %> 
<%
	if( session.getAttribute( "uid" ) == null ||
		session.getAttribute( "uid" ) == "" ||
		session.getAttribute( "editing_account_type" ) == null ||
		session.getAttribute( "editing_account_type" ) != "veteran"
	  )
	{
	%><jsp:forward page="/login.jsp"/><% 
	}
	
	long uid = Long.valueOf( session.getAttribute( "uid" ).toString() );
	
	Engine engine = new Engine();	
	User contactUser = engine.getUserFromId( uid );
	Veteran foundVeteran = contactUser.getVeteran();
	ZIP userZip = engine.getZIP( contactUser.getZip() );
%>  
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Soldier Up - Entrepreneurs serving those who served us.</title>
<link href="Styles/styles.css" rel="stylesheet" />
<link href="Styles/formStyles.css" rel="stylesheet" />
<link href="Styles/EditProfileStyles.css" rel="stylesheet" />
<link href="Styles/jquery.Jcrop.min.css" rel="stylesheet" type="text/css"/>
<link href="Styles/jquery-ui-autocomplete.min.css" rel="stylesheet" />
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
<script src="Scripts/jquery-ui-autocomplete.min.js"></script>
<script src="Scripts/CropPhoto.js" type="text/javascript"></script>
<script src="Scripts/ProfileImageScripts.js" type="text/javascript"></script>
<script src="Scripts/TagScripts.js" type="text/javascript"></script>
</head>
<body>
<jsp:include page="Includes/header.jsp"></jsp:include>
<section id="edit_profile_section" style="margin-bottom:10px;">
<% if( foundVeteran != null){ %>
<div class="edit_profile_sub_section">
	<div class="edit_profile_sub_section_left_side">
		<h3>Profile Image</h3>
		<form id="upload_profile_pic_form" action="UploadImage" method="post">
			<input type="hidden" name="type" value="profile"/>
			<span id="profile_pic_display" style="display:inline-block; position:relative; width:100px; height:100px; border:#000 solid 1px;">
				<% if( contactUser.getProfileSrc() != null){ out.println("<img id=\"profile_photo_display\" src=\"Images/"+ contactUser.getProfileSrc()+"\"/>");} %>
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
				<label for="change_password_new_password">Password</label>
				<label for="change_password_confirm_new_password">Confirm</label>
			</p>
			<input id="change_password_new_password" type="password" name="password"/>
			<input id="change_password_confirm_new_password" type="password" name="confirm_password"/>
			<input id="change_password_submit" type="submit"/>
		</form>
	</div>
</div>
<div class="edit_profile_sub_section" style="clear:both;">
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
		<h3>Aspirations</h3>
		<form method="post" action="UpdateVeteran">
			<p class="no_bottom_margin">What kind of business are you looking to start?<input type="submit" class="description_submit"/></p>
			<p class="no_top_margin"><textarea name="goal" class="description_boxes" cols="50" rows="30"><%=foundVeteran.getGoal()%></textarea></p>
		</form>
	</div>
</div>
<div class="edit_profile_sub_section" style="clear:both;">
	<div class="edit_profile_sub_section_left_side">
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
		</form>
			<%
				ArrayList<Tag> businessTags = engine.getTagsFromAccount( contactUser.getAid() );
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




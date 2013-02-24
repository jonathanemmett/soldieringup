<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Soldier Up - Entrepreneurs serving those who served us.</title>
<link href="Styles/Styles.css" rel="stylesheet" />
<!--[if lt IE 9]>
    <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]--> 
<link href="Styles/jquery.Jcrop.min.css" rel="stylesheet" type="text/css"/>
<script src="Scripts/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="Scripts/jquery.Jcrop.min.js" type="text/javascript"></script>
<script src="Scripts/CropPhoto.js" type="text/javascript"></script>
</head>
<body>
<jsp:include page="Includes/header.jsp"></jsp:include>
<section>
<img src="Images/JakeUpload.png" id="profile_pic" />
<form id="photo_rectangle" method="post">
<input type="hidden" name="profile_x_position"/>
<input type="hidden" name="profile_y_position"/>
<input type="hidden" name="profile_width"/>
<input type="hidden" name="profile_height"/>
</form>
</section>
<script type="text/javascript">
$(document).ready(function(){
	
	var formXPosition = document.getElementsByName("profile_x_position")[0];
	var formYPosition = document.getElementsByName("profile_y_position")[0];
	var formWidth = document.getElementsByName("profile_width")[0];
	var formHeight = document.getElementsByName("profile_height")[0];
	CropPhoto( "#profile_pic", "profile_x_position", "profile_y_position", "profile_width", "profile_height" );
});
</script>
</body>
</html>
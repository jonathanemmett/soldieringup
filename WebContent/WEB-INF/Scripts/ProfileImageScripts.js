$(window).load(function(){
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
					var box = new LightBox();
					box.appendElement( image );

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
});
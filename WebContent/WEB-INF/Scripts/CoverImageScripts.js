/*
 * The following script provides functionality for uploading a cover image to the server,
 * scaling it properly, and then allowing the user to reposition the photo so they get the portion
 * they want. In order to use this script, the following HTML requirements are needed.
 * 
 * 		The style sheet EditProfileStyles
 * 		A form with the id #upload_cover_image_form
 * 		A span or div with the id #upload_cover_photo_button
 * 		A input of type file with the id of #upload_cover_photo_input.
 * 		A div with the id #cover_banner
 * 		If a cover image exists for a given account, give it an id of #cover_photo_display
 * 			before inserting it into the cover banner.
 */

$(window).load(function(){
	// Ajax options for uploading of profile and cover photos
	var coverPhotoUploadAjaxOptions = {
		dataType: 'json',
		type: 'post',
		success: coverUploadComplete
	};
	
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
					$( "#cover_banner" ).prepend( image );
					addSubmitCoordinatesFunction();
					CoverImageDrag( image, document.getElementById( "cover_photo_y" ) );
				};
				image.src = "TempUploads/" + data.temp_cover_src;
			}
		},"json");
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

		$( "#upload_coordinates" ).click( function(){
			$( "#upload_profile_coordinates_form" ).ajaxForm({
				dataType: 'text',
				success: function( responseText ){
					$( uploadButton ).remove();

					$.get( "UploadImage", function(data){
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
});
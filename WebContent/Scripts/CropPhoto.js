(
function(window)
{
	/**
	 * Adds the Jcrop cropping too to an image. In order to use this, you must include
	 * jquery and the jcrop library as part of the page
	 * @param id ID of the image to receive the crop
	 * @param formFieldXId ID of the form element to receive the x value
	 * @param formFieldYId ID of the form element to receive the y value
	 * @param formFieldWId ID of the form element to receive the width of the crop
	 * @param formFieldHId ID of the form element to receive the height of the crop
	 */
	function CropPhoto( id, formFieldXId, formFieldYId, formFieldWId, formFieldHId ){
		var formXId = formFieldXId;
		var formYId = formFieldYId;
		var formWId = formFieldWId;
		var formHId = formFieldHId;
		
		$( id ).Jcrop({
			aspectRatio: 1,
			onSelect: getCoordinates
		});
		
		function getCoordinates( rectangle )
		{
			document.getElementsByName(formXId)[0].value = rectangle.x; 
			document.getElementsByName(formYId)[0].value = rectangle.y;
			document.getElementsByName(formWId)[0].value = rectangle.w;
			document.getElementsByName(formHId)[0].value = rectangle.h;
		}
	}
	
	window.CropPhoto = CropPhoto;
}
)(window);
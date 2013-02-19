(
		
function(window){
	/**
	 * Object that represents a div taking 100% of the window with a section in the middle to display
	 * the image, and the rest having a background with very small transparency. 
	 */
	function LightBox( image ){
		
		// Set up all the styles required for a light box display
		var div = document.createElement("div");
		div.style.width = "100%";
		div.style.height = "100%";
		div.style.backgroundImage = "url('Images/lightBoxBackGround.png')";
		div.style.position = "fixed";
		div.style.left = "0px";
		div.style.top = "0px";
		var inside = document.createElement("div");
		inside.style.width = image.width + "px";
		inside.style.height = image.height + "px";
		inside.style.marginLeft = "auto";
		inside.style.marginRight = "auto";
		inside.style.marginTop = "100px";
		inside.style.marginBottom = "100px";
		inside.style.paddingTop = "20px";
		inside.style.paddingBottom = "10px";
		inside.style.paddingLeft = "10px";
		inside.style.paddingRight = "10px";
		inside.style.backgroundColor = "#FFFFFF";
		inside.style.position = "relative";
		inside.appendChild( image );
		div.appendChild( inside );
		
		this.retrieveDiv = function(){
			return div;
		};
		
		function removeContainer(e){
			
		}
		
		this.appendElement = function( element ){
			inside.appendChild( element );
		}
		
		function removeWheelScroll(event){
			event.preventDefault();
			event.returnValue=false;
		}
	}
	
	window.LightBox = LightBox;
}
)(window);
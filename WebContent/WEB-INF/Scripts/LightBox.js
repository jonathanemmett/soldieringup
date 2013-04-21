(
		
function(window){
	/**
	 * Object that represents a div taking 100% of the window with a section in the middle to display
	 * the image, and the rest having a background with very small transparency. 
	 */
	function LightBox(){
		
		// Set up all the styles required for a light box display
		var div = document.createElement("div");
		div.style.width = "100%";
		div.style.height = "100%";
		div.style.textAlign = "center";
		div.style.verticalAlign = "middle";
		div.style.backgroundImage = "url('Images/lightBoxBackGround.png')";
		div.style.position = "fixed";
		div.style.left = "0px";
		div.style.top = "0px";
		div.style.marginTop = "0px";
		div.style.marginRight = "0px";
		div.style.marginBottom = "0px";
		div.style.marginLeft = "0px";

		var inside = document.createElement("span");
		inside.style.display = "inline-block";
		inside.style.width = "auto";
		inside.style.verticalAlign = "middle";
		inside.style.marginTop = "100px";
		inside.style.paddingTop = "20px";
		inside.style.paddingBottom = "10px";
		inside.style.paddingLeft = "10px";
		inside.style.paddingRight = "10px";
		inside.style.backgroundColor = "#FFFFFF";
		inside.style.position = "relative";
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
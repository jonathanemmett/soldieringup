(
/**
 * Allows for a dom object to be dragged vertically to position for cover pages
 * @param domObject Dom object to be dragged up and down
 * @param formYElement Form element that will receive the new y value
 */		
function(window){
	function CoverImageDrag( domObject, formYElement ){
		
		var lastMouseY = 0; //!< Previous y the mouse was at while dragging
		var element = domObject; //!< The cover image object
		var coverImageHeight = 300; //!< The height of the cover image
		
		var formYField = formYElement; //!< Form input that keeps track of the y position

		if( document.addEventListener ){
			element.addEventListener( 'mousedown', onDragStart, true );
		}
		else{
			element.attachEvent( 'onmousedown', onDragStart );
		}
	
		// We need to disable the default drag event.
		element.ondragstart = function() { return false; };
		
		element.style.position = "absolute";
		element.style.left = "0px";
		element.style.top = "0px";
	
	
		/**
		 *  Action that starts the dragging
		 *  @param Mouse click event
		 */
		function onDragStart( e )
		{
			if( document.addEventListener ){
				document.addEventListener( 'mousemove',onDragMove );
			}
			else{
				document.attachEvent( 'onmousemove', onDragMove );
			}
			
			if( document.addEventListener ){
				document.addEventListener( 'mouseup', onDragStop );
			}
			else{
				document.attachEvent( 'onmouseup', onDragStop );
			}
			
			lastMouseY = e.clientY;
		}

		/**
		 * 	Mouse move function that moves the object when dragging.
		 * 	@param mousemove event
	 	*/
		function onDragMove( e )
		{
			var dy = e.clientY - lastMouseY;
		
			var newY = parseInt( element.style.top ) + dy;
	
			// The draggable must be visible, so the y range for the dragged object
			// is from coverImageHeight - the elements height to 0.
			if( newY > 0 )
			{
				newY = 0;
			}
			if( newY < coverImageHeight - element.height )
			{
				newY = coverImageHeight - element.height;
			}
		
			element.style.top = newY + "px";
			lastMouseY = e.clientY;
			
			formYField.setAttribute('value', newY );
		}

		/**
		 * Stops the dragging of the object
		 * @param mouseup event
		 */
		function onDragStop( e )
		{
			// Release the events from the document when the user is done dragging
			
			if( document.removeEventListener ){
				document.removeEventListener( 'mousemove',onDragMove );
			}
			else{
				document.detachEvent( 'onmousemove',onDragMove );
			}
			
			if( document.removeEventListener){
				document.removeEventListener( 'mouseup', onDragStop );
			}
			else{
				document.detachEvent( 'onmouseup', onDragStop );
			}
		}
	}
	
	window.CoverImageDrag = CoverImageDrag;
})(window);
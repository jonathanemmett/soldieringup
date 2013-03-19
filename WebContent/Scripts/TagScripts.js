$(window).load(function() { 
	$('#tag_search_input').autocomplete({source:'QueryTags'});

	var elements = $('.account_tag');

	for( var i = 0; i < elements.length; ++i ){	
		attachRemoveTagEvent( elements[i] );
	}

	var tapUploadAjaxFunctions = {
			dataType: 'json',
			type: 'post',
			success: tagAttachComplete
	};

	$( "#tag_form" ).submit( function(){
		$(this).ajaxSubmit( tapUploadAjaxFunctions );
		return false;
	});

	/**
	 * Function that is triggered when a user attaches a tag to their account
	 */
	function tagAttachComplete( responseText, statusText, xhr, $form ){
		// Create the tag div
		var tagDiv = $( "<div>");
		tagDiv.attr( 'id', 'tag-'+responseText );
		tagDiv.attr( 'class', 'account_tag' );
		
		// Create the paragraph tag which has the name of the tag
		var tagNameParagraph = $( "<p>" );
		tagNameParagraph.append( $( "#tag_search_input" ).val() );
		tagDiv.append( tagNameParagraph );
		
		// Create the span showing the hours for the tag
		var hoursSpan = $( "<span>" );
		hoursSpan.append( $( "#hours" ).val() + " hours" );
		hoursSpan.attr( 'class', 'account_tag_hours_section' );
		tagNameParagraph.append( hoursSpan );
		
		// Create the span of the exit button
		var exitSpan = $( "<span>" );
		exitSpan.attr( 'class', 'remove_fields' );
		tagDiv.append( exitSpan );
		
		// Add the tag to the rest of the tags
		$( "#tag_form" ).append( tagDiv );
		$( "#tag_search_input" ).val( "" );
		
		// Attach the function for deleting the tag
		attachRemoveTagEvent( tagDiv );
	}
	
	/*
	 * Attaches the function for removing a tag from an account to the  
	 * exit buttons found in a div for account tags.
	 */
	function attachRemoveTagEvent( element ){
		$( element ).find( ".remove_fields" ).click(function(){	
			var tagElementId = $( this ).parent().attr( "id" );
			var tagId = tagElementId.replace( "tag-", "" );

			$.post( 'QueryTags',{ 'cmd':'detachTagFromAccount', 'tagId' : tagId }, function( data ){
				$( "#"+tagElementId ).remove();
			});
		});
	}
}); 

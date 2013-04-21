// JavaScript Document
var thanks = document.getElementById("thank_you");

if( !thanks.addEventListener )
{
	thanks.onclick = prepareForm;
}
else
{
	thanks.addEventListener("click", prepareForm, false);
}

function prepareForm( e )
{
	e = e || window.event;
	e.preventDefault();
	var div = document.createElement("div");
	div.setAttribute( "id", "comment_div" );
	div.style.backgroundColor = "#FFF"
	div.style.position = "fixed";
	div.style.left = "30%";
	div.style.top = "30%";
	div.style.border = "#000 solid 1px"
	div.style.paddingRight = "30px";
	var form = document.createElement("form");
	form.setAttribute( "id","comment_form" );
	
	var name = createInputLabel("Name: ","name","text");
	var textBox = createTextBox("Comment: ","comment","comment",10,41);
	var field = createInputLabel("","enter","submit", closeForm);

	var span = document.createElement("a");
	span.setAttribute("id","cancel_comment");
	span.innerHTML = "x";
	
	if( !span.addEventListener )
	{
		span.onclick = closeForm;
	}
	else
	{
		span.addEventListener( "click", closeForm, false );
	}

	div.appendChild( span );
	div.appendChild( form );
	
	form.appendChild( name );
	form.appendChild( textBox );
	form.appendChild( field );
	
	var bodyElement = document.getElementsByTagName("body")[0];
	
	bodyElement.appendChild( div );
}

function closeForm(e)
{
	e = e || window.event;
	e.preventDefault();
	var element = document.getElementById("comment_form");
	document.getElementsByTagName("body")[0].removeChild( element.parentNode );
}

function createTextBox( labelText, id, name, numrows, numcols )
{
	var paraHolder = document.createElement("p");
	var label = document.createElement("label");

	var text = document.createTextNode( labelText );
	label.appendChild( text );
	var element = document.createElement("textarea");
	element.cols = numcols;
	element.rows = numrows;
	
	paraHolder.appendChild( label );
	paraHolder.appendChild( element );
	return paraHolder;
}

function createInputLabel( labelText, id, type, func )
{
	var paraHolder = document.createElement("p");
	var label = document.createElement("label");
	var text = document.createTextNode( labelText );
	label.appendChild( text );
	paraHolder.appendChild( label );
	
	var element = document.createElement("input");
	element.setAttribute("type", type );
	paraHolder.appendChild( element );
	
	if( func )
	{
		if( !element.addEventListener )
		{
			element.onclick = func;
		}
		else
		{
			element.addEventListener( "click", func, false );
		}
	}
	return paraHolder;
}
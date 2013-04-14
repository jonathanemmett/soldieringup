<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AJAX calls using Jquery in Servlet</title>
        <script src="http://code.jquery.com/jquery-latest.js"></script>
        <script src="http://cloud.github.com/downloads/wycats/handlebars.js/handlebars-1.0.rc.1.min.js"></script>
        <script src="Scripts/templates.js"></script>
        <script>
            $(document).ready(function() {                        
                $('#submit').on("click", function(e) {
                    //prevents page post back by the submit button
                    e.preventDefault();

                    //only have to enter the DOM once, speeds up the page.
                    var email = $('#email').val();
                    var welcome = $('#welcometext');

                    /*I just prefer this way, more obvious control. 
                    works the same just cleaner in my eyes.*/
                    $.ajax({
                        url: 'rest/accounts',
                        type: 'get', //I use post because it hides information, get exposes it in the URL.
                        data: ({ email : email }),
                        dataType: 'json', //easier to manipulate in handlebars.js
                        success: function(results) {
                            
                            var template = Handlebars.getTemplate('email');

                            welcome.append( template(results) );
                        },
                        error: function() {
                            var context = {
                                email : "Could not find Email!",
                            }
                            var template = Handlebars.getTemplate('email');

                            welcome.append( template(context) );
                        }
                    });
                });
            });
        </script>
</head>
<body>
<form id="form1">
<h1>AJAX Demo using Jquery in JSP and Servlet</h1>
Enter your email address:
<input type="text" id="email"/>
<input type="button" id="submit" value="Ajax Submit"/>
<br/>
<div id="welcometext">
</div>
</form>
</body>
</html>
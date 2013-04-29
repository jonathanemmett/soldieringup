<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
	
	<title>Soldier Up</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">

	<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" media="screen">
	<link rel="stylesheet" href="css/styles.css" />
	<link rel="stylesheet" href="css/bootstrap-responsive.min.css" />
	
	<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="js/html5shiv.js"></script>
    <![endif]-->
    <!-- Fav and touch icons -->
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="img/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="img/ico/apple-touch-icon-114-precomposed.png">
      <link rel="apple-touch-icon-precomposed" sizes="72x72" href="img/ico/apple-touch-icon-72-precomposed.png">
                    <link rel="apple-touch-icon-precomposed" href="img/ico/apple-touch-icon-57-precomposed.png">
                                   
</head>
<body>
	
	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				 <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
		            <span class="icon-bar"></span>
		            <span class="icon-bar"></span>
		            <span class="icon-bar"></span>
		          </button>
				<a class="brand" href="/soldieringup">Soldier Up</a>
				<div class="nav-collapse collapse">
					<ul class="nav">
						<li class="active"><a href="/soldieringup"><i class="icon-home icon-white"></i> Home</a></li>
						<li><a class="about" href="#">About</a></li>
						<li><a class="profile" href="#">Profile</a></li>
					</ul>
					<form class="navbar-search pull-right">
						<input type="text" class="search-query" placeholder="Search">
					</form>
					<ul class="nav pull-right">
					<li><a class="signup" href="">Sign Up</a></li>
                  	<li class="divider-vertical"></li>
					<li class="dropdown">
						<a class="dropdown-toggle" href="#" data-toggle="dropdown">Sign In <strong class="caret"></strong></a>
						<div class="dropdown-menu" style="padding: 15px; padding-bottom: 0px;">
							<form name='f' action="<c:url value='j_spring_security_check' />" method='POST'>
								<c:if test="${not empty error}">
									<div class="errorblock">
										Your login attempt was not successful, try again.<br /> Caused :
										${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
									</div>
								</c:if>
								<input type="text" placeholder="Username" id="username" name='j_username'>
								<input type="password" placeholder="Password" id="password" name="j_password">
								<label class="checkbox">
									<input type="checkbox" name="remember-me" value="1"> Remember Me
								</label>
								<button class="btn btn-primary btn-block" type="submit" id="sign-in">Sign In</button>
							</form>
						</div>
					</li>
				</ul>
				</div><!--/.nav-collapse-->
			</div>
		</div>
	</div>

	<!-- Carousel Markup -->

	<div id="this-carousel-id" class="carousel slide">
		<div class="carousel-inner">
			<div class="item active">
				<img src="img/US_Navy_in_Columbus_Day_parade_during_SF_Fleet_Week_2010-10-10_2_1200.jpg" alt="" />
				<div class="carousel-caption">
					<p>Columbus Day Parade San Francisco, CA.</p>
				</div>
			</div>
			<div class="item">
				<img src="img/US_Army_53283_Wounded_veterans_return_to_Iraq1200.jpg" alt="" />
				<div class="carousel-caption">
					<p>Wounded Warriors Return From Iraq</p>
				</div>
			</div>
		</div>
		<a class="carousel-control left" href="#this-carousel-id" data-slide="prev">&lsaquo;</a>
    	<a class="carousel-control right" href="#this-carousel-id" data-slide="next">&rsaquo;</a>
	</div>

	<!-- Site Content Area -->

	<div class="container" id="outlet">
		
	</div>
	
	<!--  Handlebars scripts for site content -->
	<script type="text/x-handlebars" id="signup">
	<form id="signup-form">
	<div class="well span12">
	<div class="span5">
	<h1>Sign-up form</h1>
	<p>Create an account to publish.</p>
	<ul class="nav">
		<li><input type="text" name="fname" id="fname" placeholder="First Name" /></li>
		<li><input type="text" name="lname" id="lname" placeholder="Last Name" /></li>
		<li><input type="text" name="company" id="company" placeholder="Company" /></li>
		<li><input type="text" name="cellphone" id="cellphone" placeholder="Cell Phone" /></li>
		<li><input type="text" name="homephone" id="homephone" placeholder="Home Phone" /></li>
		<li><input type="text" name="businessphone" id="businessphone" placeholder="Business Number" /></li>
		<li><input type="text" name="address" id="address" placeholder="Address" /></li>
		<li><input type="text" name="city" id="city" placeholder="City" /></li>
		<li><select id="state" class="dropdown">
			<option>MO</option>
			<option>KS</option>
			<option>AR</option>
			<option>OK</option>
		</select></li>
		<li><input type="text" name="zip" id="zip" placeholder="Zip Code" /></li>
		<li><input type="text" name="email" id="email" placeholder="Email" /></li>
		<li><input type="password" name="password" id="password" placeholder="Password" /></li>
		<li><input type="password" name="password2" id="password2" placeholder="Confirm Password" /></li>
		<button class="btn" type="submit">Submit</button>
	</ul>
	</div>
	</div>
	</form>
	</script>
	<script type="text/x-handlebars" id="index">
	<div class="row">
	<div class="span4">
		<h2>Heading Area</h2>
		<p>Bacon ipsum dolor sit amet filet mignon leberkas chicken sausage pork chop strip steak turducken meatball tri-tip drumstick ribeye pork loin jerky. Pancetta tri-tip brisket meatloaf turkey. Sausage shank chicken flank chuck meatloaf tenderloin. Capicola beef bacon strip steak fatback tri-tip turducken ball tip. Short ribs prosciutto pancetta drumstick.</p>
		<p><a class="btn" href="">View Details &raquo;</a></p>
	</div>
	<div class="span4">
		<h2>Heading Area</h2>
		<p>Bacon ipsum dolor sit amet ground round beef ribs est short ribs, fatback tempor capicola drumstick sunt andouille ham eu. Meatball hamburger excepteur eu dolore eiusmod, laborum jerky pastrami pig. Beef ribs ea shank, aliquip deserunt spare ribs sint in sed cow occaecat aute. Dolor turducken fugiat, magna chuck dolore rump est reprehenderit ground round tongue ullamco cupidatat incididunt excepteur. Dolore dolore prosciutto consequat eiusmod ham hock leberkas ut fatback drumstick. Doner aute quis, bresaola spare ribs est mollit boudin ut tempor.</p>
		<p><a class="btn" href="">View Details &raquo;</a></p>
	</div>
	<div class="span4">
		<h2>Heading Area</h2>
		<p>Bacon ipsum dolor sit amet meatball corned beef fatback tenderloin biltong flank beef ribs bresaola ground round pork loin ham rump pancetta ribeye. Strip steak pancetta meatloaf jerky jowl frankfurter sausage brisket shank capicola t-bone fatback. Pork chop biltong corned beef hamburger, pork flank pork loin venison tongue shoulder andouille ball tip turducken. Beef ribs pork belly doner jowl t-bone rump brisket cow chicken pork andouille short loin swine.</p>
		<p>Pork belly turkey jerky bresaola corned beef prosciutto chuck kielbasa pig strip steak pork chop chicken tail t-bone beef. Venison biltong spare ribs andouille rump t-bone corned beef tongue fatback drumstick beef ribs kielbasa flank turkey pancetta. Hamburger ball tip turkey, turducken sirloin shankle capicola meatloaf filet mignon t-bone pork chop rump. Pork leberkas shoulder doner turducken strip steak jowl tri-tip short loin rump tongue pig frankfurter drumstick andouille. Tongue meatball tenderloin bacon cow strip steak pork chop.</p>
		<p><a class="btn" href="">View Details &raquo;</a></p>
	</div>
	</div>
	</script>
	<script type="text/x-handlebars" id="about">
	<div class="well span12">
	<h1>ABOUT US</h1>
	<p>SU is a community…a Nation if you will. A Nation where both Veterans and Entrepreneurs develop relationships and foster a cooperative spirit of promotion and support.</p>

	<p>By joining the SU Nation, both the entrepreneur and the Veteran can leverage their resources and skills to both grow and start a business.</p>

	<p>Through the SU Nation the Entrepreneur provides the added “brain” capital and expertise into the Veteran’s business, all while the Veteran includes in its advertising the promotion of the supporting company.</p>

	<p>Through this mutual relationship both businesses are able to achieve more success with less expenditure of capital. Through the growth of small and new businesses enterpreneurship can and does change our world for the better.</p>

	<h1>THE MOVEMENT</h1>
	<p>With the high rate of homelessness, unemployment and ????, many veterans are needing to turn to starting a business of their own as a way to provide for themselves and their families.</p>
	<p>In many cases the Veteran may lack the necessary set of skills or capital to start or buy a new business</p>
	</div>
	</script>
	<script type="text/x-handlebars" id="profile">
	<div class="well">
		<ul class="nav nav-tabs">
			<li class="active"><a href="#home" data-toggle="tab">Profile</a></li>
			<li><a href="#profile" data-toggle="tab">Edit Profile</a></li>
		</ul>
		<div class="tab-content">
			<div class="tab-pane active in" id="home">
				<div class="row span3">

				</div>
			</div>
		<div class="tab-pane fade" id="profile">
    	<form id="tab2">
        	<label>New Password</label>
        	<input type="password" class="input-xlarge">
        	<div>
        	    <button class="btn btn-primary">Update</button>
        	</div>
    	</form>
      </div>
		</div>
	</div>
	</script>
	<script type="text/x-handlebars" id="login-failure">
	<div class="well span12">
		<p class="alert">This is a failed log in attempt</p>
	</div>
	</script>
	<!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
	<script>window.jQuery || document.write('<script src="js/jquery-1.7.2.min.js"><\/script>')</script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/handlebars.js/1.0.0-rc.3/handlebars.min.js"></script>
	
    <script src="js/bootstrap.min.js"></script>
    <script src="js/holder.js"></script>
    <script>
    	$(document).ready(function(){
    		/* Home */
    		var outlet = $('#outlet');
    		outlet.empty().hide();
		    var source = $('#index').html();
		    var template = Handlebars.compile(source);
		    $('#outlet').append(template).fadeIn('slow');
		    
    		$('.carousel').carousel({
		      interval: 10000
		    });
    		
		    //Handles menu drop down
  			$('.dropdown-menu').find('form').click(function (e) {d
	        	e.stopPropagation();
	        });
		    
		    $('a.signup').on("click", function(e){
		    	e.preventDefault();
		    	var outlet = $('#outlet');
		    	$('.carousel').hide();
		    	outlet.empty().hide();
		    	var source = $('#signup').html();
		    	var template = Handlebars.compile(source);
		    	outlet.append(template).fadeIn('slow');
		    	
		    });
		    
		    /* About */
		    $('a.about').on("click", function(e) {
		    	e.preventDefault();
		    	var outlet = $('#outlet');
		    	$('.carousel').hide();
		    	outlet.empty().hide();
		    	var source = $('#about').html();
		    	var template = Handlebars.compile(source);
		    	outlet.append(template).fadeIn('slow');
		    });
		    
		    /* Profile */
		    $('a.profile').on("click", function(e) {
		    	e.preventDefault();
		    	var outlet = $('#outlet');
		    	$('.carousel').hide();
		    	outlet.empty().hide();
		    	var source = $('#profile').html();
		    	var template = Handlebars.compile(source);
		    	outlet.append(template).fadeIn('slow');
		    	
		    });
		    
		    /* Authentication */
		    $('#sign-in').on("click", function(e) {
		    	e.preventDefault();
		    	var outlet = $('#outlet');
		    	var user = $('#username').val();
		    	var password = $('#password').val();
		    	
		    	if(user !== "" && password !== "") {
		    		/* Do cool ajax stuff to validate users */
		    		$('.carousel').hide();
			    	outlet.empty().hide();
			    	var source = $('#profile').html();
			    	var template = Handlebars.compile(source);
			    	outlet.append(template).fadeIn('slow');
		    	} else {
		    		$('.carousel').hide();
			    	outlet.empty().hide();
			    	var source = $('#login-failure').html();
			    	var template = Handlebars.compile(source);
			    	outlet.append(template).fadeIn('slow');
		    	}
		    });
    	});
    </script>
</body>
</html>
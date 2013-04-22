<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
				<a class="brand" href="index.html">Soldier Up</a>
				<div class="nav-collapse collapse">
					<ul class="nav">
						<li class="active"><a href="index.html"><i class="icon-home icon-white"></i> Home</a></li>
						<li><a href="#">Future Page</a></li>
						<li><a href="#">Future Page</a></li>
					</ul>
					<form class="navbar-search pull-right">
						<input type="text" class="search-query" placeholder="Search">
					</form>
					<ul class="nav pull-right">
					<li><a href="/signup">Sign Up</a></li>
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
								<input class="btn btn-primary btn-block" type="submit" id="sign-in" value="Sign In">
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

	<!-- Information Columns -->

	<div class="container">
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
	</div>

	<!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
	<script>window.jQuery || document.write('<script src="js/jquery-1.7.2.min.js"><\/script>')</script>

    <script src="js/bootstrap.min.js"></script>
    <script>
    	$(document).ready(function(){
    		$('.carousel').carousel({
		      interval: 10000
		    });
		    //Handles menu drop down
  			$('.dropdown-menu').find('form').click(function (e) {
	        	e.stopPropagation();
	        });
    	});
    </script>
</body>
</html>
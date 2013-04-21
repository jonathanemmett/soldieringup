<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<link href="Styles/account_form.css" rel="stylesheet" type="text/css"/>
<title>Create Account</title>
</head>
<body>
	<div id="stylized" class="myform">
		<form id="form" name="form" method="post" action="addAccount">
			<h1>Sign-up form</h1>
			<p>Create an account to publish. Creating an account allows you to advertise how you will be helping Veterans.</p>

			<label>First Name 
				<span class="small">Add your name</span>
			</label> 
				<input type="text" name="fname" id="fname" /> 

			<label>Last Name 
				<span class="small">Add your name</span>
			</label> 
				<input type="text" name="lname" id="lname" />

			<label>Business
				<span class="small">Add your business name</span>
			</label> 
				<input type="text" name="company" id="company" /> 				 				

			<label>Cell Phone 
				<span class="small">Add a valid Cell-Phone Number (Not Required)</span>
			</label>
				<input type="text" name="cellphone" id="cellphone" />

			<label>Home Phone 
				<span class="small">Add a valid Home Phone (Not Required)</span>
			</label>
				<input type="text" name="homephone" id="homephone" />
				
			<label>Business Phone 
				<span class="small">Add a valid Business Phone (Not Required)</span>
			</label>
				<input type="text" name="businessphone" id="businessphone" />			
				
			<label>address 
				<span class="small">Add a valid Business Phone (Not Required)</span>
			</label>
				<input type="text" name="address" id="address" />
				
			<label>City 
				<span class="small">Add a valid City Name</span>
			</label>
				<input type="text" name="city" id="city" />
				
			<label>State 
				<span class="small">Add a valid State Name</span>
			</label>
				<span class="small">
				<select name="state" id="state"> 
					<option value="1">MO</option>
					<option value="2">AR</option>
					<option value="3">NY</option>
					<option value="4">WA</option>
				</select>
				</span>
			<label>ZIP
				<span class="small">Add a valid ZIP code</span>
			</label>
				<input type="text" name="zip" id="zip" />
				
			<label>Email 
				<span class="small">Add a valid address</span>
			</label>
				<input type="text" name="email" id="email" />							
 
			<label>Password
				<span class="small">Min. size 6 chars</span>
			</label>
				<input type="password" name="password" id="password" />				

			<button type="submit">Sign-up</button>
			<div class="spacer"></div>

		</form>
	</div>
</body>
</html>
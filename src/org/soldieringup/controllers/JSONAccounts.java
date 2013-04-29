package org.soldieringup.controllers;

import org.soldieringup.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class JSONAccounts extends BaseProtJSONController {
	// Example:
	//http://localhost:8080/soldieringup/rest/prot/accounts?email=jjennings

	//@Autowired
	//private MongoEngine mongoEngine;

	@RequestMapping(value="accounts", method = RequestMethod.GET)
	public @ResponseBody String getShopInJSON(@RequestParam String email) {
		System.out.println ("Request for accounts");
		return toJson (getUser (email));
	}

	@RequestMapping(value="account", method = RequestMethod.GET)
	public @ResponseBody String getAccount() {
		System.out.println ("Request for getAccount()");
		String name = getAuthenticatedUser ().getName(); //get logged in username
		return toJson (getUser (name));
	}

	private User getUser (String email)
	{
		UserDetails userDetails = (UserDetails) getAuthenticatedUser ().getPrincipal ();
		//User user = mongoEngine.getUser (userDetails);
		User user = new User ();
		user.setEmail (userDetails.getUsername ());
		return user;
	}
}
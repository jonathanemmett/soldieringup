package org.soldieringup.controllers;

import org.soldieringup.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/account")
public class JSONController {
	// Example:
	//http://localhost:8080/soldieringup/rest/account/jared

	@RequestMapping(value="{name}", method = RequestMethod.GET)
	public @ResponseBody User getShopInJSON(@PathVariable String name) {

		User user = new User ();
		user.setFirstName("Jared");
		user.setLastName ("Jennings");
		user.setEmail ("jared@jaredjennings.org");

		return user;

	}

}
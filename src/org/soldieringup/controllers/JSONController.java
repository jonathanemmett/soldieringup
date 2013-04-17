package org.soldieringup.controllers;

import org.soldieringup.Business;
import org.soldieringup.DIVISION;
import org.soldieringup.User;
import org.soldieringup.Veteran;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
@RequestMapping("/rest")
public class JSONController {
	// Example:
	//http://localhost:8080/soldieringup/rest/account/jared

	@RequestMapping(value="accounts", method = RequestMethod.GET)
	public @ResponseBody String getShopInJSON(@RequestParam String email) {
		Gson gson = new Gson();
		return gson.toJson(getUser (email));
	}

	@RequestMapping(value="account", method = RequestMethod.GET)
	public @ResponseBody String getAccount() {
		Gson gson = new Gson();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName(); //get logged in username
		return gson.toJson(getUser (name));
	}

	private User getUser (String email)
	{
		User user = new User ();

		user.setFirstName("Jared");
		user.setLastName ("Jennings");
		user.setEmail (email);
		user.setAddress ("705 Sheridan ST");
		user.setZip ("64075");
		user.setPrimary_number ("816.678.4152");
		Business bus = new Business ();
		bus.setName ("My Business Name");
		user.setBusiness (bus);

		Veteran vet = new Veteran ();
		vet.setDevision (DIVISION.AIRFORCE);
		user.setVeteran (vet);

		return user;
	}
}
package org.soldieringup.controllers;

import org.soldieringup.Business;
import org.soldieringup.DEVISION;
import org.soldieringup.User;
import org.soldieringup.Veteran;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/accounts")
public class JSONController {
	// Example:
	//http://localhost:8080/soldieringup/rest/account/jared

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody User getShopInJSON(@RequestParam String email) {
		return getUser (email);
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
		vet.setDevision (DEVISION.AIRFORCE);
		user.setVeteran (vet);

		return user;
	}
}
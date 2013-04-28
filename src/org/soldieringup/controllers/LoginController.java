package org.soldieringup.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author jjennings
 * @since April 16, 2013
 */
@Controller
public class LoginController {

	@RequestMapping("*")
	public String hello(HttpServletRequest request) {
		return "index";
	}

	@RequestMapping(value="/mockup", method = RequestMethod.GET)
	public String printWelcome(ModelMap model, Principal principal ) {

		String name = principal.getName();
		model.addAttribute("username", name);
		model.addAttribute("message", "Spring Security Custom Form example");
		return "hello";

	}

	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(ModelMap model) {

		return "login";

	}

	@RequestMapping(value="/loginfailed", method = RequestMethod.GET)
	public String loginerror(ModelMap model) {

		model.addAttribute("error", "true");
		return "login";

	}

	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {

		return "login";

	}

}

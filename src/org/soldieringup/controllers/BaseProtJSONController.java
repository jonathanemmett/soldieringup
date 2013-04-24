/**
 * 
 */
package org.soldieringup.controllers;

import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Every JSON controller should inherit from this Controller if it contains protected, secured content
 * Things like Security, authentication and such are handled from this controller
 * 
 * @author jjennings
 * @created April 20, 2013
 */
@RequestMapping("/rest/prot")
public abstract class BaseProtJSONController extends BaseJSONController
{
	private final org.slf4j.Logger logger = LoggerFactory.getLogger (getClass());
	/**
	 * Returns the current authenticated User.
	 * @return Authentication
	 */
	protected Authentication getAuthenticatedUser ()
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.debug ("Authenticated User:" + auth.getName ());
		return auth;
	}
}

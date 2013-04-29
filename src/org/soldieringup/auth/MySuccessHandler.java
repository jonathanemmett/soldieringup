/**
 * 
 */
package org.soldieringup.auth;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.soldieringup.Utilities;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * This allows for custom authentication landing pages.
 * @author jjennings
 *
 */
public class MySuccessHandler implements AuthenticationSuccessHandler
{
	private static final String URI_ADMIN = "/prot/admin";
	private static final String URI_DEFAULT = "/Welcome";
	private static final String ATTR_PARM_FORWARD_URL = "forward_url";

	/* (non-Javadoc)
	 * @see org.springframework.security.web.authentication.AuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
	 */
	@Override
	public void onAuthenticationSuccess (HttpServletRequest aRequest, HttpServletResponse aResponse, Authentication aAuthentication) throws IOException, ServletException
	{
		String forward_url = aRequest.getParameter (ATTR_PARM_FORWARD_URL);
		if (!Utilities.isNullOrEmpty (forward_url))
			aResponse.sendRedirect (forward_url);
		else {
			Set<String> roles = AuthorityUtils.authorityListToSet(aAuthentication.getAuthorities());
			if (roles.contains("ROLE_ADMIN")){
				aResponse.sendRedirect (aRequest.getContextPath ().concat (URI_ADMIN));
				return;
			}
			aResponse.sendRedirect(aRequest.getContextPath ().concat (URI_DEFAULT));
		}
	}

}

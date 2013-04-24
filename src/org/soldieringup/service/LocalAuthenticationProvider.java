package org.soldieringup.service;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.soldieringup.UserAccountStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/*
 Extend AbstractUserDetailsAuthenticationProvider when you want to
 prehandle authentication, as in throwing custom exception messages,
 checking status, etc.
 */
@Component
public class LocalAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	private final org.slf4j.Logger logger = LoggerFactory.getLogger (getClass());

	@Autowired
	private transient PasswordEncoder encoder = null;

	@Autowired
	private MongoEngine engine;

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		// TODO Auto-generated method stub
	}

	@Override
	public UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		String password = (String) authentication.getCredentials();
		if (!StringUtils.hasText(password)) {
			logger.warn("Username {}: no password provided", username);
			throw new BadCredentialsException("Please enter password");
		}

		org.soldieringup.User user = engine.getByEmailAndPassword(username, encoder.encodePassword(password, null));
		if (user == null) {
			logger.warn("Username {}, password {}: username and password not found", username, password);
			throw new BadCredentialsException("Invalid Username/Password");
		}
		if (!(UserAccountStatus.STATUS_APPROVED.equals(user.getStatus()))) {
			logger.warn("Username {}: not approved", username);
			logger.warn ("UserAccountStatus" + UserAccountStatus.STATUS_APPROVED + "==" + user.getStatus ());
			throw new BadCredentialsException("User has not been approved");
		}
		if (!user.isEnabled()) {
			logger.warn("Username {}: disabled", username);
			throw new BadCredentialsException("User disabled");
		}

		final List<GrantedAuthority> auths;
		if (!user.getRoles().isEmpty()) {
			auths = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRolesCSV());
		} else {
			auths = AuthorityUtils.NO_AUTHORITIES;
		}

		return new User(username, password, user.isEnabled(), // enabled
				true, // account not expired
				true, // credentials not expired
				true, // account not locked
				auths);
	}

}

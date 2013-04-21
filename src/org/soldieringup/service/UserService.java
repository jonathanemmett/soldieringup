package org.soldieringup.service;

import java.util.List;

import org.soldieringup.Role;
import org.soldieringup.User;
import org.soldieringup.database.RoleRepository;
import org.soldieringup.database.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserService
{

	@Autowired
	private UserRepository	userRepository;

	@Autowired
	private RoleRepository	roleRepository;

	public Role getRole (String role)
	{
		Role r = roleRepository.findOne (role);
		System.out.println ("getRole:" + r.getRole ());
		return r;
	}

	public boolean create (User user)
	{
		Assert.isNull (user.getId ());
		// user.setId(UUID.randomUUID().toString().replace("-", ""));
		// duplicate username
		//if (userRepository.findByEmail (user.getEmail ()) != null) { return false; }
		//user.setEnabled (false);
		//user.setStatus (UserAccountStatus.STATUS_DISABLED.name ());
		userRepository.insert (user);
		return true;
	}

	public void save (User user)
	{
		System.out.println ("Saving User " + user.getEmail () + " password:" + user.getPassword ());
		// Assert.notNull(user.getId());
		userRepository.save (user);
	}

	public void delete (User user)
	{
		// Assert.notNull(user.getId());
		userRepository.delete (user);
	}

	public User getByUsernameAndPassword (String email, String password)
	{
		System.out.println ("getByUsernameAndPassword User " + email + " password:" + password);
		return getByEmailAndPassword (email, password);
	}

	public User getByEmailAndPassword (String email, String password)
	{
		System.out.println ("getByEmailAndPassword User " + email + " password:" + password);
		List<User> users = userRepository.findByEmailAndPassword (email, password);
		if (users.size () == 1) { return users.get (0); }
		return null;
	}

	public User getByEmail (String email)
	{
		System.out.println ("getByEmail User " + email);
		return userRepository.findByEmail (email);
	}

}

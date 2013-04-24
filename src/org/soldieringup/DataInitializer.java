package org.soldieringup;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soldieringup.service.MongoEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.Index.Duplicates;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.stereotype.Component;

@Component
@Profile(value = "DEV")
/**
 * This class is just for development. It will pre-populate the system with test data.
 * @author jjennings
 * @creatd April 20, 2013
 */
public class DataInitializer
{

	private final Logger		logger			= LoggerFactory.getLogger (getClass ());

	@Autowired
	private MongoTemplate		mongoTemplate;

	@Autowired
	private MongoEngine		mongoEngine;

	/**
	 * Password is test
	 */
	private static final String	demoPasswordEncoded	= "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08";

	@PostConstruct
	public void init ()
	{
		logger.debug ("initializing data");

		// clear all collections
		mongoTemplate.dropCollection (Role.class);
		mongoTemplate.dropCollection (User.class);
		mongoTemplate.createCollection (User.class);
		mongoTemplate.indexOps (User.class).ensureIndex (new Index ().on ("email", Order.DESCENDING).unique (Duplicates.DROP));

		// establish roles
		mongoTemplate.insert (new Role ("ROLE_USER"), "role");
		mongoTemplate.insert (new Role ("ROLE_ADMIN"), "role");

		User user = new User ();
		user.setFirstName ("Bob");
		user.setLastName ("Doe");
		user.setPassword (demoPasswordEncoded);
		user.addRole (mongoEngine.getRole ("ROLE_USER"));
		user.setEmail ("bob@bob.com");
		mongoEngine.insertUser (user);
		// simulate account activation
		user.setEnabled (true);
		user.setStatus (UserAccountStatus.STATUS_APPROVED);
		mongoEngine.updateUser (user);

		user = new User ();
		user.setFirstName ("Jim");
		user.setLastName ("Doe");
		user.setPassword (demoPasswordEncoded);
		user.addRole (mongoEngine.getRole ("ROLE_ADMIN"));
		user.setEmail ("jim@jim.com");
		mongoEngine.insertUser (user);
		user.setEnabled (true);
		user.setStatus (UserAccountStatus.STATUS_APPROVED);
		mongoEngine.updateUser (user);

		user = new User ();
		user.setFirstName ("Ted");
		user.setLastName ("Doe");
		user.setPassword (demoPasswordEncoded);
		user.addRole (mongoEngine.getRole ("ROLE_USER"));
		user.addRole (mongoEngine.getRole ("ROLE_ADMIN"));
		user.setEmail ("ted@ted.com");
		mongoEngine.insertUser (user);
		user.setEnabled (true);
		user.setStatus (UserAccountStatus.STATUS_APPROVED);
		mongoEngine.updateUser (user);

	}
}

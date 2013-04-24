package org.soldieringup.service;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.soldieringup.Question;
import org.soldieringup.Role;
import org.soldieringup.User;
import org.soldieringup.UserAccountStatus;
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
 * @created April 20, 2013
 */
public class DataInitializer
{

	private final org.slf4j.Logger logger = LoggerFactory.getLogger (getClass());

	@Autowired
	private MongoTemplate		mongoTemplate;

	@Autowired
	private MongoEngine		engine;

	/**
	 * Password is test
	 */
	private static final String	demoPasswordEncoded	= "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08";

	@PostConstruct
	public void init ()
	{
		logger.debug ("initializing data");
		System.out.println ("Why isn't this firing???");

		// clear all collections
		mongoTemplate.dropCollection (Role.class);
		mongoTemplate.dropCollection (User.class);
		mongoTemplate.dropCollection (Question.class);
		mongoTemplate.createCollection (User.class);
		mongoTemplate.createCollection (Question.class);
		mongoTemplate.indexOps (User.class).ensureIndex (new Index ().on ("email", Order.DESCENDING).unique (Duplicates.DROP));

		// establish roles
		mongoTemplate.insert (new Role ("ROLE_USER"), "role");
		mongoTemplate.insert (new Role ("ROLE_ADMIN"), "role");

		User user = new User ();
		user.setFirstName ("Bob");
		user.setLastName ("Doe");
		user.setPassword (demoPasswordEncoded);
		user.addRole (engine.getRole ("ROLE_USER"));
		user.setEmail ("bob@bob.com");
		engine.updateUser (user);
		// simulate account activation
		user.setEnabled (true);
		user.setStatus (UserAccountStatus.STATUS_APPROVED);
		engine.updateUser (user);

		Question q = new Question ();
		q.setDetailedDescription ("This namespace element will cause the base packages to be scanned for interfaces extending MongoRepository and create Spring beans for each of them found. By default the repositories will get a MongoTemplate Spring bean wired that is called mongoTemplate, so you only need to configure mongo-template-ref explicitly if you deviate from this convention.");
		q.setTitle ("Mongo Namespace Question");
		q.setAuthor (user.getUsername ());
		engine.insertQuestion (q);

		user = new User ();
		user.setFirstName ("Jim");
		user.setLastName ("Doe");
		user.setPassword (demoPasswordEncoded);
		user.addRole (engine.getRole ("ROLE_ADMIN"));
		user.setEmail ("jim@jim.com");
		engine.updateUser (user);
		user.setEnabled (true);
		user.setStatus (UserAccountStatus.STATUS_APPROVED);
		engine.updateUser (user);

		q = new Question ();
		q.setDetailedDescription ("This namespace element will cause the base packages to be scanned for interfaces extending MongoRepository and create Spring beans for each of them found. By default the repositories will get a MongoTemplate Spring bean wired that is called mongoTemplate, so you only need to configure mongo-template-ref explicitly if you deviate from this convention.");
		q.setTitle ("Mongo Namespace Question 3");
		q.setAuthor (user.getUsername ());
		engine.insertQuestion (q);

		user = new User ();
		user.setFirstName ("Ted");
		user.setLastName ("Doe");
		user.setPassword (demoPasswordEncoded);
		user.addRole (engine.getRole ("ROLE_USER"));
		user.addRole (engine.getRole ("ROLE_ADMIN"));
		user.setEmail ("ted@ted.com");
		engine.updateUser (user);
		user.setEnabled (true);
		user.setStatus (UserAccountStatus.STATUS_APPROVED);
		engine.updateUser (user);

		q = new Question ();
		q.setDetailedDescription ("This namespace element will cause the base packages to be scanned for interfaces extending MongoRepository and create Spring beans for each of them found. By default the repositories will get a MongoTemplate Spring bean wired that is called mongoTemplate, so you only need to configure mongo-template-ref explicitly if you deviate from this convention.");
		q.setTitle ("Mongo Namespace Question 3");
		q.setAuthor (user.getUsername ());
		engine.insertQuestion (q);

	}
}

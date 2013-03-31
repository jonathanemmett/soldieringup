package org.soldieringup;


import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.soldieringup.database.UserRepository;
import org.soldieringup.database.WarRepository;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MangodbTests
{
	ConfigurableApplicationContext context;
	UserRepository userRep;
	WarRepository warRep;

	@BeforeClass
	public static void setUpBeforeClass () throws Exception
	{}

	@AfterClass
	public static void tearDownAfterClass () throws Exception
	{}

	@After
	public void tearDown () throws Exception
	{
		// so our tests are clean each time
		userRep.dropCollection ();
	}

	@Before
	public void setUp () throws Exception
	{
		context = new ClassPathXmlApplicationContext("org/soldieringup/mongo-config.xml");
		userRep = context.getBean(UserRepository.class);
		warRep = context.getBean(WarRepository.class);
	}

	@Test
	public void testVeterenUserInsert ()
	{
		User user = new User ();
		user.setFirstName ("John");
		user.setLastName ("Doe");
		user.setZip ("65301");
		Veteran vet = new Veteran ();
		vet.setDevision (DEVISION.AIRFORCE);
		user.setVeteran (vet);
		War war = new War ();
		war.setName ("Iraq");
		user.setWar (war);

		warRep.insert (war);
		userRep.insert (user);

		List<User> results = userRep.find ("first_name", "John");
		User result = results.get (0);
		System.out.println ("Total Matches:" + results.size ());
		System.out.println (results);
		assertTrue (result.getZip ().equals (user.getZip ()));
		assertTrue (result.getVeteran ().getDevision ().equals (DEVISION.AIRFORCE));
		System.out.println ("Vet fought in which war?=" + result.getWar ().getName ());
	}

	@Test
	public void testBusinessUserInsert ()
	{
		User user = new User ();
		user.setFirstName ("John");
		user.setLastName ("Doe");
		user.setZip ("65301");
		Business bus = new Business ();
		bus.setAddress ("Oak Grove");
		bus.setName ("ZEDCM");
		bus.setZip ("64075");
		user.setBusiness (bus);

		userRep.insert (user);
		List<User> results = userRep.find ("first_name", "John");
		User result = results.get (0);
		System.out.println ("Total Matches:" + results.size ());
		System.out.println (results);
		assertTrue (result.getZip ().equals (user.getZip ()));
		assertTrue (result.getBusiness ().getName ().equals (bus.getName ()));
	}
}

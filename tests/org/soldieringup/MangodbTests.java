package org.soldieringup;


import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.soldieringup.database.BusinessRepository;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mongodb.DBCollection;

public class MangodbTests
{
	ConfigurableApplicationContext context;
	BusinessRepository businessRepository;

	@BeforeClass
	public static void setUpBeforeClass () throws Exception
	{}

	@AfterClass
	public static void tearDownAfterClass () throws Exception
	{}

	@After
	public void tearDOwn () throws Exception
	{
		// so our tests are clean each time
		DBCollection collection = businessRepository.getCollection ();
		collection.drop ();
	}

	@Before
	public void setUp () throws Exception
	{
		context = new ClassPathXmlApplicationContext("org/soldieringup/mongo-config.xml");
		businessRepository = context.getBean(BusinessRepository.class);
	}

	@Test
	public void testInsert ()
	{
		Business bus = new Business ();
		bus.setAddress ("Oak Grove");
		bus.setName ("ZEDCM");
		bus.setZip ("64075");
		businessRepository.insert (bus);
		List<Business> results = businessRepository.findAll ();
		Business result = results.get (0);
		System.out.println (results);
		assertTrue (result.getZip ().equals (bus.getZip ()));
	}

	@Test
	public void testFindBusinessName ()
	{
		Business bus = new Business ();
		bus.setAddress ("Oak Grove");
		bus.setName ("ZEDCM");
		bus.setZip ("64075");
		businessRepository.insert (bus);

		Business bus2 = new Business ();
		bus2.setAddress ("Oak Grove");
		bus2.setName ("Ninja-Tools");
		bus2.setZip ("1231231");
		businessRepository.insert (bus2);

		List<Business> results = businessRepository.findBusiness ("name", "ZEDCM");
		Business result = results.get (0);
		System.out.println (results);
		assertTrue (result.getZip ().equals (bus.getZip ()));
	}

}

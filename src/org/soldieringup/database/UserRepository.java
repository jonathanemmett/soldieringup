package org.soldieringup.database;

import java.util.List;

import org.soldieringup.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository
{
	private static final String COLLECTION_NAME = "userAccount";

	@Autowired
	MongoOperations op;

	public void insert (User user)
	{
		op.insert (user);
	}

	/**
	 * On startup, make sure that the collection exists
	 */
	public void run () {
		if (!op.collectionExists (COLLECTION_NAME)) {
			op.createCollection (COLLECTION_NAME);
		}
	}

	/**
	 * WARNING!!! THIS IS ONLY FOR TESTING
	 */
	public void dropCollection ()
	{
		if (op.collectionExists (COLLECTION_NAME)) {
			op.dropCollection (COLLECTION_NAME);
		}
	}

	/**
	 * Find all businesses objects
	 * @return List<Business>
	 */
	public List<User> findAll ()
	{
		return op.findAll (User.class);
	}

	public List<User> find (String fieldName, Object fieldValue)
	{
		return op.find (new Query(Criteria.where(fieldName).is(fieldValue)),User.class);
	}

	/**
	 * Returns null if no match found.
	 * @param aEmail
	 * @param aPassword
	 * @return User
	 * @throws Exception if too many users found.
	 */
	public User validateUser( String aEmail, String aPassword ) throws Exception
	{
		//TODO: need to implement SHA1( CONCAT( salt, ? ) )
		List<User> matches = op.find (new Query(Criteria.where("email").is(aEmail)),User.class);
		User user = null;
		if (matches != null) {
			if (matches.size () > 1)
				throw new Exception ("Too many users found in repository");
			if (matches.size () == 1)
				user = matches.get (0);
		}
		return user;
	}

	/**
	 * Checks for an existing e-mail address
	 * @param email_address
	 * @return
	 */
	public boolean emailExists (String email_address)
	{
		if (op.count (new Query(Criteria.where("email").is(email_address)), User.class) == 0)
			return false;
		return true;
	}

	public void save (User user)
	{
		op.save (user);
	}

	public List<User> findByEmailAndPassword (String aEmail, String password)
	{
		List<User> matches = op.find (new Query(Criteria.where("email").is(aEmail)),User.class);
		return matches;
	}

	public void delete (User user)
	{
		System.out.println ("Deleting User:" + user.getUsername ());

	}

	public User findByEmail (String aEmail)
	{
		List<User> matches = op.find (new Query(Criteria.where("email").is(aEmail)),User.class);
		if (matches != null && matches.size () > 0) {
			System.out.println ("findByEmail found a total of:" + matches.size () + " users");
			return matches.get (0);
		} else {
			return null;
		}
	}

}

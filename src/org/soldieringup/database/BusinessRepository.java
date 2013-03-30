package org.soldieringup.database;

import java.util.List;

import org.soldieringup.Business;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.DBCollection;

/**
 * Created to managed all Business objects
 * @author jjennings
 */
@Repository
public class BusinessRepository
{
	@Autowired
	MongoOperations mongoOperations;


	public void insert (Business business)
	{
		mongoOperations.insert (business);
	}

	public void run () {
		if (!mongoOperations.collectionExists (Business.class)) {
			mongoOperations.createCollection(Business.class);
		}
	}

	/**
	 * returns a DBCollection of the Business Repository
	 * @return
	 */
	public DBCollection getCollection ()
	{
		if (mongoOperations.collectionExists (Business.class)) {
			return mongoOperations.getCollection (Business.class.toString ());
		}
		return null;
	}

	/**
	 * Find all businesses objects
	 * @return List<Business>
	 */
	public List<Business> findAll ()
	{
		return mongoOperations.findAll(Business.class);
	}

	public List<Business> findBusiness (String fieldName, Object fieldValue)
	{
		return mongoOperations.find (new Query(Criteria.where(fieldName).is(fieldValue)),Business.class);
	}
}

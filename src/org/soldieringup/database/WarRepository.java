package org.soldieringup.database;

import java.util.List;

import org.soldieringup.War;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class WarRepository
{
	@Autowired
	MongoOperations op;

	public void insert (War war)
	{
		op.insert (war);
	}

	/**
	 * On startup, make sure that the collection exists
	 */
	public void run () {
		if (!op.collectionExists (War.class)) {
			op.createCollection(War.class);
		}
	}

	/**
	 * WARNING!!! THIS IS ONLY FOR TESTING
	 */
	public void dropCollection ()
	{
		if (op.collectionExists (War.class)) {
			op.dropCollection (War.class);
		}
	}

	/**
	 * Find all businesses objects
	 * @return List<Business>
	 */
	public List<War> findAll ()
	{
		return op.findAll(War.class);
	}

	public List<War> find (String fieldName, Object fieldValue)
	{
		return op.find (new Query(Criteria.where(fieldName).is(fieldValue)),War.class);
	}
}

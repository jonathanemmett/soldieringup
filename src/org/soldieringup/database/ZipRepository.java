package org.soldieringup.database;

import java.util.List;

import org.soldieringup.ZIP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class ZipRepository
{
	@Autowired
	MongoOperations op;

	public void insert (ZIP zip)
	{
		op.insert (zip);
	}

	/**
	 * On startup, make sure that the collection exists
	 */
	public void run () {
		if (!op.collectionExists (ZIP.class)) {
			op.createCollection(ZIP.class);
		}
	}

	/**
	 * WARNING!!! THIS IS ONLY FOR TESTING
	 */
	public void dropCollection ()
	{
		if (!op.collectionExists (ZIP.class))
			op.dropCollection (ZIP.class);
	}

	public void verityZipInDatabase (ZIP zip)
	{
		if (find ("zip", zip.getZip ()) == null)
			insert (zip);
	}

	public List<ZIP> findAll ()
	{
		return op.findAll(ZIP.class);
	}

	public List<ZIP> find (String fieldName, Object fieldValue)
	{
		return op.find (new Query(Criteria.where(fieldName).is(fieldValue)),ZIP.class);
	}
}

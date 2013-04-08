package org.soldieringup.database;

import java.util.List;

import org.soldieringup.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class TagRepository
{
	@Autowired
	MongoOperations op;

	public void insert (Tag tag)
	{
		op.insert (tag);
	}

	/**
	 * On startup, make sure that the collection exists
	 */
	public void run () {
		if (!op.collectionExists (Tag.class)) {
			op.createCollection(Tag.class);
		}
	}

	/**
	 * WARNING!!! THIS IS ONLY FOR TESTING
	 */
	public void dropCollection ()
	{
		if (op.collectionExists (Tag.class)) {
			op.dropCollection (Tag.class);
		}
	}

	public void addTags (String[] tags)
	{
		for (String t : tags) {
			Tag tag = new Tag (t);
			insert (tag);
		}
	}

	/**
	 * Find all businesses objects
	 * @return List<Business>
	 */
	public List<Tag> findAll ()
	{
		return op.findAll(Tag.class);
	}

	public List<Tag> find (String fieldName, Object fieldValue)
	{
		return op.find (new Query(Criteria.where(fieldName).is(fieldValue)),Tag.class);
	}
}

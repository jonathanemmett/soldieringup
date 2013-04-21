package org.soldieringup.database;

import java.util.ArrayList;
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

	/**
	 * Find tags that are similiar to a given tag
	 * @param aTag Tag to search for
	 * @return The tags that are similiar to the given tag
	 */
	public List<Tag> findSimiliarTags( String aTag )
	{
		return op.find (new Query(Criteria.where( "name" ).regex( aTag ) ),Tag.class);
	}

	public List<Tag> findSimilarTagsFromMultipleTags( String[] aTags )
	{
		if( aTags.length > 0)
		{
			Criteria criteria = Criteria.where("name").regex( aTags[0] );
			for( int i = 1; i < aTags.length; ++i )
			{
				criteria.orOperator( Criteria.where( "name" ).regex( aTags[i] ) );
			}
			return op.find( new Query( criteria ), Tag.class);
		}
		else
		{
			return new ArrayList<Tag>();
		}
	}

}

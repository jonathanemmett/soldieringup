package org.soldieringup.database;

import java.util.List;

import org.soldieringup.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionRepository
{
	@Autowired
	MongoOperations op;

	public void insert( Question aQuestion )
	{
		op.insert( aQuestion, "questions" );
	}

	/**
	 * On startup, make sure that the collection exists
	 */
	public void run () {
		if (!op.collectionExists ( "questions" ) ) {
			op.createCollection( "questions" );
		}
	}

	public void update( Question aQuestion )
	{
		op.save( aQuestion, "questions" );
	}

	public void remove( Question aQuestion )
	{
		op.remove( aQuestion );
	}

	public List<Question> find( String aFieldName, Object aFieldValue )
	{
		return op.find( new Query( Criteria.where(aFieldName).is(aFieldValue)), Question.class, "questions" );
	}

	/**
	 * WARNING!!! THIS IS ONLY FOR TESTING
	 */
	public void dropCollection ()
	{
		if (op.collectionExists ( "questions" )) {
			op.dropCollection ( "questions" );
		}
	}
}

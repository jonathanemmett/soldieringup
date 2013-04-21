package org.soldieringup.database;

import java.util.List;

import org.soldieringup.MeetingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class MeetingRequestRepository
{
	@Autowired
	MongoOperations op;

	public void insert( MeetingRequest meeting_request )
	{
		op.insert( meeting_request );
	}

	public void update( MeetingRequest meeting_request )
	{
		op.save( meeting_request );
	}

	public void remove( MeetingRequest aQuestion )
	{
		op.remove( aQuestion );
	}

	public List<MeetingRequest> find( String aFieldName, Object aFieldValue )
	{
		return op.find( new Query(Criteria.where( aFieldName ).is( aFieldValue ) ), MeetingRequest.class );
	}

	/**
	 * On startup, make sure that the collection exists
	 */
	public void run () {
		if (!op.collectionExists (MeetingRequest.class)) {
			op.createCollection(MeetingRequest.class);
		}
	}

	/**
	 * WARNING!!! THIS IS ONLY FOR TESTING
	 */
	public void dropCollection ()
	{
		if (op.collectionExists (MeetingRequest.class)) {
			op.dropCollection (MeetingRequest.class);
		}
	}
}

package org.soldieringup.database;

import java.util.List;

import org.soldieringup.Business;
import org.soldieringup.SoldierUpAccount;
import org.soldieringup.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class SoldierUpAccountRepository
{
	@Autowired
	MongoOperations op;

	public void insert( SoldierUpAccount account )
	{
		op.insert( account, "accounts" );
	}

	/**
	 * On startup, make sure that the collection exists
	 */
	public void run () {
		if (!op.collectionExists ( "accounts" )) {
			op.createCollection( "accounts" );
		}
	}

	/**
	 * WARNING!!! THIS IS ONLY FOR TESTING
	 */
	public void dropCollection ()
	{
		if (!op.collectionExists ( "accounts" ))
			op.dropCollection ( "accounts" );
	}

	public void update( SoldierUpAccount account )
	{
		op.save( account, "accounts" );
	}

	public void delete( SoldierUpAccount account )
	{
		op.remove( account );
	}

	public List<SoldierUpAccount> findAccounts( String aFieldName, Object aFieldValue )
	{
		return op.find (new Query(Criteria.where(aFieldName).is(aFieldValue)),SoldierUpAccount.class, "accounts");
	}

	public List<Business> findBusinessesFromTags( List<Tag> aTags )
	{
		return op.find (new Query(Criteria.where("tag").in( aTags).and("_class").is(Business.class.getName()) ), Business.class, "accounts" );
	}
}

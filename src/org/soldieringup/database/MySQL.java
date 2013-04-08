//Copyright (c) <2012> <Jarrett Homann>.
//Copyright (c) <2012> Jared Jennings <jared@jaredjennings.org>.
//All rights reserved.
//
//Redistribution and use in source and binary forms are permitted
//provided that the above copyright notice and this paragraph are
//duplicated in all such forms and that any documentation,
//advertising materials, and other materials related to such
//distribution and use acknowledge that the software was developed
//by the <organization>.  The name of the
//University may not be used to endorse or promote products derived
//from this software without specific prior written permission.
//THIS SOFTWARE IS PROVIDED ``AS IS'' AND WITHOUT ANY EXPRESS OR
//IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
//WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.

package org.soldieringup.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.soldieringup.Business;
import org.soldieringup.MeetingRequest;
import org.soldieringup.Photo;
import org.soldieringup.Question;
import org.soldieringup.Tag;
import org.soldieringup.User;
import org.soldieringup.Utilities;
import org.soldieringup.Veteran;
import org.soldieringup.ZIP;

/**
 * This class is responsible for database transactions
 * @author jjennings
 *
 */
public class MySQL
{
	static final Logger log = Logger.getLogger( MySQL.class.getName() );
	private static MySQL classInstance = null;
	private static final String _jdbcConnString = "jdbc:mysql://localhost/solderingup?user=dbService&password=8w8p21zy0c4pyn";
	private static final String _jakeDB = "jdbc:mysql://localhost/solderingup?user=root&password=mtcs2012";
	private Connection connect = null;

	// database table column titles
	private static final String _tag_name = "name";
	private static final String _tag_id = "id";

	// Types of images to store in the temp uploads file
	public static final String TEMP_UPLOAD_IMAGE_PROFILE = "profile";
	public static final String TEMP_UPLOAD_IMAGE_COVER = "cover";

	public static MySQL getInstance()
	{
		if( classInstance == null )
		{
			classInstance = new MySQL();
		}

		return classInstance;
	}

	private MySQL ()
	{
		dbConn();
	}

	private void dbConn ()
	{
		log.debug ("creating db connection");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(_jakeDB);
			if( connect == null )
			{
				connect = DriverManager.getConnection(_jakeDB);
			}

		} catch (ClassNotFoundException ex) {
			log.log(Level.ERROR, null, ex);
		} catch (SQLException ex) {
			log.log(Level.ERROR, null, ex);
		}
	}

	/**
	 * Gets all the businesses associated with a user account
	 * @param oid The ID of the user
	 * @return All the businesses associated with the given account
	 * @throws SQLException Database could not be queried
	 */
	public ArrayList<Business> getBusinessesFromOwner( long oid ) throws SQLException
	{
		ArrayList<Business> businessesOwnerHas = new ArrayList<Business>();

		String businessSelectSQL = "SELECT * FROM business JOIN accounts ON business.bid = accounts.aid WHERE uid = ?";
		PreparedStatement businessesQuery = connect.prepareStatement( businessSelectSQL );
		businessesQuery.setLong( 1, oid );
		ResultSet businessesResults = businessesQuery.executeQuery();

		while( businessesResults.next() )
		{
			Business nextBusiness = new Business();
			nextBusiness.setProfileSrc( businessesResults.getString( "profile_src" ) );
			nextBusiness.setUid( businessesResults.getLong( "uid" ) );
			nextBusiness.setAid( businessesResults.getLong( "aid" ) );
			nextBusiness.setName( businessesResults.getString( "name" ) );
			nextBusiness.setShortSummary( businessesResults.getString( "short_summary" ) );
			nextBusiness.setLongSummary( businessesResults.getString( "long_summary" ) );
			nextBusiness.setWorkNumber( businessesResults.getString( "work_number" ) );
			nextBusiness.setAddress( businessesResults.getString( "address" ) );
			nextBusiness.setZip( businessesResults.getString( "ZIP" ) );
			nextBusiness.setCoverSrc( businessesResults.getString( "cover_src" ) );
			businessesOwnerHas.add( nextBusiness );
		}

		return businessesOwnerHas;
	}

	/**
	 * Gets the business information for a given business id.
	 * @param bid The business id for the business to query
	 * @return The Business associated with bid
	 * @throws SQLException
	 */
	public Business getBusiness( long bid ) throws SQLException
	{
		Business foundBusiness = null;
		System.out.println( "This business: "+bid );

		String selectSql = "SELECT * FROM accounts JOIN business ON accounts.aid = business.bid WHERE aid = ?";
		PreparedStatement businessQuery = connect.prepareStatement( selectSql );
		businessQuery.setLong( 1, bid );
		ResultSet businessQueryResults = businessQuery.executeQuery();
		foundBusiness = new Business();
		if( businessQueryResults.first() )
		{
			foundBusiness.setProfileSrc( businessQueryResults.getString( "profile_src" ) );
			foundBusiness.setUid( businessQueryResults.getLong( "uid" ) );
			foundBusiness.setAid( businessQueryResults.getLong( "aid" ) );
			foundBusiness.setName( businessQueryResults.getString( "name" ) );
			foundBusiness.setShortSummary( businessQueryResults.getString( "short_summary" ) );
			foundBusiness.setLongSummary( businessQueryResults.getString( "long_summary" ) );
			foundBusiness.setWorkNumber( businessQueryResults.getString( "work_number" ) );
			foundBusiness.setAddress( businessQueryResults.getString( "address" ) );
			foundBusiness.setZip( businessQueryResults.getString( "ZIP" ) );
			foundBusiness.setCoverSrc( businessQueryResults.getString( "cover_src" ) );
		}

		return foundBusiness;
	}

	/**
	 * Get the ZIP code information for a given ZIP code.
	 * @param aZip ZIP Code to find the information for.
	 * @return The ZIP information for a given zip code.
	 * @throws SQLException
	 */
	public ZIP getZIP( String aZip ) throws SQLException
	{
		ZIP queriedZip = new ZIP();
		PreparedStatement stmt;

		stmt = connect.prepareStatement( "SELECT * FROM zip WHERE zip = ?" );

		stmt.setString( 1, aZip );
		ResultSet rs = stmt.executeQuery();

		if( rs.first() )
		{
			queriedZip.setZip( rs.getString("zip") );
			queriedZip.setCity( rs.getString("city") );
			queriedZip.setState( rs.getString("state") );
			queriedZip.setLatitude( rs.getDouble("latitude") );
			queriedZip.setLongtitude( rs.getDouble("longitude") );
		}

		return queriedZip;
	}

	/**
	 *  Logs a user into the system
	 *  @param aEmail E-mail address of the user
	 *  @param aPassword Password of the registered user
	 * @throws SQLException
	 *  @result The result set of the login attempt
	 */
	public User validateUser( String aEmail, String aPassword ) throws SQLException
	{
		User newUser = null;

		PreparedStatement userQuery = connect.prepareStatement( "SELECT * FROM Users WHERE email = ? and password = SHA1( CONCAT( salt, ? ) )" );
		userQuery.setString( 1, aEmail );
		userQuery.setString( 2, aPassword );

		ResultSet userQueryResults = userQuery.executeQuery();
		if( userQueryResults.first() )
		{
			newUser = new User();
			newUser.setUid( userQueryResults.getLong( "id" ) );
			newUser.setFirstName( userQueryResults.getString( "first_name" ) );
			newUser.setLastName( userQueryResults.getString( "last_name" ) );
			newUser.setPrimary_number( userQueryResults.getString( "primary_number" ) );
			newUser.setSecondary_number( userQueryResults.getString( "secondary_number" ) );
			newUser.setAddress( userQueryResults.getString( "address" ) );
			newUser.setEmail(  userQueryResults.getString( "email" ) );
			newUser.setZip( userQueryResults.getString( "zip" ) );
		}

		return newUser;
	}

	/**
	 * Get the user from an associated id
	 * @param uid User ID to query
	 * @return The User if the id exists in the database, null otherwise
	 * @throws SQLException
	 */
	public User getUserFromId( long uid ) throws SQLException
	{
		User foundUser = null;

		PreparedStatement foundUserSql = connect.prepareStatement( "SELECT * FROM Users JOIN accounts ON Users.id = Accounts.uid WHERE id = ?" );
		foundUserSql.setLong( 1, uid );
		ResultSet foundUserResults = foundUserSql.executeQuery();

		if( foundUserResults.first() )
		{
			foundUser = new User();
			foundUser.setAddress( foundUserResults.getString( "address" ) );
			foundUser.setEmail( foundUserResults.getString( "email" ) );
			foundUser.setFirstName( foundUserResults.getString( "first_name" ) );
			foundUser.setLastName( foundUserResults.getString( "last_name" ) );
			foundUser.setAid( foundUserResults.getLong( "aid" ) );
			foundUser.setUid( foundUserResults.getLong( "id" ) );
			foundUser.setPrimary_number( foundUserResults.getString( "primary_number" ) );
			foundUser.setSecondary_number( foundUserResults.getString( "secondary_number" ) );
			foundUser.setZip( foundUserResults.getString( "zip" ) );
			foundUser.setProfileSrc( foundUserResults.getString("profile_src") );
			foundUser.setVeteran( getVeteran( foundUser.getUid() ) );
		}

		return foundUser;
	}

	/**
	 * Checks to see if the email exist
	 * @param aEmail Email to check
	 * @return True if the email is in use, false otherwise
	 * @throws SQLException
	 */
	public boolean checkIfEmailIsInUse( String aEmail ) throws SQLException
	{
		PreparedStatement checkEmail = connect.prepareStatement( "SELECT email FROM Users WHERE email = ?" );
		checkEmail.setString( 1, aEmail );
		ResultSet emailResults = checkEmail.executeQuery();
		return emailResults.first();
	}

	/**
	 * Inserts a new user into the database
	 * @param firstName			User's first name
	 * @param lastName			User's last name
	 * @param email				User's email
	 * @param address			User's address
	 * @param primaryNumber		User's primary phone
	 * @param secondaryNumber	User's secondary phone
	 * @param password			User's password
	 * @param zip				User's zip
	 * @param aErrors			Error messages retrieved while inserting the user
	 * @return					The result set containing the user's id
	 * @throws SQLException
	 */
	public ResultSet registerUser( String aFirstName, String aLastName, String aEmail,
			String aAddress, String aPrimaryNumber, String aSecondaryNumber,
			String aPassword, String aZip, String aCity,
			String aState, Map<String, String> aErrors ) throws SQLException
			{

		verityZipInDatabase( aZip, aCity, aState );
		String businessInsertSQL  = "INSERT INTO Users( first_name, last_name, ";
		businessInsertSQL		 +=	"email, address, primary_number, secondary_number, password, salt, zip ) ";
		businessInsertSQL		 +=	"VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

		PreparedStatement businessInsertStmt = connect.prepareStatement( businessInsertSQL, Statement.RETURN_GENERATED_KEYS );

		// The salt for the user will be the time that they registered
		long salt = new Date().getTime();

		businessInsertStmt.setString(1, aFirstName);
		businessInsertStmt.setString(2, aLastName);
		businessInsertStmt.setString(3, aEmail);
		businessInsertStmt.setString(4, aAddress);
		businessInsertStmt.setString(5, aPrimaryNumber);
		businessInsertStmt.setString(6, aSecondaryNumber == null ? "" : aSecondaryNumber);
		businessInsertStmt.setString(7, Utilities.sha1Output( salt + aPassword) );
		businessInsertStmt.setLong( 8, salt );
		businessInsertStmt.setString( 9, aZip );

		businessInsertStmt.executeUpdate();
		return businessInsertStmt.getGeneratedKeys();
			}

	/**
	 * Inserts a zip code into the database.
	 * @param ZIP ZIP code to insert
	 * @param City City associated to that zip code
	 * @param State State associated to that zip code
	 */
	private void insertZip( String ZIP, String City, String State )
	{
		String query = "INSERT INTO ZIP VALUES( ZIP, City, State) ";
		query += "VALUES(?,?,?)";
		PreparedStatement stmt;

		try
		{
			stmt = connect.prepareStatement( query );
			stmt.setString( 1, ZIP );
			stmt.setString( 2, City );
			stmt.setString( 3, State );
			stmt.executeUpdate();
		}
		catch (SQLException e)
		{
			log.error ("Errors occured while inserting zip " + ZIP, e);
		}
	}

	/**
	 * Adds an account to a user profile
	 * @param aUid ID of user to add an account to
	 * @return The ID of the new account
	 */
	private ResultSet addAccountToUser( long aUid )
	{
		ResultSet generatedKeys = null;

		try
		{
			String newAccountSQL = "INSERT INTO accounts ( uid ) VALUES (?)";
			PreparedStatement newAccountStmt = connect.prepareStatement( newAccountSQL, Statement.RETURN_GENERATED_KEYS );
			newAccountStmt.setLong( 1, aUid );
			newAccountStmt.executeUpdate();
			generatedKeys = newAccountStmt.getGeneratedKeys();
		}
		catch( SQLException e )
		{
			e.printStackTrace();
		}

		return generatedKeys;
	}

	/**
	 * Registers a Veteran profile with SoldierUp
	 * @param uid UID of the person registering the Veteran Profile
	 * @param goal The goal that Veteran wants to accomplish through SoldierUp
	 * @return
	 * @throws SQLException
	 */
	public ResultSet registerVeteran( long uid, String goal ) throws SQLException
	{
		ResultSet generatedUserID = addAccountToUser( uid );
		if( generatedUserID == null )
		{
			return null;
		}

		String insertSql = "INSERT INTO veterans( vid, goal ) VALUES( ?, ? )";
		ResultSet generatedKeys = null;

		PreparedStatement insertStmt = connect.prepareStatement( insertSql, Statement.RETURN_GENERATED_KEYS );
		generatedUserID.next();

		insertStmt.setLong( 1, generatedUserID.getLong( 1 ) );
		insertStmt.setString( 2, goal );
		insertStmt.executeUpdate();
		generatedKeys = insertStmt.getGeneratedKeys();

		return generatedKeys;
	}

	/**
	 * Updates a Veteran's profile
	 * @param vid VID of the veteran to update
	 * @param goal Goal to update the veteran profile with
	 * @throws SQLException
	 */
	public void updateProfileVeteran( long uid, String goal ) throws SQLException
	{
		String updateVeteranSQL = "UPDATE veterans SET goal = ? WHERE vid = ( SELECT aid FROM accounts WHERE uid = ? )";

		PreparedStatement updateVeteranPreparedStatement = connect.prepareStatement( updateVeteranSQL );
		updateVeteranPreparedStatement.setString( 1, goal );
		updateVeteranPreparedStatement.setLong( 2, uid );
		updateVeteranPreparedStatement.executeUpdate();
	}

	/**
	 * Changes who the primary contact of a business is
	 * @param aBusinessId ID of the business to transfer to another User
	 * @param aUserId ID of the user to be the primary contact
	 * @throws SQLException
	 */
	public void transferBusinessContact( long aUserId, long aBusinessId ) throws SQLException
	{
		PreparedStatement businessTransferStatement = connect.prepareStatement( "UPDATE accounts SET uid = ? WHERE aid = ?" );
		businessTransferStatement.setLong( 1, aUserId );
		businessTransferStatement.setLong( 2, aBusinessId );
		businessTransferStatement.executeUpdate();
	}

	/**
	 * Registers a business with SoldierUp
	 * @param aContactID 	Contact ID of the primary business contact
	 * @param aBusinessName Business Name
	 * @param aShortSummary Short description of the business
	 * @param aLongSummary  Detailed description of the business
	 * @param aWorkNumber   Work number for the business
	 * @param aAddress      Street address for the associated business
	 * @param aZip			ZIP address for the associated business
	 * @return				The result set containing the new businesses id
	 * @throws SQLException
	 */
	public ResultSet registerBusiness( int aContactID, String aBusinessName, String aShortSummary,
			String aLongSummary, String aWorkNumber, String aAddress,
			String aCity, String aState, String aZip ) throws SQLException
			{
		ResultSet generatedUserID = addAccountToUser( aContactID );
		if( generatedUserID == null )
		{
			return null;
		}

		verityZipInDatabase( aZip, aCity, aState );

		String businessInsertQuery = "INSERT INTO BUSINESS ";
		businessInsertQuery += "(bid,name,short_summary,long_summary,work_number,address,ZIP)";
		businessInsertQuery += "VALUES(?,?,?,?,?,?,?)";
		PreparedStatement businessSQLInsert = connect.prepareStatement( businessInsertQuery, Statement.RETURN_GENERATED_KEYS );
		businessSQLInsert.setLong( 1, generatedUserID.getLong( "aid" ) );
		businessSQLInsert.setString( 2, aBusinessName );
		businessSQLInsert.setString( 3, aShortSummary );
		businessSQLInsert.setString( 4, aLongSummary );
		businessSQLInsert.setString( 5, aWorkNumber );
		businessSQLInsert.setString( 6, aAddress);
		businessSQLInsert.setString( 7, aZip );

		businessSQLInsert.executeUpdate();
		return businessSQLInsert.getGeneratedKeys();
			}
	/**
	 * Updates the veteran with the given objects
	 * @param aVid User ID of the veteran
	 * @param aParameters The parameters to update the veteran with
	 * @throws SQLException
	 */
	public void updateVeteran( long aUid, Map<String,Object> aParameters ) throws SQLException
	{
		if( !aParameters.isEmpty() )
		{
			String updateVeteranSQL = "UPDATE veterans SET ";
			Iterator<String> parameterKeys = aParameters.keySet().iterator();
			ArrayList<String> validKeys = new ArrayList<String>();

			// Create the SQL String statement, and store all of the keys so
			// that we can loop through them after the statement is created.
			while( parameterKeys.hasNext() )
			{
				String currentKey = parameterKeys.next();
				updateVeteranSQL += currentKey + " = ?,";
				validKeys.add( currentKey );
			}

			//Eliminate the final comma, and append the rest of the SQL statement
			updateVeteranSQL = updateVeteranSQL.substring(0, updateVeteranSQL.length() - 1 );
			updateVeteranSQL += " WHERE vid = (SELECT aid FROM accounts WHERE uid = ?)";

			PreparedStatement updateVeteranStatement = connect.prepareStatement( updateVeteranSQL );
			int currentPreparedStatementIndex;

			for( currentPreparedStatementIndex = 0; currentPreparedStatementIndex < validKeys.size(); ++currentPreparedStatementIndex )
			{
				updateVeteranStatement.setObject( currentPreparedStatementIndex+1, aParameters.get( validKeys.get( currentPreparedStatementIndex ) ) );
			}

			updateVeteranStatement.setLong( currentPreparedStatementIndex + 1, aUid );
			updateVeteranStatement.executeUpdate();
		}
	}

	/**
	 * Updates the business with the given objects
	 * @param aBid ID of the business to update
	 * @param aParameters The parameters to update the business with
	 * @throws SQLException
	 */
	public void updateBusiness( long aBid, Map<String,Object> aParameters ) throws SQLException
	{
		if( !aParameters.isEmpty() )
		{
			String updateBusinessSQL = "UPDATE Business SET ";
			Iterator<String> parameterKeys = aParameters.keySet().iterator();
			ArrayList<String> validKeys = new ArrayList<String>();

			// Create the SQL String statement, and store all of the keys so
			// that we can loop through them after the statement is created.
			while( parameterKeys.hasNext() )
			{
				String currentKey = parameterKeys.next();
				updateBusinessSQL += currentKey + " = ?,";
				validKeys.add( currentKey );
			}

			//Eliminate the final comma, and append the rest of the SQL statement
			updateBusinessSQL = updateBusinessSQL.substring(0, updateBusinessSQL.length() - 1 );
			updateBusinessSQL += " WHERE bid = ?";

			System.out.println( updateBusinessSQL );
			PreparedStatement updateBusinessStatement = connect.prepareStatement( updateBusinessSQL );
			int currentPreparedStatementIndex;

			for( currentPreparedStatementIndex = 0; currentPreparedStatementIndex < validKeys.size(); ++currentPreparedStatementIndex )
			{
				updateBusinessStatement.setObject( currentPreparedStatementIndex+1, aParameters.get( validKeys.get( currentPreparedStatementIndex ) ) );
			}

			updateBusinessStatement.setLong( currentPreparedStatementIndex + 1, aBid );
			System.out.println( updateBusinessSQL );
			updateBusinessStatement.executeUpdate();
		}
	}

	/**
	 * Updates the user with the given objects
	 * @param aBid ID of the user to update
	 * @param aParameters The parameters to update the user with
	 * @throws SQLException
	 */
	public void updateUser( long aUid, Map<String,Object> aParameters ) throws SQLException
	{
		if( !aParameters.isEmpty() )
		{
			System.out.println( "Attempting to change password" );
			String updateBusinessSQL = "UPDATE Users SET ";
			Iterator<String> parameterKeys = aParameters.keySet().iterator();
			ArrayList<String> validKeys = new ArrayList<String>();

			// Create the SQL String statement, and store all of the keys so
			// that we can loop through them after the statement is created.
			while( parameterKeys.hasNext() )
			{
				String currentKey = parameterKeys.next();
				String[] userColumns = {"id","first_name","last_name","email","address","primary_number",
						"secondary_number","password","salt","zip"};
				if( Utilities.isElementInArray(currentKey, userColumns) )
				{
					if( currentKey == "password" )
					{
						updateBusinessSQL += currentKey + " = SHA1( CONCAT( salt, ? ) ),";
					}
					else
					{
						updateBusinessSQL += currentKey + " = ?,";
					}

					validKeys.add( currentKey );
				}

				//Eliminate the final comma, and append the rest of the SQL statement
				updateBusinessSQL = updateBusinessSQL.substring( 0, updateBusinessSQL.length() - 1 );
				updateBusinessSQL += " WHERE id = ?";

				System.out.println( updateBusinessSQL + " " + aUid );

				PreparedStatement updateBusinessStatement = connect.prepareStatement( updateBusinessSQL );
				int currentPreparedStatementIndex;

				for( currentPreparedStatementIndex = 0; currentPreparedStatementIndex < validKeys.size(); ++currentPreparedStatementIndex )
				{
					updateBusinessStatement.setObject( currentPreparedStatementIndex+1, aParameters.get( validKeys.get( currentPreparedStatementIndex ) ) );
				}

				updateBusinessStatement.setLong( currentPreparedStatementIndex + 1, aUid );
				updateBusinessStatement.executeUpdate();
				System.out.println( "Update successful" );
			}
		}
	}

	/**
	 * Checks to see if a given zip is in the database, and if not, insert it
	 * and it's associated information
	 * @param aZip ZIP code
	 * @param aCity City associated to aZip
	 * @param aState State associated to aZip
	 */
	private void verityZipInDatabase( String aZip, String aCity, String aState )
	{
		if( Utilities.stringIsNumeric(aZip) )
		{
			try
			{
				PreparedStatement findZipSql = connect.prepareStatement( "SELECT * FROM zip WHERE zip = ?" );
				findZipSql.setString( 1, aZip );
				ResultSet findZipResults = findZipSql.executeQuery();

				if( !findZipResults.first() && aCity != null && aState != null )
				{
					PreparedStatement insertZip = connect.prepareStatement( "INSERT INTO zip ( zip, city, state ) VALUES ( ?, ?, ? )" );
					insertZip.setString( 1, aZip );
					insertZip.setString( 2, aCity );
					insertZip.setString( 3, aState );
					insertZip.executeUpdate();
				}
			}
			catch (SQLException e)
			{
				log.error ("Errors occured while querying zip table", e);
			}
		}
	}

	/**
	 * Retrieves a photo from a pid
	 * @param pid Photo id of the photo to retrieve
	 * @return The photo associated with the pid
	 * @throws SQLException
	 */
	public Photo getPhotoFromId( long pid ) throws SQLException
	{
		Photo foundPhoto = null;

		System.out.println("Querying the photo: " + pid);
		PreparedStatement photoQuery = connect.prepareStatement( "SELECT * FROM Photos WHERE pid = ?");
		photoQuery.setLong( 1, pid );

		ResultSet photoQueryResult = photoQuery.executeQuery();
		if( photoQueryResult.first() )
		{
			foundPhoto = new Photo();
			foundPhoto.setBid( photoQueryResult.getLong( "bid" ) );
			foundPhoto.setPid( photoQueryResult.getLong( "pid" ) );
			foundPhoto.setSrc( photoQueryResult.getString( "src" ) );
			foundPhoto.setTitle( photoQueryResult.getString( "title" ) );
		}
		return foundPhoto;
	}

	/**
	 * Inserts a photo into the database
	 * @param bid ID of the business owning the photo
	 * @param title Title of the photo
	 * @param src SRC of the photo
	 * @return The generated photo ID if the insertion is successful, null otherwise
	 * @throws SQLException
	 */
	public ResultSet insertPhoto( long bid, String title, String src ) throws SQLException
	{
		ResultSet photoKeys = null;

		PreparedStatement insertPhoto = connect.prepareStatement(
				"INSERT INTO Photos (bid, title, src) VALUES(?,?,?)",
				Statement.RETURN_GENERATED_KEYS
				);

		insertPhoto.setLong( 1, bid );
		insertPhoto.setString( 2, title );
		insertPhoto.setString( 3, src );
		insertPhoto.executeUpdate();
		photoKeys = insertPhoto.getGeneratedKeys();

		return photoKeys;
	}

	/**
	 * Sets the photo for a given account id, photo type, and account type.
	 * @param aOid Object ID for the object to set the photo for ( Veteran or Business ID )
	 * @param aPhotoType Photo type we are setting for the account
	 * @param aAccountType Type of account that aOid corresponds to
	 * @param aSrc The src of the photo
	 * @throws SQLException
	 */
	public void setAccountPhoto( long aOid, String aPhotoType, String aAccountType, String aSrc ) throws SQLException
	{
		PreparedStatement updateUser = null;
		String updateProfilePhotoSql;

		String targetPhoto = aPhotoType.equals( MySQL.TEMP_UPLOAD_IMAGE_COVER ) ? "cover_src" : "profile_src";

		if( targetPhoto.equals( "profile_src" ) )
		{
			updateProfilePhotoSql = "UPDATE accounts SET profile_src = ? WHERE aid = ?";
		}
		else
		{
			updateProfilePhotoSql = "UPDATE business SET cover_src = ? WHERE bid = ?";
		}

		updateUser = connect.prepareStatement( updateProfilePhotoSql );

		updateUser.setString( 1, aSrc );
		updateUser.setLong( 2, aOid );
		updateUser.executeUpdate();
	}

	/**
	 * Inserts a tag into the database if it does not exist
	 * @param tag Tag to insert into the database
	 * @return The id if the insertion tag was successful, 0 otherwise
	 */
	private long insertTag( String tag )
	{
		try
		{
			String insertSql = "INSERT IGNORE INTO tags(tag) VALUES( ? )";
			PreparedStatement insertTagStmt = connect.prepareStatement( insertSql, Statement.RETURN_GENERATED_KEYS );
			insertTagStmt.setString( 1, tag );
			insertTagStmt.executeUpdate();
			ResultSet insertTagId = insertTagStmt.getGeneratedKeys();

			if( insertTagId.first() )
			{
				return insertTagId.getLong( 1 );
			}
			else
			{
				String findTagSql = "SELECT * FROM tags WHERE tags = ?";
				PreparedStatement findTagId = connect.prepareStatement( findTagSql );
				findTagId.setString( 1, tag );
				ResultSet query = findTagId.executeQuery();
				return query.getLong(0);
			}
		}
		catch (SQLException e)
		{
			log.error ("Errors occured while trying to insert a tag", e);
			return 0;
		}
	}

	/**
	 * Gets the id associated to a given tag.
	 *
	 * In the case that a tag is not found, this function also inserts
	 * the tag into the database
	 * @param aTag Tag to get the id for
	 * @return The tag id
	 * @throws SQLException
	 */
	private long getTagId( String aTag ) throws SQLException
	{
		String getTagIdSql = "SELECT id FROM Tags WHERE tag = ?";

		PreparedStatement getTagIdStmt = connect.prepareStatement( getTagIdSql );
		getTagIdStmt.setString( 1, aTag );
		ResultSet getTagIdResult = getTagIdStmt.executeQuery();

		if( getTagIdResult.first() )
		{
			return getTagIdResult.getLong( "id" );
		}
		else
		{
			return insertTag( aTag );
		}
	}

	/**
	 * Attaches a tag to the signed in account for the current session
	 * @param aTag Tag to attach to business
	 * @param aRequest Request to search for an account for
	 * @param aHoursRequested The number of hours that the object requests for a given tag
	 * @return The ID of the tag to insert into the logged in user
	 * @throws SQLException
	 */
	public long attachTagToAccount( String aTag, HttpServletRequest aRequest, Long aHoursRequested ) throws SQLException
	{
		long tagId = -1;
		tagId = getTagId( aTag );

		if( tagId > 0)
		{
			long oid = Long.valueOf( aRequest.getSession().getAttribute( "aid" ).toString() );
			String attachTagToUserSql;
			attachTagToUserSql  = "INSERT IGNORE INTO account_tags (aid, tid, hours_requested)";
			attachTagToUserSql += "VALUE(?, ?, ?)";

			PreparedStatement attachTagStmt = connect.prepareStatement( attachTagToUserSql );
			attachTagStmt.setLong( 1, oid );
			attachTagStmt.setLong( 2, tagId );
			attachTagStmt.setLong( 3, aHoursRequested );
			attachTagStmt.executeUpdate();
		}

		return tagId;
	}

	/**
	 * Detaches a tag from the signed in account for the current session
	 * @param aTag Tag to detach from signed in account
	 * @param aRequest Request to search for an account for
	 * @throws SQLException
	 */
	public void detachTagFromAccount( long aTagId, HttpServletRequest aRequest ) throws SQLException
	{
		long oid = Long.valueOf( aRequest.getSession().getAttribute( "aid" ).toString() );
		String attachTagToUserSql = "DELETE FROM account_tags WHERE tid = ? AND aid = ?";

		PreparedStatement attachTagStmt = connect.prepareStatement( attachTagToUserSql );
		attachTagStmt.setLong( 1, aTagId );
		attachTagStmt.setLong( 2, oid );
		attachTagStmt.executeUpdate();
	}

	/**
	 * Get all the business that have the given tags
	 * @param aTags Tags to search for businesses for
	 * @return The business that have the given tags
	 * @throws SQLException
	 */
	public ArrayList<Business> getBusinessesFromTags(String aTags[]) throws SQLException
	{
		ArrayList<Business> businesses = new ArrayList<Business>();

		String tagIdQuery = "SELECT id FROM tags WHERE ";
		for( int i = 0; i < aTags.length; ++i )
		{
			tagIdQuery += "tag LIKE ? OR ";
		}

		//Remove the last OR statement
		tagIdQuery = tagIdQuery.substring( 0, tagIdQuery.length() - 4 );
		String query = "SELECT * FROM business JOIN accounts on aid = bid WHERE bid in ( SELECT aid FROM account_tags WHERE tid IN ( ";
		query += tagIdQuery + " ) )";

		PreparedStatement businessQueryStatement = connect.prepareStatement( query );
		for( int i = 0; i < aTags.length; ++i )
		{
			businessQueryStatement.setString( i+1, "%"+aTags[i]+"%" );
		}

		ResultSet foundBusinesses = businessQueryStatement.executeQuery();

		while( foundBusinesses.next() )
		{
			Business nextBusiness = new Business();
			nextBusiness.setProfileSrc( foundBusinesses.getString( "profile_src" ) );
			nextBusiness.setUid( foundBusinesses.getLong( "uid" ) );
			nextBusiness.setAid( foundBusinesses.getLong( "aid" ) );
			nextBusiness.setName( foundBusinesses.getString( "name" ) );
			nextBusiness.setShortSummary( foundBusinesses.getString( "short_summary" ) );
			nextBusiness.setLongSummary( foundBusinesses.getString( "long_summary" ) );
			nextBusiness.setWorkNumber( foundBusinesses.getString( "work_number" ) );
			nextBusiness.setAddress( foundBusinesses.getString( "address" ) );
			nextBusiness.setZip( foundBusinesses.getString( "ZIP" ) );
			nextBusiness.setCoverSrc( foundBusinesses.getString( "cover_src" ) );
			businesses.add( nextBusiness );
		}

		return businesses;
	}

	/**
	 * Gets the tags from the given question
	 * @param aQid Question to get the tags for
	 * @return The tags for the given question
	 * @throws SQLException
	 */
	public ArrayList<Tag> getTagsFromQuestion( long aQid ) throws SQLException
	{
		ArrayList<Tag> questionTags = new ArrayList<Tag>();

		String questionTagsSql = "SELECT * FROM Tags WHERE id in ( SELECT tid FROM question_tags WHERE qid = ? )";
		PreparedStatement questionTagsStmt = connect.prepareStatement( questionTagsSql );
		questionTagsStmt.setLong( 1, aQid );
		ResultSet questionTagsResult = questionTagsStmt.executeQuery();

		while( questionTagsResult.next() )
		{
			Tag businessTag = new Tag( questionTagsResult.getString( "tag" ) );
			businessTag.set_tid( questionTagsResult.getInt( "id" ) );
			businessTag.set_name( questionTagsResult.getString( "tag" ) );
			questionTags.add( businessTag );
		}

		return questionTags;
	}

	/**
	 * Finds all the tags associated to a business
	 * @param aOid ID of the business to query for
	 * @return The tags associated to a business
	 * @throws SQLException
	 */
	public ArrayList<Tag> getTagsFromAccount( long aOid ) throws SQLException
	{
		ArrayList<Tag> tags = new ArrayList<Tag>();

		String tagsQuerySql = "SELECT * FROM TAGS WHERE id in ( SELECT tid FROM account_tags WHERE aid = ? )";

		PreparedStatement tagsQueryStmt = connect.prepareStatement( tagsQuerySql );
		tagsQueryStmt.setLong( 1, aOid );
		ResultSet tagsResult = tagsQueryStmt.executeQuery();

		while( tagsResult.next() )
		{
			Tag businessTag = new Tag( tagsResult.getString( "tag" ) );
			businessTag.set_tid( tagsResult.getInt( "id" ) );
			businessTag.set_name( tagsResult.getString( "tag" ) );
			tags.add( businessTag );
		}

		return tags;
	}

	/**
	 * Gets a veteran from a user id
	 * @param uid The user id of the user
	 * @return The Veteran object if found, false otherwise
	 * @throws SQLException
	 */
	public Veteran getVeteran( long uid ) throws SQLException
	{
		Veteran foundVeteran = null;

		String selectSql = "SELECT * FROM accounts JOIN veterans ON veterans.vid = accounts.aid WHERE uid = ?";
		PreparedStatement veteranSql = connect.prepareStatement( selectSql );
		veteranSql.setLong( 1, uid );

		ResultSet veteranQuery = veteranSql.executeQuery();
		if( veteranQuery.first() )
		{
			foundVeteran = new Veteran();
			foundVeteran.setVid( veteranQuery.getLong( "aid" ) );
			foundVeteran.setGoal( veteranQuery.getString( "goal" ) );
		}

		return foundVeteran;
	}

	/**
	 * Returns all the tags that are similar to a given tag and that
	 * also do not exist in the account of the logged in user.
	 * @param aTagToSearch Tag name to find similar tags for
	 * @return The tags found from a keyword
	 * @throws SQLException
	 */
	public JSONArray getSimiliarTags( String aTagToSearch ) throws SQLException
	{
		JSONArray arrayOfTags = new JSONArray();
		String getTagsSql = "SELECT * FROM tags WHERE tag LIKE ? ";

		PreparedStatement stmt = connect.prepareStatement( getTagsSql );
		stmt.setString( 1, "%"+aTagToSearch+"%" );
		ResultSet tags = stmt.executeQuery();

		while( tags.next() )
		{
			arrayOfTags.add( tags.getString( "tag" ) );
		}

		return arrayOfTags;
	}

	/**
	 * Returns all the tags that are similar to a given tag and that
	 * also do not exist in the account of the logged in user.
	 * @param aTagToSearch Tag name to find similar tags for
	 * @return The tags found from a keyword
	 * @throws SQLException
	 */
	public JSONArray getSimiliarTagsNotInAccount( String aTagToSearch, HttpServletRequest aRequest ) throws SQLException
	{
		JSONArray arrayOfTags = new JSONArray();

		long oid;
		String getTagsSql = "SELECT * FROM tags WHERE tag LIKE ? AND id NOT in";
		oid = Long.valueOf( aRequest.getSession().getAttribute( "aid" ).toString() );
		getTagsSql += " (SELECT tid FROM account_tags WHERE aid = ?)";
		PreparedStatement stmt = connect.prepareStatement( getTagsSql );
		stmt.setString( 1, "%"+aTagToSearch+"%" );
		stmt.setLong( 2, oid );
		ResultSet tags = stmt.executeQuery();

		while( tags.next() )
		{
			arrayOfTags.add( tags.getString( "tag" ) );
		}

		return arrayOfTags;
	}

	/**
	 * Inserts a question that a veteran has into the database
	 * @param aQuestionTitle Title of the question
	 * @param aAvailability General string that describes when the Veteran is available
	 * @param aQuestionDetailedDescription Detailed description of the question
	 * @param aVid Id of the veteran asking the question
	 * @throws SQLException
	 */
	public ResultSet insertVeteranQuestion(
			String aQuestionTitle,
			String aAvailability,
			String aQuestionDetailedDescription,
			long aVid ) throws SQLException
			{
		ResultSet generatedQuestionID = null;

		String insertQuestionSQL = "INSERT INTO questions (question_title, availability, question_detailed_description, vid) ";
		insertQuestionSQL 		+= "VALUES( ?, ?, ?, ? )";

		PreparedStatement insertQuestionStatement = connect.prepareStatement( insertQuestionSQL, Statement.RETURN_GENERATED_KEYS );
		insertQuestionStatement.setString( 1, aQuestionTitle );
		insertQuestionStatement.setString( 2, aAvailability );
		insertQuestionStatement.setString( 3, aQuestionDetailedDescription );
		insertQuestionStatement.setLong( 4, aVid );
		insertQuestionStatement.executeUpdate();
		generatedQuestionID = insertQuestionStatement.getGeneratedKeys();

		return generatedQuestionID;
			}

	/**
	 * Removes tags from the question associated to the given id.
	 * @param aQid ID of question to remove tags from
	 * @throws SQLException
	 */
	public void removeTagsFromQuestion( long aQid ) throws SQLException
	{
		String removeTagsSql = "DELETE FROM question_tags WHERE qid = ?";
		PreparedStatement removeTagsStmt = connect.prepareStatement( removeTagsSql );
		removeTagsStmt.setLong( 1, aQid );
		removeTagsStmt.executeUpdate();
	}

	/**
	 * Attaches the given tags to the question with the given id
	 * @param aTag Tag to insert
	 * @param aQid Question to attach tag to
	 * @return The id of the tag
	 * @throws SQLException
	 */
	public long attachTagsToQuestion( String aTag, long aQid ) throws SQLException
	{
		long tagId = -1;
		tagId = getTagId( aTag );

		if( tagId > 0)
		{
			String attachTagToQuestionSql;
			attachTagToQuestionSql  = "INSERT IGNORE INTO question_tags (qid, tid )";
			attachTagToQuestionSql += "VALUE(?, ? )";

			PreparedStatement attachTagStmt = connect.prepareStatement(
					attachTagToQuestionSql );

			attachTagStmt.setLong( 1, aQid );
			attachTagStmt.setLong( 2, tagId );
			attachTagStmt.executeUpdate();
		}

		return tagId;
	}

	/**
	 * Gets a question from the given question id
	 * @param aQid The question Id
	 * @return The question from the ID if the id is found, false otherwise
	 * @throws SQLException
	 */
	public Question getQuestionFromId( long aQid ) throws SQLException
	{
		Question foundQuestion = null;
		String getQuestionsSQL = "SELECT * FROM questions WHERE qid = ?";

		PreparedStatement getQuestionsStatement = connect.prepareStatement( getQuestionsSQL );
		getQuestionsStatement.setLong( 1, aQid );

		ResultSet getQuestionsResults = getQuestionsStatement.executeQuery();

		while( getQuestionsResults.next() )
		{
			foundQuestion = new Question();
			foundQuestion.setQid( getQuestionsResults.getLong( "qid" ) );
			foundQuestion.setQuestionTitle( getQuestionsResults.getString( "question_title" ) );
			foundQuestion.setAvailability( getQuestionsResults.getString( "availability" ) );
			foundQuestion.setVid( getQuestionsResults.getLong( "vid" ) );
		}

		return foundQuestion;
	}

	/**
	 * Gets all the questions that a veteran asked
	 * @param aVid The ID of the veteran to get the questions for
	 * @return The questions that a veteran asked
	 * @throws SQLException
	 */
	public ArrayList<Question> getQuestionsFromVeteran( long aVid ) throws SQLException
	{
		ArrayList<Question> veteranQuestions = new ArrayList<Question>();

		String getQuestionsSQL = "SELECT * FROM questions WHERE vid = ?";

		PreparedStatement getQuestionsStatement = connect.prepareStatement( getQuestionsSQL );
		getQuestionsStatement.setLong( 1, aVid );

		ResultSet getQuestionsResults = getQuestionsStatement.executeQuery();

		while( getQuestionsResults.next() )
		{
			Question currentQuestion = new Question();
			currentQuestion.setQid( getQuestionsResults.getLong( "qid" ) );
			currentQuestion.setQuestionTitle( getQuestionsResults.getString( "question_title" ) );
			currentQuestion.setAvailability( getQuestionsResults.getString( "availability" ) );
			currentQuestion.setVid( getQuestionsResults.getLong( "vid" ) );
			veteranQuestions.add( currentQuestion );
		}

		return veteranQuestions;
	}

	/**
	 * Gets the meeting request for a given question and business
	 * @param aQid ID of the question the meeting request is for
	 * @param aBid ID of the business who sent the request
	 * @return The Meeting Request if found, NULL otherwise
	 * @throws SQLException
	 */
	public MeetingRequest getMeetingRequestFromQIDAndBID( long aQid, long aBid ) throws SQLException
	{
		MeetingRequest foundMeetingRequest = null;

		String findMeetingRequestSQL = "SELECT * From MeetingRequests WHERE qid = ? and bid = ?";
		PreparedStatement findMeetingRequestStmt = connect.prepareStatement( findMeetingRequestSQL );
		findMeetingRequestStmt.setLong( 1, aQid );
		findMeetingRequestStmt.setLong( 2, aBid );
		ResultSet findMeetingRequestResults = findMeetingRequestStmt.executeQuery();

		if( findMeetingRequestResults.next() )
		{
			foundMeetingRequest = new MeetingRequest();
			foundMeetingRequest.setBid( findMeetingRequestResults.getLong( "bid" ) );
			foundMeetingRequest.setQid( findMeetingRequestResults.getLong( "qid" ) );
			foundMeetingRequest.setTime( findMeetingRequestResults.getString( "time" ) );
			foundMeetingRequest.setDay( findMeetingRequestResults.getString( "day" ) );
			foundMeetingRequest.setLocation( findMeetingRequestResults.getString( "location" ) );
		}

		return foundMeetingRequest;
	}

	/**
	 * Gets the meeting request for a given question id
	 * @param aQid The question ID to retrieve the meeting requests for
	 * @throws SQLException
	 */
	public ArrayList<MeetingRequest> getMeetingRequestsForQuestion( long aQid ) throws SQLException
	{
		ArrayList<MeetingRequest> meetingRequests = new ArrayList<MeetingRequest>();

		String getMeetingRequestsSQL = "SELECT * FROM MeetingRequests WHERE qid = ?";
		PreparedStatement getMeetingRequestsStmt;
		getMeetingRequestsStmt = connect.prepareStatement( getMeetingRequestsSQL );
		getMeetingRequestsStmt.setLong( 1, aQid );
		ResultSet getMeetingRequestsResults = getMeetingRequestsStmt.executeQuery();

		while( getMeetingRequestsResults.next() )
		{
			MeetingRequest nextMeetingRequest = new MeetingRequest();
			nextMeetingRequest.setBid( getMeetingRequestsResults.getLong( "bid" ) );
			nextMeetingRequest.setQid( getMeetingRequestsResults.getLong( "qid" ) );
			nextMeetingRequest.setTime( getMeetingRequestsResults.getString( "time" ) );
			nextMeetingRequest.setDay( getMeetingRequestsResults.getString( "day" ) );
			nextMeetingRequest.setLocation( getMeetingRequestsResults.getString( "location" ) );
			meetingRequests.add( nextMeetingRequest );
		}

		return meetingRequests;
	}

	/**
	 * Inserts a meeting request
	 * @param aMeetingParameters
	 * @throws SQLException
	 */
	public void insertMeetingRequests( Map<String,Object> aMeetingParameters ) throws SQLException
	{
		String insertTableSQL = "INSERT INTO meetingrequests (";
		String valuesSQL = " VALUES(";
		Object[] objectsToInsert = new Object[aMeetingParameters.size()];
		int currentObjectArrayIndex = 0;

		Iterator<String> tableColumns = aMeetingParameters.keySet().iterator();
		while( tableColumns.hasNext() )
		{
			String currentKey = tableColumns.next();
			insertTableSQL += currentKey + ",";
			valuesSQL += "?,";
			objectsToInsert[currentObjectArrayIndex++] = aMeetingParameters.get( currentKey );
		}

		insertTableSQL = insertTableSQL.substring( 0, insertTableSQL.length() - 1 ) + ")";
		valuesSQL = valuesSQL.substring( 0, valuesSQL.length() - 1 ) + ")";
		PreparedStatement insertRowStmt = connect.prepareStatement( insertTableSQL + valuesSQL, Statement.RETURN_GENERATED_KEYS );

		for( int i = 0; i < objectsToInsert.length; ++i )
		{
			insertRowStmt.setObject( i + 1, objectsToInsert[i] );
		}

		insertRowStmt.executeUpdate();
	}

	/**
	 * Updates the given table with the provided columns and where clause parameters
	 * @param aTable Table to update
	 * @param aUpdateParameters Mapping of column and value to update the table with
	 * @param aWhereParameters Mappion of column and value to use in the where clause
	 * @throws SQLException
	 */
	public void updateTable( String aTable, Map<String,Object> aUpdateParameters, Map<String,Object> aWhereParameters ) throws SQLException
	{
		String updateSQL = "UPDATE " + aTable + " SET ";
		int currentParameterIndex = 0;
		Object[] parameters = new Object[aUpdateParameters.size() + aWhereParameters.size()];

		Iterator<String> tableColumns = aUpdateParameters.keySet().iterator();
		while( tableColumns.hasNext() )
		{
			String currentColumnKey = tableColumns.next();
			updateSQL += currentColumnKey + " = ?,";
			parameters[currentParameterIndex++] = aUpdateParameters.get( currentColumnKey );
		}
		updateSQL = updateSQL.substring( 0, updateSQL.length() - 1 );
		updateSQL += prepareWhereClause( aWhereParameters, parameters, currentParameterIndex );

		System.out.println( updateSQL );
		PreparedStatement updateStatement = connect.prepareStatement( updateSQL );
		bindParametersToStatement(parameters, updateStatement);

		updateStatement.executeUpdate();
	}

	/**
	 * Adds a where clause to a given statement, and places the parameter values in the given object array
	 * @param aSqlQuery SQL Query to append the where clause to
	 * @param aWhereParameters Parameters to use in the where clause
	 * @param aParameters Array to add the where parameters to
	 * @param aCurrentParameterIndex Current index of the parameters array
	 * @return The where clause of the SQL statement
	 */
	public String prepareWhereClause( Map<String,Object> aWhereParameters, Object[] aParameters, int aCurrentParameterIndex  )
	{
		String whereClause = " WHERE ";
		Iterator<String> whereClauseColumns = aWhereParameters.keySet().iterator();
		while( whereClauseColumns.hasNext() )
		{
			String currentColumnKey = whereClauseColumns.next();
			whereClause += currentColumnKey + " = ? AND ";
			aParameters[aCurrentParameterIndex++] = aWhereParameters.get( currentColumnKey );
		}

		whereClause = whereClause.substring( 0, whereClause.length() - 4 );
		return whereClause;
	}

	/**
	 * Binds the given parameters to the given prepared statement
	 * @param aStatement Statement to bind parameters to
	 * @param aParametersToBind Parameters to bind
	 * @throws SQLException
	 */
	public void bindParametersToStatement( Object[] aParametersToBind, PreparedStatement aStatement ) throws SQLException
	{
		for( int i = 0; i < aParametersToBind.length; ++i )
		{
			aStatement.setObject( i+1, aParametersToBind[i] );
		}
	}

	/**
	 * Deletes rows from a table based on given where parameters
	 * @param aTable Table to delete from
	 * @param aWhereParameters Parameters for the where clause
	 * @throws SQLException
	 */
	public void deleteFromTable( String aTable, Map<String,Object> aWhereParameters ) throws SQLException
	{
		Object[] parameterObjects = new Object[aWhereParameters.size()];
		String deleteSQL = "DELETE FROM " + aTable;
		deleteSQL += prepareWhereClause(aWhereParameters, parameterObjects, 0 );

		System.out.println( deleteSQL );
		PreparedStatement deleteStatement = connect.prepareStatement( deleteSQL );
		bindParametersToStatement( parameterObjects, deleteStatement );
		deleteStatement.executeUpdate();
	}
}

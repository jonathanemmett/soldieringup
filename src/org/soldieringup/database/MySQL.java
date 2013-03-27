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

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;

import org.soldieringup.Account;
import org.soldieringup.Business;
import org.soldieringup.MeetingRequest;
import org.soldieringup.Photo;
import org.soldieringup.Question;
import org.soldieringup.Roster;
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
	private static final String _roster_title = "title";
	private static final String _roster_description = "description";
	private static final String _roster_id = "id";
	private static final String _roster_tags = "tags";
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

	public Map<Object,Roster> retrieveRoster () throws SQLException
	{
		PreparedStatement ps = connect.prepareStatement("select id,title,description,tags from `solderingup`.`roster`");
		return retrieveRoster (ps);
	}
	public Map<Object,Roster> retrieveRoster (int account_id) throws SQLException
	{
		PreparedStatement ps = connect.prepareStatement("select id,title,description,tags from `solderingup`.`roster` WHERE account_id=?");
		ps.setInt (1, account_id);
		return retrieveRoster (ps);
	}

	private Map<Object,Roster> retrieveRoster (PreparedStatement ps)
	{
		Map<Object,Roster> mp = new HashMap<Object, Roster>();
		// got all the Roster entries
		try {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Roster roster = new Roster ();
				roster = parse(rs, roster);
				mp.put (roster.get_id (), roster);
			}
		} catch (Exception e) {
			log.error ("Failed to retrieve roster events", e);
		}
		log.debug ("Loaded " + mp.size () + "Roster posts from the database");
		return mp;
	}

	private Roster parse (ResultSet rs, Roster roster) throws SQLException
	{
		roster.set_id (rs.getInt (_roster_id));
		roster.set_title (rs.getString (_roster_title));
		roster.set_description (rs.getString (_roster_description));
		List<Tag> tags = tags( splitTags (rs.getString (_roster_tags)));
		roster.set_tags (tags);
		return roster;
	}

	/**
	 * Split the tags IDs into an array
	 * @param tags
	 * @return
	 */
	private String[] splitTags (String tags)
	{
		tags = "java;Legal;Mechanical";
		String[] tags_array = tags.split (";");
		return tags_array;
	}

	/**
	 * Should retrieve the tags from the database
	 * @param tags_array
	 * @return
	 */
	private List<Tag> tags (String[] tags_array)
	{
		List<Tag> ls = new ArrayList<Tag>();
		for (String s : tags_array)
		{
			Tag tag = new Tag ();
			tag.set_name (s);
		}

		return ls;
	}

	public Map<Object, Account> retrieveAccounts ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void AddAccount (HashMap<String, String> values)
	{
		try {
			PreparedStatement ps = connect.prepareStatement("INSERT INTO `solderingup`.`accounts`" +
					"(`fname`,"+
					"`lname`,"+
					"`company`,"+
					"`cellphone`,"+
					"`homephone`,"+
					"`businessphone`,"+
					"`address`,"+
					"`city`,"+
					"`state`,"+
					"`zip`,"+
					"`email`)"+
					"VALUES"+
					"(?,"+
					"?,"+
					"?,"+
					"?,"+
					"?,"+
					"?,"+
					"?,"+
					"?,"+
					"?,"+
					"?,"+
					"?)");
			ps.setString (1, values.get ("fname"));
			ps.setString (2, values.get ("lname"));
			ps.setString (3, values.get ("company"));
			ps.setString (4, values.get ("cellphone"));
			ps.setString (5, values.get ("homephone"));
			ps.setString (6, values.get ("businessphone"));
			ps.setString (7, values.get ("address"));
			ps.setString (8, values.get ("city"));
			ps.setString (9, values.get ("state"));
			ps.setString (10, values.get ("zip"));
			ps.setString (11, values.get ("email"));
			log.debug (ps.toString ());
			int result = ps.executeUpdate();
			if (result == 0) {
				log.error (ps.getWarnings ());
				throw new SQLException("Failed, no rows affected.");
			} else {
				log.debug ("Successfully added " + result + " account to the database");
			}

		} catch (Exception e) {
			log.error ("Failed to add account", e);
		}

	}

	public Map<Integer, Tag> retrieveTags ()
	{
		Map<Integer,Tag> mp = new HashMap<Integer, Tag>();
		// got all the Roster entries
		try {
			PreparedStatement ps = connect.prepareStatement("select id,name from `solderingup`.`tags`");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Tag tag = new Tag ();
				tag = parse(rs, tag);
				mp.put (tag.get_id (), tag);
			}
		} catch (Exception e) {
			log.error ("Failed to retrieve roster events", e);
		}
		log.debug ("Loaded " + mp.size () + "Roster posts from the database");
		return mp;
	}

	private Tag parse (ResultSet rs, Tag tag) throws SQLException
	{
		tag.set_id (rs.getInt (_tag_id));
		tag.set_name (rs.getString (_tag_name));
		return tag;
	}

	public void addTags (HashMap<String, String> mp)
	{
		Iterator<?> it = mp.entrySet().iterator();
		while (it.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry pairs = (Map.Entry)it.next();
			addTag (pairs.getValue());
			it.remove(); // avoids a ConcurrentModificationException
		}

	}

	private void addTag (Object tag_name)
	{
		try {
			PreparedStatement ps = connect.prepareStatement("INSERT IGNORE INTO `solderingup`.`tags` (`name`) VALUES (?)");
			ps.setString (1, (String)tag_name);
			log.debug (ps.toString ());
			int result = ps.executeUpdate();
			if (result == 0) {
				log.error (ps.getWarnings ());
				throw new SQLException("Failed, no rows affected.");
			} else {
				log.debug ("Successfully added " + result + " to the tag database");
			}

		} catch (Exception e) {
			log.error ("Failed to add tag", e);
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
			nextBusiness.init( businessesResults );
			businessesOwnerHas.add( nextBusiness );
		}

		return businessesOwnerHas;
	}

	/**
	 * Gets the business information for a given business id.
	 * @param bid The business id for the business to query
	 * @return The Business associated with bid
	 */
	public Business getBusiness( long bid )
	{
		Business foundBusiness = null;
		System.out.println( "This business: "+bid );
		try
		{
			String selectSql = "SELECT * FROM accounts JOIN business ON accounts.aid = business.bid WHERE aid = ?";
			PreparedStatement businessQuery = connect.prepareStatement( selectSql );
			businessQuery.setLong( 1, bid );
			ResultSet businessQueryResults = businessQuery.executeQuery();
			foundBusiness = new Business();
			if( businessQueryResults.first() )
			{
				foundBusiness.init( businessQueryResults );
			}
		}
		catch( SQLException e )
		{
			log.error( "Business code could not be queried", e );
			e.printStackTrace();
		}
		return foundBusiness;
	}

	/**
	 * Get the ZIP code information for a given ZIP code.
	 * @param aZip ZIP Code to find the information for.
	 * @return The ZIP information for a given zip code.
	 */
	public ZIP getZIP( String aZip )
	{
		ZIP queriedZip = new ZIP();
		PreparedStatement stmt;

		try
		{
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
		}
		catch (SQLException e)
		{
			log.error( "ZIP code could not be queried", e );
			e.printStackTrace();
		}

		return queriedZip;
	}

	/**
	 *  Logs a user into the system
	 *  @param aEmail E-mail address of the user
	 *  @param aPassword Password of the registered user
	 *  @result The result set of the login attempt
	 */
	public User validateUser( String aEmail, String aPassword )
	{
		User newUser = null;

		try
		{
			PreparedStatement userQuery = connect.prepareStatement( "SELECT * FROM Users WHERE email = ? and password = SHA1( CONCAT( salt, ? ) )" );
			userQuery.setString( 1, aEmail );
			userQuery.setString( 2, aPassword );


			ResultSet userQueryResults = userQuery.executeQuery();
			if( userQueryResults.first() )
			{
				newUser = new User();
				newUser.setId( userQueryResults.getLong( "id" ) );
				newUser.setFirstName( userQueryResults.getString( "first_name" ) );
				newUser.setLastName( userQueryResults.getString( "last_name" ) );
				newUser.setPrimaryNumber( userQueryResults.getString( "primary_number" ) );
				newUser.setSecondaryNumber( userQueryResults.getString( "secondary_number" ) );
				newUser.setAddress( userQueryResults.getString( "address" ) );
				newUser.setEmail(  userQueryResults.getString( "email" ) );
				newUser.setZip( userQueryResults.getString( "zip" ) );
			}

		}
		catch (SQLException e)
		{
			log.log(Level.ERROR, null, e);
			e.printStackTrace();
		}

		return newUser;
	}

	/**
	 * Get the user from an associated id
	 * @param uid User ID to query
	 * @return The User if the id exists in the database, null otherwise
	 */
	public User getUserFromId( long uid )
	{
		User foundUser = null;

		try
		{
			PreparedStatement foundUserSql = connect.prepareStatement( "SELECT * FROM Users WHERE id = ?" );
			foundUserSql.setLong( 1, uid );
			ResultSet foundUserResults = foundUserSql.executeQuery();

			if( foundUserResults.first() )
			{
				foundUser = new User();
				foundUser.setAddress( foundUserResults.getString( "address" ) );
				foundUser.setEmail( foundUserResults.getString( "email" ) );
				foundUser.setFirstName( foundUserResults.getString( "first_name" ) );
				foundUser.setLastName( foundUserResults.getString( "last_name" ) );
				foundUser.setId( foundUserResults.getLong( "id" ) );
				foundUser.setPrimaryNumber( foundUserResults.getString( "primary_number" ) );
				foundUser.setSecondaryNumber( foundUserResults.getString( "secondary_number" ) );
				foundUser.setZip( foundUserResults.getString( "zip" ) );
			}
		}
		catch(SQLException e)
		{
			log.error ("Errors occured while querying user", e);

		}

		return foundUser;
	}

	/**
	 * Checks to see if the email exist
	 * @param aEmail Email to check
	 * @return True if the email is in use, false otherwise
	 */
	public boolean checkIfEmailIsInUse( String aEmail )
	{
		try
		{
			PreparedStatement checkEmail = connect.prepareStatement( "SELECT email FROM Users WHERE email = ?" );
			checkEmail.setString( 1, aEmail );
			ResultSet emailResults = checkEmail.executeQuery();
			return emailResults.first();
		}
		catch (SQLException e)
		{
			log.log(Level.ERROR, null, e);
		}

		return true;
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
	 */
	public ResultSet registerUser( String aFirstName, String aLastName, String aEmail,
			String aAddress, String aPrimaryNumber, String aSecondaryNumber,
			String aPassword, String aZip, String aCity,
			String aState, Map<String, String> aErrors )
	{
		try
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
		catch (SQLException e)
		{
			aErrors.put("result", "Your account could not be entered. " + e.getMessage() );
			e.printStackTrace();
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Inserts a zip code into the database.
	 * @param ZIP ZIP code to insert
	 * @param City City associated to that zip code
	 * @param State State associated to that zip code
	 */
	public void insertZip( String ZIP, String City, String State )
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
			e.printStackTrace();
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
	 */
	public ResultSet registerVeteran( long uid, String goal )
	{
		ResultSet generatedUserID = addAccountToUser( uid );
		if( generatedUserID == null )
		{
			return null;
		}

		String insertSql = "INSERT INTO veterans( vid, goal ) VALUES( ?, ? )";
		ResultSet generatedKeys = null;

		try
		{
			PreparedStatement insertStmt = connect.prepareStatement( insertSql, Statement.RETURN_GENERATED_KEYS );
			generatedUserID.next();

			insertStmt.setLong( 1, generatedUserID.getLong( 1 ) );
			insertStmt.setString( 2, goal );
			insertStmt.executeUpdate();
			generatedKeys = insertStmt.getGeneratedKeys();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return generatedKeys;
	}

	/**
	 * Updates a Veteran's profile
	 * @param vid VID of the veteran to update
	 * @param goal Goal to update the veteran profile with
	 */
	public void updateProfileVeteran( long vid, String goal )
	{
		try
		{
			String updateVeteranSQL = "UPDATE veterans SET goal = ? WHERE vid = ?";

			PreparedStatement updateVeteranPreparedStatement = connect.prepareStatement( updateVeteranSQL );
			updateVeteranPreparedStatement.setString( 1, goal );
			updateVeteranPreparedStatement.setLong( 2, vid );
			updateVeteranPreparedStatement.executeUpdate();
		}
		catch( SQLException e )
		{
			log.error ("Veteran could not be update", e);
		}
	}

	/**
	 * Changes who the primary contact of a business is
	 * @param aBusinessId ID of the business to transfer to another User
	 * @param aUserId ID of the user to be the primary contact
	 */
	public void transferBusinessContact( long aUserId, long aBusinessId )
	{
		try
		{
			PreparedStatement businessTransferStatement = connect.prepareStatement( "UPDATE accounts SET uid = ? WHERE aid = ?" );
			businessTransferStatement.setLong( 1, aUserId );
			businessTransferStatement.setLong( 2, aBusinessId );
			businessTransferStatement.executeUpdate();
		}
		catch (SQLException e)
		{
			log.error ("Business " + aBusinessId + " could not be transferred to " + aUserId, e );
		}
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
	 */
	public ResultSet registerBusiness( int aContactID, String aBusinessName, String aShortSummary,
			String aLongSummary, String aWorkNumber, String aAddress,
			String aCity, String aState, String aZip )
	{
		ResultSet generatedUserID = addAccountToUser( aContactID );
		if( generatedUserID == null )
		{
			return null;
		}

		try
		{
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
		catch (SQLException e)
		{
			log.error ("Business could not be registered", e);
			return null;
		}
	}

	/**
	 * Updates the veteran with the given objects
	 * @param aVid ID of the veteran to update
	 * @param aParameters The parameters to update the veteran with
	 */
	public void updateVeteran( long aVid, Map<String,Object> aParameters )
	{
		try
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
				updateVeteranSQL += " WHERE vid = ?";

				PreparedStatement updateVeteranStatement = connect.prepareStatement( updateVeteranSQL );
				int currentPreparedStatementIndex;

				for( currentPreparedStatementIndex = 0; currentPreparedStatementIndex < validKeys.size(); ++currentPreparedStatementIndex )
				{
					updateVeteranStatement.setObject( currentPreparedStatementIndex+1, aParameters.get( validKeys.get( currentPreparedStatementIndex ) ) );
				}

				updateVeteranStatement.setLong( currentPreparedStatementIndex + 1, aVid );
				System.out.println( updateVeteranSQL );
				updateVeteranStatement.executeUpdate();
			}
		}
		catch(SQLException e)
		{
			log.error ("Veteran "+aVid+" could not be registered", e);
		}
	}

	/**
	 * Updates the business with the given objects
	 * @param aBid ID of the business to update
	 * @param aParameters The parameters to update the business with
	 */
	public void updateBusiness( long aBid, Map<String,Object> aParameters )
	{
		try
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
		catch(SQLException e)
		{
			log.error ("Business "+aBid+" could not be registered", e);
		}
	}

	/**
	 * Updates the user with the given objects
	 * @param aBid ID of the user to update
	 * @param aParameters The parameters to update the user with
	 */
	public void updateUser( long aUid, Map<String,Object> aParameters )
	{
		try
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
					if( User.isValidDatabaseInput( currentKey, (String) aParameters.get( currentKey ) ) )
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
		catch(SQLException e)
		{
			e.printStackTrace();
			log.error ("User "+aUid+" could not be registered", e);
		}
	}

	/**
	 * Checks to see if a given zip is in the database, and if not, insert it
	 * and it's associated information
	 * @param aZip ZIP code
	 * @param aCity City associated to aZip
	 * @param aState State associated to aZip
	 */
	public void verityZipInDatabase( String aZip, String aCity, String aState )
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
	 */
	public Photo getPhotoFromId( long pid )
	{
		Photo foundPhoto = null;

		System.out.println("Querying the photo: " + pid);
		try
		{
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
		}
		catch (SQLException e)
		{
			log.error ("Errors occured while querying photo table", e);
		}

		return foundPhoto;
	}

	/**
	 * Inserts a photo into the database
	 * @param bid ID of the business owning the photo
	 * @param title Title of the photo
	 * @param src SRC of the photo
	 * @return The generated photo ID if the insertion is successful, null otherwise
	 */
	public ResultSet insertPhoto( long bid, String title, String src )
	{
		ResultSet photoKeys = null;
		try
		{
			PreparedStatement insertPhoto = connect.prepareStatement(
					"INSERT INTO Photos (bid, title, src) VALUES(?,?,?)",
					Statement.RETURN_GENERATED_KEYS
					);

			insertPhoto.setLong( 1, bid );
			insertPhoto.setString( 2, title );
			insertPhoto.setString( 3, src );
			insertPhoto.executeUpdate();
			photoKeys = insertPhoto.getGeneratedKeys();
		}
		catch(SQLException e)
		{

		}

		return photoKeys;
	}

	/**
	 * Sets the photo for a given account id, photo type, and account type.
	 * @param aOid Object ID for the object to set the photo for ( Veteran or Business ID )
	 * @param aPhotoType Photo type we are setting for the account
	 * @param aAccountType Type of account that aOid corresponds to
	 * @param aSrc The src of the photo
	 */
	public void setAccountPhoto(
			long aOid,
			String aPhotoType,
			String aAccountType,
			String aSrc )
	{
		System.out.println( "Account Profile" );
		try
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
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Inserts a tag into the database if it does not exist
	 * @param tag Tag to insert into the database
	 * @return The id if the insertion tag was successful, 0 otherwise
	 */
	public long insertTag( String tag )
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
	public long getTagId( String aTag ) throws SQLException
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
	 */
	public long attachTagToAccount( String aTag, HttpServletRequest aRequest, Long aHoursRequested )
	{
		long tagId = -1;

		System.out.println( "Attempting to tag" );
		try
		{
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
		}
		catch( SQLException e)
		{
			e.printStackTrace();
			log.error ("Errors occured while trying to attach a tag to an account", e);
		}

		return tagId;
	}

	/**
	 * Detaches a tag from the signed in account for the current session
	 * @param aTag Tag to detach from signed in account
	 * @param aRequest Request to search for an account for
	 */
	public void detachTagFromAccount( long aTagId, HttpServletRequest aRequest )
	{
		try
		{
			long oid = Long.valueOf( aRequest.getSession().getAttribute( "aid" ).toString() );
			String attachTagToUserSql = "DELETE FROM account_tags WHERE tid = ? AND aid = ?";

			PreparedStatement attachTagStmt = connect.prepareStatement( attachTagToUserSql );
			attachTagStmt.setLong( 1, aTagId );
			attachTagStmt.setLong( 2, oid );
			attachTagStmt.executeUpdate();
		}
		catch( SQLException e)
		{
			e.printStackTrace();
			log.error ("Errors occured while trying to detach a tag from an account", e);
		}
	}

	/**
	 * Get all the business that have the given tags
	 * @param aTags Tags to search for businesses for
	 * @return The business that have the given tags
	 */
	public ArrayList<Business> getBusinessesFromTags(String aTags[])
	{
		ArrayList<Business> businesses = new ArrayList<Business>();

		try
		{
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
				nextBusiness.init( foundBusinesses );
				businesses.add( nextBusiness );
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}

		return businesses;
	}

	/**
	 * Gets the tags from the given question
	 * @param aQid Question to get the tags for
	 * @return The tags for the given question
	 */
	public ArrayList<Tag> getTagsFromQuestion( long aQid )
	{
		ArrayList<Tag> questionTags = new ArrayList<Tag>();

		try
		{
			String questionTagsSql = "SELECT * FROM Tags WHERE id in ( SELECT tid FROM question_tags WHERE qid = ? )";
			PreparedStatement questionTagsStmt = connect.prepareStatement( questionTagsSql );
			questionTagsStmt.setLong( 1, aQid );
			ResultSet questionTagsResult = questionTagsStmt.executeQuery();

			System.out.println( questionTagsSql );
			while( questionTagsResult.next() )
			{
				Tag businessTag = new Tag();
				businessTag.set_id( questionTagsResult.getInt( "id" ) );
				businessTag.set_name( questionTagsResult.getString( "tag" ) );
				System.out.println( "Tag: " + businessTag.get_name() + " " + businessTag.get_id() );
				questionTags.add( businessTag );
			}

			System.out.println("Count: " + questionTags.size() );
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return questionTags;
	}

	/**
	 * Finds all the tags associated to a business
	 * @param aOid ID of the business to query for
	 * @return The tags associated to a business
	 */
	public ArrayList<Tag> getTagsFromAccount( long aOid )
	{
		ArrayList<Tag> tags = new ArrayList<Tag>();

		try
		{
			String tagsQuerySql = "SELECT * FROM TAGS WHERE id in ( SELECT tid FROM account_tags WHERE aid = ? )";

			PreparedStatement tagsQueryStmt = connect.prepareStatement( tagsQuerySql );
			tagsQueryStmt.setLong( 1, aOid );
			ResultSet tagsResult = tagsQueryStmt.executeQuery();

			while( tagsResult.next() )
			{
				Tag businessTag = new Tag();
				businessTag.set_id( tagsResult.getInt( "id" ) );
				businessTag.set_name( tagsResult.getString( "tag" ) );
				tags.add( businessTag );
			}
		}
		catch(SQLException e)
		{
			log.error ("Errors occured while searching for business tags", e);
		}

		return tags;
	}

	/**
	 * Gets a veteran from a user id
	 * @param uid The user id of the user
	 * @return The Veteran object if found, false otherwise
	 */
	public Veteran getVeteran( long uid )
	{
		Veteran foundVeteran = null;
		try
		{
			String selectSql = "SELECT * FROM accounts JOIN veterans ON veterans.vid = accounts.aid WHERE uid = ?";
			PreparedStatement veteranSql = connect.prepareStatement( selectSql );
			veteranSql.setLong( 1, uid );

			ResultSet veteranQuery = veteranSql.executeQuery();
			if( veteranQuery.first() )
			{
				foundVeteran = new Veteran();
				foundVeteran.init( veteranQuery );
			}
		}
		catch( SQLException e)
		{
			log.error ("Errors occured while trying to retrieve a veteran", e);
			e.printStackTrace();
		}

		return foundVeteran;
	}

	/**
	 * Returns all the tags that are similar to a given tag and that
	 * also do not exist in the account of the logged in user.
	 * @param aTagToSearch Tag name to find similar tags for
	 * @return The tags found from a keyword
	 */
	public JSONArray getSimiliarTags( String aTagToSearch )
	{
		JSONArray arrayOfTags = new JSONArray();

		try
		{
			String getTagsSql = "SELECT * FROM tags WHERE tag LIKE ? ";

			PreparedStatement stmt = connect.prepareStatement( getTagsSql );
			stmt.setString( 1, "%"+aTagToSearch+"%" );
			ResultSet tags = stmt.executeQuery();

			while( tags.next() )
			{
				arrayOfTags.add( tags.getString( "tag" ) );
			}
		}
		catch(SQLException e)
		{

		}

		return arrayOfTags;
	}

	/**
	 * Returns all the tags that are similar to a given tag and that
	 * also do not exist in the account of the logged in user.
	 * @param aTagToSearch Tag name to find similar tags for
	 * @return The tags found from a keyword
	 */
	public JSONArray getSimiliarTagsNotInAccount( String aTagToSearch, HttpServletRequest aRequest )
	{
		JSONArray arrayOfTags = new JSONArray();

		try
		{
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
		}
		catch(SQLException e)
		{

		}

		return arrayOfTags;
	}

	/**
	 * Inserts a question that a veteran has into the database
	 * @param aQuestionTitle Title of the question
	 * @param aAvailability General string that describes when the Veteran is available
	 * @param aQuestionDetailedDescription Detailed description of the question
	 * @param aVid Id of the veteran asking the question
	 */
	public ResultSet insertVeteranQuestion(
			String aQuestionTitle,
			String aAvailability,
			String aQuestionDetailedDescription,
			long aVid )
	{
		ResultSet generatedQuestionID = null;

		try
		{
			String insertQuestionSQL = "INSERT INTO questions (question_title, availability, question_detailed_description, vid) ";
			insertQuestionSQL 		+= "VALUES( ?, ?, ?, ? )";

			PreparedStatement insertQuestionStatement = connect.prepareStatement( insertQuestionSQL, Statement.RETURN_GENERATED_KEYS );
			insertQuestionStatement.setString( 1, aQuestionTitle );
			insertQuestionStatement.setString( 2, aAvailability );
			insertQuestionStatement.setString( 3, aQuestionDetailedDescription );
			insertQuestionStatement.setLong( 4, aVid );
			insertQuestionStatement.executeUpdate();
			generatedQuestionID = insertQuestionStatement.getGeneratedKeys();
		}
		catch( SQLException e )
		{
			log.error ("Failed to insert a question into the database", e);
		}

		return generatedQuestionID;
	}

	/**
	 * Removes tags from the question associated to the given id.
	 * @param aQid ID of question to remove tags from
	 */
	public void removeTagsFromQuestion( long aQid )
	{
		try
		{
			String removeTagsSql = "DELETE FROM question_tags WHERE qid = ?";
			PreparedStatement removeTagsStmt = connect.prepareStatement( removeTagsSql );
			removeTagsStmt.setLong( 1, aQid );
			removeTagsStmt.executeUpdate();
		}
		catch (SQLException e )
		{
			log.error ("Failed to delete tags from question", e);
		}
	}
	/**
	 * Attaches the given tags to the question with the given id
	 */
	public long attachTagsToQuestion( String aTag, long aQid )
	{
		long tagId = -1;
		try
		{
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
		}
		catch( SQLException e)
		{
			e.printStackTrace();
			log.error ("Errors occured while trying to attach a tag to an account", e);
		}

		return tagId;
	}

	/**
	 * Gets a question from the given question id
	 * @param aQid The question Id
	 * @return The question from the ID if the id is found, false otherwise
	 */
	public Question getQuestionFromId( long aQid )
	{
		Question foundQuestion = null;
		try
		{
			String getQuestionsSQL = "SELECT * FROM questions WHERE qid = ?";

			PreparedStatement getQuestionsStatement = connect.prepareStatement( getQuestionsSQL );
			getQuestionsStatement.setLong( 1, aQid );

			ResultSet getQuestionsResults = getQuestionsStatement.executeQuery();

			while( getQuestionsResults.next() )
			{
				foundQuestion = new Question();
				foundQuestion.init( getQuestionsResults );
			}
		}
		catch( SQLException e )
		{

		}

		return foundQuestion;
	}

	/**
	 * Gets all the questions that a veteran asked
	 * @param aVid The ID of the veteran to get the questions for
	 * @return The questions that a veteran asked
	 */
	public ArrayList<Question> getQuestionsFromVeteran( long aVid )
	{
		ArrayList<Question> veteranQuestions = new ArrayList<Question>();

		try
		{
			String getQuestionsSQL = "SELECT * FROM questions WHERE vid = ?";

			PreparedStatement getQuestionsStatement = connect.prepareStatement( getQuestionsSQL );
			getQuestionsStatement.setLong( 1, aVid );

			ResultSet getQuestionsResults = getQuestionsStatement.executeQuery();

			while( getQuestionsResults.next() )
			{
				Question currentQuestion = new Question();
				currentQuestion.init( getQuestionsResults );
				veteranQuestions.add( currentQuestion );
			}
		}
		catch( SQLException e )
		{

		}

		return veteranQuestions;
	}

	/**
	 * Gets the meeting request for a given question and business
	 * @param aQid ID of the question the meeting request is for
	 * @param aBid ID of the business who sent the request
	 * @return The Meeting Request if found, NULL otherwise
	 */
	public MeetingRequest getMeetingRequestFromQIDAndBID( long aQid, long aBid )
	{
		MeetingRequest foundMeetingRequest = null;

		try
		{
			String findMeetingRequestSQL = "SELECT * From MeetingRequests WHERE qid = ? and bid = ?";
			PreparedStatement findMeetingRequestStmt = connect.prepareStatement( findMeetingRequestSQL );
			findMeetingRequestStmt.setLong( 1, aQid );
			findMeetingRequestStmt.setLong( 2, aBid );
			ResultSet findMeetingRequestResults = findMeetingRequestStmt.executeQuery();

			if( findMeetingRequestResults.next() )
			{
				foundMeetingRequest = new MeetingRequest();
				foundMeetingRequest.init( findMeetingRequestResults );
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return foundMeetingRequest;
	}

	/**
	 * Gets the meeting request for a given question id
	 * @param aQid The question ID to retrieve the meeting requests for
	 */
	public ArrayList<MeetingRequest> getMeetingRequestsForQuestion( long aQid )
	{
		ArrayList<MeetingRequest> meetingRequests = new ArrayList<MeetingRequest>();

		try
		{
			String getMeetingRequestsSQL = "SELECT * FROM MeetingRequests WHERE qid = ?";
			PreparedStatement getMeetingRequestsStmt;
			getMeetingRequestsStmt = connect.prepareStatement( getMeetingRequestsSQL );
			getMeetingRequestsStmt.setLong( 1, aQid );
			ResultSet getMeetingRequestsResults = getMeetingRequestsStmt.executeQuery();

			while( getMeetingRequestsResults.next() )
			{
				MeetingRequest nextMeetingRequest = new MeetingRequest();
				nextMeetingRequest.init( getMeetingRequestsResults );
				meetingRequests.add( nextMeetingRequest );
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return meetingRequests;
	}

	/**
	 * Inserts the a row into the given table with the given column names and values
	 * @param aTable Table to insert a new row into
	 * @param aInsertParameters Parameters to use for the new row
\	 * @throws SQLException
	 */
	public void insertIntoTable( String aTable, Map<String,Object> aInsertParameters ) throws SQLException
	{
		String insertTableSQL = "INSERT INTO " + aTable + " (";
		String valuesSQL = " VALUES(";
		Object[] objectsToInsert = new Object[aInsertParameters.size()];
		int currentObjectArrayIndex = 0;

		Iterator<String> tableColumns = aInsertParameters.keySet().iterator();
		while( tableColumns.hasNext() )
		{
			String currentKey = tableColumns.next();
			insertTableSQL += currentKey + ",";
			valuesSQL += "?,";
			objectsToInsert[currentObjectArrayIndex++] = aInsertParameters.get( currentKey );
		}

		insertTableSQL = insertTableSQL.substring( 0, insertTableSQL.length() - 1 ) + ")";
		valuesSQL = valuesSQL.substring( 0, valuesSQL.length() - 1 ) + ")";
		PreparedStatement insertRowStmt = connect.prepareStatement(
				insertTableSQL + valuesSQL,
				Statement.RETURN_GENERATED_KEYS
				);

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

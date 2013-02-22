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

import java.io.File;
import java.io.IOException;
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
import java.util.Set;

import org.apache.catalina.tribes.util.Arrays;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.soldieringup.Account;
import org.soldieringup.Business;
import org.soldieringup.Photo;
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
		roster.set_id ((Integer) rs.getInt (_roster_id));
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
		tag.set_id ((Integer) rs.getInt (_tag_id));
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
	 * Creates a prepared statement that allows for the return of generated primary keys
	 * @param sql SQL statement to initialize the PreparedStatement with
	 * @return The generated PreparedStatement
	 * @throws SQLException
	 */
	public PreparedStatement getPreparedStatement(String sql) throws SQLException
	{
		return connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	}
	 
	/**
	 * Gets all the businesses associated with a user account
	 * @param oid The ID of the user
	 * @return All the businesses associated with the given account
	 * @throws SQLException Database could not be queried
	 */
	public Map<Integer,Business> getBusinessesFromOwner( long oid ) throws SQLException
	{
		Map<Integer,Business> businessesOwnerHas = new HashMap<Integer,Business>();
		
		PreparedStatement businessesQuery = connect.prepareStatement( "SELECT * FROM Business WHERE contact_id = ?" );
		businessesQuery.setLong( 1, oid );
		ResultSet businessesResults = businessesQuery.executeQuery();
		
		while( businessesResults.next() )
		{
			Business nextBusiness = new Business();
			nextBusiness.setBid( businessesResults.getLong( "bid" ) );
			nextBusiness.setContactId( businessesResults.getLong( "contact_id" ) );
			nextBusiness.setCoverSrc( businessesResults.getString( "cover_src" ) );
			nextBusiness.setProfilePhotoSrc( businessesResults.getString( "profile_src") );
			nextBusiness.setName( businessesResults.getString( "name" ) );
			nextBusiness.setShortSummary( businessesResults.getString( "short_summary" ) );
			nextBusiness.setLongSummary( businessesResults.getString("long_summary") );
			nextBusiness.setAddress( businessesResults.getString("address") );
			nextBusiness.setZip( businessesResults.getString( "zip" ) );
			businessesOwnerHas.put( businessesResults.getInt( "bid" ), nextBusiness );
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
			PreparedStatement businessQuery = connect.prepareStatement("SELECT * FROM Business WHERE bid = ?");
			businessQuery.setLong( 1, bid );
			ResultSet businessQueryResults = businessQuery.executeQuery();
			foundBusiness = new Business();
			if( businessQueryResults.first() )
			{
				foundBusiness.setBid( businessQueryResults.getInt( "bid" ) );
				foundBusiness.setContactId( businessQueryResults.getInt( "contact_id" ) );
				foundBusiness.setName( businessQueryResults.getString( "name" ) );
				foundBusiness.setCoverSrc( businessQueryResults.getString( "cover_src" ) );
				foundBusiness.setProfilePhotoSrc( businessQueryResults.getString( "profile_src" ) );
				foundBusiness.setShortSummary( businessQueryResults.getString( "short_summary" ) );
				foundBusiness.setLongSummary( businessQueryResults.getString("long_summary") );
				foundBusiness.setAddress( businessQueryResults.getString("address") );
				foundBusiness.setZip( businessQueryResults.getString( "zip" ) );
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
		MySQL connection = MySQL.getInstance();
		
		ZIP queriedZip = new ZIP();
		PreparedStatement stmt;
		
		try 
		{
			stmt = connection.getPreparedStatement("SELECT * FROM zip WHERE zip = ?");
		
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
			// Database could not be queried
			log.log(Level.ERROR, null, e);
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
		MySQL databaseConnection = MySQL.getInstance();
		try 
		{
			verityZipInDatabase( aZip, aCity, aState );
			PreparedStatement businessSQLInsert = 
					databaseConnection.getPreparedStatement("INSERT INTO Users( first_name, last_name, " +
					"email, address, primary_number, secondary_number, password, salt, zip ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
			
			// The salt for the user will be the time that they registered
			long salt = new Date().getTime();
			
			businessSQLInsert.setString(1, aFirstName);
			businessSQLInsert.setString(2, aLastName);
			businessSQLInsert.setString(3, aEmail);
			businessSQLInsert.setString(4, aAddress);
			businessSQLInsert.setString(5, aPrimaryNumber);
			businessSQLInsert.setString(6, aSecondaryNumber == null ? "" : aSecondaryNumber);
			businessSQLInsert.setString(7, Utilities.sha1Output( salt + aPassword) );
			businessSQLInsert.setLong( 8, salt );
			businessSQLInsert.setString( 9, aZip );
			
			businessSQLInsert.executeUpdate();
			return businessSQLInsert.getGeneratedKeys();
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
		try {
			stmt = getPreparedStatement( query );
			stmt.setString( 1, ZIP );
			stmt.setString( 2, City );
			stmt.setString( 3, State );
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Registers a Veteran profile with SoldierUp
	 * @param uid UID of ther person registering the Veteran Profile
	 * @param goal The goal that Veteran wants to accomplish through SoldierUp
	 * @return
	 */
	public ResultSet registerVeteran( long uid, String goal )
	{
		String insertSql = "INSERT INTO veterans( id, Goal ) VALUES( ?, ? )";
		ResultSet generatedKeys = null;
		
		try 
		{
			PreparedStatement insertStmt = getPreparedStatement( insertSql );
			insertStmt.setLong( 1, uid );
			insertStmt.setString( 2, goal );
			insertStmt.executeUpdate();
			generatedKeys = insertStmt.getGeneratedKeys();
			System.out.println( "Generated keys: " + (generatedKeys == null ) );
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
		
			PreparedStatement updateVeteranPreparedStatement = getPreparedStatement( updateVeteranSQL );
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
	 * Updates a business profile with the following parameters
	 * @param aBusinessId
	 * @param aBusinessProfileObjects
	 */
	public void updateProfileBusiness( long aBusinessId, Map<String,String> aBusinessProfileObjects)
	{
		
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
			PreparedStatement businessTransferStatement = getPreparedStatement( "UPDATE Business SET contact_id = ? WHERE bid = ?" );
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
		try 
		{
			verityZipInDatabase( aZip, aCity, aState );
			
			MySQL databaseConnection = MySQL.getInstance();
			String businessInsertQuery = "INSERT INTO BUSINESS ";
			businessInsertQuery += "(contact_id,name,short_summary,long_summary,work_number,address,ZIP)";
			businessInsertQuery += "VALUES(?,?,?,?,?,?,?)";
			PreparedStatement businessSQLInsert = databaseConnection.getPreparedStatement(businessInsertQuery);
			businessSQLInsert.setInt( 1, aContactID );
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
			
				PreparedStatement updateBusinessStatement = getPreparedStatement( updateBusinessSQL );
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
			PreparedStatement insertPhoto = getPreparedStatement( "INSERT INTO Photos (bid, title, src) VALUES(?,?,?)" );
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
		try 
		{
			PreparedStatement updateUser = null;
			String updateProfilePhotoSql;
			
			String targetPhoto = aPhotoType.equals( MySQL.TEMP_UPLOAD_IMAGE_COVER ) ? "cover_src" : "profile_src";
			
			if( aPhotoType.equals( "veteran" ) )
			{
				updateProfilePhotoSql = "UPDATE veterans SET " + targetPhoto + " = ? WHERE vid = ?";
			}
			else
			{
				updateProfilePhotoSql = "UPDATE business SET " + targetPhoto + " = ? WHERE bid = ?";
			}
			
			updateUser = getPreparedStatement( updateProfilePhotoSql );

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
	public long insertTag( String tag)
	{
		try 
		{
			String insertSql = "INSERT IGNORE INTO tags(tag) VALUES( ? )";
			PreparedStatement insertTagStmt = connect.prepareStatement( insertSql );
			insertTagStmt.setString( 1, tag );
			insertTagStmt.executeUpdate();
			ResultSet insertTagId = insertTagStmt.getGeneratedKeys();
			
			if( insertTagId.first() )
			{
				return insertTagId.getLong( 0 );
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
	 * Attaches a tag to a given business
	 * @param tag Tag to attach to business
	 * @param bid Business id of business to attach tag to
	 */
	public void attachTagToBusiness( String tag, long bid )
	{
		try
		{
			long insertResult = insertTag( tag );
			
			if( insertResult > 0)
			{
				String attachTagToBusinessSql = "INSERT IGNORE INTO business_tags VALUE(?, ?)";
				PreparedStatement attachTagStmt = connect.prepareStatement( attachTagToBusinessSql );
				attachTagStmt.setLong( 1, insertResult );
				attachTagStmt.setLong( 2, bid );
			}
		}
		catch( SQLException e)
		{
			log.error ("Errors occured while trying to attach a tag to a business", e);
		}
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
			PreparedStatement veteranSql = connect.prepareStatement( "SELECT * FROM veterans WHERE uid = ?" );
			veteranSql.setLong( 1, uid );
			
			ResultSet veteranQuery = veteranSql.executeQuery();
			if( veteranQuery.first() )
			{
				foundVeteran = new Veteran();
				foundVeteran.setVid( veteranQuery.getLong( "vid" ) );
				foundVeteran.setGoal( veteranQuery.getString( "goal" ) );
				foundVeteran.setUid( veteranQuery.getLong( "uid" ) );
				foundVeteran.setProfileSrc( veteranQuery.getString( "profile_src" ) );
			}
		}
		catch( SQLException e)
		{
			log.error ("Errors occured while trying to retrieve a veteran", e);
		}
		
		return foundVeteran;
	}
	
	/**
	 * Gets all the tags that exist in the database
	 * @return All the tags that exist in the database
	 */
	public JSONObject getAllTags()
	{
		JSONObject tagsObject = new JSONObject();
		JSONArray arrayOfTags = new JSONArray();
		
		try
		{
			PreparedStatement stmt = connect.prepareStatement( "SELECT * FROM tags" );
			ResultSet tags = stmt.executeQuery();
			while( tags.next() )
			{
				arrayOfTags.add( tags.getString( "tag" ) );
			}
			
			tagsObject.put( "tags", arrayOfTags );
		}
		catch(SQLException e)
		{
			
		}
		
		return tagsObject;
	}
}

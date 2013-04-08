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

package org.soldieringup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.soldieringup.database.MySQL;

/**
 * @author jjennings
 * This class is responsible for all transactions of the website
 */
public class Engine
{
	private static final Logger log = Logger.getLogger(Engine.class.getName());

	public Engine ()
	{

	}

	/**
	 * Gets all the businesses associated with a user account
	 * @param oid The ID of the user
	 * @return All the businesses associated with the given account
	 */
	public ArrayList<Business> getBusinessesFromOwner( long oid )
	{
		ArrayList<Business> foundBusinesses;
		MySQL databaseConnection = MySQL.getInstance();

		try
		{
			foundBusinesses = databaseConnection.getBusinessesFromOwner( oid );
		}
		catch (SQLException e)
		{
			foundBusinesses = new ArrayList<Business>();
			log.error( "Issues occured with quering businesses for user", e );
		}
		return foundBusinesses;
	}

	/**
	 * Gets the business information for a given business id.
	 * @param bid The business id for the business to query
	 * @return The Business associated with bid
	 */
	public Business getBusiness( long bid )
	{
		Business foundBusiness;
		MySQL databaseConnection = MySQL.getInstance();

		try
		{
			foundBusiness = databaseConnection.getBusiness( bid );
		}
		catch (SQLException e)
		{
			foundBusiness = null;
			log.error( "Business could not be queried", e );
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
		ZIP foundZip;
		MySQL databaseConnection = MySQL.getInstance();

		try
		{
			foundZip = databaseConnection.getZIP( aZip );
		}
		catch (SQLException e)
		{
			foundZip = null;
			log.error( "ZIP code could not be queried", e );
		}
		return foundZip;
	}

	/**
	 *  Logs a user into the system
	 *  @param aEmail E-mail address of the user
	 *  @param aPassword Password of the registered user
	 *  @result The result set of the login attempt
	 */
	public User validateUser( String aEmail, String aPassword )
	{
		User validatedUser;
		MySQL databaseConnection = MySQL.getInstance();

		try
		{
			validatedUser = databaseConnection.validateUser( aEmail, aPassword );
		}
		catch( SQLException e )
		{
			validatedUser = null;
			log.error( "User could not be validated", e );
		}

		return validatedUser;
	}

	/**
	 * Get the user from an associated id
	 * @param uid User ID to query
	 * @return The User if the id exists in the database, null otherwise
	 */
	public User getUserFromId( long uid )
	{
		User foundUser;
		MySQL databaseConnection = MySQL.getInstance();

		try
		{
			foundUser = databaseConnection.getUserFromId( uid );
		}
		catch (SQLException e)
		{
			foundUser = null;
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
			return MySQL.getInstance().checkIfEmailIsInUse(aEmail);
		}
		catch (SQLException e)
		{
			log.error ("Errors occured while querying email", e);
			return true;
		}
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
		ResultSet registeredUser;
		MySQL databaseConnection = MySQL.getInstance();

		try
		{
			registeredUser = databaseConnection.registerUser(
					aFirstName,
					aLastName,
					aEmail,
					aAddress,
					aPrimaryNumber,
					aSecondaryNumber,
					aPassword,
					aZip,
					aCity,
					aState,
					aErrors
					);
		}
		catch (SQLException e) {
			registeredUser = null;
			log.error ("Errors occured while registering user", e);
		}

		return registeredUser;
	}

	/**
	 * Registers a Veteran profile with SoldierUp
	 * @param uid UID of ther person registering the Veteran Profile
	 * @param goal The goal that Veteran wants to accomplish through SoldierUp
	 * @return
	 */
	public ResultSet registerVeteran( long uid, String goal )
	{
		ResultSet registeredVeteran;
		MySQL databaseConnection = MySQL.getInstance();

		try
		{
			registeredVeteran = databaseConnection.registerVeteran( uid, goal );
		}
		catch (SQLException e)
		{
			registeredVeteran = null;
			log.error ("Errors occured while registering veteran", e);
		}
		return registeredVeteran;
	}

	/**
	 * Updates a Veteran's profile
	 * @param vid VID of the veteran to update
	 * @param goal Goal to update the veteran profile with
	 */
	public void updateProfileVeteran( long uid, String goal )
	{
		try
		{
			MySQL.getInstance().updateProfileVeteran(uid, goal);
		}
		catch (SQLException e)
		{
			log.error ("Errors occured while querying email", e);
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
			MySQL.getInstance().transferBusinessContact(aUserId, aBusinessId);
		}
		catch (SQLException e)
		{
			log.error ("Errors occured while transferring business", e);
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
		ResultSet registeredBusiness;
		MySQL databaseConnection = MySQL.getInstance();

		try
		{
			registeredBusiness = databaseConnection.registerBusiness(
					aContactID,
					aBusinessName,
					aShortSummary,
					aLongSummary,
					aWorkNumber,
					aAddress,
					aCity,
					aState,
					aZip);
		}
		catch (SQLException e)
		{
			registeredBusiness = null;
			log.error ("Errors occured while querying email", e);
		}
		return registeredBusiness;
	}

	/**
	 * Updates the veteran with the given objects
	 * @param aVid User ID of the veteran
	 * @param aParameters The parameters to update the veteran with
	 */
	public void updateVeteran( long aUid, Map<String,Object> aParameters )
	{
		try
		{
			MySQL.getInstance().updateVeteran(aUid, aParameters);
		}
		catch (SQLException e)
		{
			log.error ("Issues updating veteran", e);
			e.printStackTrace();
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
			MySQL.getInstance().updateBusiness( aBid, aParameters );
		}
		catch (SQLException e)
		{
			log.error ("Errors occured while updating business", e);
			e.printStackTrace();
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
			MySQL.getInstance().updateUser(aUid, aParameters);
		} catch (SQLException e)
		{
			log.error ("Errors occured while updating user", e);
		}
	}

	/**
	 * Retrieves a photo from a pid
	 * @param pid Photo id of the photo to retrieve
	 * @return The photo associated with the pid
	 */
	public Photo getPhotoFromId( long pid )
	{
		Photo foundPhoto;
		MySQL databaseConnection = MySQL.getInstance();

		try
		{
			foundPhoto = databaseConnection.getPhotoFromId(pid);
		}
		catch (SQLException e)
		{
			foundPhoto = null;
			log.error ("Errors occured while querying photo", e);
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
		ResultSet insertedPhoto;
		MySQL databaseConnection = MySQL.getInstance();

		try
		{
			insertedPhoto = databaseConnection.insertPhoto(bid, title, src);
		}
		catch (SQLException e)
		{
			insertedPhoto = null;
			log.error ("Errors occured while querying email", e);
		}

		return insertedPhoto;
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
			MySQL.getInstance().setAccountPhoto(aOid, aPhotoType, aAccountType, aSrc);
		}
		catch (SQLException e)
		{
			log.error ("Errors occured while setting an account photo", e);

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
		long tagId;
		MySQL databaseConnection = MySQL.getInstance();

		try
		{
			tagId = databaseConnection.attachTagToAccount(aTag, aRequest, aHoursRequested);
		}
		catch (SQLException e)
		{
			tagId = -1;
			log.error ("Error occured while trying to attach tag to account", e);
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
			MySQL.getInstance().detachTagFromAccount(aTagId, aRequest);
		}
		catch (SQLException e)
		{
			log.error ("Error occurred while attempting to detach tag from an account", e);
		}
	}

	/**
	 * Get all the business that have the given tags
	 * @param aTags Tags to search for businesses for
	 * @return The business that have the given tags
	 */
	public ArrayList<Business> getBusinessesFromTags(String aTags[])
	{
		ArrayList<Business> businessesFromTags;
		MySQL databaseConnection = MySQL.getInstance();

		try
		{
			businessesFromTags = databaseConnection.getBusinessesFromTags(aTags);
		}
		catch (SQLException e)
		{
			businessesFromTags = new ArrayList<Business>();
			log.error ("Error occurred while searching for businesses from given tags", e);
		}

		return businessesFromTags;
	}

	/**
	 * Gets the tags from the given question
	 * @param aQid Question to get the tags for
	 * @return The tags for the given question
	 */
	public ArrayList<Tag> getTagsFromQuestion( long aQid )
	{
		ArrayList<Tag> tagsFromQuestion;
		MySQL databaseConnection = MySQL.getInstance();

		try
		{
			tagsFromQuestion = databaseConnection.getTagsFromQuestion(aQid);
		}
		catch (SQLException e)
		{
			tagsFromQuestion = new ArrayList<Tag>();
			log.error ("Error occurred while retrievent tags from a given question", e);
		}

		return tagsFromQuestion;
	}

	/**
	 * Finds all the tags associated to a business
	 * @param aOid ID of the business to query for
	 * @return The tags associated to a business
	 */
	public ArrayList<Tag> getTagsFromAccount( long aOid )
	{
		ArrayList<Tag> tagsFromAccount;
		MySQL databaseConnection = MySQL.getInstance();

		try
		{
			tagsFromAccount = databaseConnection.getTagsFromAccount(aOid);
		}
		catch (SQLException e)
		{
			tagsFromAccount = new ArrayList<Tag>();
			log.error ("Error occured while retrieving tags from an account id", e);
		}

		return tagsFromAccount;
	}

	/**
	 * Gets a veteran from a user id
	 * @param uid The user id of the user
	 * @return The Veteran object if found, false otherwise
	 */
	public Veteran getVeteran( long uid )
	{
		Veteran foundVeteran;
		MySQL databaseConnection = MySQL.getInstance();

		try
		{
			foundVeteran = databaseConnection.getVeteran(uid);
		}
		catch (SQLException e)
		{
			foundVeteran = null;
			log.error ("", e);
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
		JSONArray arrayOfTags;
		MySQL databaseConnection = MySQL.getInstance();

		try
		{
			arrayOfTags = databaseConnection.getSimiliarTags(aTagToSearch);
		}
		catch (SQLException e)
		{
			arrayOfTags = new JSONArray();
			log.error ("Error occured while querying for tags from a search term", e);

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
		JSONArray similiarTags;
		MySQL databaseConnection = MySQL.getInstance();

		try
		{
			similiarTags = databaseConnection.getSimiliarTagsNotInAccount(aTagToSearch, aRequest);
		}
		catch (SQLException e)
		{
			similiarTags = new JSONArray();
			log.error ("Error occured while searching for similiar tags not found in an account", e);
		}

		return similiarTags;
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
		ResultSet insertedVeteranQuestion;
		MySQL databaseConnection = MySQL.getInstance();

		try
		{
			insertedVeteranQuestion = databaseConnection.insertVeteranQuestion(aQuestionTitle, aAvailability, aQuestionDetailedDescription, aVid);
		}
		catch (SQLException e)
		{
			insertedVeteranQuestion = null;
			log.error ("Error occured wile inserting a question", e);
		}

		return insertedVeteranQuestion;
	}

	/**
	 * Removes tags from the question associated to the given id.
	 * @param aQid ID of question to remove tags from
	 */
	public void removeTagsFromQuestion( long aQid )
	{
		try
		{
			MySQL.getInstance().removeTagsFromQuestion(aQid);
		}
		catch (SQLException e)
		{
			log.error ("Error occured while removing tags from a question", e);
		}
	}

	/**
	 * Attaches the given tags to the question with the given id
	 * @param aTag Tag to insert
	 * @param aQid Question to attach tag to
	 * @return The id of the tag
	 */
	public long attachTagsToQuestion( String aTag, long aQid )
	{
		long aTagId = -1;
		MySQL databaseConnection = MySQL.getInstance();

		try
		{
			aTagId = databaseConnection.attachTagsToQuestion(aTag, aQid);
		}
		catch (SQLException e)
		{
			log.error ("Error occured while attaching a tag to a question", e);
		}
		return aTagId;
	}

	/**
	 * Gets a question from the given question id
	 * @param aQid The question Id
	 * @return The question from the ID if the id is found, false otherwise
	 */
	public Question getQuestionFromId( long aQid )
	{
		Question foundQuestion;
		MySQL databaseConnection = MySQL.getInstance();

		try
		{
			foundQuestion = databaseConnection.getQuestionFromId(aQid);
		}
		catch (SQLException e)
		{
			foundQuestion = null;
			log.error ("Error occured while querying a question from a given id", e);
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
		ArrayList<Question> getQuestionsFromVeteran;
		MySQL databaseConnection = MySQL.getInstance();

		try
		{
			getQuestionsFromVeteran = databaseConnection.getQuestionsFromVeteran(aVid);
		}
		catch (SQLException e)
		{
			getQuestionsFromVeteran = new ArrayList<Question>();
			log.error ("Errors occured while getting questions from a veteran", e);
		}
		return getQuestionsFromVeteran;
	}

	/**
	 * Gets the meeting request for a given question and business
	 * @param aQid ID of the question the meeting request is for
	 * @param aBid ID of the business who sent the request
	 * @return The Meeting Request if found, NULL otherwise
	 */
	public MeetingRequest getMeetingRequestFromQIDAndBID( long aQid, long aBid )
	{
		MeetingRequest foundMeetingRequest;
		MySQL databaseConnection = MySQL.getInstance();

		try
		{
			foundMeetingRequest = databaseConnection.getMeetingRequestFromQIDAndBID(aQid, aBid);
		}
		catch (SQLException e)
		{
			foundMeetingRequest = null;
			log.error ( "Error occured while querying for a meeting request", e);
		}

		return foundMeetingRequest;
	}

	/**
	 * Gets the meeting request for a given question id
	 * @param aQid The question ID to retrieve the meeting requests for
	 */
	public ArrayList<MeetingRequest> getMeetingRequestsForQuestion( long aQid )
	{
		ArrayList<MeetingRequest> meetingRequests;
		MySQL databaseConnection = MySQL.getInstance();

		try
		{
			meetingRequests = databaseConnection.getMeetingRequestsForQuestion(aQid);
		}
		catch (SQLException e)
		{
			meetingRequests = null;
			log.error ("Error occured while searching for meeting requests for a given question", e);
		}

		return meetingRequests;
	}

	/**
	 * Inserts a meeting request
	 * @param aMeetingParameters
	 * @throws SQLException
	 */
	public void insertMeetingRequests( Map<String,Object> aMeetingParameters )
	{
		try
		{
			MySQL.getInstance().insertMeetingRequests(aMeetingParameters);
		}
		catch (SQLException e)
		{
			log.error( "User could not be validated", e );
		}
	}

}

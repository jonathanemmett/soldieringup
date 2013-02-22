package org.soldieringup.database;

import java.sql.ResultSet;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.routines.EmailValidator;
import org.soldieringup.Utilities;

/**
 * Class that holds the methods for registering a general user account into the Database. The functions take in
 * an input from a request, checks for any errors, and adds the account to SoldierUp if no errors are found.
 * Note that these methods do not register any businesses or veterans, and that they are exclusively for
 * registering a general User account. 
 * @author Jake
 *
 */

public class UserRegistration
{
	/**
	 * Makes sure that the registration inputs for the user are not null. If any errors are found, 
	 * they will be populated inside the registrationErrors map.
	 * @param aRequest Request to check the parameters for
	 * @param aRegistrationErrors (in/out) Holds any errors that result from invalid inputs. 
	 */
	private static void checkForNullUserInputs( HttpServletRequest aRequest, Map<String,String> aRegistrationErrors )
	{
		String[] requiredKeys = { "first_name", "last_name", "contact_address",
				  "contact_state", "contact_city", "contact_email", "contact_ZIP", "contact_primary_number" };

		// Check to make sure all the required keys are present in the request
		for( String key : requiredKeys )
		{
			Utilities.checkParameterIsNull( key, aRequest, aRegistrationErrors );
		}
	}
	
	/**
	 * Registers the user with the database.
	 * @param request Request containing the input parameters for account
	 * @param registrationErrors (in/out) Holds any errors that result from the user registration
	 * @return The ResultSet containing the generated ID of the user if successfully inserted into the database.
	 * 		   If the insertion fails, this function returns null.
	 */
	public static ResultSet registerUser( HttpServletRequest request, Map<String,String> registrationErrors )
	{
		ResultSet generatedId = null;
		
		MySQL databaseConnection = MySQL.getInstance();
		String primaryContactNumber = "";
		String secondaryContactNumber = "";
		
		// Make sure all of the required inputs are not null 
		// before processing any of the inputs.
		checkForNullUserInputs( request, registrationErrors );
		
		// Check to make sure that the passwords are equal to each other
		if( registrationErrors.isEmpty() )
		{	
			try
			{
				// Convert the phone number to a numeric string, and make sure that string is indeed numeric
				primaryContactNumber = request.getParameter( "contact_primary_number" ).substring(1,4);
				primaryContactNumber += request.getParameter( "contact_primary_number" ).substring(6,9);
				primaryContactNumber += request.getParameter( "contact_primary_number" ).substring(10,14);
				
				if( !Utilities.stringIsNumeric( primaryContactNumber ) )
				{
					registrationErrors.put( "contact_primary_number", "Invalid Number" );
				}
			}
			catch( StringIndexOutOfBoundsException e )
			{
				//String is most likely not of the proper format (###) ###-####
				registrationErrors.put( "contact_primary_number", "Invalid Number" );
			}
			
			// Validate that the email is of correct form. This doesn't check to make sure
			// this is a working email.
			if( !EmailValidator.getInstance().isValid( request.getParameter( "contact_email" ) ) )
			{
				registrationErrors.put( "contact_email", "Invalid Email" );
			}
			
			if( !Utilities.stringIsNumeric( request.getParameter( "contact_ZIP" ) ) )
			{
				registrationErrors.put( "contact_ZIP", "Invalid Number" );
			}
		}
		if( registrationErrors.isEmpty() )
		{
			// Generate a password for the user.
			String generatedPassword = Utilities.generatePassword( 3, 8, 1 );
			
			// The secondary number is not a requirement for a new user
			if( request.getParameter( "contact_secondary_number" ) != null )
			{
				try
				{
					// Convert the phone number to a numeric string, and make sure that string is indeed numeric
					secondaryContactNumber = request.getParameter( "contact_secondary_number" ).substring(1,4);
					secondaryContactNumber += request.getParameter( "contact_secondary_number" ).substring(6,9);
					secondaryContactNumber += request.getParameter( "contact_secondary_number" ).substring(10,14);
				}
				catch( StringIndexOutOfBoundsException e )
				{
					// If the number is not of the correct form when coming into 
					// the servlet ( (###) ###-#### ), set it to empty.
					secondaryContactNumber = "";
				}
			}
			
			generatedId = databaseConnection.registerUser(
						  	request.getParameter( "first_name" ),
						  	request.getParameter( "last_name" ),
						  	request.getParameter( "contact_email" ),
						  	request.getParameter( "contact_address" ),
						  	primaryContactNumber,
						  	secondaryContactNumber,
						  	generatedPassword,
						  	request.getParameter( "contact_ZIP" ),
						  	request.getParameter( "contact_city" ),
						  	request.getParameter( "contact_state" ),
						  	registrationErrors );
			
			System.out.println( "Thank you for creating an account! Your password is " + generatedPassword );
		}

		return generatedId;
	}
}

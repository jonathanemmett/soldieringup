package org.soldieringup.servlets;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.security.NoSuchAlgorithmException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.soldieringup.Utilities;
import org.soldieringup.database.MySQL;

/**
 * Business Registration servlet that tries to register a business given from
 * a form of inputs.
 */
@WebServlet("/BusinessRegistration")
public class BusinessRegistration extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger( MySQL.class.getName() );
   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BusinessRegistration() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException 
	{
		MySQL databaseConnection = MySQL.getInstance();
		Map<String,String> registrationErrors = new HashMap<String,String>();
		
		// All of the following keys must be filled in.
		validateUserForms( request, registrationErrors );
		String[] requiredBusinessKeys = { "business_name", "business_short_summary", "business_long_summary",	
								  		  "business_address", "business_city", "business_state", "business_ZIP" };

		// Check to make sure all the required keys are present in the request
		for( String key : requiredBusinessKeys )
		{
			Utilities.checkParameterIsNull( key, request, registrationErrors );
		}
		
		// Make sure that the zip numbers are numeric
		if( !Utilities.stringIsNumeric( request.getParameter( "business_ZIP" ) ) )
		{
			registrationErrors.put( "business_ZIP", "Invalid ZIP" );
		}
		
		if( registrationErrors.isEmpty() )
		{
			ResultSet generatedId = registerUser( request, registrationErrors );
					
			// If the user was successfully inserted, go on ahead and register the business
			if( generatedId != null )
			{
				try 
				{	
					if( generatedId.first() )
					{	
						String primaryContactNumber;
						primaryContactNumber = request.getParameter( "contact_primary_number" ).substring(1,4);
						primaryContactNumber += request.getParameter( "contact_primary_number" ).substring(6,9);
						primaryContactNumber += request.getParameter( "contact_primary_number" ).substring(10,14);
						
						ResultSet registeredBusiness = 
								databaseConnection.registerBusiness( Integer.parseInt( generatedId.getString(1) ), 
										  							 request.getParameter( "business_name" ), 
										  							 request.getParameter( "business_short_summary" ), 
										  							 request.getParameter( "business_long_summary" ), 
										  							 primaryContactNumber, 
										  							 request.getParameter( "business_address" ),
										  							 request.getParameter( "business_city" ),
										  							 request.getParameter( "business_state" ),
										  							 request.getParameter( "business_ZIP" ) );
						
						if( registeredBusiness.first() )
						{
							request.setAttribute("bid", registeredBusiness.getLong( 0 ) );
							request.getRequestDispatcher("/editBusiness.jsp").forward( request, response );
							return;
						}
					}
				} 
				catch (SQLException e)  
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		request.setAttribute( "registration_errors", registrationErrors );
		request.getRequestDispatcher("/NewBusiness.jsp").forward( request, response );
	}
	
	public void validateUserForms( HttpServletRequest request, Map<String,String> registrationErrors )
	{
		String[] requiredKeys = { "first_name", "last_name", "password", "redo_password", "contact_address",
				  "contact_state", "contact_city", "contact_email", "contact_ZIP", "contact_primary_number" };

		// Check to make sure all the required keys are present in the request
		for( String key : requiredKeys )
		{
			Utilities.checkParameterIsNull( key, request, registrationErrors );
		}
	}
	
	public ResultSet registerUser( HttpServletRequest request, Map<String,String> registrationErrors )
	{
		ResultSet generatedId = null;
		
		MySQL databaseConnection = MySQL.getInstance();
		String primaryContactNumber = "";
		String secondaryContactNumber = "";
		
		// Check to make sure that the passwords are equal to each other
		if( registrationErrors.isEmpty() )
		{	
			// If the passwords do not match, send an error.
			if( !request.getParameter("password").equals( request.getParameter("redo_password") ) )
			{
				registrationErrors.put("redo_password", "Password does not match");
			}
			
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
					// If the number is not of the correct form when coming into the servlet (###) ###-####, set it empty.
					secondaryContactNumber = "";
				}
			}
			
			generatedId = databaseConnection.registerUser( request.getParameter( "first_name" ),
						  request.getParameter( "last_name" ),
						  request.getParameter( "contact_email" ),
						  request.getParameter( "contact_address" ),
						  primaryContactNumber,
						  secondaryContactNumber,
						  request.getParameter( "password" ),
						  request.getParameter( "contact_ZIP" ),
						  request.getParameter( "contact_city" ),
						  request.getParameter( "contact_state" ),
						  registrationErrors );
		}
		
		return generatedId;
	}
}

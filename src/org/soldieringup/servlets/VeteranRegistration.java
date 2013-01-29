package org.soldieringup.servlets;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.routines.EmailValidator;
import org.soldieringup.Utilities;
import org.soldieringup.database.MySQL;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * Servlet implementation class VeteranRegistration
 */
@WebServlet("/VeteranRegistration")
public class VeteranRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VeteranRegistration()
    {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.getWriter().print( "Entering" );
		String goal = request.getParameter( "goal" );
		Map<String,String> registrationErrors = new HashMap<String,String>();

		validateUserForms( request, registrationErrors );
		
		if( registrationErrors.isEmpty() )
		{
			ResultSet getUserInfo = registerUser(request, registrationErrors);
			
			try
			{
				response.getWriter().print( "Good" );
				if( getUserInfo != null && getUserInfo.first() )
				{
					request.getSession().setAttribute( "id" , getUserInfo.getLong( 1 ) );

					MySQL databaseConnection = MySQL.getInstance();
					ResultSet vetId = databaseConnection.registerVeteran( getUserInfo.getLong( 1 ), goal );
					response.getWriter().print( "Good again " + ( vetId == null ) );
					if( vetId != null && vetId.first() )
					{
						request.getSession().setAttribute( "vid" , vetId.getLong( 1 ) );
						request.getRequestDispatcher( "/editVeteranProfile.jsp" ).forward( request, response );
					}
					else
					{
						response.getWriter().print( "not Good dd" );
					}
					
				}
			}
			catch(SQLException e)
			{
				response.getWriter().print( "Error" );
			}
		}
		else
		{
			response.getWriter().print( Arrays.toString( registrationErrors.keySet().toArray() ) );
			//request.getRequestDispatcher( "/NewAccount.jsp" ).forward( request, response );
		}
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

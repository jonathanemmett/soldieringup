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
import org.soldieringup.database.UserRegistration;

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
			// A business registration requires a valid user registration  
			ResultSet generatedId = UserRegistration.registerUser( request, registrationErrors );
					
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
							request.getRequestDispatcher( "/accountCreationSuccess.jsp" ).forward( request, response );
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
}

package org.soldieringup.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.soldieringup.Business;
import org.soldieringup.MongoEngine;
import org.soldieringup.User;
import org.soldieringup.Utilities;
import org.soldieringup.database.UserRegistration;

/**
 * Business Registration servlet that tries to register a business given from
 * a form of inputs.
 */
@WebServlet("/BusinessRegistration")
public class BusinessRegistration extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger( MongoEngine.class.getName() );

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BusinessRegistration()
	{
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		MongoEngine engine = new MongoEngine();
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
			User registeredUser = UserRegistration.registerUser( request, registrationErrors );

			// If the user was successfully inserted, go on ahead and register the business
			if( registeredUser != null )
			{
				String primaryContactNumber;
				primaryContactNumber = request.getParameter( "contact_primary_number" ).substring(1,4);
				primaryContactNumber += request.getParameter( "contact_primary_number" ).substring(6,9);
				primaryContactNumber += request.getParameter( "contact_primary_number" ).substring(10,14);

				Business newBusiness = new Business();
				newBusiness.setName( request.getParameter( "business_name" ) );
				newBusiness.setShortSummary( request.getParameter( "business_short_summary" ) );
				newBusiness.setLongSummary( request.getParameter( "business_long_summary" ) );
				newBusiness.setPrimary_number( primaryContactNumber );
				newBusiness.setAddress( request.getParameter( "business_address" ) );
				newBusiness.setZip( request.getParameter( "business_ZIP" ) );
				newBusiness.setOwner( registeredUser );
				engine.insertAccount( newBusiness );

				if( newBusiness.getObject_id() != null )
				{
					request.getRequestDispatcher( "/accountCreationSuccess.jsp" ).forward( request, response );
					return;
				}
			}
		}

		request.setAttribute( "registration_errors", registrationErrors );
		request.getRequestDispatcher("/NewBusiness.jsp").forward( request, response );
	}
}

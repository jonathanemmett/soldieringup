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
import org.soldieringup.database.UserRegistration;

import edu.emory.mathcs.backport.java.util.Arrays;

/** 
 * This servlet attempts to register a Veteran with SoldierUp with given inputs
 */
@WebServlet("/VeteranRegistration")
public class VeteranRegistration extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * Constructor for Veteran registration
     */
    public VeteranRegistration()
    {
        super();
    }

	/**
	 * Currently does nothing for this Servlet
	 * @param request The request to be sent into the post request
	 * @param response The response associated with the servlet request
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
	}

	/**
	 * Registers a veteran into the database
	 * @param request The request to be sent into the post request
	 * @param response The response associated with the servlet request
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Veteran profiles require a goal to be present. This helps businesses to determine who they can best
		// serve in our network.
		String veteranGoal = request.getParameter( "goal" );
		Map<String,String> registrationErrors = new HashMap<String,String>();

		// We need to register a user account to be associated with this Veteran account.		
		ResultSet getUserInfo = UserRegistration.registerUser(request, registrationErrors);
		
		if( registrationErrors.isEmpty() )
		{	
			try
			{
				if( getUserInfo != null && getUserInfo.first() )
				{
					MySQL databaseConnection = MySQL.getInstance();
					ResultSet vetId = databaseConnection.registerVeteran( getUserInfo.getLong( 1 ), veteranGoal );

					if( vetId != null && vetId.first() )
					{
						request.getRequestDispatcher( "/accountCreationSuccess.jsp" ).forward( request, response );
						return;
					}
				}
			}
			catch(SQLException e)
			{
				response.getWriter().print( "Error editing database. Sorry for the inconvenience." );
			}
		}
		else
		{
			response.getWriter().print( Arrays.toString( registrationErrors.keySet().toArray() ) );
		}
		
		request.setAttribute( "registration_errors", registrationErrors );
		request.getRequestDispatcher("/NewVeteran.jsp").forward( request, response );
	}
}

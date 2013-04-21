package org.soldieringup.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.soldieringup.User;
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
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
	}

	/**
	 * Registers a veteran into the database
	 * @param request The request to be sent into the post request
	 * @param response The response associated with the servlet request
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Veteran profiles require a goal to be present. This helps businesses to determine who they can best
		// serve in our network.
		String veteranGoal = request.getParameter( "goal" );
		Map<String,String> registrationErrors = new HashMap<String,String>();

		// We need to register a user account to be associated with this Veteran account.
		User getUserInfo = UserRegistration.registerUser(request, registrationErrors);

		if( registrationErrors.isEmpty() )
		{
			request.getRequestDispatcher( "/accountCreationSuccess.jsp" ).forward( request, response );
		}
		else
		{
			response.getWriter().print( Arrays.toString( registrationErrors.keySet().toArray() ) );
		}

		request.setAttribute( "registration_errors", registrationErrors );
		request.getRequestDispatcher("/NewVeteran.jsp").forward( request, response );
	}
}

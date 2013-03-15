package org.soldieringup.servlets;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.routines.EmailValidator;
import org.soldieringup.Business;
import org.soldieringup.User;
import org.soldieringup.Veteran;
import org.soldieringup.database.MySQL;

/**
 * Servlet implementation class Login
 *
 * This servlet validates user login and redirects to the proper places on POST requests, and
 * redirects the user to the login page if the servlet is processed with a GET request.
 * @author Jake LaCombe
 */
@WebServlet("/Login")
public class Login extends HttpServlet
{
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * This doGet method removes any login errors, and redirects the user to the login page.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// We need to erase any login errors that exists if we are retrieving the login page
		request.getSession().removeAttribute( "login_errors" );
		request.getRequestDispatcher("/login.jsp").forward( request, response );
	}

	/**
	 * Takes in a user login, and redirects depending on the login result.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Map<String,String> loginErrors = new HashMap<String,String>();
		boolean loginSuccessful = false;

		try
		{
			if( request.getParameter( "login" ) != null )
			{
				String email = request.getParameter( "email" );
				String password = request.getParameter( "password" );

				if( email == null || !EmailValidator.getInstance().isValid( email ) )
				{
					loginErrors.put( "missing_email", "Invalid" );
				}

				if( password == null )
				{
					loginErrors.put( "missing_password", "Required" );
				}

				// If the map of login results is empty, we have not found any errors, so we can continue processing
				if( loginErrors.isEmpty() )
				{
					MySQL databaseConnection = MySQL.getInstance();
					User loggedInUser = databaseConnection.validateUser( email, password );
					if( loggedInUser != null )
					{
						loginSuccessful = true;
						handleAccountRedirect( request, response, loggedInUser );
					}
				}
			}
		}
		catch( SQLException e ){ /* No-op*/ }

		if( !loginSuccessful )
		{
			loginErrors.put( "final", "invalid" );
			request.getSession().setAttribute( "login_errors", loginErrors );
			request.getRequestDispatcher( getRedirectUrl( request.getSession().getAttribute( "login_page" ) ) ).forward( request, response );
		}
	}

	/**
	 * Gets the redirect URL for the user to use.
	 * @param aSessionRedirectObject Redirect URL that the session may or may not contain
	 * @return The URL path for the given string
	 */
	public String getRedirectUrl( Object aSessionRedirectObject )
	{
		String redirectUrl;

		System.out.println( "Session Null: " + aSessionRedirectObject == null );
		if( aSessionRedirectObject != null )
		{
			redirectUrl = aSessionRedirectObject.toString();

			while( redirectUrl.contains( "/" ) )
			{
				redirectUrl = redirectUrl.substring(  redirectUrl.indexOf( "/" ) + 1 );
			}

			redirectUrl = "/" + redirectUrl;
		}
		else
		{
			redirectUrl = "/login.jsp";
		}

		return redirectUrl;
	}

	/**
	 * Redirects a logged in user to the correct account profile page
	 * @param aLoggedInUser Logged In user to find the account for
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	public void handleAccountRedirect( HttpServletRequest request, HttpServletResponse response, User aLoggedInUser ) throws SQLException, ServletException, IOException
	{
		MySQL databaseConnection = MySQL.getInstance();
		HttpSession currentSession = request.getSession();
		currentSession.setAttribute( "uid", aLoggedInUser.getId() );
		Map<Integer, Business> ownedBusinesses;

		ownedBusinesses = databaseConnection.getBusinessesFromOwner( aLoggedInUser.getId() );

		if( ownedBusinesses.size() > 0 )
		{
			List<Integer> businessIDs = new ArrayList<Integer>( ownedBusinesses.keySet() );
			currentSession.setAttribute( "aid", businessIDs.get( 0 ) );
			request.getRequestDispatcher( "editBusiness.jsp" ).forward( request, response );
		}

		Veteran foundVeteran = databaseConnection.getVeteran( aLoggedInUser.getId() );
		if( foundVeteran != null )
		{
			currentSession.setAttribute( "aid", foundVeteran.getAid() );
			request.getRequestDispatcher( "editVeteranProfile.jsp" ).forward( request, response );
		}
	}
}

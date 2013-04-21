package org.soldieringup.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;
import org.soldieringup.MongoEngine;
import org.soldieringup.User;
import org.soldieringup.Utilities;
import org.soldieringup.utils.PasswordValidator;

/**
 * Servlet implementation class PasswordUpdate
 */
@WebServlet("/UpdatePassword")
public class UpdatePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdatePassword() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PrintWriter out = response.getWriter();

		String password = request.getParameter( "password" );
		String confirmPassword = request.getParameter( "confirm_password" );
		if( password != null && confirmPassword != null && request.getSession().getAttribute( "uid" ) != null )
		{
			ObjectId uid = (ObjectId) request.getSession().getAttribute( "uid" );
			MongoEngine engine = new MongoEngine();
			boolean passwordIsValid = password.equals( confirmPassword );

			if( !passwordIsValid )
			{
				out.println( "Passwords are not the same" );
				return;
			}

			PasswordValidator validator = new PasswordValidator();

			validator.setMixedCaseAlphabetRequired( true );
			validator.setNumberOfRequiredAlphabeticalCharacters( 8 );
			validator.setNumberOfRequiredDigits( 3 );
			if( !validator.validatePassword( password ) )
			{
				ArrayList<String> validationErrors = validator.getValidationErrors();
				Iterator<String> iterator = validationErrors.iterator();

				while( iterator.hasNext() )
				{
					out.println( iterator.next() );
				}
			}
			else
			{
				User currentUser = engine.findUsers( "_id", uid ).get( 0 );
				currentUser.setPassword( Utilities.sha1Output( currentUser.getSalt() + password ) );
				engine.updateUser( currentUser );
				out.println( "Password Successfully Updated" );
			}
		}
	}

}

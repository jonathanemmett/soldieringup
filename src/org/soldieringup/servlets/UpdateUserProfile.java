package org.soldieringup.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;
import org.soldieringup.User;
import org.soldieringup.service.MongoEngine;

/**
 * Servlet implementation class UpdateUserProfile
 */
@WebServlet("/UpdateUserProfile")
public class UpdateUserProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateUserProfile() {
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
		if( request.getSession().getAttribute( "uid" ) != null )
		{
			MongoEngine engine = new MongoEngine();
			ObjectId uid = (ObjectId) request.getSession().getAttribute( "uid" );
			User userToUpdate = engine.findUsers( "_id", uid ).get( 0 );

			if( request.getParameter( "first_name" ) != null )
			{
				userToUpdate.setFirstName( request.getParameter( "first_name") );
			}

			if( request.getParameter( "last_name" ) != null )
			{
				userToUpdate.setLastName( request.getParameter( "last_name" ) );
			}

			if( request.getParameter( "email" ) != null && !engine.emailExists( request.getParameter( "email" ) ) )
			{
				userToUpdate.setEmail( request.getParameter( "email" ) );
			}

			if( request.getParameter( "primary_number" ) != null )
			{
				userToUpdate.setPrimary_number( request.getParameter( "primary_number" ) );
			}

			if( request.getParameter( "secondary_number" ) != null )
			{
				userToUpdate.setSecondary_number( request.getParameter( "secondary_number" ) );
			}

			if( request.getParameter( "ZIP" ) != null && engine.findZip( request.getParameter( "ZIP" ) ) != null )
			{
				userToUpdate.setZip( request.getParameter( "ZIP" ) );
			}

			engine.updateUser( userToUpdate );

			if( request.getSession().getAttribute( "editing_account_type" ).equals( "business" ) )
			{
				response.sendRedirect( "UpdateBusiness" );
			}
			else
			{
				response.sendRedirect( "UpdateVeteran" );
			}
		}
	}

}

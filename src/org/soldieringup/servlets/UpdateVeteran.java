package org.soldieringup.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.types.ObjectId;
import org.soldieringup.User;
import org.soldieringup.service.MongoEngine;

/**
 * Servlet implementation class UpdateVeteran
 */
@WebServlet("/UpdateVeteran")
public class UpdateVeteran extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateVeteran()
	{
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession currentSession = request.getSession();
		if( currentSession.getAttribute( "editing_account_type" ) != null &&
				currentSession.getAttribute( "editing_account_type" ).equals( "veteran" ) )
		{
			MongoEngine engine = new MongoEngine();
			User veteran = engine.findUsers( "_id", currentSession.getAttribute( "uid" ) ).get(0);

			request.setAttribute( "account_to_edit", veteran );
			request.setAttribute( "account_zip", engine.findZip( veteran.getZip() ) );
			request.getRequestDispatcher( "/editVeteranProfile.jsp" ).forward( request, response );
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String goal = request.getParameter( "goal" );
		Object accountType = request.getSession().getAttribute( "editing_account_type" );

		if( request.getSession().getAttribute( "uid" ) != null && accountType != null &&
				accountType.equals( "veteran" ) && goal != null )
		{
			MongoEngine engine = new MongoEngine();
			ObjectId uid = (ObjectId) request.getSession().getAttribute( "uid" );
			User userToUpdate = engine.findUsers( "_id", uid ).get( 0 );
			userToUpdate.getVeteran().setGoal( goal );
			engine.updateUser( userToUpdate );
			response.sendRedirect( "/UpdateVeteran" );
		}
	}
}

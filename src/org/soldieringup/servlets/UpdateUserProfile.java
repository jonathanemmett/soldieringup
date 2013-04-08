package org.soldieringup.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.soldieringup.Engine;
import org.soldieringup.Utilities;

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
		if( request.getSession().getAttribute( "id" ) != null )
		{
			Engine engine = new Engine();
			long bid = Long.valueOf( request.getSession().getAttribute( "id" ).toString() );
			Set<String> keys = request.getParameterMap().keySet();
			Map<String,Object> updateParameters = new HashMap<String,Object>();
			Iterator<String> keysIterator = keys.iterator();
			String[] userProfileColumns = { "id", "first_name", "last_name", "email", "address",
					"primary_number", "secondary_number", "password","salt", "ZIP" };

			while( keysIterator.hasNext() )
			{
				String currentKey = keysIterator.next();

				if( Utilities.isElementInArray( currentKey, userProfileColumns ) )
				{
					updateParameters.put( currentKey, request.getParameter( currentKey ) );
				}
			}

			engine.updateUser( bid, updateParameters );

			if( request.getSession().getAttribute( "bid" ) != null )
			{
				request.getRequestDispatcher("/editBusiness.jsp").forward(request, response);
			}
			else
			{
				request.getRequestDispatcher("/editVeteranProfile.jsp").forward(request, response);

			}
		}
	}

}

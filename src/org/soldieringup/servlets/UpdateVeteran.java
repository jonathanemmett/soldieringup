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
import org.soldieringup.database.MySQL;

/**
 * Servlet implementation class UpdateVeteran
 */
@WebServlet("/UpdateVeteran")
public class UpdateVeteran extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateVeteran() {
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
		MySQL databaseConnection = MySQL.getInstance();
		Map<String,String> registrationErrors = new HashMap<String,String>();
		String[] requiredVeteranColumns = { "goal" };

		if( request.getSession().getAttribute( "uid" ) != null )
		{
			Engine engine = new Engine();
			long uid = Long.valueOf( request.getSession().getAttribute( "uid" ).toString() );
			Set<String> keys = request.getParameterMap().keySet();
			Map<String,Object> updateParameters = new HashMap<String,Object>();
			Iterator<String> keysIterator = keys.iterator();

			while( keysIterator.hasNext() )
			{
				String currentKey = keysIterator.next();

				if( Utilities.isElementInArray( currentKey, requiredVeteranColumns ) )
				{
					updateParameters.put( currentKey, request.getParameter( currentKey ) );
				}
			}

			engine.updateVeteran( uid, updateParameters );
			request.getRequestDispatcher("/editVeteranProfile.jsp").forward(request, response);
		}
	}

}

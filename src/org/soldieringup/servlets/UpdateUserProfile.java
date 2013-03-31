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

import org.soldieringup.Business;
import org.soldieringup.User;
import org.soldieringup.database.MySQL;

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
			long uid = Long.valueOf( request.getSession().getAttribute( "uid" ).toString() );
			Set<String> keys = request.getParameterMap().keySet();
			Map<String,Object> updateParameters = new HashMap<String,Object>();
			Iterator<String> keysIterator = keys.iterator();

			while( keysIterator.hasNext() )
			{
				String currentKey = keysIterator.next();

				if( User.isValidDatabaseInput( currentKey, request.getParameter( currentKey ) ) )
				{
					updateParameters.put( currentKey, request.getParameter( currentKey ) );
				}
			}

			MySQL.getInstance().updateUser( uid, updateParameters );

			if( request.getSession().getAttribute( "editing_account_type" ).equals( "business" ) )
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

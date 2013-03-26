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
import org.soldieringup.Veteran;
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
		String[] requiredBusinessKeys = { "business_name", "business_short_summary", "business_long_summary",
		  		  "business_address", "business_city", "business_state", "business_ZIP" };

		if( request.getSession().getAttribute( "aid" ) != null )
		{
			long aid = Long.valueOf( request.getSession().getAttribute( "aid" ).toString() );
			Set<String> keys = request.getParameterMap().keySet();
			Map<String,Object> updateParameters = new HashMap<String,Object>();
			Iterator<String> keysIterator = keys.iterator();

			while( keysIterator.hasNext() )
			{
				String currentKey = keysIterator.next();

				if( Veteran.isValidDatabaseInput( currentKey, request.getParameter( currentKey ) ) )
				{
					updateParameters.put( currentKey, request.getParameter( currentKey ) );
				}
			}

			MySQL.getInstance().updateVeteran( aid, updateParameters );
			request.getRequestDispatcher("/editVeteranProfile.jsp").forward(request, response);
		}
	}

}

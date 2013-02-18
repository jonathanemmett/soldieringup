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
import org.soldieringup.Utilities;
import org.soldieringup.database.MySQL;

/**
 * Servlet implementation class UpdateBusiness
 */
@WebServlet("/UpdateBusiness")
public class UpdateBusiness extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateBusiness() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if( request.getSession().getAttribute( "bid" ) != null )
		{
			long bid = Long.valueOf( request.getSession().getAttribute( "bid" ).toString() );
			Set<String> keys = request.getParameterMap().keySet();
			Map<String,Object> updateParameters = new HashMap<String,Object>();
			Iterator<String> keysIterator = keys.iterator();
			
			while( keysIterator.hasNext() )
			{
				String currentKey = keysIterator.next();
				
				if( Utilities.isElementInArray( currentKey, Business.databaseColumns ) )
				{
					System.out.println( "Inserting the key: " + currentKey );
					updateParameters.put( currentKey, request.getParameter( currentKey ) );
				}
			}
			
			MySQL.getInstance().updateBusiness( bid, updateParameters );
			request.getRequestDispatcher("/editBusiness.jsp").forward(request, response);
		}
	}

}

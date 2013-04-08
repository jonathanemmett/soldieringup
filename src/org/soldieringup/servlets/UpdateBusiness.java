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
		if( request.getSession().getAttribute( "aid" ) != null )
		{
			Engine engine = new Engine();
			long bid = Long.valueOf( request.getSession().getAttribute( "aid" ).toString() );
			Set<String> keys = request.getParameterMap().keySet();
			Map<String,Object> updateParameters = new HashMap<String,Object>();
			Iterator<String> keysIterator = keys.iterator();

			while( keysIterator.hasNext() )
			{
				String currentKey = keysIterator.next();
				String[] requiredBusinessKeys = { "business_name", "business_short_summary", "business_long_summary",
						"business_address", "business_city", "business_state", "business_ZIP" };


				if( Utilities.isElementInArray( currentKey, requiredBusinessKeys ) )
				{
					updateParameters.put( currentKey, request.getParameter( currentKey ) );
				}
			}

			engine.updateBusiness( bid, updateParameters );
			request.getRequestDispatcher("/editBusiness.jsp").forward(request, response);
		}
	}
}

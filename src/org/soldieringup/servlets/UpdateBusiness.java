package org.soldieringup.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.types.ObjectId;
import org.soldieringup.Business;
import org.soldieringup.User;
import org.soldieringup.service.MongoEngine;

/**
 * Servlet implementation class UpdateBusiness
 */
@WebServlet("/UpdateBusiness")
public class UpdateBusiness extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public UpdateBusiness()
	{
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession currentSession = request.getSession();
		if( currentSession.getAttribute( "editing_account_type" ) != null &&
				currentSession.getAttribute( "editing_account_type" ).equals( "business" ) )
		{
			MongoEngine engine = new MongoEngine();
			ObjectId businessId = (ObjectId) request.getSession().getAttribute( "aid" );
			Business businessToEdit = (Business) engine.findAccounts( "_id", businessId ).get( 0 );
			User userFromBusiness = engine.findUsers( "_id", currentSession.getAttribute( "uid" ) ).get(0);

			request.setAttribute( "business_to_edit", businessToEdit );
			request.setAttribute( "user_to_edit", userFromBusiness );
			request.setAttribute( "business_zip", engine.findZip( businessToEdit.getZip() ) );
			request.getRequestDispatcher( "/editBusiness.jsp" ).forward( request, response );
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if( request.getSession().getAttribute( "aid" ) != null )
		{
			MongoEngine engine = new MongoEngine();
			Business business = (Business) engine.findAccounts( "_id", request.getSession().getAttribute( "aid" ) ).get( 0 );

			if( request.getParameter( "address" ) != null )
			{
				business.setAddress( request.getParameter( "address" ) );
			}

			if( request.getParameter( "short_summary" ) != null )
			{
				business.setShortSummary( request.getParameter( "short_summary" ) );
			}

			if( request.getParameter( "long_summary" ) != null )
			{
				business.setLongSummary( request.getParameter( "long_summary" ) );
			}

			if( request.getParameter( "name" ) != null )
			{
				business.setName( request.getParameter( "name" ) );
			}

			if( request.getParameter( "zip" ) != null )
			{
				business.setZip( request.getParameter( "zip" ) );
			}

			engine.updateAccount( business );
		}
		doGet( request, response );
	}
}

package org.soldieringup.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;
import org.json.simple.JSONObject;
import org.soldieringup.Business;
import org.soldieringup.SoldierUpAccount;
import org.soldieringup.service.MongoEngine;

/**
 * Servlet implementation class Account
 * 
 * This Servlet contains the logic for retrieving user accounts.
 */
@WebServlet("/Account")
public class Account extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	MongoEngine engine = new MongoEngine();

	public Account()
	{
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String accountId = request.getParameter( "aid" );
		boolean ajax = "XMLHttpRequest".equals( request.getHeader( "x-requested-with" ) );

		if( accountId != null )
		{
			SoldierUpAccount foundAccount = findSoldierUpAccountFromId( accountId );

			if( foundAccount == null )
			{
				response.getWriter().println("<p>No account found</p>");
			}
			else if( ajax )
			{
				// TODO : printAccountInformation( foundAccount );
			}
			else
			{
				redirectToUrl( request, response, foundAccount );
			}
		}
	}

	/**
	 * Retrieves the account associated to the given id
	 * @param accountId String of the ObjectId of the account
	 * @return The account associated with the given accountId if found
	 */
	public SoldierUpAccount findSoldierUpAccountFromId( String accountId )
	{
		SoldierUpAccount foundAccount = null;
		if( ObjectId.isValid( accountId ) )
		{
			List<SoldierUpAccount> accountsFromId = engine.findAccounts( "_id", new ObjectId( accountId ) );
			if( accountsFromId.size() > 0 )
			{
				foundAccount = accountsFromId.get(0);
			}
		}

		return foundAccount;
	}

	/**
	 * Prints the given account as a json object
	 * @param aAccount
	 * @return
	 */
	public JSONObject getAccountAsJSONObject( SoldierUpAccount aAccount )
	{
		JSONObject accountJson = new JSONObject();
		return accountJson;
	}

	public void redirectToUrl( HttpServletRequest aRequest, HttpServletResponse aResponse, SoldierUpAccount aAccount ) throws ServletException, IOException
	{
		aRequest.setAttribute( "account", aAccount );
		aRequest.setAttribute( "account_zip", engine.findZip( aAccount.getZip() ) );

		if( aAccount instanceof Business)
		{
			aRequest.getRequestDispatcher( "/business.jsp").forward( aRequest, aResponse );
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

	}

}

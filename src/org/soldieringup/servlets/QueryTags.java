package org.soldieringup.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.soldieringup.database.MySQL;

/**
 * Servlet implementation class QueryTags
 */
@WebServlet("/QueryTags")
public class QueryTags extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryTags()
    {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println( "We are in the get" );
		String tagSearchTerm = request.getParameter( "term" );
		String objectType = request.getParameter( "type" );
		String command = request.getParameter( "cmd" );

		if( tagSearchTerm != null )
		{
			if( objectType != null && objectType.equals( "account" ) )
			{
				response.getWriter().println( MySQL.getInstance().getSimiliarTagsNotInAccount( tagSearchTerm, request ) );
			}
			else
			{
				response.getWriter().println( MySQL.getInstance().getSimiliarTags( tagSearchTerm ) );
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		MySQL databaseConnection = MySQL.getInstance();
		String tagSearchTerm = request.getParameter( "term" );
		String command = request.getParameter( "cmd");

		if( tagSearchTerm != null )
		{
			response.getWriter().println( databaseConnection.getSimiliarTagsNotInAccount( tagSearchTerm, request ) );
		}

		if( command.equals( "attachTagToAccount" ) )
		{
			String tag = request.getParameter( "tag" );
			tag = tag.trim();

			if( tag != null && request.getParameter( "hours_requested") != null )
			{
				long hoursRequested = 1;
				if( request.getParameter( "hours_requested") != null )
				{
					Long.valueOf( request.getParameter( "hours_requested" ).toString() );
				}

				JSONArray tagId = new JSONArray();
				tagId.add( databaseConnection.attachTagToAccount( tag, request, hoursRequested ) );
				response.getWriter().println( tagId );
			}
		}

		if( command.equals( "detachTagFromAccount" ) && request.getParameter( "tagId" ) != null )
		{
			long tagId = Long.valueOf( request.getParameter( "tagId" ).toString() );
			databaseConnection.detachTagFromAccount( tagId, request );
		}

	}
}

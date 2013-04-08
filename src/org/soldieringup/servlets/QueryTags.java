package org.soldieringup.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.soldieringup.Engine;

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
			Engine engine = new Engine();

			if( objectType != null && objectType.equals( "account" ) )
			{
				response.getWriter().println( engine.getSimiliarTagsNotInAccount( tagSearchTerm, request ) );
			}
			else
			{
				response.getWriter().println( engine.getSimiliarTags( tagSearchTerm ) );
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Engine engine = new Engine();
		String tagSearchTerm = request.getParameter( "term" );
		String command = request.getParameter( "cmd");

		if( tagSearchTerm != null )
		{
			response.getWriter().println( engine.getSimiliarTagsNotInAccount( tagSearchTerm, request ) );
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
				tagId.add( engine.attachTagToAccount( tag, request, hoursRequested ) );
				response.getWriter().println( tagId );
			}
		}

		if( command.equals( "detachTagFromAccount" ) && request.getParameter( "tagId" ) != null )
		{
			long tagId = Long.valueOf( request.getParameter( "tagId" ).toString() );
			engine.detachTagFromAccount( tagId, request );
		}

	}
}

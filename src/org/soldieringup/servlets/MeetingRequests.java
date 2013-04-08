package org.soldieringup.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.soldieringup.Engine;
import org.soldieringup.MeetingRequest;

/**
 *	This servlet is used to process the meeting requests that veterans and businesses will have
 */
@WebServlet("/MeetingRequests")
public class MeetingRequests extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MeetingRequests(){
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if( request.getParameter( "qid" ) != null && request.getParameter( "bid" ) != null )
		{
			Engine engine = new Engine();
			Long qid = Long.valueOf( request.getParameter( "qid" ).toString() );
			Long bid = Long.valueOf( request.getParameter( "bid" ).toString() );
			MeetingRequest meetingRequest = engine.getMeetingRequestFromQIDAndBID( qid, bid );
			if( meetingRequest != null )
			{
				JSONObject meetingRequestJSON = new JSONObject();
				meetingRequestJSON.put( "day", meetingRequest.getDay() );
				meetingRequestJSON.put( "time", meetingRequest.getTime() );
				meetingRequestJSON.put( "location", meetingRequest.getLocation() );
				meetingRequestJSON.put( "business_showed", meetingRequest.getBusinessShowed() );
				meetingRequestJSON.put( "veteran_showed", meetingRequest.getVeteranShowed() );
				response.getWriter().println( meetingRequestJSON );
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		String cmd = request.getParameter( "cmd" );

		if( cmd != null )
		{
			try
			{
				switch( cmd )
				{
				case "insert":
					insertNewMeetingRequest( request, response );
					break;
				}
			}
			catch( SQLException e )
			{
				e.printStackTrace();
			}
		}
	}

	private void insertNewMeetingRequest( HttpServletRequest request, HttpServletResponse response ) throws SQLException
	{
		Engine engine = new Engine();
		String[] databaseColumns = { "qid", "time", "day", "location" };
		HttpSession currentSession = request.getSession();

		if( currentSession.getAttribute( "editing_account_type" ) != null &&
				currentSession.getAttribute( "editing_account_type" ).equals( "business" ) )
		{
			Map<String,Object> newMeetingRequestParameters = new HashMap<String,Object>();

			newMeetingRequestParameters.put( "bid", currentSession.getAttribute( "aid" ).toString() );

			for( int i = 0; i < databaseColumns.length; ++i )
			{
				newMeetingRequestParameters.put( databaseColumns[i], request.getParameter( databaseColumns[i] ) );
			}

			engine.insertMeetingRequests(newMeetingRequestParameters);
		}
	}
}

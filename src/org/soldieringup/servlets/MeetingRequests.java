package org.soldieringup.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;
import org.json.simple.JSONObject;
import org.soldieringup.MeetingRequest;
import org.soldieringup.MongoEngine;
import org.soldieringup.Question;
import org.soldieringup.User;

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
	public MeetingRequests()
	{
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if( request.getParameter( "mid" ) != null )
		{
			MongoEngine engine = new MongoEngine();
			ObjectId meetingRequestId = new ObjectId( request.getParameter( "mid" ) );
			MeetingRequest meetingRequest = engine.findMeetingRequest( "_id", meetingRequestId ).get( 0 );

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
		MongoEngine engine = new MongoEngine();
		User veteran;
		ObjectId businessId;

		if( request.getSession().getAttribute( "editing_account_type" ) == "veteran" )
		{
			veteran = engine.findUsers( "_id", request.getSession().getAttribute( "uid" ) ).get( 0 );
			businessId = new ObjectId( request.getParameter( "aid" ) );
		}
		else
		{
			businessId = (ObjectId) request.getSession().getAttribute( "aid" );
			ObjectId veteranId = new ObjectId( request.getParameter( "aid" ) );
			veteran = engine.findUsers( "_id", veteranId ).get( 0 );
		}

		MeetingRequest meetingRequest = new MeetingRequest();
		meetingRequest.setDay( request.getParameter( "day" ) );
		meetingRequest.setTime( request.getParameter( "time" ) );
		meetingRequest.setLocation( request.getParameter( "location" ) );
		meetingRequest.setVeteran( veteran );
		meetingRequest.setBusiness( businessId );

		String questionId = request.getParameter( "qid" );
		if( questionId != null && ObjectId.isValid( questionId ) )
		{
			Question questionFromId = engine.findQuestions( "_id", new ObjectId( questionId ) ).get( 0 );
			meetingRequest.setQuestion( questionFromId );
		}

		engine.insertMeetingRequest( meetingRequest );
	}
}

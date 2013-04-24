package org.soldieringup.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.types.ObjectId;
import org.soldieringup.Question;
import org.soldieringup.Tag;
import org.soldieringup.User;
import org.soldieringup.service.MongoEngine;

/**
 * Servlet implementation class UpdateVeteranQuestion
 */
@WebServlet("/UpdateVeteranQuestion")
public class UpdateVeteranQuestion extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public UpdateVeteranQuestion()
	{
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String command = request.getParameter( "command" );

		if( command != null && command.equals( "delete") && request.getParameter( "qid" ) != null )
		{
			removeQuestion( request.getParameter( "qid" ), (ObjectId) request.getSession().getAttribute( "uid" ) );
			request.getRequestDispatcher( "/Questions.jsp" ).forward( request, response );
		}
	}

	/**
	 * Removes the question from the id if the given veteran
	 * id matches the veteran that asked the question
	 * @param aQuestionId ID of the question to remove
	 * @param aVeteranId ID of the veteran
	 */
	public void removeQuestion( String aQuestionId, ObjectId aVeteranId )
	{
		if( ObjectId.isValid( aQuestionId ) )
		{
			ObjectId questionId = new ObjectId( aQuestionId );
			MongoEngine engine = new MongoEngine();
			List<Question> possibleQuestions = engine.findQuestions( "_id", questionId );
			User loggedInVeteran = engine.findUsers( "_id", aVeteranId ).get( 0 );
			if( possibleQuestions.size() > 0 && possibleQuestions.get( 0 ).getVeteran().equals( loggedInVeteran ) )
			{
				engine.removeQuestion( possibleQuestions.get( 0 ) );
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		HttpSession currentSession = request.getSession();

		if( currentSession.getAttribute( "question_form_request_type" ) == null )
			return;

		Map<String,String> inputErrors = new HashMap<String,String>();

		String command = currentSession.getAttribute( "question_form_request_type" ).toString();

		getValidationErrors( request, inputErrors );
		if( inputErrors.isEmpty() )
		{
			if( command.equals( "insert" ) )
			{
				insertQuestion( request, response );
			}
			else if( command.equals( "update" ) )
			{
				updateQuestion( request );
			}
			request.getRequestDispatcher( "/Questions.jsp").forward( request, response );
		}
		else
		{
			String redirectUrl = "/VeteranQuestionForm.jsp";
			if( request.getSession().getAttribute( "question_form_update_question_id" ) != null )
			{
				redirectUrl += "?qid="+request.getSession().getAttribute( "question_form_update_question_id" );
			}

			request.getRequestDispatcher( redirectUrl ).forward( request, response );
		}
	}

	/**
	 * Creates a question from a given request, and inserts it into the server
	 * @param request Request to retrieve the question from
	 */
	private void insertQuestion( HttpServletRequest request, HttpServletResponse response )
	{
		MongoEngine engine = new MongoEngine();
		Question newQuestion = new Question();
		User veteranThatAsked = engine.findUsers( "_id", request.getSession().getAttribute( "aid" ) ).get( 0 );

		newQuestion.setTitle( request.getParameter( "question_title" ) );
		newQuestion.setAvailability( request.getParameter( "availability" ) );
		newQuestion.setDetailedDescription( request.getParameter( "question_detailed_description" ) );
		newQuestion.setUser( veteranThatAsked );
		List<Tag> questionTags = new ArrayList<Tag>();
		addTags( request.getParameterValues( "tag" ), questionTags );
		newQuestion.setTags( questionTags );
		engine.insertQuestion( newQuestion );
	}

	/**
	 * Updates the question from the given request.
	 * @param request Request to retrieve the question from
	 * @param response
	 */
	public void updateQuestion( HttpServletRequest request )
	{
		ObjectId questionId = (ObjectId) request.getSession().getAttribute( "question_form_update_question_id" );

		MongoEngine engine = new MongoEngine();
		Question updatedQuestion = engine.findQuestions( "_id", questionId ).get( 0 );
		User veteranFromQuestion = engine.findUsers( "_id", request.getSession().getAttribute( "aid" ) ).get( 0 );

		if( updatedQuestion.getVeteran().equals( veteranFromQuestion ) )
		{
			updatedQuestion.setTitle( request.getParameter( "question_title" ) );
			updatedQuestion.setAvailability( request.getParameter( "availability" ) );
			updatedQuestion.setDetailedDescription( request.getParameter( "question_detailed_description" ) );
			updatedQuestion.setUser( veteranFromQuestion );
			List<Tag> questionTags = new ArrayList<Tag>();
			addTags( request.getParameterValues( "tag" ), questionTags );
			updatedQuestion.setTags( questionTags );
			engine.updateQuestion( updatedQuestion );
		}
	}

	/**
	 * Adds tags to a given tag list
	 * @param aTagsToAdd Tags to add to list
	 * @param aTagList Tag list to add to
	 */
	public void addTags( String[] aTagsToAdd, List<Tag> aTagList )
	{
		MongoEngine engine = new MongoEngine();
		if( aTagsToAdd != null )
		{
			for( int i = 0; i < aTagsToAdd.length; ++i )
			{
				List<Tag> possibleTags = engine.findTags( "name", aTagsToAdd[i] );
				if( possibleTags.size() > 0  && !aTagList.contains( possibleTags.get( 0 ) ) )
				{
					aTagList.add( possibleTags.get( 0 ) );
				}

				if( possibleTags.size() == 0 )
				{
					Tag newTag = new Tag( aTagsToAdd[i] );
					engine.insertTag( newTag );
					aTagList.add( newTag );
				}
			}
		}

	}

	/**
	 * Determines if a request has any input errors
	 * @param request Request to check for errors
	 * @param inputErrors (out) Errors that occured from the request.
	 */
	public void getValidationErrors( HttpServletRequest request, Map<String,String> inputErrors )
	{
		String[] requiredStrings = { "qid", "question_title", "availability",
				"question_detailed_description", "vid" };

		for( int i = 0; i <requiredStrings.length; ++i )
		{
			if( ( requiredStrings[i].equals( "vid" ) || requiredStrings[i].equals( "qid" ) ) )
			{
				// Do nothing.
			}
			else if( request.getParameter( requiredStrings[i] ) == null )
			{
				inputErrors.put( requiredStrings[i], "required" );
			}
		}
	}
}

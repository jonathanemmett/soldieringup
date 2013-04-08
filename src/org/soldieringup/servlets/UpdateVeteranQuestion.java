package org.soldieringup.servlets;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.soldieringup.Engine;
import org.soldieringup.database.MySQL;

/**
 * Servlet implementation class UpdateVeteranQuestion
 */
@WebServlet("/UpdateVeteranQuestion")
public class UpdateVeteranQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateVeteranQuestion() {
		super();
		// TODO Auto-generated constructor stub
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
			Map<String,Object> objectsToDelete = new HashMap<String,Object>();
			objectsToDelete.put( "qid", request.getParameter( "qid" ) );
			try
			{
				Engine engine = new Engine();
				engine.removeTagsFromQuestion( Long.valueOf( request.getParameter ("qid") ) );
				MySQL.getInstance().deleteFromTable( "questions", objectsToDelete );
				request.getRequestDispatcher( "/Questions.jsp" ).forward( request, response );
			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
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

		Map<String,String[]> questionParameters = request.getParameterMap();
		Map<String,String> inputErrors = new HashMap<String,String>();
		Map<String,Object> parameters = new HashMap<String,Object>();
		Map<String,Object> whereParameters = new HashMap<String,Object>();

		String command = currentSession.getAttribute( "question_form_request_type" ).toString();

		getValidationErrors( request, response, inputErrors, parameters );
		if( inputErrors.isEmpty() )
		{
			if( command.equals( "insert" ) )
			{
				insertQuestion( request, response );
			}
			else if( command.equals( "update" ) )
			{
				try
				{
					long qid =  Long.valueOf( request.getSession().getAttribute( "question_form_update_question_id" ).toString() );
					whereParameters.put( "vid", request.getSession().getAttribute( "aid" ) );
					whereParameters.put( "qid", request.getSession().getAttribute( "question_form_update_question_id" ) );
					MySQL.getInstance().updateTable( "questions", parameters, whereParameters );
					MySQL.getInstance().removeTagsFromQuestion( qid );
					if( request.getParameterValues( "tag" ) != null )
					{
						String[] tags = request.getParameterValues( "tag" );
						for( int i = 0; i < tags.length; ++i )
						{
							if( !tags[i].equals("") )
							{
								MySQL.getInstance().attachTagsToQuestion( tags[i], qid );
							}
						}
					}
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
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

	private void insertQuestion( HttpServletRequest request, HttpServletResponse response )
	{
		Engine engine = new Engine();
		ResultSet generatedQuestionId = engine.insertVeteranQuestion(
				request.getParameter( "question_title" ),
				request.getParameter( "availability" ),
				request.getParameter( "question_detailed_description" ),
				Long.valueOf( request.getSession().getAttribute( "aid" ).toString() ) );

		try
		{
			if( generatedQuestionId.next() && request.getParameterValues( "tag" ) != null )
			{
				long qid = generatedQuestionId.getLong( 1 );
				String[] tags = request.getParameterValues( "tag" );
				for( int i = 0; i < tags.length; ++i )
				{
					MySQL.getInstance().attachTagsToQuestion( tags[i], qid );
				}
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public void getValidationErrors
	(
			HttpServletRequest request,
			HttpServletResponse response,
			Map<String,String> inputErrors,
			Map<String,Object> parametersForQuery
			)
	{
		String[] requiredStrings = { "qid", "question_title", "availability", "question_detailed_description", "vid" };
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
			else
			{
				parametersForQuery.put( requiredStrings[i], request.getParameter( requiredStrings[i] ) );
			}
		}

		System.out.println( "Parameters size: " + parametersForQuery.size() );
	}
}

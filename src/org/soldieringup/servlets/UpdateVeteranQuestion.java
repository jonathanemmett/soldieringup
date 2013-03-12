package org.soldieringup.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.soldieringup.Question;
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Map<String,String[]> questionParameters = request.getParameterMap();
		Map<String,String> inputErrors = new HashMap<String,String>();
 
		String[] requiredStrings = Question.QuestionDatabaseColumnsStrings;
		for( int i = 0; i <requiredStrings.length; ++i )
		{
			if( ( requiredStrings[i].equals( Question.QuestionDatabaseColumns.VID.getDatabaseColumnString() ) ||
			      requiredStrings[i].equals( Question.QuestionDatabaseColumns.QID.getDatabaseColumnString() ) ) )
			{
				// Do nothing.
			}
			else if( request.getParameterMap().get( requiredStrings[i] ) == null )
			{
				inputErrors.put( requiredStrings[i], "required" );
			}
		}
		
		if( inputErrors.isEmpty() )
		{
			response.getWriter().println( "Inserting" );
			MySQL.getInstance().insertVeteranQuestion(
					request.getParameter( "question_title" ),
					request.getParameter( "availability" ),
					request.getParameter( "question_detailed_description" ), 
					Long.valueOf( request.getSession().getAttribute( "vid" ).toString() ) );
		}
		else
		{
			response.getWriter().println( "Not evertying is preodsadf" );
			Iterator<String> errors = inputErrors.keySet().iterator();
			
			while( errors.hasNext() )
			{
				String currentKey = errors.next();
				response.getWriter().println( currentKey + " " + inputErrors.get( currentKey ) );
			}
		}
	}
}

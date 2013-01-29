package org.soldieringup.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.routines.EmailValidator;
import org.soldieringup.database.MySQL;

/**
 * Servlet implementation class UniqueEmail
 */
@WebServlet("/UniqueEmail")
public class UniqueEmail extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Ajax request parameters
	private static final String REQUEST_EMAIL = "email";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UniqueEmail() 
    {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter writer = response.getWriter();
		
		String requestParameter = request.getParameter( "request" );
		
		if( requestParameter == null)
			return;
		
		switch( requestParameter )
		{
		case REQUEST_EMAIL:
			checkValidAndUniqueEmail( writer, request.getParameter("email") );
			break;
		}
	}
	
	/**
	 * Checks to make sure that an email is valid, and unique
	 * @param aResponseOutput The writer to output to the request
	 * @param aEmail The email to check for as unique
	 */
	public void checkValidAndUniqueEmail( PrintWriter aResponseOutput, String aEmail )
	{

		boolean validEmail =  aEmail != null &&
							  EmailValidator.getInstance().isValid( aEmail ) &&
							  !MySQL.getInstance().checkIfEmailIsInUse( aEmail );
		
		if( validEmail )
		{
			aResponseOutput.println( "true" );
		}
		else
		{
			aResponseOutput.println( "" );
		}
		
		
	}

}

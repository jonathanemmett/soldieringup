package org.soldieringup.servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.soldieringup.Engine;

/**
 * Servlet implementation class AccountSubscriber
 */
@WebServlet({ "/AccountSubscriber", "/addAccount" })
public class AccountSubscriber extends HttpServlet {
	private static final Logger log = Logger.getLogger (AccountSubscriber.class.getName ());
	private static final long serialVersionUID = 1L;
	private static final String _regex_fname = "^[a-zA-Z0-9]*$";
	private static final String _regex_lname = "^[a-zA-Z0-9]*$";
	private static final String _regex_company = "^[a-zA-Z0-9]*$";
	private static final String _regex_cellphone = "^[a-zA-Z0-9]*$";
	private static final String _regex_homephone = "^[a-zA-Z0-9]*$";
	private static final String _regex_businessphone = "^[a-zA-Z0-9]*$";
	private static final String _regex_address = "^[a-zA-Z0-9]*$";
	private static final String _regex_city = "^[a-zA-Z0-9]*$";
	private static final String _regex_state = "^[a-zA-Z0-9]*$";
	private static final String _regex_zip = "^[a-zA-Z0-9]*$";
	private static final String _regex_email = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.(?:[A-Z]{2}|com|org|net|edu|gov|mil|biz|info|mobi|name|aero|asia|jobs|museum)$";
	// holds the values that are passed by the user
	private HashMap<String, String> values = new HashMap <String, String> ();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountSubscriber() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher ("account_add.jsp").include (request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (ParseRequest (request, response)) {
			Engine engine = new Engine ();
			try {
				engine.AddAccount (values);
				request.getRequestDispatcher ("account_add_success.jsp").include (request, response);
			} catch (Exception e) {
				log.error ("Failed to add account", e);
			}
		}
	}
	
	private boolean ParseRequest (HttpServletRequest request, HttpServletResponse response)
	{
		
		HashMap<String, String> error_messages = new HashMap <String, String> ();
		boolean valid_form = true;
		
		//TODO: need a map and iteration to handle this properly
		values.put ("fname", request.getParameter ("fname"));
		values.put ("lname", request.getParameter ("lname"));
		values.put ("company", request.getParameter ("company"));
		values.put ("cellphone", request.getParameter ("cellphone"));
		values.put ("homephone", request.getParameter ("homephone"));
		values.put ("businessphone", request.getParameter ("businessphone"));
		values.put ("address", request.getParameter ("address"));
		values.put ("city", request.getParameter ("city"));
		values.put ("state", request.getParameter ("state"));
		values.put ("zip", request.getParameter ("zip"));
		values.put ("password", request.getParameter ("password"));
		values.put ("email", request.getParameter ("email"));
		
		if (error_messages.size () != 0)
			valid_form = false;
		
		return valid_form;
	}

}

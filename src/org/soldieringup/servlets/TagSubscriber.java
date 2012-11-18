package org.soldieringup.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.soldieringup.Engine;
import org.soldieringup.Tag;

/**
 * Servlet implementation class TagSubscriber
 */
@WebServlet({ "/TagSubscriber", "/tags" })
public class TagSubscriber extends HttpServlet
{
	private static final long	serialVersionUID	= 1L;
	private static final Logger	log			= Logger.getLogger (TagSubscriber.class.getName ());
	private static final String	_cmd			= "cmd";
	private HashMap<String, String> values = new HashMap <String, String> ();
	
	private static final String _regex_fname = "^[a-zA-Z0-9]*$";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TagSubscriber ()
	{
		super ();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String cmd = request.getParameter (_cmd);

		if (cmd != null) {
			switch (cmd) {
				case "add":
					ParseRequest (request, response);
				default:
					ParseRequest (request, response);
					request.getRequestDispatcher ("tag_add.jsp").include (request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
	}

	private void ParseRequest (HttpServletRequest request, HttpServletResponse response)
	{
		boolean valid_form = true;
		values.put ("name", request.getParameter ("fname"));
		if (values.get ("name").matches (_regex_fname) == false)
			valid_form = false;
		
		Engine engine = new Engine ();
		try {
			if (valid_form)
				engine.addTags (values);

		} catch (Exception e) {
			log.error ("Failed to add tags", e);
		}
	}
}

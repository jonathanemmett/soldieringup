package org.soldieringup.servlets;

import java.io.IOException;
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
 * Servlet implementation class TagPublisher
 */
@WebServlet({ "/TagPublisher", "/tagpub" })
public class TagPublisher extends HttpServlet
{
	private static final long	serialVersionUID	= 1L;
	private static final Logger	log			= Logger.getLogger (TagPublisher.class.getName ());
	private static final String	_cmd			= "cmd";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TagPublisher ()
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
				case "tags":
					ParseRequest (request, response);
				default:
					ParseRequest (request, response);
					request.getRequestDispatcher ("tags.jsp").include (request, response);
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
		Engine engine = new Engine ();
		try {
			Map<Integer, Tag> tags = engine.retrieveTags ();
			request.setAttribute ("tags", tags);

		} catch (Exception e) {
			log.error ("Failed to retrieve tags", e);
		}
	}
}

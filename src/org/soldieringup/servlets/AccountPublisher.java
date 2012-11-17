package org.soldieringup.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.soldieringup.Engine;

/**
 * Servlet implementation class PublisherAccounts
 */
@WebServlet({ "/PublisherAccounts", "/paccct" })
public class AccountPublisher extends HttpServlet
{
	private static final long	serialVersionUID	= 1L;
	private static final String	_cmd			= "cmd";
	static final Logger		log			= Logger.getLogger (AccountPublisher.class.getName ());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AccountPublisher ()
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
				case "roster":
					retrieveAccounts (request, response);
				default:
					log.debug ("Don't know what to do");
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

	private void retrieveAccounts (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		Engine engine = new Engine ();
		req.setAttribute ("accounts", engine.retrieveAccounts ());
		req.getRequestDispatcher ("/jsp/calendar_setup.jsp").include (req, resp);
	}
}

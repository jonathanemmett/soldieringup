package org.soldieringup.servlets;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.soldieringup.Engine;
import org.soldieringup.Roster;

/**
 * Servlet implementation class Welcome
 */
@WebServlet("/Welcome")
public class Welcome extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final Logger log = Logger.getLogger (Welcome.class.getName ());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Welcome() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug ("Loading Welcome Servlet");
		retrieveRoster (request, response);
		request.getRequestDispatcher ("index.jsp").include (request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		retrieveRoster (request, response);
		request.getRequestDispatcher ("index.jsp").include (request, response);
	}
	
	private void retrieveRoster (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		Engine engine = new Engine ();
		Map<Object, Roster> hm = engine.retrieveRoster ();
		// Get a set of the entries 
		Set set = hm.entrySet(); 
		// Get an iterator 
		Iterator i = set.iterator(); 
		
		while(i.hasNext()) { 
			Map.Entry me = (Map.Entry)i.next(); 
			log.debug ("Key:" + me.getKey() + ": "); 
			log.debug ("Value:" + ((Roster)me.getValue()).get_title()); 
			}
		
		req.setAttribute ("roster", engine.retrieveRoster ());
	}
}

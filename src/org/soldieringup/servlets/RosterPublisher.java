//Copyright (c) <2012> <Jarrett Homann>.
//Copyright (c) <2012> Jared Jennings <jared@jaredjennings.org>.
//All rights reserved.
//
//Redistribution and use in source and binary forms are permitted
//provided that the above copyright notice and this paragraph are
//duplicated in all such forms and that any documentation,
//advertising materials, and other materials related to such
//distribution and use acknowledge that the software was developed
//by the <organization>.  The name of the
//University may not be used to endorse or promote products derived
//from this software without specific prior written permission.
//THIS SOFTWARE IS PROVIDED ``AS IS'' AND WITHOUT ANY EXPRESS OR
//IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
//WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.

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
import org.soldieringup.Roster;

/**
 * Servlet implementation class RosterPublisher
 */
@WebServlet({ "/RosterPublisher", "/rp" })
public class RosterPublisher extends HttpServlet {
	static final Logger log = Logger.getLogger(RosterPublisher.class.getName ());
	private static final long serialVersionUID = 1L;
	private static final String _cmd = "cmd";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RosterPublisher() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cmd = request.getParameter (_cmd);
		log.debug ("Loading Roster Publisher");
		if (cmd != null) {
			switch (cmd) {
				case "roster":
					retrieveRoster (request, response);
				default:
					log.debug ("Don't know what to do");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	private void retrieveRoster (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		Engine engine = new Engine ();
		req.setAttribute ("roster", engine.retrieveRoster ());
		req.getRequestDispatcher ("/jsp/calendar_setup.jsp").include (req, resp);
	}
}

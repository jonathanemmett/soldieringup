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

package org.soldieringup;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.soldieringup.database.MySQL;

/**
 * @author jjennings
 * This class is responsible for all transactions of the website
 */
public class Engine
{
	private static final Logger log = Logger.getLogger(Engine.class.getName());

	public Engine ()
	{
		
	}
	
	/**
	 * retrieve events from the database
	 * @return
	 */
	public Map<Object,Roster> retrieveRoster ()
	{
		MySQL mysql = new MySQL ();
		try {
			return mysql.retrieveRoster ();
		} catch (SQLException e) {
			log.error ("Failed to retrieve roster", e);
		}
		return null;
	}
	
	public Map<Object,Roster> retrieveRoster (int account_id)
	{
		MySQL mysql = new MySQL ();
		try {
			return mysql.retrieveRoster ();
		} catch (SQLException e) {
			log.error ("Failed to retrieve roster", e);
		}
		return null;
	}

	public Map<Object,Account> retrieveAccounts ()
	{
		MySQL mysql = new MySQL ();
		return mysql.retrieveAccounts ();
	}

	public void AddAccount (HashMap<String, String> values)
	{
		MySQL mysql = new MySQL ();
		mysql.AddAccount (values);
		
	}

	public Map<Integer, Tag> retrieveTags ()
	{
		MySQL mysql = new MySQL ();
		return mysql.retrieveTags ();
	}
}

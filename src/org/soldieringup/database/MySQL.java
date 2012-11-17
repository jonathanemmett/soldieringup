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

package org.soldieringup.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.soldieringup.Account;
import org.soldieringup.Roster;
import org.soldieringup.Tag;

/**
 * This class is responsible for database transactions
 * @author jjennings
 *
 */
public class MySQL
{
	static final Logger log = Logger.getLogger(MySQL.class.getName());
	private static final String _jdbcConnString = "jdbc:mysql://localhost/solderingup?user=dbService&password=8w8p21zy0c4pyn";
	private Connection connect = null;
	
	// database table column titles
	private static final String _roster_title = "title";
	private static final String _roster_description = "description";
	private static final String _roster_id = "id";
	private static final String _roster_tags = "tags";
	
	public MySQL ()
	{
		dbConn();
	}

	private void dbConn ()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(_jdbcConnString);
			
		} catch (ClassNotFoundException ex) {
			log.log(Level.ERROR, null, ex);
		} catch (SQLException ex) {
			log.log(Level.ERROR, null, ex);
		}
	}
	
	
	public Map<Object,Roster> retrieveRoster ()
	{
		Map<Object,Roster> mp = new HashMap<Object, Roster>();
		// got all the Roster entries
		try {
			PreparedStatement ps = connect.prepareStatement("select id,title,description,tags from `solderingup`.`roster`");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Roster roster = new Roster ();
				roster = parse(rs, roster);
				mp.put (roster.get_id (), roster);
			}			
		} catch (Exception e) {
			log.error ("Failed to retrieve roster events", e);
		}
		log.debug ("Loaded " + mp.size () + "Roster posts from the database");
		return mp;
	}
	
	private Roster parse (ResultSet rs, Roster roster) throws SQLException
	{
		roster.set_id ((Integer) rs.getInt (_roster_id));
		roster.set_title (rs.getString (_roster_title));
		roster.set_description (rs.getString (_roster_description));
		List<Tag> tags = tags( splitTags (rs.getString (_roster_tags)));
		roster.set_tags (tags);
		return roster;
	}
	
	/**
	 * Split the tags IDs into an array
	 * @param tags
	 * @return
	 */
	private String[] splitTags (String tags)
	{
		tags = "java;Legal;Mechanical";
		String[] tags_array = tags.split (";");
		return tags_array;
	}
	
	/**
	 * Should retrieve the tags from the database
	 * @param tags_array
	 * @return
	 */
	private List<Tag> tags (String[] tags_array)
	{
		List<Tag> ls = new ArrayList<Tag>();
		for (String s : tags_array)
		{
			Tag tag = new Tag ();
			tag.set_name (s);
		}
		
		return ls;
	}

	public Map<Object, Account> retrieveAccounts ()
	{
		// TODO Auto-generated method stub
		return null;
	}

}

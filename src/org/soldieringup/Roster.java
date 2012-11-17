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

import java.util.List;

/**
 * @author jjennings
 *
 */
public class Roster
{
	
	private int _id;
	private String _title;
	private String _description;
	private List<Tag> _tags;
	/**
	 * @return the _id
	 */
	public int get_id ()
	{
		return _id;
	}
	/**
	 * @param _id the _id to set
	 */
	public void set_id (int _id)
	{
		this._id = _id;
	}
	/**
	 * @return the _title
	 */
	public String get_title ()
	{
		return _title;
	}
	/**
	 * @param _title the _title to set
	 */
	public void set_title (String _title)
	{
		this._title = _title;
	}
	/**
	 * @return the _description
	 */
	public String get_description ()
	{
		return _description;
	}
	/**
	 * @param _description the _description to set
	 */
	public void set_description (String _description)
	{
		this._description = _description;
	}
	/**
	 * @return the _tags
	 */
	public List<Tag> get_tags ()
	{
		return _tags;
	}
	/**
	 * @param _tags the _tags to set
	 */
	public void set_tags (List<Tag> _tags)
	{
		this._tags = _tags;
	}

}

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

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author jjennings
 */
@Document
public class Tag
{
	@Id
	protected ObjectId id;
	protected long tid;
	@Indexed(unique = true)
	private String name;

	public ObjectId getObject_id()
	{
		return id;
	}

	public Tag (String name)
	{
		this.name = name;
	}

	public long get_tid()
	{
		return tid;
	}

	public void set_tid( long tid )
	{
		this.tid = tid;
	}

	public String get_name()
	{
		return name;
	}

	public void set_name( String name )
	{
		this.name = name;
	}
}

package org.soldieringup;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * What War's the Veteran has been in.
 * @author jjennings
 *
 */
public class War
{
	@Id
	private ObjectId id;
	@Indexed
	private String name;
	/**
	 * @return the name
	 */
	public String getName ()
	{
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName (String name)
	{
		this.name = name;
	}
	@Override
	public String toString ()
	{
		return "War { name=" + name + "]";
	}
}

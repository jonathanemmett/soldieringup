package org.soldieringup;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Role
{
	@Id
	protected ObjectId	id;
	private String		role	= "";

	public Role ()
	{
		super ();
	}

	public Role (String role_name)
	{
		super ();
		this.role = role_name;
	}

	/**
	 * @return the role
	 */
	public String getName ()
	{
		return role;
	}

	public String getRole ()
	{
		return role;
	}

	/**
	 * @param role
	 *                the role to set
	 */
	public void setRole (String role)
	{
		this.role = role;
	}

	@Override
	public boolean equals (Object obj)
	{
		if (!(obj instanceof Role)) { return false; }
		if (this == obj) { return true; }
		Role rhs = (Role) obj;
		return new EqualsBuilder ().append (id, rhs.id).isEquals ();
	}

	@Override
	public int hashCode ()
	{
		return new HashCodeBuilder ().append (id).toHashCode ();
	}
}

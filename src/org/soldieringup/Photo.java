package org.soldieringup;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Photo
{
	@Id
	protected ObjectId id;
	private long bid;
	private String title;
	private String src;

	/**
	 * @return the object id
	 */
	public ObjectId getId ()
	{
		return id;
	}

	public long getBid()
	{
		return bid;
	}

	public String getTitle()
	{
		return title;
	}

	public String getSrc()
	{
		return src;
	}

	public void setBid( long bid )
	{
		this.bid = bid;
	}

	public void setTitle( String title )
	{
		this.title = title;
	}

	public void setSrc( String src )
	{
		this.src = src;
	}
}

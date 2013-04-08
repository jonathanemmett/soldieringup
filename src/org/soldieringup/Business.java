package org.soldieringup;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;



/**
 * Class represents a registered business
 * @author Jake
 *
 */
public class Business extends SoldierUpAccount {

	private long bid;
	private String name;
	protected String short_summary;
	protected String long_summary;
	protected String work_number;
	@DBRef
	private List<Tag> tag;

	/**
	 * Gets the business id
	 * @return The business id
	 */
	public long getBid()
	{
		return bid;
	}

	/**
	 * Sets the business id number
	 * @param bid ID number of the business
	 */
	public void setBid( long bid )
	{
		this.bid = bid;
	}

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

	/**
	 * Gets the short summary
	 * @return The short summary
	 */
	public String getShortSummary()
	{
		return short_summary;
	}

	/**
	 * Sets the short summary
	 * @param short_summary Short Summary
	 */
	public void setShortSummary( String short_summary )
	{
		this.short_summary = short_summary;
	}

	/**
	 * Gets the long summary
	 * @return The long summary
	 */
	public String getLongSummary()
	{
		return long_summary;
	}

	public void setLongSummary( String long_summary )
	{
		this.long_summary = long_summary;
	}

	/**
	 * Sets the phone number
	 * @param work_number Phone Number
	 */
	public void setWorkNumber( String work_number )
	{
		this.work_number = work_number;
	}

	/**
	 * Gets the phone number
	 * @return Phone number
	 */
	public String getWorkNumber()
	{
		return work_number;
	}


	/**
	 * @return the tag
	 */
	public List<Tag> getTag ()
	{
		return tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag (List<Tag> tag)
	{
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "Business [id=" + id + ", name=" + name + ", address=" + address + ", zip=" + zip + "]";
	}
}

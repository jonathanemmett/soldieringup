package org.soldieringup;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;



/**
 * Class represents a registered business
 * @author Jake
 *
 */
@Document(collection = "user")
public class Business extends SoldierUpAccount {

	private long bid;
	private String name;
	protected String short_summary;
	protected String long_summary;
	protected String work_number;

	@DBRef
	private User owner;

	/**
	 * Sets the id of the business owner
	 * @param owner_id The ID of the business owner
	 */
	public void setOwner( User owner )
	{
		this.owner = owner;
	}

	/**
	 * Gets the id of the business owner
	 * @return The id of the business owner
	 */
	public User getOwner()
	{
		return owner;
	}

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

	@Override
	public String toString() {
		return "Business [id=" + id + ", name=" + name + ", address=" + address + ", zip=" + zip + "]";
	}
}

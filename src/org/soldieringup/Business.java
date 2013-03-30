package org.soldieringup;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;


/**
 * Class represents a registered business
 * @author Jake
 *
 */
public class Business extends SoldierUpAccount {

	@Id private ObjectId id;
	private long bid;
	private String cover_src;
	private String name;
	private String short_summary;
	private String long_summary;
	private String work_number;
	private String address;
	private String zip;

	/**
	 * Class Constructor
	 */
	public Business( )
	{

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
	 * Gets the src of the cover photo
	 * @return The src of the cover photo
	 */
	public String getCoverSrc()
	{
		return cover_src;
	}

	/**
	 * Gets the name
	 * @return The name
	 */
	public String getBusinessName()
	{
		return name;
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
	 * Gets the long summary
	 * @return The long summary
	 */
	public String getLongSummary()
	{
		return long_summary;
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
	 * Gets the street address
	 * @return Street Address
	 */
	public String getAddress()
	{
		return address;
	}

	/**
	 * Gets the zip code
	 * @return Zip code
	 */
	public String getZip()
	{
		return zip;
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
	 * Sets the src of the cover photo
	 * @param cover_src Src of the business cover
	 */
	public void setCoverSrc( String cover_src )
	{
		this.cover_src = cover_src;
	}

	/**
	 * Sets the name
	 * @param name Business Name
	 */
	public void setName( String name )
	{
		this.name = name;
	}

	/**
	 * Sets the short summary
	 * @param short_summary Short Summary
	 */
	public void setShortSummary( String short_summary )
	{
		this.short_summary = short_summary;
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
	 * Sets the street address
	 * @param Street address
	 */
	public void setAddress( String address )
	{
		this.address = address;
	}

	/**
	 * Sets the zip code
	 * @param zip Zip code
	 */
	public void setZip( String zip )
	{
		this.zip = zip;
	}

	/**
	 * Makes that a given key and it's associated value are valid inputs
	 * for the database
	 * @param aKey Key to check
	 * @param aValue Value associated to the key
	 * @return True if the input is valid for the database, false otherwise
	 */
	public static boolean isValidDatabaseInput( String aKey, String aValue )
	{
		return false;
	}

	@Override
	public String toString() {
		return "Business [id=" + id + ", name=" + name + ", address=" + address + ", zip=" + zip + "]";
	}
}

package org.soldieringup;

/**
 * Class represents a registered business 
 * @author Jake
 *
 */
public class Business {
	
	public static String databaseColumns[] =
		{
		"bid",
		"contact_id",
		"name",
		"short_summary",
		"long_summary",
		"work_number",
		"address",
		"ZIP",
		"cover_photo_id",
		"profile_photo_id"
		};
	
	private long bid;
	private long contact_id;
	private String cover_src;
	private String profile_src;
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
	 * Gets the main contact's id
	 * @return The main contact's id
	 */
	public long getContactId()
	{
		return contact_id;
	}
	
	public String getCoverSrc()
	{
		return cover_src;
	}
	
	public String getProfilePhotoSrc()
	{
		return profile_src;
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
	 * Sets the id of the main contact 
	 * @param contact_id Contact's user id
	 */
	public void setContactId( long contact_id )
	{
		this.contact_id = contact_id;
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
	 * Sets the src of the business profile photo
	 * @param profile_src Src of the business profile photo
	 */
	public void setProfilePhotoSrc( String profile_src )
	{
		this.profile_src = profile_src;
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
}

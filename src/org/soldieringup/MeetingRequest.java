package org.soldieringup;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MeetingRequest
{
	private long bid;
	private long qid;
	private String time;
	private String day;
	private String location;
	private String veteran_showed;
	private char business_showed;

	/**
	 * Default constructor
	 */
	public MeetingRequest()
	{
	}

	/**
	 * Initializes the object with the given result set
	 * @param aAccountResultSet Result set to initialize the meeting request with
	 */
	public void init(ResultSet aAccountResultSet) throws SQLException
	{
		this.bid = aAccountResultSet.getLong( "bid" );
		this.qid = aAccountResultSet.getLong( "qid" );
		this.time = aAccountResultSet.getString( "time" );
		this.day = aAccountResultSet.getString( "day" );
		this.location = aAccountResultSet.getString( "location" );
		this.veteran_showed = aAccountResultSet.getString( "veteran_showed" );
	}

	/**
	 * Sets the id of the business who created the meeting request
	 * @param aBid The id of the business who created the meeting request
	 */
	public void setBid( long aBid )
	{
		this.bid = aBid;
	}

	/**
	 * Sets the id of the question this request is for
	 * @param aQid The id of the question this request is for
	 */
	public void setQid( long aQid)
	{
		this.qid = aQid;
	}

	/**
	 * Sets the time of the meeting request
	 * @param aTime The time of the meeting request
	 */
	public void setTime( String aTime )
	{
		this.time = aTime;
	}

	/**
	 * Sets the day for the meeting to occur
	 * @param aDay The day for the meeting to occur
	 */
	public void setDay( String aDay )
	{
		this.day = aDay;
	}

	/**
	 * Sets the location for the meeting
	 * @param aLocation The location for the meeting
	 */
	public void setLocation( String aLocation )
	{
		this.location = aLocation;
	}

	/**
	 * Sets the status of how the veteran showed up
	 * @param aVeteranShowed The status of how the veteran showed up
	 */
	public void setVeteranShowed( String aVeteranShowed )
	{
		this.veteran_showed = aVeteranShowed;
	}

	/**
	  * Sets the status of how the business showed up
	 * @param aVeteranShowed The status of how the business showed up
	 */
	public void setBusinessShowed( char aBusinessShowed )
	{
		this.business_showed = aBusinessShowed;
	}

	/**
	 * Gets the id of the business who sent the request
	 * @return The id of the business who sent the request
	 */
	public long getBid()
	{
		return this.bid;
	}

	/**
	 * Gets the id of the question that this meeting request is for
	 * @return The id of the question that this meeting request is for
	 */
	public long getQid()
	{
		return this.qid;
	}

	/**
	 * Gets the time for the meeting request to occur
	 * @return The time for this meeting request to occur
	 */
	public String getTime()
	{
		return this.time;
	}

	/**
	 * Gets the day for this meeting to occur
	 * @return The day for this meeting to occur
	 */
	public String getDay()
	{
		return this.day;
	}

	/**
	 * Gets the location for this meeting
	 * @return The location for this meeting
	 */
	public String getLocation()
	{
		return this.location;
	}

	/**
	 * Gets how the veteran showed
	 * @return How the veteran showed
	 */
	public String getVeteranShowed()
	{
		return this.veteran_showed;
	}

	/**
	 * Gets how the business showed
	 * @return How the business showed
	 */
	public char getBusinessShowed()
	{
		return this.business_showed;
	}
}

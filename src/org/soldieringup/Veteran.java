package org.soldieringup;

import java.util.GregorianCalendar;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * Class to represent
 * @author Jake
 *
 */
public class Veteran
{
	@Id
	protected ObjectId id;
	private long vid;
	private String mGoal = null;
	private GregorianCalendar start;
	private GregorianCalendar end;
	@Indexed
	private DIVISION devision;

	/**
	 * Gets the MySQL id for this veteran
	 * @return MySQL id for this veteran
	 */
	public long getVid()
	{
		return this.vid;
	}

	/**
	 * Sets the MySQL id for this Veteran object
	 * @param MySQL VID for this object
	 */
	public void setVid( long vid )
	{
		this.vid = vid;
	}

	/**
	 * Gets the veteran's goal
	 * @return The goal of the veteran
	 */
	public String getGoal()
	{
		return mGoal;
	}

	/**
	 * Sets the veteran's goal
	 * @param aGoal Goal for the veteran
	 */
	public void setGoal( String aGoal )
	{
		this.mGoal = aGoal;
	}

	/**
	 * When the Vet joined the Armed Forces
	 * @return the start
	 */
	public GregorianCalendar getStart ()
	{
		return start;
	}

	/**
	 * When the Vet left the Armed Forces
	 * @param start the start to set
	 */
	public void setStart (GregorianCalendar start)
	{
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public GregorianCalendar getEnd ()
	{
		return end;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd (GregorianCalendar end)
	{
		this.end = end;
	}

	/**
	 * @return the devision
	 */
	public DIVISION getDevision ()
	{
		return devision;
	}

	/**
	 * @param devision the devision to set
	 */
	public void setDevision (DIVISION devision)
	{
		this.devision = devision;
	}
}

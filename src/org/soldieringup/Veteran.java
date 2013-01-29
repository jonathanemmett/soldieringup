package org.soldieringup;

/**
 * Class to represent 
 * @author Jake
 *
 */

public class Veteran 
{
	private long vid;
	private String goal;
	
	/**
	 * Constructor
	 */
	public Veteran()
	{
		
	}
	
	/**
	 * Gets the id of the veteran
	 * @return Veteran id
	 */
	public long getVid()
	{
		return vid;
	}
	
	/**
	 * Gets the veteran's goal
	 * @return The goal of the veteran
	 */
	public String getGoal()
	{
		return goal;
	}
	
	/**
	 * Sets the id for this veteran
	 * @param vid ID of the veteran
	 */
	public void setVid( long vid )
	{
		this.vid = vid;
	}
	
	/**
	 * Sets the veteran's goal
	 * @param goal Goal for the veteran
	 */
	public void setGoal( String goal )
	{
		this.goal = goal;
	}
}

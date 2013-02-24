package org.soldieringup;

import java.sql.Date;

/**
 * Class to represent 
 * @author Jake
 *
 */

public class Veteran 
{
	public static enum VeteranDatabaseColumns
	{
		UID (0),
		GOAL (1),
		VID (2),
		PROFILE_SRC (3);
		
		int mDatabaseTableIndex;
		
		private VeteranDatabaseColumns( int aDatabaseTableIndex )
		{
			this.mDatabaseTableIndex = aDatabaseTableIndex;
		}
		
		public int getDatabaseColumnIndex()
		{
			return mDatabaseTableIndex;
		}
	}
	
	public static String VeteranDatabaseColumnsStrings[] =
		{
		"uid",
		"Goal",
		"vid",
		"profile_src"
		};
	
	private long vid;
	private String goal = null;
	private long uid;
	private String profile_src = null;
	
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
	 * Gets the id of the user account associated with this veteran
	 * @return The id associated with the user account of this veteran.
	 */
	public long getUid()
	{
		return uid;
	}
	
	/**
	 * Gets the src of the user profile picture
	 * @return The src of the user profile picture
	 */
	public String getProfileSrc()
	{
		return profile_src;
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
	
	/**
	 * Sets the user id associated with the veteran profile
	 * @param uid User id of the account associated with this veteran profile
	 */
	public void setUid( long uid)
	{
		this.uid = uid; 	
	}
	
	/**
	 * Sets the src of the profile picture.
	 * @param src Src of the profile file
	 */
	public void setProfileSrc( String src )
	{
		this.profile_src = src;
	}
}

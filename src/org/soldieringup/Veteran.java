package org.soldieringup;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class to represent
 * @author Jake
 *
 */

public class Veteran extends SoldierUpAccount
{
	public static enum VeteranDatabaseColumns
	{
		GOAL (0);

		int mDatabaseTableIndex;

		private VeteranDatabaseColumns( int aDatabaseTableIndex )
		{
			this.mDatabaseTableIndex = aDatabaseTableIndex;
		}

		public int getDatabaseColumnIndex()
		{
			return mDatabaseTableIndex;
		}

		public String getDatabaseColumn()
		{
			return VeteranDatabaseColumnsStrings[mDatabaseTableIndex];
		}
	}

	public static String VeteranDatabaseColumnsStrings[] =
		{
		"goal"
		};

	private String mGoal = null;

	/**
	 * Constructor
	 */
	public Veteran()
	{

	}

	/**
	 * Initializes the Veteran with the given SQL result set
	 * @param aVeteranResultSet The result set to initialize the veteran with
	 */
	@Override
	public void init( ResultSet aVeteranResultSet ) throws SQLException
	{
		super.init( aVeteranResultSet );

		mGoal = aVeteranResultSet.getString( VeteranDatabaseColumns.GOAL.getDatabaseColumn() );
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
	 * Makes that a given key and it's associated value are valid inputs
	 * for the database
	 * @param aKey Key to check
	 * @param aValue Value associated to the key
	 * @return True if the input is valid for the database, false otherwise
	 */
	public static boolean isValidDatabaseInput( String aKey, String aValue )
	{
		if( Utilities.isElementInArray( aKey, VeteranDatabaseColumnsStrings) )
		{
			return aValue != null;
		}

		return false;
	}
}

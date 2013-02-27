package org.soldieringup;

import org.soldieringup.Business.BusinessDatabaseColumns;

/**
 * Class represents a user in the database 
 * @author Jake
 *
 */
public class User 
{
	public static enum UserDatabaseColumns
	{
		ID (0),
		FIRST_NAME (1),
		LAST_NAME (2),
		EMAIL (3),
		ADDRESS (4),
		PRIMARY_NUMBER (5),
		SECONDARY_NUMBER (6),
		PASSWORD (7),
		SALT (8),
		ZIP (9);
		
		int mDatabaseTableIndex;
		
		private UserDatabaseColumns( int aDatabaseTableIndex )
		{
			this.mDatabaseTableIndex = aDatabaseTableIndex;
		}
		
		public int getDatabaseColumnIndex()
		{
			return mDatabaseTableIndex;
		}
	}
	
	public static String UserDatabaseColumnsStrings[] =
		{
		"id",
		"first_name",
		"last_name",
		"email",
		"address",
		"primary_number",
		"secondary_number",
		"password",
		"salt",
		"ZIP"
		};
	
	private long id;
	private String first_name;
	private String last_name;
	private String email;
	private String address;
	private String primary_number;
	private String secondary_number;
	private String zip;
	
	public User()
	{
		
	}
	
	public long getId()
	{
		return id;
	}
	
	public String getFirstName()
	{
		return first_name;
	}
	
	public String getLastName()
	{
		return last_name;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public String getAddress()
	{
		return address;
	}
	
	public String getPrimaryNumber()
	{
		return primary_number;
	}
	
	public String getSecondaryNumber()
	{
		return secondary_number;
	}
	
	public String getZip()
	{
		return zip;
	}
	
	public void setId( long id )
	{
		this.id = id;
	}
	
	public void setFirstName( String first_name )
	{
		this.first_name = first_name;
	}
	
	public void setLastName( String last_name )
	{
		this.last_name = last_name;
	}
	
	public void setEmail( String email )
	{
		this.email = email;
	}
	
	public void setAddress( String address )
	{
		this.address = address;
	}
	
	public void setPrimaryNumber( String primary_number )
	{
		this.primary_number = primary_number;
	}
	
	public void setSecondaryNumber( String secondary_number )
	{
		this.secondary_number = secondary_number;
	}
	
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
		if( Utilities.isElementInArray( aKey, UserDatabaseColumnsStrings) )
		{
			if( aKey == UserDatabaseColumnsStrings[UserDatabaseColumns.ZIP.getDatabaseColumnIndex()] ||
				aKey == UserDatabaseColumnsStrings[UserDatabaseColumns.PRIMARY_NUMBER.getDatabaseColumnIndex()] ||
				aKey == UserDatabaseColumnsStrings[UserDatabaseColumns.SECONDARY_NUMBER.getDatabaseColumnIndex()])
			{
				return Utilities.stringIsNumeric( aValue );
			}
			else
			{
				return aValue != null;
			}
		}
		return false;
	}
}

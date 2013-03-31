package org.soldieringup;



/**
 * Class represents a registered business
 * @author Jake
 *
 */
public class Business extends SoldierUpAccount {

	private long bid;

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

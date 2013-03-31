package org.soldieringup;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;



/**
 * Class represents a registered business
 * @author Jake
 *
 */
public class Business extends SoldierUpAccount {

	private long bid;
	private String name;
	@DBRef
	private List<Tag> tag;

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
	 * @return the tag
	 */
	public List<Tag> getTag ()
	{
		return tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag (List<Tag> tag)
	{
		this.tag = tag;
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

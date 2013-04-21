package org.soldieringup;

import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * Class represents a user in the database
 * @author Jake
 *
 */
public class User extends SoldierUpAccount
{
	private String first_name;
	private String last_name;
	private long salt;
	private String password;
	private Business business;
	private Veteran veteran;
	@DBRef
	private War war;

	public String getFirstName()
	{
		return first_name;
	}

	public String getLastName()
	{
		return last_name;
	}

	public void setFirstName( String first_name )
	{
		this.first_name = first_name;
	}

	public void setLastName( String last_name )
	{
		this.last_name = last_name;
	}

	/**
	 * Gets the salt for the account password
	 * @return The salt for the account password
	 */
	public long getSalt()
	{
		return this.salt;
	}

	/**
	 * Gets the password for the account
	 * @return The password for the account
	 */
	public String getPassword()
	{
		return this.password;
	}

	/**
	 * Sets the salt for the account password
	 * @param salt The salt for the account password
	 */
	public void setSalt( long salt )
	{
		this.salt = salt;
	}

	/**
	 * Sets the password for the account
	 * @param The password for the account
	 */
	public void setPassword( String password )
	{
		this.password = password;
	}

	/**
	 * @return the business
	 */
	public Business getBusiness ()
	{
		return business;
	}

	/**
	 * @param business the business to set
	 */
	public void setBusiness (Business business)
	{
		this.business = business;
	}

	/**
	 * @return the veteran
	 */
	public Veteran getVeteran ()
	{
		return veteran;
	}

	/**
	 * @return the war
	 */
	public War getWar ()
	{
		return war;
	}

	/**
	 * @param war the war to set
	 */
	public void setWar (War war)
	{
		this.war = war;
	}

	/**
	 * @param veteran the veteran to set
	 */
	public void setVeteran (Veteran veteran)
	{
		this.veteran = veteran;
	}

	@Override
	public String toString ()
	{
		return "User [id=" + id + ", " + "first_name=" + first_name + ", " + "last_name=" + last_name + ", " + "address=" + address + ", " + "zip=" + zip + "]";
	}
}

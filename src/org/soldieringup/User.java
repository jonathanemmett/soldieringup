package org.soldieringup;

/**
 * Class represents a user in the database
 * @author Jake
 *
 */
public class User extends SoldierUpAccount
{
	private String first_name;
	private String last_name;
	private Business business;
	private Veteran veteran;

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

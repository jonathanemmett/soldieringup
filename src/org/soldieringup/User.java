package org.soldieringup;

public class User 
{
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
}

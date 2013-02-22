package org.soldieringup;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.soldieringup.database.MySQL;

public class ZIP 
{
	private String zip;
	private String city;
	private String state;
	private double latitude;
	private double longtitude;
	
	public ZIP()
	{
		
	}
	
	public void setZip( String zip )
	{
		this.zip = zip;
	}
	
	public void setCity( String city )
	{
		this.city = city;
	}
	
	public void setState( String state )
	{
		this.state = state;
	}
	
	public void setLatitude( double latitude )
	{
		this.latitude = latitude;
	}
	
	public void setLongtitude( double longtitude )
	{
		this.longtitude = longtitude;
	}
	
	public String getZip()
	{
		return zip;
	}
	
	public String getCity()
	{
		return city;
	}
	
	public String getState()
	{
		return state;
	}
	
	public double getLatitude()
	{
		return latitude;
	}
	
	public double getLongtitude()
	{
		return longtitude;
	}
}

package org.soldieringup.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ZIP 
{
	private String zip;
	private String city;
	private String state;
	private double latitude;
	private double longtitude;
	
	public static void insertZip( String ZIP, String City, String State )
	{
		MySQL connection = MySQL.getInstance();
		String query = "INSERT INTO ZIP VALUES( ZIP, City, State) ";
		query += "VALUES(?,?,?)";
		PreparedStatement stmt;
		try {
			stmt = connection.getPreparedStatement( query );
			stmt.setString( 1, ZIP );
			stmt.setString( 2, City );
			stmt.setString( 3, State );
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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

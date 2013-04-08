package org.soldieringup;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

public class ZIP
{
	@Id
	protected ObjectId id;
	@Indexed
	private String zip;
	private String city;
	@Indexed
	private String state;
	private double latitude;
	private double longtitude;

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

	@Override
	public String toString() {
		return "ZIP [id=" + id + ", city=" + city + ", state=" + state + ", zip=" + zip + ", latitude=" + latitude + ",logtitude=" + longtitude + "]";
	}
}

package org.soldieringup;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

/**
 * Abstract Class that all accounts inherit from
 *
 */
public abstract class SoldierUpAccount
{
	@Id
	protected ObjectId id;
	protected long mAid;
	protected long mUid;
	protected String mProfileSrc;
	protected Photo profilePhoto;
	protected List<Photo> photos;
	protected String cover_src;
	protected String name;
	protected String short_summary;
	protected String long_summary;
	protected String work_number;
	protected String address;
	protected String zip;
	protected String primary_number;
	protected String secondary_number;
	protected String email;

	/**
	 * @return the primary_number
	 */
	public String getPrimary_number ()
	{
		return primary_number;
	}

	/**
	 * @param primary_number the primary_number to set
	 */
	public void setPrimary_number (String primary_number)
	{
		this.primary_number = primary_number;
	}

	/**
	 * @return the secondary_number
	 */
	public String getSecondary_number ()
	{
		return secondary_number;
	}

	/**
	 * @param secondary_number the secondary_number to set
	 */
	public void setSecondary_number (String secondary_number)
	{
		this.secondary_number = secondary_number;
	}

	/**
	 * @return the email
	 */
	public String getEmail ()
	{
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail (String email)
	{
		this.email = email;
	}

	public void setAid( long aAid )
	{
		this.mAid = aAid;
	}

	public void setUid( long aUid )
	{
		this.mUid = aUid;
	}

	public void setProfileSrc( String aProfileSrc )
	{
		this.mProfileSrc = aProfileSrc;
	}

	public long getAid()
	{
		return mAid;
	}

	public long getUid()
	{
		return mUid;
	}

	public String getProfileSrc()
	{
		return mProfileSrc;
	}


	/**
	 * Gets the name
	 * @return The name
	 */
	public String getName ()
	{
		return name;
	}

	/**
	 * Gets the short summary
	 * @return The short summary
	 */
	public String getShortSummary()
	{
		return short_summary;
	}

	/**
	 * Gets the long summary
	 * @return The long summary
	 */
	public String getLongSummary()
	{
		return long_summary;
	}

	/**
	 * Gets the phone number
	 * @return Phone number
	 */
	public String getWorkNumber()
	{
		return work_number;
	}

	/**
	 * Gets the street address
	 * @return Street Address
	 */
	public String getAddress()
	{
		return address;
	}

	/**
	 * Gets the zip code
	 * @return Zip code
	 */
	public String getZip()
	{
		return zip;
	}

	/**
	 * Gets the src of the cover photo
	 * @return The src of the cover photo
	 */
	public String getCoverSrc()
	{
		return cover_src;
	}

	/**
	 * Sets the src of the cover photo
	 * @param cover_src Src of the business cover
	 */
	public void setCoverSrc( String cover_src )
	{
		this.cover_src = cover_src;
	}

	/**
	 * Sets the name
	 * @param name Name
	 */
	public void setName ( String name )
	{
		this.name = name;
	}

	/**
	 * Sets the short summary
	 * @param short_summary Short Summary
	 */
	public void setShortSummary( String short_summary )
	{
		this.short_summary = short_summary;
	}

	public void setLongSummary( String long_summary )
	{
		this.long_summary = long_summary;
	}

	/**
	 * Sets the phone number
	 * @param work_number Phone Number
	 */
	public void setWorkNumber( String work_number )
	{
		this.work_number = work_number;
	}

	/**
	 * Sets the street address
	 * @param Street address
	 */
	public void setAddress( String address )
	{
		this.address = address;
	}

	/**
	 * Sets the zip code
	 * @param zip Zip code
	 */
	public void setZip( String zip )
	{
		this.zip = zip;
	}

	/**
	 * @return the profile_photo
	 */
	public Photo getProfilePhoto ()
	{
		return profilePhoto;
	}

	/**
	 * @param profile_photo the profile_photo to set
	 */
	public void setProfilePhoto (Photo profile_photo)
	{
		this.profilePhoto = profile_photo;
	}

	/**
	 * @return the photos
	 */
	public List<Photo> getPhotos ()
	{
		return photos;
	}

	/**
	 * @param photos the photos to set
	 */
	public void setPhotos (List<Photo> photos)
	{
		this.photos = photos;
	}

	public void addPhoto (Photo photo)
	{
		if (this.photos == null)
			this.photos = new ArrayList<Photo>();
		this.photos.add (photo);
	}
}

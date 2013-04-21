package org.soldieringup;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Abstract Class that all accounts inherit from
 * 
 */
@Document(collection = "user")
public abstract class SoldierUpAccount
{

	public interface IUserService
	{

		public abstract long countAllUsers ();

		public abstract void deleteUser (User user);

		public abstract User findUser (String id);

		public abstract List<?> findAllUsers ();

		public abstract List<?> findUserEntries (int firstResult, int maxResults);

		public abstract User findByUsername (String username);

		public abstract void saveUser (User user);

		public abstract User updateUser (User user);

		public abstract List<?> getUserRoles (String id);

	}

	@Id
	protected ObjectId	id;
	protected long		mAid;
	protected long		mUid;
	protected String	mProfileSrc;
	protected Photo		profilePhoto;
	protected List<Photo>	photos;
	protected String	cover_src;
	protected String	address;
	protected String	zip;
	protected String	primary_number;
	protected String	secondary_number;
	@Indexed
	protected String email;
	@DBRef
	protected List<Tag> tag;

	/**
	 * @return the id
	 */
	public ObjectId getObject_id ()
	{
		return id;
	}

	/**
	 * @return the primary_number
	 */
	public String getPrimary_number ()
	{
		return primary_number;
	}

	/**
	 * @param primary_number
	 *                the primary_number to set
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
	 * @param secondary_number
	 *                the secondary_number to set
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
	 * @param email
	 *                the email to set
	 */
	public void setEmail (String email)
	{
		this.email = email;
	}

	public void setAid (long aAid)
	{
		this.mAid = aAid;
	}

	public void setUid (long aUid)
	{
		this.mUid = aUid;
	}

	public void setProfileSrc (String aProfileSrc)
	{
		this.mProfileSrc = aProfileSrc;
	}

	public long getAid ()
	{
		return mAid;
	}

	public long getUid ()
	{
		return mUid;
	}

	public String getProfileSrc ()
	{
		return mProfileSrc;
	}

	/**
	 * Gets the street address
	 * 
	 * @return Street Address
	 */
	public String getAddress ()
	{
		return address;
	}

	/**
	 * Gets the zip code
	 * 
	 * @return Zip code
	 */
	public String getZip ()
	{
		return zip;
	}

	/**
	 * Gets the src of the cover photo
	 * 
	 * @return The src of the cover photo
	 */
	public String getCoverSrc ()
	{
		return cover_src;
	}

	/**
	 * Sets the src of the cover photo
	 * 
	 * @param cover_src
	 *                Src of the business cover
	 */
	public void setCoverSrc (String cover_src)
	{
		this.cover_src = cover_src;
	}

	/**
	 * Sets the street address
	 * 
	 * @param Street
	 *                address
	 */
	public void setAddress (String address)
	{
		this.address = address;
	}

	/**
	 * Sets the zip code
	 * 
	 * @param zip
	 *                Zip code
	 */
	public void setZip (String zip)
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
	 * @param profile_photo
	 *                the profile_photo to set
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
	 * @param photos
	 *                the photos to set
	 */
	public void setPhotos (List<Photo> photos)
	{
		this.photos = photos;
	}

	public void addPhoto (Photo photo)
	{
		if (this.photos == null)
			this.photos = new ArrayList<Photo> ();
		this.photos.add (photo);
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

}

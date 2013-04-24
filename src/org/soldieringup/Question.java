package org.soldieringup;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * Class the represents a question that a Veteran is asking
 * @author Jake
 *
 */
public class Question
{
	@Id
	protected ObjectId id;
	public String title;
	public String availability;
	public String description;
	@DBRef
	public User user;
	@DBRef
	public List<Tag> tag;

	/**
	 * Constructor for question
	 */
	public Question()
	{

	}

	/**
	 * Sets the title of the question
	 * @param aQuestionTitle The title to set the question to
	 */
	public void setTitle( String aQuestionTitle )
	{
		this.title = aQuestionTitle;
	}

	/**
	 * Sets the string that describes when the Veteran is available
	 * @param aAvailability String of when the veteran is available
	 */
	public void setAvailability( String aAvailability )
	{
		this.availability = aAvailability;
	}

	/**
	 * Sets the veteran that asked the question
	 * @param aVeteran Veteran that asked the question
	 */
	public void setUser( User aUser )
	{
		this.user = aUser;
	}

	/**
	 * 
	 * @param aTags
	 */
	public void setTags( List<Tag> aTags )
	{
		this.tag = aTags;
	}

	/**
	 * Sets the detailed description for the question
	 * @param aDetailedDescription Detailed description to set the question to
	 */
	public void setDetailedDescription( String aDetailedDescription )
	{
		this.description = aDetailedDescription;
	}

	/**
	 * Gets the id of the question
	 * @return The id of the question
	 */
	public ObjectId getID()
	{
		return id;
	}

	/**
	 * Gets the title of the question
	 * @return The title of the question
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * Gets when the veteran is available
	 * @return The string telling when the veteran is available
	 */
	public String getAvailability()
	{
		return availability;
	}

	/**
	 * Gets the veteran that asked the question
	 * @return Veteran that asked the question
	 */
	public User getVeteran()
	{
		return user;
	}

	/**
	 * Gets the tags associated to this question
	 * @return The tags associated to this question
	 */
	public List<Tag> getTags()
	{
		return tag;
	}

	/**
	 * Gets the detailed description of the question
	 * @return The detailed description of the question
	 */
	public String getDescription()
	{
		return description;
	}
}

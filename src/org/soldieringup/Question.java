package org.soldieringup;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;

/**
 * Class the represents a question that a Veteran is asking
 * @author Jake
 *
 */
@Document(collection = "question")
public class Question
{
	@Id
	@Expose
	private ObjectId id;
	@Since(1.0)
	@Expose
	private String title;
	@Expose
	private String availability;
	@Expose
	private String description;
	@Expose
	private Date postedDateTime;
	@Expose
	private String author;
	@DBRef
	@Expose
	private List<Tag> tag;

	/**
	 * Constructor for question
	 */
	public Question ()
	{
		super ();
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

	/**
	 * @return the postedDateTime
	 */
	public Date getPostedDateTime ()
	{
		return postedDateTime;
	}

	/**
	 * @param postedDateTime the postedDateTime to set
	 */
	public void setPostedDateTime (Date postedDateTime)
	{
		this.postedDateTime = postedDateTime;
	}

	/**
	 * @return the poster
	 */
	public String getAuthor ()
	{
		return author;
	}

	/**
	 * @param poster the poster to set
	 */
	public void setAuthor (String author)
	{
		this.author = author;
	}
}

package org.soldieringup;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

/**
 * Class the represents a question that a Veteran is asking
 * @author Jake
 *
 */
public class Question
{
	@Id
	protected ObjectId id;
	public long qid;
	public String question_title;
	public String availability;
	public String question_detailed_description;
	public long vid;

	/**
	 * Constructor for question
	 */
	public Question()
	{

	}

	/**
	 * Sets the MySQL id for the question
	 * @param qid Question id
	 */
	public void setQid( long qid )
	{
		this.qid = qid;
	}
	/**
	 * Sets the title of the question
	 * @param aQuestionTitle The title to set the question to
	 */
	public void setQuestionTitle( String aQuestionTitle )
	{
		this.question_title = aQuestionTitle;
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
	 * Sets the id of the veteran that asked the question
	 * @param aVid The id of the veteran
	 */
	public void setVid( long aVid )
	{
		this.vid = aVid;
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
	 * Sets the MySQL id for the question
	 * @param qid Question id
	 */
	public long getQid()
	{
		return this.qid;
	}

	/**
	 * Gets the title of the question
	 * @return The title of the question
	 */
	public String getQuestionTitle()
	{
		return question_title;
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
	 * Gets the id of the veteran that asked the question
	 * @return The id of the veteran that asked the question
	 */
	public long getVid()
	{
		return vid;
	}

	/**
	 * Gets the detailed description of the question
	 * @return The detailed description of the question
	 */
	public String getDetailedDescription()
	{
		return question_detailed_description;
	}
}

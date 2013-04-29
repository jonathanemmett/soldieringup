package org.soldieringup;

import java.util.GregorianCalendar;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Handles the different messages that a Vet and Ent. might pass between each other.
 * @author jjennings
 *
 */
@Document
public class Message
{
	@Id
	private ObjectId id;
	private ObjectId parentId;
	private String body;
	@DBRef
	private ObjectId authorId;
	private String title;
	private GregorianCalendar date;

	/**
	 * @return the id
	 */
	public ObjectId getId ()
	{
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId (ObjectId id)
	{
		this.id = id;
	}
	/**
	 * @return the parentId
	 */
	public ObjectId getParentId ()
	{
		return parentId;
	}
	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId (ObjectId parentId)
	{
		this.parentId = parentId;
	}
	/**
	 * @return the body
	 */
	public String getBody ()
	{
		return body;
	}
	/**
	 * @param body the body to set
	 */
	public void setBody (String body)
	{
		this.body = body;
	}
	/**
	 * @return the authorId
	 */
	public ObjectId getAuthorId ()
	{
		return authorId;
	}
	/**
	 * @param authorId the authorId to set
	 */
	public void setAuthorId (ObjectId authorId)
	{
		this.authorId = authorId;
	}
	/**
	 * @return the title
	 */
	public String getTitle ()
	{
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle (String title)
	{
		this.title = title;
	}
	/**
	 * @return the date
	 */
	public GregorianCalendar getDate ()
	{
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate (GregorianCalendar date)
	{
		this.date = date;
	}
}

package org.soldieringup;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class the represents a question that a Veteran is asking
 * @author Jake
 *
 */
public class Question 
{
	public static enum QuestionDatabaseColumns
	{
		QID (0),
		QUESTION_TITLE (1),
		AVAILABILITY (2),
		QUESTION_DETAILED_DESCRIPTION (3),
		VID (4);
		
		int mDatabaseTableIndex;
		
		private QuestionDatabaseColumns( int aDatabaseTableIndex )
		{
			this.mDatabaseTableIndex = aDatabaseTableIndex;
		}
		
		public int getDatabaseColumnIndex()
		{
			return mDatabaseTableIndex;
		}
		
		public String getDatabaseColumnString()
		{
			return QuestionDatabaseColumnsStrings[mDatabaseTableIndex];
		}
	}
	
	public static String QuestionDatabaseColumnsStrings[] =
		{
		"qid",
		"question_title",
		"availability",
		"question_detailed_description",
		"vid"
		};

	public Long qid;
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
	 * Sets the question id
	 * @param aQid ID to set the question id to
	 */
	public void setQid( long aQid)
	{
		this.qid = aQid;
	}
	
	/**
	 * Initializes a question from a given database result set
	 * @param aResultSet ResultSet to initialize the object with
	 * @throws SQLException 
	 */
	public void init( ResultSet aResultSet ) throws SQLException
	{
		qid = aResultSet.getLong( QuestionDatabaseColumns.QID.getDatabaseColumnString() );
		question_title = aResultSet.getString( QuestionDatabaseColumns.QUESTION_TITLE.getDatabaseColumnString() );
		availability = aResultSet.getString( QuestionDatabaseColumns.AVAILABILITY.getDatabaseColumnString() );
		question_detailed_description = aResultSet.getString( QuestionDatabaseColumns.QUESTION_DETAILED_DESCRIPTION.getDatabaseColumnString() );
		vid = aResultSet.getLong( QuestionDatabaseColumns.VID.getDatabaseColumnString() );
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
	public long getQid()
	{
		return qid;
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
	
	/**
	 * Makes that a given key and it's associated value are valid inputs
	 * for the database
	 * @param aKey Key to check
	 * @param aValue Value associated to the key
	 * @return True if the input is valid for the database, false otherwise
	 */
	public static boolean isValidDatabaseInput( String aKey, String aValue )
	{
		if( Utilities.isElementInArray( aKey, QuestionDatabaseColumnsStrings) )
		{
			if( aKey == QuestionDatabaseColumns.QID.getDatabaseColumnString() ||
				aKey == QuestionDatabaseColumns.VID.getDatabaseColumnString() )
			{
				return Utilities.stringIsNumeric( aValue );
			}
			else
			{
				return aValue != null;
			}
		}
		return false;
	}
}

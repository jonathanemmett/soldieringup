package org.soldieringup;

import java.util.List;

import org.soldieringup.database.MeetingRequestRepository;
import org.soldieringup.database.QuestionRepository;
import org.soldieringup.database.SoldierUpAccountRepository;
import org.soldieringup.database.TagRepository;
import org.soldieringup.database.UserRepository;
import org.soldieringup.database.ZipRepository;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MongoEngine
{
	ConfigurableApplicationContext context;
	UserRepository userRepository;
	SoldierUpAccountRepository accountRepository;
	ZipRepository zipRepository;
	TagRepository tagRepository;
	QuestionRepository questionRepository;
	MeetingRequestRepository meetingRepository;

	public MongoEngine()
	{
		context = new ClassPathXmlApplicationContext("org/soldieringup/mongo-config.xml");
		userRepository = context.getBean(UserRepository.class);
		accountRepository = context.getBean(SoldierUpAccountRepository.class);
		zipRepository = context.getBean(ZipRepository.class);
		tagRepository = context.getBean(TagRepository.class);
		questionRepository = context.getBean(QuestionRepository.class);
		meetingRepository = context.getBean(MeetingRequestRepository.class);
	}

	public void insertUser( User aUser )
	{
		userRepository.insert( aUser );
	}

	public void updateUser( User account )
	{
		userRepository.update( account );
	}

	public List<User> findUsers( String aFieldName, Object aFieldValue )
	{
		return userRepository.find( aFieldName, aFieldValue );
	}

	public void insertAccount( SoldierUpAccount aBusiness )
	{
		accountRepository.insert( aBusiness );
	}

	public void updateAccount( SoldierUpAccount aBusiness )
	{
		accountRepository.update( aBusiness );
	}

	public List<SoldierUpAccount> findAccounts( String aFieldName, Object aFieldValue )
	{
		return accountRepository.findAccounts(aFieldName, aFieldValue);
	}

	public List<Business> findBusinessesFromTags( List<Tag> aTags )
	{
		return accountRepository.findBusinessesFromTags( aTags );
	}

	/**
	 * Checks for an existing e-mail address
	 * @param email_address
	 * @return
	 */
	public boolean emailExists (String email_address)
	{
		return userRepository.emailExists( email_address );
	}

	/**
	 * Gets an account from a given login
	 * @param aEmail Email of the account
	 * @param aPassword Password of the account
	 * @return User account associated with the login if valid
	 */
	public User getAccountFromLogin( String aEmail, String aPassword )
	{
		try
		{
			return userRepository.validateUser( aEmail, aPassword );
		}
		catch (Exception e)
		{
			return null;
		}
	}

	/**
	 * Inserts a zip into the database
	 * @param aZip Zip to insert
	 */
	public void insertZip( ZIP aZip )
	{
		zipRepository.insert( aZip );
	}

	/**
	 * Gets the ZIP associated to a given zipcode
	 * @param aZipCode Zip code of the ZIP to find
	 * @return The ZIP from the zip code if found, false otherwise
	 */
	public ZIP findZip( String aZipCode )
	{
		List<ZIP> possibleZipCodes = zipRepository.find( "zip", aZipCode );
		if( possibleZipCodes.size() > 0 )
		{
			return possibleZipCodes.get( 0 );
		}
		else
		{
			return null;
		}
	}

	/**
	 * Updates a given ZIP in the database
	 * @param aZip ZIP to update
	 */
	public void updateZip( ZIP aZip )
	{
		zipRepository.update(aZip);
	}

	/**
	 * Inserts a tag into the database
	 * @param aTag
	 */
	public void insertTag( Tag aTag )
	{
		tagRepository.insert( aTag );
	}

	/**
	 * Finds a tag from a given field name and value
	 * @param aFieldName Name of the field to search
	 * @param aFieldValue Value of the given field
	 * @return Any tags associated with the given field name and value
	 */
	public List<Tag> findTags( String aFieldName, Object aFieldValue )
	{
		return tagRepository.find( aFieldName, aFieldValue );
	}

	public List<Tag> findSimiliarTags( String aTag )
	{
		return tagRepository.findSimiliarTags( aTag );
	}

	public List<Tag> findSimiliarTagsFromMultipleTags( String[] aTags)
	{
		return tagRepository.findSimilarTagsFromMultipleTags( aTags );
	}

	public List<Question> findQuestions( String aFieldName, Object aFieldValue )
	{
		return questionRepository.find(aFieldName, aFieldValue);
	}

	public void insertQuestion( Question aQuestion )
	{
		questionRepository.insert( aQuestion );
	}

	public void updateQuestion( Question aQuestion )
	{
		questionRepository.update( aQuestion );
	}

	public void removeQuestion( Question aQuestion )
	{
		questionRepository.remove( aQuestion);
	}

	public void insertMeetingRequest( MeetingRequest aRequest )
	{
		meetingRepository.insert( aRequest );
	}

	public void updateMeetingRequest( MeetingRequest aRequest )
	{
		meetingRepository.update( aRequest );
	}

	public void removeMeetingRequest( MeetingRequest aRequest )
	{
		meetingRepository.remove( aRequest );
	}

	public List<MeetingRequest> findMeetingRequest( String aFieldName, Object aFieldValue )
	{
		return meetingRepository.find( aFieldName, aFieldValue );
	}
}

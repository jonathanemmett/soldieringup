package org.soldieringup.service;

import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.soldieringup.Business;
import org.soldieringup.MeetingRequest;
import org.soldieringup.Question;
import org.soldieringup.Role;
import org.soldieringup.Tag;
import org.soldieringup.User;
import org.soldieringup.Zip;
import org.soldieringup.database.BusinessRepository;
import org.soldieringup.database.MeetingRequestRepository;
import org.soldieringup.database.QuestionRepository;
import org.soldieringup.database.RoleRepository;
import org.soldieringup.database.TagRepository;
import org.soldieringup.database.UserRepository;
import org.soldieringup.database.ZipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MongoEngine
{
	@Autowired
	private BusinessRepository businessRepository;
	@Autowired
	private ZipRepository zipRepository;
	@Autowired
	private TagRepository tagRepository;
	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private MeetingRequestRepository meetingRepository;
	@Autowired
	private UserRepository	userRepository;
	@Autowired
	private RoleRepository	roleRepository;

	public void insertUser( User aUser )
	{
		userRepository.save ( aUser );
	}

	public void updateUser( User account )
	{
		userRepository.save ( account );
	}

	public void savebusiness( Business aBusiness )
	{
		businessRepository.save ( aBusiness );
	}

	public void updateAccount( Business aBusiness )
	{
		businessRepository.save ( aBusiness );
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
	 * Inserts a zip into the database
	 * @param aZip Zip to insert
	 */
	public void insertZip( Zip aZip )
	{
		zipRepository.save ( aZip );
	}

	/**
	 * Gets the Zip associated to a given zipcode
	 * @param aZipCode Zip code of the Zip to find
	 * @return The Zip from the zip code if found, false otherwise
	 */
	public Zip findZip( String zip )
	{
		return zipRepository.findOne( zip );
	}

	/**
	 * Updates a given Zip in the database
	 * @param aZip Zip to update
	 */
	public void updateZip( Zip aZip )
	{
		zipRepository.save (aZip);
	}

	/**
	 * Inserts a tag into the database
	 * @param aTag
	 */
	public void insertTag( Tag aTag )
	{
		tagRepository.save ( aTag );
	}

	/**
	 * Finds a tag from a given field name and value
	 * @param aFieldName Name of the field to search
	 * @param aFieldValue Value of the given field
	 * @return Any tags associated with the given field name and value
	 */
	public List<Tag> findTags( String aFieldName, Object aFieldValue )
	{
		//return tagRepository.find( aFieldName, aFieldValue );
		throw new NotImplementedException ();
	}

	public List<Tag> findSimiliarTags( String aTag )
	{
		//return tagRepository.findSimiliarTags( aTag );
		throw new NotImplementedException ();
	}

	public List<Tag> findSimiliarTagsFromMultipleTags( String[] aTags)
	{
		throw new NotImplementedException ();
	}

	public List<Question> findQuestions( String aFieldName, Object aFieldValue )
	{
		//return questionRepository.find(aFieldName, aFieldValue);
		throw new NotImplementedException ();
	}

	public void insertQuestion( Question aQuestion )
	{
		questionRepository.save ( aQuestion );
	}

	public void updateQuestion( Question aQuestion )
	{
		questionRepository.save ( aQuestion );
	}

	public void removeQuestion( Question aQuestion )
	{
		questionRepository.delete ( aQuestion);
	}

	public void insertMeetingRequest( MeetingRequest aRequest )
	{
		meetingRepository.save ( aRequest );
	}

	public void updateMeetingRequest( MeetingRequest aRequest )
	{
		meetingRepository.save ( aRequest );
	}

	public void removeMeetingRequest( MeetingRequest aRequest )
	{
		meetingRepository.delete ( aRequest );
	}

	public List<MeetingRequest> findMeetingRequest( String aFieldName, Object aFieldValue )
	{
		//return meetingRepository.find( aFieldName, aFieldValue );
		throw new NotImplementedException ();
	}

	public List<Question> getTopQuestions ()
	{
		Pageable p = new PageRequest(0, 20);
		Page<Question> page = questionRepository.findAll (p);
		List<Question> q = page.getContent ();
		return q;
	}

	public Role getRole (String role)
	{
		return roleRepository.findOne(role);
	}

	public org.soldieringup.User getByEmailAndPassword (String username, String encodePassword)
	{
		return userRepository.findByUsernameAndPassword (username, encodePassword);
	}
}

package org.soldieringup.database;

import java.util.ArrayList;

/**
 * Class that makes sure a password satisfies certain requirements. For this class, requirements
 * include minimum number of required digits, minimum number of alphabetical characters, and 
 * whether the password contains mixed case. To set these, just call the appropriate setter
 * function with the desired value.
 * @author Jake
 *
 */
public class PasswordValidator
{
	private int mRequiredNumberOfDigits = 0;
	private int mRequiredNumberOfAlphabeticalCharacters = 0;
	private boolean mMixedCaseAlphabetRequired = false;
	private ArrayList<String> mPasswordValidationErrors;
	
	/**
	 * Class constructor
	 */
	public PasswordValidator()
	{
		mPasswordValidationErrors = new ArrayList<String>();
	}
	
	/**
	 * Sets the minimun number of required digits in the password
	 * @param aRequiredNumberOfDigits Minimun number of required digits in the password
	 */
	public void setNumberOfRequiredDigits( int aRequiredNumberOfDigits)
	{
		this.mRequiredNumberOfDigits = aRequiredNumberOfDigits;
	}

	/**
	 * Sets the minimun number of required alphabetical characters in the password
	 * @param aRequiredNumberOfAlphabeticalCharacters Minimun number of required alphabetical characters in the password
	 */
	public void setNumberOfRequiredAlphabeticalCharacters( int aRequiredNumberOfAlphabeticalCharacters )
	{
		this.mRequiredNumberOfAlphabeticalCharacters = aRequiredNumberOfAlphabeticalCharacters;
	}
	
	/**
	 * Sets whether the password has to have both uppercase and lowercase
 	 * @param aMixedCaseAlphabetRequired Whether the password must have both upper and lowercase letters
	 */
	public void setMixedCaseAlphabetRequired( boolean aMixedCaseAlphabetRequired )
	{
		this.mMixedCaseAlphabetRequired = aMixedCaseAlphabetRequired;
	}
	
	/**
	 * Checks to make sure that a password is valid
	 * @param aPassword The password to validate
	 * @return True if no errors around while validationg the password, false otherwise.
	 */
	public boolean validatePassword( String aPassword )
	{
		// Clear the errors before checking for any
		mPasswordValidationErrors.clear();
		
		
		// Check to make sure we have the required amount of digits.
		if( !hasRequiredNumberOfDigits( aPassword ) )
		{
			mPasswordValidationErrors.add( "The password must contain at least " + mRequiredNumberOfDigits + " digits." );
		}

		// Check to make sure the password has the required amount of alphabetical characters.
		if( !hasRequiredNumberOfAlphabeticalCharacters( aPassword ) )
		{
			mPasswordValidationErrors.add( "The password must contain at least " + mRequiredNumberOfAlphabeticalCharacters + " alphabetical characters.");
		}

		// Check to make sure the password is has mixed case if it's a password requirement.
		if( mMixedCaseAlphabetRequired && aPassword.toUpperCase().equals( aPassword ) && aPassword.toLowerCase().equals( aPassword ) )
		{
			mPasswordValidationErrors.add( "The password must contain both upper and low case characters " + aPassword );
		}
	
		return mPasswordValidationErrors.isEmpty();
	}

	/**
	 * Determines if a given password has the required minimum number of alphabetical characters  
	 * @param aPassword The password to check
	 * @return True if the password has the required minimum number of alphabetical
	 * 		   characters, false otherwise. 
	 */
	private boolean hasRequiredNumberOfAlphabeticalCharacters( String aPassword )
	{
		int numberOfAlphabeticalCharacters = 0;
		
		for( int i = 0; i < aPassword.length(); ++i )
		{
			if( Character.isAlphabetic( aPassword.charAt( i ) ) )
			{
				numberOfAlphabeticalCharacters++;
			}
		}
		
		return numberOfAlphabeticalCharacters >= this.mRequiredNumberOfAlphabeticalCharacters; 
	}
	
	/**
	 * Determines if a given password has the required minimum number of digits  
	 * @param aPassword The password to check
	 * @return True if the password has the required minimum number of digits, false otherwise. 
	 */
	private boolean hasRequiredNumberOfDigits( String aPassword )
	{
		int numberOfDigits = 0;
		
		for( int i = 0; i < aPassword.length(); ++i )
		{
			if( Character.isDefined( aPassword.charAt( i ) ) )
			{
				numberOfDigits++;
			}
		}
		
		return numberOfDigits >= this.mRequiredNumberOfDigits;
	}
	
	/**
	 * Get the errors that resulted from validating the password
	 * @return The errors that occurred while validating the password.
	 */
	public ArrayList<String> getValidationErrors()
	{
		return mPasswordValidationErrors;
	}
	
}

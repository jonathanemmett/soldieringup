package org.soldieringup.database;

import java.util.ArrayList;

/**
 * Class that makes sure a password satisfies certain requirements. For this class, requirements
 * include minimum password length, minimum number of required digits, minimum number of alphabetical
 * characters, and whether the password contains mixed case. To set these, just call the 
 * appropriate setter function with the desired value.
 * @author Jake
 *
 */
public class PasswordValidator
{
	private int mRequiredMinimumPasswordLength = 0;
	private int mRequiredNumberOfDigits = 0;
	private int mRequiredNumberOfAlphabeticalCharacters = 0;
	private int mRequiredNumberOfSpecialCharacters = 0;
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
	 * Sets the minimum length the password needs to be
	 * @param aRequiredMinimumPasswordLength Minimum length the password needs to be
	 */
	public void setRequiredMinimumPasswordLength( int aRequiredMinimumPasswordLength )
	{
		mRequiredMinimumPasswordLength = aRequiredMinimumPasswordLength;
	}
	
	/**
	 * Sets the minimun number of required digits in the password
	 * @param aRequiredNumberOfDigits Minimun number of required digits in the password
	 */
	public void setNumberOfRequiredDigits( int aRequiredNumberOfDigits)
	{
		mRequiredNumberOfDigits = aRequiredNumberOfDigits;
	}

	/**
	 * Sets the minimun number of required alphabetical characters in the password
	 * @param aRequiredNumberOfAlphabeticalCharacters Minimun number of required alphabetical characters in the password
	 */
	public void setNumberOfRequiredAlphabeticalCharacters( int aRequiredNumberOfAlphabeticalCharacters )
	{
		mRequiredNumberOfAlphabeticalCharacters = aRequiredNumberOfAlphabeticalCharacters;
	}
	
	/**
	 * Sets the minimun number of required special characters in the password. Specia characters include characters
	 * such as ?, @, ", and &.
	 * @param aRequiredNumberOfSpecialCharacters Minimun number of required special characters in the password
	 */
	public void setNumberOfRequiredSpecialCharacters( int aRequiredNumberOfSpecialCharacters )
	{
		mRequiredNumberOfSpecialCharacters = aRequiredNumberOfSpecialCharacters;
	}
	
	/**
	 * Sets whether the password has to have both uppercase and lowercase
 	 * @param aMixedCaseAlphabetRequired Whether the password must have both upper and lowercase letters
	 */
	public void setMixedCaseAlphabetRequired( boolean aMixedCaseAlphabetRequired )
	{
		mMixedCaseAlphabetRequired = aMixedCaseAlphabetRequired;
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
		
		//Check to make sure the password is of the correct size.
		if( aPassword.length() < mRequiredMinimumPasswordLength )
		{
			mPasswordValidationErrors.add( "The password must be at least " + mRequiredMinimumPasswordLength + " characters long." );
		}
		
		// Check to make sure we have the required amount of digits.
		if( aPassword.matches( "[0-9]{" + mRequiredNumberOfDigits + "}" ) )
		{
			mPasswordValidationErrors.add( "The password must contain at least " + mRequiredNumberOfDigits + " digits." );
		}
		
		// Check to make sure the password has the required amount of alphabetical characters.
		if( aPassword.matches( "[a-zA-Z]{"+mRequiredNumberOfAlphabeticalCharacters+"}" ) )
		{
			mPasswordValidationErrors.add( "The password must contain at least " + mRequiredNumberOfAlphabeticalCharacters + " alphabetical characters.");
		}
		
		// Check to make sure the password has the required amount of special characters.
		if( aPassword.matches( "[!@#$%^&&*()_+='\"{}\\]{" + mRequiredNumberOfSpecialCharacters + "}" ) )
		{
			mPasswordValidationErrors.add( "The password must contain at least " + mRequiredNumberOfSpecialCharacters + " special characters.");
		}
		
		// Check to make sure the password is has mixed case if it's a password requirement.
		if( mMixedCaseAlphabetRequired && aPassword.toUpperCase().equals( aPassword ) )
		{
			mPasswordValidationErrors.add( "The password must contain both upper and lower case characters" );
		}
		
		return mPasswordValidationErrors.isEmpty();
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

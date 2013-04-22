package org.soldieringup;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

/**
 * Class represents various functions that are used throughout the web application
 * @author Jake LaCombe
 */
public class Utilities {

	/**
	 * Takes in a string, and returns the hashed version of the string when the Sha-1 algorithm is added to it
	 * @param aString The string to run the Sha-1 algorithm with
	 * @return The hashed version of the passed in string
	 * @throws NoSuchAlgorithmException System does not have the Sha-1 algorithm
	 * @throws UnsupportedEncodingException does not support UTF-8 encoding
	 */
	public static String sha1Output( String aString)
	{
		try
		{
			MessageDigest md = null;
			md = MessageDigest.getInstance("SHA-1");
			return byteArrayToHexString( md.digest( aString.getBytes( "UTF-8") ) );
		}
		catch (NoSuchAlgorithmException e)
		{
			return aString;
		}
		catch (UnsupportedEncodingException e)
		{
			return aString;
		}
	}

	/**
	 * Takes in an array of bytes, and converts it to its proper hexadecimal string
	 * @param aBytes The bytes array to convert to a string
	 * @return The converted hexadecimal string
	 */
	public static String byteArrayToHexString(byte[] aBytes) {
		String result = "";
		for (int i=0; i < aBytes.length; i++) {
			result += Integer.toString( ( aBytes[i] & 0xff ) + 0x100, 16).substring( 1 );
		}
		return result;
	}

	/**
	 * Determine if a given string is a numeric string
	 * @param aString String to check
	 * @return True if the inputed string is a number, false otherwise
	 */
	public static boolean stringIsNumeric(String aString)
	{
		for( int i = 0; i < aString.length(); ++i )
		{
			if( !Character.isDigit( aString.charAt( i ) ) )
			{
				return false;
			}
		}

		return true;
	}

	/**
	 * Determines if a given string from a request with a given key is null. If it is, we add an error to the error map
	 * @param aString Key to check
	 * @param request HTTP request
	 * @param aErrorMap Error mapping for request
	 */
	public static void checkParameterIsNull(String aString, HttpServletRequest request, Map<String,String> aErrorMap )
	{
		if( request.getParameter(aString) == null || request.getParameter( aString ).isEmpty() )
		{
			aErrorMap.put(aString, "required" );
		}
	}

	/**
	 * Prints out a error span if the given key has an error
	 * @param aWriter JspWriter that we will output the error to
	 * @param aKey The key used to search for an error.
	 */
	public static void printErrorSpan( JspWriter aWriter, String aKey, Map<String,String> aErrors )
	{
		try
		{
			if( aErrors != null && aErrors.containsKey( aKey ) )
			{
				aWriter.print("<span class=\"form_error\">"+aErrors.get( aKey ) + "</span>");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static String getValueFromString( String value )
	{
		return value == null ? "" : value;
	}

	/**
-	 * Checks to see if a given element is in an array
	 * @param aElement Element to check
	 * @param aArray Array to search
	 * @return True if the given element exists in the given array,
	 * 		   false otherwise.
	 */
	public static boolean isElementInArray( String aElement, String[] aArray )
	{
		for( int i = 0; i < aArray.length; ++i )
		{
			if( aArray[i].equals( aElement ) )
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * Generates a password from a given set of inputs.
	 * 
	 * This password returns a generated string in clear text. Make sure to encrypt
	 * the password before inserting it into the database.
	 * @param numberOfDigits The number of digits the password must have
	 * @param numberOfAlphabeticalCharacters The number of alphabetical characters the password must have
	 * @param numberOfSpecialCharacters The number of special characters the password must have. Special characters
	 * 		  are characters such as @,!,$,%.
	 * @return The generated characters.
	 */
	public static String generatePassword( int numberOfDigits, int numberOfAlphabeticalCharacters, int numberOfSpecialCharacters)
	{
		int passwordLength = numberOfDigits + numberOfAlphabeticalCharacters + numberOfSpecialCharacters;
		char password[] = new char[passwordLength];
		int currentPasswordIndex = 0;

		String letters = "abcdefghijklmnopqrstuvwxyz";
		String digits = "0123456789";
		String specialCharacters = "!@#$%^&&*()_+='\"{}\\";

		for( int i = 0; i < numberOfDigits; ++i )
		{
			password[currentPasswordIndex++] = digits.charAt( (int)( Math.random() * digits.length() ) );
		}

		for( int i = 0; i < numberOfAlphabeticalCharacters; ++i )
		{
			password[currentPasswordIndex++] = letters.charAt( (int)( Math.random() * letters.length() ) );
		}

		for( int i = 0; i < numberOfSpecialCharacters; ++i )
		{
			password[currentPasswordIndex++] = specialCharacters.charAt( (int)( Math.random() * specialCharacters.length() ) );
		}

		// Scramble the password
		for( int i = 0; i < password.length; ++i )
		{
			int randIndex = (int)( Math.random() * password.length );

			char tempChar = password[i];
			password[i] = password[randIndex];
			password[randIndex] = tempChar;
		}

		return new String( password );
	}

	/**
	 * If a value is null or has a length of zero, returns true.
	 * Otherwise returns false.
	 * 
	 * @param obj
	 * @return boolean
	 */
	public static boolean isNullOrEmpty (Object obj)
	{
		if (obj == null)
			return true;
		if (((String)obj).length () == 0)
			return true;
		return false;
	}
}

package org.soldieringup;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
	public static String sha1Output( String aString) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		MessageDigest md = null;
		md = MessageDigest.getInstance("SHA-1");
		return byteArrayToHexString( md.digest( aString.getBytes( "UTF-8") ) );
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
}

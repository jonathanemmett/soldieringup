package org.soldieringup;

import java.io.IOException;
import java.io.PrintWriter;
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
}

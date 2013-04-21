/**
 * 
 */
package org.soldieringup.controllers;

import com.google.gson.Gson;


/**
 * Base Controller that either BaseProtJSONController or BasePubJSONController inherits from
 * @author jjennings
 * @created April 20, 2013
 */
public abstract class BaseJSONController
{

	/**
	 * Returns a string representation of a JSON object of whatever Class object that is passed in.
	 * 
	 * @author jjennings
	 * @param Object obj
	 * @return String JSON object
	 */
	protected String toJson (Object obj)
	{
		Gson gson = new Gson ();
		String result = "";
		result = gson.toJson (obj);
		return result;
	}
}

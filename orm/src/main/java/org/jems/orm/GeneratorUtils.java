/************************************************************/
/*
	File: GeneratorUtils.java

	Generator Utilities

	Author: James Cowan
*/
/************************************************************/

package org.jems.orm;

/************************************************************/

public class GeneratorUtils
{
	/************************************************************/
	// public methods
	/************************************************************/
	/** getSimpleClassName */

	public String getSimpleClassName(String className)
	{
	int index = className.lastIndexOf(".");
	
		if (index < 0)
		{
			return className;
		}
		
		return className.substring(index+1);
		
	} // getSimpleClassName
	
	/************************************************************/
	/** getPackageName */

	public String getPackageName(String className)
	{
	int index = className.lastIndexOf(".");
	
		if (index < 0)
		{
			return className;
		}
		
		return className.substring(0, index);
		
	} // getPackageName
	
}  // GeneratorUtils

/*******************************************************************/
/*
	File: GeneratorException.java

	Author: James Cowan
*/
/*******************************************************************/

package org.jems.generator;

/*******************************************************************/

public class GeneratorException extends Exception
{
	private static final long serialVersionUID = 1L;

	public GeneratorException(String msg)
	{
		this(msg, true);
	}

	public GeneratorException(String msg, boolean print)
	{
		super(msg);
		
		if (print)
			System.out.println(msg);
	}
} // GeneratorException

/*******************************************************************/


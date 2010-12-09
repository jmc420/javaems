/*******************************************************************/
/*
	File: IGenerator.java

	Author: James Cowan
*/
/*******************************************************************/

package org.jems.generator;

import java.io.IOException;
import java.io.PrintStream;

/*******************************************************************/
/** IGenerator */

public interface IGenerator
{
	/************************************************************/
	/** generate */

	public void generate(PrintStream out) throws IOException, GeneratorException;

} // IGenerator

/*******************************************************************/


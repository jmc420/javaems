/************************************************************/
/*
	File: TestAS3Compiler.java

*/
/************************************************************/

package org.jems.compiler.test;

import java.io.File;

import junit.framework.TestCase;

import org.jems.compiler.JavaToAS3Compiler;

public class TestAS3Compiler extends TestCase
{	
	
	static final protected String	SCHEMA_DIRECTORY = "src/test/schema/";
	static final protected String	SCHEMA_FILE = "ClassCompiler.xml";
	static final protected String	OUTPUT_DIRECTORY = "target/generated";
	
	/************************************************************/
	// public methods
	/************************************************************/
	/** testCompiler */
	
	public void testCompiler()throws Exception
	{
	File schemaDir = new File(SCHEMA_DIRECTORY);
	File schemaFile = new File(SCHEMA_DIRECTORY, SCHEMA_FILE);
	File outputDir = new File(OUTPUT_DIRECTORY);
	JavaToAS3Compiler compiler = new JavaToAS3Compiler(schemaDir, schemaFile, outputDir);
		
		compiler.generate(System.out);
		
    } // testCompiler
	
	/************************************************************/
	// protected methods
	/************************************************************/
	
} // TestAS3Compiler

/************************************************************/
// End of class
/************************************************************/

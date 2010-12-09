/*******************************************************************/
/*
	File: Generator.java

	Abstract Generator class which takes a schema input file
	and output file/directory and generates the output.

	Author: James Cowan
*/
/*******************************************************************/

package org.jems.generator;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Constructor;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;

/*******************************************************************/

abstract public class Generator implements FileFilter, IGenerator
{
	static protected File		m_schemaDirectory;
	static protected File		m_schemaFile;
	static protected File		m_output;

	public Generator(File schemaDirectory, File schemaFile, File output)
	{
		m_schemaDirectory = schemaDirectory;
		m_schemaFile = schemaFile;
		m_output = output;
	}
	
	/*******************************************************************/
	/* public methods */
	/*******************************************************************/
	/** accept */
	
	public boolean accept(File file)
	{
		return true;
		
	} // accept
	
	/************************************************************/
	// static public methods
	/************************************************************/
	/* createPackageDir */
	
	static public File createPackageDir(File baseDir, String packageName) 
	throws IOException, GeneratorException
	{
	String paths[] = packageName.split("\\.");
	File pathDir = baseDir;

		for (int count=0; count<paths.length; count++)
		{
		String path = paths[count];
		
			pathDir = new File(pathDir, path);
			
			FileUtils.forceMkdir(pathDir);
		}
		
		return pathDir;
		
	} // createPackageDir
	
	/*******************************************************************/
	/* getOutput */
	
	static public File getOutput()
	{
		return m_output;
		
	} // getOutput
	
	/*******************************************************************/
	/* getSchemaDirectory */
	
	static public File getSchemaDirectory()
	{
		return m_schemaDirectory;
		
	} // getSchemaDirectory
		
	/*******************************************************************/
	/** main */
	
	public static void main(String argv[]) throws GeneratorException
	{
		if (argv.length < 4)
		{
		String msg = "arguments are generator class, input directory, input schema and output file";
		
			throw new GeneratorException(msg);
		}
		
		try
		{
		Class<?> cl = Class.forName(argv[0]);
		File schemaDirectory = new File(argv[1]);
		String schemaFile = argv[2];
		File output = new File(argv[3]);
		
			if (!isWildCard(schemaFile))
			{			
				generate(cl, schemaDirectory, new File(schemaDirectory, schemaFile), output);
			}
			else
			{
			FileFilter fileFilter = new WildcardFileFilter(schemaFile);
			File[] files = schemaDirectory.listFiles(fileFilter);
				
				if (files != null)
				{
					for (int count=0; count<files.length; count++)
					{
						m_schemaFile = files[count];
						generate(cl, schemaDirectory, files[count], output);
					}
				}
				else
				{
						throw new GeneratorException(schemaFile+" not found");
				}
			}
		}
		catch (Exception e)
		{
			System.out.println("Exception: "+e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/*******************************************************************/
	/* protected methods */
	/*******************************************************************/
	/** isWildCard */
	
	protected static boolean isWildCard(String arg)
	{
		if (arg.indexOf("*") >= 0)
		{
			return true;
		}
		return false;
		
	} // isWildCard
	
	/*******************************************************************/
	/** generate */
	
	protected static void generate(Class<?> cl, File schemaDirectory, File schemaFile, File output)
	throws Exception
	{	
	Class<?> clArgs[] = {File.class, File.class, File.class};
	Object args[] = {schemaDirectory, schemaFile, output};
	Constructor<?> ctor = cl.getDeclaredConstructor(clArgs);
	IGenerator generator = (IGenerator)ctor.newInstance(args);

		generator.generate(System.out);
	
	} // generate
	
} // Generator

/*******************************************************************/



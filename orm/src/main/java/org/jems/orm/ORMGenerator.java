/************************************************************/
/*
	File: ORMGenerator.java

	ORMGenerator reads an ORM schema definition and	generates an ORM mapping file.

	Author: James Cowan
*/
/************************************************************/

package org.jems.orm;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

import org.apache.log4j.Logger;
import org.jems.generator.Generator;
import org.jems.generator.GeneratorException;
import org.jems.generator.IGenerator;
import org.jems.orm.model.Orm;

/************************************************************/

abstract public class ORMGenerator extends Generator implements IGenerator
{
	protected Orm	m_orm;
	
	static protected Logger		m_log = Logger.getLogger(ORMGenerator.class);
	
	public ORMGenerator(File schemaDirectory, File schemaFile, File output)
	throws GeneratorException
	{
		super(schemaDirectory, schemaFile, output);
		
		m_orm = getOrm();
	}

	/************************************************************/
	/* public methods */
	/************************************************************/
	/** generate */

	public void generate(PrintStream out) throws IOException, GeneratorException
	{
		if (!m_schemaFile.exists())
		{
		String msg = m_schemaFile.getAbsolutePath()+" is missing";
		
			throw new GeneratorException(msg);		
		}
		
		if (m_output.exists())
		{
			if (m_output.lastModified() > m_schemaFile.lastModified())
			{
				m_log.info(m_output.getAbsolutePath()+" is up to date");
				return;
			}
		}
			
		m_log.info("Generate: "+m_output.getAbsolutePath());
		
		PrintStream ps = new PrintStream(m_output);
		
		generateMapping(ps);
		ps.close();

	} // generate
	
	/************************************************************/
	/* protected methods */
	/************************************************************/
	/* generateMapping */
	
	abstract void generateMapping(PrintStream out) throws IOException, GeneratorException;
	
	/************************************************************/
	/** getOrm */
	
	protected Orm getOrm()throws GeneratorException
	{	
        	try
        	{
        	FileReader fr = new FileReader(m_schemaFile);
        	
        		return Orm.unmarshalOrm(fr);
        	}
        	catch (Throwable e)
        	{
        	String schemaFileName = m_schemaFile.getAbsolutePath();
        	String msg = "Cannot read "+schemaFileName+": "+e.getMessage();
        	
        		throw new GeneratorException(msg);
        	}
        	
	} // getOrm
	
} // ORMGenerator

/************************************************************/



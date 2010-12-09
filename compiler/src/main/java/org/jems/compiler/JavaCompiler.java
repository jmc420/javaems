/************************************************************/
/*
	File: JavaCompiler.java

	JavaCompiler reads a list of java classes to compile and
	generates classes for other languages (as3 etc). 

	Author: James Cowan
*/
/************************************************************/

package org.jems.compiler;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.jems.compiler.model.JavaClass;
import org.jems.compiler.model.JavaClassList;
import org.jems.generator.Generator;
import org.jems.generator.GeneratorException;
import org.jems.generator.IGenerator;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IUnmarshallingContext;

/************************************************************/

abstract public class JavaCompiler extends Generator implements IGenerator
{
	protected JavaClassList		m_javaClassList;

	static protected Logger		m_log = Logger.getLogger(JavaCompiler.class);
	
	public JavaCompiler(File schemaDirectory, File schemaFile, File output)
	throws GeneratorException
	{
		super(schemaDirectory, schemaFile, output);
		
		m_javaClassList = getJavaClassList();
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
		
		FileUtils.forceMkdir(m_output);	
			
		compileClassList();

	} // generate
	
	/************************************************************/
	/* protected methods */
	/************************************************************/
	/** compileClassList */
	
	abstract protected void compileClassList() throws IOException, GeneratorException;
	
   	/************************************************************/
	/** getJavaClass */

    protected Class<?> getJavaClass(JavaClass javaClass)throws GeneratorException
    {
    	return getJavaClass(javaClass.getClassName());
    
    } // getJavaClass
    
	/************************************************************/
	/** getJavaClassList */
	
	protected JavaClassList getJavaClassList()throws GeneratorException
	{	
        try
        {
        FileReader fr = new FileReader(m_schemaFile);
      	IBindingFactory bfact = BindingDirectory.getFactory(JavaClassList.class);
        IUnmarshallingContext uctx = bfact.createUnmarshallingContext();

        	return (JavaClassList) uctx.unmarshalDocument(fr, null);
        }
        catch (Throwable e)
        {
        String schemaFileName = m_schemaFile.getAbsolutePath();
        String msg = "Cannot read "+schemaFileName+": "+e.getMessage();
        	
        	throw new GeneratorException(msg);
        }
        	
	} // getJavaClassList

   	/************************************************************/
	// static public
   	/************************************************************/
	/** getJavaClass */

    static public Class<?> getJavaClass(String className)throws GeneratorException
    {    
    	try
    	{
			return Class.forName(className);		
		} 
    	catch (ClassNotFoundException e)
		{
    		throw new GeneratorException("Invalid class: "+className);		
		}   	
    	
    } // getJavaClass
	
} // JavaCompiler

/************************************************************/



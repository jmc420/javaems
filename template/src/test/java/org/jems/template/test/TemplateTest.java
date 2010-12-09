/*******************************************************************/
/*
	File: TemplateTranslator.java
*/
/*******************************************************************/

package org.jems.template.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jems.template.TemplateTranslator;

/*******************************************************************/
/**
 * Unit test for TemplateTranslator
 * 
 */
/*******************************************************************/

public class TemplateTest extends TestCase
{
/*******************************************************************/
    /** Create the test case */

    public TemplateTest()
    {
    }

    /*******************************************************************/
    /** create a test suite */
 
    public static Test suite()
    {
        return new TestSuite(TemplateTest.class);
    }

    /*******************************************************************/
    /** testTemplate */

    public void testTemplate()
    {
    String baseDir = System.getProperty("BASEDIR");
    String srcDir = baseDir+"/src/test/templates";
    String targetDir = baseDir+"/target/generated-sources";
    TemplateTranslator templateTranslator = new TemplateTranslator(srcDir, targetDir);
    	
    	try
    	{
		templateTranslator.generate();
		assertTrue(true);
    	}
    	catch (Exception e)
    	{ 
    	String msg = "Exception "+e.getMessage();
    	
    		assertTrue(msg, false);
    	}
        
    } // testTemplate
    
} // TemplateTest


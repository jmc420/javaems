/*******************************************************************/
/*
        File: TestAll.java
        
        Author: James Cowan
*/
/*******************************************************************/

package org.jems.example.model.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/*******************************************************************/

public class TestAll
{
	public TestAll()
	{
	}
	
	/*******************************************************************/
	// public methods
	/*******************************************************************/

	public static Test suite()
	{
	TestSuite suite = new TestSuite();

		suite.addTestSuite(TestDatabase.class);
		return suite;

    } // suite

} // TestAll

/*******************************************************************/

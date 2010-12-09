/*******************************************************************/
/*
        File: BaseTest.java
        
        Author: James Cowan
*/
/*******************************************************************/

package org.jems.example.model.test;

import org.apache.log4j.Logger;
import org.jems.dao.IDao;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/*******************************************************************/

@SuppressWarnings("deprecation")
abstract public class BaseTest extends AbstractTransactionalDataSourceSpringContextTests
{
	protected Logger			m_log = Logger.getLogger(BaseTest.class);
	
	private IDao				m_dao;
	
	public BaseTest()
	{
		setDefaultRollback(true);
	}
	
	/*******************************************************************/
	// public methods
	/*******************************************************************/	
	/** getDao */
	
	public IDao getDao()
	{
		if (m_dao == null)
		{
			m_dao = (IDao)applicationContext.getBean("JemsJpaDao");
		}
		return m_dao;
		
	} // getDao
	
	/*******************************************************************/
	// protected methods
	/*******************************************************************/
	/** getConfigLocations */
	
	protected String[] getConfigLocations()
	{
		return new String[]
		{ 
			"file:src/main/resources/applicationContext-jpa.xml",
    		"file:src/test/resources/applicationContext.xml"

		};

	} // getConfigLocations

} // BaseTest

/*******************************************************************/

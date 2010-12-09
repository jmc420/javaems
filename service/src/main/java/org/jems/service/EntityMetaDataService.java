/************************************************************/
/*
	File: EntityMetaDataService.java

	Author: James Cowan
*/
/************************************************************/

package org.jems.service;

import java.util.ArrayList;
import java.util.List;

import org.jems.dao.IDao;
import org.jems.dao.types.EntityMetaData;

public class EntityMetaDataService implements IEntityMetaDataService
{
	protected IDao	m_dao;
	protected List<EntityMetaData>	m_entityMetaData;
	
	public EntityMetaDataService(IDao dao)
	{
		m_dao = dao;
		m_entityMetaData = new ArrayList<EntityMetaData>(dao.getEntityMetaData().values());
	}
	
	/************************************************************/
	// public methods
	/************************************************************/
	/** EntityMetaData */
	
	public List<EntityMetaData> getEntityMetaData()
	{
		return m_entityMetaData;
		
	} // getEntityMetaData
	
} // EntityMetaDataService

/************************************************************/
// End of class
/************************************************************/

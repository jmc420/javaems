/************************************************************/
/*
	File: EntityMetaDataService.java

	Author: James Cowan
*/
/************************************************************/

package org.jems.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jems.dao.DaoException;
import org.jems.dao.IDao;
import org.jems.dao.types.EntityMetaData;
import org.jems.dao.types.EntityPropertyMetaData;

public class EntityMetaDataService implements IEntityMetaDataService
{
	protected IDao												m_dao;
	protected List<EntityMetaData>								m_entityMetaData;
	protected Map<String, EntityPropertyMetaData>				m_entityPrimaryKeyMap;
	protected Map<String, Map<String, EntityPropertyMetaData>>	m_entityPropertyMap;
	
	public EntityMetaDataService(IDao dao)
	{
		m_dao = dao;
		m_entityMetaData = new ArrayList<EntityMetaData>(dao.getEntityMetaData().values());
		
		initialiseMetaData();
	}
	
	/************************************************************/
	// public methods
	/************************************************************/
	/** EntityMetaData */
	
	public List<EntityMetaData> getEntityMetaData()
	{
		return m_entityMetaData;
		
	} // getEntityMetaData
	
	/************************************************************/
	/** getPrimaryKey */
	
	public EntityPropertyMetaData getPrimaryKey(String entityName) throws DaoException
	{
	EntityPropertyMetaData primaryKey = m_entityPrimaryKeyMap.get(entityName);
		
		if (primaryKey != null)
		{
			return primaryKey;
		}
		
		throw new DaoException(entityName+" has no primary key");	
		
	} // getPrimaryKey
	
	/************************************************************/
	/** getEntityPropertyMetaData */
	
	public EntityPropertyMetaData getEntityPropertyMetaData(String entityName, String propertyName)
	throws DaoException
	{
		checkEntity(entityName);
		
		Map<String, EntityPropertyMetaData> propertyMap = m_entityPropertyMap.get(entityName);
		
		if (propertyMap == null)
		{
			throw new DaoException(entityName+" has no properties");
		}
		
		EntityPropertyMetaData metaData = propertyMap.get(propertyName);
		
		if (metaData != null)
		{
			return metaData;
		}
		
		throw new DaoException(entityName+" has no property: "+propertyName);			
		
	} // getEntityPropertyMetaData
	
	/*******************************************************************/
	// protected methods
	/*******************************************************************/
	/** checkEntity */

	protected void checkEntity(String entityName) throws DaoException
	{
		if (m_entityPropertyMap.get(entityName) == null)
		{
			throw new DaoException("Invalid entity: "+entityName);
		}
		
	} // checkEntity
	
	/*******************************************************************/
	/** initialiseMetaData */

	protected void initialiseMetaData()
	{
	List<EntityMetaData> entityMetaDataList = getEntityMetaData();

		m_entityPropertyMap = new HashMap<String, Map<String, EntityPropertyMetaData>>();
		m_entityPrimaryKeyMap = new HashMap<String, EntityPropertyMetaData>();
	
		for (EntityMetaData entityMetaData : entityMetaDataList)
		{
		EntityPropertyMetaData entityPropertyMetaDataList[]	= entityMetaData.getEntityPropertyMetaData();
		Map<String, EntityPropertyMetaData> propertyMap = new HashMap<String, EntityPropertyMetaData>();
		
			m_entityPropertyMap.put(entityMetaData.getName(), propertyMap);
		
			for (int count=0; count<entityPropertyMetaDataList.length; count++)
			{
			EntityPropertyMetaData entityPropertyMetaData = entityPropertyMetaDataList[count];
			
				if (entityPropertyMetaData.isPrimaryKey())
				{
					m_entityPrimaryKeyMap.put(entityMetaData.getName(), entityPropertyMetaData);
				}
				
				propertyMap.put(entityPropertyMetaData.getName(), entityPropertyMetaData);
			}
		}
	    
	} // initialiseMetaData
	
} // EntityMetaDataService

/************************************************************/
// End of class
/************************************************************/

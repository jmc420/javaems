/************************************************************/
/*
	File: EntityService.java
	
	Author: James Cowan

*/
/************************************************************/

package org.jems.service;

import java.util.HashMap;
import java.util.List;

import org.jems.dao.DaoException;
import org.jems.dao.DaoNotFoundException;
import org.jems.dao.IDao;
import org.jems.dao.types.DaoFilter;
import org.jems.dao.types.DaoSort;
import org.jems.dao.types.EntityMetaData;

public class EntityService implements IEntityService
{
	protected IDao				m_dao;
	protected HashMap<String,Class<?>>	m_entityClassTable;
	
	public EntityService(IDao dao, EntityMetaDataService entityMetaDataService)
	throws DaoException
	{
		m_dao = dao;
		m_entityClassTable = getEntityClassTable(entityMetaDataService);
	}
	
	/************************************************************/
	/** create */

	public <T> T create(T entity)
	throws DaoException
	{
		return m_dao.create(entity);

	} // create

	/************************************************************/
	/** get */

	@SuppressWarnings("unchecked")
	public <T> T get(String entityName, DaoFilter daoFilter[])
	throws DaoException, DaoNotFoundException
	{
	Class<T> cl = (Class<T>) getEntityClass(entityName);
	
		return (T) m_dao.get(cl, daoFilter);

	} // get

	/************************************************************/
	/** getList */

	@SuppressWarnings("unchecked")
	public <T> List<T> getList(String entityName, DaoFilter daoFilter[], DaoSort daoSort[])
	throws DaoException
	{
	Class<T> cl = (Class<T>) getEntityClass(entityName);
		
		return m_dao.getList(cl, daoFilter, daoSort);

	} // getList
	
	/************************************************************/
	/** getList */

	@SuppressWarnings("unchecked")
	public <T> List<T> getList(String entityName, DaoFilter daoFilter[], DaoSort daoSort[], int startRow, int maxRows)
	throws DaoException
	{
	Class<T> cl = (Class<T>) getEntityClass(entityName);
		
		return m_dao.getList(cl, daoFilter, daoSort, startRow, maxRows);

	} // getList

	/************************************************************/
	/** getListCount */

	public long getListCount(String entityName, DaoFilter daoFilter[])
	throws DaoException
	{
	Class<?> cl = getEntityClass(entityName);

		return m_dao.getListCount(cl, daoFilter);

	} // getListCount

	/************************************************************/
	/** remove */

	public <T> void remove(T entity)
	throws DaoException
	{
		m_dao.remove(entity);

	} // remove

	/************************************************************/
	/** update */

	public <T> void update(T entity)
	throws DaoException
	{
		m_dao.update(entity);

	} // update

	/************************************************************/
	// protected methods
	/************************************************************/
	/** getEntityClassTable */
	
	protected Class<?> getEntityClass(String className) throws DaoException
	{
	Class<?> cl = m_entityClassTable.get(className);
	
		if (cl == null)
		{
		String msg = "Invalid class: "+className;
			
			throw new DaoException(msg);
		}
		return cl;
		
	} // getEntityClass
	
	/************************************************************/
	/** getEntityClassTable */
	
	protected HashMap<String,Class<?>> getEntityClassTable(EntityMetaDataService entityMetaDataService)
	throws DaoException
	{
	HashMap<String,Class<?>> entityClassTable = new HashMap<String,Class<?>>();
	List<EntityMetaData> metaDataList = entityMetaDataService.getEntityMetaData();
	
		for (int index=0; index<metaDataList.size(); index++)
		{
		EntityMetaData metaData = metaDataList.get(index);
		String className = metaData.getClassName();
		
			try
			{
			Class<?>cl = Class.forName(className);
			
				entityClassTable.put(metaData.getName(), cl);
			}
			catch (ClassNotFoundException e)
			{
			String msg = "Invalid class: "+className;
			
				throw new DaoException(msg);
			}
		}

		return entityClassTable;
		
	} // getEntityClassTable
	
} // EntityService

/************************************************************/
// End of class
/************************************************************/

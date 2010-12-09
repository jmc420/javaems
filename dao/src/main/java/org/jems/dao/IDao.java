/*******************************************************************/
/*
	File: IDao.java
	
	A generic DOA interface
	
	Author: James Cowan
*/
/*******************************************************************/

package org.jems.dao;

import java.util.List;
import java.util.Map;

import org.jems.dao.types.DaoFilter;
import org.jems.dao.types.DaoParameter;
import org.jems.dao.types.DaoSort;
import org.jems.dao.types.EntityMetaData;

/*******************************************************************/
/** IDao interface */

public interface IDao
{
	/*******************************************************************/
	/** create */
	
	public <T> T create(T entity) throws DaoException;

	/*******************************************************************/
	/** get */
	
	public <T>T get(Class<T> cl, DaoFilter filter[])
	throws DaoException, DaoNotFoundException;
	
	/*******************************************************************/
	/** get */
	
	public <T>T get(Class<T> cl, DaoFilter filter[], boolean allowNull)
	throws DaoException, DaoNotFoundException;
	
	/*******************************************************************/
	/** get */
	
	public <T>T get(String queryName, DaoParameter param[], DaoFilter filter[])
	throws DaoException, DaoNotFoundException;
	
	/*******************************************************************/
	/** get */
	
	public <T>T get(SQLQuery query, DaoParameter param[], DaoFilter filter[])
	throws DaoException, DaoNotFoundException;
	
	/*******************************************************************/
	/** getEntityMetaData */
	
	public Map<String, EntityMetaData> getEntityMetaData();
	
	/*******************************************************************/
	/** getList */
	
	public <T>List<T> getList(Class<T> cl, DaoFilter filter[], DaoSort sort[])
	throws DaoException;
	
	/*******************************************************************/
	/** getList */
	
	public <T>List<T> getList(Class<T> cl, DaoFilter filter[], DaoSort sort[], int startRow, int maxRows)
	throws DaoException;
	
	/*******************************************************************/
	/** getList */
	
	public <T>List<T> getList(String queryName)throws DaoException;
	
	/*******************************************************************/
	/** getList */
	
	public <T>List<T> getList(String queryName, DaoParameter param[], DaoFilter filter[], DaoSort sort[])
	throws DaoException;
	
	/*******************************************************************/
	/** getList */
	
	public <T>List<T> getList(String queryName, DaoParameter param[], DaoFilter filter[], DaoSort sort[], int startRow, int maxRows)
	throws DaoException;
	
	/*******************************************************************/
	/** getList */
	
	public <T>List<T> getList(SQLQuery query)throws DaoException;
	
	/*******************************************************************/
	/** getList */
	
	public <T>List<T> getList(SQLQuery query, DaoParameter param[], DaoFilter filter[], DaoSort sort[])
	throws DaoException;
	
	/*******************************************************************/
	/** getList */
	
	public <T>List<T> getList(SQLQuery query, DaoParameter param[], DaoFilter filter[], DaoSort sort[], int startRow, int maxRows)
	throws DaoException;
	
	/*******************************************************************/
	/** getListCount */
	
	public <T>int getListCount(Class<T> cl, DaoFilter filter[]) throws DaoException;
	
	/*******************************************************************/
	/** remove */
	
	public <T> void remove(T entity) throws DaoException;

	/*******************************************************************/
	/** update */
	
	public <T> T update(T entity) throws DaoException;

} // IDao

/*******************************************************************/


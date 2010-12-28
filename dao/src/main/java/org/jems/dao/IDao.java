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
	
	public <T> T create(T entity);

	/*******************************************************************/
	/** get */
	
	public <T>T get(Class<T> cl, DaoFilter filter[])
	throws DaoNotFoundException;
	
	/*******************************************************************/
	/** get */
	
	public <T>T get(Class<T> cl, DaoFilter filter[], boolean allowNull)
	throws DaoNotFoundException;
	
	/*******************************************************************/
	/** get */
	
	public <T>T get(String queryName, DaoParameter param[], DaoFilter filter[])
	throws DaoNotFoundException;
	
	/*******************************************************************/
	/** get */
	
	public <T>T get(SQLQuery query, DaoParameter param[], DaoFilter filter[])
	throws DaoNotFoundException;
	
	/*******************************************************************/
	/** getEntityMetaData */
	
	public Map<String, EntityMetaData> getEntityMetaData();
	
	/*******************************************************************/
	/** getList */
	
	public <T>List<T> getList(Class<T> cl, DaoFilter filter[], DaoSort sort[]);
	
	/*******************************************************************/
	/** getList */
	
	public <T>List<T> getList(Class<T> cl, DaoFilter filter[], DaoSort sort[], int startRow, int maxRows);
	
	/*******************************************************************/
	/** getList */
	
	public <T>List<T> getList(String queryName);
	
	/*******************************************************************/
	/** getList */
	
	public <T>List<T> getList(String queryName, DaoParameter param[], DaoFilter filter[], DaoSort sort[]);
	
	/*******************************************************************/
	/** getList */
	
	public <T>List<T> getList(String queryName, DaoParameter param[], DaoFilter filter[], DaoSort sort[], int startRow, int maxRows);
	
	/*******************************************************************/
	/** getList */
	
	public <T>List<T> getList(SQLQuery query);
	
	/*******************************************************************/
	/** getList */
	
	public <T>List<T> getList(SQLQuery query, DaoParameter param[], DaoFilter filter[], DaoSort sort[]);
	
	/*******************************************************************/
	/** getList */
	
	public <T>List<T> getList(SQLQuery query, DaoParameter param[], DaoFilter filter[], DaoSort sort[], int startRow, int maxRows);
	
	/*******************************************************************/
	/** getListCount */
	
	public <T>int getListCount(Class<T> cl, DaoFilter filter[]);
	
	/*******************************************************************/
	/** remove */
	
	public <T> void remove(T entity);

	/*******************************************************************/
	/** update */
	
	public <T> T update(T entity);

} // IDao

/*******************************************************************/


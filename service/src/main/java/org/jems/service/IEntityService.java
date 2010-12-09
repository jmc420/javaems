/************************************************************/
/*
	File: IEntityService.java
	
	Author: James Cowan

*/
/************************************************************/

package org.jems.service;

import java.util.List;

import org.jems.dao.DaoException;
import org.jems.dao.DaoNotFoundException;
import org.jems.dao.types.DaoFilter;
import org.jems.dao.types.DaoSort;

public interface IEntityService
{
	
	/************************************************************/
	/** create */

	public <T> T create(T entity) throws DaoException;

	/************************************************************/
	/** get */

	public <T> T get(String entityName, DaoFilter daoFilter[])
	throws DaoException, DaoNotFoundException;

	/************************************************************/
	/** getList */

	public <T> List<T> getList(String entityName, DaoFilter daoFilter[], DaoSort daoSort[])
	throws DaoException;

	/************************************************************/
	/** getList */

	public <T> List<T> getList(String entityName, DaoFilter daoFilter[], DaoSort daoSort[], int startRow, int maxRows)
	throws DaoException;

	/************************************************************/
	/** getListCount */

	public long getListCount(String entityName, DaoFilter daoFilter[])
	throws DaoException;

	/************************************************************/
	/** remove */

	public <T> void remove(T entity) throws DaoException;

	/************************************************************/
	/** update */

	public <T> void update(T entity)throws DaoException;

} // IEntityService

/************************************************************/
// End of class
/************************************************************/

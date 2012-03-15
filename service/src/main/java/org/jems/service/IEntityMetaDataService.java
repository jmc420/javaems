/************************************************************/
/*
	File: EntityMetaDataService.java

	Author: James Cowan
*/
/************************************************************/

package org.jems.service;

import java.util.List;

import org.jems.dao.DaoException;
import org.jems.dao.types.EntityMetaData;
import org.jems.dao.types.EntityPropertyMetaData;

public interface IEntityMetaDataService
{	
	/************************************************************/
	// public methods
	/************************************************************/
	/** getEntityMetaData */
	
	public List<EntityMetaData> getEntityMetaData();
	
	/************************************************************/
	/** getPrimaryKey */
	
	public EntityPropertyMetaData getPrimaryKey(String entityName)
	throws DaoException;
	
	/************************************************************/
	/** getProperty */
	
	public EntityPropertyMetaData getEntityPropertyMetaData(String entityName, String propertyName)
	throws DaoException;
	
} // IEntityMetaDataService

/************************************************************/
// End of class
/************************************************************/

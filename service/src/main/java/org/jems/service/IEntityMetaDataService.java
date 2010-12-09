/************************************************************/
/*
	File: EntityMetaDataService.java

	Author: James Cowan
*/
/************************************************************/

package org.jems.service;

import java.util.List;

import org.jems.dao.types.EntityMetaData;

public interface IEntityMetaDataService
{	
	/************************************************************/
	// public methods
	/************************************************************/
	/** EntityMetaData */
	
	public List<EntityMetaData> getEntityMetaData();
	
} // IEntityMetaDataService

/************************************************************/
// End of class
/************************************************************/

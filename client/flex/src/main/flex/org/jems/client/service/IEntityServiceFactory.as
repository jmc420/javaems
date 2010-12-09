/*******************************************************************/
/*
	File: IEntityServiceFactory.as

	Author: James Cowan
*/
/*******************************************************************/

package org.jems.client.service
{		
	public interface IEntityServiceFactory
	{
		
		/*******************************************************************/
		// public methods
		/*******************************************************************/
		/** getEntityService */
		
		function getEntityService(resultHandler:Function=null, faultHandler:Function=null):IEntityService;
		
		/*******************************************************************/
		/** getEntityMetaDataService */
		
		function getEntityMetaDataService(resultHandler:Function=null, faultHandler:Function=null):IEntityMetaDataService;

	} // IEntityServiceFactory
	
} // package

/*******************************************************************/
// End of file
/*******************************************************************/


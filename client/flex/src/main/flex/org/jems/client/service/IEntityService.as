/*******************************************************************/
/*
	File: IEntityService.as

	Author: James Cowan
*/
/*******************************************************************/

package org.jems.client.service
{		

	public interface IEntityService
	{		
		/*******************************************************************/
		/** create */
		
		function create(entity:Object):void;
		
		/*******************************************************************/
		/** get */
		
		function get(entityName:String, filter:Array):void
				
		/*******************************************************************/
		/** getList */
		
		function getList(entityName:String, filter:Array, sort:Array):void
		
		/*******************************************************************/
		/** getListCount */
		
		function getListCount(entityName:String, filter:Array):void
		
		/*******************************************************************/
		/** getPagedList */
		
		function getPagedList(entityName:String, filter:Array, sort:Array, startRow:int, pageSize:int):void
		
		/*******************************************************************/
		/** remove */
		
		function remove(entity:Object):void;
		
		/*******************************************************************/
		/** update */
		
		function update(entity:Object):void;
		

	} // IEntityService
	
} // package

/*******************************************************************/
// End of file
/*******************************************************************/


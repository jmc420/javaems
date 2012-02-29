/*******************************************************************/
/*
File: IEntityMetaDataController.as

Author: James Cowan
*/
/*******************************************************************/

package org.jems.client.controller
{
	import mx.core.UIComponent;
	
	import org.jems.dao.types.DaoFilter;
	import org.jems.dao.types.EntityPropertyMetaData;
	
	public interface IEntityMetaDataController 
	{
		/*******************************************************************/
		/** bindFormToObject */
		
		function bindFormToObject(entityName:String, data:Object, form:UIComponent):Object;

		/*******************************************************************/
		/** bindObjectToForm */
		
		function bindObjectToForm(entityName:String, data:Object, form:UIComponent):void;

		/*******************************************************************/
		/** createEntity */
		
		function createEntity(entityName:String):Object;
		
		/*******************************************************************/
		/** getFilter */
		
		function getFilter(entityName:String, column:String, value:Object, operation:String="=", className:String=""):DaoFilter;
		
		/*******************************************************************/
		/** getPropertyMetaData */
		
		function getPropertyMetaData(entityName:String, propertyName:String):EntityPropertyMetaData;
		
	} // interface
		
} // package

/*******************************************************************/
// End of file
/*******************************************************************/ 


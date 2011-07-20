/*******************************************************************/
/*
	File: IEntityController.as

	Author: James Cowan
*/
/*******************************************************************/

package org.jems.client.controller
{
	import mx.collections.ArrayCollection;
	import mx.controls.DataGrid;
	
	public interface IEntityController
	{
		/*******************************************************************/	
		/** createEntity */
		
		function createEntity(entity:Object):void;
			
		/*******************************************************************/
		/** getData */
		
		function getData():ArrayCollection;

		/*******************************************************************/
		/** getDataGrid */
		
		function getDataGrid():DataGrid;
		
		/*******************************************************************/
		/** getEntity */
		
		function getEntity(filters:Array):void;
		
		/*******************************************************************/
		/** getEntityFactory */
		
		function getEntityFactory():IEntityFactory;
	
		/*******************************************************************/
		/** getEntityName */
		
		function getEntityName():String;
		
		/*******************************************************************/
		/** getTableRowCount */
		
		function getTableRowCount():int;
		
		/*******************************************************************/
		/** isPaged */
		
		function isPaged():Boolean;
		
		/*******************************************************************/
		/** loadTable */
		
		function loadTable(reset:Boolean):void;
		
		/*******************************************************************/	
		/** updateEntity */
		
		function updateEntity(entity:Object):void
		
	} // IEntityController

} // package

/*******************************************************************/
// End of file
/*******************************************************************/ 


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
		/** getData */
		
		function getData():ArrayCollection;

		/*******************************************************************/
		/** getDataGrid */
		
		function getDataGrid():DataGrid;
		
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
		
	} // IEntityController

} // package

/*******************************************************************/
// End of file
/*******************************************************************/ 


/*******************************************************************/
/*
	File: TableSort.as

	Author: James Cowan
*/
/*******************************************************************/

package org.jems.client.controller
{
	import flash.events.TimerEvent;
	import flash.utils.Timer;
	
	import mx.collections.ArrayCollection;
	import mx.collections.Sort;
	import mx.collections.SortField;
	import mx.controls.DataGrid;
	import mx.controls.dataGridClasses.DataGridColumn;
	import mx.events.DataGridEvent;
	import mx.utils.ObjectUtil;
	
	import org.jems.dao.types.DaoSort;
	
	public class TableSort extends Controller
	{
		protected var	m_entityController:IEntityController;
		protected var	m_sort:Sort;
		protected var	m_sortColumn:DaoSort;
				
		public function TableSort(entityController:IEntityController):void
		{
			m_entityController = entityController;
		
			var dataGrid:DataGrid = m_entityController.getDataGrid(); 
		
			dataGrid.addEventListener(DataGridEvent.HEADER_RELEASE, handleSortEvent);
		}
		
		/*******************************************************************/
		// public methods
		/*******************************************************************/
		/** getSort */
		
		public function getSort():Array
		{
			if (m_sortColumn == null)
			{
				return [];
			}
			
			return [m_sortColumn];
			
		} // getSort
		
		/*******************************************************************/
		/** sort */
		
		public function sort():void
		{
			if (m_sortColumn != null)
			{
			var sortField:SortField = getTableSort().fields[m_sortColumn.column];
			var sort:Sort = new Sort();
			var data:ArrayCollection = m_entityController.getData();
				
				//sort.compareFunction = compareFunction;
				sort.fields = [sortField];
				data.sort = sort;
				data.refresh();
			}
			
		} // sort
		
		/*******************************************************************/
		// protected methods
		/*******************************************************************/
		
		protected function compareFunction(objA:Object, objB:Object, fields:Array=null):int
		{
		var sortField:SortField = fields[0] as SortField;
		var propertyName:String = sortField.name;
		var objectA:Object = getProperty(objA, propertyName);
		var objectB:Object = getProperty(objB, propertyName);
		
        	if (objectA is String)
        	{
        		return ObjectUtil.stringCompare(objectA as String, objectB as String, true);
        	}
        	
        	if (objectA is Number)
        	{
        		return ObjectUtil.numericCompare(objectA as Number, objectB as Number);
        	}
        	
        	if (objectA is Date)
        	{
        		return ObjectUtil.dateCompare(objectA as Date, objectB as Date);
        	}
        	
        	throw Error("Cannot sort");
        	
  		} // compareFunction
  		
  		/*******************************************************************/
		/** getProperty */
		
		protected function getProperty(data:Object, name:String):Object
		{
		var fields:Array = name.split("."); 
		var result:Object = data; 
		
			for each( var field:String in fields) 
			{
				result = result[field]; 
			}

			return result; 

		} // getProperty
  		
		/*******************************************************************/
		/** getTableSort */
		
		protected function getTableSort():Sort
		{
			if (m_sort != null)
			{
				return m_sort;		
			}
		
			m_sort = new Sort();
			
			var columns:Array = m_entityController.getDataGrid().columns;
			var sortFields:Array = new Array();
		
			for (var count:int = 0; count<columns.length; count++)
			{
			var column:DataGridColumn = columns[count];
			var dataField:String = column.dataField;
			var sortField:SortField = new SortField(dataField);
			
				if (column.sortCompareFunction != null)
				{
					sortField.compareFunction = column.sortCompareFunction;
				}
				sortFields[dataField] = sortField;
			}
		
			m_sort.fields = sortFields;
			
			return m_sort;

		} // getTableSort
		
		/*******************************************************************/
		/** getTableColumn */
		
		protected function getTableColumn(name:String):DataGridColumn
		{
		var columns:Array = m_entityController.getDataGrid().columns;
		
			for (var count:int=0; count<columns.length; count++)
			{
			var column:DataGridColumn = columns[count];
			
				if (column.dataField == name)
				{
					return column;
				}
			}
			
			throw new Error("Invalid table column "+name);
			
		} // getTableColumn
		
		/*******************************************************************/
		/** handleSortEvent */
		
		protected function handleSortEvent(e:DataGridEvent):void
		{
		var dataField:String = e.dataField;
		var column:DataGridColumn = getTableColumn(dataField);
		
			e.preventDefault();
			
			if (column.sortable)
			{
			var sortField:SortField = getTableSort().fields[dataField];
			var ascending:Boolean = (sortField.descending) ? true : false;
			var sort:DaoSort = new DaoSort();
   		
   				sort.column = dataField;
   				sort.ascending = ascending;
   				sortField.descending = !ascending;
   				m_sortColumn = sort;
   				
   				if (m_entityController.isPaged())
   				{
					m_entityController.loadTable(true);
   				}
   				else
   				{
					this.sort();	// can cause run time error in flex 3.4
   				}
			}
			
		} // handleSortEvent
		
		/*******************************************************************/

	} // TableSort

} // package

/*******************************************************************/
// End of file
/*******************************************************************/ 


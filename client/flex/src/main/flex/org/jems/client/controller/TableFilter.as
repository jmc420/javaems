/*******************************************************************/
/*
	File: TableFilter.as

	Author: James Cowan
*/
/*******************************************************************/

package org.jems.client.controller
{
	import flash.events.ContextMenuEvent;
	import flash.events.MouseEvent;
	import flash.ui.ContextMenu;
	import flash.ui.ContextMenuItem;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Button;
	import mx.controls.ComboBox;
	import mx.controls.DataGrid;
	import mx.core.UIComponent;
	import mx.events.ListEvent;
	
	import org.jems.client.service.IEntityServiceFactory;
	import org.jems.dao.types.DaoFilter;
	import org.jems.dao.types.EntityPropertyMetaData;
	
	public class TableFilter extends Controller
	{
		protected var	m_columnIndex:int;
		protected var	m_entityController:IEntityController;
		protected var	m_entityMetaDataController:EntityMetaDataController;
		protected var	m_filters:Array;
		protected var	m_filterStringList:Array;
		protected var	m_menuFilter:Array;
		protected var	m_rowIndex:int;
		protected var	m_view:UIComponent;

		public static const	FILTERLIST:String = "FilterList";		
		public static const	OPERATIONS:Array = ["=", "!=", "<", "<=", ">", ">="];
		public static const	REMOVEFILTERBUTTON:String = "RemoveFilterButton";
				
		public function TableFilter(entityServiceFactory:IEntityServiceFactory, view:UIComponent, entityController:IEntityController):void
		{
			m_view = view;
			m_entityController = entityController;
			m_entityMetaDataController = EntityMetaDataController.getInstance(entityServiceFactory, entityController.getEntityFactory());
			m_filters = [];
			m_filterStringList = [];
			m_menuFilter = [];
		
			var dataGrid:DataGrid = m_entityController.getDataGrid(); 
		
			dataGrid.addEventListener(ListEvent.ITEM_ROLL_OVER, handleItemRollOver);   				
   			dataGrid.contextMenu = createContextMenu();
   			setButtonHandler(m_view, getEntityNamePrefix()+REMOVEFILTERBUTTON, handleRemoveFilterButton, false);
		}
		
		/*******************************************************************/
		// public methods
		/*******************************************************************/
		/** addFilter */
	
		public function addFilter(column:String, value:Object, operation:String="="):void
		{
			for (var count:int=0; count<m_filters.length; count++)
			{
			var filter:DaoFilter = getFilter(column, value, operation);
			
				if (filter.column == column && filter.value == value)
				{
					return;
				}
			}
			
			m_filters[m_filters.length] = getFilter(column, value, operation);
				
		} // addFilter
		
		/*******************************************************************/
		/** filter */
	
		public function filter():void
		{
			if (!m_entityController.isPaged())
			{			
			var data:ArrayCollection = m_entityController.getData();
			
				data.filterFunction = includeRow;
				data.refresh();
			}

		} // filter
		
		/*******************************************************************/
		/** getFilter */
	
		public function getFilter(column:String, value:Object, operation:String="=", className:String=""):DaoFilter
		{
		var filter:DaoFilter = new DaoFilter();
		
			if (className == "")
			{
			var entityName:String = m_entityController.getEntityName();
			var md:EntityPropertyMetaData = m_entityMetaDataController.getPropertyMetaData(entityName, column);
			
				className = md.typeName;
			}
			filter.className = className;
			filter.column = column;
			filter.operation = operation;
			filter.value = value;
			return filter;
			
		} // getFilter
		
		/*******************************************************************/
		/** getFilters */
	
		public function getFilters():Array
		{
			return m_filters;
			
		} // getFilters
		
		/*******************************************************************/
		/** includeRow */
		
		public function includeRow(row:Object):Boolean
		{
			if (m_filters.length == 0)
			{
				return true;
			}
			
			for (var count:int=0; count<m_filters.length; count++)
			{
			var filter:DaoFilter = m_filters[count];
			var filterValue:Object = filter.value;
			var value:Object = getProperty(row, filter.column);
			
				switch (filter.operation)
				{
				case "=":
					if (!(value == filterValue))
					{
						return false;
					}
					break;
				
				case "!=":
					if (!(value != filterValue))
					{
						return false;
					}
					break;
					
				case ">":
					if (!(value > filterValue))
					{
						return false;
					}
					break;
					
				case ">=":
					if (!(value >= filterValue))
					{
						return false;
					}
					break;

				case "<":
					if (!(value < filterValue))
					{
						return false;
					}
					break;
					
				case "<=":
					if (!(value <= filterValue))
					{
						return false;
					}
					break;
				}
			}
			
			return true;
			
		} // includeRow
		
		/*******************************************************************/
		// protected methods
		/*******************************************************************/
		/** createContextMenu */
		
		protected function createContextMenu():ContextMenu
		{
		var contextMenu:ContextMenu = new ContextMenu(); 
		 		
			contextMenu.hideBuiltInItems();
			contextMenu.addEventListener(ContextMenuEvent.MENU_SELECT, handleContextMenuSelectEvent);
			return contextMenu;
			
		} // createContextMenu
		
		/*******************************************************************/
		/** getEntityNamePrefix */
		
		protected function getEntityNamePrefix():String
		{
			return m_entityController.getEntityName()+"_";
			
		} // getEntityNamePrefix
		
		/*******************************************************************/
		/** getFilterMenu */
		
		protected function getFilterMenu(daoFilter:DaoFilter):String
		{
			return daoFilter.column + " " + daoFilter.operation + " "+daoFilter.value;
			
		} // getFilterMenu
		
		/*******************************************************************/
		/** getProperty */
		
		protected function getProperty(data:Object, name:String):Object
		{
			while (true)
			{
			var index:int = name.indexOf(".");
			
				if (index < 0)
				{
					break;
				}
				
				data = data[name.substr(0, index)];
				name = name.substr(index+1);
			}
			
			return data[name];
			
		} // getProperty
		
		/*******************************************************************/
		/** handleContextMenuSelectEvent */
		
		protected function handleContextMenuSelectEvent(e:ContextMenuEvent):void
		{
		var data:ArrayCollection = m_entityController.getData();
		
			if (data.length <= m_rowIndex)
			{
				return;
			}
			
			var dataGrid:DataGrid = m_entityController.getDataGrid(); 
			var row:Object = data.getItemAt(m_rowIndex);
			var dataField:String = dataGrid.columns[m_columnIndex].dataField;
			var value:Object = getProperty(row, dataField);
			var menuItems:Array = [];
		
			m_menuFilter = [];
			
			for (var count:int=0; count<OPERATIONS.length; count++)
			{
			var operation:String = OPERATIONS[count];
			var filter:DaoFilter = getFilter(dataField, value, operation);
			var menuString:String = getFilterMenu(filter);
			var menuItem:ContextMenuItem = new ContextMenuItem(menuString, true);
			
				menuItem.addEventListener(ContextMenuEvent.MENU_ITEM_SELECT, handleContextMenuItemSelectEvent);
				m_menuFilter[count] = filter;
				menuItems[count] = menuItem;
			}
		 		
			dataGrid.contextMenu.customItems = menuItems;
			
		} // handleContextMenuSelectEvent
		
		/*******************************************************************/
		/** handleContextMenuItemSelectEvent */
		
		protected function handleContextMenuItemSelectEvent(e:ContextMenuEvent):void
		{
		var caption:String = ContextMenuItem(e.currentTarget).caption;
		
			for (var count:int=0; count<OPERATIONS.length; count++)
			{
			var filter:DaoFilter = m_menuFilter[count];
			
				if (caption == getFilterMenu(filter))
				{
				var removeFilterButton:Button = m_view[getEntityNamePrefix()+REMOVEFILTERBUTTON];
				var filterList:ComboBox = m_view[getEntityNamePrefix()+FILTERLIST];
				var filterString:String = getFilterMenu(filter);
				
					removeFilterButton.enabled = true;
					m_filters[m_filters.length] = filter;
					m_filterStringList[m_filterStringList.length] = filterString;
					filterList.dataProvider = m_filterStringList;
					filterList.selectedItem = filterString;
					break;
				}
			}
			
			if (m_entityController.isPaged())
   			{
				m_entityController.loadTable(true);
   			}
   			else
   			{
				this.filter();		// can cause run time error in flex 3.4
				m_entityController.loadTable(true);
   			}
	
		} // handleContextMenuItemSelectEvent
		
		/*******************************************************************/
		/** handleItemRollOver */
		
		protected function handleItemRollOver(e:ListEvent):void
		{
			m_columnIndex = e.columnIndex;
			m_rowIndex = e.rowIndex;
			
		} // handleItemRollOver
		
		/*******************************************************************/
		/** handleRemoveFilterButton */
		
		protected function handleRemoveFilterButton(e:MouseEvent):void
		{
		var filterList:ComboBox = m_view[getEntityNamePrefix()+FILTERLIST];
		var selectedItem:String = filterList.selectedItem as String;
		var filters:Array = [];
		var filterStringList:Array = [];
		
			for (var count:int=0; count<m_filterStringList.length; count++)
			{
			var filterString:String = m_filterStringList[count];
			
				if (filterString == selectedItem)
				{
					continue;
				}
				filters[filters.length] = m_filters[count];
				filterStringList[filterStringList.length] = filterString;
			}
			
			m_filters = filters;
			m_filterStringList = filterStringList;
			filterList.dataProvider = m_filterStringList;
			
			if (filterStringList.length > 0)
			{
				filterList.selectedItem = m_filterStringList[0];
			}
			else
			{
			var removeFilterButton:Button = m_view[getEntityNamePrefix()+REMOVEFILTERBUTTON];
			
				removeFilterButton.enabled = false;
			}
					
			m_entityController.loadTable(false);
			this.filter();

		} // handleRemoveFilterButton
		
		/*******************************************************************/

	} // TableFilter

} // package

/*******************************************************************/
// End of file
/*******************************************************************/ 


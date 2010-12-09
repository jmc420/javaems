/*******************************************************************/
/*
	File: EntityController.as

	Author: James Cowan
*/
/*******************************************************************/

package org.jems.client.controller
{
	import flash.events.MouseEvent;
	
	import mx.collections.ArrayCollection;
	import mx.containers.TitleWindow;
	import mx.controls.Alert;
	import mx.controls.DataGrid;
	import mx.core.UIComponent;
	import mx.events.CloseEvent;
	import mx.managers.PopUpManager;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;
	
	import org.jems.client.service.IEntityService;
	import org.jems.client.service.IEntityServiceFactory;

	public class EntityController extends Controller implements IEntityController
	{	
		public var		m_view:UIComponent;
		public var		m_entityName:String;
		public var		m_entity:Object;
		
		protected var	m_data:ArrayCollection;
		protected var	m_entityFactory:IEntityFactory;
		protected var 	m_entityServiceFactory:IEntityServiceFactory;
		protected var	m_formFactory:IFormFactory;
		protected var	m_entityMetaDataController:EntityMetaDataController;
		protected var	m_entityService:IEntityService;
		protected var	m_totalRows:int;
		protected var	m_tableService:IEntityService;
		protected var	m_tableFilter:TableFilter;
		protected var	m_tableSort:TableSort;
		protected var	m_tablePager:TablePager;
		protected var	m_tableRowCountService:IEntityService;
				
		public function EntityController(entityServiceFactory:IEntityServiceFactory, entityFactory:IEntityFactory=null, formFactory:IFormFactory=null):void
		{
			m_entityServiceFactory = entityServiceFactory;
			m_entityFactory = entityFactory;
			m_formFactory = formFactory;
			m_entityMetaDataController = EntityMetaDataController.getInstance(entityServiceFactory, entityFactory);
			m_totalRows = 0;
		}
		
		/*******************************************************************/
		// public methods
		/*******************************************************************/
		/** getData */
		
		public function getData():ArrayCollection
		{
			return m_data;
			
		} // getData
				
		/*******************************************************************/
		/** getDatagrid */
		
		public function getDataGrid():DataGrid
		{
			return m_view.getChildByName(TABLE) as DataGrid;
			
		} // getDataGrid
		
		/*******************************************************************/
		/** getEntityFactory */
		
		public function getEntityFactory():IEntityFactory
		{
			return m_entityFactory;
			
		} // getEntityFactory
		
		/*******************************************************************/
		/** getEntityName */
		
		public function getEntityName():String
		{
			return m_entityName;
			
		} // getEntityName
		
		/*******************************************************************/
		/** getTableRowCount */
		
		public function getTableRowCount():int
		{
			return m_totalRows;
			
		} // getTableRowCount
		
		/*******************************************************************/
		/** handleCreationComplete */

		public function handleCreationComplete():void
		{
		var dataGrid:DataGrid = getDataGrid();
			
			m_tableFilter = new TableFilter(m_entityServiceFactory, m_view, this);
			m_tableSort = new TableSort(this);
			dataGrid.doubleClickEnabled = true;
			dataGrid.addEventListener(MouseEvent.DOUBLE_CLICK, handleEditEvent);

  			m_entityService = m_entityServiceFactory.getEntityService(handleUpdateResult);
  			m_tableService =  m_entityServiceFactory.getEntityService(handleTableResult);
  			m_tableRowCountService = m_entityServiceFactory.getEntityService(handleTableRowCountResult);
  			
   			setButtonHandler(m_view, ADDBUTTON, handleAddEvent, false);
   			setButtonHandler(m_view, DELETEBUTTON, handleDeleteEvent, false);
   			setButtonHandler(m_view, EDITBUTTON, handleEditEvent, false);
   			setButtonHandler(m_view, REFRESHBUTTON, handleRefreshEvent, false);
    			   				
   			if (m_view.hasOwnProperty(TablePager.NEXTBUTTON))
   			{
   				m_tablePager = new TablePager(m_view, this);
   				readTableRowCount();
   			}
   			else
   			{
  				readTable();
  			}
  			
		} // handleCreationComplete
		
		/*******************************************************************/
		/** isPaged */
		
		public function isPaged():Boolean
		{
			if (m_tablePager == null)
			{
				return false;
			}
			
			return true;
			
		} // isPaged
		
		/*******************************************************************/
		/** loadTable */
		
		public function loadTable(reset:Boolean): void
		{
			if (reset && isPaged())
			{
				m_tablePager.resetCurrentPage();
			}
			
			if (isPaged())
			{
				readTableRowCount();	
			}
			else
			{
				readTable();
			}
			
		} // loadTable		
		
		/*******************************************************************/
		// protected methods
		/*******************************************************************/
		/** closePopupForm */
		
		protected function closePopupForm():void
		{
		var wdw:TitleWindow = getForm();

			PopUpManager.removePopUp(wdw);
			
		} // closePopupForm
		
		/*******************************************************************/	
		/** createEntity */
		
		protected function createEntity(entity:Object):void
		{
			m_entityService.create(entity);
			
		} // createEntity
		
		/*******************************************************************/	
		/** deleteEntity */
		
		protected function deleteEntity(entity:Object):void
		{
			m_entityService.remove(entity);
			
		} // deleteEntity
		
		/*******************************************************************/
		/** displaySelectRowError */
		
		protected function displaySelectRowError():void
		{
			Alert.show("Please select a row");
			
		} // displaySelectRowError
		
		/*******************************************************************/
		/** getForm */
		
		protected function getForm():TitleWindow
		{
			return m_formFactory.getForm(m_entityName);
			
		} // getForm
		
		/*******************************************************************/
		/** handleAddEvent */
		
		protected function handleAddEvent(e:MouseEvent):void
		{
		var form:TitleWindow = showPopupForm(handleCloseFormAddEvent);
		
			m_entity = m_entityMetaDataController.createEntity(m_entityName);
			m_entityMetaDataController.bindObjectToForm(m_entityName, m_entity, form);

		} // handleAddEvent
		
		/*******************************************************************/
		/** handleCancelFormEvent */
		
		protected function handleCancelFormEvent(e:MouseEvent):void
		{
			closePopupForm();
			
		} // handleCancelFormEvent
		
		/*******************************************************************/
		/** handleCloseFormAddEvent */
		
		protected function handleCloseFormAddEvent(e:MouseEvent):void
		{
			try
			{
			var form:TitleWindow = getForm();
			var entity:Object = m_entityMetaDataController.bindFormToObject(m_entityName, m_entity, form);
		
				closePopupForm();
				createEntity(entity);
			}
			catch (error:Error)
			{
				Alert.show(error.message);
			}
			
		} // handleCloseAddFormEvent
		
		/*******************************************************************/
		/** handleCloseFormUpdateEvent */
		
		protected function handleCloseFormUpdateEvent(e:MouseEvent):void
		{
		var form:TitleWindow = getForm();
		var entity:Object = m_entityMetaDataController.bindFormToObject(m_entityName, m_entity, form);
		
			closePopupForm();
			refreshTable();
			updateEntity(entity);
			
		} // handleCloseFormUpdateEvent
		
		/*******************************************************************/
		/** handleDeleteEvent */
		
		protected function handleDeleteEvent(e:MouseEvent):void
		{
		var dataGrid:DataGrid = m_view.getChildByName(TABLE) as DataGrid;
		
			if (dataGrid == null)
			{
				return;
			}
	
			var selectedIndex:int = dataGrid.selectedIndex;
			
			if (selectedIndex < 0)
			{
				displaySelectRowError();
				return;
			}
			
			Alert.show("Are you sure you want to delete this row?", "Delete Row", Alert.YES | Alert.NO, null, handleDeleteCloseEvent, null, Alert.YES );
			
		} // handleDeleteEvent
		
		/*******************************************************************/
		/** handleDeleteCloseEvent */
		
		protected function handleDeleteCloseEvent(e:CloseEvent):void
		{
			if (e.detail != Alert.YES)
			{
				return;
			}
			
			var dataGrid:DataGrid = m_view.getChildByName(TABLE) as DataGrid;
		
			if (dataGrid == null)
			{
				return;
			}
	
			var selectedIndex:int = dataGrid.selectedIndex;
			
			if (selectedIndex < 0)
			{
				return;
			}
			
			var entity:Object = dataGrid.selectedItem;
			
			deleteEntity(entity);
			
		} // handleDeleteCloseEvent
		
		/*******************************************************************/
		/** handleEditEvent */
		
		protected function handleEditEvent(e:MouseEvent):void
		{
		var dataGrid:DataGrid = m_view.getChildByName(TABLE) as DataGrid;
		
			if (dataGrid == null)
			{
				return;
			}
	
			var selectedIndex:int = dataGrid.selectedIndex;
			
			if (selectedIndex < 0)
			{
				displaySelectRowError();
				return;
			}

			var form:TitleWindow = showPopupForm(handleCloseFormUpdateEvent);
			
			m_entity = dataGrid.selectedItem;
		
			m_entityMetaDataController.bindObjectToForm(m_entityName, m_entity, form);
			
		} // handleEditEvent
		
		/*******************************************************************/
		/** handleRefreshEvent */
		
		protected function handleRefreshEvent(e:MouseEvent):void
		{
			readTable();
			
		} // handleRefreshEvent
		
		/*******************************************************************/
		/** handleTableResult */

		protected function handleTableResult(e:ResultEvent):void
		{
			m_data = ArrayCollection(e.result);
			
			refreshTable();
							
		} // handleTableResult
		
		/*******************************************************************/
		/** handleTableRowCountResult */
		
		protected function handleTableRowCountResult(e:ResultEvent):void
		{
			m_totalRows = int(e.result);
			readTable();
			
		} // handleTableRowCountResult
		
		/*******************************************************************/
		/** handleUpdateResult */

		protected function handleUpdateResult(e:ResultEvent):void
		{
				readTable();	// reload table
				
		} // handleUpdateResult
			
		/*******************************************************************/	
		/** readTable */
		
		protected function readTable():void
		{
		var filters:Array = m_tableFilter.getFilters();
		var sorts:Array = m_tableSort.getSort();
		
			if (isPaged())
			{
			var startRow:int = m_tablePager.getPageStartRow();
			var pageSize:int = m_tablePager.getPageSize();
			
				m_tableService.getPagedList(m_entityName, filters, sorts, startRow, pageSize);
			}
			else
			{
				m_tableService.getList(m_entityName, filters, sorts);
			}
			
		} // readTable
		
		/*******************************************************************/
		/** readTableRowCount */
		
		protected function readTableRowCount(): void
		{
			m_tableRowCountService.getListCount(m_entityName, m_tableFilter.getFilters());
			
		} // readTableRowCount
		
		/*******************************************************************/
		/** refreshTable */

		protected function refreshTable():void
		{
			if (isPaged())
			{
				m_tablePager.updatePageLabel();
			}
			
			m_tableFilter.filter();
			m_tableSort.sort();
			
			getDataGrid().dataProvider = m_data;	
						
		} // refreshTable
		
		/*******************************************************************/
		/** showPopupForm */
		
		protected function showPopupForm(saveHandler:Function):TitleWindow
		{
		var wdw:TitleWindow = getForm();
		
			PopUpManager.addPopUp(wdw, m_view, true);
			PopUpManager.centerPopUp(wdw);
			setButtonHandler(wdw, OKBUTTON, saveHandler, true);
			setButtonHandler(wdw, CANCELBUTTON, handleCancelFormEvent, true);
			return wdw;
			
		} // showPopupForm
		
		/*******************************************************************/	
		/** updateEntity */
		
		protected function updateEntity(entity:Object):void
		{
			m_entityService.update(entity);
			
		} // updateEntity
		
	} // EntityController
	
} // package

/*******************************************************************/
// End of file
/*******************************************************************/ 


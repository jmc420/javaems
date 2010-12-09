/*******************************************************************/
/*
	File: TablePager.as

	Author: James Cowan
*/
/*******************************************************************/

package org.jems.client.controller
{
	import flash.events.MouseEvent;
	
	import mx.controls.Alert;
	import mx.controls.DataGrid;
	import mx.core.UIComponent;
	
	public class TablePager extends Controller
	{
		public var		m_view:UIComponent;
		
		protected var	m_currentPage:int;
		protected var	m_entityController:IEntityController;
		protected var	m_pageSize:int;
		
		public static const	FIRSTBUTTON:String			= "m_firstButton";
		public static const	LASTBUTTON:String			= "m_lastButton";
		public static const	NEXTBUTTON:String			= "m_nextButton";
		public static const	PAGECOUNTLABEL:String		= "m_pageCountLabel";
		public static const	PAGESIZE:String				= "m_pageSize";
		public static const	PAGESIZEBUTTON:String		= "m_pageSizeButton";
		public static const	PREVIOUSBUTTON:String		= "m_previousButton";
		
		public function TablePager(view:UIComponent, entityController:IEntityController, pageSize:int = 500):void
		{
			m_view = view;
			m_entityController = entityController;
			m_pageSize = pageSize;
		
			var dataGrid:DataGrid = m_entityController.getDataGrid(); 
		
			setButtonHandler(m_view, FIRSTBUTTON, handleFirstPageEvent, false);
   			setButtonHandler(m_view, LASTBUTTON, handleLastPageEvent, false);
   			setButtonHandler(m_view, NEXTBUTTON, handleNextPageEvent, false);
   			setButtonHandler(m_view, PREVIOUSBUTTON, handlePreviousPageEvent, false);
   			setButtonHandler(m_view, PAGESIZEBUTTON, handlePageSizeEvent, false);
   			
   			m_currentPage = 0;
   			setPageSize();
		}
		
		/*******************************************************************/
		// public methods
		/*******************************************************************/
		/** getCurrentPage */
		
		public function getCurrentPage():int
		{
			return m_currentPage;
			
		} // getCurrentPage
		
		/*******************************************************************/
		/** getPageCount */
		
		public function getPageCount():int
		{
		var rowCount:int = m_entityController.getTableRowCount();
		
			if (rowCount == 0)
			{
				return 0;
			}
			
			return rowCount / m_pageSize;
			
		} // getPageCount
		
		/*******************************************************************/
		/** getPageSize */
		
		public function getPageSize():int
		{
			return m_pageSize;
			
		} // getPageSize
		
		/*******************************************************************/
		/** getPageStartRow */
		
		public function getPageStartRow():int
		{
			return m_currentPage * m_pageSize;
			
		} // getPageStartRow
		
		/*******************************************************************/
		/** resetCurrentPage */
		
		public function resetCurrentPage():void
		{
			m_currentPage = 0;
			
		} // resetCurrentPage
		
		/*******************************************************************/
		/** updatePageLabel */
		
		public function updatePageLabel():void
		{
			m_view[PAGECOUNTLABEL].text = "Page "+(getCurrentPage()+1) + " of "+(getPageCount()+1);
			
		} // updatePageLabel
		
		/*******************************************************************/
		// protected methods
		/*******************************************************************/
		/** handleFirstPageEvent */
		
		protected function handleFirstPageEvent(e:MouseEvent = null):void
		{
			m_currentPage = 0;
			m_entityController.loadTable(false);
			
		} // handleFirstPageEvent
		
		/*******************************************************************/
		/** handleLastPageEvent */
		
		protected function handleLastPageEvent(e:MouseEvent = null): void
		{
			m_currentPage = getPageCount();
			m_entityController.loadTable(false);
			
		} // handleLastPageEvent
		
		/*******************************************************************/
		/** handleNextPageEvent */
		
		protected function handleNextPageEvent(e:MouseEvent = null): void
		{
			if (m_currentPage < getPageCount())
			{
				m_currentPage++;
			}
			m_entityController.loadTable(false);
			
		} // handleNextPageEvent
		
		/*******************************************************************/
		/** handlePageSizeEvent */
		
		protected function handlePageSizeEvent(e:MouseEvent = null):void
		{
		var pageSize:int = parseInt(m_view[PAGESIZE].text);
			
			if (isNaN(pageSize) || pageSize <= 0)
			{
				Alert.show("Invalid page size");
				return;
			}
			
			m_pageSize = pageSize;
			m_currentPage = 0;
			m_entityController.loadTable(true);
			
		} // handlePageSizeEvent
		
		/*******************************************************************/
		/** handlePreviousPageEvent */
		
		protected function handlePreviousPageEvent(e:MouseEvent = null): void
		{
			if (m_currentPage > 0)
			{
				m_currentPage--;
			}
			m_entityController.loadTable(false);
			
		} // handlePreviousPageEvent
		
		/*******************************************************************/
		/** setPageSize */
		
		protected function setPageSize():void
		{
			m_view[PAGESIZE].text = m_pageSize.toString();
		
		} // setPageSize
		
		/*******************************************************************/

	} // TablePager

} // package

/*******************************************************************/
// End of file
/*******************************************************************/ 


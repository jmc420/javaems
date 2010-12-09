/*******************************************************************/
/*
	File: Controller.as

	Author: James Cowan
*/
/*******************************************************************/

package org.jems.client.controller
{
	import flash.events.MouseEvent;
	
	import mx.controls.Button;
	import mx.core.Application;
	import mx.core.UIComponent;
	
	public class Controller
	{
		protected static const	ADDBUTTON:String		= "m_addButton";
		protected static const	CANCELBUTTON:String		= "m_cancelButton";
		protected static const	DELETEBUTTON:String		= "m_deleteButton";
		protected static const	EDITBUTTON:String		= "m_editButton";
		protected static const	FIRSTBUTTON:String		= "m_firstButton";
		protected static const	LASTBUTTON:String		= "m_lastButton";
		protected static const	NEXTBUTTON:String		= "m_nextButton";
		protected static const	OKBUTTON:String			= "m_okButton";
		protected static const	PAGESIZE:String			= "m_pageSize";
		protected static const	PAGESIZEBUTTON:String		= "m_pageSizeButton";
		protected static const	PREVIOUSBUTTON:String		= "m_previousButton";
		protected static const	REFRESHBUTTON:String		= "m_refreshButton";
		protected static const	TABLE:String			= "m_table";
		
		protected var			m_eventTable:Array;
	
		public function Controller():void
		{
			m_eventTable = new Array();	
		}
		
		/*******************************************************************/
		// protected methods
		/*******************************************************************/	
		/** setButtonHandler */
		
		protected function setButtonHandler(parent:UIComponent, buttonName:String, handler:Function, throwError:Boolean):void
		{
			if (parent.hasOwnProperty(buttonName))
			{
			var button:Button = parent[buttonName];
			var oldHandler:Function = m_eventTable[buttonName];
			
				if (oldHandler != null)
				{
					button.removeEventListener(MouseEvent.CLICK, oldHandler);
				}
			
				button.addEventListener(MouseEvent.CLICK, handler);
				m_eventTable[buttonName] = handler;
 				return;
			}
			
			if (throwError)
			{
				throw new Error(buttonName+" is missing");				
			}
			
		} // setButtonHandler
	
	} // Controller

} // package

/*******************************************************************/
// End of file
/*******************************************************************/ 

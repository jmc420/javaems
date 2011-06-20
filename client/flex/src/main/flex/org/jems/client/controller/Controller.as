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
		protected static const	ADDBUTTON:String		= "AddButton";
		protected static const	CANCELBUTTON:String		= "CancelButton";
		protected static const	DELETEBUTTON:String		= "DeleteButton";
		protected static const	EDITBUTTON:String		= "EditButton";
		protected static const	FIRSTBUTTON:String		= "FirstButton";
		protected static const	LASTBUTTON:String		= "LastButton";
		protected static const	NEXTBUTTON:String		= "NextButton";
		protected static const	OKBUTTON:String			= "OkButton";
		protected static const	PAGESIZE:String			= "PageSize";
		protected static const	PAGESIZEBUTTON:String	= "PageSizeButton";
		protected static const	PREVIOUSBUTTON:String	= "PreviousButton";
		protected static const	REFRESHBUTTON:String	= "RefreshButton";
		protected static const	TABLE:String			= "Table";
		
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

/*******************************************************************/
/*
	File: DaoFilter.as
*/
/*******************************************************************/

package org.jems.dao.types
{
	
	[Bindable]
    [RemoteClass(alias="org.jems.dao.types.DaoFilter")]
    
	public class DaoFilter extends DaoParameter
	{
		protected var		m_booleanOperation:String;
		protected var		m_endExpression:Boolean;
		protected var		m_operation:String;
		protected var		m_startExpression:Boolean;
	
		public function DaoFilter():void
		{
		}

		/*******************************************************************/
		/** get booleanOperation */

		public function get booleanOperation():String
		{
			return m_booleanOperation;

		} // get booleanOperation

		/*******************************************************************/
		/** get endExpression */

		public function get endExpression():Boolean
		{
			return m_endExpression;

		} // get endExpression

		/*******************************************************************/
		/** get operation */

		public function get operation():String
		{
			return m_operation;

		} // get operation

		/*******************************************************************/
		/** get startExpression */

		public function get startExpression():Boolean
		{
			return m_startExpression;

		} // get startExpression

		/*******************************************************************/
		/** set booleanOperation */

		public function set booleanOperation(booleanOperation:String):void
		{
			m_booleanOperation = booleanOperation;

		} // set booleanOperation

		/*******************************************************************/
		/** set endExpression */

		public function set endExpression(endExpression:Boolean):void
		{
			m_endExpression = endExpression;

		} // set endExpression

		/*******************************************************************/
		/** set operation */

		public function set operation(operation:String):void
		{
			m_operation = operation;

		} // set operation

		/*******************************************************************/
		/** set startExpression */

		public function set startExpression(startExpression:Boolean):void
		{
			m_startExpression = startExpression;

		} // set startExpression
	
	} // DaoFilter

} // end of package

/*******************************************************************/
// End of file
/*******************************************************************/ 

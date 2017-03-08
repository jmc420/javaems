/*******************************************************************/
/*
	File: DaoParameter.as
*/
/*******************************************************************/

package org.jems.dao.types
{
	
	[Bindable]
    [RemoteClass(alias="org.jems.dao.types.DaoParameter")]
    
	public class DaoParameter
	{
		protected var		m_className:String;
		protected var		m_column:String;
		protected var		m_value:Object;
	
		public function DaoParameter():void
		{
		}

		/*******************************************************************/
		/** get className */

		public function get className():String
		{
			return m_className;

		} // get className

		/*******************************************************************/
		/** get column */

		public function get column():String
		{
			return m_column;

		} // get column

		/*******************************************************************/
		/** get value */

		public function get value():Object
		{
			return m_value;

		} // get value

		/*******************************************************************/
		/** set className */

		public function set className(className:String):void
		{
			m_className = className;

		} // set className

		/*******************************************************************/
		/** set column */

		public function set column(column:String):void
		{
			m_column = column;

		} // set column

		/*******************************************************************/
		/** set value */

		public function set value(value:Object):void
		{
			m_value = value;

		} // set value
	
	} // DaoParameter

} // end of package

/*******************************************************************/
// End of file
/*******************************************************************/ 

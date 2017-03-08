/*******************************************************************/
/*
	File: DaoSort.as
*/
/*******************************************************************/

package org.jems.dao.types
{
	
	[Bindable]
    [RemoteClass(alias="org.jems.dao.types.DaoSort")]
    
	public class DaoSort
	{
		protected var		m_ascending:Boolean;
		protected var		m_column:String;
	
		public function DaoSort():void
		{
		}

		/*******************************************************************/
		/** get ascending */

		public function get ascending():Boolean
		{
			return m_ascending;

		} // get ascending

		/*******************************************************************/
		/** get column */

		public function get column():String
		{
			return m_column;

		} // get column

		/*******************************************************************/
		/** set ascending */

		public function set ascending(ascending:Boolean):void
		{
			m_ascending = ascending;

		} // set ascending

		/*******************************************************************/
		/** set column */

		public function set column(column:String):void
		{
			m_column = column;

		} // set column
	
	} // DaoSort

} // end of package

/*******************************************************************/
// End of file
/*******************************************************************/ 

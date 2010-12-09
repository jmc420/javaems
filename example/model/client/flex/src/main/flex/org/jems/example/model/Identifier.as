/*******************************************************************/
/*
	File: Identifier.as
*/
/*******************************************************************/

package org.jems.example.model
{
	
	[Bindable]
    [RemoteClass(alias="org.jems.example.model.Identifier")]
    
	public class Identifier
	{
		protected var		m_id:Number;
	
		public function Identifier():void
		{
		}

		/*******************************************************************/
		/** get id */

		public function get id():Number
		{
			return m_id;

		} // get id

		/*******************************************************************/
		/** set id */

		public function set id(id:Number):void
		{
			m_id = id;

		} // set id
	
	} // Identifier

} // end of package

/*******************************************************************/
// End of file
/*******************************************************************/ 

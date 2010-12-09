/*******************************************************************/
/*
	File: Artist.as
*/
/*******************************************************************/

package org.jems.example.model
{
	
	[Bindable]
    [RemoteClass(alias="org.jems.example.model.Artist")]
    
	public class Artist extends Identifier
	{
		protected var		m_name:String;
	
		public function Artist():void
		{
		}

		/*******************************************************************/
		/** get name */

		public function get name():String
		{
			return m_name;

		} // get name

		/*******************************************************************/
		/** set name */

		public function set name(name:String):void
		{
			m_name = name;

		} // set name
	
	} // Artist

} // end of package

/*******************************************************************/
// End of file
/*******************************************************************/ 

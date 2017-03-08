/*******************************************************************/
/*
	File: Track.as
*/
/*******************************************************************/

package org.jems.example.model
{
	
	[Bindable]
    [RemoteClass(alias="org.jems.example.model.Track")]
    
	public class Track extends Identifier
	{
		protected var		m_album:Album;
		protected var		m_length:int;
		protected var		m_name:String;
	
		public function Track():void
		{
		}

		/*******************************************************************/
		/** get album */

		public function get album():Album
		{
			return m_album;

		} // get album

		/*******************************************************************/
		/** get length */

		public function get length():int
		{
			return m_length;

		} // get length

		/*******************************************************************/
		/** get name */

		public function get name():String
		{
			return m_name;

		} // get name

		/*******************************************************************/
		/** set album */

		public function set album(album:Album):void
		{
			m_album = album;

		} // set album

		/*******************************************************************/
		/** set length */

		public function set length(length:int):void
		{
			m_length = length;

		} // set length

		/*******************************************************************/
		/** set name */

		public function set name(name:String):void
		{
			m_name = name;

		} // set name
	
	} // Track

} // end of package

/*******************************************************************/
// End of file
/*******************************************************************/ 

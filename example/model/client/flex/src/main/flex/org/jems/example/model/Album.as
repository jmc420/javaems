/*******************************************************************/
/*
	File: Album.as
*/
/*******************************************************************/

package org.jems.example.model
{
	
	[Bindable]
    [RemoteClass(alias="org.jems.example.model.Album")]
    
	public class Album extends Identifier
	{
		protected var		m_artist:Artist;
		protected var		m_name:String;
	
		public function Album():void
		{
		}

		/*******************************************************************/
		/** get artist */

		public function get artist():Artist
		{
			return m_artist;

		} // get artist

		/*******************************************************************/
		/** get name */

		public function get name():String
		{
			return m_name;

		} // get name

		/*******************************************************************/
		/** set artist */

		public function set artist(artist:Artist):void
		{
			m_artist = artist;

		} // set artist

		/*******************************************************************/
		/** set name */

		public function set name(name:String):void
		{
			m_name = name;

		} // set name
	
	} // Album

} // end of package

/*******************************************************************/
// End of file
/*******************************************************************/ 

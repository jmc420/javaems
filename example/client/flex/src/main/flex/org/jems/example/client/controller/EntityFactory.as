/*******************************************************************/
/*
	File: EntityFactory.as
*/
/*******************************************************************/

package org.jems.example.client.controller
{
	import org.jems.client.controller.IEntityFactory;
	import org.jems.example.model.Artist;
	import org.jems.example.model.Album;
	import org.jems.example.model.Track;
	
	public class EntityFactory implements IEntityFactory
	{
		protected static var	m_entityFactoryTable:Array;
		protected static var	m_instance:EntityFactory;

		public function EntityFactory():void
		{
			m_entityFactoryTable = getEntityFactoryTable();
		}
		
		/*******************************************************************/
		// public methods
		/*******************************************************************/
		/** createEntity */
		
		public function createEntity(entity:String):Object
		{
 		var factoryFN:Function = m_entityFactoryTable[entity];
			
			if (factoryFN == null)
			{
				throw new Error("Cannot create entity: "+ entity);
			}
				
			return factoryFN.call();
			
		} // createEntity

		/*******************************************************************/
		// protected methods
		/*******************************************************************/
		/** createArtist */
		
		protected function createArtist():Artist
		{
 			return new Artist();
			
		} // createArtist
		
		/*******************************************************************/
		/** createAlbum */
		
		protected function createAlbum():Album
		{
 			return new Album();
			
		} // createAlbum
		
		/*******************************************************************/
		/** createTrack */
		
		protected function createTrack():Track
		{
 			return new Track();
			
		} // createTrack
		
		
		/*******************************************************************/
		/** getEntityFactoryTable */
		
		protected function getEntityFactoryTable():Array
		{
		var entityFactoryTable:Array = new Array();
		
			entityFactoryTable["Artist"] = createArtist;
			entityFactoryTable["Album"] = createAlbum;
			entityFactoryTable["Track"] = createTrack;
		
			return entityFactoryTable;
			
		} // getEntityFactoryTable
		
		/*******************************************************************/
		// static methods
		/*******************************************************************/
		/** getInstance */
		
		public static function getInstance():EntityFactory
		{
			if (m_instance == null)
			{
				m_instance = new EntityFactory();	
			}
			
			return m_instance;
			
		} // getInstance
	
	} // EntityFactory
	
} // package

/*******************************************************************/
// End of file
/*******************************************************************/ 

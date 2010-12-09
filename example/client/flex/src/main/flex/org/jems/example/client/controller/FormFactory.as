/*******************************************************************/
/*
	File: FormFactory.as
*/
/*******************************************************************/

package org.jems.example.client.controller
{
	import org.jems.example.client.view.ArtistForm;
	import org.jems.example.client.view.AlbumForm;
	import org.jems.example.client.view.TrackForm;
	
	import org.jems.client.controller.IFormFactory;
	import mx.containers.TitleWindow;
	
	public class FormFactory implements IFormFactory
	{
		protected static var	m_formTable:Array;
		protected static var	m_formFactoryTable:Array;
		protected static var	m_instance:FormFactory;

		public function FormFactory():void
		{
			m_formFactoryTable = getFormFactoryTable();
			m_formTable = [];
		}
		
		/*******************************************************************/
		// public methods
		/*******************************************************************/
		/** getForm */
		
		public function getForm(entity:String):TitleWindow
		{
		var form:TitleWindow = m_formTable[entity];
		
			if (form == null)
			{
			var factoryFN:Function = m_formFactoryTable[entity]
			
				if (factoryFN == null)
				{
					throw new Error("No Form for entity: "+ entity);
				}
				
				form = factoryFN.call();
				m_formTable[entity] = form;
			}
			
			return form;
			
		} // getForm

		/*******************************************************************/
		// protected methods
		/*******************************************************************/
		/** createArtistForm */
		
		protected function createArtistForm():ArtistForm
		{
 			return new ArtistForm();
			
		} // createArtistForm
		
		/*******************************************************************/
		/** createAlbumForm */
		
		protected function createAlbumForm():AlbumForm
		{
 			return new AlbumForm();
			
		} // createAlbumForm
		
		/*******************************************************************/
		/** createTrackForm */
		
		protected function createTrackForm():TrackForm
		{
 			return new TrackForm();
			
		} // createTrackForm
		
		
		/*******************************************************************/
		/** getFormFactoryTable */
		
		protected function getFormFactoryTable():Array
		{
		var formFactoryTable:Array = new Array();
		
			formFactoryTable["Artist"] = createArtistForm;
			formFactoryTable["Album"] = createAlbumForm;
			formFactoryTable["Track"] = createTrackForm;
		
			return formFactoryTable;
			
		} // getFormFactoryTable
		
		/*******************************************************************/
		// static methods
		/*******************************************************************/
		/** getInstance */
		
		public static function getInstance():FormFactory
		{
			if (m_instance == null)
			{
				m_instance = new FormFactory();	
			}
			
			return m_instance;
			
		} // getInstance
	
	} // FormFactory
	
} // package

/*******************************************************************/
// End of file
/*******************************************************************/ 

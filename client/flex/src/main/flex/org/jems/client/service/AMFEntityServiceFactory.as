/*******************************************************************/
/*
	File: AMFEntityServiceFactory.as

	Author: James Cowan
*/
/*******************************************************************/

package org.jems.client.service
{		
	public class AMFEntityServiceFactory implements IEntityServiceFactory
	{
		protected var	m_channel:String;
		protected var	m_url:String;
		
		public function AMFEntityServiceFactory(channel:String, url:String):void
		{
			m_channel = channel;
			m_url = url;
		}

		/*******************************************************************/
		// public methods
		/*******************************************************************/
		/** getEntityService */
		
		public function getEntityService(resultHandler:Function=null, faultHandler:Function=null):IEntityService
		{
			return new AMFEntityService(m_channel, m_url, resultHandler, faultHandler);
			
		} // getEntityService
		
		/*******************************************************************/
		/** getEntityMetaDataService */
		
		public function getEntityMetaDataService(resultHandler:Function=null, faultHandler:Function=null):IEntityMetaDataService
		{
			return new AMFEntityMetaDataService(m_channel, m_url, resultHandler, faultHandler);
			
		} // getEntityMetaDataService
		

	} // AMFEntityServiceFactory
	
} // package

/*******************************************************************/
// End of file
/*******************************************************************/


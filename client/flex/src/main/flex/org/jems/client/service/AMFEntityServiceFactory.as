/*******************************************************************/
/*
	File: AMFEntityServiceFactory.as

	Author: James Cowan
*/
/*******************************************************************/

package org.jems.client.service
{		
	import mx.messaging.ChannelSet;

	public class AMFEntityServiceFactory implements IEntityServiceFactory
	{
		protected var	m_channelSet:ChannelSet;
		
		public function AMFEntityServiceFactory(channelSet:ChannelSet):void
		{
			m_channelSet = channelSet;
		}

		/*******************************************************************/
		// public methods
		/*******************************************************************/
		/** getEntityService */
		
		public function getEntityService(resultHandler:Function=null, faultHandler:Function=null):IEntityService
		{
			return new AMFEntityService(m_channelSet, resultHandler, faultHandler);
			
		} // getEntityService
		
		/*******************************************************************/
		/** getEntityMetaDataService */
		
		public function getEntityMetaDataService(resultHandler:Function=null, faultHandler:Function=null):IEntityMetaDataService
		{
			return new AMFEntityMetaDataService(m_channelSet, resultHandler, faultHandler);
			
		} // getEntityMetaDataService
		

	} // AMFEntityServiceFactory
	
} // package

/*******************************************************************/
// End of file
/*******************************************************************/


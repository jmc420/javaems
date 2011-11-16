/*******************************************************************/
/*
	File: AMFEntityMetaDataService.as

	Author: James Cowan
*/
/*******************************************************************/

package org.jems.client.service
{		
	import mx.messaging.ChannelSet;

	public class AMFEntityMetaDataService implements IEntityMetaDataService
	{
		protected var	m_service:AMFService;
		
		public function AMFEntityMetaDataService(channelSet:ChannelSet, resultHandler:Function=null, faultHandler:Function=null):void
		{
			m_service = new AMFService(channelSet, ServiceConstant.ENTITY_METADATA_SERVICE, resultHandler, faultHandler);
		}
		
		/*******************************************************************/
		// public methods
		/*******************************************************************/
		/** getEntityMetaData */
		
		public function getEntityMetaData():void
		{
			m_service.getRemoteObject().getEntityMetaData();
			
		} // getEntityMetaData
		
	} // AMFEntityMetaDataService
	
} // package

/*******************************************************************/
// End of file
/*******************************************************************/


/*******************************************************************/
/*
	File: AMFEntityService.as

	Author: James Cowan
*/
/*******************************************************************/

package org.jems.client.service
{
	import mx.messaging.ChannelSet;

	public class AMFEntityService implements IEntityService
	{
		protected var	m_service:AMFService;

		public function AMFEntityService(channelSet:ChannelSet, resultHandler:Function=null, faultHandler:Function=null):void
		{
			m_service = new AMFService(channelSet, ServiceConstant.ENTITY_SERVICE, resultHandler, faultHandler);
		}
		
		/*******************************************************************/
		// public methods
		/*******************************************************************/
		/** create */
		
		public function create(entity:Object):void
		{
			m_service.getRemoteObject().create(entity);
			
		} // create

		/*******************************************************************/
		/** get */
		
		public function get(entityName:String, filter:Array):void
		{
			m_service.getRemoteObject().get(entityName, filter);
			
		} // get
		
		/*******************************************************************/
		/** getList */
		
		public function getList(entityName:String, filter:Array, sort:Array):void
		{
			m_service.getRemoteObject().getList(entityName, filter, sort);
			
		} // getList
		
		/*******************************************************************/
		/** getListCount */
		
		public function getListCount(entityName:String, filter:Array):void
		{
			m_service.getRemoteObject().getListCount(entityName, filter);
			
		} // getListCount

		/*******************************************************************/
		/** getPagedList */
		
		public function getPagedList(entityName:String, filter:Array, sort:Array, startRow:int, pageSize:int):void
		{
			m_service.getRemoteObject().getList(entityName, filter, sort, startRow, pageSize);
			
		} // getPagedList
		
		/*******************************************************************/
		/** remove */
		
		public function remove(entity:Object):void
		{
			m_service.getRemoteObject().remove(entity);
			
		} // remove
		
		/*******************************************************************/
		/** update */
		
		public function update(entity:Object):void
		{
			m_service.getRemoteObject().update(entity);
			
		} // update
		

	} // AMFEntityService
	
} // package

/*******************************************************************/
// End of file
/*******************************************************************/


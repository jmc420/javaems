/*******************************************************************/
/*
	File: AMFService.as

	Author: James Cowan
*/
/*******************************************************************/

package org.jems.client.service
{		
	import mx.controls.Alert;
	import mx.messaging.ChannelSet;
	import mx.messaging.channels.AMFChannel;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;

	public class AMFService
	{
		protected var	m_dataReceived:Boolean;
		protected var	m_faultHandler:Function;
		protected var	m_loadCallBack:Function;
		protected var	m_remoteService:String;
		protected var	m_remoteObject:RemoteObject;
		protected var	m_url:String;
		protected var	m_resultHandler:Function;
		
		public static const	DEFAULT_CHANNEL:String = "my-amf";
		public static const	UNSECURED_CHANNEL:String = "my-amf-unsecured";
		
		public function AMFService(channel:String, url:String, remoteService:String, resultHandler:Function=null, faultHandler:Function=null):void
		{
			m_url = url;
			m_remoteService = remoteService;
			m_dataReceived = false;
			initialise(channel);
			
			m_remoteObject.addEventListener(FaultEvent.FAULT, handleFault);
			m_remoteObject.addEventListener(ResultEvent.RESULT, handleResult);
			
			addFaultEventListener(faultHandler);			
			addResultEventListener(resultHandler);
		}
		
		/*******************************************************************/
		// public methods
		/*******************************************************************/
		/** addFaultEventListener */
		
		public function addFaultEventListener(faultHandler:Function):void
		{
			m_faultHandler = faultHandler;
			
		} // addFaultEventListener
		
		/*******************************************************************/
		/** addResultEventListener */
		
		public function addResultEventListener(resultHandler:Function):void
		{
			m_resultHandler = resultHandler;
			
		} // addResultEventListener
		
		/*******************************************************************/
		/** close */
		
		public function close():void
		{
			m_remoteObject.channelSet.disconnectAll();
			
		} // close
		
		/*******************************************************************/
		/** getRemoteObject */
		
		public function getRemoteObject():RemoteObject
		{
			return m_remoteObject;
			
		} // getRemoteObject
		
		/*******************************************************************/
		/** hasReceivedData */
		
		public function hasReceivedData():Boolean
		{
			return m_dataReceived;
			
		} // hasReceivedData
		
		/*******************************************************************/
		// protected methods
		/*******************************************************************/
		/** handleFault */
		
		protected function handleFault(e:FaultEvent):void
		{
			if (m_faultHandler == null)
			{
				Alert.show(m_remoteService+" error: "+e.fault.faultString);
			}
			else
			{
				m_faultHandler.call(this, e);
			}
			
		} // handleFault

		/*******************************************************************/
		/** handleResult */

		protected function handleResult(e:ResultEvent):void
		{
			m_dataReceived = true;
			
			if (m_resultHandler != null)
			{
				m_resultHandler.call(this, e);
			}
					
		} // handleResult
			
		/*******************************************************************/
		/** initialise */
		
		protected function initialise(channelName:String):void
		{
		var channel:AMFChannel = new AMFChannel(channelName, m_url);
		var channelSet:ChannelSet = new ChannelSet();
				
			// remote binding
				
			channelSet.addChannel(channel);
			m_remoteObject = new RemoteObject();
			m_remoteObject.channelSet = channelSet;
			m_remoteObject.destination = m_remoteService;
			
		} // initialise
		
	} // AMFService
		
} // package

/*******************************************************************/
// End of file
/*******************************************************************/


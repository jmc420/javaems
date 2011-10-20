/*******************************************************************/
/*
File: ConsumerService.as

Author: James Cowan
*/
/*******************************************************************/

package org.jems.client.service
{		
	import mx.controls.Alert;
	import mx.events.CloseEvent;
	import mx.messaging.ChannelSet;
	import mx.messaging.Consumer;
	import mx.messaging.MessageAgent;
	import mx.messaging.Producer;
	import mx.messaging.channels.AMFChannel;
	import mx.messaging.events.ChannelEvent;
	import mx.messaging.events.MessageEvent;
	import mx.messaging.events.MessageFaultEvent;
	import mx.messaging.messages.AsyncMessage;
	import mx.messaging.messages.IMessage;
	
	public class ConsumerService extends MessageService
	{
		protected var	m_consumer:Consumer;
		protected var	m_destination:String;
		protected var	m_channelSet:ChannelSet;
				
		public function ConsumerService(channelSet:ChannelSet, destination:String):void
		{
			m_channelSet = channelSet;
			m_destination = destination;
			initialise();
		}
		
		/*******************************************************************/
		// public methods
		/*******************************************************************/
		/** close */
		
		public function close():void
		{
			m_consumer.removeEventListener(ChannelEvent.DISCONNECT, resubscriberHandler);
			m_consumer.channelSet.disconnectAll();
			
		} // close
		
		/*******************************************************************/
		/** getConsumer */
		
		public function getConsumer():Consumer
		{
			return m_consumer;
			
		} // getConsumer
		
		/*******************************************************************/
		/** receive */
		
		public function receive(callBack:Function):void
		{
			m_consumer.addEventListener(MessageEvent.MESSAGE, callBack);
			m_consumer.subscribe();
			
		} // receive
		
		/*******************************************************************/
		// protected method
		/*******************************************************************/
		/** faultHandler */
		
		protected function faultHandler(event:MessageFaultEvent):void
		{
		var msg:String = "Receive error: "+event.faultCode;
		
			displayError(msg);
			
		} // faultHandler
		
		/*******************************************************************/
		/** initialise */
		
		protected function initialise():void
		{
			m_consumer = new Consumer();
			initialiseMessageAgent(m_consumer, m_channelSet, m_destination, faultHandler, resubscriberHandler);
			
		} // initialise	
		
		/*******************************************************************/
		/** resubscriberHandler */
		
		protected function resubscriberHandler(event:ChannelEvent):void
		{
			m_consumer.subscribe();
			
		} // resubscriberHandler
		
	} // ConsumerService
	
} // package

/*******************************************************************/
// End of file
/*******************************************************************/


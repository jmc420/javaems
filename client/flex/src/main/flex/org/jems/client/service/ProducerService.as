/*******************************************************************/
/*
File: ProducerService.as

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
	
	public class ProducerService extends MessageService
	{
		protected var	m_producer:Producer;
		protected var	m_destination:String;
		protected var	m_url:Vector.<String>;
				
		public function ProducerService(url:Vector.<String>, destination:String):void
		{
			m_url = url;
			m_destination = destination;
			initialise();
		}
		
		/*******************************************************************/
		// public methods
		/*******************************************************************/
		/** close */
		
		public function close():void
		{
			m_producer.removeEventListener(ChannelEvent.DISCONNECT, reconnectHandler);
			m_producer.channelSet.disconnectAll();
			
		} // close
		
		/*******************************************************************/
		/** getProducer */
		
		public function getProducer():Producer
		{
			return m_producer;
			
		} // getProducer
		
		/*******************************************************************/
		/** send */
		
		public function send(msg:Object):void
		{
		var message:IMessage = new AsyncMessage();
			
			message.body = msg;
			
			m_producer.send(message);
			
		} // send
		
		/*******************************************************************/
		// protected method
		/*******************************************************************/
		/** faultHandler */
		
		protected function faultHandler(event:MessageFaultEvent):void
		{
		var msg:String = "Send error: "+event.faultCode;
			
			displayError(msg);
			
		} // faultHandler
		
		/*******************************************************************/
		/** initialise */
		
		protected function initialise():void
		{
		var producerChannelSet:ChannelSet = getChannelSet(m_url);

			m_producer = new Producer();
			initialiseMessageAgent(m_producer, producerChannelSet, m_destination, faultHandler, reconnectHandler);
			m_producer.connect();
			
		} // initialise	
		
		/*******************************************************************/
		/** reconnectHandler */
		
		protected function reconnectHandler(event:ChannelEvent):void
		{
			m_producer.connect();
			
		} // reconnectHandler	
		
	} // ProducerService
	
} // package

/*******************************************************************/
// End of file
/*******************************************************************/


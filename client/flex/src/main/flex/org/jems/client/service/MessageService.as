/*******************************************************************/
/*
	File: MessageService.as

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

	public class MessageService
	{
		protected var	m_consumer:Consumer;
		protected var	m_producer:Producer;
		protected var	m_destination:String;
		protected var	m_url:String;
		protected var	m_displaySendFaultMessage:Boolean;

		public function MessageService(url:String, destination:String):void
		{
			m_url = url;
			m_destination = destination;
			m_displaySendFaultMessage = false;
			initialise();
		}
		
		/*******************************************************************/
		// public methods
		/*******************************************************************/
		/** getConsumer */
		
		public function getConsumer():Consumer
		{
			return m_consumer;
			
		} // getConsumer
		
		/*******************************************************************/
		/** getProducer */
		
		public function getProducer():Producer
		{
			return m_producer;
			
		} // getProducer
		
		/*******************************************************************/
		/** receive */
		
		public function receive(callBack:Function):void
		{
			m_consumer.addEventListener(MessageEvent.MESSAGE, callBack);
			m_consumer.subscribe();
			
		} // receive
		
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
		/** initialise */
		
		protected function initialise():void
		{
		var amfPolling:AMFChannel = new AMFChannel("amflongpolling", m_url+"/messagebroker/amflongpolling");
		var amfLongPolling:AMFChannel = new AMFChannel("amfpolling", m_url+"/messagebroker/amfpolling");
		var channelSet:ChannelSet = new ChannelSet();

			channelSet.addChannel(amfLongPolling);				
			channelSet.addChannel(amfPolling);
			m_consumer = new Consumer();
			initialiseMessageAgent(m_consumer, channelSet, receiveFaultHandler, resubscriberHandler);
			m_producer = new Producer();
			initialiseMessageAgent(m_producer, channelSet, sendFaultHandler, reconnectHandler);
			
		} // initialise	
		
		/*******************************************************************/
		/** initialiseMessageAgent */
		
		protected function initialiseMessageAgent(messageAgent:MessageAgent, channelSet:ChannelSet, 
												  faultHandler:Function, reconnectHandler:Function):void
		{	
			messageAgent.channelSet = channelSet;
			messageAgent.destination = m_destination;
			messageAgent.addEventListener(MessageFaultEvent.FAULT, faultHandler);
			messageAgent.addEventListener(ChannelEvent.DISCONNECT, reconnectHandler);
			
		} // initialiseMessageAgent
		
		/*******************************************************************/
		/** receiveFaultHandler */
		
		protected function receiveFaultHandler(event:MessageFaultEvent):void
		{
			Alert.show("Error receiving from Message Service destination: "+m_destination+
			           " error: "+event.faultDetail);
			
		} // receiveFaultHandler	
				
		/*******************************************************************/
		/** reconnectHandler */
		
		protected function reconnectHandler(event:ChannelEvent):void
		{
			m_producer.connect();
			
		} // reconnectHandler	
		
		/*******************************************************************/
		/** resubscriberHandler */
		
		protected function resubscriberHandler(event:ChannelEvent):void
		{
			m_consumer.subscribe();
			
		} // resubscriberHandler
		
		/*******************************************************************/
		/** sendFaultHandler */
		
		protected function sendFaultHandler(event:MessageFaultEvent):void
		{
			if (!m_displaySendFaultMessage)
			{
				m_displaySendFaultMessage = true;
				Alert.show("Error sending to Message Service destination: "+m_destination+
			    	       " error: "+event.faultDetail,"",Alert.OK,null,sendFaultCloseHandler);
			}
			
		} // sendFaultHandler
				
		/*******************************************************************/
		/** sendFaultCloseHandler */
		
		protected function sendFaultCloseHandler(e:CloseEvent):void
		{
			m_displaySendFaultMessage = false;
			
		} // sendFaultCloseHandler
		
	} // MessageService
	
} // package

/*******************************************************************/
// End of file
/*******************************************************************/


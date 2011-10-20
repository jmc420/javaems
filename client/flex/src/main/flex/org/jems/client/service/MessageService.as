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
	import mx.messaging.channels.StreamingAMFChannel;
	import mx.messaging.events.ChannelEvent;
	import mx.messaging.events.MessageEvent;
	import mx.messaging.events.MessageFaultEvent;
	import mx.messaging.messages.AsyncMessage;
	import mx.messaging.messages.IMessage;

	public class MessageService
	{		
		protected var	m_displayError:Boolean;
		
		public static const	LONG_POLLING_CHANNEL:String = "amflongpolling";
		public static const	POLLING_CHANNEL:String = "amfpolling";
		public static const	STREAMING_CHANNEL:String = "streamingamf";
		
		public function MessageService():void
		{
			m_displayError = false;
		}
		
		/*******************************************************************/
		// public methods
		/*******************************************************************/
		
		/*******************************************************************/
		// protected method
		/*******************************************************************/
		/** displayError */
		
		protected function displayError(msg:String):void
		{
			if (!m_displayError)
			{
				m_displayError = true;
				Alert.show(msg,"Error",Alert.OK,null,handleDisplayErrorClose);
			}
			
		} // displayError
		
		/*******************************************************************/
		/** getChannelSet */
		
		protected function getChannelSet(urls:Vector.<String>):ChannelSet
		{
		var channelSet:ChannelSet = new ChannelSet();
		var now:Date = new Date();
		var id:String = now.getTime().toString();
		
			for (var count:int=0; count<urls.length; count++)
			{
			var url:String = urls[count];
			var channel:AMFChannel;
			
				if (url.indexOf(STREAMING_CHANNEL) >= 0)
				{
					channel = new StreamingAMFChannel(id, url);
				}
				else
				{
					channel = new AMFChannel(id, url);
				}
		
				channelSet.addChannel(channel);
			}
			
			return channelSet;
			
		} // getChannelSet
		
		/*******************************************************************/
		/** handleDisplayErrorClose */
		
		protected function handleDisplayErrorClose(e:CloseEvent):void
		{
			m_displayError = false;
			
		} // handleDisplayErrorClose
		
		/*******************************************************************/
		/** initialiseMessageAgent */
		
		protected function initialiseMessageAgent(messageAgent:MessageAgent, channelSet:ChannelSet, 
												  destination:String, faultHandler:Function, 
												  reconnectHandler:Function):void
		{	
			messageAgent.channelSet = channelSet;
			messageAgent.destination = destination;
			messageAgent.addEventListener(MessageFaultEvent.FAULT, faultHandler);
			messageAgent.addEventListener(ChannelEvent.DISCONNECT, reconnectHandler);
			
		} // initialiseMessageAgent
		
	} // MessageService
	
} // package

/*******************************************************************/
// End of file
/*******************************************************************/


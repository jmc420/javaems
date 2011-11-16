/*******************************************************************/
/*
File: ChannelFactory.as

Author: James Cowan
*/
/*******************************************************************/

package org.jems.client.service
{			
	import mx.messaging.ChannelSet;
	import mx.messaging.channels.AMFChannel;
	import mx.messaging.channels.SecureAMFChannel;
	import mx.messaging.channels.StreamingAMFChannel;

	public class ChannelFactory
	{
		static private var	m_table:Array = [];

		/*******************************************************************/
		// static public methods
		/*******************************************************************/
		/** createChannelSet */
		
		static public function createChannelSet(channelIdentities:Vector.<ChannelIdentity>):ChannelSet
		{
		var channelSet:ChannelSet = new ChannelSet();
			
			for (var count:int=0; count<channelIdentities.length; count++)
			{
			var channelIdentity:ChannelIdentity = channelIdentities[count];
			var channel:AMFChannel;
			var id:String = channelIdentity.getId();
			var url:String = channelIdentity.getUrl();
				
				if (channelIdentity.isStream())
				{
					channel = new StreamingAMFChannel(id, url);
				}
				else
				{
					if (channelIdentity.isSSL())
					{
						channel = new SecureAMFChannel(id, url);	
					}
					else
					{
						channel = new AMFChannel(id, url);
					}
				}
				
				channelSet.addChannel(channel);
			}
			
			return channelSet;
			
		} // createChannelSet
		
		/*******************************************************************/
		/** getChannelSet */
		
		static public function getChannelSet(channelIdentities:Vector.<ChannelIdentity>):ChannelSet
		{
		var key:String = getChannelSetKey(channelIdentities);
		var channelSet:ChannelSet = m_table[key];
			
			if (m_table[key] == null)
			{
				channelSet = createChannelSet(channelIdentities);
				m_table[key] = channelSet;
			}
			
			return channelSet;
			
		} // getChannelSet	
		
		/*******************************************************************/
		/** getSingleChannelSet */
		
		static public function getSingleChannelSet(id:String, url:String, isStream:Boolean=false, isSSL:Boolean=false, reuseChannel:Boolean=true):ChannelSet
		{
		var channelIdentities:Vector.<ChannelIdentity> = new Vector.<ChannelIdentity>;
		var channelIdentity:ChannelIdentity = new ChannelIdentity(id, url, isStream, isSSL);
			
			channelIdentities[channelIdentities.length] = channelIdentity;
			
			if (reuseChannel)
			{
				return ChannelFactory.getChannelSet(channelIdentities);
			}
			
			return ChannelFactory.createChannelSet(channelIdentities);
			
		} // getSingleChannelSet
		
		/*******************************************************************/
		// static private metnods
		/*******************************************************************/
		/** getChannelSetKey */
		
		static private function getChannelSetKey(channelIdentities:Vector.<ChannelIdentity>):String
		{
		var key:String = "";
		
			for (var count:int=0; count<channelIdentities.length; count++)
			{
			var channelIdentity:ChannelIdentity = channelIdentities[count];
			var channel:AMFChannel;
			var id:String = channelIdentity.getId();
			var url:String = channelIdentity.getUrl();
			
				key += id + url;
			}
			
			return key;
			
		} // getChannelSetKey
		
	} // ChannelFactory
	
} // package

/*******************************************************************/
// End of file
/*******************************************************************/


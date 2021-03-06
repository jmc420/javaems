/*******************************************************************/
/*
File: ChannelIdentity.as

Author: James Cowan
*/
/*******************************************************************/

package org.jems.client.service
{			
	import mx.utils.URLUtil;

	public class ChannelIdentity
	{
		protected var	m_id:String;
		protected var	m_isStream:Boolean;
		protected var	m_isSSL:Boolean;
		protected var	m_url:String;
				
		public function ChannelIdentity(url:String, requiresLogin:Boolean=false, channelId:String="", isStream:Boolean=false):void
		{
			m_url = url;
			m_isStream = isStream;
			m_isSSL = URLUtil.isHttpsURL(url);
			m_id = getChannelId(channelId, requiresLogin, m_isSSL);
		}
		
		/*******************************************************************/
		// public methods
		/*******************************************************************/
		/** getId */
		
		public function getId():String
		{
			return m_id;
			
		} // getId
		
		/*******************************************************************/
		/** getUrl */
		
		public function getUrl():String
		{
			return m_url;
			
		} // getUrl
		
		/*******************************************************************/
		/** isSSL */
		
		public function isSSL():Boolean
		{
			return m_isSSL;
			
		} // isSSL
		
		/*******************************************************************/
		/** isStream */
		
		public function isStream():Boolean
		{
			return m_isStream;
			
		} // isStream
		
		/*******************************************************************/
		// protected method
		/*******************************************************************/
		/** getChannelId */
		
		protected function getChannelId(channelId:String, requiresLogin:Boolean, isSSL:Boolean):String
		{
			if (channelId != "")
			{
				return channelId;
			}
			
			if (requiresLogin)
			{
				if (isSSL)
				{
					return AMFService.SSL_CHANNEL;
				}
				
				return AMFService.DEFAULT_CHANNEL;
			}
			
			if (isSSL)
			{
				return AMFService.SSL_UNSECURED_CHANNEL;
			}
			
			return AMFService.UNSECURED_CHANNEL;
			
		} // getChannelId
		
		/*******************************************************************/
		// static public methods
		/*******************************************************************/
		/** createChannelIdentitySet */
		
		static public function createChannelIdentitySet(url:String, requiresLogin:Boolean=false, id:String=""):Vector.<ChannelIdentity>
		{
		var result:Vector.<ChannelIdentity> = new Vector.<ChannelIdentity>();
		
			result[0] = new ChannelIdentity(url, requiresLogin, id);
			
			return result;
			
		} // createChannelIdentitySet	

	} // ChannelIdentity
	
} // package

/*******************************************************************/
// End of file
/*******************************************************************/


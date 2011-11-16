/*******************************************************************/
/*
File: ChannelIdentity.as

Author: James Cowan
*/
/*******************************************************************/

package org.jems.client.service
{			
	public class ChannelIdentity
	{
		protected var	m_id:String;
		protected var	m_isStream:Boolean;
		protected var	m_isSSL:Boolean;
		protected var	m_url:String;
				
		public function ChannelIdentity(id:String, url:String, isStream:Boolean=false, isSSL:Boolean=false):void
		{
			m_id = id;
			m_url = url;
			m_isStream = isStream;
			m_isSSL = isSSL;
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
		
		/*******************************************************************/
		// static public methods
		/*******************************************************************/
		/** createChannelIdentitySet */
		
		static public function createChannelIdentitySet(id:String, url:String):Vector.<ChannelIdentity>
		{
		var result:Vector.<ChannelIdentity> = new Vector.<ChannelIdentity>();
		
			result[0] = new ChannelIdentity(id, url);
			
			return result;
			
		} // createChannelIdentitySet	

	} // ChannelIdentity
	
} // package

/*******************************************************************/
// End of file
/*******************************************************************/


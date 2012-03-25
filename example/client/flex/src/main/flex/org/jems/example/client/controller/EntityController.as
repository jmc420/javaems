/*******************************************************************/
/*
	File: EntityController.as
*/
/*******************************************************************/

package org.jems.example.client.controller
{
	import mx.core.FlexGlobals;
	import mx.messaging.ChannelSet;
	
	import org.jems.client.service.ChannelFactory;
	
	import org.jems.client.controller.EntityController;
	import org.jems.client.service.AMFEntityServiceFactory;
	
	public class EntityController extends org.jems.client.controller.EntityController
	{
		public function EntityController():void
		{
		var cs:ChannelSet = ChannelFactory.createSingleChannelSet(getUrl(), false);
		
			super(new AMFEntityServiceFactory(cs), EntityFactory.getInstance(), FormFactory.getInstance());				
		}
		
		/*******************************************************************/
		// protected methods
		/*******************************************************************/
		/** getUrl */
		
		protected function getUrl():String
		{
 		var url:String = FlexGlobals.topLevelApplication.parameters.url;
		
			if (url == null)
			{
				url = "http://localhost/jems-example-server-amf";
			}
			
			if (url.indexOf("https") >= 0)
			{
				return url + "/messagebroker/amfssl";
			}
			
			return url + "/messagebroker/amf";
			
		} // getUrl
		
	} // EntityController
	
} // package

/*******************************************************************/
// End of file
/*******************************************************************/ 


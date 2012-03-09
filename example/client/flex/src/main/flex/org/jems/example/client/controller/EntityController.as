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
	import org.jems.client.service.AMFService;
	
	public class EntityController extends org.jems.client.controller.EntityController
	{
		public function EntityController():void
		{
		var cs:ChannelSet = ChannelFactory.createSingleChannelSet(getUrl(), true);
			
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
			
			return url + "/messagebroker/amf";
			
		} // getUrl
		
	} // EntityController
	
} // package

/*******************************************************************/
// End of file
/*******************************************************************/ 


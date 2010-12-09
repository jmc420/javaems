/*******************************************************************/
/*
	File: EntityController.as
*/
/*******************************************************************/

package org.jems.example.client.controller
{
	import mx.core.Application;
	
	import org.jems.client.controller.EntityController;
	import org.jems.client.service.AMFEntityServiceFactory;
	import org.jems.client.service.AMFService;
	
	public class EntityController extends org.jems.client.controller.EntityController
	{
		public function EntityController():void
		{
			super(new AMFEntityServiceFactory(AMFService.DEFAULT_CHANNEL, getUrl()), EntityFactory.getInstance(), FormFactory.getInstance());
		}
		
		/*******************************************************************/
		// protected methods
		/*******************************************************************/
		/** getUrl */
		
		protected function getUrl():String
		{
 		var url:String = Application.application.parameters.url;
		
			if (url == null)
			{
				url = "http://localhost:8080/jems-example-server-amf";
			}
			
			return url + "/messagebroker/amf";
			
		} // getUrl
		
	} // EntityController
	
} // package

/*******************************************************************/
// End of file
/*******************************************************************/ 


/*******************************************************************/
/*
	File: IFormFactory.as

	Author: James Cowan
*/
/*******************************************************************/

package org.jems.client.controller
{
	import mx.containers.TitleWindow;
	
	public interface IFormFactory
	{
		/*******************************************************************/
		/** getForm */
		
		function getForm(entityName:String):TitleWindow;

	} // IFormFactory

} // package

/*******************************************************************/
// End of file
/*******************************************************************/ 


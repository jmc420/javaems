/*******************************************************************/
/*
	File: EntityAssociationMetaData.as
*/
/*******************************************************************/

package org.jems.dao.types
{
	
	[Bindable]
    [RemoteClass(alias="org.jems.dao.types.EntityAssociationMetaData")]
    
	public class EntityAssociationMetaData
	{
		protected var		m_className:String;
		protected var		m_entityName:String;
		protected var		m_isCollection:Boolean;
		protected var		m_name:String;
	
		public function EntityAssociationMetaData():void
		{
		}

		/*******************************************************************/
		/** get className */

		public function get className():String
		{
			return m_className;

		} // get className

		/*******************************************************************/
		/** get entityName */

		public function get entityName():String
		{
			return m_entityName;

		} // get entityName

		/*******************************************************************/
		/** get isCollection */

		public function get isCollection():Boolean
		{
			return m_isCollection;

		} // get isCollection

		/*******************************************************************/
		/** get name */

		public function get name():String
		{
			return m_name;

		} // get name

		/*******************************************************************/
		/** set className */

		public function set className(className:String):void
		{
			m_className = className;

		} // set className

		/*******************************************************************/
		/** set entityName */

		public function set entityName(entityName:String):void
		{
			m_entityName = entityName;

		} // set entityName

		/*******************************************************************/
		/** set isCollection */

		public function set isCollection(isCollection:Boolean):void
		{
			m_isCollection = isCollection;

		} // set isCollection

		/*******************************************************************/
		/** set name */

		public function set name(name:String):void
		{
			m_name = name;

		} // set name
	
	} // EntityAssociationMetaData

} // end of package

/*******************************************************************/
// End of file
/*******************************************************************/ 

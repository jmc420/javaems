/*******************************************************************/
/*
	File: EntityMetaData.as
*/
/*******************************************************************/

package org.jems.dao.types
{
	
	[Bindable]
    [RemoteClass(alias="org.jems.dao.types.EntityMetaData")]
    
	public class EntityMetaData
	{
		protected var		m_className:String;
		protected var		m_entityAssociationMetaData:Array;
		protected var		m_entityAssociationMetaDataCount:int;
		protected var		m_entityPropertyMetaData:Array;
		protected var		m_entityPropertyMetaDataCount:int;
		protected var		m_name:String;
	
		public function EntityMetaData():void
		{
		}

		/*******************************************************************/
		/** get className */

		public function get className():String
		{
			return m_className;

		} // get className

		/*******************************************************************/
		/** get entityAssociationMetaData */

		public function get entityAssociationMetaData():Array
		{
			return m_entityAssociationMetaData;

		} // get entityAssociationMetaData

		/*******************************************************************/
		/** get entityAssociationMetaDataCount */

		public function get entityAssociationMetaDataCount():int
		{
			return m_entityAssociationMetaDataCount;

		} // get entityAssociationMetaDataCount

		/*******************************************************************/
		/** get entityPropertyMetaData */

		public function get entityPropertyMetaData():Array
		{
			return m_entityPropertyMetaData;

		} // get entityPropertyMetaData

		/*******************************************************************/
		/** get entityPropertyMetaDataCount */

		public function get entityPropertyMetaDataCount():int
		{
			return m_entityPropertyMetaDataCount;

		} // get entityPropertyMetaDataCount

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
		/** set entityAssociationMetaData */

		public function set entityAssociationMetaData(entityAssociationMetaData:Array):void
		{
			m_entityAssociationMetaData = entityAssociationMetaData;

		} // set entityAssociationMetaData

		/*******************************************************************/
		/** set entityAssociationMetaDataCount */

		public function set entityAssociationMetaDataCount(entityAssociationMetaDataCount:int):void
		{
			m_entityAssociationMetaDataCount = entityAssociationMetaDataCount;

		} // set entityAssociationMetaDataCount

		/*******************************************************************/
		/** set entityPropertyMetaData */

		public function set entityPropertyMetaData(entityPropertyMetaData:Array):void
		{
			m_entityPropertyMetaData = entityPropertyMetaData;

		} // set entityPropertyMetaData

		/*******************************************************************/
		/** set entityPropertyMetaDataCount */

		public function set entityPropertyMetaDataCount(entityPropertyMetaDataCount:int):void
		{
			m_entityPropertyMetaDataCount = entityPropertyMetaDataCount;

		} // set entityPropertyMetaDataCount

		/*******************************************************************/
		/** set name */

		public function set name(name:String):void
		{
			m_name = name;

		} // set name
	
	} // EntityMetaData

} // end of package

/*******************************************************************/
// End of file
/*******************************************************************/ 

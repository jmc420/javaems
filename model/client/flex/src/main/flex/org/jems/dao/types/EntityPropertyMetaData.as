/*******************************************************************/
/*
	File: EntityPropertyMetaData.as
*/
/*******************************************************************/

package org.jems.dao.types
{
	
	[Bindable]
    [RemoteClass(alias="org.jems.dao.types.EntityPropertyMetaData")]
    
	public class EntityPropertyMetaData
	{
		protected var		m_name:String;
		protected var		m_primaryKey:Boolean;
		protected var		m_typeName:String;
	
		public function EntityPropertyMetaData():void
		{
		}

		/*******************************************************************/
		/** get name */

		public function get name():String
		{
			return m_name;

		} // get name

		/*******************************************************************/
		/** get primaryKey */

		public function get primaryKey():Boolean
		{
			return m_primaryKey;

		} // get primaryKey

		/*******************************************************************/
		/** get typeName */

		public function get typeName():String
		{
			return m_typeName;

		} // get typeName

		/*******************************************************************/
		/** set name */

		public function set name(name:String):void
		{
			m_name = name;

		} // set name

		/*******************************************************************/
		/** set primaryKey */

		public function set primaryKey(primaryKey:Boolean):void
		{
			m_primaryKey = primaryKey;

		} // set primaryKey

		/*******************************************************************/
		/** set typeName */

		public function set typeName(typeName:String):void
		{
			m_typeName = typeName;

		} // set typeName
	
	} // EntityPropertyMetaData

} // end of package

/*******************************************************************/
// End of file
/*******************************************************************/ 

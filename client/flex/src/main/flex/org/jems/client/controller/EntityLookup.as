/*******************************************************************/
/*
	File: EntityLookup.as

	Author: James Cowan
*/
/*******************************************************************/

package org.jems.client.controller
{
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.controls.ComboBox;
	import mx.rpc.events.ResultEvent;
	
	import org.jems.client.service.IEntityService;
	import org.jems.client.service.IEntityServiceFactory;
	import org.jems.dao.types.DaoSort;
	import org.jems.dao.types.EntityAssociationMetaData;
	
	public class EntityLookup extends Controller
	{
		protected var		m_data:Object;
		protected var		m_md:EntityAssociationMetaData;
		protected var		m_comboBox:ComboBox;
		
		public function EntityLookup(entityServiceFactory:IEntityServiceFactory, data:Object, md:EntityAssociationMetaData, comboBox:ComboBox):void
		{
		var join:String = comboBox.labelField;
		var sort:DaoSort = new DaoSort();
		var entityService:IEntityService = entityServiceFactory.getEntityService(handleDataResult);
	
			m_data = data;
			m_md = md;
			m_comboBox = comboBox;
			sort.column = comboBox.labelField;
			sort.ascending = true;
			entityService.getList(md.entityName, [], [sort]);	
		}
		
		/*******************************************************************/
		// public methods
		/*******************************************************************/


		/*******************************************************************/
		// protected methods
		/*******************************************************************/
		/** handleDataResult */
		
		protected function handleDataResult(event:ResultEvent):void
		{
		var data:ArrayCollection = ArrayCollection(event.result);
		
			if (data.length == 0)
			{
				Alert.show("There are no values for "+m_md.entityName);
			}
			
 			m_comboBox.dataProvider = data;
 			
			var join:Object = m_data[m_md.name];
 			
 			if (join != null)
 			{
 			var joinName:String = m_comboBox.labelField;
 			var joinValue:Object = join[joinName];
 			
 				for (var count:int=0; count<data.length; count++)
 				{
 				var value:Object = data.getItemAt(count);
 			
 					if (value[joinName] == joinValue)
 					{
 						m_comboBox.selectedIndex = count;
						m_comboBox.selectedItem = value;
 						break;
 					}
 				}
 			}
			
		} // handleDataResult
	
	} // EntityLookup
	
} // package

/*******************************************************************/
// End of file
/*******************************************************************/

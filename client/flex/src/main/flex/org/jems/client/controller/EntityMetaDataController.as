/*******************************************************************/
/*
	File: EntityMetaDataController.as

	Author: James Cowan
*/
/*******************************************************************/

package org.jems.client.controller
{
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.controls.CheckBox;
	import mx.controls.ComboBox;
	import mx.controls.DateField;
	import mx.controls.TextInput;
	import mx.core.Container;
	import mx.rpc.events.ResultEvent;
	
	import org.jems.client.service.IEntityMetaDataService;
	import org.jems.client.service.IEntityServiceFactory;
	import org.jems.dao.types.DaoFilter;
	import org.jems.dao.types.EntityAssociationMetaData;
	import org.jems.dao.types.EntityMetaData;
	import org.jems.dao.types.EntityPropertyMetaData;
	
	public class EntityMetaDataController extends Controller implements IEntityMetaDataController
	{
		protected var		m_callBack:Function;
		protected var		m_bindingTable:Array;
		protected var		m_entityFactory:IEntityFactory;
		protected var		m_entityMetaDataTable:Array;
		protected var		m_entityMetaDataService:IEntityMetaDataService;
		protected var		m_entityServiceFactory:IEntityServiceFactory;
				
		protected static var	m_instance:EntityMetaDataController;

		public function EntityMetaDataController(entityServiceFactory:IEntityServiceFactory, entityFactory:IEntityFactory, callBack:Function = null):void
		{
			m_entityServiceFactory = entityServiceFactory;
			m_entityFactory = entityFactory;
			m_callBack = callBack;
			m_entityMetaDataTable = new Array();
			m_bindingTable = new Array();
			m_entityMetaDataService = entityServiceFactory.getEntityMetaDataService(handleResult);
			m_entityMetaDataService.getEntityMetaData();
		}
		
		/*******************************************************************/
		// public methods
		/*******************************************************************/
		/** bindFormToObject */
				
		public function bindFormToObject(entityName:String, data:Object, form:Container):Object
		{
 		var entityMetaData:EntityMetaData = getEntityMetaData(entityName);
 		var properties:Array = entityMetaData.entityPropertyMetaData;
 		var associations:Array = entityMetaData.entityAssociationMetaData;
 		var name:String;
 		var formField:Object;
 			
 			if (data == null)
 			{
 				throw new Error(entityName+" not bound");
 			}
 		 		
 			for (var count:int=0; count<properties.length; count++)
 			{
 			var entityPropertyMetaData:EntityPropertyMetaData = properties[count] as EntityPropertyMetaData;
 			
 				name = entityPropertyMetaData.name;
 				formField = form[entityName+"_"+name];
 			
 				if (!isValidColumn(name, data, formField))
 				{
 					continue;
 				}
 				
 				if (formField is TextInput)
 				{
 				var textInput:TextInput = formField as TextInput;
 				var value:String = textInput.text;
 				var typeName:String = entityPropertyMetaData.typeName;
 					
 					switch (typeName)
 					{
 					default:
 						data[name] = value;
 						break;
 						
 					case "Boolean":
 						switch (value.toLowerCase())
 						{
 						default:
 							throw new Error(name+" expects true or false");
 						
 						case "false":
 							data[name] = false;
 							break;
 							
 						 case "true":
 							data[name] = true;
 							break;
 						}	
 						break;
 					}
 				}
 				
 				if (formField is CheckBox)
 				{
 				var checkBox:CheckBox = formField as CheckBox;
 				
 					data[name] = checkBox.selected;
 				}
 				
 				if (formField is DateField)
 				{
 				var dateField:DateField = formField as DateField;
 				
 					data[name] = dateField.selectedDate;
 				}
 			}
 			
 			for (var associationCount:int=0; associationCount<associations.length; associationCount++)
 			{
 			var entityAssociationMetaData:EntityAssociationMetaData = associations[associationCount] as EntityAssociationMetaData;
 				 			
 				name = entityAssociationMetaData.name;
 				formField = form[entityName+"_"+name];
 				
 				if (formField is ComboBox)
 				{
 				var comboBox:ComboBox = formField as ComboBox;
 				var selectedItem:Object = comboBox.selectedItem;
 				
 					if (selectedItem == null)
 					{
 						throw new Error(name+" has no value");
 					}
 					data[name] = selectedItem;
 				}
 			}
 			
 			return data;
 			
		} // bindFormToObject
	
		/*******************************************************************/
		/** bindObjectToForm */
				
		public function bindObjectToForm(entityName:String, data:Object, form:Container):void
		{
 		var entityMetaData:EntityMetaData = getEntityMetaData(entityName);
 		var properties:Array = entityMetaData.entityPropertyMetaData;
 		var associations:Array = entityMetaData.entityAssociationMetaData;
 		var name:String;
 		var formField:Object;
 		var value:Object;
 		
 			for (var propertyCount:int=0; propertyCount<properties.length; propertyCount++)
 			{
 			var entityPropertyMetaData:EntityPropertyMetaData = properties[propertyCount] as EntityPropertyMetaData;
 			
	 			name = entityPropertyMetaData.name;
 				formField = form[entityName+"_"+name];
 				value = data[name];
 			
 				if (!isValidColumn(name, data, formField))
 				{
 					continue;
 				}
 				
  				if (isNull(value))
 				{
 					value = "";
 				}
 				
 				if (formField is TextInput)
 				{
 				var textInput:TextInput = formField as TextInput;
 				var typeName:String = entityPropertyMetaData.typeName;
 					
 					switch (typeName)
 					{
 					default:
 						textInput.text = value.toString();
 						break;
 					}
 				}
 				 
  				if (formField is CheckBox)
 				{
 				var checkBox:CheckBox = formField as CheckBox;
 				
 					checkBox.selected = value as Boolean;
 				}
 
 				if (formField is DateField)
 				{
 				var dateField:DateField = formField as DateField;
 				
 					dateField.selectedDate = value as Date;
 					dateField.showToday = false;
 				}
 			}
 			
 			for (var associationCount:int=0; associationCount<associations.length; associationCount++)
 			{
 			var entityAssociationMetaData:EntityAssociationMetaData = associations[associationCount] as EntityAssociationMetaData;
 				 			
 				name = entityAssociationMetaData.name;
 				formField = form[entityName+"_"+name];
 				value = data[name];
 				
 				if (!isValidColumn(name, data, formField))
 				{
 					continue;
 				}
 				
 				new EntityLookup(m_entityServiceFactory, data, entityAssociationMetaData, formField as ComboBox);
 			}
 			
		} // bindObjectToForm
		
		/*******************************************************************/
		/** createEntity */
				
		public function createEntity(entityName:String):Object
		{
  		var entity:Object = m_entityFactory.createEntity(entityName);
  		var entityMetaData:EntityMetaData = getEntityMetaData(entityName);
 		var properties:Array = entityMetaData.entityPropertyMetaData;
 		
 			for (var count:int=0; count<properties.length; count++)
 			{
 			var md:EntityPropertyMetaData = properties[count] as EntityPropertyMetaData;
 			var propertyName:String = md.name;
 			var typeName:String = md.typeName;
 			
 				if (md.primaryKey)
 				{
 					continue;	// do not initialise primary key
 				}
 				
 				switch (typeName)
 				{
 				default:
 					throw new Error(entityName+" has unknown type "+md.typeName);
 					
 				case "Boolean":
 					entity[propertyName] = true;
 					break;
 					
 				case "Date":
 					entity[propertyName] = new Date();
 					break;
 					
 				case "String":
 					entity[propertyName] = "";
 					break;
 					
 				case "Double":
 				case "Float":
 					entity[propertyName] = 0.0;
 					break;
 					
 				case "Integer":					
 				case "Long":
 					entity[propertyName] = 0;
 					break;
 				}
 			}
 			
 			return entity;
 			
		} // createEntity
		
		/*******************************************************************/
		/** getFilter */
		
		public function getFilter(entityName:String, column:String, value:Object, operation:String="=", className:String=""):DaoFilter
		{
		var filter:DaoFilter = new DaoFilter();
			
			if (className == "")
			{
			var md:EntityPropertyMetaData = getPropertyMetaData(entityName, column);
				
				className = md.typeName;
			}
			filter.className = className;
			filter.column = column;
			filter.operation = operation;
			filter.value = value;
			return filter;
			
		} // getFilter
		
		/*******************************************************************/
		/** getPropertyMetaData */
				
		public function getPropertyMetaData(entityName:String, propertyName:String):EntityPropertyMetaData
		{
		var index:int;
		
  			while ((index=propertyName.indexOf(".")) >= 0)
  			{
			var name:String = propertyName.substr(0, index); 
  			var eamd:EntityAssociationMetaData = getEntityAssociationMetaData(entityName, name);

				entityName = eamd.entityName;
  				propertyName = propertyName.substr(index+1);
  			}
  			
  			var entityMetaData:EntityMetaData = getEntityMetaData(entityName);
 			var properties:Array = entityMetaData.entityPropertyMetaData;
 		
 			for (var count:int=0; count<properties.length; count++)
 			{
 			var md:EntityPropertyMetaData = properties[count] as EntityPropertyMetaData;
 			
 				if (md.name == propertyName)
 				{
 					return md;
 				}
 			} 
 			
 			throw new Error("Entity: "+entityName+" does not have property: "+propertyName);
 			
 		} // getPropertyMetaData
 	
		/*******************************************************************/
		// protected methods
		/*******************************************************************/
		/** getEntityMetaData */
				
		protected function getEntityMetaData(entityName:String):EntityMetaData
		{
 		var entityMetaData:EntityMetaData = m_entityMetaDataTable[entityName.toLowerCase()];
 		
 			if (entityMetaData != null)
 			{
 				return entityMetaData;
 			}
 			
 			throw new Error("Invalid entity: "+entityName);
 			
		} // getEntityMetaData
		
		/*******************************************************************/
		/** getEntityAssociationMetaData */
		
		protected function getEntityAssociationMetaData(entityName:String, associationName:String):EntityAssociationMetaData
		{
		var entityMetaData:EntityMetaData = getEntityMetaData(entityName);
		var associations:Array = entityMetaData.entityAssociationMetaData;
			
			for (var associationCount:int=0; associationCount<associations.length; associationCount++)
			{
			var entityAssociationMetaData:EntityAssociationMetaData = associations[associationCount] as EntityAssociationMetaData;
			
				if (entityAssociationMetaData.name == associationName)
				{
					return entityAssociationMetaData;
				}
			}
			
			throw new Error(entityName+" does not have association: "+associationName);
			
		} // getEntityAssociationMetaData
		
		/*******************************************************************/
		/** handleResult */

		protected function handleResult(e:ResultEvent):void
		{
		var	data:ArrayCollection = ArrayCollection(e.result);

			for (var count:int=0; count<data.length; count++)
			{
			var md:EntityMetaData = data.getItemAt(count) as EntityMetaData;
				
				m_entityMetaDataTable[md.name.toLowerCase()] = md;
			}
			
			if (m_callBack != null)
			{
				m_callBack.call();
			}
							
		} // handleResult
		
		/*******************************************************************/
		/** isNull */
				
		protected function isNull(value:Object):Boolean
		{
			if (value == null)
			{
				return true;
			}
			
			if (value is Number)
			{
			var number:Number = value as Number;
			
				if (isNaN(number))
				{
					return true;
				}
			}
			
			return false;
			
		} // isNull
		
		/*******************************************************************/
		/** isValidColumn */
				
		protected function isValidColumn(name:String, data:Object, formField:Object):Boolean
		{
			if (formField == null)
 			{
 				Alert.show("Form is missing input field: "+ name);
 				return false;
 			}
 				
 			if (!data.hasOwnProperty(name))
 			{
 				Alert.show(name + " field is missing");
 				return false;
 			}
 			
 			return true;
 			
		} // isValidColumn
		
		/*******************************************************************/
		// static methods
		/*******************************************************************/
		/** getInstance */
		
		public static function getInstance(entityServiceFactory:IEntityServiceFactory, entityFactory:IEntityFactory, callBack:Function = null):EntityMetaDataController
		{
			if (callBack != null)
			{
				return new EntityMetaDataController(entityServiceFactory, entityFactory, callBack);
			}
			
			if (m_instance == null)
			{
				m_instance = new EntityMetaDataController(entityServiceFactory, entityFactory);	
			}
			
			return m_instance;
			
		} // getInstance
		
	} // EntityMetaDataController
	
} // package

/*******************************************************************/
// End of file
/*******************************************************************/ 


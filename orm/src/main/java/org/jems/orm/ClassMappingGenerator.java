/************************************************************/
/*
	File: ClassMappingGenerator.java

	Template for Class mapping.

	Author: James Cowan
*/
/************************************************************/

package org.jems.orm;

import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import org.jems.generator.ClassMetaData;
import org.jems.generator.GeneratorException;
import org.jems.generator.IGenerator;
import org.jems.orm.model.Orm;
import org.jems.orm.model.OrmAssociationProperty;
import org.jems.orm.model.OrmBasicProperty;
import org.jems.orm.model.OrmEntity;
import org.jems.orm.model.OrmId;
import org.jems.orm.model.OrmJoin;
import org.jems.orm.model.OrmProperty;

/************************************************************/

abstract public class ClassMappingGenerator implements IGenerator
{
	protected ClassMetaData								m_metaData;
	protected Orm										m_orm;
	protected OrmEntity									m_ormEntity;
	protected Map<String, Map<String, OrmProperty>>		m_classPropertyMap;
	protected Map<String, OrmProperty>					m_propertyMap;
	protected Map<String, OrmBasicProperty>				m_basicPropertyMap;
	protected Map<String, String>						m_embeddedPropertyMap;
	protected Map<String, OrmAssociationProperty>		m_joinPropertyMap;
	protected Map<String, String>						m_transientPropertyMap;
	
	public ClassMappingGenerator(ClassMetaData metaData, Orm orm, OrmEntity ormEntity)
	{
		this(orm, ormEntity);
		m_metaData = metaData;
	}
	
	public ClassMappingGenerator(Orm orm, OrmEntity ormEntity)
	{
		m_orm = orm;
		m_ormEntity = ormEntity;
		m_propertyMap = new HashMap<String, OrmProperty>();
		m_basicPropertyMap = new HashMap<String, OrmBasicProperty>();
		m_joinPropertyMap = new HashMap<String, OrmAssociationProperty>();
		
		OrmBasicProperty ormBasicProperties[] = ormEntity.getOrmBasicProperty();
		
		for (int count=0; count<ormBasicProperties.length; count++)
		{
		OrmBasicProperty ormBasicProperty = ormBasicProperties[count];
		
			m_basicPropertyMap.put(ormBasicProperty.getName(), ormBasicProperty);
		}
		
		m_classPropertyMap = getClassPropertyMap(orm);
		
		m_propertyMap.putAll(getOrmPropertyMap(ormEntity.getOrmBasicProperty()));
		m_propertyMap.putAll(getOrmPropertyMap(ormEntity.getOrmVersionProperty()));
		m_propertyMap.putAll(getOrmPropertyMap(ormEntity.getOrmManyToOneProperty()));
		m_propertyMap.putAll(getOrmPropertyMap(ormEntity.getOrmOneToManyProperty()));
		m_propertyMap.putAll(getOrmPropertyMap(ormEntity.getOrmOneToOneProperty()));
		m_propertyMap.putAll(getOrmPropertyMap(ormEntity.getOrmManyToManyProperty()));
		m_embeddedPropertyMap = getOrmPropertyMap(ormEntity.getOrmEmbeddedProperty());
		m_joinPropertyMap.putAll(getOrmJoinPropertyMap(ormEntity.getOrmManyToOneProperty()));
		m_joinPropertyMap.putAll(getOrmJoinPropertyMap(ormEntity.getOrmOneToManyProperty()));
		m_joinPropertyMap.putAll(getOrmJoinPropertyMap(ormEntity.getOrmManyToManyProperty()));
		m_joinPropertyMap.putAll(getOrmJoinPropertyMap(ormEntity.getOrmManyToManyProperty()));
		m_transientPropertyMap = getOrmPropertyMap(ormEntity.getOrmTransientProperty());

	}

	/************************************************************/
	/* protected methods */
	/************************************************************/
	/** checkProperty */

	protected void checkProperty(String name) throws GeneratorException
	{
		if (!m_metaData.hasProperty(name))
		{
			throw new GeneratorException("Missing property: "+name);
		}
		
	} // checkProperty
	
	/************************************************************/
	/* getClassPropertyMap */
	
	protected Map<String, Map<String, OrmProperty>>	getClassPropertyMap(Orm orm)
	{
	Map<String, Map<String, OrmProperty>> map = new HashMap<String, Map<String, OrmProperty>>();
	OrmEntity ormEntities[] = orm.getOrmEntity();
	GeneratorUtils generatorUtils = new GeneratorUtils();
	
		for (int count=0; count<ormEntities.length; count++)
		{
		OrmEntity ormEntity = ormEntities[count];
		String className = generatorUtils.getSimpleClassName(ormEntity.getClassName());
		
			map.put(className, getClassPropertyMap(ormEntity));
		}
		
		return map;
		
	} // getClassPropertyMap
	
	/************************************************************/
	/* getClassPropertyMap */
	
	protected Map<String, OrmProperty> getClassPropertyMap(OrmEntity ormEntity)
	{
	Map<String, OrmProperty> map = new HashMap<String, OrmProperty>();
	OrmProperty ormProperties[] = ormEntity.getOrmBasicProperty();
	
		for (int count=0; count<ormProperties.length; count++)
		{
		OrmProperty ormProperty = ormProperties[count];
		
			map.put(ormProperty.getName(), ormProperty);
		
		}
		
		OrmId ormId = ormEntity.getOrmId();
		
		if (ormId != null)
		{
		OrmProperty ormProperty = new OrmProperty();
		
			map.put(ormId.getName(), ormProperty);
		}

		return map;
		
	} // getClassPropertyMap
	
	/************************************************************/
	/* getJavaType */
	
	protected String getJavaType(OrmProperty ormProperty)
	throws GeneratorException
	{
	String typeName = ormProperty.getSqlTypeName().toUpperCase();
	SQLType sqlType = SQLType.valueOf(typeName);
	
		switch (sqlType)
		{
		case BIGINT:
			return "Long";
			
		case INT:
			return "Integer";
/*			
		case LONG:
			return "Long";
*/			
		case VARCHAR:
			return "String";
		}

		throw new GeneratorException("Invalid SQL Type: "+typeName);
		
	} // isStringProperty
	
	/************************************************************/
	/* getOrmBasicProperty */
	
	protected OrmBasicProperty getOrmBasicProperty(String name)
	{
	OrmBasicProperty ormProperty = m_basicPropertyMap.get(name);
	
		if (ormProperty != null)
		{
			return ormProperty;
		}
		
		ormProperty = new OrmBasicProperty();
		
		ormProperty.setName(name);
		
		return ormProperty;

	} // getOrmBasicProperty

	/************************************************************/
	/** getOrmJoinPropertyMap */
	
	protected HashMap<String, OrmAssociationProperty> getOrmJoinPropertyMap(OrmAssociationProperty ormAssociationProperties[])
	{
	HashMap<String, OrmAssociationProperty> propertyMap = new HashMap<String, OrmAssociationProperty>();

		for (int count=0; count<ormAssociationProperties.length; count++)
		{
		OrmAssociationProperty ormAssociationProperty = ormAssociationProperties[count];
		OrmJoin ormJoin = ormAssociationProperty.getOrmJoin();
		OrmProperty joinProperties[] = ormJoin.getJoinColumn();
		
			for (int index=0; index<ormAssociationProperties.length; index++)
			{
			OrmProperty ormJoinProperty = joinProperties[index];

				propertyMap.put(ormJoinProperty.getName(), ormAssociationProperty);
			}
		}
		
		return propertyMap;
		
	} // getOrmPropertyMap
	
	/************************************************************/
	/* getOrmPropertyMap */
	
	protected HashMap<String, OrmProperty> getOrmPropertyMap(OrmProperty ormProperties[])
	{
	HashMap<String, OrmProperty> propertyMap = new HashMap<String, OrmProperty>();

		for (int count=0; count<ormProperties.length; count++)
		{
		OrmProperty ormProperty = ormProperties[count];
		
			propertyMap.put(ormProperty.getName(), ormProperty);
		}
		
		return propertyMap;
		
	} // getOrmPropertyMap
	
	/************************************************************/
	/* getOrmPropertyMap */
	
	protected HashMap<String, String> getOrmPropertyMap(String ormProperties[])
	{
	HashMap<String, String> propertyMap = new HashMap<String, String>();

		for (int count=0; count<ormProperties.length; count++)
		{
		String ormProperty = ormProperties[count];
		
			propertyMap.put(ormProperty, ormProperty);
		}
		
		return propertyMap;
		
	} // getOrmPropertyMap
	
	/************************************************************/
	/* isStringProperty */
	
	protected boolean isStringProperty(OrmProperty ormProperty)
	{
		if (ormProperty.getSqlTypeName().toUpperCase().equals("VARCHAR"))
		{
			return true;
		}
		
		return false;
		
	} // isStringProperty
	
	/************************************************************/
	/* isMappedProperty */
	
	protected boolean isMappedProperty(OrmEntity ormEntity, String name)
	{
		if (m_embeddedPropertyMap.get(name) != null)
		{
			return true;
		}
		
		if (isOrmIdProperty(name))
		{
			return true;
		}
		
		if (isOrmEmbeddedProperty(name))
		{
			return true;
		}
		
		if (isOrmTransientProperty(name))
		{
			return true;
		}
		
		if (m_basicPropertyMap.get(name) != null)
		{
			return true;
		}
		
		if (m_propertyMap.get(name) != null)
		{
			return true;
		}
		
		if (m_joinPropertyMap.get(name) != null)
		{
			return true;
		}
		
		if (!ormEntity.getParentClassName().equals(""))
		{
		String className = ormEntity.getParentClassName();
		Map<String, OrmProperty> propertyMap = m_classPropertyMap.get(className);
		
			if (propertyMap.containsKey(name))
			{
				return true;
			}
		}
		
		return false;
		
	} // isOrmEmbeddedProperty
	
	/************************************************************/
	/* isOrmEmbeddedProperty */
	
	protected boolean isOrmEmbeddedProperty(String name)
	{
		if (m_embeddedPropertyMap.get(name) != null)
		{
			return true;
		}
		
		return false;
		
	} // isOrmEmbeddedProperty
	
	/************************************************************/
	/* isOrmBasicProperty */
	
	protected boolean isOrmBasicProperty(String name)
	{
	OrmProperty ormProperty = m_propertyMap.get(name);
	
		if (ormProperty == null)
		{
			return true;
		}
		
		if (m_basicPropertyMap.get(name) != null)
		{
			return true;
		}
		
		return false;

	} // isOrmBasicProperty
	
	/************************************************************/
	/* isOrmIdProperty */
	
	protected boolean isOrmIdProperty(String name)
	{
	OrmId ormId = m_ormEntity.getOrmId();
		
		if (ormId != null && ormId.getName().equals(name))
		{
			return true;
		}
		
		return false;
		
	} // isOrmIdProperty
	
	/************************************************************/
	/* isOrmTransientProperty */
	
	protected boolean isOrmTransientProperty(String name)
	{
		if (m_transientPropertyMap.get(name) != null)
		{
			return true;
		}
		
		return false;
		
	} // isOrmTransientProperty
	
	/************************************************************/
	/** writeSeparator */
	
	protected void writeSeparator(PrintStream out, int indent) throws IOException
	{
		writeText(out, indent, "<!-- **************************************************************** -->");
		
	} // writeSeparator
	
	/************************************************************/
	/** writeText */
	
	protected void writeText(PrintStream out, int indent, String text) throws IOException
	{
		for (int count=0; count<indent; count++)
		{
			out.print(" ");
		}
		
		out.println(text);
		
	} // writeText
	
} // ClassMappingGenerator

/************************************************************/



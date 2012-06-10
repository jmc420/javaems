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

import org.jems.generator.ClassMetaData;
import org.jems.generator.GeneratorException;
import org.jems.generator.IGenerator;
import org.jems.orm.model.Orm;
import org.jems.orm.model.OrmBasicProperty;
import org.jems.orm.model.OrmEntity;
import org.jems.orm.model.OrmId;
import org.jems.orm.model.OrmProperty;

/************************************************************/

abstract public class ClassMappingGenerator implements IGenerator
{
	protected ClassMetaData						m_metaData;
	protected Orm								m_orm;
	protected OrmEntity							m_ormEntity;
	protected HashMap<String, OrmProperty>		m_propertyMap;
	protected HashMap<String, OrmBasicProperty>	m_basicPropertyMap;
	protected HashMap<String, String>			m_transientPropertyMap;
	protected HashMap<String, String>			m_embeddedPropertyMap;
	
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
		
		OrmBasicProperty ormBasicProperties[] = ormEntity.getOrmBasicProperty();
		
		for (int count=0; count<ormBasicProperties.length; count++)
		{
		OrmBasicProperty ormBasicProperty = ormBasicProperties[count];
		
			m_basicPropertyMap.put(ormBasicProperty.getName(), ormBasicProperty);
		}
		
		m_propertyMap.putAll(getOrmPropertyMap(ormEntity.getOrmBasicProperty()));
		m_propertyMap.putAll(getOrmPropertyMap(ormEntity.getOrmVersionProperty()));
		m_propertyMap.putAll(getOrmPropertyMap(ormEntity.getOrmManyToOneProperty()));
		m_propertyMap.putAll(getOrmPropertyMap(ormEntity.getOrmOneToManyProperty()));
		m_propertyMap.putAll(getOrmPropertyMap(ormEntity.getOrmOneToOneProperty()));
		m_propertyMap.putAll(getOrmPropertyMap(ormEntity.getOrmManyToManyProperty()));
		m_embeddedPropertyMap = getOrmPropertyMap(ormEntity.getOrmEmbeddedProperty());
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
	/* isMappedProperty */
	
	protected boolean isMappedProperty(String name)
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



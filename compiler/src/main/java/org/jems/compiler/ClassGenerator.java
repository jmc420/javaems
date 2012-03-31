/************************************************************/
/*
	File: ClassGenerator.java

	ClassGenerator generates as AS3 class file from a java class.

	Author: James Cowan
*/
/************************************************************/

package org.jems.compiler;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jems.compiler.model.JavaClass;
import org.jems.compiler.model.TypeDescriptor;
import org.jems.compiler.model.TypeDescriptorList;
import org.jems.generator.ClassMetaData;
import org.jems.generator.GeneratorException;

/************************************************************/

abstract public class ClassGenerator 
{
	protected JavaClass							m_javaClass;
	protected Class<?>							m_class;
	protected HashMap<String, String>			m_excludedPropertyMap;
	protected HashMap<String, TypeDescriptor>	m_typeDescriptorMap;
	
	public ClassGenerator(JavaClass javaClass, Class<?> cl, TypeDescriptorList typeDescriptorList)
	throws GeneratorException
	{
	String excludedProperties[] = javaClass.getExcludedProperty();
	TypeDescriptor typeDescriptors[] = typeDescriptorList.getTypeDescriptor();
	
		m_javaClass = javaClass;
		m_class = cl;
		m_excludedPropertyMap = new HashMap<String, String>();
		m_typeDescriptorMap = new HashMap<String, TypeDescriptor>();
		
		for (int count=0; count<excludedProperties.length; count++)
		{	
		String name = excludedProperties[count];

			m_excludedPropertyMap.put(name, name);
		}
		
		for (int count=0; count<typeDescriptors.length; count++)
		{	
		TypeDescriptor typeDescriptor = typeDescriptors[count];
			
			m_typeDescriptorMap.put(typeDescriptor.getName(), typeDescriptor);
		}
	}

	/************************************************************/
	/* public methods */
	/************************************************************/

	/************************************************************/
	/* protected methods */
	/************************************************************/
	/** getPropertyDescriptorList */
	
	protected List<PropertyDescriptor> getPropertyDescriptorList() throws GeneratorException
	{
	String className = m_javaClass.getClassName();
	
		try
		{
		ClassMetaData md = new ClassMetaData(className, "");
		PropertyDescriptor descriptors[] = md.getPropertyDescriptors();
		ArrayList<PropertyDescriptor> resultList = new ArrayList<PropertyDescriptor>();
	
			for (int count=0; count<descriptors.length; count++)
			{
			PropertyDescriptor propertyDescriptor = descriptors[count];
			String name = propertyDescriptor.getName();
			
				if (m_excludedPropertyMap.get(name) != null)
				{
					continue;
				}
			
				resultList.add(propertyDescriptor);
			}
			return resultList;
		}
		catch (ClassNotFoundException cne)
		{
			throw new GeneratorException("Invalid class: "+className);
		}
		catch (IntrospectionException ie)
		{
			throw new GeneratorException(ie.getMessage());
		}
		
	} // getPropertyDescriptorList
	
	/************************************************************/
	/** getPropertyImport */
	
	protected String getPropertyImport(PropertyDescriptor propertyDescriptor)
	{
	Class<?> cl = propertyDescriptor.getPropertyType();
	String typeName = cl.getSimpleName();
	TypeDescriptor typeDescriptor = m_typeDescriptorMap.get(typeName);
	
		if (typeDescriptor != null)
		{
			return typeDescriptor.getImport();
		}
		
		Package pkg = cl.getPackage();
		
		if (pkg == null)
		{
			return "";
		}
		
		//System.out.println("name "+propertyDescriptor.getName()+" class "+cl.getName()+" pkg "+pkg);
		return "import "+cl.getPackage().getName()+"."+typeName;
		
	} // getPropertyImport
	
	/************************************************************/
	/** getPropertyName */
	
	protected String getPropertyName(PropertyDescriptor propertyDescriptor)
	{
	String name = propertyDescriptor.getName();
	
		if (name.equals(""))
		{
			return "";
		}
		
		String firstChar = name.substring(0, 1).toUpperCase();
		
		String remainder = "";
	
		if (name.length() > 1)
		{
			remainder = name.substring(1);
		}
	
		return firstChar.toUpperCase()+remainder;
	
	} // getPropertyTypeName
	
	/************************************************************/
	/** getPropertyTypeName */
	
	protected String getPropertyTypeName(PropertyDescriptor propertyDescriptor)
	{
	String typeName = propertyDescriptor.getPropertyType().getSimpleName();
	
		return getPropertyTypeName(typeName);
	
	} // getPropertyTypeName

	/************************************************************/
	/** getPropertyTypeName */
	
	protected String getPropertyTypeName(String typeName)
	{
	TypeDescriptor typeDescriptor = m_typeDescriptorMap.get(typeName);
	
		if (typeDescriptor != null)
		{
			return typeDescriptor.getTargetName();
		}
		
		return typeName;
		
	} // getPropertyTypeName
	
	/************************************************************/
	/** hasSuperClass */
	
	protected boolean hasSuperClass()
	{
		if (m_class.getSuperclass() == Object.class)
		{
			return false;
		}
		
		return true;
		
	} // hasSuperClass
	
} // ClassGenerator

/************************************************************/



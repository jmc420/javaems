/*******************************************************************/
/*
	File: ClassMetaData.java

	Author: James Cowan
*/
/*******************************************************************/

package org.jems.generator;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;

/*******************************************************************/
/**
 * ClassMetaData 
 *
 */
/*******************************************************************/

public class ClassMetaData
{
	protected Class<?>								m_class;
	protected PropertyDescriptor					m_propertyDescriptors[];
	protected HashMap<String, PropertyDescriptor>	m_propertyMap;
	
	public ClassMetaData(String className, String superClassName)
	throws ClassNotFoundException, IntrospectionException
	{
		m_class = getClass(className);
		m_propertyDescriptors = getPropertyDescriptors(superClassName);
		m_propertyMap = new HashMap<String, PropertyDescriptor>();
		
		for (int count=0; count<m_propertyDescriptors.length; count++)
		{
		PropertyDescriptor propertyDescriptor = m_propertyDescriptors[count];
		
			m_propertyMap.put(propertyDescriptor.getName(), propertyDescriptor);
		}
	}

	/*******************************************************************/
	/* public methods */
	/*******************************************************************/
	/** getPropertyDescriptors */
	
	public PropertyDescriptor[] getPropertyDescriptors()
	{
		return m_propertyDescriptors;
		
	} // getPropertyDescriptors
	
	/*******************************************************************/
	/** hasProperty */
	
	public boolean hasProperty(String name)
	{
		if (m_propertyMap.get(name) == null)
		{
			return false;
		}
		
		return true;
		
	} // hasProperty
	
	/*******************************************************************/
	/* protected methods */
	/*******************************************************************/
	/** getPropertyDescriptors */
	
	protected PropertyDescriptor[] getPropertyDescriptors(String superClassName)
	throws ClassNotFoundException, IntrospectionException
	{
	Class<?> superCl = m_class.getSuperclass();
	
		if (!superClassName.equals(""))
		{
			superCl = getClass(superClassName).getSuperclass();
		}
		
		return getPropertyDescriptors(getBeanInfo(m_class, superCl));
		
	} // getPropertyDescriptors
	
	/*******************************************************************/
	/** getBeanInfo */
	
	protected BeanInfo getBeanInfo(Class<?> cl, Class<?> superCl) 
	throws IntrospectionException
	{	
		return Introspector.getBeanInfo(cl, superCl);
		
	} // getBeanInfo
	
	/*******************************************************************/
	/** getClass */
	
	protected Class<?> getClass(String className) throws ClassNotFoundException
	{	
		return Class.forName(className);
		
	} // getClass
	
	/*******************************************************************/
	/** getPropertyDescriptors */
	
	protected PropertyDescriptor[] getPropertyDescriptors(BeanInfo info)
	{
	PropertyDescriptor props[] = info.getPropertyDescriptors();
	ArrayList<PropertyDescriptor> list = new ArrayList<PropertyDescriptor>();
		
		for (int count=0; count<props.length; count++)
		{
		PropertyDescriptor prop = props[count];
		
			if (prop.getReadMethod() == null)
			{
				continue;
			}
			list.add(prop);
		}
		
		PropertyDescriptor result[] = new PropertyDescriptor[list.size()];
		
		list.toArray(result);
		
		return result;
		
	} // getPropertyDescriptors
	
	/*******************************************************************/
	/* static classes */
	/*******************************************************************/
	
	public static void main(String argv[])
	{
		if (argv.length < 2)
		{
		String msg = "arguments are class name and super class name";
		
			System.out.println(msg);
			return;
		}
		
		try
		{
		ClassMetaData metaData = new ClassMetaData(argv[0], argv[1]);
		PropertyDescriptor props[] = metaData.getPropertyDescriptors();
		
			for (int count=0; count<props.length; count++)
			{
			PropertyDescriptor prop = props[count];
			
				System.out.println("NAME "+prop.getName());
			}
		}
		catch (Exception e)
		{
			//System.out.println("Exception: "+e.getMessage());
			//e.printStackTrace(System.out);
			System.exit(1);
		}
		
	} // main
	
} // ClassMetaData

/*******************************************************************/



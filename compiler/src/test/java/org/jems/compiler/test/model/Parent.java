/************************************************************/
/*
	File: Parent.java

	Author: James Cowan
*/
/************************************************************/

package org.jems.compiler.test.model;

/************************************************************/

public class Parent
{
	protected String	m_name;
	protected Child		m_child;
	
	public Parent()
	{
	}

	/************************************************************/
	/* public methods */
	/************************************************************/
	/** getChild */
	
	public Child getChild()
	{
		return m_child;
		
	} // getChild
	
	/************************************************************/
	/** getName */
	
	public String getName()
	{
		return m_name;
		
	} // getName
	
	/************************************************************/
	/** setChild */
	
	public void setChild(Child child)
	{
		m_child = child;
		
	} // setChild
	
	/************************************************************/
	/** setName */
	
	public void setName(String name)
	{
		m_name = name;
		
	} // setName
	
} // Parent

/************************************************************/



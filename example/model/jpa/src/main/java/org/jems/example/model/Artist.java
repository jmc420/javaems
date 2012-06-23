/*******************************************************************/
/*
	File: Artist.java
*/
/*******************************************************************/

package org.jems.example.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Artist extends Identifier implements Serializable
{
	@Column(name="name", nullable=false, length=255)
	protected String		name;
	private static final long serialVersionUID = 1L;

	public Artist() {}

	/*******************************************************************/
	/** getName */

	public String getName()
	{
		return this.name;
	}

	/*******************************************************************/
	/** setName */

	public void setName(String name)
	{
		this.name = name;
	}

} // Artist

/*******************************************************************/
// End of file
/*******************************************************************/

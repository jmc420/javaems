/*******************************************************************/
/*
	File: Identifier.java
*/
/*******************************************************************/

package org.jems.example.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Identifier implements Serializable
{
	@Id
	@Column(name="id", nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected long		id;

	private static final long serialVersionUID = 1L;

	public Identifier() {}

	/*******************************************************************/
	/** getId */

	public long getId()
	{
		return this.id;
	}

	/*******************************************************************/
	/** setId */

	public void setId(long id)
	{
		this.id = id;
	}

} // Identifier

/*******************************************************************/
// End of file
/*******************************************************************/

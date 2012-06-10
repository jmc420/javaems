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


} // Identifier

/*******************************************************************/
// End of file
/*******************************************************************/

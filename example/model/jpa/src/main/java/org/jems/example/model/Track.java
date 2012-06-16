/*******************************************************************/
/*
	File: Track.java
*/
/*******************************************************************/

package org.jems.example.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Track extends Identifier implements Serializable
{
	@Column(name="id", nullable=false)
	protected Long		id;
	@Column(name="length", nullable=false)
	protected Integer		length;
	@Column(name="name", nullable=false, length=255)
	protected String		name;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="albumId", nullable=false)
	protected Album		album;
	private static final long serialVersionUID = 1L;

	public Track() {}


} // Track

/*******************************************************************/
// End of file
/*******************************************************************/

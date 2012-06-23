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
	@Column(name="length", nullable=false)
	protected Integer		length;
	@Column(name="name", nullable=false, length=255)
	protected String		name;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="albumId", nullable=false)
	protected Album		album;
	private static final long serialVersionUID = 1L;

	public Track() {}

	/*******************************************************************/
	/** getLength */

	public Integer getLength()
	{
		return this.length;
	}

	/*******************************************************************/
	/** setLength */

	public void setLength(Integer length)
	{
		this.length = length;
	}
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
	/*******************************************************************/
	/** getAlbum */

	public Album getAlbum()
	{
		return this.album;
	}

	/*******************************************************************/
	/** setAlbum */

	public void setAlbum(Album album)
	{
		this.album = album;
	}

} // Track

/*******************************************************************/
// End of file
/*******************************************************************/

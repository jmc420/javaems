/*******************************************************************/
/*
	File: Album.java
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
public class Album extends Identifier implements Serializable
{
	@Column(name="name", nullable=false, length=255)
	protected String		name;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="artistId", nullable=false)
	protected Artist		artist;
	private static final long serialVersionUID = 1L;

	public Album() {}

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
	/** getArtist */

	public Artist getArtist()
	{
		return this.artist;
	}

	/*******************************************************************/
	/** setArtist */

	public void setArtist(Artist artist)
	{
		this.artist = artist;
	}


} // Album

/*******************************************************************/
// End of file
/*******************************************************************/

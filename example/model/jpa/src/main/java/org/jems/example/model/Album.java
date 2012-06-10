/*******************************************************************/
/*
	File: Album.java
*/
/*******************************************************************/

package org.jems.example.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Album extends Identifier implements Serializable
{
	private static final long serialVersionUID = 1L;

	public Album() {}


} // Album

/*******************************************************************/
// End of file
/*******************************************************************/

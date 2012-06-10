/*******************************************************************/
/*
	File: Track.java
*/
/*******************************************************************/

package org.jems.example.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Track extends Identifier implements Serializable
{
	private static final long serialVersionUID = 1L;

	public Track() {}


} // Track

/*******************************************************************/
// End of file
/*******************************************************************/

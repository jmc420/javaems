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
	private static final long serialVersionUID = 1L;

	public Artist() {}


} // Artist

/*******************************************************************/
// End of file
/*******************************************************************/

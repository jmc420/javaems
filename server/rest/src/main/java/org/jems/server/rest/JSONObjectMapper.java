/*******************************************************************/
/*
	File: JSONObjectMapper.java
*/
/*******************************************************************/

package org.jems.server.rest;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

/*******************************************************************/

public class JSONObjectMapper extends ObjectMapper
{
	
	public JSONObjectMapper()
	{
		addIgnores();
	}
	
	/*******************************************************************/
	// public methods
	/*******************************************************************/
	
	/*******************************************************************/
	// protected methods
	/*******************************************************************/
	/** addIgnores */
	
	protected void addIgnores()
	{
	SerializationConfig cfg = this.getSerializationConfig();
	
		cfg.disable(SerializationConfig.Feature.AUTO_DETECT_IS_GETTERS);

	}

} // JSONObjectMapper

/*******************************************************************/


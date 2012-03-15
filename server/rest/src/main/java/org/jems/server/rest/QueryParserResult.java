/*******************************************************************/
/*
	File: QueryParserResult.java
*/
/*******************************************************************/

package org.jems.server.rest;

import java.util.List;

import org.jems.dao.types.DaoFilter;
import org.jems.dao.types.DaoSort;

/*******************************************************************/

public class QueryParserResult
{
	protected DaoFilter	m_filterList[];
	protected DaoSort	m_sortList[];
	
	public QueryParserResult(List<DaoFilter> filterList, List<DaoSort> sortList)
	{
		m_filterList = new DaoFilter[filterList.size()];
		filterList.toArray(m_filterList);
		m_sortList = new DaoSort[sortList.size()];
		sortList.toArray(m_sortList);
	}
	
	/*******************************************************************/
	// public methods
	/*******************************************************************/
	/** getFilter */
	
	public DaoFilter[] getFilter()
	{
		return m_filterList;
		
	} // getFilter
	
	/*******************************************************************/
	/** getSort */
	
	public DaoSort[] getSort()
	{
		return m_sortList;
		
	} // getSort
	
	/*******************************************************************/
	// protected methods
	/*******************************************************************/
	

} // QueryParserResult

/*******************************************************************/


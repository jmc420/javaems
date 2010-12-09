/*******************************************************************/
/*
        File: SQLQuery.java
        
        SQLQUery splits up an SQL into its constituent parts.
        	
		Author: James Cowan
*/
/*******************************************************************/

package org.jems.dao;

/*******************************************************************/
/** SQLQuery */

public class SQLQuery
{
	protected String 	m_groupBy;
	protected String 	m_orderBy;
	protected String	m_query;
	protected String 	m_select;
	protected String	m_where;
	
	public static final String	GROUP_BY = "GROUP BY";
	public static final String	ORDER_BY = "ORDER BY";
	public static final String	WHERE = "WHERE";
	
	public SQLQuery(String query)
	{
	String uCaseQuery = query.toUpperCase();
	int orderByIndex = getOrderByIndex(uCaseQuery);
	int groupByIndex = getGroupByIndex(uCaseQuery);
	int whereIndex = getWhereIndex(uCaseQuery);
	
		m_query = query;
		m_orderBy = getOrderBy(query, orderByIndex);
		m_where = getWhere(query, whereIndex, groupByIndex, orderByIndex);
		m_groupBy = getGroupBy(query, groupByIndex, orderByIndex);
		m_select = getSelect(query, whereIndex, groupByIndex, orderByIndex);
	}
	
	/*******************************************************************/
	/** getGroupBy */
	
	public String getGroupBy()
	{
		return m_groupBy;
		
	} // getGroupBy
	
	/*******************************************************************/
	/** getOrderBy */
	
	public String getOrderBy()
	{
		return m_orderBy;
		
	} // getOrderBy
	
	/*******************************************************************/
	/** getQuery */
	
	public String getQuery()
	{
		return m_query;
		
	} // getQuery
	
	/*******************************************************************/
	/** getSelect */
	
	public String getSelect()
	{
		return m_select;
		
	} // getSelect
	
	/*******************************************************************/
	/** getWhere */
	
	public String getWhere()
	{
		return m_where;
		
	} // getWhere
	
	/*******************************************************************/
	// protected methods
	/*******************************************************************/
	/** getGroupBy */
	
	protected String getGroupBy(String query, int groupByIndex, int orderByIndex)
	{
		if (groupByIndex < 0)
		{
			return "";
		}
		
		if (orderByIndex < 0)
		{
			return query.substring(groupByIndex);
		}
		
		return query.substring(groupByIndex, orderByIndex);
		
	} // getGroupBy
	
	/*******************************************************************/
	/** getGroupByIndex */
	
	protected int getGroupByIndex(String uCaseQuery)
	{
		return uCaseQuery.indexOf(GROUP_BY);
		
	} // getGroupByIndex
	
	/*******************************************************************/
	/** getOrderBy */
	
	protected String getOrderBy(String query, int orderByIndex)
	{
		if (orderByIndex < 0)
		{
			return "";
		}
		
		return query.substring(orderByIndex);
		
	} // getOrderBy
	
	/*******************************************************************/
	/** getOrderByIndex */
	
	protected int getOrderByIndex(String uCaseQuery)
	{
		return uCaseQuery.indexOf(ORDER_BY);
		
	} // getOrderByIndex
	
	/*******************************************************************/
	/** getSelect */
	
	protected String getSelect(String query, int whereIndex, int groupByIndex, int orderByIndex)
	{
		if (whereIndex >= 0)
		{
			return query.substring(0, whereIndex);
		}
		
		if (groupByIndex >= 0)
		{
			return query.substring(0, groupByIndex);
		}
		
		if (orderByIndex >= 0)
		{
			return query.substring(0, orderByIndex);
		}
		
		return query;
		
	} // getSelect
	
	/*******************************************************************/
	/** getWhere */
	
	protected String getWhere(String query, int whereIndex, int groupByIndex, int orderByIndex)
	{
		if (whereIndex < 0)
		{
			return "";
		}
		
		if (groupByIndex >= 0)
		{
			return query.substring(whereIndex, groupByIndex);
		}
		
		if (orderByIndex >= 0)
		{
			return query.substring(whereIndex, orderByIndex);
		}
		
		return query.substring(whereIndex);
		
	} // getWhere
	
	/*******************************************************************/
	/** getWhereIndex */
	
	protected int getWhereIndex(String uCaseQuery)
	{
		return uCaseQuery.indexOf(WHERE);
		
	} // getWhereIndex
	
} // SQLQuery

/*******************************************************************/
// End of class
/*******************************************************************/

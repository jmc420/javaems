/*******************************************************************/
/*
	File: QueryParser.java
	
	parses strings like filter="{name}{operator}{value}"&sort="name:asc";
	
*/
/*******************************************************************/

package org.jems.server.rest;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.jems.dao.DaoException;
import org.jems.dao.types.DaoFilter;
import org.jems.dao.types.DaoSort;
import org.jems.dao.types.EntityPropertyMetaData;
import org.jems.service.IEntityMetaDataService;

/*******************************************************************/

public class QueryParser
{
	protected IEntityMetaDataService	m_entityMetaDataService;
	
	protected final static Pattern ID_MATCH = Pattern.compile("[a-zA-Z][a-zA-Z0-9_]*");
	protected final static Pattern OP_MATCH = Pattern.compile("=|!=|>|>=|<|<=");
	
	static protected Logger		m_log = Logger.getLogger(QueryParser.class);
	
	protected final static String	FILTER="filter";
	protected final static String	SORT="sort";
	protected final static String	SORT_ASC=":asc";
	protected final static String	SORT_DESC=":desc";
	
	public QueryParser(IEntityMetaDataService entityMetaDataService)
	{
		m_entityMetaDataService = entityMetaDataService;
	}
	
	/*******************************************************************/
	// public methods
	/*******************************************************************/
	/** parseQuery */
	
	public QueryParserResult parseQuery(String entityName, String query) throws DaoException
	{
	List<DaoFilter> filterList = new ArrayList<DaoFilter>();
	List<DaoSort> sortList = new ArrayList<DaoSort>();
	
		query = URLDecoder.decode(query);
		
		m_log.debug("Query "+query);
		
		if (query == null)
		{
			return new QueryParserResult(filterList, sortList);
		}
		
		do
		{
			if (query.startsWith("&"))
			{
				query = query.substring(1);
			}
			
			String parameterName = getParameterName(query);
		
			if (parameterName.equals(""))
			{
				break;
			}
			
			// advance beyond parameter name
			
			query = query.substring(parameterName.length());
			
			if (!query.startsWith("="))
			{
				throw new DaoException(query+" is missing =");
			}
			
			// advance beyond =
			
			query = query.substring(1);
			
			String parameterValue = getParameterValue(query);
			
			// advance beyond parameter value and 2 enclosing quotes
			
			query = query.substring(parameterValue.length()+2);
			
			if (parameterName.equals(FILTER))
			{
				filterList.add(getFilter(entityName, parameterValue));
				continue;
			}
			
			if (parameterName.equals(SORT))
			{
				sortList.add(getSort(entityName, parameterValue));
				continue;
			}
		}
		while (true);
		
		return new QueryParserResult(filterList, sortList);
		
	} // parseQuery
	
	/*******************************************************************/
	// protected methods
	/*******************************************************************/
	/** getFilter */
	
	protected DaoFilter getFilter(String entityName, String query) throws DaoException
	{
	DaoFilter filter = new DaoFilter();
	EntityPropertyMetaData property = getIdentifier(entityName, query);
	String columnName = property.getName();
	
		query = query.substring(columnName.length());
		
		String operator = getOperator(query);
		String value = query.substring(operator.length());
		
		filter.setColumn(columnName);
		filter.setOperation(operator);
		filter.setValue(getValue(property, value));
	
		return filter;

	} // getFilter
	
	/*******************************************************************/
	/** getIdentifier */
	
	protected EntityPropertyMetaData getIdentifier(String entityName, String query) throws DaoException
	{
	Matcher matcher = ID_MATCH.matcher(query);
	
		if(matcher.find())
		{
		String name = query.substring(matcher.start(), matcher.end());
		
			return m_entityMetaDataService.getEntityPropertyMetaData(entityName, name);
		}
		
		throw new DaoException(query+" has invalid identifer");

	} // getIdentifier
	
	/*******************************************************************/
	/** getOperator */
	
	protected String getOperator(String query) throws DaoException
	{
	Matcher matcher = OP_MATCH.matcher(query);
	
		if(matcher.find())
		{
			return query.substring(matcher.start(), matcher.end());
		}
		
		throw new DaoException(query+" has invalid operator");

	} // getOperator
	
	/*******************************************************************/
	/** getParameterName */
	
	protected String getParameterName(String query) throws DaoException
	{
		if (query.startsWith(FILTER))
		{
			return FILTER;
		}
		
		if (query.startsWith(SORT))
		{
			return SORT;
		}
		
		if (query.trim().equals(""))
		{
			return "";
		}
		
		throw new DaoException(query+" has invalid parameter name: "+FILTER+" or "+SORT+" expected");

	} // getParameterName
	
	/*******************************************************************/
	/** getParameterValue */
	
	protected String getParameterValue(String query) throws DaoException
	{
		if (!query.startsWith("\""))
		{
			throw new DaoException(query+" is missing \"");
		}
		
		query = query.substring(1);
		
		int index = query.indexOf("\"", 1);
		
		if (index < 0)
		{
			throw new DaoException(query+" is missing terminating \"");
		}
		
		return query.substring(0, index);
		
	} // getParameterValue
	
	/*******************************************************************/
	/** getSort */
	
	protected DaoSort getSort(String entityName, String query) throws DaoException
	{
	DaoSort sort = new DaoSort();
	EntityPropertyMetaData property = getIdentifier(entityName, query);
	String columnName = property.getName();
	
		// move beyond identifier
	
		query = query.substring(columnName.length());
		
		sort.setColumn(columnName);
		sort.setAscending(getSortAscending(query));

		return sort;

	} // getSort
	
	/*******************************************************************/
	/** getSortAscending */
	
	protected boolean getSortAscending(String query) throws DaoException
	{
		if (query.equals(""))
		{
			return true;
		}
		
		if (query.startsWith(SORT_ASC))
		{
			return true;
		}
		
		if (query.startsWith(SORT_DESC))
		{
			return false;
		}
		
		throw new DaoException(query+" is an invalid sort: "+SORT_ASC+" or "+SORT_DESC+" expected");

	} // getSortAscending
	
	/*******************************************************************/
	/** getValue */
	
	protected Object getValue(EntityPropertyMetaData property, String value) throws DaoException
	{
	String typeName = property.getTypeName();
	
		if (typeName.equals("String"))
		{
			return value;
		}

		if (typeName.equals("Long"))
		{
			return Long.parseLong(value);
		}
	
		if (typeName.equals("Integer"))
		{
			return Integer.parseInt(value);
		}
		
		throw new DaoException(property.getName()+" cannot be converted to "+typeName);
		
	} // getValue	
	
} // QueryParser

/*******************************************************************/


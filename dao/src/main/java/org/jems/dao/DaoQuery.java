/*******************************************************************/
/*
        File: DaoQuery.java
        
        A generic JPA Query class that provides filter and sort.
        	
		Author: James Cowan
*/
/*******************************************************************/

package org.jems.dao;

import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.jems.dao.types.DaoFilter;
import org.jems.dao.types.DaoParameter;
import org.jems.dao.types.DaoSort;

/*******************************************************************/
/** TypeConverter */

abstract class TypeConverter
{
	abstract public Object convert(Object src);

	abstract public Class<?> getFromClass();

	abstract public Class<?> getToClass();
	
} // TypeConverter

/*******************************************************************/
/** IntegerToLongTypeConverter */

class IntegerToLongTypeConverter extends TypeConverter
{

	public Object convert(Object src)
	{
	Integer intValue = (Integer)src;
	
		return new Long(intValue.longValue());
	}

	public Class<?> getFromClass()
	{
		return Integer.class;
		
	} // getFromClass

	public Class<?> getToClass()
	{
		return Long.class;
		
	} // getToClass
	
} // IntoToLongTypeConverter

/*******************************************************************/
/** DoubleToLongTypeConverter */

class DoubleToLongTypeConverter extends TypeConverter
{
	public Object convert(Object src)
	{
	Double dValue = (Double)src;
	
		return new Long(dValue.longValue());
	}

	public Class<?> getFromClass()
	{
		return Double.class;
		
	} // getFromClass

	public Class<?> getToClass()
	{
		return Long.class;
		
	} // getToClass
	
} // IntoToLongTypeConverter

/*******************************************************************/

public class DaoQuery<T> 
{
	protected String	m_queryString;
	protected DaoFilter	m_filters[];
	protected DaoParameter	m_params[];
	
	private final static String		PARAMNAME = "param";
	private final static String		PARAMPREFIX = ":";
	static private HashMap<String, TypeConverter>	m_typeConvertTable;
	
	static protected Logger		m_log = Logger.getLogger(JpaDao.class);

	public DaoQuery(String query, DaoParameter params[], DaoFilter filters[], DaoSort sorts[])
	{
	SQLQuery sqlQuery = new SQLQuery(query);
	String sort = getSort(sqlQuery, sorts);
	String where = getWhere(sqlQuery, filters);
	
		m_filters = filters;
		m_params = params;
		m_queryString = sqlQuery.getSelect()+" "+where+" "+sqlQuery.getGroupBy()+" "+sort;
		
		if (m_log.isInfoEnabled())
		{
			m_log.info(" Query: "+m_queryString);
		}
	}

	/*******************************************************************/
	/** getQuery */
	
	public Query getQuery(EntityManager EntityManager)
	{
	Query query = EntityManager.createQuery(m_queryString);
	boolean log = m_log.isInfoEnabled();
		
		for (int count=0; count<m_params.length; count++)
		{
		DaoParameter param = m_params[count];
		String name = param.getColumn();
		String parameterName = getParameterName(name);
		Object value = getValue(param);
		
			if (log)
			{
				m_log.info(" parameter: "+parameterName+" value: "+value.toString());
			}
			query.setParameter(parameterName, value);
		}
		
		for (int count=0; count<m_filters.length; count++)
		{
		DaoFilter filter = m_filters[count];
		String parameterName = PARAMNAME+count;
		Object value = getValue(filter);
		
			if (log)
			{
				m_log.info(" filter: "+parameterName+" value: "+value.toString());
			}
		
			query.setParameter(parameterName, value);
		}
		
		return query;

	} // getQuery

	/*******************************************************************/
	/** getQueryString */
	
	public String getQueryString()
	{
		return m_queryString;
		
	} // getQueryString
	
	/*******************************************************************/
	// // protected methods
	/*******************************************************************/
	/** getParameterName */
	
	protected String getParameterName(String name)
	{
		return name.replace(".", "_");
		
	} // getParameterName
	/*******************************************************************/
	/** getSort */
	
	protected String getSort(SQLQuery sqlQuery, DaoSort DaoSorts[])
	{
	String sort = sqlQuery.getOrderBy();
		
		if (DaoSorts.length == 0)
			return sort;
		
		if (sort.equals(""))
		{
			sort = SQLQuery.ORDER_BY;
		}

		for (int count=0; count<DaoSorts.length; count++)
		{
		DaoSort daoSort = DaoSorts[count];
		String name = daoSort.getColumn();
		
			if (count > 0)
				sort += ",";
			
			sort += " "+name+" ";
			
			if (daoSort.getAscending())
				sort += "ASC";
			else
				sort += "DESC";	
		}
		
		return sort;
		
	} // getSort

	/*******************************************************************/
	/** getValue */
	
	protected Object getValue(DaoParameter parameter)
	{
	String className = parameter.getClassName();
	Object value = parameter.getValue();
	
		if (className.equals(""))
		{
			return value;
		}
		
		// Hibernate type information has type Long rather java.lang.Long
		
		Class<?> valueClass = value.getClass();
		String valueClassName = valueClass.getName();
		String simpleValueClassName = valueClass.getSimpleName();
		
		if (valueClassName.equals(className) || simpleValueClassName.equals(className))
		{
			return value;
		}
		
		String key = valueClassName+className;
		TypeConverter typeConverter = m_typeConvertTable.get(key);
		
		if (typeConverter != null)
		{
			return typeConverter.convert(value);
		}
		
		return value;

	} // getValue
	
	/*******************************************************************/
	/** getWhere */
	
	protected String getWhere(SQLQuery sqlQuery, DaoFilter daoFilters[])
	{
	String where = sqlQuery.getWhere();

		if (daoFilters.length == 0)
			return where;

		if (where.equals(""))
		{
			where = SQLQuery.WHERE;
		}

		for (int count=0; count<daoFilters.length; count++)
		{
		DaoFilter daoFilter = daoFilters[count];
		String name = daoFilter.getColumn();
		String op = daoFilter.getOperation();
		String startExpression = "";
		String endExpression = "";
		String booleanOp = daoFilter.getBooleanOperation();
		
			if (booleanOp == null)
			{
				booleanOp = "AND";
			}
		
			if (daoFilter.isStartExpression())
			{
				startExpression = "(";
			}
		
			if (daoFilter.isEndExpression())
			{
				endExpression = ")";
			}
			
			if (count > 0)
				where += " "+booleanOp+" ";
			
			where += " "+startExpression+name+" "+op+" "+PARAMPREFIX+PARAMNAME+count+endExpression;
		}
		
		return where;
		
	} // getWhere

	/*******************************************************************/
	// static init
	/*******************************************************************/

	static
	{
	TypeConverter typeConverters[] = new TypeConverter[]
	{
		new IntegerToLongTypeConverter(),
		new DoubleToLongTypeConverter()
	};
		m_typeConvertTable = new HashMap<String, TypeConverter>();
		
		for (int count=0; count<typeConverters.length; count++)
		{
		TypeConverter typeConverter = typeConverters[count];
		Class<?> fromClass = typeConverter.getFromClass();
		Class<?> toClass = typeConverter.getToClass();
		String key = fromClass.getName()+toClass.getName();
		String simpleKey = fromClass.getName()+toClass.getSimpleName();
		
			m_typeConvertTable.put(key, typeConverter);
			m_typeConvertTable.put(simpleKey, typeConverter);
		}

	}
	
} // DaoQuery

/*******************************************************************/




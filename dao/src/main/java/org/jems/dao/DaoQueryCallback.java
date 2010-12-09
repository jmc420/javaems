/*******************************************************************/
/*
        File: DaoQueryCallback.java
        
        Spring JPA Callback class
        
        Author: James Cowan
*/
/*******************************************************************/

package org.jems.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jems.dao.types.DaoFilter;
import org.jems.dao.types.DaoParameter;
import org.jems.dao.types.DaoSort;
import org.springframework.orm.jpa.JpaCallback;

/*******************************************************************/

public class DaoQueryCallback<T> extends DaoQuery<T> implements JpaCallback
{
	protected int	m_startRow;
	protected int	m_maxRows;
	
	public DaoQueryCallback(Class<T> cl)
	{
		this(cl, new DaoFilter[0], new DaoSort[0], 0, 0);
	}
	
	public DaoQueryCallback(Class<T> cl, DaoFilter filters[])
	{
		this(cl, filters, new DaoSort[0], 0, 0);
	}
	
	public DaoQueryCallback(Class<T> cl, DaoFilter filters[], DaoSort sorts[])
	{
		this(cl, filters, sorts, 0, 0);
	}
	
	public DaoQueryCallback(Class<T> cl, DaoFilter filters[], DaoSort sorts[], int startRow, int maxRows)
	{
		this("FROM "+cl.getName(), new DaoParameter[0], filters, sorts, startRow, maxRows);
	}
	
	public DaoQueryCallback(String query, DaoParameter params[], DaoFilter filters[], DaoSort sorts[], int startRow, int maxRows)
	{
		super(query, params, filters, sorts);
		
		m_startRow = startRow;
		m_maxRows = maxRows;
	}
	
	/*******************************************************************/
	/** doInJpa */
	
	public Object doInJpa(EntityManager em)
    {
	Query query = getQuery(em);

		if (m_startRow > 0)
		{
			query.setFirstResult(m_startRow);
		}
		
		if (m_maxRows > 0)
		{
			query.setMaxResults(m_maxRows);
		}

		return query.getResultList();
		
    } // doInJpa
	
} // DaoQueryCallback

/*******************************************************************/


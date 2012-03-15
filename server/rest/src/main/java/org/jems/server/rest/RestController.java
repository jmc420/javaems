/*******************************************************************/
/*
	File: RestController.java
*/
/*******************************************************************/

package org.jems.server.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jems.dao.DaoException;
import org.jems.dao.DaoNotFoundException;
import org.jems.dao.types.DaoFilter;
import org.jems.dao.types.DaoSort;
import org.jems.dao.types.EntityMetaData;
import org.jems.dao.types.EntityPropertyMetaData;
import org.jems.service.IEntityMetaDataService;
import org.jems.service.IEntityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/*******************************************************************/

@Controller
public class RestController
{
	protected IEntityMetaDataService	m_entityMetaDataService;
	protected IEntityService			m_entityService;
	protected QueryParser				m_queryParser;
	
	static protected Logger		m_log = Logger.getLogger(RestController.class);
	
	public RestController(IEntityService entityService, IEntityMetaDataService entityMetaDataService)
	{
		m_entityService = entityService;
		m_entityMetaDataService = entityMetaDataService;
		m_queryParser = new QueryParser(entityMetaDataService);
	}
	
	/*******************************************************************/
	// public methods
	/*******************************************************************/
	/** deleteEntity */
	
	@RequestMapping(value="/{entityName}/{entityId}",method=RequestMethod.DELETE)
	public @ResponseBody void deleteEntity(@PathVariable String entityName, @PathVariable Long entityId)
	throws DaoException, DaoNotFoundException
	{
		m_entityService.remove(get(entityName, entityId));
	    
	} // deleteEntity
	
	/*******************************************************************/
	/** getEntity */
	
	@RequestMapping(value="/{entityName}/{entityId}",method=RequestMethod.GET)
	public @ResponseBody <T> T getEntity(@PathVariable String entityName, @PathVariable Long entityId)
	throws DaoException, DaoNotFoundException
	{
		return get(entityName, entityId);
	    
	} // getEntity
	
	/*******************************************************************/
	/** getEntityMetaData */
	
	@RequestMapping(value="/meta",method=RequestMethod.GET)
	public @ResponseBody List<EntityMetaData> getEntityMetaData()
	{
		return m_entityMetaDataService.getEntityMetaData();
	    
	} // getEntityMetaData
	
	/*******************************************************************/
	/** getList */
	
	@RequestMapping(value="/{entityName}",method=RequestMethod.GET)
	public @ResponseBody <T>List<T> getList(@PathVariable String entityName, HttpServletRequest request)
	throws ClassNotFoundException, DaoException
	{
	String query = request.getQueryString();
	QueryParserResult queryResult = m_queryParser.parseQuery(entityName, query);
	DaoFilter filters[] = queryResult.getFilter();
	DaoSort sorts[] = queryResult.getSort();
	
		return m_entityService.getList(entityName, filters, sorts);
	    
	} // getList
	
	/*******************************************************************/
	/** getListCount */
	
	@RequestMapping(value="/count/{entityName}",method=RequestMethod.GET)
	public @ResponseBody long getListCount(@PathVariable String entityName, HttpServletRequest request)
	throws ClassNotFoundException, DaoException
	{
	DaoFilter filters[] = new DaoFilter[0];
	
		return m_entityService.getListCount(entityName, filters);		
	    
	} // getListCount
	
	/*******************************************************************/
	/** postEntity */
	
	@RequestMapping(value="/{entityName}/{entityId}",method=RequestMethod.POST)
	public @ResponseBody <T> T postEntity(@PathVariable String entityName, @PathVariable Long entityId)
	throws DaoException, DaoNotFoundException
	{
	T entity = get(entityName, entityId);
	
		return m_entityService.update(entity);
	    
	} // postEntity
	
	/*******************************************************************/
	/** putEntity */
	
	@RequestMapping(value="/{entityName}/{entityId}",method=RequestMethod.PUT)
	public @ResponseBody <T> T putEntity(@PathVariable String entityName, @PathVariable Long entityId)
	throws DaoException, DaoNotFoundException
	{
	T entity = get(entityName, entityId);
	
		return m_entityService.update(entity);
	    
	} // putEntity
	
	/*******************************************************************/
	// protected methods
	/*******************************************************************/
	/** get */
	
	protected <T> T get(String entityName, Long entityId) throws DaoException, DaoNotFoundException
	{
	DaoFilter filter = new DaoFilter();
	DaoFilter filters[] = new DaoFilter[]{filter};
	
		filter.setColumn(getPrimaryKey(entityName));
		filter.setValue(entityId);
		
		return m_entityService.get(entityName, filters);
	    
	} // get
	
	/*******************************************************************/
	/** getPrimaryKey */

	protected String getPrimaryKey(String entityName) throws DaoException
	{
	EntityPropertyMetaData primaryKey = m_entityMetaDataService.getPrimaryKey(entityName);
	
		return primaryKey.getName();
		
	} // getPrimaryKey

} // RestController

/*******************************************************************/


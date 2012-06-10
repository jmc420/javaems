/************************************************************/
/*
	File: DBMetaData.java

	Database meta data

	Author: James Cowan
*/
/************************************************************/

package org.jems.orm;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jems.orm.model.OrmConnection;
import org.jems.orm.model.OrmProperty;
         
public class DBMetaData
{
	protected Connection   		m_connection;
	protected DatabaseMetaData	m_metaData;
	
	static protected Logger		m_log = Logger.getLogger(DBMetaData.class);
         
   	public DBMetaData(OrmConnection ormConnection) 
	throws InstantiationException, IllegalAccessException,
		   ClassNotFoundException, SQLException
	{
    		m_connection = getConnection(ormConnection);
    		m_metaData = m_connection.getMetaData();
	}
         

	/************************************************************/
	// public methods
	/************************************************************/
	/** displayMetaData */
      
	public void displayMetaData(String targetSchema) throws SQLException
	{
	List<String>catalogs = getCatalogs();
	List<String>schemas = getSchemas();
 
		for (String catalog : catalogs)
		{
			m_log.info("\ncatalog:  "+catalog);
		}

		for (String schema : schemas)
        {
			m_log.info("\nschema: "+schema);
        }

		List<String>tables = getTables(targetSchema);
         
		for (String table : tables)
		{
		List<OrmProperty> columns = getOrmColumns(table);
         
			m_log.info("\nTable: "+table);
         
			for (OrmProperty column : columns)
			{
				m_log.info("\n\tcolumn name: "+column.getColumnName()+
						   " type: "+column.getTypeName()+
						   " size: "+column.getColumnSize()+
						   " nullable: "+column.isNullable());
			}
		}
         
	} // displayMetaData
         
	/************************************************************/
	/** getCatalogs */
	 
	public List<String> getCatalogs() throws SQLException
	{
	ResultSet catalogRS = m_metaData.getCatalogs();
	List<String> result = new ArrayList<String>();

		if (catalogRS.next())
		{
			displayMetaData(catalogRS);

			do
			{
			}
			while (catalogRS.next());
		}

		return result;

	} // getCatalogs
         
	/************************************************************/
	/** getColumns */

	public List<String> getColumns(String tableName) throws SQLException
	{
	ResultSet columnRS = m_metaData.getColumns(null, null, tableName, null);
	List<String> result = new ArrayList<String>();

		if (columnRS.next())
		{
			displayMetaData(columnRS);
			
			do
			{
			   result.add(columnRS.getString("COLUMN_NAME"));
			}
			while (columnRS.next());
		}

		return result;

	} // getColumns
	
	/************************************************************/
	/** getOrmColumns */

	public List<OrmProperty> getOrmColumns(String tableName) throws SQLException
	{
	ResultSet columnRS = m_metaData.getColumns(null, null, tableName, null);
	List<OrmProperty> result = new ArrayList<OrmProperty>();

		if (!columnRS.next())
		{
			return result;
		}
	
		//displayMetaData(columnRS);
		
		do
		{
		OrmProperty ormProperty = new OrmProperty();
			
			ormProperty.setColumnName(columnRS.getString("COLUMN_NAME"));
			ormProperty.setColumnSize(columnRS.getInt("COLUMN_SIZE"));
			ormProperty.setNullable(columnRS.getBoolean("NULLABLE"));
			ormProperty.setTypeName(columnRS.getString("TYPE_NAME"));
			result.add(ormProperty);
		}
		while (columnRS.next());

		return result;

	} // getOrmColumns

	/************************************************************/
	/** getSchemas */

	public List<String> getSchemas() throws SQLException
	{
	ResultSet schemaRS = m_metaData.getSchemas();
	List<String> result = new ArrayList<String>();

		if (schemaRS.next())
		{
			//displayMetaData(schemaRS);

			do
			{
				result.add(schemaRS.getString("Table_SCHEM"));
			}
			while (schemaRS.next());
		}

		return result;

	} // getSchemas
         
	/************************************************************/
	/** getTables */

	public List<String> getTables(String schema) throws SQLException
	{
	List<String> result = new ArrayList<String>();
	ResultSet tableRS = m_metaData.getTables(null, schema, null, null);

		if (tableRS.next())
		{
	   		// displayMetaData(tableRS);

			do
			{
			String tableName = tableRS.getString("TABLE_NAME");
			String tableType = tableRS.getString("TABLE_TYPE");

			       if (tableType.equals ("TABLE"))
			       {
				       result.add(tableName);
			       }
			}
			while (tableRS.next());			
		}
		
		return result;
         
	} // getTables
         
	/************************************************************/
	// protected methods
	/************************************************************/
	/** displayMetaData */

	protected void displayMetaData(ResultSet rs) throws SQLException
	{
	ResultSetMetaData md = rs.getMetaData();
	int maxColumns = md.getColumnCount();
 
		for (int count = 1; count <= maxColumns; count++)
		{
		String columnName = md.getColumnName(count);
		String tableName = md.getTableName(count);
		String typeName = md.getColumnTypeName(count);
		boolean writable = md.isWritable(count);

			m_log.info("\ncolumn " + columnName+ " table: "+tableName+
				     	   " type: "+typeName+" writable: "+writable);
		}

	} // displayMetaData
         
	/************************************************************/
	/** getConnection */
         
	protected Connection getConnection(OrmConnection ormConnection)
	throws InstantiationException, IllegalAccessException,
	       ClassNotFoundException, SQLException
	{
	String url = ormConnection.getUrl();
	String user = ormConnection.getUser();
	String password = ormConnection.getPassword();
	
	   Class.forName(ormConnection.getDriver()).newInstance();
	   
	   return DriverManager.getConnection(url, user, password);

	} // getConnection
	
	/************************************************************/
	// public static methods
	/************************************************************/

	public static void main(String argv[])
	{
		if (argv.length < 5)
		{
			System.out.println("Please specify driver, url, user, password and schema");
			return;
		}

		OrmConnection ormConnection = new OrmConnection();
		
		ormConnection.setDriver(argv[0]);
		ormConnection.setUrl(argv[1]);
		ormConnection.setUser(argv[2]);
		ormConnection.setPassword(argv[3]);

		try
		{
		DBMetaData dbMetaData = new DBMetaData(ormConnection);

			dbMetaData.displayMetaData(argv[4]);
		}
		catch (Exception e)
		{
			System.out.print("Exception "+e.getMessage());
		}

	} // main

} // DBMetaData
         
/************************************************************/
         
         
         

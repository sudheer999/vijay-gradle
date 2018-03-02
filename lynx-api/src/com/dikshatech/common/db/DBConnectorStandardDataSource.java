package com.dikshatech.common.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.dikshatech.common.utils.LoggerUtil;

/**
 * The Class DBConnectorPoolingDataSource implements {@link DBConnector}. This
 * is for handling connection object for DataSource
 * 
 * @author Rajesh Hanumanthappa
 * @version $Revision: 01 $ $Date: 2010-03-19 10:51:48 -0500 (Fri, 19 Mar 2010)
 *          $
 */
class DBConnectorStandardDataSource implements DBConnector
{

	public static Logger		log			= LoggerUtil
													.getLogger(DBConnectorStandardDataSource.class);
	private static DataSource	dataSource	= null;

	/**
	 * Instantiates a new dB connector pooling data source.
	 * 
	 * @param dataSource_Context
	 *            the Data Source Context for lookup
	 * @throws Exception
	 *             the SQLException
	 */
	private DBConnectorStandardDataSource(String dataSource_Context) throws SQLException
	{
		try
		{
			Context initialContext = new InitialContext();
			if (initialContext == null)
			{
				log.error("JNDI problem. Cannot get InitialContext.");
			}
			else
			{
				DataSource _datasource = (DataSource) initialContext.lookup(dataSource_Context);
				if (_datasource != null)
				{
					dataSource = _datasource;
				}
				else
				{
					log.error("Failed to lookup datasource.");
				}
			}
		}
		catch (NamingException ne)
		{
			throw new SQLException("Failed to load Initial Context");
		}
	}

	/**
	 * Prints the LoginTimeout value of the DataSource,
	 */
	public void printStatus() throws SQLException
	{

		log.info("LoginTimeOut :" + dataSource.getLoginTimeout());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dikshatech.common.db.DBConnector#getConnection()
	 */
	@Override
	public Connection getConnection() throws SQLException
	{

		return (dataSource != null ? dataSource.getConnection() : null);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dikshatech.common.db.DBConnector#freeConnection(java.sql.Connection)
	 */
	@Override
	public void releaseConnection(Connection connection) throws SQLException
	{
		if (connection != null && !connection.isClosed())
		{
			connection.close();
		}

	}

	public static DBConnectorStandardDataSource getSingletonObject(String dataSource)
			throws SQLException
	{

		return new DBConnectorStandardDataSource(dataSource);

	}

}

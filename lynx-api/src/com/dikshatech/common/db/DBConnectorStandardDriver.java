package com.dikshatech.common.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.Logger;

import com.dikshatech.common.utils.LoggerUtil;

/**
 * The Class DBConnectorPlainDriver implements {@link DBConnector}. This is for
 * simple JDBC connection
 * 
 * @author Rajesh Hanumanthappa
 * @version $Revision: 01 $ $Date: 2010-03-19 10:51:48 -0500 (Fri, 19 Mar 2010)
 *          $
 */
public final class DBConnectorStandardDriver implements DBConnector
{

	public static Logger	log		= LoggerUtil.getLogger(DBConnectorStandardDriver.class);

	/** The url. */
	private String			url		= null;
	private String			user	= null;
	private String			pwd		= null;

	/**
	 * Instantiates a new dB connector plain driver.
	 * 
	 * @param _url
	 *            the _url
	 */
	private DBConnectorStandardDriver(final String _url, final String _user, final String _pwd)
	{
		log.debug("Constructing DBConnectorPlainDriver : " + _url + ":" + _user);
		this.url = _url;
		this.pwd = _pwd;
		this.user = _user;
	}

	public static DBConnectorStandardDriver getSingletonObject(String _url, String _user,
			String _pwd)
	{
		return new DBConnectorStandardDriver(_url, _user, _pwd);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dikshatech.common.db.DBConnector#getConnection()
	 */
	@Override
	public Connection getConnection() throws SQLException
	{
		Connection conn = null;
		if (url != null)
		{
			conn = DriverManager.getConnection(url, user, pwd);
		}

		return conn;
	}

}

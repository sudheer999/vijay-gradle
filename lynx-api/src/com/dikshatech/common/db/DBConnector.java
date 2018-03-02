/**
 * 
 */
package com.dikshatech.common.db;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The Interface DBConnector.
 * 
 * @author Rajesh Hanumanthappa
 * @version $Revision: 01 $ $Date: 2010-03-19 10:51:48 -0500 (Fri, 19 Mar 2010)
 *          $
 */
public interface DBConnector
{

	/** The Standard Driver Manager connection. */
	public final int STANDARD_DM_CONNECTION = 0;

	/** The Standard Data Source connection. */
	public final int STANDARD_DS_CONNECTION = 1;

	/** The Connection Pooling for Driver Manager Connections. */
	public final int POOLING_DM_CONNECTION = 2;

	/**
	 * Gets the connection.
	 * 
	 * @return the connection
	 * @throws SQLException
	 *             the SQL exception
	 */
	public Connection getConnection() throws SQLException;

	/**
	 * Release connection.
	 * 
	 * @param connection
	 *            the DB connection
	 * @throws SQLException
	 *             the sQL exception
	 */
	public void releaseConnection(Connection connection) throws SQLException;

}

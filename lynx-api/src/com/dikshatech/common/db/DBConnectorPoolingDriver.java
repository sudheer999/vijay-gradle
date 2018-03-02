package com.dikshatech.common.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDriver;
import org.apache.commons.pool.KeyedObjectPoolFactory;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericKeyedObjectPoolFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool.Config;
import org.apache.log4j.Logger;

import com.dikshatech.common.utils.LoggerUtil;

/**
 * A {@link DBConnector}-based implementation of {@link DBConnectorFactory}.
 * This class is for Creating and managing connections from connection pool
 * 
 * @author Rajesh Hanumanthappa
 * @version $Revision: 01 $ $Date: 2010-03-19 10:51:48 -0500 (Fri, 19 Mar 2010)
 *          $
 */
public class DBConnectorPoolingDriver implements DBConnector
{
	public static Logger				log						= LoggerUtil
																		.getLogger(DBConnectorStandardDataSource.class);

	/** The pool name. */
	private String						poolName;

	/** if PreparedStatementPool enabled , then set the KeyedObjectPoolFactory **/
	private KeyedObjectPoolFactory		_keyObjectPoolFactory	= null;

	private String						connectURI				= null;
	private String						JDBC_USER				= null;
	private String						JDBC_PWD				= null;
	private GenericObjectPool.Config	dbPoolConfig			= null;
	private boolean						isStatementPool			= false;

	private DBConnectorPoolingDriver()
	{
	}

	/**
	 * Prints the status.
	 * 
	 * @throws SQLException
	 *             the sQL exception
	 */
	public void printStatus() throws SQLException
	{

		PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
		ObjectPool connectionPool = driver.getConnectionPool(poolName);

		log.debug("NumActive connections: " + connectionPool.getNumActive());
		log.debug("NumIdle connections: " + connectionPool.getNumIdle());

	}

	/**
	 * Sets the up.
	 * 
	 * @param connectURI
	 *            the new up
	 * @param dbPoolConfig
	 * @param isStatementPool
	 * @throws ClassNotFoundException
	 * @throws Exception
	 *             the exception
	 */
	public void setup() throws SQLException, ClassNotFoundException
	{

		// First, we'll need a ObjectPool that serves as the
		// actual pool of connections.
		// We'll use a GenericObjectPool instance, although
		// any ObjectPool implementation will suffice.

		GenericObjectPool connectionPool = new GenericObjectPool(null);

		connectionPool.setConfig(dbPoolConfig);

		//
		// Next, we'll create a ConnectionFactory that the
		// pool will use to create Connections.
		// We'll use the DriverManagerConnectionFactory,
		// using the connect string passed in the command line
		// arguments.
		//
		ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(connectURI,
				JDBC_USER, JDBC_PWD);

		if (isStatementPool)
		{
			_keyObjectPoolFactory = new GenericKeyedObjectPoolFactory(null);
		}

		//
		// Now we'll create the PoolableConnectionFactory, which wraps
		// the "real" Connections created by the ConnectionFactory with
		// the classes that implement the pooling functionality.
		//
		@SuppressWarnings("unused")
		PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, connectionPool, _keyObjectPoolFactory,  "SELECT 1", 0, false, true);
		
		//
		// Finally, we create the PoolingDriver itself...
		//

		Class.forName("org.apache.commons.dbcp.PoolingDriver");
		PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");

		//
		// ...and register our pool with it.
		//
		driver.registerPool(poolName, connectionPool);

		//
		// Now we can just use the connect string
		// "jdbc:apache:commons:dbcp:example"
		// to access our pool of Connections.

	}

	/**
	 * Shutdown.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public void shutdown() throws SQLException
	{
		PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
		driver.closePool(poolName);
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
		printStatus();
		if (connection != null && !connection.isClosed())
		{
			log.debug("Connection closed!");
			connection.close();
		}
		printStatus();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dikshatech.common.db.DBConnector#getConnection()
	 */
	@Override
	public Connection getConnection() throws SQLException
	{
		printStatus();
		return DriverManager.getConnection("jdbc:apache:commons:dbcp:" + poolName);
	}

	/**
	 * Sets the pool name.
	 * 
	 * @param poolName
	 *            the new pool name
	 */
	private void setPoolName(String poolName)
	{
		this.poolName = poolName;
	}

	/**
	 * Gets the pool name.
	 * 
	 * @return the pool name
	 */
	public String getPoolName()
	{
		return poolName;
	}

	public static DBConnectorPoolingDriver getSingletonObject(String url, String usr, String pwd,
			Config dbPoolConfig, boolean isStatementPool, String _poolName) throws SQLException,
			ClassNotFoundException
	{

		DBConnectorPoolingDriver dbcpDriver = new DBConnectorPoolingDriver();
		dbcpDriver.connectURI = url;
		dbcpDriver.JDBC_USER = usr;
		dbcpDriver.JDBC_PWD = pwd;
		dbcpDriver.dbPoolConfig = dbPoolConfig;
		dbcpDriver.isStatementPool = isStatementPool;
		dbcpDriver.setPoolName(_poolName);
		
		dbcpDriver.setup();

		return dbcpDriver;
	}

}

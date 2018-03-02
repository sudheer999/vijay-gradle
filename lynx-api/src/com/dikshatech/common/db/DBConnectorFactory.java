package com.dikshatech.common.db;

import static com.dikshatech.common.db.DBConnector.POOLING_DM_CONNECTION;
import static com.dikshatech.common.db.DBConnector.STANDARD_DM_CONNECTION;
import static com.dikshatech.common.db.DBConnector.STANDARD_DS_CONNECTION;

import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;

import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.PropertyLoader;

/**
 * A factory for creating DBConnector objects.
 * 
 * 
 * @author Rajesh Hanumanthappa
 * @version $Revision: 01 $ $Date: 2010-03-19 10:51:48 -0500 (Fri, 19 Mar 2010)
 *          $
 */

public class DBConnectorFactory
{

	/**
	 * Instantiates a new dB connector factory and will load DB connection
	 * properties from the file ../conf/dbconnections.properties. Properties to
	 * set are, <br>
	 * For {@link STANDARD_DM_CONNECTION} or {@link POOLING_DM_CONNECTION} <br>
	 * {@link JDBC_URL} , {@link JDBC_USER} ,{@link JDBC_PWD} <br>
	 * For {@link STANDARD_DS_CONNECTION} <br> {@link JDBC_DS}
	 */
	public DBConnectorFactory(String connectionID)
	{

		try
		{
			Properties pro = PropertyLoader.loadProperties("conf.Portal.properties");
			setJDBC_URL(pro.getProperty(connectionID + ".JDBC_URL"));
			setJDBC_USER(pro.getProperty(connectionID + ".JDBC_USER"));
			setJDBC_PWD(pro.getProperty(connectionID + ".JDBC_PWD"));
			setJDBC_DS(pro.getProperty(connectionID + ".JDBC_DS"));

			if (getJDBC_URL() != null)
			{
				/*Class.forName(pro.getProperty(connectionID + ".JDBC_DRIVER"));*/
				Class.forName("com.mysql.jdbc.Driver");
			}	
		}
		catch (ClassNotFoundException e)
		{
			log.error("Unable to load Driver Class!" + e);
			e.printStackTrace();
		}

	}

	/** The log. */
	private static Logger log = LoggerUtil.getLogger(DBConnectorFactory.class);

	/**
	 * The db pool configuraions like maxActive,maxIdle,etc @link
	 * GenericObjectPool.Config .
	 */
	private GenericObjectPool.Config dbPoolConfig = new GenericObjectPool.Config();

	/** The JDBC db url. */
	private String JDBC_URL = null;

	/** The JDBC db userName. */
	private String JDBC_USER = null;

	/** The JDBC db pwd. */
	private String JDBC_PWD = null;

	/** The JDBC db ds. */
	private String JDBC_DS = null;

	/**
	 * Gets the jDBC data source.
	 * 
	 * @return the jDBC data source
	 */
	public String getJDBC_DS()
	{
		return JDBC_DS;
	}

	/**
	 * Sets the jDBC data source.
	 * 
	 * @param dataSource
	 *            the new jDBC data source
	 */
	public void setJDBC_DS(String dataSource)
	{
		JDBC_DS = dataSource;
	}

	/**
	 * Gets the db pool config.
	 * 
	 * @return the db pool config
	 */
	public GenericObjectPool.Config getDbPoolConfig()
	{
		return dbPoolConfig;
	}

	/**
	 * Sets the db pool config.
	 * 
	 * @param dbPoolConfig
	 *            the new db pool config
	 */
	public void setDbPoolConfig(GenericObjectPool.Config dbPoolConfig)
	{
		this.dbPoolConfig = dbPoolConfig;
	}

	/**
	 * Gets the JDBC connection url.
	 * 
	 * @return the JDBC connection url
	 */
	public String getJDBC_URL()
	{
		return JDBC_URL;
	}

	/**
	 * Sets the JDBC connection url.
	 * 
	 * @param url
	 *            the new JDBC connection url
	 */
	public void setJDBC_URL(String url)
	{
		JDBC_URL = url;
	}

	/**
	 * Gets the JDBC user.
	 * 
	 * @return the JDBC user
	 */
	public String getJDBC_USER()
	{
		return JDBC_USER;
	}

	/**
	 * Sets the JDBC user.
	 * 
	 * @param user
	 *            the new JDBC user
	 */
	public void setJDBC_USER(String user)
	{
		JDBC_USER = user;
	}

	/**
	 * Gets the JDBC password.
	 * 
	 * @return the JDBC password
	 */
	public String getJDBC_PWD()
	{
		return JDBC_PWD;
	}

	/**
	 * Sets the JDBC password.
	 * 
	 * @param password
	 *            the new JDBC password
	 */
	public void setJDBC_PWD(String password)
	{
		JDBC_PWD = password;
	}

	/**
	 * Gets the dB connector.
	 * 
	 * @param connectType
	 *            the connect type defined in {@link DBConnector}
	 * @return the {@link DBConnector}
	 * @throws Exception
	 *             the SQLexception
	 * @throws SQLException
	 *             the sQL exception
	 */
	public DBConnector getDBConnector(int connectType) throws SQLException
	{
		DBConnector dbConnector = null;

		if (connectType == STANDARD_DM_CONNECTION)
		{
			if (JDBC_URL == null || JDBC_USER == null || JDBC_PWD == null)
				throw new SQLException("Invalid arguments JDBC_URL=" + JDBC_URL + ",JDBC_USER,"
				        + JDBC_USER + ",JDBC_PWD" + JDBC_PWD);

			DBConnectorStandardDriver _plainDriver = DBConnectorStandardDriver.getSingletonObject(
			        JDBC_URL, JDBC_USER, JDBC_PWD);
			log.debug("Created Plain Driver DBConnector");
			dbConnector = _plainDriver;
		}
		else if (connectType == STANDARD_DS_CONNECTION)
		{
			if (JDBC_DS == null)
				throw new SQLException("Invalid arguments JDBC_DS=" + JDBC_DS);

			DBConnectorStandardDataSource _poolingDataSource = DBConnectorStandardDataSource
			        .getSingletonObject(JDBC_DS);
			log.debug("Created Pooling DataSource DBConnector");
			dbConnector = _poolingDataSource;
		}

		else
		{
			throw new SQLException("Invalid arguments connectType=" + connectType);
		}

		log.debug("Leaving Method getDBConnector Successfully!");
		return dbConnector;
	}

	/**
	 * Gets the dB connector.
	 * 
	 * @param connectType
	 *            the connect type
	 * @param poolName
	 *            the pool name
	 * @param isStatementPool
	 *            to enable the PreparedStatementPool
	 * @return the dB connector
	 * @throws Exception
	 *             the exception
	 */
	public DBConnector getDBConnector(int connectType, String poolName, boolean isStatementPool)
	        throws SQLException, ClassNotFoundException
	{
		DBConnector dbConnector = null;

		if (JDBC_URL == null || JDBC_USER == null || JDBC_PWD == null)
			throw new SQLException("Invalid arguments JDBC_URL=" + JDBC_URL + ",JDBC_USER,"
			        + JDBC_USER + ",JDBC_PWD " + JDBC_PWD);

		if (connectType == POOLING_DM_CONNECTION)
		{
			dbPoolConfig.minEvictableIdleTimeMillis = 60000 * 30;
			dbPoolConfig.timeBetweenEvictionRunsMillis = 60000 * 10;
			dbPoolConfig.numTestsPerEvictionRun = -1;
			
			DBConnectorPoolingDriver _poolDriverManager = DBConnectorPoolingDriver
			        .getSingletonObject(JDBC_URL, JDBC_USER, JDBC_PWD, dbPoolConfig,
			                isStatementPool, poolName);
			dbConnector = _poolDriverManager;
		}

		else
		{
			throw new SQLException("Invalid arguments connectType=" + connectType);
		}

		log.debug("Leaving Method getDBConnector Successfully!");
		return dbConnector;
	}

	/**
	 * Close pool db connector.
	 * 
	 * @param dbConnector
	 *            the db connector
	 * @throws SQLException
	 * @throws Exception
	 *             the exception
	 */
	public static void closePoolDBConnector(DBConnector dbConnector) throws SQLException
	{

		if (dbConnector instanceof DBConnectorPoolingDriver)
		{
			DBConnectorPoolingDriver _poolingDriver = (DBConnectorPoolingDriver) dbConnector;
			log.debug("Released Connection resource.");
			_poolingDriver.shutdown();
		}
		log.debug("Leaving Method closePoolDBConnector Successfully!");

	}

}

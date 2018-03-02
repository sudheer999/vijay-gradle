package com.dikshatech.common.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.dikshatech.common.utils.LoggerUtil;

public class MyDBConnect
{
	private static Logger log = LoggerUtil.getLogger(MyDBConnect.class);

	static DBConnector dbConnector = null;
	static DBConnectorFactory dbcf = null;

	static
	{
		log.debug("Setting up driver.");
		try
		{
			dbcf = new DBConnectorFactory("conn1");
			dbConnector = dbcf.getDBConnector(DBConnector.POOLING_DM_CONNECTION, "mypool", true);
			log.debug("Done.");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public static Connection getConnect()
	{
		try
		{
			return dbConnector.getConnection();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void close(Connection conn) throws SQLException
	{
		dbConnector.releaseConnection(conn);
	}

	public static void releaseConnection()
	{
		try
		{
			DBConnectorFactory.closePoolDBConnector(dbConnector);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

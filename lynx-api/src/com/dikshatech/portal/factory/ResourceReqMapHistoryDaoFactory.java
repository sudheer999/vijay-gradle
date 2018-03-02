package com.dikshatech.portal.factory;

import java.sql.Connection;

import com.dikshatech.portal.dao.ResourceReqMapHistoryDao;
import com.dikshatech.portal.jdbc.ResourceReqMapHistoryDaoImpl;

public class ResourceReqMapHistoryDaoFactory {
	/**
	 * Method 'create'
	 * 
	 * @return RequestTypeDao
	 */
	public static ResourceReqMapHistoryDao create()
	{
		return new ResourceReqMapHistoryDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return RequestTypeDao
	 */
	public static ResourceReqMapHistoryDao create(Connection conn)
	{
		return new ResourceReqMapHistoryDaoImpl( conn );
	}

}

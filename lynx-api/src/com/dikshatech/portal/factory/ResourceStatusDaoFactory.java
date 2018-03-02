package com.dikshatech.portal.factory;

import java.sql.Connection;

import com.dikshatech.portal.dao.ResourceStatusDao;
import com.dikshatech.portal.jdbc.ResourceStatusDaoImpl;

public class ResourceStatusDaoFactory {
	/**
	 * Method 'create'
	 * 
	 * @return RequestTypeDao
	 */
	public static ResourceStatusDao create()
	{
		return new ResourceStatusDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return RequestTypeDao
	 */
	public static ResourceStatusDao create(Connection conn)
	{
		return new ResourceStatusDaoImpl( conn );
	}

}


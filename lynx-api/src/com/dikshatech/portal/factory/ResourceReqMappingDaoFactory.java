

package com.dikshatech.portal.factory;

import java.sql.Connection;

import com.dikshatech.portal.dao.ResourceReqMappingDao;
import com.dikshatech.portal.jdbc.ResourceReqMappingDaoImpl;

public class ResourceReqMappingDaoFactory {
	/**
	 * Method 'create'
	 * 
	 * @return RequestTypeDao
	 */
	public static ResourceReqMappingDao create()
	{
		return new ResourceReqMappingDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return RequestTypeDao
	 */
	public static ResourceReqMappingDao create(Connection conn)
	{
		return new ResourceReqMappingDaoImpl( conn );
	}

}

package com.dikshatech.portal.factory;

import java.sql.Connection;

import com.dikshatech.portal.dao.ReqResMapDao;
import com.dikshatech.portal.jdbc.ReqResMapDaoImpl;

public class ReqResMapDaoFactory {
	/**
	 * Method 'create'
	 * 
	 * @return RequestTypeDao
	 */
	public static ReqResMapDao create()
	{
		return new ReqResMapDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return RequestTypeDao
	 */
	public static ReqResMapDao create(Connection conn)
	{
		return new ReqResMapDaoImpl( conn );
	}

}

package com.dikshatech.portal.factory;

import java.sql.Connection;

import com.dikshatech.portal.dao.RetentionEmpSerReqMapDao;
import com.dikshatech.portal.jdbc.RetentionEmpSerReqMapDaoImpl;

public class RetentionEmpSerReqMapDaoFactory {

	/**
	 * Method 'create'
	 * 
	 * @return RetentionEmpSerReqMapDao
	 */
	public static RetentionEmpSerReqMapDao create()
	{
		return new RetentionEmpSerReqMapDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return RetentionEmpSerReqMapDao
	 */
	public static RetentionEmpSerReqMapDao create(Connection conn)
	{
		return new RetentionEmpSerReqMapDaoImpl( conn );
	}


}

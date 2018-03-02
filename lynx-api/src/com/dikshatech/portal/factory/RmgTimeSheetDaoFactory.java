package com.dikshatech.portal.factory;

import java.sql.Connection;


import com.dikshatech.portal.dao.RmgTimeSheetDao;
import com.dikshatech.portal.jdbc.RmgTimeSheetDaoImpl;

public class RmgTimeSheetDaoFactory {
	

	/**
	 * Method 'create'
	 * 
	 * @return ProjectDao
	 */
	public static RmgTimeSheetDao create()
	{
		return new RmgTimeSheetDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return ProjectDao
	 */
	public static RmgTimeSheetDao create(Connection conn)
	{
		return new RmgTimeSheetDaoImpl( conn );
	}

}

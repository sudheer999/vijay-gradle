package com.dikshatech.portal.factory;

import java.sql.Connection;

import com.dikshatech.portal.dao.DepPerdiemReqDao;
import com.dikshatech.portal.dao.RmgTimeSheetReqDao;
import com.dikshatech.portal.jdbc.DepPerdiemReqDaoImpl;
import com.dikshatech.portal.jdbc.RmgTimeSheetReqDaoImpl;

public class RmgTimeSheetReqDaoFactory {
	

	/**
	 * Method 'create'
	 * 
	 * @return DepPerdiemReqDao
	 */
	public static RmgTimeSheetReqDao create()
	{
		return new RmgTimeSheetReqDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return DepPerdiemReqDao
	 */
	public static RmgTimeSheetReqDao create(Connection conn)
	{
		return new RmgTimeSheetReqDaoImpl( conn );
	}



}

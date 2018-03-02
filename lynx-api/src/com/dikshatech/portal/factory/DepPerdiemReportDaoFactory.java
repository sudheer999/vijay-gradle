/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
 * For more information please visit http://www.codefutures.com/products/firestorm
 */

package com.dikshatech.portal.factory;

import java.sql.Connection;
import com.dikshatech.portal.dao.*;
import com.dikshatech.portal.jdbc.*;

public class DepPerdiemReportDaoFactory
{
	/**
	 * Method 'create'
	 * 
	 * @return DepPerdiemReportDao
	 */
	public static DepPerdiemReportDao create()
	{
		return new DepPerdiemReportDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return DepPerdiemReportDao
	 */
	public static DepPerdiemReportDao create(Connection conn)
	{
		return new DepPerdiemReportDaoImpl( conn );
	}

}
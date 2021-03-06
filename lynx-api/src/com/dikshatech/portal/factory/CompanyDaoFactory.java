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

public class CompanyDaoFactory
{
	/**
	 * Method 'create'
	 * 
	 * @return CompanyDao
	 */
	public static CompanyDao create()
	{
		return new CompanyDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return CompanyDao
	 */
	public static CompanyDao create(Connection conn)
	{
		return new CompanyDaoImpl( conn );
	}

}

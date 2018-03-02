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

public class AppraisalProjectsDaoFactory
{
	/**
	 * Method 'create'
	 * 
	 * @return AppraisalProjectsDao
	 */
	public static AppraisalProjectsDao create()
	{
		return new AppraisalProjectsDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return AppraisalProjectsDao
	 */
	public static AppraisalProjectsDao create(Connection conn)
	{
		return new AppraisalProjectsDaoImpl( conn );
	}

}

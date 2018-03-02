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

public class LoanRequestDaoFactory
{
	/**
	 * Method 'create'
	 * 
	 * @return LoanRequestDao
	 */
	public static LoanRequestDao create()
	{
		return new LoanRequestDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return LoanRequestDao
	 */
	public static LoanRequestDao create(Connection conn)
	{
		return new LoanRequestDaoImpl( conn );
	}

}

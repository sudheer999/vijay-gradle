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

public class UserTaskFilledDaoFactory
{
	/**
	 * Method 'create'
	 * 
	 * @return UserTaskFilledDao
	 */
	public static UserTaskFilledDao create()
	{
		return new UserTaskFilledDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return UserTaskFilledDao
	 */
	public static UserTaskFilledDao create(Connection conn)
	{
		return new UserTaskFilledDaoImpl( conn );
	}

}

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

public class TbdHistoryDaoFactory
{
	/**
	 * Method 'create'
	 * 
	 * @return TbdHistoryDao
	 */
	public static TbdHistoryDao create()
	{
		return new TbdHistoryDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return TbdHistoryDao
	 */
	public static TbdHistoryDao create(Connection conn)
	{
		return new TbdHistoryDaoImpl( conn );
	}

}
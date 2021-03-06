/*
 * This source file was generated by FireStorm/DAO.
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * For more information please visit http://www.codefutures.com/products/firestorm
 */
package com.dikshatech.portal.factory;

import java.sql.Connection;
import com.dikshatech.portal.dao.ExitFinanceNocDao;
import com.dikshatech.portal.jdbc.ExitFinanceNocDaoImpl;

public class ExitFinanceNocDaoFactory {

	/**
	 * Method 'create'
	 * 
	 * @return ExitFinanceNocDao
	 */
	public static ExitFinanceNocDao create() {
		return new ExitFinanceNocDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return ExitFinanceNocDao
	 */
	public static ExitFinanceNocDao create(Connection conn) {
		return new ExitFinanceNocDaoImpl(conn);
	}
}

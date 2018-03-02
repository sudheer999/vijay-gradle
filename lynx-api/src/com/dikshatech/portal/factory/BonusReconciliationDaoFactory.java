package com.dikshatech.portal.factory;

import java.sql.Connection;

import com.dikshatech.portal.dao.BonusReconciliationDao;
import com.dikshatech.portal.jdbc.BonusReconciliationDaoImpl;



public class BonusReconciliationDaoFactory {
	
	/**
	 * Method 'create'
	 * 
	 * @return BonusReconciliationDao
	 */
	public static BonusReconciliationDao create()
	{
		return new BonusReconciliationDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return BonusReconciliationDao
	 */
	public static BonusReconciliationDao create(Connection conn)
	{
		return new BonusReconciliationDaoImpl( conn );
	}
}

package com.dikshatech.portal.factory;

import java.sql.Connection;

import com.dikshatech.portal.dao.BonusReconciliationHoldDao;
import com.dikshatech.portal.jdbc.BonusReconciliationHoldDaoImpl;


public class BonusReconciliationHoldDaoFactory {
	
	/**
	 * Method 'create'
	 * 
	 * @return BonusReconciliationHoldDao
	 */
	public static BonusReconciliationHoldDao create()
	{
		return new BonusReconciliationHoldDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return BonusReconciliationHoldDao
	 */
	public static BonusReconciliationHoldDao create(Connection conn)
	{
		return new BonusReconciliationHoldDaoImpl( conn );
	}
}

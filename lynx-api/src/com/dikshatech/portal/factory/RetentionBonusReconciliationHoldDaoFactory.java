package com.dikshatech.portal.factory;

import java.sql.Connection;

import com.dikshatech.portal.dao.RetentionBonusReconciliationHoldDao;
import com.dikshatech.portal.jdbc.RetentionBonusReconciliationHoldDaoImpl;

public class RetentionBonusReconciliationHoldDaoFactory {

	
	/**
	 * Method 'create'
	 * 
	 * @return BonusReconciliationHoldDao
	 */
	public static RetentionBonusReconciliationHoldDao create()
	{
		return new RetentionBonusReconciliationHoldDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return BonusReconciliationHoldDao
	 */
	public static RetentionBonusReconciliationHoldDao create(Connection conn)
	{
		return new RetentionBonusReconciliationHoldDaoImpl( conn );
	}

}

package com.dikshatech.portal.factory;

import java.sql.Connection;

import com.dikshatech.portal.dao.RetentionBonusReconciliationDao;
import com.dikshatech.portal.jdbc.RetentionBonusReconciliationDaoImpl;

public class RetentionBonusReconciliationDaoFactory {

	
	/**
	 * Method 'create'
	 * 
	 * @return BonusReconciliationDao
	 */
	public static RetentionBonusReconciliationDao create()
	{
		return new RetentionBonusReconciliationDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return BonusReconciliationDao
	 */
	public static RetentionBonusReconciliationDao create(Connection conn)
	{
		return new RetentionBonusReconciliationDaoImpl( conn );
	}

}

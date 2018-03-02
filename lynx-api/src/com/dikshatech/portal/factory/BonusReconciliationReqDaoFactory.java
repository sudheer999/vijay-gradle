package com.dikshatech.portal.factory;

import java.sql.Connection;

import com.dikshatech.portal.dao.BonusReconciliationReqDao;
import com.dikshatech.portal.jdbc.BonusReconciliationReqDaoImpl;


public class BonusReconciliationReqDaoFactory {
	
	/**
	 * Method 'create'
	 * 
	 * @return BonusReconciliationReqDao
	 */
	public static BonusReconciliationReqDao create()
	{
		return new BonusReconciliationReqDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return BonusReconciliationDao
	 */
	public static BonusReconciliationReqDao create(Connection conn)
	{
		return new BonusReconciliationReqDaoImpl( conn );
	}
}

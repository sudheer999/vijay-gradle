package com.dikshatech.portal.factory;

import java.sql.Connection;

import com.dikshatech.portal.dao.RetentionBonusReconciliationReqDao;
import com.dikshatech.portal.jdbc.RetentionBonusReconciliationReqDaoImpl;

public class RetentionBonusReconciliationReqDaoFactory {

	
	/**
	 * Method 'create'
	 * 
	 * @return BonusReconciliationReqDao
	 */
	public static RetentionBonusReconciliationReqDao create()
	{
		return new RetentionBonusReconciliationReqDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return BonusReconciliationDao
	 */
	public static RetentionBonusReconciliationReqDao create(Connection conn)
	{
		return new RetentionBonusReconciliationReqDaoImpl( conn );
	}

}

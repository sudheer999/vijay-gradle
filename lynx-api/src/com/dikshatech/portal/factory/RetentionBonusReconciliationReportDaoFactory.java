package com.dikshatech.portal.factory;

import java.sql.Connection;

import com.dikshatech.portal.dao.RetentionBonusReconciliationReportDao;
import com.dikshatech.portal.jdbc.RetentionBonusReconciliationReportDaoImpl;

public class RetentionBonusReconciliationReportDaoFactory {

	
	/**
	 * Method 'create'
	 * 
	 * @return BonusReconciliationReqDao
	 */
	public static RetentionBonusReconciliationReportDao create()
	{
		return new RetentionBonusReconciliationReportDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return BonusReconciliationDao
	 */
	public static RetentionBonusReconciliationReportDao create(Connection conn)
	{
		return new RetentionBonusReconciliationReportDaoImpl( conn );
	}

}

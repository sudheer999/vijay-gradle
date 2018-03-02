package com.dikshatech.portal.factory;

import java.sql.Connection;

import com.dikshatech.portal.dao.RetentionBonusRecReportHistoryDao;
import com.dikshatech.portal.jdbc.RetentionBonusRecReportHistoryDaoImpl;

public class RetentionBonusRecReportHistoryDaoFactory {

	
	/**
	 * Method 'create'
	 * 
	 * @return BonusReconciliationReqDao
	 */
	public static RetentionBonusRecReportHistoryDao create()
	{
		return new RetentionBonusRecReportHistoryDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return BonusReconciliationDao
	 */
	public static RetentionBonusRecReportHistoryDao create(Connection conn)
	{
		return new RetentionBonusRecReportHistoryDaoImpl( conn );
	}
	

}

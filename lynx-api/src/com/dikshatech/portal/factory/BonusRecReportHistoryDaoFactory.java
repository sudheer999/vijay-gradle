package com.dikshatech.portal.factory;

import java.sql.Connection;

import com.dikshatech.portal.dao.BonusRecReportHistoryDao;
import com.dikshatech.portal.jdbc.BonusRecReportHistoryDaoImpl;


public class BonusRecReportHistoryDaoFactory {
	
	/**
	 * Method 'create'
	 * 
	 * @return BonusReconciliationReqDao
	 */
	public static BonusRecReportHistoryDao create()
	{
		return new BonusRecReportHistoryDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return BonusReconciliationDao
	 */
	public static BonusRecReportHistoryDao create(Connection conn)
	{
		return new BonusRecReportHistoryDaoImpl( conn );
	}
	
}

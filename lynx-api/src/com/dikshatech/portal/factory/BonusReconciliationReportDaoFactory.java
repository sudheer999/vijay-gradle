package com.dikshatech.portal.factory;

import java.sql.Connection;

import com.dikshatech.portal.dao.BonusReconciliationReportDao;
import com.dikshatech.portal.jdbc.BonusReconciliationReportDaoImpl;



public class BonusReconciliationReportDaoFactory {
	
	/**
	 * Method 'create'
	 * 
	 * @return BonusReconciliationReqDao
	 */
	public static BonusReconciliationReportDao create()
	{
		return new BonusReconciliationReportDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return BonusReconciliationDao
	 */
	public static BonusReconciliationReportDao create(Connection conn)
	{
		return new BonusReconciliationReportDaoImpl( conn );
	}
}

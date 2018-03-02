package com.dikshatech.portal.factory;

import java.sql.Connection;

import com.dikshatech.portal.dao.InvoiceReconciliationDao;
import com.dikshatech.portal.jdbc.InvoiceReconciliationDaoImpl;


public class InvoiceReconciliationDaoFactory {

	public static InvoiceReconciliationDao create()
	{
		
		
		return new InvoiceReconciliationDaoImpl();
		//return new InvoiceReconciliationDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return DepPerdiemReportDao
	 */
	public static InvoiceReconciliationDao create(Connection conn)
	{
		
		return new InvoiceReconciliationDaoImpl(conn);
		//return new DepPerdiemDaoImpl( conn );
	}

	
}

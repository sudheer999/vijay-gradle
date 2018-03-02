package com.dikshatech.portal.factory;

import com.dikshatech.portal.dao.InvoiceReconciliationReportDao;
import com.dikshatech.portal.jdbc.InvoiceReconciliationReportDaoImpl;
import com.mysql.jdbc.Connection;

public class InvoiceReconciliationReportDaoFactory {
	
	public static InvoiceReconciliationReportDao create()
	{
		
		
		return new InvoiceReconciliationReportDaoImpl();
		//return new InvoiceReconciliationDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return DepPerdiemReportDao
	 */
	public static InvoiceReconciliationReportDao create(Connection conn)
	{
		
		return new InvoiceReconciliationReportDaoImpl(conn);
		//return new DepPerdiemDaoImpl( conn );
	}
	
	

}



/*package com.dikshatech.portal.factory;

import java.sql.Connection;

import com.dikshatech.portal.dao.InvoiceReconciliationDao;
import com.dikshatech.portal.jdbc.InvoiceReconciliationDaoImpl;
*/




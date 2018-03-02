package com.dikshatech.portal.dao;

import com.dikshatech.portal.dto.InvoiceReconciliation;
import com.dikshatech.portal.dto.InvoiceReconciliationPk;
import com.dikshatech.portal.dto.Invoicedto;
import com.dikshatech.portal.dto.RmgTimeSheetReco;
import com.dikshatech.portal.exceptions.InvoiceReconciliationDaoException;

public interface InvoiceReconciliationReportDao {
	public InvoiceReconciliationPk insert(Invoicedto dto) throws InvoiceReconciliationDaoException;
	
	
	public InvoiceReconciliation[] findAll() throws InvoiceReconciliationDaoException ;
	public RmgTimeSheetReco[] findByTimeSheetId(int id) throws InvoiceReconciliationDaoException ;
	
}

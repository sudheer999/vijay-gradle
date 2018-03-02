package com.dikshatech.portal.dao;

import java.util.ArrayList;
import java.util.List;

import com.dikshatech.portal.dto.DepPerdiemComponents;
import com.dikshatech.portal.dto.DepPerdiemComponentsPk;
import com.dikshatech.portal.dto.InvoiceReconciliationPk;
import com.dikshatech.portal.dto.Invoicedto;
import com.dikshatech.portal.exceptions.DepPerdiemComponentsDaoException;
import com.dikshatech.portal.exceptions.InvoiceReconciliationDaoException;


public interface InvoiceReconciliationDao {

	//public ProjLocationsPk insert(ProjLocations dto) throws ProjLocationsDaoException;
	
	public InvoiceReconciliationPk insert(Invoicedto dto) throws InvoiceReconciliationDaoException;
	public void update(Invoicedto dto) throws InvoiceReconciliationDaoException;
	
	public  Invoicedto[] findWhereInvoiceReportId(int id) throws InvoiceReconciliationDaoException ;
	public  Invoicedto[] findWhereInvoiceId(int id,String flag) throws InvoiceReconciliationDaoException ;
	public  Invoicedto[] findAll() throws InvoiceReconciliationDaoException ;
	
	public List<String[]> findWhereInvoiceidForReport(int id) throws InvoiceReconciliationDaoException;
	public List<String[]> findWhereFinalReconciliationReport(int id) throws InvoiceReconciliationDaoException;
	public InvoiceReconciliationPk saveForInvoiceReconciliation(Invoicedto dto) throws InvoiceReconciliationDaoException;
	
	public  Invoicedto[] findAllReport(int ID) throws InvoiceReconciliationDaoException;
	
	
	
}


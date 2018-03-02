/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
 * For more information please visit http://www.codefutures.com/products/firestorm
 */

package com.dikshatech.portal.dao;

import com.dikshatech.portal.dto.SalaryRecReportHistory;
import com.dikshatech.portal.dto.SalaryRecReportHistoryPk;
import com.dikshatech.portal.exceptions.SalaryRecReportHistoryDaoException;

public interface SalaryRecReportHistoryDao
{
	/** 
	 * Inserts a new row in the salary_rec_report_history table.
	 */
	public SalaryRecReportHistoryPk insert(SalaryRecReportHistory dto) throws SalaryRecReportHistoryDaoException;

	/** 
	 * Updates a single row in the salary_rec_report_history table.
	 */
	public void update(SalaryRecReportHistoryPk pk, SalaryRecReportHistory dto) throws SalaryRecReportHistoryDaoException;

	/** 
	 * Deletes a single row in the salary_rec_report_history table.
	 */
	public void delete(SalaryRecReportHistoryPk pk) throws SalaryRecReportHistoryDaoException;

	/** 
	 * Returns the rows from the salary_rec_report_history table that matches the specified primary-key value.
	 */
	public SalaryRecReportHistory findByPrimaryKey(SalaryRecReportHistoryPk pk) throws SalaryRecReportHistoryDaoException;

	/** 
	 * Returns all rows from the salary_rec_report_history table that match the criteria 'ID = :id'.
	 */
	public SalaryRecReportHistory findByPrimaryKey(long id) throws SalaryRecReportHistoryDaoException;

	/** 
	 * Returns all rows from the salary_rec_report_history table that match the criteria 'SRR_ID = :srrId'.
	 */
	public SalaryRecReportHistory[] findBySalaryReconciliationReport(long srrId) throws SalaryRecReportHistoryDaoException;

	/** 
	 * Returns all rows from the salary_rec_report_history table that match the criteria 'SRR_ID = :srrId'.
	 */
	public SalaryRecReportHistory[] findWhereSrrIdEquals(long srrId) throws SalaryRecReportHistoryDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the salary_rec_report_history table that match the specified arbitrary SQL statement
	 */
	public SalaryRecReportHistory[] findByDynamicSelect(String sql, Object[] sqlParams) throws SalaryRecReportHistoryDaoException;

	/** 
	 * Returns all rows from the salary_rec_report_history table that match the specified arbitrary SQL statement
	 */
	public SalaryRecReportHistory[] findByDynamicWhere(String sql, Object[] sqlParams) throws SalaryRecReportHistoryDaoException;

}
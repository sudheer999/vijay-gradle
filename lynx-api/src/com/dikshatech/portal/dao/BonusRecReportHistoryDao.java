package com.dikshatech.portal.dao;

import com.dikshatech.portal.dto.BonusRecReportHistory;
import com.dikshatech.portal.dto.BonusRecReportHistoryPk;
import com.dikshatech.portal.exceptions.BonusRecReportHistoryDaoException;


public interface BonusRecReportHistoryDao {
	
	/** 
	 * Inserts a new row in the dep_perdiem_report_history table.
	 */
	public BonusRecReportHistoryPk insert(BonusRecReportHistory dto) throws BonusRecReportHistoryDaoException;

	/** 
	 * Updates a single row in the dep_perdiem_report_history table.
	 */
	public void update(BonusRecReportHistoryPk pk, BonusRecReportHistory dto) throws BonusRecReportHistoryDaoException;

	/** 
	 * Deletes a single row in the dep_perdiem_report_history table.
	 */
	public void delete(BonusRecReportHistoryPk pk) throws BonusRecReportHistoryDaoException;

	/** 
	 * Returns the rows from the dep_perdiem_report_history table that matches the specified primary-key value.
	 */
	public BonusRecReportHistory findByPrimaryKey(BonusRecReportHistoryPk pk) throws BonusRecReportHistoryDaoException;

	/** 
	 * Returns all rows from the dep_perdiem_report_history table that match the criteria 'ID = :id'.
	 */
	public BonusRecReportHistory findByPrimaryKey(int id) throws BonusRecReportHistoryDaoException;

	/** 
	 * Returns all rows from the dep_perdiem_report_history table that match the criteria ''.
	 */
	public BonusRecReportHistory[] findAll() throws BonusRecReportHistoryDaoException;

	/** 
	 * Returns all rows from the dep_perdiem_report_history table that match the criteria 'REP_ID = :repId'.
	 */
	public BonusRecReportHistory[] findByBonusReport(int repId) throws BonusRecReportHistoryDaoException;

	/** 
	 * Returns all rows from the dep_perdiem_report_history table that match the criteria 'REP_ID = :repId'.
	 */
	public BonusRecReportHistory[] findWhereRepIdEquals(int repId) throws BonusRecReportHistoryDaoException;

	/** 
	 * Returns all rows from the dep_perdiem_report_history table that match the criteria 'IS_DELETED = :isDeleted'.
	 */
	public BonusRecReportHistory[] findWhereIsDeletedEquals(short isDeleted) throws BonusRecReportHistoryDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the dep_perdiem_report_history table that match the specified arbitrary SQL statement
	 */
	public BonusRecReportHistory[] findByDynamicSelect(String sql, Object[] sqlParams) throws BonusRecReportHistoryDaoException;

	/** 
	 * Returns all rows from the dep_perdiem_report_history table that match the specified arbitrary SQL statement
	 */
	public BonusRecReportHistory[] findByDynamicWhere(String sql, Object[] sqlParams) throws BonusRecReportHistoryDaoException;
}

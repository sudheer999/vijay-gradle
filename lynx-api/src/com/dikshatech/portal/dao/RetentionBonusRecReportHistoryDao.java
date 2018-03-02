package com.dikshatech.portal.dao;

import com.dikshatech.portal.dto.RetentionBonusRecReportHistory;
import com.dikshatech.portal.dto.RetentionBonusRecReportHistoryPk;
import com.dikshatech.portal.exceptions.RetentionBonusRecReportHistoryDaoException;

public interface RetentionBonusRecReportHistoryDao {

	
	/** 
	 * Inserts a new row in the RETENTION_DEP_PERDIEM_REPORT_HISTORY table.
	 */
	public RetentionBonusRecReportHistoryPk insert(RetentionBonusRecReportHistory dto) throws RetentionBonusRecReportHistoryDaoException;

	/** 
	 * Updates a single row in the RETENTION_DEP_PERDIEM_REPORT_HISTORY table.
	 */
	public void update(RetentionBonusRecReportHistoryPk pk, RetentionBonusRecReportHistory dto) throws RetentionBonusRecReportHistoryDaoException;

	/** 
	 * Deletes a single row in the RETENTION_DEP_PERDIEM_REPORT_HISTORY table.
	 */
	public void delete(RetentionBonusRecReportHistoryPk pk) throws RetentionBonusRecReportHistoryDaoException;

	/** 
	 * Returns the rows from the RETENTION_DEP_PERDIEM_REPORT_HISTORY table that matches the specified primary-key value.
	 */
	public RetentionBonusRecReportHistory findByPrimaryKey(RetentionBonusRecReportHistoryPk pk) throws RetentionBonusRecReportHistoryDaoException;

	/** 
	 * Returns all rows from the RETENTION_DEP_PERDIEM_REPORT_HISTORY table that match the criteria 'ID = :id'.
	 */
	public RetentionBonusRecReportHistory findByPrimaryKey(int id) throws RetentionBonusRecReportHistoryDaoException;

	/** 
	 * Returns all rows from the RETENTION_DEP_PERDIEM_REPORT_HISTORY table that match the criteria ''.
	 */
	public RetentionBonusRecReportHistory[] findAll() throws RetentionBonusRecReportHistoryDaoException;

	/** 
	 * Returns all rows from the RETENTION_DEP_PERDIEM_REPORT_HISTORY table that match the criteria 'REP_ID = :repId'.
	 */
	public RetentionBonusRecReportHistory[] findByBonusReport(int repId) throws RetentionBonusRecReportHistoryDaoException;

	/** 
	 * Returns all rows from the RETENTION_DEP_PERDIEM_REPORT_HISTORY table that match the criteria 'REP_ID = :repId'.
	 */
	public RetentionBonusRecReportHistory[] findWhereRepIdEquals(int repId) throws RetentionBonusRecReportHistoryDaoException;

	/** 
	 * Returns all rows from the RETENTION_DEP_PERDIEM_REPORT_HISTORY table that match the criteria 'IS_DELETED = :isDeleted'.
	 */
	public RetentionBonusRecReportHistory[] findWhereIsDeletedEquals(short isDeleted) throws RetentionBonusRecReportHistoryDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the RETENTION_DEP_PERDIEM_REPORT_HISTORY table that match the specified arbitrary SQL statement
	 */
	public RetentionBonusRecReportHistory[] findByDynamicSelect(String sql, Object[] sqlParams) throws RetentionBonusRecReportHistoryDaoException;

	/** 
	 * Returns all rows from the RETENTION_DEP_PERDIEM_REPORT_HISTORY table that match the specified arbitrary SQL statement
	 */
	public RetentionBonusRecReportHistory[] findByDynamicWhere(String sql, Object[] sqlParams) throws RetentionBonusRecReportHistoryDaoException;

}

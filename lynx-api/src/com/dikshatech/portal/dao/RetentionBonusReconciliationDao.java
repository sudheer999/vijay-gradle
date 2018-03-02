package com.dikshatech.portal.dao;

import java.util.Date;


import com.dikshatech.portal.dto.RetentionBonusReconciliation;
import com.dikshatech.portal.dto.RetentionBonusReconciliationPk;
import com.dikshatech.portal.exceptions.RetentionBonusReconciliationDaoException;

public interface RetentionBonusReconciliationDao {

	
	/** 
	 * Inserts a new row in the DEP_PERDIEM table.
	 */
	public RetentionBonusReconciliationPk insert(RetentionBonusReconciliation dto) throws RetentionBonusReconciliationDaoException;

	/** 
	 * Updates a single row in the DEP_PERDIEM table.
	 */
	public void update(RetentionBonusReconciliationPk pk, RetentionBonusReconciliation dto) throws RetentionBonusReconciliationDaoException;

	/** 
	 * Deletes a single row in the DEP_PERDIEM table.
	 */
	public void delete(RetentionBonusReconciliation pk) throws RetentionBonusReconciliationDaoException;

	/** 
	 * Returns the rows from the DEP_PERDIEM table that matches the specified primary-key value.
	 */
	public RetentionBonusReconciliation findByPrimaryKey(RetentionBonusReconciliationPk pk) throws RetentionBonusReconciliationDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'ID = :id'.
	 */
	public RetentionBonusReconciliation findByPrimaryKey(int id) throws RetentionBonusReconciliationDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria ''.
	 */
	public RetentionBonusReconciliation[] findAll() throws RetentionBonusReconciliationDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'ID = :id'.
	 */
	public RetentionBonusReconciliation[] findWhereIdEquals(int id) throws RetentionBonusReconciliationDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'ESR_MAP_ID = :esrMapId'.
	 */
	public RetentionBonusReconciliation findWhereEsrMapIdEquals(int esrMapId) throws RetentionBonusReconciliationDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'TERM = :term'.
	 */
	public RetentionBonusReconciliation[] findWhereTermEquals(String term) throws RetentionBonusReconciliationDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'MONTH = :month'.
	 */
	public RetentionBonusReconciliation[] findWhereMonthEquals(String month) throws RetentionBonusReconciliationDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'YEAR = :year'.
	 */
	public RetentionBonusReconciliation[] findWhereYearEquals(int year) throws RetentionBonusReconciliationDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'SUBMITTED_ON = :submittedOn'.
	 */
	public RetentionBonusReconciliation[] findWhereSubmittedOnEquals(Date submittedOn) throws RetentionBonusReconciliationDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'COMPLETED_ON = :completedOn'.
	 */
	public RetentionBonusReconciliation[] findWhereCompletedOnEquals(Date completedOn) throws RetentionBonusReconciliationDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'STATUS = :status'.
	 */
	public RetentionBonusReconciliation[] findWhereStatusEquals(String status) throws RetentionBonusReconciliationDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'HTML_STATUS = :htmlStatus'.
	 */
	public RetentionBonusReconciliation[] findWhereHtmlStatusEquals(String htmlStatus) throws RetentionBonusReconciliationDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the specified arbitrary SQL statement
	 */
	public RetentionBonusReconciliation[] findByDynamicSelect(String sql, Object[] sqlParams) throws RetentionBonusReconciliationDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the specified arbitrary SQL statement
	 */
	public RetentionBonusReconciliation[] findByDynamicWhere(String sql, Object[] sqlParams) throws RetentionBonusReconciliationDaoException;

	public int findMaxId() throws RetentionBonusReconciliationDaoException;

}

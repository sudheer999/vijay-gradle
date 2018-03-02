package com.dikshatech.portal.dao;

import java.util.Date;

import com.dikshatech.portal.dto.BonusReconciliation;
import com.dikshatech.portal.dto.BonusReconciliationPk;
import com.dikshatech.portal.exceptions.BonusReconciliationDaoException;


public interface BonusReconciliationDao {
	
	/** 
	 * Inserts a new row in the DEP_PERDIEM table.
	 */
	public BonusReconciliationPk insert(BonusReconciliation dto) throws BonusReconciliationDaoException;

	/** 
	 * Updates a single row in the DEP_PERDIEM table.
	 */
	public void update(BonusReconciliationPk pk, BonusReconciliation dto) throws BonusReconciliationDaoException;

	/** 
	 * Deletes a single row in the DEP_PERDIEM table.
	 */
	public void delete(BonusReconciliation pk) throws BonusReconciliationDaoException;

	/** 
	 * Returns the rows from the DEP_PERDIEM table that matches the specified primary-key value.
	 */
	public BonusReconciliation findByPrimaryKey(BonusReconciliationPk pk) throws BonusReconciliationDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'ID = :id'.
	 */
	public BonusReconciliation findByPrimaryKey(int id) throws BonusReconciliationDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria ''.
	 */
	public BonusReconciliation[] findAll() throws BonusReconciliationDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'ID = :id'.
	 */
	public BonusReconciliation[] findWhereIdEquals(int id) throws BonusReconciliationDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'ESR_MAP_ID = :esrMapId'.
	 */
	public BonusReconciliation findWhereEsrMapIdEquals(int esrMapId) throws BonusReconciliationDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'TERM = :term'.
	 */
	public BonusReconciliation[] findWhereTermEquals(String term) throws BonusReconciliationDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'MONTH = :month'.
	 */
	public BonusReconciliation[] findWhereMonthEquals(String month) throws BonusReconciliationDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'YEAR = :year'.
	 */
	public BonusReconciliation[] findWhereYearEquals(int year) throws BonusReconciliationDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'SUBMITTED_ON = :submittedOn'.
	 */
	public BonusReconciliation[] findWhereSubmittedOnEquals(Date submittedOn) throws BonusReconciliationDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'COMPLETED_ON = :completedOn'.
	 */
	public BonusReconciliation[] findWhereCompletedOnEquals(Date completedOn) throws BonusReconciliationDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'STATUS = :status'.
	 */
	public BonusReconciliation[] findWhereStatusEquals(String status) throws BonusReconciliationDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the criteria 'HTML_STATUS = :htmlStatus'.
	 */
	public BonusReconciliation[] findWhereHtmlStatusEquals(String htmlStatus) throws BonusReconciliationDaoException;

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
	public BonusReconciliation[] findByDynamicSelect(String sql, Object[] sqlParams) throws BonusReconciliationDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM table that match the specified arbitrary SQL statement
	 */
	public BonusReconciliation[] findByDynamicWhere(String sql, Object[] sqlParams) throws BonusReconciliationDaoException;

	public int findMaxId() throws BonusReconciliationDaoException;
}

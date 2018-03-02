package com.dikshatech.portal.dao;

import com.dikshatech.portal.dto.RetentionBonusReconciliationHold;
import com.dikshatech.portal.dto.RetentionBonusReconciliationHoldPk;
import com.dikshatech.portal.exceptions.RetentionBonusReconciliationHoldDaoException;

public interface RetentionBonusReconciliationHoldDao {

	
	
	/** 
	 * Inserts a new row in the RETENTION_DEP_PERDIEM_HOLD table.
	 */
	public RetentionBonusReconciliationHoldPk insert(RetentionBonusReconciliationHold dto) throws RetentionBonusReconciliationHoldDaoException;

	/** 
	 * Updates a single row in the RETENTION_DEP_PERDIEM_HOLD table.
	 */
	public void update(RetentionBonusReconciliationHoldPk pk, RetentionBonusReconciliationHold dto) throws RetentionBonusReconciliationHoldDaoException;

	/** 
	 * Deletes a single row in the RETENTION_DEP_PERDIEM_HOLD table.
	 */
	public void delete(RetentionBonusReconciliationHoldPk pk) throws RetentionBonusReconciliationHoldDaoException;

	/** 
	 * Returns the rows from the RETENTION_DEP_PERDIEM_HOLD table that matches the specified primary-key value.
	 */
	public RetentionBonusReconciliationHold findByPrimaryKey(RetentionBonusReconciliationHoldPk pk) throws RetentionBonusReconciliationHoldDaoException;

	/** 
	 * Returns all rows from the RETENTION_DEP_PERDIEM_HOLD table that match the criteria 'ID = :id'.
	 */
	public RetentionBonusReconciliationHold findByPrimaryKey(int id) throws RetentionBonusReconciliationHoldDaoException;

	/** 
	 * Returns all rows from the RETENTION_DEP_PERDIEM_HOLD table that match the criteria ''.
	 */
	public RetentionBonusReconciliationHold[] findAll() throws RetentionBonusReconciliationHoldDaoException;

	/** 
	 * Returns all rows from the dep_perdiem_hold table that match the criteria 'REP_ID = :repId'.
	 */
	public RetentionBonusReconciliationHold[] findWhereRepIdEquals(Integer repId) throws RetentionBonusReconciliationHoldDaoException;

	/** 
	 * Returns all rows from the RETENTION_DEP_PERDIEM_HOLD table that match the criteria 'USER_ID = :userId'.
	 */
	public RetentionBonusReconciliationHold[] findWhereUserIdEquals(Integer userId) throws RetentionBonusReconciliationHoldDaoException;

	/** 
	 * Returns all rows from the RETENTION_DEP_PERDIEM_HOLD table that match the criteria 'ESC_FROM = :escFrom'.
	 */
	public RetentionBonusReconciliationHold[] findWhereEscFromEquals(Integer escFrom) throws RetentionBonusReconciliationHoldDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the RETENTION_DEP_PERDIEM_HOLD table that match the specified arbitrary SQL statement
	 */
	public RetentionBonusReconciliationHold[] findByDynamicSelect(String sql, Object[] sqlParams) throws RetentionBonusReconciliationHoldDaoException;

	/** 
	 * Returns all rows from the RETENTION_DEP_PERDIEM_HOLD table that match the specified arbitrary SQL statement
	 */
	public RetentionBonusReconciliationHold[] findByDynamicWhere(String sql, Object[] sqlParams) throws RetentionBonusReconciliationHoldDaoException;


}

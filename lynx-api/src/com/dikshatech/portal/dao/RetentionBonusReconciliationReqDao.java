package com.dikshatech.portal.dao;

import java.util.Date;

import com.dikshatech.portal.dto.RetentionBonusReconciliationReq;
import com.dikshatech.portal.dto.RetentionBonusReconciliationReqPk;
import com.dikshatech.portal.exceptions.RetentionBonusReconciliationReqDaoException;

public interface RetentionBonusReconciliationReqDao {

	
	/** 
	 * Inserts a new row in the RETENTION_BONUS_RECONCILIATION_REQ table.
	 */
	public RetentionBonusReconciliationReqPk insert(RetentionBonusReconciliationReq dto) throws RetentionBonusReconciliationReqDaoException;

	/** 
	 * Updates a single row in the RETENTION_BONUS_RECONCILIATION_REQ table.
	 */
	public void update(RetentionBonusReconciliationReqPk pk, RetentionBonusReconciliationReq dto) throws RetentionBonusReconciliationReqDaoException;

	/** 
	 * Deletes a single row in the RETENTION_BONUS_RECONCILIATION_REQ table.
	 */
	public void delete(RetentionBonusReconciliationReqPk pk) throws RetentionBonusReconciliationReqDaoException;

	/** 
	 * Returns the rows from the RETENTION_BONUS_RECONCILIATION_REQ table that matches the specified primary-key value.
	 */
	public RetentionBonusReconciliationReq findByPrimaryKey(RetentionBonusReconciliationReqPk pk) throws RetentionBonusReconciliationReqDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_REQ table that match the criteria 'ID = :id'.
	 */
	public RetentionBonusReconciliationReq findByPrimaryKey(int id) throws RetentionBonusReconciliationReqDaoException;

	/** 
	 * Returns all rows from the RETENTION_BONUS_RECONCILIATION_REQ table that match the criteria ''.
	 */
	public RetentionBonusReconciliationReq[] findAll() throws RetentionBonusReconciliationReqDaoException;

	/** 
	 * Returns all rows from the RETENTION_BONUS_RECONCILIATION_REQ table that match the criteria 'ID = :id'.
	 */
	public RetentionBonusReconciliationReq[] findWhereIdEquals(int id) throws RetentionBonusReconciliationReqDaoException;

	/** 
	 * Returns all rows from the RETENTION_BONUS_RECONCILIATION_REQ table that match the criteria 'BR_ID = :bonusId'.
	 */
	public RetentionBonusReconciliationReq[] findWhereBonusIdEquals(int bonusId) throws RetentionBonusReconciliationReqDaoException;

	/** 
	 * Returns all rows from the RETENTION_BONUS_RECONCILIATION_REQ table that match the criteria 'SEQ_ID = :seqId'.
	 */
	public RetentionBonusReconciliationReq[] findWhereSeqIdEquals(int seqId) throws RetentionBonusReconciliationReqDaoException;

	/** 
	 * Returns all rows from the RETENTION_BONUS_RECONCILIATION_REQ table that match the criteria 'ASSIGNED_TO = :assignedTo'.
	 */
	public RetentionBonusReconciliationReq[] findWhereAssignedToEquals(int assignedTo) throws RetentionBonusReconciliationReqDaoException;

	/** 
	 * Returns all rows from the RETENTION_BONUS_RECONCILIATION_REQ table that match the criteria 'RECEIVED_ON = :receivedOn'.
	 */
	public RetentionBonusReconciliationReq[] findWhereReceivedOnEquals(Date receivedOn) throws RetentionBonusReconciliationReqDaoException;

	/** 
	 * Returns all rows from the RETENTION_BONUS_RECONCILIATION_REQ table that match the criteria 'SUBMITTED_ON = :submittedOn'.
	 */
	public RetentionBonusReconciliationReq[] findWhereSubmittedOnEquals(Date submittedOn) throws RetentionBonusReconciliationReqDaoException;

	/** 
	 * Returns all rows from the RETENTION_BONUS_RECONCILIATION_REQ table that match the criteria 'IS_ESCALATED = :isEscalated'.
	 */
	public RetentionBonusReconciliationReq[] findWhereIsEscalatedEquals(int isEscalated) throws RetentionBonusReconciliationReqDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the RETENTION_BONUS_RECONCILIATION_REQ table that match the specified arbitrary SQL statement
	 */
	public RetentionBonusReconciliationReq[] findByDynamicSelect(String sql, Object[] sqlParams) throws RetentionBonusReconciliationReqDaoException;

	/** 
	 * Returns all rows from the RETENTION_BONUS_RECONCILIATION_REQ table that match the specified arbitrary SQL statement
	 */
	public RetentionBonusReconciliationReq[] findByDynamicWhere(String sql, Object[] sqlParams) throws RetentionBonusReconciliationReqDaoException;


}

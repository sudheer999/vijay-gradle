package com.dikshatech.portal.dao;

import java.util.Date;

import com.dikshatech.portal.dto.BonusReconciliationReq;
import com.dikshatech.portal.dto.BonusReconciliationReqPk;
import com.dikshatech.portal.exceptions.BonusReconciliationReqDaoException;


public interface BonusReconciliationReqDao {
	
	/** 
	 * Inserts a new row in the BONUS_RECONCILIATION_REQ table.
	 */
	public BonusReconciliationReqPk insert(BonusReconciliationReq dto) throws BonusReconciliationReqDaoException;

	/** 
	 * Updates a single row in the BONUS_RECONCILIATION_REQ table.
	 */
	public void update(BonusReconciliationReqPk pk, BonusReconciliationReq dto) throws BonusReconciliationReqDaoException;

	/** 
	 * Deletes a single row in the BONUS_RECONCILIATION_REQ table.
	 */
	public void delete(BonusReconciliationReqPk pk) throws BonusReconciliationReqDaoException;

	/** 
	 * Returns the rows from the BONUS_RECONCILIATION_REQ table that matches the specified primary-key value.
	 */
	public BonusReconciliationReq findByPrimaryKey(BonusReconciliationReqPk pk) throws BonusReconciliationReqDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_REQ table that match the criteria 'ID = :id'.
	 */
	public BonusReconciliationReq findByPrimaryKey(int id) throws BonusReconciliationReqDaoException;

	/** 
	 * Returns all rows from the BONUS_RECONCILIATION_REQ table that match the criteria ''.
	 */
	public BonusReconciliationReq[] findAll() throws BonusReconciliationReqDaoException;

	/** 
	 * Returns all rows from the BONUS_RECONCILIATION_REQ table that match the criteria 'ID = :id'.
	 */
	public BonusReconciliationReq[] findWhereIdEquals(int id) throws BonusReconciliationReqDaoException;

	/** 
	 * Returns all rows from the BONUS_RECONCILIATION_REQ table that match the criteria 'BR_ID = :bonusId'.
	 */
	public BonusReconciliationReq[] findWhereBonusIdEquals(int bonusId) throws BonusReconciliationReqDaoException;

	/** 
	 * Returns all rows from the BONUS_RECONCILIATION_REQ table that match the criteria 'SEQ_ID = :seqId'.
	 */
	public BonusReconciliationReq[] findWhereSeqIdEquals(int seqId) throws BonusReconciliationReqDaoException;

	/** 
	 * Returns all rows from the BONUS_RECONCILIATION_REQ table that match the criteria 'ASSIGNED_TO = :assignedTo'.
	 */
	public BonusReconciliationReq[] findWhereAssignedToEquals(int assignedTo) throws BonusReconciliationReqDaoException;

	/** 
	 * Returns all rows from the BONUS_RECONCILIATION_REQ table that match the criteria 'RECEIVED_ON = :receivedOn'.
	 */
	public BonusReconciliationReq[] findWhereReceivedOnEquals(Date receivedOn) throws BonusReconciliationReqDaoException;

	/** 
	 * Returns all rows from the BONUS_RECONCILIATION_REQ table that match the criteria 'SUBMITTED_ON = :submittedOn'.
	 */
	public BonusReconciliationReq[] findWhereSubmittedOnEquals(Date submittedOn) throws BonusReconciliationReqDaoException;

	/** 
	 * Returns all rows from the BONUS_RECONCILIATION_REQ table that match the criteria 'IS_ESCALATED = :isEscalated'.
	 */
	public BonusReconciliationReq[] findWhereIsEscalatedEquals(int isEscalated) throws BonusReconciliationReqDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the BONUS_RECONCILIATION_REQ table that match the specified arbitrary SQL statement
	 */
	public BonusReconciliationReq[] findByDynamicSelect(String sql, Object[] sqlParams) throws BonusReconciliationReqDaoException;

	/** 
	 * Returns all rows from the BONUS_RECONCILIATION_REQ table that match the specified arbitrary SQL statement
	 */
	public BonusReconciliationReq[] findByDynamicWhere(String sql, Object[] sqlParams) throws BonusReconciliationReqDaoException;

}

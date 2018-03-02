package com.dikshatech.portal.dao;

import java.util.Date;

import com.dikshatech.portal.dto.RmgTimeSheet;
import com.dikshatech.portal.dto.RmgTimeSheetPk;
import com.dikshatech.portal.dto.RmgTimeSheetReq;
import com.dikshatech.portal.dto.RmgTimeSheetReqPk;
import com.dikshatech.portal.exceptions.RmgTimeSheetReqDaoException;

public interface RmgTimeSheetReqDao {
	

	/** 
	 * Inserts a new row in the DEP_PERDIEM_REQ table.
	 */
	public RmgTimeSheetReqPk insert(RmgTimeSheetReq dto) throws RmgTimeSheetReqDaoException;

	/** 
	 * Updates a single row in the DEP_PERDIEM_REQ table.
	 */
	public void update(RmgTimeSheetReqPk pk, RmgTimeSheetReq dto) throws RmgTimeSheetReqDaoException;

	/** 
	 * Deletes a single row in the DEP_PERDIEM_REQ table.
	 */
	public void delete(RmgTimeSheetReqPk pk) throws RmgTimeSheetReqDaoException;

	/** 
	 * Returns the rows from the DEP_PERDIEM_REQ table that matches the specified primary-key value.
	 */
	public RmgTimeSheetReq findByPrimaryKey(RmgTimeSheetReqPk pk) throws RmgTimeSheetReqDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_REQ table that match the criteria 'ID = :id'.
	 */
	public RmgTimeSheetReq findByPrimaryKey(int id) throws RmgTimeSheetReqDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_REQ table that match the criteria ''.
	 */
	public RmgTimeSheetReq[] findAll() throws RmgTimeSheetReqDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_REQ table that match the criteria 'ID = :id'.
	 */
	public RmgTimeSheetReq[] findWhereIdEquals(int id) throws RmgTimeSheetReqDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_REQ table that match the criteria 'DEP_ID = :depId'.
	 */
	public RmgTimeSheetReq[] findWhereDepIdEquals(int depId) throws RmgTimeSheetReqDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_REQ table that match the criteria 'SEQ_ID = :seqId'.
	 */
	public RmgTimeSheetReq[] findWhereSeqIdEquals(int seqId) throws RmgTimeSheetReqDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_REQ table that match the criteria 'ASSIGNED_TO = :assignedTo'.
	 */
	public RmgTimeSheetReq[] findWhereAssignedToEquals(int assignedTo) throws RmgTimeSheetReqDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_REQ table that match the criteria 'RECEIVED_ON = :receivedOn'.
	 */
	public RmgTimeSheetReq[] findWhereReceivedOnEquals(Date receivedOn) throws RmgTimeSheetReqDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_REQ table that match the criteria 'SUBMITTED_ON = :submittedOn'.
	 */
	public RmgTimeSheetReq[] findWhereSubmittedOnEquals(Date submittedOn) throws RmgTimeSheetReqDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_REQ table that match the criteria 'IS_ESCALATED = :isEscalated'.
	 */
	public RmgTimeSheetReq[] findWhereIsEscalatedEquals(int isEscalated) throws RmgTimeSheetReqDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the DEP_PERDIEM_REQ table that match the specified arbitrary SQL statement
	 */
	public RmgTimeSheetReq[] findByDynamicSelect(String sql, Object[] sqlParams) throws RmgTimeSheetReqDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_REQ table that match the specified arbitrary SQL statement
	 */
	public RmgTimeSheetReq[] findByDynamicWhere(String sql, Object[] sqlParams) throws RmgTimeSheetReqDaoException;

	



}

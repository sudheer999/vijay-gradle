/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
 * For more information please visit http://www.codefutures.com/products/firestorm
 */

package com.dikshatech.portal.dao;

import java.util.Date;
import com.dikshatech.portal.dto.*;
import com.dikshatech.portal.exceptions.*;

public interface TimesheetReqDao
{
	/** 
	 * Inserts a new row in the TIMESHEET_REQ table.
	 */
	public TimesheetReqPk insert(TimesheetReq dto) throws TimesheetReqDaoException;

	/** 
	 * Updates a single row in the TIMESHEET_REQ table.
	 */
	public void update(TimesheetReqPk pk, TimesheetReq dto) throws TimesheetReqDaoException;

	/** 
	 * Updates a single row in the TIMESHEET_REQ table.
	 */
	public int update(String query, Object[] sqlParams) throws TimesheetReqDaoException;

	/** 
	 * Deletes a single row in the TIMESHEET_REQ table.
	 */
	public void delete(TimesheetReqPk pk) throws TimesheetReqDaoException;

	/** 
	 * Returns the rows from the TIMESHEET_REQ table that matches the specified primary-key value.
	 */
	public TimesheetReq findByPrimaryKey(TimesheetReqPk pk) throws TimesheetReqDaoException;

	/** 
	 * Returns all rows from the TIMESHEET_REQ table that match the criteria 'ID = :id'.
	 */
	public TimesheetReq findByPrimaryKey(int id) throws TimesheetReqDaoException;

	/** 
	 * Returns all rows from the TIMESHEET_REQ table that match the criteria ''.
	 */
	public TimesheetReq[] findAll() throws TimesheetReqDaoException;

	/** 
	 * Returns all rows from the TIMESHEET_REQ table that match the criteria 'ID = :id'.
	 */
	public TimesheetReq[] findWhereIdEquals(int id) throws TimesheetReqDaoException;

	/** 
	 * Returns all rows from the TIMESHEET_REQ table that match the criteria 'ESRQM_ID = :esrqmId'.
	 */
	public TimesheetReq[] findWhereEsrqmIdEquals(int esrqmId) throws TimesheetReqDaoException;

	/** 
	 * Returns all rows from the TIMESHEET_REQ table that match the criteria 'COMMENTS = :comments'.
	 */
	public TimesheetReq[] findWhereCommentsEquals(String comments) throws TimesheetReqDaoException;

	/** 
	 * Returns all rows from the TIMESHEET_REQ table that match the criteria 'STATUS = :status'.
	 */
	public TimesheetReq[] findWhereStatusEquals(String status) throws TimesheetReqDaoException;

	/** 
	 * Returns all rows from the TIMESHEET_REQ table that match the criteria 'ASSIGNED_TO = :assignedTo'.
	 */
	public TimesheetReq[] findWhereAssignedToEquals(int assignedTo) throws TimesheetReqDaoException;

	/** 
	 * Returns all rows from the TIMESHEET_REQ table that match the criteria 'RAISED_BY = :raisedBy'.
	 */
	public TimesheetReq[] findWhereRaisedByEquals(int raisedBy) throws TimesheetReqDaoException;

	/** 
	 * Returns all rows from the TIMESHEET_REQ table that match the criteria 'SUMMARY = :summary'.
	 */
	public TimesheetReq[] findWhereSummaryEquals(String summary) throws TimesheetReqDaoException;

	/** 
	 * Returns all rows from the TIMESHEET_REQ table that match the criteria 'TSHEET_ID = :tsheetId'.
	 */
	public TimesheetReq[] findWhereTsheetIdEquals(int tsheetId) throws TimesheetReqDaoException;

	/** 
	 * Returns all rows from the TIMESHEET_REQ table that match the criteria 'CREATEDATETIME = :createdatetime'.
	 */
	public TimesheetReq[] findWhereCreatedatetimeEquals(Date createdatetime) throws TimesheetReqDaoException;

	/** 
	 * Returns all rows from the TIMESHEET_REQ table that match the criteria 'SEQUENCE = :sequence'.
	 */
	public TimesheetReq[] findWhereSequenceEquals(int sequence) throws TimesheetReqDaoException;

	/** 
	 * Returns all rows from the TIMESHEET_REQ table that match the criteria 'MESSAGE_BODY = :messageBody'.
	 */
	public TimesheetReq[] findWhereMessageBodyEquals(String messageBody) throws TimesheetReqDaoException;

	/** 
	 * Returns all rows from the TIMESHEET_REQ table that match the criteria 'ACTION_BY = :actionBy'.
	 */
	public TimesheetReq[] findWhereActionByEquals(int actionBy) throws TimesheetReqDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the TIMESHEET_REQ table that match the specified arbitrary SQL statement
	 */
	public TimesheetReq[] findByDynamicSelect(String sql, Object[] sqlParams) throws TimesheetReqDaoException;

	/** 
	 * Returns all rows from the TIMESHEET_REQ table that match the specified arbitrary SQL statement
	 */
	public TimesheetReq[] findByDynamicWhere(String sql, Object[] sqlParams) throws TimesheetReqDaoException;

}

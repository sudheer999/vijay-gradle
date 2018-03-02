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

public interface TravelReqDao
{
	/** 
	 * Inserts a new row in the TRAVEL_REQ table.
	 */
	public TravelReqPk insert(TravelReq dto) throws TravelReqDaoException;

	/** 
	 * Updates a single row in the TRAVEL_REQ table.
	 */
	public void update(TravelReqPk pk, TravelReq dto) throws TravelReqDaoException;

	/** 
	 * Deletes a single row in the TRAVEL_REQ table.
	 */
	public void delete(TravelReqPk pk) throws TravelReqDaoException;

	/** 
	 * Returns the rows from the TRAVEL_REQ table that matches the specified primary-key value.
	 */
	public TravelReq findByPrimaryKey(TravelReqPk pk) throws TravelReqDaoException;

	/** 
	 * Returns all rows from the TRAVEL_REQ table that match the criteria 'ID = :id'.
	 */
	public TravelReq findByPrimaryKey(int id) throws TravelReqDaoException;

	/** 
	 * Returns all rows from the TRAVEL_REQ table that match the criteria ''.
	 */
	public TravelReq[] findAll() throws TravelReqDaoException;

	/** 
	 * Returns all rows from the TRAVEL_REQ table that match the criteria 'ID = :id'.
	 */
	public TravelReq[] findWhereIdEquals(int id) throws TravelReqDaoException;

	/** 
	 * Returns all rows from the TRAVEL_REQ table that match the criteria 'TL_REQ_ID = :tlReqId'.
	 */
	public TravelReq[] findWhereTlReqIdEquals(int tlReqId) throws TravelReqDaoException;

	/** 
	 * Returns all rows from the TRAVEL_REQ table that match the criteria 'STATUS = :status'.
	 */
	public TravelReq[] findWhereStatusEquals(int status) throws TravelReqDaoException;

	/** 
	 * Returns all rows from the TRAVEL_REQ table that match the criteria 'ASSIGNED_TO = :assignedTo'.
	 */
	public TravelReq[] findWhereAssignedToEquals(int assignedTo) throws TravelReqDaoException;

	/** 
	 * Returns all rows from the TRAVEL_REQ table that match the criteria 'APP_LEVEL = :appLevel'.
	 */
	public TravelReq[] findWhereAppLevelEquals(int appLevel) throws TravelReqDaoException;

	/** 
	 * Returns all rows from the TRAVEL_REQ table that match the criteria 'ACTION_TYPE = :actionType'.
	 */
	public TravelReq[] findWhereActionTypeEquals(int actionType) throws TravelReqDaoException;

	/** 
	 * Returns all rows from the TRAVEL_REQ table that match the criteria 'COMMENT = :comment'.
	 */
	public TravelReq[] findWhereCommentEquals(String comment) throws TravelReqDaoException;

	/** 
	 * Returns all rows from the TRAVEL_REQ table that match the criteria 'CURRENCY = :currency'.
	 */
	public TravelReq[] findWhereCurrencyEquals(String currency) throws TravelReqDaoException;

	/** 
	 * Returns all rows from the TRAVEL_REQ table that match the criteria 'TOTAL_COST = :totalCost'.
	 */
	public TravelReq[] findWhereTotalCostEquals(double totalCost) throws TravelReqDaoException;

	/** 
	 * Returns all rows from the TRAVEL_REQ table that match the criteria 'MESSAGE_BODY = :messageBody'.
	 */
	public TravelReq[] findWhereMessageBodyEquals(String messageBody) throws TravelReqDaoException;

	/** 
	 * Returns all rows from the TRAVEL_REQ table that match the criteria 'DATE_OF_COMPLETION = :dateOfCompletion'.
	 */
	public TravelReq[] findWhereDateOfCompletionEquals(Date dateOfCompletion) throws TravelReqDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the TRAVEL_REQ table that match the specified arbitrary SQL statement
	 */
	public TravelReq[] findByDynamicSelect(String sql, Object[] sqlParams) throws TravelReqDaoException;

	/** 
	 * Returns all rows from the TRAVEL_REQ table that match the specified arbitrary SQL statement
	 */
	public TravelReq[] findByDynamicWhere(String sql, Object[] sqlParams) throws TravelReqDaoException;

	public TravelReq[] findByTravel(int tlReqId) throws TravelReqDaoException;

	public Object findMaxLevel(String sql, Object[] sqlParams) throws TravelReqDaoException;

	public void executeUpdate(String string)throws TravelReqDaoException;
}

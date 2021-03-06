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

public interface FbpReqDao
{
	/** 
	 * Inserts a new row in the FBP_REQ table.
	 */
	public FbpReqPk insert(FbpReq dto) throws FbpReqDaoException;

	/** 
	 * Updates a single row in the FBP_REQ table.
	 */
	public void update(FbpReqPk pk, FbpReq dto) throws FbpReqDaoException;

	/** 
	 * Deletes a single row in the FBP_REQ table.
	 */
	public void delete(FbpReqPk pk) throws FbpReqDaoException;

	/** 
	 * Returns the rows from the FBP_REQ table that matches the specified primary-key value.
	 */
	public FbpReq findByPrimaryKey(FbpReqPk pk) throws FbpReqDaoException;

	/** 
	 * Returns all rows from the FBP_REQ table that match the criteria 'ID = :id'.
	 */
	public FbpReq findByPrimaryKey(int id) throws FbpReqDaoException;

	/** 
	 * Returns all rows from the FBP_REQ table that match the criteria ''.
	 */
	public FbpReq[] findAll() throws FbpReqDaoException;

	/** 
	 * Returns all rows from the FBP_REQ table that match the criteria 'ID = :id'.
	 */
	public FbpReq[] findWhereIdEquals(int id) throws FbpReqDaoException;

	/** 
	 * Returns all rows from the FBP_REQ table that match the criteria 'USER_ID = :userId'.
	 */
	public FbpReq[] findWhereUserIdEquals(int userId) throws FbpReqDaoException;

	/** 
	 * Returns all rows from the FBP_REQ table that match the criteria 'LEVEL_ID = :levelId'.
	 */
	public FbpReq[] findWhereLevelIdEquals(int levelId) throws FbpReqDaoException;

	/** 
	 * Returns all rows from the FBP_REQ table that match the criteria 'LEVEL = :level'.
	 */
	public FbpReq[] findWhereLevelEquals(String level) throws FbpReqDaoException;

	/** 
	 * Returns all rows from the FBP_REQ table that match the criteria 'MONTH_ID = :monthId'.
	 */
	public FbpReq[] findWhereMonthIdEquals(String monthId) throws FbpReqDaoException;

	/** 
	 * Returns all rows from the FBP_REQ table that match the criteria 'ESR_MAP_ID = :esrMapId'.
	 */
	public FbpReq[] findWhereEsrMapIdEquals(int esrMapId) throws FbpReqDaoException;

	/** 
	 * Returns all rows from the FBP_REQ table that match the criteria 'CREATED_ON = :createdOn'.
	 */
	public FbpReq[] findWhereCreatedOnEquals(Date createdOn) throws FbpReqDaoException;

	/** 
	 * Returns all rows from the FBP_REQ table that match the criteria 'STATUS = :status'.
	 */
	public FbpReq[] findWhereStatusEquals(String status) throws FbpReqDaoException;

	/** 
	 * Returns all rows from the FBP_REQ table that match the criteria 'SEQUENCE = :sequence'.
	 */
	public FbpReq[] findWhereSequenceEquals(int sequence) throws FbpReqDaoException;

	/** 
	 * Returns all rows from the FBP_REQ table that match the criteria 'MESSAGE_BODY = :messageBody'.
	 */
	public FbpReq[] findWhereMessageBodyEquals(String messageBody) throws FbpReqDaoException;

	/** 
	 * Returns all rows from the FBP_REQ table that match the criteria 'FREQUENT = :frequent'.
	 */
	public FbpReq[] findWhereFrequentEquals(String frequent) throws FbpReqDaoException;

	/** 
	 * Returns all rows from the FBP_REQ table that match the criteria 'COMMENTS = :comments'.
	 */
	public FbpReq[] findWhereCommentsEquals(String comments) throws FbpReqDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the FBP_REQ table that match the specified arbitrary SQL statement
	 */
	public FbpReq[] findByDynamicSelect(String sql, Object[] sqlParams) throws FbpReqDaoException;

	/** 
	 * Returns all rows from the FBP_REQ table that match the specified arbitrary SQL statement
	 */
	public FbpReq[] findByDynamicWhere(String sql, Object[] sqlParams) throws FbpReqDaoException;
	
	public FbpReq[] findByDynamicWhereMaxF(String sql, Object[] sqlParams) throws FbpReqDaoException;
	
	
	/*public FbpReq[] findByVpf(String sql, Object[] sqlParams) throws FbpReqDaoException;*/

	/*public 	FbpReq[] findByVpf(String sql, Integer integer) throws FbpReqDaoException;*/

	

}

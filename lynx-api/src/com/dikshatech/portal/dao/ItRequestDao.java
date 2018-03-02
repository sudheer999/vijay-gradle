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

public interface ItRequestDao
{
	/** 
	 * Inserts a new row in the IT_REQUEST table.
	 */
	public ItRequestPk insert(ItRequest dto) throws ItRequestDaoException;

	/** 
	 * Updates a single row in the IT_REQUEST table.
	 */
	public void update(ItRequestPk pk, ItRequest dto) throws ItRequestDaoException;

	/** 
	 * Deletes a single row in the IT_REQUEST table.
	 */
	public void delete(ItRequestPk pk) throws ItRequestDaoException;

	/** 
	 * Returns the rows from the IT_REQUEST table that matches the specified primary-key value.
	 */
	public ItRequest findByPrimaryKey(ItRequestPk pk) throws ItRequestDaoException;

	/** 
	 * Returns all rows from the IT_REQUEST table that match the criteria 'ID = :id'.
	 */
	public ItRequest findByPrimaryKey(int id) throws ItRequestDaoException;

	/** 
	 * Returns all rows from the IT_REQUEST table that match the criteria ''.
	 */
	public ItRequest[] findAll() throws ItRequestDaoException;

	/** 
	 * Returns all rows from the IT_REQUEST table that match the criteria 'ID = :id'.
	 */
	public ItRequest[] findWhereIdEquals(int id) throws ItRequestDaoException;

	/** 
	 * Returns all rows from the IT_REQUEST table that match the criteria 'ESR_MAP_ID = :esrMapId'.
	 */
	public ItRequest[] findWhereEsrMapIdEquals(int esrMapId) throws ItRequestDaoException;

	/** 
	 * Returns all rows from the IT_REQUEST table that match the criteria 'CATEGORY = :category'.
	 */
	public ItRequest[] findWhereCategoryEquals(String category) throws ItRequestDaoException;

	/** 
	 * Returns all rows from the IT_REQUEST table that match the criteria 'SUMMARY = :summary'.
	 */
	public ItRequest[] findWhereSummaryEquals(String summary) throws ItRequestDaoException;

	/** 
	 * Returns all rows from the IT_REQUEST table that match the criteria 'DESCRIPTION = :description'.
	 */
	public ItRequest[] findWhereDescriptionEquals(String description) throws ItRequestDaoException;

	/** 
	 * Returns all rows from the IT_REQUEST table that match the criteria 'ATTACHMENT = :attachment'.
	 */
	public ItRequest[] findWhereAttachmentEquals(int attachment) throws ItRequestDaoException;

	/** 
	 * Returns all rows from the IT_REQUEST table that match the criteria 'COMMENTS = :comments'.
	 */
	public ItRequest[] findWhereCommentsEquals(String comments) throws ItRequestDaoException;

	/** 
	 * Returns all rows from the IT_REQUEST table that match the criteria 'STATUS = :status'.
	 */
	public ItRequest[] findWhereStatusEquals(String status) throws ItRequestDaoException;

	/** 
	 * Returns all rows from the IT_REQUEST table that match the criteria 'REQUESTER_ID = :requesterId'.
	 */
	public ItRequest[] findWhereRequesterIdEquals(int requesterId) throws ItRequestDaoException;

	/** 
	 * Returns all rows from the IT_REQUEST table that match the criteria 'ASSIGN_TO = :assignTo'.
	 */
	public ItRequest[] findWhereAssignToEquals(int assignTo) throws ItRequestDaoException;

	/** 
	 * Returns all rows from the IT_REQUEST table that match the criteria 'REMARK = :remark'.
	 */
	public ItRequest[] findWhereRemarkEquals(String remark) throws ItRequestDaoException;

	/** 
	 * Returns all rows from the IT_REQUEST table that match the criteria 'MESSAGE_BODY = :messageBody'.
	 */
	public ItRequest[] findWhereMessageBodyEquals(String messageBody) throws ItRequestDaoException;

	/** 
	 * Returns all rows from the IT_REQUEST table that match the criteria 'CREATE_DATE = :createDate'.
	 */
	public ItRequest[] findWhereCreateDateEquals(Date createDate) throws ItRequestDaoException;

	/** 
	 * Returns all rows from the IT_REQUEST table that match the criteria 'LAST_MODIFIED_DATE = :lastModifiedDate'.
	 */
	public ItRequest[] findWhereLastModifiedDateEquals(Date lastModifiedDate) throws ItRequestDaoException;

	/** 
	 * Returns all rows from the IT_REQUEST table that match the criteria 'RECEIVER_ID = :receiverId'.
	 */
	public ItRequest[] findWhereReceiverIdEquals(int receiverId) throws ItRequestDaoException;

	/** 
	 * Returns all rows from the IT_REQUEST table that match the criteria 'IS_DELETED = :isDeleted'.
	 */
	public ItRequest[] findWhereIsDeletedEquals(int isDeleted) throws ItRequestDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the IT_REQUEST table that match the specified arbitrary SQL statement
	 */
	public ItRequest[] findByDynamicSelect(String sql, Object[] sqlParams) throws ItRequestDaoException;

	/** 
	 * Returns all rows from the IT_REQUEST table that match the specified arbitrary SQL statement
	 */
	public ItRequest[] findByDynamicWhere(String sql, Object[] sqlParams) throws ItRequestDaoException;

}

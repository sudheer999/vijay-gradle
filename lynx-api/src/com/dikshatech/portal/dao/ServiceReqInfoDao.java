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

public interface ServiceReqInfoDao
{
	/** 
	 * Inserts a new row in the SERVICE_REQ_INFO table.
	 */
	public ServiceReqInfoPk insert(ServiceReqInfo dto) throws ServiceReqInfoDaoException;

	/** 
	 * Updates a single row in the SERVICE_REQ_INFO table.
	 */
	public void update(ServiceReqInfoPk pk, ServiceReqInfo dto) throws ServiceReqInfoDaoException;

	/** 
	 * Deletes a single row in the SERVICE_REQ_INFO table.
	 */
	public void delete(ServiceReqInfoPk pk) throws ServiceReqInfoDaoException;

	/** 
	 * Returns the rows from the SERVICE_REQ_INFO table that matches the specified primary-key value.
	 */
	public ServiceReqInfo findByPrimaryKey(ServiceReqInfoPk pk) throws ServiceReqInfoDaoException;

	/** 
	 * Returns all rows from the SERVICE_REQ_INFO table that match the criteria 'ID = :id'.
	 */
	public ServiceReqInfo findByPrimaryKey(int id) throws ServiceReqInfoDaoException;

	/** 
	 * Returns all rows from the SERVICE_REQ_INFO table that match the criteria ''.
	 */
	public ServiceReqInfo[] findAll() throws ServiceReqInfoDaoException;

	/** 
	 * Returns all rows from the SERVICE_REQ_INFO table that match the criteria 'ID = :id'.
	 */
	public ServiceReqInfo[] findWhereIdEquals(int id) throws ServiceReqInfoDaoException;

	/** 
	 * Returns all rows from the SERVICE_REQ_INFO table that match the criteria 'ESR_MAP_ID = :esrMapId'.
	 */
	public ServiceReqInfo[] findWhereEsrMapIdEquals(int esrMapId) throws ServiceReqInfoDaoException;

	/** 
	 * Returns all rows from the SERVICE_REQ_INFO table that match the criteria 'ASSIGNED_TO_DIV = :assignedToDiv'.
	 */
	public ServiceReqInfo[] findWhereAssignedToDivEquals(int assignedToDiv) throws ServiceReqInfoDaoException;

	/** 
	 * Returns all rows from the SERVICE_REQ_INFO table that match the criteria 'STATUS = :status'.
	 */
	public ServiceReqInfo[] findWhereStatusEquals(String status) throws ServiceReqInfoDaoException;

	/** 
	 * Returns all rows from the SERVICE_REQ_INFO table that match the criteria 'ACTION_BY = :actionBy'.
	 */
	public ServiceReqInfo[] findWhereActionByEquals(int actionBy) throws ServiceReqInfoDaoException;

	/** 
	 * Returns all rows from the SERVICE_REQ_INFO table that match the criteria 'HD_EST_DATE_RESOLVE = :hdEstDateResolve'.
	 */
	public ServiceReqInfo[] findWhereHdEstDateResolveEquals(Date hdEstDateResolve) throws ServiceReqInfoDaoException;

	/** 
	 * Returns all rows from the SERVICE_REQ_INFO table that match the criteria 'HD_COMMENTS = :hdComments'.
	 */
	public ServiceReqInfo[] findWhereHdCommentsEquals(String hdComments) throws ServiceReqInfoDaoException;

	/** 
	 * Returns all rows from the SERVICE_REQ_INFO table that match the criteria 'ESCALATE_TO = :escalateTo'.
	 */
	public ServiceReqInfo[] findWhereEscalateToEquals(int escalateTo) throws ServiceReqInfoDaoException;

	/** 
	 * Returns all rows from the SERVICE_REQ_INFO table that match the criteria 'H_REMARKS = :hRemarks'.
	 */
	public ServiceReqInfo[] findWhereHRemarksEquals(String hRemarks) throws ServiceReqInfoDaoException;

	/** 
	 * Returns all rows from the SERVICE_REQ_INFO table that match the criteria 'CREATION_DATETIME = :creationDatetime'.
	 */
	public ServiceReqInfo[] findWhereCreationDatetimeEquals(Date creationDatetime) throws ServiceReqInfoDaoException;

	/** 
	 * Returns all rows from the SERVICE_REQ_INFO table that match the criteria 'SUMMARY = :summary'.
	 */
	public ServiceReqInfo[] findWhereSummaryEquals(String summary) throws ServiceReqInfoDaoException;

	/** 
	 * Returns all rows from the SERVICE_REQ_INFO table that match the criteria 'DESCRIPTION = :description'.
	 */
	public ServiceReqInfo[] findWhereDescriptionEquals(String description) throws ServiceReqInfoDaoException;

	/** 
	 * Returns all rows from the SERVICE_REQ_INFO table that match the criteria 'EST_DATE_RESOLVE = :estDateResolve'.
	 */
	public ServiceReqInfo[] findWhereEstDateResolveEquals(Date estDateResolve) throws ServiceReqInfoDaoException;

	/** 
	 * Returns all rows from the SERVICE_REQ_INFO table that match the criteria 'COMMENT = :comment'.
	 */
	public ServiceReqInfo[] findWhereCommentEquals(String comment) throws ServiceReqInfoDaoException;

	/** 
	 * Returns all rows from the SERVICE_REQ_INFO table that match the criteria 'DEP_SERV_REQ = :depServReq'.
	 */
	public ServiceReqInfo[] findWhereDepServReqEquals(int depServReq) throws ServiceReqInfoDaoException;

	/** 
	 * Returns all rows from the SERVICE_REQ_INFO table that match the criteria 'ASSIGNED_TO = :assignedTo'.
	 */
	public ServiceReqInfo[] findWhereAssignedToEquals(int assignedTo) throws ServiceReqInfoDaoException;

	/** 
	 * Returns all rows from the SERVICE_REQ_INFO table that match the criteria 'SEVERITY = :severity'.
	 */
	public ServiceReqInfo[] findWhereSeverityEquals(String severity) throws ServiceReqInfoDaoException;

	/** 
	 * Returns all rows from the SERVICE_REQ_INFO table that match the criteria 'PRIORITY = :priority'.
	 */
	public ServiceReqInfo[] findWherePriorityEquals(String priority) throws ServiceReqInfoDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the SERVICE_REQ_INFO table that match the specified arbitrary SQL statement
	 */
	public ServiceReqInfo[] findByDynamicSelect(String sql, Object[] sqlParams) throws ServiceReqInfoDaoException;

	/** 
	 * Returns all rows from the SERVICE_REQ_INFO table that match the specified arbitrary SQL statement
	 */
	public ServiceReqInfo[] findByDynamicWhere(String sql, Object[] sqlParams) throws ServiceReqInfoDaoException;

}

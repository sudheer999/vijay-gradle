package com.dikshatech.portal.dao;

import java.util.Date;
import java.util.HashMap;

import com.dikshatech.portal.dto.RetentionEmpSerReqMap;
import com.dikshatech.portal.dto.RetentionEmpSerReqMapPk;
import com.dikshatech.portal.exceptions.RetentionEmpSerReqMapDaoException;

public interface RetentionEmpSerReqMapDao {

	/** 
	 * Inserts a new row in the RETENTION_EMP_SER_REQ_MAP table.
	 */
	public RetentionEmpSerReqMapPk insert(RetentionEmpSerReqMap dto) throws RetentionEmpSerReqMapDaoException;

	/** 
	 * Updates a single row in the RETENTION_EMP_SER_REQ_MAP table.
	 */
	public void update(RetentionEmpSerReqMapPk pk, RetentionEmpSerReqMap dto) throws RetentionEmpSerReqMapDaoException;

	/** 
	 * Deletes a single row in the RETENTION_EMP_SER_REQ_MAP table.
	 */
	public void delete(RetentionEmpSerReqMapPk pk) throws RetentionEmpSerReqMapDaoException;

	/** 
	 * Returns the rows from the RETENTION_EMP_SER_REQ_MAP table that matches the specified primary-key value.
	 */
	public RetentionEmpSerReqMap findByPrimaryKey(RetentionEmpSerReqMapPk pk) throws RetentionEmpSerReqMapDaoException;

	/** 
	 * Returns all rows from the RETENTION_EMP_SER_REQ_MAP table that match the criteria 'ID = :id'.
	 */
	public RetentionEmpSerReqMap findByPrimaryKey(int id) throws RetentionEmpSerReqMapDaoException;

	/** 
	 * Returns all rows from the RETENTION_EMP_SER_REQ_MAP table that match the criteria ''.
	 */
	public RetentionEmpSerReqMap[] findAll() throws RetentionEmpSerReqMapDaoException;

	/** 
	 * Returns all rows from the RETENTION_EMP_SER_REQ_MAP table that match the criteria 'ID = :id'.
	 */
	public RetentionEmpSerReqMap[] findWhereIdEquals(int id) throws RetentionEmpSerReqMapDaoException;

	/** 
	 * Returns all rows from the RETENTION_EMP_SER_REQ_MAP table that match the criteria 'SUB_DATE = :subDate'.
	 */
	public RetentionEmpSerReqMap[] findWhereSubDateEquals(Date subDate) throws RetentionEmpSerReqMapDaoException;

	/** 
	 * Returns all rows from the RETENTION_EMP_SER_REQ_MAP table that match the criteria 'REQ_ID = :reqId'.
	 */
	public RetentionEmpSerReqMap[] findWhereReqIdEquals(String reqId) throws RetentionEmpSerReqMapDaoException;

	/** 
	 * Returns all rows from the RETENTION_EMP_SER_REQ_MAP table that match the criteria 'REQ_TYPE_ID = :reqTypeId'.
	 */
	public RetentionEmpSerReqMap[] findWhereReqTypeIdEquals(int reqTypeId) throws RetentionEmpSerReqMapDaoException;

	/** 
	 * Returns all rows from the RETENTION_EMP_SER_REQ_MAP table that match the criteria 'REGION_ID = :regionId'.
	 */
	public RetentionEmpSerReqMap[] findWhereRegionIdEquals(int regionId) throws RetentionEmpSerReqMapDaoException;

	/** 
	 * Returns all rows from the RETENTION_EMP_SER_REQ_MAP table that match the criteria 'REQUESTOR_ID = :requestorId'.
	 */
	public RetentionEmpSerReqMap[] findWhereRequestorIdEquals(int requestorId) throws RetentionEmpSerReqMapDaoException;

	/** 
	 * Returns all rows from the RETENTION_EMP_SER_REQ_MAP table that match the criteria 'APPROVAL_CHAIN = :approvalChain'.
	 */
	public RetentionEmpSerReqMap[] findWhereApprovalChainEquals(String approvalChain) throws RetentionEmpSerReqMapDaoException;

	/** 
	 * Returns all rows from the RETENTION_EMP_SER_REQ_MAP table that match the criteria 'NOTIFY = :notify'.
	 */
	public RetentionEmpSerReqMap[] findWhereNotifyEquals(String notify) throws RetentionEmpSerReqMapDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the RETENTION_EMP_SER_REQ_MAP table that match the specified arbitrary SQL statement
	 */
	public RetentionEmpSerReqMap[] findByDynamicSelect(String sql, Object[] sqlParams) throws RetentionEmpSerReqMapDaoException;

	/** 
	 * Returns all rows from the RETENTION_EMP_SER_REQ_MAP table that match the specified arbitrary SQL statement
	 */
	public RetentionEmpSerReqMap[] findByDynamicWhere(String sql, Object[] sqlParams) throws RetentionEmpSerReqMapDaoException;
	
	public HashMap<Integer,String> getIdReqIdMap(int reqTypeId) throws RetentionEmpSerReqMapDaoException;


}

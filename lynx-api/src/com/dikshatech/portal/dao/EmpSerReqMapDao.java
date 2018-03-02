/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
 * For more information please visit http://www.codefutures.com/products/firestorm
 */

package com.dikshatech.portal.dao;

import java.util.Date;
import java.util.HashMap;

import com.dikshatech.portal.dto.*;
import com.dikshatech.portal.exceptions.*;

public interface EmpSerReqMapDao
{
	/** 
	 * Inserts a new row in the EMP_SER_REQ_MAP table.
	 */
	public EmpSerReqMapPk insert(EmpSerReqMap dto) throws EmpSerReqMapDaoException;

	/** 
	 * Updates a single row in the EMP_SER_REQ_MAP table.
	 */
	public void update(EmpSerReqMapPk pk, EmpSerReqMap dto) throws EmpSerReqMapDaoException;

	/** 
	 * Deletes a single row in the EMP_SER_REQ_MAP table.
	 */
	public void delete(EmpSerReqMapPk pk) throws EmpSerReqMapDaoException;

	/** 
	 * Returns the rows from the EMP_SER_REQ_MAP table that matches the specified primary-key value.
	 */
	public EmpSerReqMap findByPrimaryKey(EmpSerReqMapPk pk) throws EmpSerReqMapDaoException;

	/** 
	 * Returns all rows from the EMP_SER_REQ_MAP table that match the criteria 'ID = :id'.
	 */
	public EmpSerReqMap findByPrimaryKey(int id) throws EmpSerReqMapDaoException;

	/** 
	 * Returns all rows from the EMP_SER_REQ_MAP table that match the criteria ''.
	 */
	public EmpSerReqMap[] findAll() throws EmpSerReqMapDaoException;

	/** 
	 * Returns all rows from the EMP_SER_REQ_MAP table that match the criteria 'ID = :id'.
	 */
	public EmpSerReqMap[] findWhereIdEquals(int id) throws EmpSerReqMapDaoException;

	/** 
	 * Returns all rows from the EMP_SER_REQ_MAP table that match the criteria 'SUB_DATE = :subDate'.
	 */
	public EmpSerReqMap[] findWhereSubDateEquals(Date subDate) throws EmpSerReqMapDaoException;

	/** 
	 * Returns all rows from the EMP_SER_REQ_MAP table that match the criteria 'REQ_ID = :reqId'.
	 */
	public EmpSerReqMap[] findWhereReqIdEquals(String reqId) throws EmpSerReqMapDaoException;

	/** 
	 * Returns all rows from the EMP_SER_REQ_MAP table that match the criteria 'REQ_TYPE_ID = :reqTypeId'.
	 */
	public EmpSerReqMap[] findWhereReqTypeIdEquals(int reqTypeId) throws EmpSerReqMapDaoException;

	/** 
	 * Returns all rows from the EMP_SER_REQ_MAP table that match the criteria 'REGION_ID = :regionId'.
	 */
	public EmpSerReqMap[] findWhereRegionIdEquals(int regionId) throws EmpSerReqMapDaoException;

	/** 
	 * Returns all rows from the EMP_SER_REQ_MAP table that match the criteria 'REQUESTOR_ID = :requestorId'.
	 */
	public EmpSerReqMap[] findWhereRequestorIdEquals(int requestorId) throws EmpSerReqMapDaoException;

	/** 
	 * Returns all rows from the EMP_SER_REQ_MAP table that match the criteria 'APPROVAL_CHAIN = :approvalChain'.
	 */
	public EmpSerReqMap[] findWhereApprovalChainEquals(String approvalChain) throws EmpSerReqMapDaoException;

	/** 
	 * Returns all rows from the EMP_SER_REQ_MAP table that match the criteria 'NOTIFY = :notify'.
	 */
	public EmpSerReqMap[] findWhereNotifyEquals(String notify) throws EmpSerReqMapDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the EMP_SER_REQ_MAP table that match the specified arbitrary SQL statement
	 */
	public EmpSerReqMap[] findByDynamicSelect(String sql, Object[] sqlParams) throws EmpSerReqMapDaoException;

	/** 
	 * Returns all rows from the EMP_SER_REQ_MAP table that match the specified arbitrary SQL statement
	 */
	public EmpSerReqMap[] findByDynamicWhere(String sql, Object[] sqlParams) throws EmpSerReqMapDaoException;
	
	public HashMap<Integer,String> getIdReqIdMap(int reqTypeId) throws EmpSerReqMapDaoException;

}
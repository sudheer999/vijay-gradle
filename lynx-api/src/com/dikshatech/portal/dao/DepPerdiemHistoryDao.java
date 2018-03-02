/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
 * For more information please visit http://www.codefutures.com/products/firestorm
 */

package com.dikshatech.portal.dao;

import com.dikshatech.portal.dto.*;
import com.dikshatech.portal.exceptions.*;

public interface DepPerdiemHistoryDao
{
	/** 
	 * Inserts a new row in the DEP_PERDIEM_HISTORY table.
	 */
	public DepPerdiemHistoryPk insert(DepPerdiemHistory dto) throws DepPerdiemHistoryDaoException;

	/** 
	 * Updates a single row in the DEP_PERDIEM_HISTORY table.
	 */
	public void update(DepPerdiemHistoryPk pk, DepPerdiemHistory dto) throws DepPerdiemHistoryDaoException;

	/** 
	 * Deletes a single row in the DEP_PERDIEM_HISTORY table.
	 */
	public void delete(DepPerdiemHistoryPk pk) throws DepPerdiemHistoryDaoException;

	/** 
	 * Returns the rows from the DEP_PERDIEM_HISTORY table that matches the specified primary-key value.
	 */
	public DepPerdiemHistory findByPrimaryKey(DepPerdiemHistoryPk pk) throws DepPerdiemHistoryDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the criteria 'ID = :id'.
	 */
	public DepPerdiemHistory findByPrimaryKey(int id) throws DepPerdiemHistoryDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the criteria ''.
	 */
	public DepPerdiemHistory[] findAll() throws DepPerdiemHistoryDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the criteria 'ID = :id'.
	 */
	public DepPerdiemHistory[] findWhereIdEquals(int id) throws DepPerdiemHistoryDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the criteria 'DEP_ID = :depId'.
	 */
	public DepPerdiemHistory[] findWhereDepIdEquals(int depId) throws DepPerdiemHistoryDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the criteria 'USER_ID = :userId'.
	 */
	public DepPerdiemHistory[] findWhereUserIdEquals(int userId) throws DepPerdiemHistoryDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the criteria 'ADDED_BY = :addedBy'.
	 */
	public DepPerdiemHistory[] findWhereAddedByEquals(int addedBy) throws DepPerdiemHistoryDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the criteria 'MODIFIED_BY = :modifiedBy'.
	 */
	public DepPerdiemHistory[] findWhereModifiedByEquals(int modifiedBy) throws DepPerdiemHistoryDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the criteria 'MANAGER_ID = :managerId'.
	 */
	public DepPerdiemHistory[] findWhereManagerIdEquals(int managerId) throws DepPerdiemHistoryDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the criteria 'MANAGER_NAME = :managerName'.
	 */
	public DepPerdiemHistory[] findWhereManagerNameEquals(String managerName) throws DepPerdiemHistoryDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the criteria 'PROJECT_NAME = :projectName'.
	 */
	public DepPerdiemHistory[] findWhereProjectNameEquals(String projectName) throws DepPerdiemHistoryDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the criteria 'PERDIEM = :perdiem'.
	 */
	public DepPerdiemHistory[] findWherePerdiemEquals(String perdiem) throws DepPerdiemHistoryDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the criteria 'PERDIEM_HISTORY = :perdiemHistory'.
	 */
	public DepPerdiemHistory[] findWherePerdiemHistoryEquals(String perdiemHistory) throws DepPerdiemHistoryDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the criteria 'PAYABLE_DAYS = :payableDays'.
	 */
	public DepPerdiemHistory[] findWherePayableDaysEquals(int payableDays) throws DepPerdiemHistoryDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the criteria 'PAYABLE_DAYS_HISTORY = :payableDaysHistory'.
	 */
	public DepPerdiemHistory[] findWherePayableDaysHistoryEquals(String payableDaysHistory) throws DepPerdiemHistoryDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the criteria 'CURRENCY = :currency'.
	 */
	public DepPerdiemHistory[] findWhereCurrencyEquals(String currency) throws DepPerdiemHistoryDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the criteria 'CURRENCY_HISTORY = :currencyHistory'.
	 */
	public DepPerdiemHistory[] findWhereCurrencyHistoryEquals(String currencyHistory) throws DepPerdiemHistoryDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the criteria 'PERDIEM_COMPUTED = :perdiemComputed'.
	 */
	public DepPerdiemHistory[] findWherePerdiemComputedEquals(String perdiemComputed) throws DepPerdiemHistoryDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the criteria 'COMMENTS = :comments'.
	 */
	public DepPerdiemHistory[] findWhereCommentsEquals(String comments) throws DepPerdiemHistoryDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the specified arbitrary SQL statement
	 */
	public DepPerdiemHistory[] findByDynamicSelect(String sql, Object[] sqlParams) throws DepPerdiemHistoryDaoException;

	/** 
	 * Returns all rows from the DEP_PERDIEM_HISTORY table that match the specified arbitrary SQL statement
	 */
	public DepPerdiemHistory[] findByDynamicWhere(String sql, Object[] sqlParams) throws DepPerdiemHistoryDaoException;

}

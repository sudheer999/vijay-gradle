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

public interface DepPerdiemHoldDao
{
	/** 
	 * Inserts a new row in the dep_perdiem_hold table.
	 */
	public DepPerdiemHoldPk insert(DepPerdiemHold dto) throws DepPerdiemHoldDaoException;

	/** 
	 * Updates a single row in the dep_perdiem_hold table.
	 */
	public void update(DepPerdiemHoldPk pk, DepPerdiemHold dto) throws DepPerdiemHoldDaoException;

	/** 
	 * Deletes a single row in the dep_perdiem_hold table.
	 */
	public void delete(DepPerdiemHoldPk pk) throws DepPerdiemHoldDaoException;

	/** 
	 * Returns the rows from the dep_perdiem_hold table that matches the specified primary-key value.
	 */
	public DepPerdiemHold findByPrimaryKey(DepPerdiemHoldPk pk) throws DepPerdiemHoldDaoException;

	/** 
	 * Returns all rows from the dep_perdiem_hold table that match the criteria 'ID = :id'.
	 */
	public DepPerdiemHold findByPrimaryKey(int id) throws DepPerdiemHoldDaoException;

	/** 
	 * Returns all rows from the dep_perdiem_hold table that match the criteria ''.
	 */
	public DepPerdiemHold[] findAll() throws DepPerdiemHoldDaoException;

	/** 
	 * Returns all rows from the dep_perdiem_hold table that match the criteria 'REP_ID = :repId'.
	 */
	public DepPerdiemHold[] findWhereRepIdEquals(Integer repId) throws DepPerdiemHoldDaoException;

	/** 
	 * Returns all rows from the dep_perdiem_hold table that match the criteria 'USER_ID = :userId'.
	 */
	public DepPerdiemHold[] findWhereUserIdEquals(Integer userId) throws DepPerdiemHoldDaoException;

	/** 
	 * Returns all rows from the dep_perdiem_hold table that match the criteria 'ESC_FROM = :escFrom'.
	 */
	public DepPerdiemHold[] findWhereEscFromEquals(Integer escFrom) throws DepPerdiemHoldDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the dep_perdiem_hold table that match the specified arbitrary SQL statement
	 */
	public DepPerdiemHold[] findByDynamicSelect(String sql, Object[] sqlParams) throws DepPerdiemHoldDaoException;

	/** 
	 * Returns all rows from the dep_perdiem_hold table that match the specified arbitrary SQL statement
	 */
	public DepPerdiemHold[] findByDynamicWhere(String sql, Object[] sqlParams) throws DepPerdiemHoldDaoException;

}

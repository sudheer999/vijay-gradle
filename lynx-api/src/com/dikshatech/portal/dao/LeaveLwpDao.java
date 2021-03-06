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

public interface LeaveLwpDao
{
	/** 
	 * Inserts a new row in the LEAVE_LWP table.
	 */
	public LeaveLwpPk insert(LeaveLwp dto) throws LeaveLwpDaoException;
	
	/** 
	 * Updates a single row in the LEAVE_LWP table.
	 */
	public void update(LeaveLwpPk pk, LeaveLwp dto) throws LeaveLwpDaoException;
	
	/** 
	 * Deletes a single row in the LEAVE_LWP table.
	 */
	public void delete(LeaveLwpPk pk) throws LeaveLwpDaoException;

	/** 
	 * Returns the rows from the LEAVE_Lwp table that matches the specified primary-key value.
	 */
	public LeaveLwp findByPrimaryKey(LeaveLwpPk pk) throws LeaveLwpDaoException;

	/** 
	 * Returns all rows from the LEAVE_Lwp table that match the criteria 'ID = :id'.
	 */
	public LeaveLwp findByPrimaryKey(int id) throws LeaveLwpDaoException;

	/** 
	 * Returns all rows from the LEAVE_Lwp table that match the criteria ''.
	 */
	public LeaveLwp[] findAll() throws LeaveLwpDaoException;

	/** 
	 * Returns all rows from the LEAVE_Lwp table that match the criteria 'ID = :id'.
	 */
	public LeaveLwp[] findWhereIdEquals(int id) throws LeaveLwpDaoException;

	/** 
	 * Returns all rows from the LEAVE_Lwp table that match the criteria 'USER_ID = :userId'.
	 */
	public LeaveLwp findWhereUserIdEquals(int userId) throws LeaveLwpDaoException;
	
	/** 
	 * Returns all rows from the LEAVE_Lwp table that match the criteria 'MONTH_CYCLE = :monthCycle'.
	 */
	public LeaveLwp findWhereUserIdEquals(String monthCycle) throws LeaveLwpDaoException;
	
	/** 
	 * Returns all rows from the LEAVE_Lwp table that match the criteria 'USER_ID = :userId' and 'MONTH_CYCLE = :monthCycle'.
	 */
	public LeaveLwp findWhereUserIdEquals(int userId,String monthCycle) throws LeaveLwpDaoException;
	
	/** 
	 * Returns all rows from the LEAVE_Lwp table that match the criteria 'LWP = :lwp'.
	 */
	public LeaveLwp[] findWhereLwpEquals(float lwp) throws LeaveLwpDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the LEAVE_Lwp table that match the specified arbitrary SQL statement
	 */
	public LeaveLwp[] findByDynamicSelect(String sql, Object[] sqlParams) throws LeaveLwpDaoException;

	/** 
	 * Returns all rows from the LEAVE_Lwp table that match the specified arbitrary SQL statement
	 */
	public LeaveLwp[] findByDynamicWhere(String sql, Object[] sqlParams) throws LeaveLwpDaoException;
	
	public LeaveLwp[] findByUserID(String sql, Object[] sqlParams) throws LeaveLwpDaoException;

}
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

public interface LeaveProjectDao
{
	/** 
	 * Inserts a new row in the LEAVE_PROJECT table.
	 */
	public LeaveProjectPk insert(LeaveProject dto) throws LeaveProjectDaoException;

	/** 
	 * Updates a single row in the LEAVE_PROJECT table.
	 */
	public void update(LeaveProjectPk pk, LeaveProject dto) throws LeaveProjectDaoException;

	/** 
	 * Deletes a single row in the LEAVE_PROJECT table.
	 */
	public void delete(LeaveProjectPk pk) throws LeaveProjectDaoException;

	/** 
	 * Returns the rows from the LEAVE_PROJECT table that matches the specified primary-key value.
	 */
	public LeaveProject findByPrimaryKey(LeaveProjectPk pk) throws LeaveProjectDaoException;

	/** 
	 * Returns all rows from the LEAVE_PROJECT table that match the criteria 'ID = :id'.
	 */
	public LeaveProject findByPrimaryKey(int id) throws LeaveProjectDaoException;

	/** 
	 * Returns all rows from the LEAVE_PROJECT table that match the criteria ''.
	 */
	public LeaveProject[] findAll() throws LeaveProjectDaoException;

	/** 
	 * Returns all rows from the LEAVE_PROJECT table that match the criteria 'ID = :id'.
	 */
	public LeaveProject[] findWhereIdEquals(int id) throws LeaveProjectDaoException;

	/** 
	 * Returns all rows from the LEAVE_PROJECT table that match the criteria 'PROJECT_ID = :projectId'.
	 */
	public LeaveProject[] findWhereProjectIdEquals(String projectId) throws LeaveProjectDaoException;

	/** 
	 * Returns all rows from the LEAVE_PROJECT table that match the criteria 'PROJECT_NAME = :projectName'.
	 */
	public LeaveProject[] findWhereProjectNameEquals(String projectName) throws LeaveProjectDaoException;

	/** 
	 * Returns all rows from the LEAVE_PROJECT table that match the criteria 'CHARGE_CODE = :chargeCode'.
	 */
	public LeaveProject[] findWhereChargeCodeEquals(String chargeCode) throws LeaveProjectDaoException;

	/** 
	 * Returns all rows from the LEAVE_PROJECT table that match the criteria 'TITLE = :title'.
	 */
	public LeaveProject[] findWhereTitleEquals(String title) throws LeaveProjectDaoException;

	/** 
	 * Returns all rows from the LEAVE_PROJECT table that match the criteria 'LEAVE_MASTER_ID = :leaveMasterId'.
	 */
	public LeaveProject[] findWhereLeaveMasterIdEquals(int leaveMasterId) throws LeaveProjectDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the LEAVE_PROJECT table that match the specified arbitrary SQL statement
	 */
	public LeaveProject[] findByDynamicSelect(String sql, Object[] sqlParams) throws LeaveProjectDaoException;

	/** 
	 * Returns all rows from the LEAVE_PROJECT table that match the specified arbitrary SQL statement
	 */
	public LeaveProject[] findByDynamicWhere(String sql, Object[] sqlParams) throws LeaveProjectDaoException;

}

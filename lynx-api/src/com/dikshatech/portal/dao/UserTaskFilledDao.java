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

public interface UserTaskFilledDao
{
	/** 
	 * Inserts a new row in the USER_TASK_FILLED table.
	 */
	public UserTaskFilledPk insert(UserTaskFilled dto) throws UserTaskFilledDaoException;

	/** 
	 * Updates a single row in the USER_TASK_FILLED table.
	 */
	public void update(UserTaskFilledPk pk, UserTaskFilled dto) throws UserTaskFilledDaoException;

	/** 
	 * Deletes a single row in the USER_TASK_FILLED table.
	 */
	public void delete(UserTaskFilledPk pk) throws UserTaskFilledDaoException;

	/** 
	 * Returns the rows from the USER_TASK_FILLED table that matches the specified primary-key value.
	 */
	public UserTaskFilled findByPrimaryKey(UserTaskFilledPk pk) throws UserTaskFilledDaoException;

	/** 
	 * Returns all rows from the USER_TASK_FILLED table that match the criteria 'ID = :id'.
	 */
	public UserTaskFilled findByPrimaryKey(int id) throws UserTaskFilledDaoException;

	/** 
	 * Returns all rows from the USER_TASK_FILLED table that match the criteria ''.
	 */
	public UserTaskFilled[] findAll() throws UserTaskFilledDaoException;

	/** 
	 * Returns all rows from the USER_TASK_FILLED table that match the criteria 'MAP_ID = :mapId'.
	 */
	public UserTaskFilled[] findByUserTaskTimesheetMap(int mapId) throws UserTaskFilledDaoException;

	/** 
	 * Returns all rows from the USER_TASK_FILLED table that match the criteria 'ID = :id'.
	 */
	public UserTaskFilled[] findWhereIdEquals(int id) throws UserTaskFilledDaoException;

	/** 
	 * Returns all rows from the USER_TASK_FILLED table that match the criteria 'HOURS_SPENT = :hoursSpent'.
	 */
	public UserTaskFilled[] findWhereHoursSpentEquals(String hoursSpent) throws UserTaskFilledDaoException;

	/** 
	 * Returns all rows from the USER_TASK_FILLED table that match the criteria 'MAP_ID = :mapId'.
	 */
	public UserTaskFilled[] findWhereMapIdEquals(int mapId) throws UserTaskFilledDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the USER_TASK_FILLED table that match the specified arbitrary SQL statement
	 */
	public UserTaskFilled[] findByDynamicSelect(String sql, Object[] sqlParams) throws UserTaskFilledDaoException;

	/** 
	 * Returns all rows from the USER_TASK_FILLED table that match the specified arbitrary SQL statement
	 */
	public UserTaskFilled[] findByDynamicWhere(String sql, Object[] sqlParams) throws UserTaskFilledDaoException;

}

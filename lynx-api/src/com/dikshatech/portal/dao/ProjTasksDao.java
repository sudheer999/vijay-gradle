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

public interface ProjTasksDao
{
	/** 
	 * Inserts a new row in the PROJ_TASKS table.
	 */
	public ProjTasksPk insert(ProjTasks dto) throws ProjTasksDaoException;

	/** 
	 * Updates a single row in the PROJ_TASKS table.
	 */
	public void update(ProjTasksPk pk, ProjTasks dto) throws ProjTasksDaoException;

	/** 
	 * Deletes a single row in the PROJ_TASKS table.
	 */
	public void delete(ProjTasksPk pk) throws ProjTasksDaoException;

	/** 
	 * Returns the rows from the PROJ_TASKS table that matches the specified primary-key value.
	 */
	public ProjTasks findByPrimaryKey(ProjTasksPk pk) throws ProjTasksDaoException;

	/** 
	 * Returns all rows from the PROJ_TASKS table that match the criteria 'ID = :id'.
	 */
	public ProjTasks findByPrimaryKey(int id) throws ProjTasksDaoException;

	/** 
	 * Returns all rows from the PROJ_TASKS table that match the criteria ''.
	 */
	public ProjTasks[] findAll() throws ProjTasksDaoException;

	/** 
	 * Returns all rows from the PROJ_TASKS table that match the criteria 'ID = :id'.
	 */
	public ProjTasks[] findWhereIdEquals(int id) throws ProjTasksDaoException;

	/** 
	 * Returns all rows from the PROJ_TASKS table that match the criteria 'PROJ_ID = :projId'.
	 */
	public ProjTasks[] findWhereProjIdEquals(int projId) throws ProjTasksDaoException;

	/** 
	 * Returns all rows from the PROJ_TASKS table that match the criteria 'TASKS = :tasks'.
	 */
	public ProjTasks[] findWhereTasksEquals(String tasks) throws ProjTasksDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the PROJ_TASKS table that match the specified arbitrary SQL statement
	 */
	public ProjTasks[] findByDynamicSelect(String sql, Object[] sqlParams) throws ProjTasksDaoException;

	/** 
	 * Returns all rows from the PROJ_TASKS table that match the specified arbitrary SQL statement
	 */
	public ProjTasks[] findByDynamicWhere(String sql, Object[] sqlParams) throws ProjTasksDaoException;
	
	public boolean deleteAllTaskByProject(int projectId);
	


}

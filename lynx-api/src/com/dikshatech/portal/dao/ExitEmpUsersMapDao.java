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

public interface ExitEmpUsersMapDao
{
	/** 
	 * Inserts a new row in the EXIT_EMP_USERS_MAP table.
	 */
	public ExitEmpUsersMapPk insert(ExitEmpUsersMap dto) throws ExitEmpUsersMapDaoException;

	/** 
	 * Updates a single row in the EXIT_EMP_USERS_MAP table.
	 */
	public void update(ExitEmpUsersMapPk pk, ExitEmpUsersMap dto) throws ExitEmpUsersMapDaoException;

	/** 
	 * Deletes a single row in the EXIT_EMP_USERS_MAP table.
	 */
	public void delete(ExitEmpUsersMapPk pk) throws ExitEmpUsersMapDaoException;

	/** 
	 * Returns the rows from the EXIT_EMP_USERS_MAP table that matches the specified primary-key value.
	 */
	public ExitEmpUsersMap findByPrimaryKey(ExitEmpUsersMapPk pk) throws ExitEmpUsersMapDaoException;

	/** 
	 * Returns all rows from the EXIT_EMP_USERS_MAP table that match the criteria 'ID = :id'.
	 */
	public ExitEmpUsersMap findByPrimaryKey(int id) throws ExitEmpUsersMapDaoException;

	/** 
	 * Returns all rows from the EXIT_EMP_USERS_MAP table that match the criteria ''.
	 */
	public ExitEmpUsersMap[] findAll() throws ExitEmpUsersMapDaoException;

	/** 
	 * Returns all rows from the EXIT_EMP_USERS_MAP table that match the criteria 'ID = :id'.
	 */
	public ExitEmpUsersMap[] findWhereIdEquals(int id) throws ExitEmpUsersMapDaoException;

	/** 
	 * Returns all rows from the EXIT_EMP_USERS_MAP table that match the criteria 'EXIT_ID = :exitId'.
	 */
	public ExitEmpUsersMap[] findWhereExitIdEquals(int exitId) throws ExitEmpUsersMapDaoException;

	/** 
	 * Returns all rows from the EXIT_EMP_USERS_MAP table that match the criteria 'USER_ID = :userId'.
	 */
	public ExitEmpUsersMap[] findWhereUserIdEquals(int userId) throws ExitEmpUsersMapDaoException;

	/** 
	 * Returns all rows from the EXIT_EMP_USERS_MAP table that match the criteria 'TYPE = :type'.
	 */
	public ExitEmpUsersMap[] findWhereTypeEquals(int type) throws ExitEmpUsersMapDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the EXIT_EMP_USERS_MAP table that match the specified arbitrary SQL statement
	 */
	public ExitEmpUsersMap[] findByDynamicSelect(String sql, Object[] sqlParams) throws ExitEmpUsersMapDaoException;

	/** 
	 * Returns all rows from the EXIT_EMP_USERS_MAP table that match the specified arbitrary SQL statement
	 */
	public ExitEmpUsersMap[] findByDynamicWhere(String sql, Object[] sqlParams) throws ExitEmpUsersMapDaoException;

}

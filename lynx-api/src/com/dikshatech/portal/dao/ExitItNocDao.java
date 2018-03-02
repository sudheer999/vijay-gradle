/*
 * This source file was generated by FireStorm/DAO.
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * For more information please visit http://www.codefutures.com/products/firestorm
 */
package com.dikshatech.portal.dao;

import com.dikshatech.portal.dto.ExitItNoc;
import com.dikshatech.portal.dto.ExitItNocPk;
import com.dikshatech.portal.exceptions.ExitItNocDaoException;

public interface ExitItNocDao {

	/**
	 * Inserts a new row in the EXIT_IT_NOC table.
	 */
	public ExitItNocPk insert(ExitItNoc dto) throws ExitItNocDaoException;

	/**
	 * Updates a single row in the EXIT_IT_NOC table.
	 */
	public void update(ExitItNocPk pk, ExitItNoc dto) throws ExitItNocDaoException;

	/**
	 * Deletes a single row in the EXIT_IT_NOC table.
	 */
	public void delete(ExitItNocPk pk) throws ExitItNocDaoException;

	/**
	 * Returns the rows from the EXIT_IT_NOC table that matches the specified primary-key value.
	 */
	public ExitItNoc findByPrimaryKey(ExitItNocPk pk) throws ExitItNocDaoException;

	/**
	 * Returns all rows from the EXIT_IT_NOC table that match the criteria 'ID = :id'.
	 */
	public ExitItNoc findByPrimaryKey(int id) throws ExitItNocDaoException;

	/**
	 * Returns all rows from the EXIT_IT_NOC table that match the criteria ''.
	 */
	public ExitItNoc[] findAll() throws ExitItNocDaoException;

	/**
	 * Returns all rows from the EXIT_IT_NOC table that match the criteria 'EXIT_ID = :exitId'.
	 */
	public ExitItNoc findWhereExitIdEquals(int exitId) throws ExitItNocDaoException;

	/**
	 * Returns all rows from the EXIT_IT_NOC table that match the criteria 'STATUS_ID = :statusId'.
	 */
	public ExitItNoc[] findWhereStatusIdEquals(int statusId) throws ExitItNocDaoException;

	/**
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/**
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/**
	 * Returns all rows from the EXIT_IT_NOC table that match the specified arbitrary SQL statement
	 */
	public ExitItNoc[] findByDynamicSelect(String sql, Object[] sqlParams) throws ExitItNocDaoException;

	/**
	 * Returns all rows from the EXIT_IT_NOC table that match the specified arbitrary SQL statement
	 */
	public ExitItNoc[] findByDynamicWhere(String sql, Object[] sqlParams) throws ExitItNocDaoException;
}
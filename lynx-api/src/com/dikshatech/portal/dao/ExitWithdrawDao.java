/*
 * This source file was generated by FireStorm/DAO.
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * For more information please visit http://www.codefutures.com/products/firestorm
 */
package com.dikshatech.portal.dao;

import com.dikshatech.portal.dto.ExitWithdraw;
import com.dikshatech.portal.dto.ExitWithdrawPk;
import com.dikshatech.portal.exceptions.ExitWithdrawDaoException;

public interface ExitWithdrawDao {

	/**
	 * Inserts a new row in the EXIT_WITHDRAW table.
	 */
	public ExitWithdrawPk insert(ExitWithdraw dto) throws ExitWithdrawDaoException;

	/**
	 * Updates a single row in the EXIT_WITHDRAW table.
	 */
	public void update(ExitWithdrawPk pk, ExitWithdraw dto) throws ExitWithdrawDaoException;

	/**
	 * Deletes a single row in the EXIT_WITHDRAW table.
	 */
	public void delete(ExitWithdrawPk pk) throws ExitWithdrawDaoException;

	/**
	 * Returns the rows from the EXIT_WITHDRAW table that matches the specified primary-key value.
	 */
	public ExitWithdraw findByPrimaryKey(ExitWithdrawPk pk) throws ExitWithdrawDaoException;

	/**
	 * Returns all rows from the EXIT_WITHDRAW table that match the criteria 'ID = :id'.
	 */
	public ExitWithdraw findByPrimaryKey(int id) throws ExitWithdrawDaoException;

	/**
	 * Returns all rows from the EXIT_WITHDRAW table that match the criteria ''.
	 */
	public ExitWithdraw[] findAll() throws ExitWithdrawDaoException;

	/**
	 * Returns all rows from the EXIT_WITHDRAW table that match the criteria 'EXIT_ID = :exitId'.
	 */
	public ExitWithdraw[] findWhereExitIdEquals(int exitId) throws ExitWithdrawDaoException;

	/**
	 * Returns all rows from the EXIT_WITHDRAW table that match the criteria 'STATUS_ID = :statusId'.
	 */
	public ExitWithdraw[] findWhereStatusIdEquals(int statusId) throws ExitWithdrawDaoException;

	/**
	 * Returns all rows from the EXIT_WITHDRAW table that match the criteria 'ON_STATUS_ID = :onStatusId'.
	 */
	public ExitWithdraw[] findWhereOnStatusIdEquals(int onStatusId) throws ExitWithdrawDaoException;

	/**
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/**
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/**
	 * Returns all rows from the EXIT_WITHDRAW table that match the specified arbitrary SQL statement
	 */
	public ExitWithdraw[] findByDynamicSelect(String sql, Object[] sqlParams) throws ExitWithdrawDaoException;

	/**
	 * Returns all rows from the EXIT_WITHDRAW table that match the specified arbitrary SQL statement
	 */
	public ExitWithdraw[] findByDynamicWhere(String sql, Object[] sqlParams) throws ExitWithdrawDaoException;
}

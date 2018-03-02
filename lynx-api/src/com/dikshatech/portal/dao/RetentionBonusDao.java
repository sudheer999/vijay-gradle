package com.dikshatech.portal.dao;


import com.dikshatech.portal.dto.RetentionBonus;
import com.dikshatech.portal.dto.RetentionBonusPk;
import com.dikshatech.portal.exceptions.RetentionBonusDaoException;

public interface RetentionBonusDao {

	/**
	 * Inserts a new row in the PERDIEM_MASTER_DATA table.
	 */
	public RetentionBonusPk insert(RetentionBonus dto)
			throws RetentionBonusDaoException;

	/**
	 * Updates a single row in the PERDIEM_MASTER_DATA table.
	 */
	public void update(RetentionBonusPk pk, RetentionBonus dto)
			throws RetentionBonusDaoException;

	/**
	 * Deletes a single row in the PERDIEM_MASTER_DATA table.
	 */
	public void delete(RetentionBonusPk pk)
			throws RetentionBonusDaoException;

	/**
	 * Returns the rows from the PERDIEM_MASTER_DATA table that matches the
	 * specified primary-key value.
	 */
	public RetentionBonus findByPrimaryKey(RetentionBonusPk pk)
			throws RetentionBonusDaoException;

	/**
	 * Returns all rows from the PERDIEM_MASTER_DATA table that match the
	 * criteria 'ID = :id'.
	 */
	public RetentionBonus findByPrimaryKey(int id)
			throws RetentionBonusDaoException;

	/**
	 * Returns all rows from the PERDIEM_MASTER_DATA table that match the
	 * criteria ''.
	 */
	public RetentionBonus[] findAll() throws RetentionBonusDaoException;

	/**
	 * Returns all rows from the PERDIEM_MASTER_DATA table that match the
	 * criteria 'ID = :id'.
	 */
	public RetentionBonus[] findWhereIdEquals(int id)
			throws RetentionBonusDaoException;

	/**
	 * Returns all rows from the PERDIEM_MASTER_DATA table that match the
	 * criteria 'USER_ID = :userId'.
	 */
	public RetentionBonus[] findWhereUserIdEquals(int userId)
			throws RetentionBonusDaoException;

	
	/**
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/**
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/**
	 * Returns all rows from the PERDIEM_MASTER_DATA table that match the
	 * specified arbitrary SQL statement
	 */
	public RetentionBonus[] findByDynamicSelect(String sql,
			Object[] sqlParams) throws RetentionBonusDaoException;

	/**
	 * Returns all rows from the PERDIEM_MASTER_DATA table that match the
	 * specified arbitrary SQL statement
	 */
	public RetentionBonus[] findByDynamicWhere(String sql, Object[] sqlParams)
			throws RetentionBonusDaoException;


}
